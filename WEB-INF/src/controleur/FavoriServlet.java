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
import modele.dao.PostsDAO;
import modele.dao.UsersDAO;
import modele.dto.Favori;
import modele.dto.PostDetails;
import utils.BAO;
import modele.dao.FavorisDAO;
import modele.dao.LogsDAO;

@WebServlet("/favori/*")
public class FavoriServlet extends HttpServlet {

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
    
        UsersDAO uDAO = new UsersDAO();
        PostsDAO pDAO = new PostsDAO();
        FavorisDAO fDAO = new FavorisDAO();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<Favori> fav = uDAO.getListFavorisOfUser((int) session.getAttribute("me_uid"));
            List<PostDetails> list = fav.stream()
                                        .map(f -> pDAO.findPostDetails(f.getPid()))
                                        .toList();
            session.setAttribute("listeDesPosts", list);
            req.getRequestDispatcher(BAO.getRepertory() + "listeFavoris.jsp").forward(req, res);
            return;
        }
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            session.setAttribute("error", "Vous avez entré un mauvais lien.");
            res.sendRedirect(req.getContextPath() + "/accueil");
            return;
        }

        if ("change".equals(pathParts[1])) {
            int uid = (int) req.getSession().getAttribute("me_uid");
            int pid = Integer.parseInt(req.getParameter("pid"));

            Favori existingFavori = fDAO.findFavori(uid, pid);
            if (existingFavori == null) {
                Favori newFavori = new Favori(uid, pid, LocalDateTime.now());
                fDAO.insert(newFavori);
                LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "Ajout d'un nouveau favori : " + pid);
            } else {
                fDAO.delete(existingFavori);
                LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "Suppression d'un favori : " + pid);
            }
            
            List<Integer> listFavoriUser = new ArrayList<>();
            uDAO.getListFavorisOfUser((int) req.getSession().getAttribute("me_uid")).forEach(favori -> listFavoriUser.add(favori.getPid()));
            
            req.getSession().setAttribute("me_listFavori", listFavoriUser);
            res.sendRedirect(referer + "#post" + pid);
        }
    }
}
