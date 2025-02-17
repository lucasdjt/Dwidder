import java.sql.*;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
 
@WebServlet("/ListeRencontres")
public class ListeRencontres extends HttpServlet {
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
 
        PrintWriter out = res.getWriter();
        out.println( "<head><title>servlet Liste Recontres</title>" );
        out.println( "<link rel=\"stylesheet\" href=\"styles.css\">" );
        out.println( "<META content=\"charset=UTF-8\"></head><body><center>" );

        try {
            Class.forName("org.postgresql.Driver");
 
            String url = "jdbc:postgresql://localhost:5432/reseau_social"; // URL de connexion
            String nom = "postgres"; // Nom d'utilisateur
            String mdp = "moi"; // Mot de passe de l'utilisateur
            Connection con = DriverManager.getConnection(url, nom, mdp);
 
            Statement stmt = con.createStatement();
            String query = "SELECT *" +
                    "FROM rencontres";
            ResultSet rs = stmt.executeQuery(query);
 
            out.println("<h1>Liste des rencontres</h1>");
            out.println("<table>");
            out.println("<tr><th>num_match</th><th>equipe1</th><th>equipe2</th><th>jour</th><th>score1</th><th>score2</th></tr>");
            while (rs.next()) {
 
                int numMatch = rs.getInt("num_match");
                int eq1 = rs.getInt("eq1");
                int eq2 = rs.getInt("eq2");
                Date jour = rs.getDate("jour");
                int sc1 = rs.getInt("sc1");
                int sc2 = rs.getInt("sc2");
 
                out.println("<tr>" );
                out.println("<td>" + numMatch + "</td>");
                out.println("<td>" + eq1 + "</td>");
                out.println("<td>" + eq2 + "</td>");
                out.println("<td>" + jour + "</td>");
                out.println("<td>" + sc1 + "</td>");
                out.println("<td>" + sc2 + "</td>");
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