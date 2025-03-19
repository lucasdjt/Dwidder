package controleur;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.PostsDAO;
import modele.dto.Post;
import modele.dto.PostDetails;
import modele.dto.User;
import utils.BAO;
import modele.dao.GroupesDAO;
import modele.dao.LogsDAO;

@WebServlet("/post/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class PostServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("me_user") == null) {
            session = req.getSession(true);
            session.setAttribute("error", "Vous n'êtes pas connecté");
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) referer = referer.substring(0, referer.indexOf("?"));
        
        PostsDAO pDao = new PostsDAO();
        GroupesDAO gDao = new GroupesDAO();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.split("/").length < 2) {
            session.setAttribute("error", "Le lien est invalide");
            res.sendRedirect(req.getContextPath() + "/accueil");
            return;
        }

        String[] pathParts = pathInfo.split("/");

        if ("delete".equals(pathParts[1])) {
            try {
                pDao.delete(Integer.parseInt(pathParts[2]));
                LogsDAO.insert(req.getSession().getAttribute("me_pseudo").toString(), "Suppression du post " + pathParts[2]);
                session.setAttribute("success", "Vous avez supprimé un post");
                res.sendRedirect(referer);
            } catch (NumberFormatException e) {
                session.setAttribute("error", "Le lien est invalide");
                res.sendRedirect(req.getContextPath() + "/accueil");
            }
            return;
        }

        try {
            int pid = Integer.parseInt(pathParts[1]);
            PostDetails post = pDao.findPostDetails(pid);

            if (post == null) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
                return;
            }

            System.out.println(post.getGid());
            if(post.getGid() != null && post.getGid() != 0){
                if(!gDao.getListUsersOfAGroup(post.getGid()).contains((User)session.getAttribute("me_user"))){
                    session.setAttribute("error", "Vous n'avez pas accès à ce post");
                    res.sendRedirect(req.getContextPath() + "/accueil");
                    return;
                }
            }

            session.setAttribute("post", post);
            session.setAttribute("listeDesPosts", pDao.getListPostsOfPostParent(pid));

            req.getRequestDispatcher(BAO.getRepertory() + "posts.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Le lien est invalide");
            res.sendRedirect(req.getContextPath() + "/accueil");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("me_user") == null) {
            session = req.getSession(true);
            session.setAttribute("error", "Vous n'êtes pas connecté");
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        
        PostsDAO pDao = new PostsDAO();

        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) referer = referer.substring(0, referer.indexOf("?"));

        int uid = Integer.parseInt(req.getParameter("uid"));
        Integer gid = null;
        if(req.getParameter("gid") != null && Integer.parseInt(req.getParameter("gid")) != 0) gid = Integer.parseInt(req.getParameter("gid"));
        Integer pidParent = null;
        if(req.getParameter("pidParent") != null) pidParent = Integer.parseInt(req.getParameter("pidParent"));
        String contenu = req.getParameter("contenu");
        String media = BAO.uploadImage(req.getPart("image"), getServletContext().getRealPath(""));
        if (media == null && (contenu == null || contenu.isEmpty())) {
            session.setAttribute("error", "Mettez au moins un contenu");
            res.sendRedirect(referer);
            return;
        }
        LocalDateTime dpub = LocalDateTime.now();
        String dureeStr = req.getParameter("duree");
        String dureeUnite = req.getParameter("dureeUnite");
        LocalDateTime dfin = null;
        if (dureeStr != null && !dureeStr.isEmpty()) {
            int dureeDuration = Integer.parseInt(dureeStr) * ("days".equalsIgnoreCase(dureeUnite) ? 24 : 1);
            dfin = dpub.plusHours(dureeDuration);
        }

        try {
            pDao.insert(new Post(0, uid, gid, pidParent, contenu, media, dpub, dfin));
            LogsDAO.insert(req.getSession().getAttribute("me_pseudo").toString(), "Ajout d'un nouveau post");
            
            session.setAttribute("success", "Vous avez ajouté un nouveau post");
            res.sendRedirect(referer);
        } catch (Exception e) {
            session.setAttribute("error", "Le post n'a pas été ajouté");
            res.sendRedirect(referer);
        }
    }
}
