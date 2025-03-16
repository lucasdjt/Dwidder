package controleur;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;/* 
import modele.dao.PostsDAO;
import modele.dao.UsersDAO;
import modele.dto.Post;
import modele.dto.PostDetails;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
*/
@WebServlet("/api/*")
public class APIServlet extends HttpServlet {
    /*
     * API :
     * - /api/post : liste des posts publics
     * - /api/post/{id} : liste des posts reponses au post avec id = {id}
     * - /api/user/{pseudo} : liste des posts publics avec idpseudo = {idpseudo}
     * - /api/group/{name} : liste des posts d'un groupe données
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
            /*
        res.setContentType("application/json; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        
        ObjectMapper objectMapper = new ObjectMapper();

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
        String responseJson = "";
        
        try (PrintWriter out = res.getWriter()) {
            switch (resource) {
                case "post":
                    if (pathParts.length == 3) {
                        int postId = Integer.parseInt(pathParts[2]);
                        PostsDAO postsDAO = new PostsDAO();
                        PostDetails p = postsDAO.findByPid(postId);
                        String jsonstring = objectMapper.writeValueAsString(p);
                        out.print(jsonstring);
                    } else {
                        PostsDAO postsDAO = new PostsDAO();
                        List<PostDetails> posts = postsDAO.selectAllPublic(true);
                        StringBuilder jsonBuilder = new StringBuilder("[");
                        for (int i = 0; i < posts.size(); i++) {
                            jsonBuilder.append(objectMapper.writeValueAsString(posts.get(i)));
                            if (i < posts.size() - 1) {
                                jsonBuilder.append(",");
                            }
                        }
                        jsonBuilder.append("]");
                        String jsonstring = jsonBuilder.toString();
                        out.print(jsonstring);
                    }
                    break;
                case "user":
                    if (pathParts.length == 3) {
                        int userID = Integer.parseInt(pathParts[2]);
                        UsersDAO usersDAO = new UsersDAO();
                        List<PostDetails> posts = usersDAO.getUsersPosts(true);
                        StringBuilder jsonBuilder = new StringBuilder("[");
                        for (int i = 0; i < posts.size(); i++) {
                            jsonBuilder.append(objectMapper.writeValueAsString(posts.get(i)));
                            if (i < posts.size() - 1) {
                                jsonBuilder.append(",");
                            }
                        }
                        jsonBuilder.append("]");
                        String jsonstring = jsonBuilder.toString();
                        out.print(jsonstring);
                    } else {
                        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "User id required");
                        return;
                    }
                    break;
                case "group":
                    if (pathParts.length == 3) {
                        String groupName = pathParts[2];
                        // Intégration du DAO pour récupérer le groupe avec nom = groupName (SI USER EST DANS LE GROUPE !)
                        responseJson = "{\"message\": \"Détails du groupe " + groupName + "\"}";
                    } else {
                        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Group name required");
                        return;
                    }
                    break;
                default:
                    res.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
                    return;
            }
            out.print(responseJson);
            out.flush();
        } catch (NumberFormatException e) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        }
             */
    }
}