package controleur;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import modele.dao.*;
import modele.dto.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/users/*")
public class UserAPI extends HttpServlet {
    final String vuelink = "WEB-INF/vue/user.jsp";

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        UsersDAO dao = new UsersDAO();
        String info = req.getPathInfo();

        if(info == null || info.equals("/")) {
            List<User> users = dao.selectAll();
            req.setAttribute("users", users);
            RequestDispatcher dispatcher = req.getRequestDispatcher(vuelink);
            dispatcher.forward(req, res);
            return;
        }

        String[] splits = info.split("/");
        
        if (splits.length != 2) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String id_pseudo = splits[1];
        User u = dao.findById_pseudo(id_pseudo);
        if (u==null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setAttribute("user", u);
        RequestDispatcher dispatcher = req.getRequestDispatcher(vuelink);
        dispatcher.forward(req, res);
        return;

    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json;charset=UTF-8");
        StringBuilder data = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }
        User newUser = new User();
        newUser.setId_pseudo(req.getParameter("id_pseudo"));
        newUser.setPseudo(req.getParameter("pseudo"));
        newUser.setPrenom(req.getParameter("prenom"));
        newUser.setNom_user(req.getParameter("nom_user"));
        newUser.setEmail(req.getParameter("email"));
        newUser.setMdp(req.getParameter("mdp"));
        newUser.setBio(req.getParameter("bio"));
        newUser.setPdp(req.getParameter("pdp"));
        newUser.setDate_insc(LocalDateTime.now());
        System.out.println(req.getParameter("date_naiss"));
        newUser.setDate_naiss(req.getParameter("date_naiss") == null ? null : LocalDate.parse(req.getParameter("date_naiss")));
        System.out.println("test2");
        newUser.setLoca(req.getParameter("loca"));
        newUser.setSexe(req.getParameter("sexe"));
        newUser.setNum_tel(req.getParameter("num_tel"));
        newUser.setLangue(req.getParameter("langue"));
        UsersDAO dao = new UsersDAO();
        dao.insert(newUser);
        req.setAttribute("add", "Ajout réussi");
        RequestDispatcher dispatcher = req.getRequestDispatcher(vuelink);
        dispatcher.forward(req, res);
        //BOUCLE
    }
}