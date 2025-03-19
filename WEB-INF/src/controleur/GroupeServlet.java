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
import modele.dao.LogsDAO;
import modele.dao.PostsDAO;
import modele.dao.UsersDAO;
import modele.dto.Groupe;
import modele.dto.User;
import modele.dao.MembresDAO;
import modele.dto.Membre;
import utils.BAO;

@WebServlet("/groupe/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class GroupeServlet extends HttpServlet {

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

        UsersDAO uDao = new UsersDAO();
        PostsDAO dao = new PostsDAO();
        GroupesDAO gDao = new GroupesDAO();
        MembresDAO mDao = new MembresDAO();

        session.setAttribute("listeDesGroupes", uDao.getUserGroups((int) session.getAttribute("me_uid")));
        
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            req.getRequestDispatcher(BAO.getRepertory() + "groupes.jsp").forward(req, res);
            return;
        }
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            session.setAttribute("error", "Vous avez entré un mauvais lien.");
            res.sendRedirect(req.getContextPath() + "/accueil");
            return;
        }

        if ("delete".equals(pathParts[1])) {
            try {
                int gid = Integer.parseInt(req.getParameter("gid"));
                gDao.delete(gDao.findGroupByGid(gid));
                LogsDAO.insert(req.getSession().getAttribute("me_pseudo").toString(), "Suppression du groupe " + gid);
                session.setAttribute("success", "Vous avez supprimé un groupe");
                res.sendRedirect(req.getContextPath() + "/accueil");
            } catch (Exception e) {
                session.setAttribute("error", "Échec de la suppression du groupe.");
                res.sendRedirect(req.getContextPath() + "/accueil");
            }
            return;
        }

        if ("add".equals(pathParts[1])) {
            req.getRequestDispatcher(BAO.getRepertory() + "creerGroupe.jsp").forward(req, res);
            return;
        }

        if ("edit".equals(pathParts[1])) {
            try {
                String gidStr = pathParts[2];
                int gid = Integer.parseInt(gidStr);
                Groupe groupe = gDao.findGroupByGid(gid);
                if (groupe == null) {
                    session.setAttribute("error", "Le groupe n'existe pas");
                    res.sendRedirect(req.getContextPath() + "/accueil");
                    return;
                }
                if(groupe.getUid() != (int) session.getAttribute("me_uid")){
                    session.setAttribute("error", "Vous n'êtes pas admin de ce groupe.");
                    res.sendRedirect(req.getContextPath() + "/groupe");
                    return;
                }

                session.setAttribute("groupeSelect", groupe);
                session.setAttribute("membreGrp", gDao.getListUsersOfAGroup(gid));
                req.getRequestDispatcher(BAO.getRepertory() + "chgGroupe.jsp").forward(req, res);
            } catch (NumberFormatException e) {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
            }
        }

        if ("member".equals(pathParts[1])) {
            int gid = Integer.parseInt(req.getParameter("gid"));
            int uid = Integer.parseInt(req.getParameter("uid"));

            String referer = req.getHeader("Referer");
            if (referer != null && referer.contains("?")) referer = referer.substring(0, referer.indexOf("?"));

            try {
                mDao.delete(new Membre(uid, gid, null));
                
                LogsDAO.insert(req.getSession().getAttribute("me_pseudo").toString(), "Suppression du membre " + uid + " du groupe " + gid);
                session.setAttribute("success", "Le membre " + uid + " a été supprimé du groupe");
                
                res.sendRedirect(referer);
            } catch (NumberFormatException e) {
                session.setAttribute("error", "Erreur dans la suppression du membre");
                res.sendRedirect(req.getContextPath() + "/accueil");
            }
            return;
        }

        try {
            int gid = Integer.parseInt(pathParts[1]);
            Groupe groupe = gDao.findGroupByGid(gid);
            if (groupe == null) {
                session.setAttribute("error", "Ce groupe est inexistant.");
                res.sendRedirect(req.getContextPath() + "/accueil");
                return;
            }
            if(!gDao.getListUsersOfAGroup(gid).contains((User)session.getAttribute("me_user"))){
                session.setAttribute("error", "Vous n'êtes pas membre de ce groupe.");
                req.getRequestDispatcher(BAO.getRepertory() + "groupes.jsp").forward(req, res);
                return;
            }

            session.setAttribute("groupe", groupe);
            session.setAttribute("listeDesPosts", dao.getListPostsOfGroup(gid, (boolean) req.getSession().getAttribute("me_tri")));
            
            req.getRequestDispatcher(BAO.getRepertory() + "groupes.jsp").forward(req, res);

        } catch (NumberFormatException e) {
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

        GroupesDAO gDao = new GroupesDAO();
        MembresDAO mDao = new MembresDAO();
        UsersDAO uDao = new UsersDAO();

        String[] pathParts = req.getPathInfo().split("/");
        if (pathParts.length < 2) {
            session.setAttribute("error", "Vous avez entré un mauvais lien.");
            res.sendRedirect(req.getContextPath() + "/accueil");
            return;
        }
        
        if ("add".equals(pathParts[1])) {
            int uid = Integer.parseInt(req.getParameter("uid"));
            String pdpGrp = BAO.uploadImage(req.getPart("pdpGrp"), getServletContext().getRealPath(""));
            if(pdpGrp == null) pdpGrp = "img/pdp.png";
            String nomGrp = req.getParameter("nomGrp");
            String description = req.getParameter("description");
            LocalDateTime dcreat = LocalDateTime.now();

            try {
                if(gDao.findGroupByName(nomGrp) != null){
                    session.setAttribute("error", "Le nom du groupe est déjà utilisé.");
                    res.sendRedirect(req.getContextPath() + "/groupe/add");
                    return;
                }

                gDao.insert(new Groupe(0, uid, pdpGrp, nomGrp, description, dcreat));
                mDao.insert(new Membre(uid, gDao.findGroupByName(nomGrp).getGid(), dcreat));

                LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "Création d'un nouveau groupe : " + nomGrp);
                session.setAttribute("success", "Vous avez créer un nouveau groupe : " + nomGrp);
                
                res.sendRedirect(req.getContextPath() + "/groupe");
            } catch (Exception e) {
                session.setAttribute("error", "Erreur lors de la création du groupe.");
                res.sendRedirect(req.getContextPath() + "/groupe");
            }
            return;
        }

        if("edit".equals(pathParts[1])){
            int gid = Integer.parseInt(req.getParameter("gid"));
            Groupe groupe = gDao.findGroupByGid(gid);
            
            String pdpGrp = BAO.uploadImage(req.getPart("pdpGrp"), getServletContext().getRealPath(""));
            if(pdpGrp != null) groupe.setPdpGrp(pdpGrp);
            groupe.setNomGrp(req.getParameter("nomGrp"));
            groupe.setDescription(req.getParameter("description"));
            String admin = req.getParameter("newAdmin");
            if(admin != null && !admin.isEmpty()){
                groupe.setUid(Integer.parseInt(admin));
                LogsDAO.insert(session.getAttribute("me_pseudo").toString(), admin + " est le nouvel admin du groupe " + gid);
            }
            try {
                gDao.update(groupe);
                LogsDAO.insert(session.getAttribute("me_pseudo").toString(), "Changement des infos du groupe " + gid);
                session.setAttribute("success", "Vous avez changé les infos du groupe : " + groupe.getNomGrp());
                res.sendRedirect(req.getContextPath() + "/groupe");
            } catch (Exception e) {
                session.setAttribute("error", "Erreur lors de la modification du groupe.");
                res.sendRedirect(req.getContextPath() + "/groupe");
            }
            return;
        }

        if("member".equals(pathParts[1])){
            int gid = Integer.parseInt(req.getParameter("gid"));
            String idPseudo = req.getParameter("idPseudo");
            User user = uDao.findUserByPseudo(idPseudo);

            if(user == null) {
                session.setAttribute("error", "Erreur pour l'ajout d'un utilisateur.");
                res.sendRedirect(referer);
                return;
            }

            try {
                if(gDao.getListUsersOfAGroup(gid).contains(user)){
                    session.setAttribute("error", "Cette personne est déjà dans le groupe.");
                    res.sendRedirect(referer);
                    return;
                }
                mDao.insert(new Membre(user.getUid(), gid, LocalDateTime.now()));
                LogsDAO.insert(req.getSession().getAttribute("me_pseudo").toString(), "Ajout du membre " + user.getUid() + " du groupe " + gid);
                session.setAttribute("success", user.getIdPseudo() + " a été ajouté au groupe");
                res.sendRedirect(referer);
            } catch (Exception e) {
                session.setAttribute("error", "Erreur lors de l'ajout d'un membre.");
                res.sendRedirect(req.getContextPath() + "/groupe");
            }
            return;
        }

        session.setAttribute("error", "Post incorrect.");
        res.sendRedirect(req.getContextPath() + "/groupe");
    }
}
