package controleur;

import java.io.IOException;
import java.time.LocalDateTime;

import modele.dao.FavorisDAO;
import modele.dto.Favori;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addFavori")
public class AddFavoriServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int uid = Integer.parseInt(req.getParameter("uid"));
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
        res.sendRedirect(referer);
    }
}
