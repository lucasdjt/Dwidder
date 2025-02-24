package controleur;

import java.io.*;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import modele.dao.*;
import modele.dto.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/users")
public class UserRestAPI extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        UsersDAO dao = new UsersDAO();
        ObjectMapper objectMapper = new ObjectMapper();

        String info = req.getPathInfo();

        if(info == null || info.equals("/")) {
            List<User> users = dao.selectAll();
            out.println(objectMapper.writeValueAsString(users));
            out.close();
            return;
        }

        String[] splits = info.split("/");
        
        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            out.close();
            return;
        }
        String id_pseudo = splits[1];
        User u = dao.findById_pseudo(id_pseudo);
        if (u==null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            out.close();
            return;
        }
        out.println(objectMapper.writeValueAsString(u));
        out.close();
        return;

    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        User newUser = objectMapper.readValue(data.toString(), User.class);

        UsersDAO dao = new UsersDAO();
        dao.insert(newUser);
        
        out.println("sucess - \"User\": " + objectMapper.writeValueAsString(newUser));
        out.close();
    }
}