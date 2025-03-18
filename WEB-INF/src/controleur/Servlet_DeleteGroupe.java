package controleur;

import java.io.IOException;

import modele.dao.GroupesDAO;
import modele.dao.LogsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/delGroupe")
public class Servlet_DeleteGroupe extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int gid = Integer.parseInt(req.getParameter("gid"));
        GroupesDAO grpDAO = new GroupesDAO();
        try {
            grpDAO.delete(grpDAO.findGroupByGid(gid));
            LogsDAO.insert(req.getSession().getAttribute("me_pseudo").toString(), "Suppression du groupe " + gid);
            res.sendRedirect(req.getContextPath() + "/accueil");
        } catch (Exception e) {
            res.getWriter().write("Groupe deletion failed");
        }
    }
}