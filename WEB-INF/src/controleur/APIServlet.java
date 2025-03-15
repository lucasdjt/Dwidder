package controleur;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("/api/*")
public class APIServlet extends HttpServlet {
    // A REALISER ETAPE2.0
    
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        res.setContentType("application/json; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid API request");
            return;
        }
        
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length < 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid API format");
            return;
        }
        
        String resource = pathParts[1];
        String responseJson = "";
        
        try (PrintWriter out = res.getWriter()) {
            switch (resource) {
                case "users":
                    // Intégration du DAO pour récupérer la liste des utilisateurs (sans données personnelles)
                    responseJson = "{\"message\": \"Liste des utilisateurs\"}";
                    break;
                
                case "post":
                    if (pathParts.length == 3) {
                        int postId = Integer.parseInt(pathParts[2]);
                        // Intégration du DAO pour récupérer le post avec id = postId
                        responseJson = "{\"message\": \"Détails du post " + postId + "\"}";
                    } else {
                        // Intégration du DAO pour récupérer tous les posts publics
                        responseJson = "{\"message\": \"Liste des posts\"}";
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
                
                case "like":
                    if (pathParts.length == 3) {
                        int likeId = Integer.parseInt(pathParts[2]);
                        // Intégration du DAO pour récupérer les détails du like avec id = likeId
                        responseJson = "{\"message\": \"Détails du like " + likeId + "\"}";
                    } else {
                        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Like ID required");
                        return;
                    }
                    break;
                
                case "follow":
                    if (pathParts.length == 3) {
                        int followId = Integer.parseInt(pathParts[2]);
                        // Intégration du DAO pour récupérer les follow de l'utilisateur avec id = followId
                        responseJson = "{\"message\": \"Détails du follow " + followId + "\"}";
                    } else {
                        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Follow ID required");
                        return;
                    }
                    break;
                
                case "followers":
                    if (pathParts.length == 3) {
                        int userId = Integer.parseInt(pathParts[2]);
                        responseJson = "{\"message\": \"Liste des followers de l'utilisateur " + userId + "\"}";
                    } else {
                        res.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID required");
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
    }
}