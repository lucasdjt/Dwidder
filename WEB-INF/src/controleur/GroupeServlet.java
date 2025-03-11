package controleur;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.dao.GroupesDAO;
import modele.dao.PostsDAO;
import modele.dao.UsersDAO;
import modele.dto.Groupe;

@WebServlet("/groupes/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class GroupeServlet extends HttpServlet {
    private static final String REPERTORY = "/WEB-INF/vue/";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");

        int uidSet = 1;
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            req.getRequestDispatcher(REPERTORY + "creerGroupe.jsp").forward(req, res);
            return;
        }
        
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid GID format");
            return;
        }
        try {
            String gidStr = pathParts[1];
            int gid = Integer.parseInt(gidStr);
            PostsDAO dao = new PostsDAO();
            GroupesDAO gDao = new GroupesDAO();
            Groupe groupe = gDao.findByGid(gid);
            UsersDAO uDao = new UsersDAO();
            if (groupe == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "Groupe not found");
            return;
            }
            req.setAttribute("groupe", groupe);
            req.setAttribute("posts", dao.selectFromGroup(gid, false));
            req.setAttribute("listGrp", uDao.getUserGroups(uidSet));
            req.getRequestDispatcher(REPERTORY + "groupes.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
        }
    }
}
