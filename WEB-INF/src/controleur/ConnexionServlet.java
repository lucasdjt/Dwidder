package controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.UsersDAO;
import modele.dto.User;
import modele.dao.LogsDAO;
import modele.dto.Reaction;

@WebServlet("/connexion")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class ConnexionServlet extends HttpServlet {
    private static final String REPERTORY = "/WEB-INF/vue/";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            LogsDAO.insert(req.getSession().getAttribute("pseudo").toString(), "Déconnexion");
            session.invalidate();
        }
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher(REPERTORY + "connexion.jsp").forward(req, res);
    }

        protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String emailOrMDP = req.getParameter("identifier");
        User user = null;
        UsersDAO dao = new UsersDAO();
        if (emailOrMDP.contains("@")) {
            user = dao.findUserByEmail(emailOrMDP);
        } else {
            user = dao.findUserByPseudo(emailOrMDP);
        }
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        if(user != null && user.getMdp().equals(req.getParameter("password"))) {
            req.getSession().setAttribute("uid", user.getUid());
            req.getSession().setAttribute("pseudo", user.getIdPseudo());
            List<Integer> listFavoriUser = new ArrayList<>();
            dao.getListFavorisOfUser(user.getUid()).forEach(favori -> listFavoriUser.add(favori.getPid()));
            List<Integer> listFollowUser = new ArrayList<>();
            dao.getListFollowsOfUser(user.getUid()).forEach(follow -> listFollowUser.add(follow.getUid()));
            List<Integer> listFollowersUser = new ArrayList<>();
            dao.getListFollowersOfUser(user.getUid()).forEach(follower -> listFollowersUser.add(follower.getUid()));
            req.getSession().setAttribute("listFavoriUser", listFavoriUser);
            req.getSession().setAttribute("listFollowUser", listFollowUser);
            req.getSession().setAttribute("listFollowersUser", listFollowersUser);
            req.getSession().setAttribute("pseudo", user.getIdPseudo());
            req.getSession().setAttribute("tri", false);
            req.getSession().setAttribute("admin", user.isAdmin());
            Map<Integer, String> listReactionsUser = new HashMap<>();
            for (Reaction r : dao.getListReactionsOfUser(user.getUid())) {
                listReactionsUser.put(r.getPid(), r.getTypeEmoji());
            }
            req.getSession().setAttribute("listReactionsUser", listReactionsUser);
            LogsDAO.insert(req.getSession().getAttribute("pseudo").toString(), "Connexion de l'utilisateur");
            res.sendRedirect(req.getContextPath() + "/accueil");
        } else {
            res.sendRedirect(referer + "?error=1");
        }
    }
}
