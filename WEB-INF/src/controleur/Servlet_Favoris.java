package controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.PostsDAO;
import modele.dao.UsersDAO;
import modele.dto.Favori;
import modele.dto.PostDetails;

@WebServlet("/favoris")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class Servlet_Favoris extends HttpServlet {
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
        UsersDAO usersDAO = new UsersDAO();
        PostsDAO postsDAO = new PostsDAO();
        int uid = (int) session.getAttribute("uid");
        List<Favori> fav = usersDAO.getListFavorisOfUser(uid);
        List<PostDetails> list = new ArrayList<>();
        for(Favori f : fav){
            list.add(postsDAO.findPostDetails(f.getPid()));
        }
        req.setAttribute("listePosts", list);
        req.getRequestDispatcher(REPERTORY + "listeFavoris.jsp").forward(req, res);
    }
}
