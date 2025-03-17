package controleur;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dao.FavorisDAO;
import modele.dao.UsersDAO;
import modele.dto.Favori;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addFavori")
public class AddFavoriServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("uid") == null || session.getAttribute("pseudo") == null) {
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        int uid = (int) req.getSession().getAttribute("uid");
        int pid = Integer.parseInt(req.getParameter("pid"));
        FavorisDAO favorisDAO = new FavorisDAO();

        Favori existingFavori = favorisDAO.findByUidAndPid(uid, pid);
        if (existingFavori == null) {
            Favori newFavori = new Favori(uid, pid, LocalDateTime.now());
            System.out.println("insert");
            favorisDAO.insert(newFavori);
        } else {
            System.out.println(existingFavori);
            favorisDAO.delete(existingFavori);
        }
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        UsersDAO dao = new UsersDAO();
        List<Integer> listFavoriUser = new ArrayList<>();
        dao.getUserFavoris((int) req.getSession().getAttribute("uid")).forEach(favori -> listFavoriUser.add(favori.getPid()));
        req.getSession().setAttribute("listFavoriUser", listFavoriUser);
        res.sendRedirect(referer);
    }
}
