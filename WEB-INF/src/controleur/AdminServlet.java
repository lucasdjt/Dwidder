package controleur;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.LogsDAO;
import modele.dao.UsersDAO;
import utils.BAO;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || ! (boolean) session.getAttribute("me_admin")) {
            session = req.getSession(true);
            session.setAttribute("error", "Vous n'êtes pas administrateur du réseau social");
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }

        UsersDAO dao = new UsersDAO();

        LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "est rentré dans la base ADMIN");
        
        session.setAttribute("listeDesUsers", dao.listUsers());
        session.setAttribute("listeDesLogs", LogsDAO.selectAll());

        req.getRequestDispatcher(BAO.getRepertory() + "gestionAdmin.jsp").forward(req, res);
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
        
        String idPseudo = req.getParameter("uid");

        UsersDAO uDao = new UsersDAO();
        
        try {
            uDao.delete(idPseudo);
            LogsDAO.insert(session.getAttribute("me_pseudo").toString(), idPseudo + " a été supprimé depuis la base ADMIN");
            session.setAttribute("success", idPseudo + " a été supprimé.");
        } catch (Exception e) {
            session.setAttribute("error", "Une erreur est survenue lors de la suppression d'un utilisateur.");
        }
        res.sendRedirect(req.getContextPath() + "/admin");
    }
}
