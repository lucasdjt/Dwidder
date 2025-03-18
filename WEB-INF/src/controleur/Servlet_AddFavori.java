package controleur;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dao.FavorisDAO;
import modele.dao.LogsDAO;
import modele.dao.UsersDAO;
import modele.dto.Favori;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addFavori")
public class Servlet_AddFavori extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("me_uid") == null || session.getAttribute("me_pseudo") == null) {
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        int uid = (int) req.getSession().getAttribute("me_uid");
        int pid = Integer.parseInt(req.getParameter("pid"));
        FavorisDAO favorisDAO = new FavorisDAO();

        Favori existingFavori = favorisDAO.findFavori(uid, pid);
        if (existingFavori == null) {
            Favori newFavori = new Favori(uid, pid, LocalDateTime.now());
            favorisDAO.insert(newFavori);
            LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "Ajout d'un nouveau favori : " + pid);
        } else {
            LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "Suppression d'un favori : " + pid);
            favorisDAO.delete(existingFavori);
        }
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        UsersDAO dao = new UsersDAO();
        List<Integer> listFavoriUser = new ArrayList<>();
        dao.getListFavorisOfUser((int) req.getSession().getAttribute("me_uid")).forEach(favori -> listFavoriUser.add(favori.getPid()));
        req.getSession().setAttribute("me_listFavori", listFavoriUser);
        res.sendRedirect(referer);
    }
}
