package controleur;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dao.AbonnementsDAO;
import modele.dto.Abonnement;
import modele.dao.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addFollow")
public class AddFollowServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("uid") == null || session.getAttribute("pseudo") == null) {
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        int uidFollowed = Integer.parseInt(req.getParameter("follow"));
        int uidFollowers = Integer.parseInt(req.getParameter("follower"));
        AbonnementsDAO abonnementsDAO = new AbonnementsDAO();

        Abonnement existingAbonnement = abonnementsDAO.findAbonnement(uidFollowers, uidFollowed);
        if (existingAbonnement == null) {
            Abonnement newAbonnement = new Abonnement(uidFollowers, uidFollowed, LocalDateTime.now());
            abonnementsDAO.insert(newAbonnement);
        } else {
            abonnementsDAO.delete(existingAbonnement);
        }
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        UsersDAO dao = new UsersDAO();
        List<Integer> listFollowUser = new ArrayList<>();
        dao.getListFollowsOfUser((int) req.getSession().getAttribute("uid")).forEach(follow -> listFollowUser.add(follow.getUid()));
        List<Integer> listFollowersUser = new ArrayList<>();
        dao.getListFollowersOfUser((int) req.getSession().getAttribute("uid")).forEach(follower -> listFollowersUser.add(follower.getUid()));
        req.getSession().setAttribute("listFollowUser", listFollowUser);
        req.getSession().setAttribute("listFollowersUser", listFollowersUser);
        res.sendRedirect(referer);
    }
}
