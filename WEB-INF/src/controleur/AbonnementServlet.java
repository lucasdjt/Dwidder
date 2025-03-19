package controleur;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.UsersDAO;
import modele.dto.User;
import modele.dao.AbonnementsDAO;
import modele.dao.LogsDAO;
import modele.dto.Abonnement;
import utils.BAO;

@WebServlet("/follow/*")
public class AbonnementServlet extends HttpServlet {

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

        UsersDAO uDao = new UsersDAO();
        AbonnementsDAO aDao = new AbonnementsDAO();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user IdPseudo");
            return;
        }
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.split("/").length < 2) {
            session.setAttribute("error", "Vous avez entré un mauvais lien.");
            res.sendRedirect(req.getContextPath() + "/accueil");
            return;
        }
        String[] pathParts = pathInfo.split("/");

        if ("follow".equals(pathParts[1]) || "follower".equals(pathParts[1])) {
            try {
                User user = uDao.findUserByPseudo(pathParts[2]);
                List<User> follow = new ArrayList<>();
                if ("follow".equals(pathParts[1])) follow = uDao.getListFollowsOfUser(user.getUid());
                else follow = uDao.getListFollowersOfUser(user.getUid());

                session.setAttribute("listUsers", follow);
                req.getRequestDispatcher(BAO.getRepertory() + "listeUser.jsp").forward(req, res);
            } catch (NumberFormatException e) {
                res.sendRedirect(req.getContextPath() + "/accueil");
            }
        }

        if("addFollow".equals(pathParts[1])){
            int uidFollowed = Integer.parseInt(pathParts[2]);
            int uidFollowers = (int) session.getAttribute("me_uid");
            Abonnement existingAbonnement = aDao.findAbonnement(uidFollowers, uidFollowed);
            if (existingAbonnement == null) {
                aDao.insert(new Abonnement(uidFollowers, uidFollowed, LocalDateTime.now()));
                LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "Ajout d'un nouvelle abonnement : " + uidFollowers + "-->" + uidFollowed);
            } else {
                aDao.delete(existingAbonnement);
                LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "Suppression d'un abonnement : " + uidFollowers + "-->" + uidFollowed);
            }

            List<Integer> listFollowUser = new ArrayList<>();
            List<Integer> listFollowersUser = new ArrayList<>();
            uDao.getListFollowsOfUser((int) req.getSession().getAttribute("me_uid")).forEach(follow -> listFollowUser.add(follow.getUid()));
            uDao.getListFollowersOfUser((int) req.getSession().getAttribute("me_uid")).forEach(follower -> listFollowersUser.add(follower.getUid()));
            
            req.getSession().setAttribute("me_listFollow", listFollowUser);
            req.getSession().setAttribute("me_listFollowers", listFollowersUser);

            res.sendRedirect(referer + "#user_" + uDao.findUserByUid(uidFollowed).getIdPseudo());
        }
    }
}
