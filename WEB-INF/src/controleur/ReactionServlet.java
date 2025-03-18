package controleur;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.PostsDAO;
import modele.dao.UsersDAO;
import modele.dto.Reaction;
import modele.dto.User;
import modele.dao.LogsDAO;
import modele.dao.ReactionsDAO;
import utils.BAO;

@WebServlet("/reaction/*")
public class ReactionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
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
        
        PostsDAO pDao = new PostsDAO();
        UsersDAO uDao = new UsersDAO();
        ReactionsDAO rDao = new ReactionsDAO();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.split("/").length < 2) {
            session.setAttribute("error", "Le lien est invalide");
            res.sendRedirect(req.getContextPath() + "/accueil");
            return;
        }
        String[] pathParts = pathInfo.split("/");

        if ("add".equals(pathParts[1]) || "delete".equals(pathParts[1])) {
            int uid = Integer.parseInt(req.getParameter("uid"));
            int pid = Integer.parseInt(req.getParameter("pid"));
            String reaction = req.getParameter("type");
            Reaction existingReaction = rDao.findReaction(uid, pid);

            if("add".equals(pathParts[1])) {
                if (existingReaction == null) {
                    Reaction newReaction = new Reaction(uid, pid, reaction);
                    rDao.insert(newReaction);
                    LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "Ajout d'un réaction \"" + reaction + "\" au post " + pid);
                } else {
                    existingReaction.setType(reaction);
                    rDao.update(existingReaction);
                    LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "Modification de la réaction du post " + pid + "par " + reaction);
                }
            } else {
                rDao.delete(existingReaction);
            }

            Map<Integer, String> listReactionsUser = new HashMap<>();
            for (Reaction r : uDao.getListReactionsOfUser(uid)) listReactionsUser.put(r.getPid(), r.getTypeEmoji());

            session.setAttribute("me_listReactions", listReactionsUser);
            res.sendRedirect(referer);
            return;
        }

        try {
            int pid = Integer.parseInt(pathParts[1]);
            List<Reaction> listR = pDao.getListReactionsOfPost(pid);
            Map<User, String> userReactions = new HashMap<>();
            for (Reaction r : listR) userReactions.put(uDao.findUserByUid(r.getUid()), r.getTypeEmoji());

            session.setAttribute("listeDesReactions", userReactions);
            session.setAttribute("pidReaction", pid);
            
            req.getRequestDispatcher(BAO.getRepertory() + "listeLike.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Le lien est invalide");
            res.sendRedirect(req.getContextPath() + "/accueil");
        }
    }
}
