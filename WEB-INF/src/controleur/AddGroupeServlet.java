package controleur;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.GroupesDAO;
import modele.dao.MembresDAO;
import modele.dto.Groupe;
import modele.dto.Membre;

@WebServlet("/addGroupe")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class AddGroupeServlet extends HttpServlet {
    final String REPERTORY = "/WEB-INF/vue/";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("uid") == null || session.getAttribute("pseudo") == null) {
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        req.getRequestDispatcher(REPERTORY + "creerGroupe.jsp").forward(req, res);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int uid = Integer.parseInt(req.getParameter("uid"));
        Integer pid = null;
        String nomGrp = req.getParameter("nomGrp");
        String description = req.getParameter("description");
        LocalDateTime dcreat = LocalDateTime.now();
        GroupesDAO gDao = new GroupesDAO();
        String referer = req.getHeader("Referer");
        MembresDAO mDao = new MembresDAO();
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        try {
            if(gDao.findByName(nomGrp) != null){
                res.sendRedirect(referer + "?existant=0");
                return;
            }
            gDao.insert(new Groupe(0, uid, pid, nomGrp, description, dcreat));
            mDao.insert(new Membre(uid, gDao.findByName(nomGrp).getGid(), dcreat));
            
            res.sendRedirect(req.getContextPath() + "/accueil?groupe=1");
        } catch (Exception e) {
            res.sendRedirect(referer + "?success=0");
        }
    }
}
