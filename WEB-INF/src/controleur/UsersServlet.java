package controleur;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.UsersDAO;
import modele.dto.User;

@WebServlet("/user/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class UsersServlet extends HttpServlet {
    private static final String REPERTORY = "/WEB-INF/vue/";

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
        UsersDAO dao = new UsersDAO();
        if (pathInfo == null){
            if(req.getParameter("query") != null){
                req.setAttribute("listFollow", dao.getListUsersWithEquivalentKey(req.getParameter("query")));
            } else {
                req.setAttribute("listFollow", dao.listUsers());
            }
            req.getRequestDispatcher(REPERTORY + "listeUser.jsp").forward(req, res);
        }
        if (pathInfo.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user IdPseudo");
            return;
        }
        
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid IdPseudo format");
            return;
        }
        try {
            String idPseudo = pathParts[1];
            User user = dao.findUserByPseudo(idPseudo);
            if (user == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
            }
            List<User> follow = dao.getListFollowsOfUser(user.getUid());
            List<User> followers = dao.getListFollowersOfUser(user.getUid());
            req.setAttribute("user", user);
            req.setAttribute("listePosts", dao.getListPostsOfUser(user.getUid(), false));
            req.setAttribute("follows", follow.size());
            req.setAttribute("followers", followers.size());
            req.getRequestDispatcher(REPERTORY + "user.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
        }
    }
}
