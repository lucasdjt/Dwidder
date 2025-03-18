package controleur;

import java.io.IOException;
import modele.dao.UsersDAO;
import modele.dao.LogsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delAccount")
public class Servlet_DeleteAccount extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String pseudo = req.getSession().getAttribute("pseudo").toString();
        UsersDAO usersDAO = new UsersDAO();
        try {
            usersDAO.delete(pseudo);
            LogsDAO.insert(req.getSession().getAttribute("pseudo").toString(), "Suppression de l'utilisateur");
            res.sendRedirect(req.getContextPath() + "/connexion");
        } catch (Exception e) {
            res.getWriter().write("Account deletion failed");
        }
    }
}