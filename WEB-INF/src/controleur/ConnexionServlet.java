package controleur;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.UsersDAO;
import modele.dto.User;
import utils.BAO;
import modele.dao.LogsDAO;
import modele.dto.Reaction;

@WebServlet("/connexion")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class ConnexionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String error = (String) session.getAttribute("error");
            String success = (String) session.getAttribute("success");
            if(req.getSession().getAttribute("pseudo") != null) LogsDAO.insert(req.getSession().getAttribute("pseudo").toString(), "Déconnexion");
            session.invalidate();
            session = req.getSession(true);
            session.setAttribute("error", error);
            session.setAttribute("success", success);
        }
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher(BAO.getRepertory() + "connexion.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(true);
        UsersDAO dao = new UsersDAO();

        String emailOrPseudo = req.getParameter("identifier");

        User user = emailOrPseudo.contains("@") ? dao.findUserByEmail(emailOrPseudo) : dao.findUserByPseudo(emailOrPseudo);
        
        if(user == null || !user.getMdp().equals(req.getParameter("password"))){
            session.setAttribute("error", "L'utilisateur ou le mot de passe est incorrect");
            req.getRequestDispatcher(BAO.getRepertory() + "connexion.jsp").forward(req, res);
            return;
        }
        List<Integer> listFavoriUser = dao.getListFavorisOfUser(user.getUid())
                                            .stream()
                                            .map(favori -> favori.getPid())
                                            .toList();
        List<Integer> listFollowUser = dao.getListFollowsOfUser(user.getUid())
                                            .stream()
                                            .map(follow -> follow.getUid())
                                            .toList();
        List<Integer> listFollowersUser = dao.getListFollowersOfUser(user.getUid())
                                                .stream()
                                                .map(follower -> follower.getUid())
                                                .toList();
        Map<Integer, String> listReactionsUser = dao.getListReactionsOfUser(user.getUid())
                                                    .stream()
                                                    .collect(Collectors.toMap(Reaction::getPid, Reaction::getTypeEmoji));
        session.setAttribute("user", user);
        session.setAttribute("uid", user.getUid());
        session.setAttribute("pseudo", user.getIdPseudo());
        session.setAttribute("admin", user.isAdmin());
        session.setAttribute("tri", false);
        session.setAttribute("listFavoriUser", listFavoriUser);
        session.setAttribute("listFollowUser", listFollowUser);
        session.setAttribute("listFollowersUser", listFollowersUser);
        session.setAttribute("listReactionsUser", listReactionsUser);
        session.setAttribute("success", "Bienvenue " + user.getIdPseudo());
        LogsDAO.insert(user.getIdPseudo(), "Connexion de l'utilisateur");
        res.sendRedirect(req.getContextPath() + "/accueil");
    }
}
