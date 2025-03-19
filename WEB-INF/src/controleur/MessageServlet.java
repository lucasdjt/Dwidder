package controleur;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.MessagesDAO;
import modele.dao.UsersDAO;
import modele.dto.Message;
import modele.dto.User;
import utils.BAO;
import modele.dao.LogsDAO;

@WebServlet("/message/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class MessageServlet extends HttpServlet {

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
        
        UsersDAO uDao = new UsersDAO();
        
        int uid = (int) session.getAttribute("me_uid");;
        List<User> listUsers = uDao.getListConversationsOfUser(uid);
        session.setAttribute("listDesUtilisateurs", listUsers);

        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            if(req.getParameter("query") != null) session.setAttribute("listDesUtilisateurs", uDao.getListUsersWithEquivalentKey(req.getParameter("query")));
            req.getRequestDispatcher(BAO.getRepertory() + "messages.jsp").forward(req, res);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            session.setAttribute("error", "Le lien est invalide");
            res.sendRedirect(req.getContextPath() + "/accueil");
        }

        System.out.println("PathInfo: " + pathInfo);
        System.out.println("Utilisateur en session avant recherche: " + session.getAttribute("userMess"));
        System.out.println("Requête utilisateur pour pseudo: " + pathParts[1]);
        try {
            User user = uDao.findUserByPseudo(pathParts[1]);
            if (user == null) {
                session.setAttribute("error", "L'utilisateur n'existe pas");
                res.sendRedirect(req.getContextPath() + "/message");
            }

            session.setAttribute("userMess", user);
            session.setAttribute("listeDesMessages", uDao.getListMessagesOf2Users(uid, user.getUid()));
            req.getRequestDispatcher(BAO.getRepertory() + "messages.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Le lien est invalide");
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
        
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) referer = referer.substring(0, referer.indexOf("?"));

        MessagesDAO dao = new MessagesDAO();
        
        int uidEnvoyeur = (int) session.getAttribute("me_uid");
        int uidReceveur = Integer.parseInt(req.getParameter("uidReceveur"));
        String corps = req.getParameter("corps");
        String imgMess = BAO.uploadImage(req.getPart("imgMess"), getServletContext().getRealPath(""));
        if (imgMess == null && (corps == null || corps.isEmpty())) {
            session.setAttribute("error", "Mettez au moins un contenu");
            res.sendRedirect(referer);
            return;
        }
        LocalDateTime dmess = LocalDateTime.now();

        try {
            dao.insert(new Message(0, uidEnvoyeur, uidReceveur, corps, imgMess, dmess));
            LogsDAO.insert(req.getSession().getAttribute("me_pseudo").toString(), "Envoi d'un message " + uidEnvoyeur + " --> " + uidReceveur);
            
            session.setAttribute("success", "Vous avez envoyé un message");
            res.sendRedirect(referer);
        } catch (Exception e) {
            session.setAttribute("error", "Le post n'a pas pu être envoyé");
            res.sendRedirect(referer);
        }
    }
}
