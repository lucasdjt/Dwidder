package controleur;

import java.io.IOException;

import modele.dao.PostsDAO;
import modele.dao.UsersDAO;
import utils.BAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {

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

        PostsDAO postsDAO = new PostsDAO();
        UsersDAO usersDAO = new UsersDAO();

        postsDAO.deleteAllExpiredPosts();
        
        session.setAttribute("listeDesPosts", postsDAO.getListPostsInPublic((boolean) session.getAttribute("me_tri")));
        session.setAttribute("listeDesUsers", usersDAO.listUsers());

        req.getRequestDispatcher(BAO.getRepertory() + "accueil.jsp").forward(req, res);
    }
}
