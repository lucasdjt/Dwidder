package controleur;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import modele.dao.MessagesDAO;
import modele.dao.UsersDAO;
import modele.dto.Message;
import modele.dto.User;
import modele.dao.LogsDAO;

@WebServlet("/messages/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class MessageServlet extends HttpServlet {
    private static final String REPERTORY = "/WEB-INF/vue/";
    private static final String UPLOAD_DIR = "img";
    private static final String SEP = File.separator;

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("uid") == null || session.getAttribute("pseudo") == null) {
                res.sendRedirect(req.getContextPath() + "/connexion");
                return;
            }
        int uid = (int) session.getAttribute("uid");
        UsersDAO uDao = new UsersDAO();
        List<User> listUsers = uDao.getListConversationsOfUser(uid);
        List<User> listFollow = uDao.getListFollowsOfUser(uid);
        listFollow.removeAll(listUsers);
        req.setAttribute("follows", listFollow);
        req.setAttribute("listUser", listUsers);

        res.setContentType("text/html; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            if(req.getParameter("query") != null){
                req.setAttribute("listUser", uDao.getListUsersWithEquivalentKey(req.getParameter("query")));
            }
            req.getRequestDispatcher(REPERTORY + "messages.jsp").forward(req, res);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid CID format");
            return;
        }
        String cidStr = pathParts[1];
        try {
            User user = uDao.findUserByPseudo(cidStr);
            if (user == null) {
                res.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;
            }
            req.setAttribute("user", user);
            req.setAttribute("listMess", uDao.getListMessagesOf2Users(uid, user.getUid()));
            req.getRequestDispatcher(REPERTORY + "messages.jsp").forward(req, res);
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int uidEnvoyeur = Integer.parseInt(req.getParameter("uidEnvoyeur"));
        int uidReceveur = Integer.parseInt(req.getParameter("uidReceveur"));
        String corps = req.getParameter("corps");
        Part filePart = req.getPart("imgMess");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String imgMess = null;
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
            imgMess = UPLOAD_DIR + SEP + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath());
            }
        }
        if (imgMess == null && (corps == null || corps.isEmpty())) {
            res.sendRedirect(req.getContextPath() + "/messages?error=1");
            return;
        }
        LocalDateTime dmess = LocalDateTime.now();
        MessagesDAO dao = new MessagesDAO();
        String referer = req.getHeader("Referer");
        if (referer != null && referer.contains("?")) {
            referer = referer.substring(0, referer.indexOf("?"));
        }
        try {
            dao.insert(new Message(0, uidEnvoyeur, uidReceveur, corps, imgMess, dmess));
            LogsDAO.insert(req.getSession().getAttribute("pseudo").toString(), "Envoi d'un message " + uidEnvoyeur + " --> " + uidReceveur);
            res.sendRedirect(referer);
        } catch (Exception e) {
            res.sendRedirect(referer + "?success=0");
        }
    }
}
