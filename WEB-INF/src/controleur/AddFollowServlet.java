package controleur;

import java.io.IOException;
import java.time.LocalDateTime;

import modele.dao.AbonnementsDAO;
import modele.dto.Abonnement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addFollow")
public class AddFollowServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int uidFollowed = Integer.parseInt(req.getParameter("follow"));
        int uidFollowers = Integer.parseInt(req.getParameter("follower"));
        AbonnementsDAO abonnementsDAO = new AbonnementsDAO();

        Abonnement existingAbonnement = abonnementsDAO.findByFollowAndFollowed(uidFollowers, uidFollowed);
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
        res.sendRedirect(referer);
    }
}
