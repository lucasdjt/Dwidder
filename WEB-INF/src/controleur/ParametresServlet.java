package controleur;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import modele.dao.UsersDAO;
import modele.dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/parametres")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class ParametresServlet extends HttpServlet {
    private static final String REPERTORY = "/WEB-INF/vue/";
    private static final String UPLOAD_DIR = "img";
    private static final String SEP = File.separator;

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("uid") == null || session.getAttribute("pseudo") == null) {
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        UsersDAO usersDAO = new UsersDAO();
        req.setAttribute("user", usersDAO.findByUid((int) session.getAttribute("uid")));
        req.getRequestDispatcher(REPERTORY + "parametres.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int uid = (int) req.getSession().getAttribute("uid");
        UsersDAO usersDAO = new UsersDAO();
        User user = usersDAO.findByUid(uid);
        req.setCharacterEncoding("UTF-8");
        user.setPseudo(req.getParameter("pseudo"));
        user.setPrenom(req.getParameter("prenom"));
        user.setNomUser(req.getParameter("nomUser"));
        user.setBio(req.getParameter("bio"));
        Part filePart = req.getPart("pdp");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String pdp = null;
        if (fileName != null && !fileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("") + SEP + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            
            File file = new File(uploadPath + SEP + fileName);
            String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
            String extension = fileName.substring(fileName.lastIndexOf('.'));
            int counter = 1;
            while (file.exists()) {
            fileName = baseName + "_" + counter + extension;
            file = new File(uploadPath + SEP + fileName);
            counter++;
            }
            pdp = UPLOAD_DIR + SEP + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath());
            }
        }
        if(pdp != null){
            user.setPdp(pdp);
        }
        String dnaiss = req.getParameter("dnaiss");
        if (dnaiss != null && !dnaiss.isEmpty()) {
            user.setDnaiss(LocalDate.parse(dnaiss));
        } else {
            user.setDnaiss(null);
        }
        user.setLoca(req.getParameter("loca"));
        String referer = req.getHeader("Referer");
        UsersDAO uDao = new UsersDAO();
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        String idPseudo = req.getParameter("idPseudo");
        String email = req.getParameter("email");
        String mdp = req.getParameter("mdp");
        if(mdp != null){
            user.setMdp(mdp);
        }
        if(!idPseudo.equals(user.getIdPseudo()) && uDao.findByIdPseudo(idPseudo) != null){
            res.sendRedirect(referer + "?success=0");
            return;
        }
        if(!email.equals(user.getHTMLEmail()) && uDao.findByEmail(email) != null && email.contains("@")){
            res.sendRedirect(referer + "?success=0");
            return;
        }
        user.setIdPseudo(idPseudo);
        user.setEmail(email);
        try {
            uDao.update(user);
            req.getSession().setAttribute("pseudo", user.getIdPseudo());
            req.setAttribute("user", user);
            res.sendRedirect(req.getContextPath() + "/parametres?success=1");
        } catch (Exception e) {
            res.sendRedirect(referer + "?success=0");
        }
    }
}
