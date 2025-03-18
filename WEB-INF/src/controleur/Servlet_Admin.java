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

@WebServlet("/admin")
public class Servlet_Admin extends HttpServlet {
    final String REPERTORY = "/WEB-INF/vue/";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("uid") == null || session.getAttribute("pseudo") == null || ((boolean)session.getAttribute("admin")) == false) {
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        try {
            UsersDAO dao = new UsersDAO();
            req.setAttribute("users", dao.listUsers());
            req.setAttribute("logs", LogsDAO.selectAll());
            LogsDAO.insert(session.getAttribute("pseudo").toString(), "est rentré dans la base ADMIN");
            req.getRequestDispatcher(REPERTORY + "admin.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        req.setCharacterEncoding("UTF-8");
        String idPseudo = req.getParameter("uid");
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        try {
            UsersDAO dao = new UsersDAO();
            dao.delete(idPseudo);
            LogsDAO.insert(session.getAttribute("pseudo").toString(), idPseudo + " a été supprimé depuis la base ADMIN");
            res.sendRedirect(req.getContextPath() + "/admin");
        } catch (Exception e) {
            res.sendRedirect(referer + "?success=0");
        }
    }
}
