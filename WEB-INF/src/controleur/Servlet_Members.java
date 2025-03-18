package controleur;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.MembresDAO;
import modele.dao.UsersDAO;
import modele.dto.Membre;
import modele.dto.User;
import modele.dao.LogsDAO;

@WebServlet("/member")
public class Servlet_Members extends HttpServlet {
    final String REPERTORY = "/WEB-INF/vue/";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("uid") == null || session.getAttribute("pseudo") == null) {
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        int gid = Integer.parseInt(req.getParameter("gid"));
        int uid = Integer.parseInt(req.getParameter("uid"));
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        try {
            MembresDAO mDao = new MembresDAO();
            mDao.delete(new Membre(uid, gid, null));
            LogsDAO.insert(req.getSession().getAttribute("pseudo").toString(), "Suppression du membre " + uid + " du groupe " + gid);
            res.sendRedirect(referer);
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int gid = Integer.parseInt(req.getParameter("gid"));
        String idPseudo = req.getParameter("idPseudo");
        UsersDAO uDao = new UsersDAO();
        User user = uDao.findUserByPseudo(idPseudo);
        if(user == null) {
            res.sendRedirect(req.getContextPath() + "/chgGroupe/" + gid + "?success=0");
            return;
        }
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        MembresDAO mDao = new MembresDAO();
        try {
            mDao.insert(new Membre(user.getUid(), gid, LocalDateTime.now()));
            LogsDAO.insert(req.getSession().getAttribute("pseudo").toString(), "Ajout du membre " + user.getUid() + " du groupe " + gid);
            res.sendRedirect(req.getContextPath() + "/chgGroupe/" + gid);
        } catch (Exception e) {
            res.sendRedirect(referer + "?success=0");
        }
    }
}
