import java.sql.*;

public class TestConnect {

    public static void main(String args[]) throws Exception {
    Class.forName("org.postgresql.Driver");
    String url = "jdbc:postgresql://localhost:5432/reseau_social"; // URL de connexion
    String nom = "postgres"; // Nom d'utilisateur
    String mdp = "moi"; // Mot de passe de l'utilisateur

    Connection con = DriverManager.getConnection(url,nom,mdp);

    con.close();
    System.out.println("Tout fonctionne");

    }
}