package controleur;

import java.io.IOException;
import modele.dao.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delAccount")
public class DeleteAccountServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String pseudo = req.getSession().getAttribute("pseudo").toString();
        UsersDAO usersDAO = new UsersDAO();
        try {
            usersDAO.delete(pseudo);
            res.sendRedirect(req.getContextPath() + "/connexion");
        } catch (Exception e) {
            res.getWriter().write("Account deletion failed");
        }
    }
}