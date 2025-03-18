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

@WebServlet("/api/*")
public class APIServlet extends HttpServlet {
    /*
     * API :
     * - /api/post : liste des posts publics
     * > curl -i -X GET http://localhost:8080/ReseauSocial/api/post
     * - /api/post/{uid} : un post en particulier à moi
     * > curl -i -x GET -u draggas:draggas GET http://localhost:8080/ReseauSocial/api/post/1
     * - /api/user : liste de nos posts
     * > curl -i -X GET -u draggas:draggas http://localhost:8080/ReseauSocial/api/user
     * - /api/group/{gid} : liste des posts d'un groupe données
     * > curl -i -X GET -u draggas:draggas http://localhost:8080/ReseauSocial/api/group/1
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        res.setContentType("application/json; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        
        

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid API request");
            return;
        }
        
        res.setContentType("application/json; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");

        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid API format");
            return;
        }
        
        String resource = pathParts[1];
        
        try (PrintWriter out = res.getWriter()) {
            switch (resource) {
                case "post":
                    if (pathParts.length == 3) {
                        String authorization = req.getHeader("Authorization");
                        if (authorization == null || !authorization.startsWith("Basic")){
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid");
                            return;
                        }
                        String token = authorization.substring("Basic".length()).trim();
                        byte[] base64 = Base64.getDecoder().decode(token);
                        String[] values = (new String(base64)).split(":");

                        if (values.length != 2 || !isValidUser(values[0], values[1])) {
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
                            return;
                        }

                        int postId = Integer.parseInt(pathParts[2]);
                        PostsDAO postsDAO = new PostsDAO();
                        PostDetails p = postsDAO.findPostDetails(postId);
                        if (p == null) {
                            res.sendError(HttpServletResponse.SC_NOT_FOUND, "Post does not exist");
                            return;
                        }
                        if(!p.getIdPseudo().equals(values[0])){
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not your post");
                            return;
                        }
                        out.print(BAO.toJson(p));
                        LogsDAO.insert("API", "API GET post particulier");
                    } else {
                        PostsDAO postsDAO = new PostsDAO();
                        List<PostDetails> posts = postsDAO.getListPostsInPublic(true);
                        String json = "[";
                        for (PostDetails p : posts) {
                            json += BAO.toJson(p) + ",\n";
                        }
                        if (json.endsWith(",\n")) {
                            json = json.substring(0, json.length() - 2);
                        }
                        out.print(json + "]");
                        LogsDAO.insert("API", "API GET post public");
                    }
                    break;
                case "user":
                    if (pathParts.length == 2) {
                        String authorization = req.getHeader("Authorization");
                        if (authorization == null || !authorization.startsWith("Basic")){
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid");
                            return;
                        }
                        String token = authorization.substring("Basic".length()).trim();
                        byte[] base64 = Base64.getDecoder().decode(token);
                        String[] values = (new String(base64)).split(":");

                        if (values.length != 2 || !isValidUser(values[0], values[1])) {
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
                            return;
                        }

                        UsersDAO usersDAO = new UsersDAO();
                        User u = usersDAO.findUserByPseudo(values[0]);
                        if (u == null) {
                            res.sendError(HttpServletResponse.SC_NOT_FOUND, "User does not exist");
                            return;
                        }
                        List<PostDetails> posts = usersDAO.getListPostsOfUser(u.getUid(), true);
                        String json = "[";
                        for (PostDetails p : posts) {
                            json += BAO.toJson(p) + ",\n";
                        }
                        if (json.endsWith(",\n")) {
                            json = json.substring(0, json.length() - 2);
                        }
                        out.print(json + "]");
                        LogsDAO.insert("API", "API GET post user");
                    } else {
                        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "User id required");
                        return;
                    }
                    break;
                case "group":
                    if (pathParts.length == 3) {
                        String authorization = req.getHeader("Authorization");
                        if (authorization == null || !authorization.startsWith("Basic")){
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid");
                            return;
                        }
                        String token = authorization.substring("Basic".length()).trim();
                        byte[] base64 = Base64.getDecoder().decode(token);
                        String[] values = (new String(base64)).split(":");

                        if (values.length != 2 || !isValidUser(values[0], values[1])) {
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
                            return;
                        }

                        int gid = Integer.parseInt(pathParts[2]);
                        GroupesDAO gDao = new GroupesDAO();
                        PostsDAO dao = new PostsDAO();
                        UsersDAO uDao = new UsersDAO();
                        if(gDao.findGroupByGid(gid) == null){
                            res.sendError(HttpServletResponse.SC_NOT_FOUND, "Group does not exist");
                            return;
                        }
                        if(!gDao.getListUsersOfAGroup(gid).contains(uDao.findUserByPseudo(values[0]))){
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not part of the group");
                            return;
                        }
                        List<PostDetails> posts = dao.getListPostsOfGroup(gid, true);
                        String json = "[";
                        for (PostDetails p : posts) {
                            json += BAO.toJson(p) + ",\n";
                        }
                        if (json.endsWith(",\n")) {
                            json = json.substring(0, json.length() - 2);
                        }
                        out.print(json + "]");
                        LogsDAO.insert("API", "API GET post group");
                    } else {
                        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Group name required");
                        return;
                    }
                    break;
                default:
                    res.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
                    return;
            }
            out.flush();
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (IllegalAccessException e) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error occurred");
        }
    }

    private boolean isValidUser(String idPseudo, String password) {
        UsersDAO usersDAO = new UsersDAO();
        User user = usersDAO.findUserByPseudo(idPseudo);
        if (user == null) {
            return false;
        }
        return user.getMdp().equals(password);
    }
}