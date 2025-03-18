package controleur;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.LogsDAO;
import modele.dao.UsersDAO;
import modele.dto.User;
import utils.BAO;

@WebServlet("/user/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class UserServlet extends HttpServlet {

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

        UsersDAO uDao = new UsersDAO();
        
        String pathInfo = req.getPathInfo();
        if (pathInfo == null){
            if(req.getParameter("query") != null){
                session.setAttribute("listUsers", uDao.getListUsersWithEquivalentKey(req.getParameter("query")));
            } else {
                session.setAttribute("listUsers", uDao.listUsers());
            }
            req.getRequestDispatcher(BAO.getRepertory() + "listeUser.jsp").forward(req, res);
        }
        if (pathInfo.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user IdPseudo");
            return;
        }
        
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid IdPseudo format");
            return;
        }

        if ("delete".equals(pathParts[1])) {
            String pseudo = session.getAttribute("me_pseudo").toString();
            try {
                uDao.delete(pseudo);
                session.setAttribute("success", "Vous avez supprimé votre compte.");
                LogsDAO.insert(req.getSession().getAttribute("me_pseudo").toString(), "Suppression de l'utilisateur");
            } catch (Exception e) {
                session.setAttribute("error", "Une erreur est survenue lors de la suppression de votre compte.");
            }
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }

        if ("edit".equals(pathParts[1])) {
            req.getRequestDispatcher(BAO.getRepertory() + "parametres.jsp").forward(req, res);
            return;
        }

        try {
            String idPseudo = pathParts[1];
            User user = uDao.findUserByPseudo(idPseudo);
            if (user == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
            }

            session.setAttribute("userSearch", user);
            session.setAttribute("listeDesPosts", uDao.getListPostsOfUser(user.getUid(), (boolean) session.getAttribute("me_tri")));
            session.setAttribute("nombreFollows", uDao.getListFollowsOfUser(user.getUid()).size());
            session.setAttribute("nombreFollowers", uDao.getListFollowersOfUser(user.getUid()).size());

            req.getRequestDispatcher(BAO.getRepertory() + "user.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Erreur dans la recherche d'un utilisateur.");
            res.sendRedirect(req.getContextPath() + "/accueil");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("me_user") == null) {
            session = req.getSession(true);
            session.setAttribute("error", "Vous n'êtes pas connecté");
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }

        int uid = (int) req.getSession().getAttribute("me_uid");

        UsersDAO uDao = new UsersDAO();
        
        User user = uDao.findUserByUid(uid);
        user.setPseudo(req.getParameter("pseudo"));
        user.setPrenom(req.getParameter("prenom"));
        user.setNomUser(req.getParameter("nomUser"));
        user.setBio(req.getParameter("bio"));
        String pdp = BAO.uploadImage(req.getPart("pdp"), getServletContext().getRealPath(""));
        if(pdp != null) user.setPdp(pdp);
        String dnaiss = req.getParameter("dnaiss");
        user.setDnaiss((dnaiss != null && !dnaiss.isEmpty()) ? LocalDate.parse(dnaiss) : null);
        user.setLoca(req.getParameter("loca"));
        String idPseudo = req.getParameter("idPseudo");
        String email = req.getParameter("email");
        String mdp = req.getParameter("mdp");
        boolean triParDate = "date".equals(req.getParameter("tri"));
        if(mdp != null) user.setMdp(mdp);
        
        if(!idPseudo.equals(user.getIdPseudo()) && uDao.findUserByPseudo(idPseudo) != null){
            session.setAttribute("error", "Identifiant déjà utilisé.");
            res.sendRedirect(req.getContextPath() + "/user/edit");
            return;
        }
        if(!email.equals(user.getHTMLEmail()) && uDao.findUserByEmail(email) != null && email.contains("@")){
            session.setAttribute("error", "Email déjà utilisé.");
            res.sendRedirect(req.getContextPath() + "/user/edit");
            return;
        }
        
        user.setIdPseudo(idPseudo);
        user.setEmail(email);

        try {
            uDao.update(user);
            session.setAttribute("me_tri", triParDate);
            LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "Changement des informations de " + user.getIdPseudo());
            session.setAttribute("me_pseudo", user.getIdPseudo());
            session.setAttribute("me_user", user);
            session.setAttribute("success", "Mise à jour des informations réalisé.");
            res.sendRedirect(req.getContextPath() + "/user/edit");
        } catch (Exception e) {
            session.setAttribute("error", "Problème durant la mise à jour des informations.");
            res.sendRedirect(req.getContextPath() + "/user/edit");
        }
    }

}
