package controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            List<Reaction> listR = dao.getPostReactions(pid);
            List<User> user = new ArrayList<>();
            for(Reaction r : listR){
                user.add(uDao.findByUid(r.getUid()));
            }
            req.setAttribute("reactions", user);
            req.setAttribute("pid", pid);
            req.getRequestDispatcher(REPERTORY + "listeLike.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
        }
    }
}
