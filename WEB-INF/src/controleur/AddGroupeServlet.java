package controleur;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import modele.dao.GroupesDAO;
import modele.dao.MembresDAO;
import modele.dto.Groupe;
import modele.dto.Membre;
import modele.dao.LogsDAO;

@WebServlet("/addGroupe")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AddGroupeServlet extends HttpServlet {
    final String REPERTORY = "/WEB-INF/vue/";
    private static final String UPLOAD_DIR = "img";
    private static final String SEP = File.separator;

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("uid") == null || session.getAttribute("pseudo") == null) {
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        req.getRequestDispatcher(REPERTORY + "creerGroupe.jsp").forward(req, res);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        req.setCharacterEncoding("UTF-8");
        int uid = Integer.parseInt(req.getParameter("uid"));
        Part filePart = req.getPart("pdpGrp");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String pdpGrp = null;
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
            pdpGrp = UPLOAD_DIR + SEP + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath());
            }
        }
        if(pdpGrp == null){
            pdpGrp = "img" + SEP + "pdp.png";
        }
        String nomGrp = req.getParameter("nomGrp");
        String description = req.getParameter("description");
        LocalDateTime dcreat = LocalDateTime.now();
        GroupesDAO gDao = new GroupesDAO();
        String referer = req.getHeader("Referer");
        MembresDAO mDao = new MembresDAO();
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        try {
            if(gDao.findGroupByName(nomGrp) != null){
                res.sendRedirect(referer + "?existant=0");
                return;
            }
            gDao.insert(new Groupe(0, uid, pdpGrp, nomGrp, description, dcreat));
            mDao.insert(new Membre(uid, gDao.findGroupByName(nomGrp).getGid(), dcreat));
            LogsDAO.insert(session.getAttribute("pseudo").toString(), "Création d'un nouveau groupe : " + nomGrp);
            
            res.sendRedirect(req.getContextPath() + "/accueil?groupe=1");
        } catch (Exception e) {
            res.sendRedirect(referer + "?success=0");
        }
    }
}
