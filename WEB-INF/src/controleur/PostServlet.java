package controleur;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modele.dao.PostsDAO;
import modele.dto.Post;

@WebServlet("/posts")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class PostServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "img";
    
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        int uid = Integer.parseInt(req.getParameter("uid"));
        Integer gid = Integer.parseInt(req.getParameter("gid"));
        Integer pidParent = Integer.parseInt(req.getParameter("pidParent"));
        String contenu = req.getParameter("contenu");

        Part filePart = req.getPart("image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String media = null;
        if (fileName != null && !fileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            media = uploadPath + File.separator + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, new File(media).toPath());
            }
        }

        LocalDateTime dpub = LocalDateTime.now();
        String dureeStr = req.getParameter("duree");
        String dureeUnite = req.getParameter("dureeUnite");
        int dureeInt = (dureeStr != null && !dureeStr.isEmpty()) ? Integer.parseInt(dureeStr) : 0;
        Duration dureePost = Duration.ZERO;

        if ("Heures".equalsIgnoreCase(dureeUnite)) {
            dureePost = Duration.ofHours(dureeInt);
        } else if ("Jours".equalsIgnoreCase(dureeUnite)) {
            dureePost = Duration.ofDays(dureeInt);
        }

        PostsDAO dao = new PostsDAO();
        try {
            dao.insert(new Post(0, uid, gid, pidParent, contenu, media, dpub, dureePost));
            String referer = req.getHeader("Referer");
            if (referer != null && !referer.isEmpty()) {
            res.sendRedirect(referer + "?success=1");
            } else {
            res.sendRedirect(req.getContextPath() + "/accueil?success=1");
            }
        } catch (Exception e) {
            String referer = req.getHeader("Referer");
            if (referer != null && !referer.isEmpty()) {
            res.sendRedirect(referer + "?success=0");
            } else {
            res.sendRedirect(req.getContextPath() + "/accueil?success=0");
            }
        }
    }
}
