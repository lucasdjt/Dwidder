package controleur;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modele.dao.GroupesDAO;
import modele.dao.PostsDAO;
import modele.dao.UsersDAO;
import modele.dto.PostDetails;
import modele.dto.User;
import modele.dao.LogsDAO;
import utils.BAO;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;
/*
 * /api/post/{uid} : un post en particulier à moi
 * cmd > curl -i -X GET http://localhost:8080/Dwidder/api/post/1 -u draggas:draggas
 * 
 * /api/post : liste des posts publics
 * cmd > curl -i -X GET http://localhost:8080/Dwidder/api/post
 * 
 * /api/user : liste de nos posts
 * cmd > curl -i -X GET http://localhost:8080/Dwidder/api/user -u draggas:draggas
 * 
 * /api/group/{gid} : liste des posts d'un groupe données
 * cmd > curl -i -X GET http://localhost:8080/Dwidder/api/group/1 -u draggas:draggas
 */
@WebServlet("/api/*")
public class APIServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        
        PostsDAO pDao = new PostsDAO();
        UsersDAO uDao = new UsersDAO();
        GroupesDAO gDao = new GroupesDAO();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.split("/").length < 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid API format/request");
            return;
        }
        String[] pathParts = pathInfo.split("/");
        
        try (PrintWriter out = res.getWriter()) {
            switch (pathParts[1]) {
                case "post":
                    if (pathParts.length == 3) {
                        if (!isAuthenticated(req, res)) {
                            return;
                        }
                        String[] values = getValues(req);
                        int postId = Integer.parseInt(pathParts[2]);
                        PostDetails p = pDao.findPostDetails(postId);
                        if (p == null) {
                            res.sendError(HttpServletResponse.SC_NOT_FOUND, "Post does not exist");
                            return;
                        }
                        if(!p.getIdPseudo().equals(values[0])){
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not your post");
                            return;
                        }
                        LogsDAO.insert("API", "API GET post particulier");
                        out.print(BAO.toJson(p));
                    } else {
                        out.print(listToString(pDao.getListPostsInPublic(true)));
                        LogsDAO.insert("API", "API GET post public");
                    }
                    break;
                case "user":
                    if (!isAuthenticated(req, res)) {
                        return;
                    }
                    String[] values = getValues(req);
                    
                    User user = uDao.findUserByPseudo(values[0]);
                    out.print(listToString(uDao.getListPostsOfUser(user.getUid(), true)));
                    LogsDAO.insert("API", "API GET post user");
                    break;
                case "group":
                    if (pathParts.length != 3) {
                        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Group name required");
                        return;
                    }
                    if (!isAuthenticated(req, res)) {
                        return;
                    }

                    values = getValues(req);

                    int gid = Integer.parseInt(pathParts[2]);
                    if(gDao.findGroupByGid(gid) == null){
                        res.sendError(HttpServletResponse.SC_NOT_FOUND, "Group does not exist");
                        return;
                    }
                    if(!gDao.getListUsersOfAGroup(gid).contains(uDao.findUserByPseudo(values[0]))){
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not part of the group");
                        return;
                    }
                    List<PostDetails> posts = pDao.getListPostsOfGroup(gid, true);
                        out.print(listToString(posts));
                        LogsDAO.insert("API", "API GET post group");
                    break;
                default:
                    res.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
                    return;
            }
            out.flush();
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid format");
        } catch (IllegalAccessException e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error occurred");
        }
    }

    private boolean isAuthenticated(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String authorization = req.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Basic")) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing Authorization header");
            return false;
        }
        String token = authorization.substring("Basic".length()).trim();
        byte[] base64 = Base64.getDecoder().decode(token);
        String[] values = (new String(base64)).split(":");
    
        if (values.length != 2 || !isValidUser(values[0], values[1])) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
            return false;
        }
        return true;
    }

    private boolean isValidUser(String idPseudo, String password) {
        UsersDAO uDao = new UsersDAO();
        User user = uDao.findUserByPseudo(idPseudo);
        if (user == null) {
            return false;
        }
        return user.getMdp().equals(password);
    }

    private String[] getValues(HttpServletRequest req) throws IOException {
        String authorization = req.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Basic")) {
            throw new IOException("Invalid or missing Authorization header");
        }
        String token = authorization.substring("Basic".length()).trim();
        byte[] base64 = Base64.getDecoder().decode(token);
        return (new String(base64)).split(":");
    }

    private String listToString(List<PostDetails> posts) throws IllegalAccessException{
        String json = "[";
        for (PostDetails p : posts) json += BAO.toJson(p) + ",\n";
        if (json.endsWith(",\n")) json = json.substring(0, json.length() - 2);
        return json + "]";
    }
}