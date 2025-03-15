package controleur;

import java.io.IOException;
import java.time.LocalDateTime;
import modele.dao.ReactionsDAO;
import modele.dto.Reaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addLike")
public class AddLikeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (req.getSession().getAttribute("uid") == null) {
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        int uid = Integer.parseInt(req.getParameter("uid"));
        int pid = Integer.parseInt(req.getParameter("pid"));
        ReactionsDAO reactionsDAO = new ReactionsDAO();

        Reaction existingReaction = reactionsDAO.findByUidAndPid(uid, pid);
        if (existingReaction == null) {
            Reaction newReaction = new Reaction(uid, pid, "LIKES", LocalDateTime.now());
            reactionsDAO.insert(newReaction);
        } else {
            reactionsDAO.delete(existingReaction);
        }
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        res.sendRedirect(referer);
    }
}
