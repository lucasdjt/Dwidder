package controleur;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modele.dao.UsersDAO;
import modele.dto.User;

@WebServlet("/addAccount")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AddAccountServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "img";
    private static final String SEP = File.separator;

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String idPseudo = req.getParameter("idPseudo");
        String pseudo = req.getParameter("pseudo");
        String prenom = req.getParameter("prenom");
        String nomUser = req.getParameter("nomUser");
        String email = req.getParameter("email");
        String mdp = req.getParameter("mdp");
        String bio = req.getParameter("bio");
        Part filePart = req.getPart("image");
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
        if(pdp == null){
            pdp = "img" + SEP + "pdp.png";
        }
        LocalDateTime dinsc = LocalDateTime.now();
        LocalDate dnaiss = LocalDate.parse(req.getParameter("dnaiss"));
        String loca = req.getParameter("loca");
        String sexe = req.getParameter("sexe");
        String tel = req.getParameter("tel");
        String langue = req.getParameter("langue");

        String referer = req.getHeader("Referer");
        UsersDAO uDao = new UsersDAO();
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        if (uDao.findByIdPseudo(idPseudo) != null || uDao.findByEmail(email) != null || email.contains("@")) {
            res.sendRedirect(referer + "?success=0");
            return;
        }
        try {
            uDao.insert(new User(0, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, sexe, tel, langue, false));
            res.sendRedirect(req.getContextPath() + "/connexion?success=1");
        } catch (Exception e) {
            res.sendRedirect(referer + "?success=0");
        }
    }
}
