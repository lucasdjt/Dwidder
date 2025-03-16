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
import modele.dao.PostsDAO;
import modele.dto.Post;
import modele.dto.PostDetails;

@WebServlet("/posts/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class PostServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "img";
    private static final String REPERTORY = "/WEB-INF/vue/";
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
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid post PID");
            return;
        }
        
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid PID format");
            return;
        }
        try {
            String pidStr = pathParts[1];
            int pid = Integer.parseInt(pidStr);
            PostsDAO dao = new PostsDAO();
            PostDetails post = dao.findByPid(pid);
            if (post == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
            return;
            }
            req.setAttribute("post", post);
            req.setAttribute("listePosts", dao.selectFromPostParent(pid));
            req.getRequestDispatcher(REPERTORY + "posts.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int uid = Integer.parseInt(req.getParameter("uid"));
        Integer gid = null;
        if(req.getParameter("gid") != null){
            gid = Integer.parseInt(req.getParameter("gid"));
        }
        Integer pidParent = null;
        if(req.getParameter("pidParent") != null){
            pidParent = Integer.parseInt(req.getParameter("pidParent"));
        }
        String contenu = req.getParameter("contenu");
        Part filePart = req.getPart("image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String media = null;
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
            media = UPLOAD_DIR + SEP + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath());
            }
        }

        LocalDateTime dpub = LocalDateTime.now();
        String dureeStr = req.getParameter("duree");
        String dureeUnite = req.getParameter("dureeUnite");
        Integer dureeDuration = null;
        LocalDateTime dfin = null;
        if(dureeStr != null && !dureeStr.isEmpty()){
            if ("hours".equalsIgnoreCase(dureeUnite)) {
                dureeDuration = Integer.parseInt(dureeStr);
            } else if ("days".equalsIgnoreCase(dureeUnite)) {
                dureeDuration = Integer.parseInt(dureeStr)*24;
            }
            dfin = dpub.plusHours(dureeDuration);
        }
        PostsDAO dao = new PostsDAO();
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        try {
            dao.insert(new Post(0, uid, gid, pidParent, contenu, media, dpub, dfin));
            res.sendRedirect(referer + "?success=1");
        } catch (Exception e) {
            res.sendRedirect(referer + "?success=0");
        }
    }
}
