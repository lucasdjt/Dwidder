package controleur;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.PostsDAO;
import modele.dao.UsersDAO;
import modele.dto.Reaction;
import modele.dto.User;

@WebServlet("/reactions/*")
public class LikeServlet extends HttpServlet {
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
        if (pathInfo == null || pathInfo.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid pid");
            return;
        }
        
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid pid format");
            return;
        }
        try {
            String pidStr = pathParts[1];
            int pid = Integer.parseInt(pidStr);
            PostsDAO dao = new PostsDAO();
            UsersDAO uDao = new UsersDAO();
            List<Reaction> listR = dao.getListReactionsOfPost(pid);
            Map<User, String> userReactions = new HashMap<>();
            for (Reaction r : listR) {
                User u = uDao.findUserByUid(r.getUid());
                userReactions.put(u, r.getTypeEmoji());
            }
            req.setAttribute("listReactions", userReactions);
            req.setAttribute("pid", pid);
            req.getRequestDispatcher(REPERTORY + "listeLike.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
        }
    }
}
