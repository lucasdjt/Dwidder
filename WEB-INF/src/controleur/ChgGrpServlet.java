package controleur;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import modele.dao.GroupesDAO;
import modele.dto.Groupe;

@WebServlet("/chgGroupe/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class ChgGrpServlet extends HttpServlet {
    final String REPERTORY = "/WEB-INF/vue/";
    private static final String UPLOAD_DIR = "img";
    private static final String SEP = File.separator;

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("uid") == null || session.getAttribute("pseudo") == null) {
            res.sendRedirect(req.getContextPath() + "/connexion");
            return;
        }
        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
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
            GroupesDAO gDao = new GroupesDAO();
            Groupe groupe = gDao.findGroupByGid(gid);
            if (groupe == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "Groupe not found");
            return;
            }
            req.setAttribute("groupe", groupe);
            req.setAttribute("listeMembres", gDao.getListUsersOfAGroup(gid));
            req.getRequestDispatcher(REPERTORY + "chgGroupe.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Part filePart = req.getPart("pdpGrp");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String pdpGrp = null;
        if (fileName != null && !fileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("") + SEP + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            
            File file = new File(uploadPath + SEP + fileName);
            String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
            String extension = fileName.substring(fileName.lastIndexOf('.'));
            int counter = 1;
            while (file.exists()) {
            fileName = baseName + "_" + counter + extension;
            file = new File(uploadPath + SEP + fileName);
            counter++;
            }
            pdpGrp = UPLOAD_DIR + SEP + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath());
            }
        }
        if(pdpGrp == null){
            pdpGrp = "img" + SEP + "pdp.png";
        }
        String nomGrp = req.getParameter("nomGrp");
        String description = req.getParameter("description");
        String admin = req.getParameter("newAdmin");
        GroupesDAO gDao = new GroupesDAO();
        int gid = Integer.parseInt(req.getParameter("gid"));
        Groupe groupe = gDao.findGroupByGid(gid);
        groupe.setNomGrp(nomGrp);
        groupe.setDescription(description);
        groupe.setPdpGrp(pdpGrp);
        if(admin != null && !admin.isEmpty()){
            groupe.setUid(Integer.parseInt(admin));
        }
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        try {
            gDao.update(groupe);
            res.sendRedirect(req.getContextPath() + "/groupes/" + gid);
        } catch (Exception e) {
            res.sendRedirect(referer + "?success=0");
        }
    }
}
