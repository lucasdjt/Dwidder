package controleur;

import java.sql.*;
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
 
@WebServlet("/GetTest")
public class Test extends HttpServlet {
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
 
        PrintWriter out = res.getWriter();
        out.println( "<head><title>servlet Get Test</title>" );
        out.println( "<META content=\"charset=UTF-8\"></head><body><center>" );

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://psqlserv/but2","lucasdejesusteixeiraetu","moi"); // IUT
            // Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/reseau_social","postgres","moi"); // HOME
 
            Statement stmt = con.createStatement();
            String query = "SELECT *" +
                    "FROM Users";
            ResultSet rs = stmt.executeQuery(query);
 
            out.println("<h1>Liste des users</h1>");
            out.println("<table>");
            out.println("<tr><th>uid</th><th>id_pseudo</th><th>pseudo</th><th>prenom</th><th>nom_user</th><th>email</th><th>mdp</th><th>bio</th><th>pdp</th><th>date_insc</th><th>date_naiss</th><th>loca</th><th>sexe</th><th>num_tel</th><th>langue</th><th>admin</th>");
            while (rs.next()) {
                int uid = rs.getInt("uid");
                String id_pseudo = rs.getString("id_pseudo");
                String pseudo = rs.getString("pseudo");
                String prenom = rs.getString("prenom");
                String nom_user = rs.getString("nom_user");
                String email = rs.getString("email");
                String mdp = rs.getString("mdp");
                String bio = rs.getString("bio");
                String pdp = rs.getString("pdp");
                Date date_insc = rs.getDate("date_insc");
                Date date_naiss = rs.getDate("date_naiss");
                String loca = rs.getString("loca");
                String sexe = rs.getString("sexe");
                String num_tel = rs.getString("num_tel");
                String langue = rs.getString("langue");
                String admin = rs.getString("admin");

                out.println("<tr>");
                out.println("<td>" + uid + "</td>");
                out.println("<td>" + id_pseudo + "</td>");
                out.println("<td>" + pseudo + "</td>");
                out.println("<td>" + prenom + "</td>");
                out.println("<td>" + nom_user + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + mdp + "</td>");
                out.println("<td>" + bio + "</td>");
                out.println("<td>" + pdp + "</td>");
                out.println("<td>" + date_insc + "</td>");
                out.println("<td>" + date_naiss + "</td>");
                out.println("<td>" + loca + "</td>");
                out.println("<td>" + sexe + "</td>");
                out.println("<td>" + num_tel + "</td>");
                out.println("<td>" + langue + "</td>");
                out.println("<td>" + admin + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            con.close();
 
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException e) {
            out.println("<p>Errord: " + e.getMessage() + "</p>");
        } catch (ClassNotFoundException e) {
            out.println("<p>Error: Nop - Driver not found - " + e.getMessage() + "</p>");
        }
    }
}