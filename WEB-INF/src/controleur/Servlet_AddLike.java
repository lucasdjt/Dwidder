package controleur;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import modele.dao.ReactionsDAO;
import modele.dao.UsersDAO;
import modele.dto.Reaction;
import modele.dao.LogsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addReaction")
public class Servlet_AddLike extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("uid") == null || session.getAttribute("pseudo") == null) {
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        int uid = Integer.parseInt(req.getParameter("uid"));
        int pid = Integer.parseInt(req.getParameter("pid"));
        String reaction = req.getParameter("type");
        UsersDAO dao = new UsersDAO();
        ReactionsDAO reactionsDAO = new ReactionsDAO();
        Reaction existingReaction = reactionsDAO.findReaction(uid, pid);
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        if(req.getParameter("supprimer") != null) {
            reactionsDAO.delete(existingReaction);
        } else {
            if (existingReaction == null) {
                Reaction newReaction = new Reaction(uid, pid, reaction);
                reactionsDAO.insert(newReaction);
                LogsDAO.insert(session.getAttribute("pseudo").toString(), "Ajout d'un réaction \"" + reaction + "\" au post " + pid);
            } else {
                existingReaction.setType(reaction);
                reactionsDAO.update(existingReaction);
                LogsDAO.insert(session.getAttribute("pseudo").toString(), "Modification de la réaction du post " + pid + "par " + reaction);
            }
        }
        Map<Integer, String> listReactionsUser = new HashMap<>();
        for (Reaction r : dao.getListReactionsOfUser(uid)) {
            listReactionsUser.put(r.getPid(), r.getTypeEmoji());
        }
        req.getSession().setAttribute("listReactionsUser", listReactionsUser);
        res.sendRedirect(referer);
    }
}
