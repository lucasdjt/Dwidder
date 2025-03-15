package controleur;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modele.dao.UsersDAO;
import modele.dto.User;

@WebServlet("/connexion")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class ConnexionServlet extends HttpServlet {
    private static final String REPERTORY = "/WEB-INF/vue/";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher(REPERTORY + "connexion.jsp").forward(req, res);
    }

        protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String emailOrMDP = req.getParameter("identifier");
        User user = null;
        UsersDAO dao = new UsersDAO();
        if (emailOrMDP.contains("@")) {
            user = dao.findByEmail(emailOrMDP);
        } else {
            user = dao.findByIdPseudo(emailOrMDP);
        }
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        if(user != null && user.getMdp().equals(req.getParameter("password"))) {
            req.getSession().setAttribute("uid", user.getUid());
            req.getSession().setAttribute("pseudo", user.getIdPseudo());
            res.sendRedirect(req.getContextPath() + "/accueil");
        } else {
            res.sendRedirect(referer + "?error=1");
        }
    }
}
