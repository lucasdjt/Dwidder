package controleur;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.IOException;

import modele.dao.LogsDAO;
import modele.dao.UsersDAO;
import modele.dto.User;
import utils.BAO;

@WebServlet("/inscription")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class InscriptionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");

        if (req.getSession(false) != null) req.getSession(false).invalidate();

        req.getRequestDispatcher(BAO.getRepertory() + "inscription.jsp").forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        UsersDAO uDao = new UsersDAO();

        String idPseudo = req.getParameter("idPseudo");
        String pseudo = req.getParameter("pseudo");
        String prenom = req.getParameter("prenom");
        String nomUser = req.getParameter("nomUser");
        String email = req.getParameter("email");
        String mdp = req.getParameter("mdp");
        String bio = req.getParameter("bio");
        String pdp = BAO.uploadImage(req.getPart("image"), getServletContext().getRealPath(""));
        pdp = (pdp != null) ? pdp : "img/pdp.png";
        LocalDateTime dinsc = LocalDateTime.now();
        LocalDate dnaiss = LocalDate.parse(req.getParameter("dnaiss"));
        String loca = req.getParameter("loca");

        String error = "";
        if (uDao.findUserByPseudo(idPseudo) != null || idPseudo.equals("delete")) error += "Identifiant déjà utilisé. ";
        if (uDao.findUserByEmail(email) != null) error += "Mail déjà utilisé. ";
        if (!mdp.equals(req.getParameter("confirmPassword"))) error += "Validation de mot de passe incorect. ";
        if (!error.isEmpty()) {
            req.getSession().setAttribute("error", error.trim());
            req.getRequestDispatcher(BAO.getRepertory() + "inscription.jsp").forward(req, res);
            return;
        }

        try {
            uDao.insert(new User(0, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, false));

            req.getSession().setAttribute("success", "Compte créé avec succès !");

            LogsDAO.insert(pseudo, "Création de son compte");
            res.sendRedirect(req.getContextPath() + "/connexion");
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Une erreur est survenue lors de la création du compte.");
            req.getRequestDispatcher(BAO.getRepertory() + "inscription.jsp").forward(req, res);
        }
    }
}
