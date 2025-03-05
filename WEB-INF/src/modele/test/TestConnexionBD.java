package modele.test;

import java.sql.*;

import modele.utils.*;

public class TestConnexionBD {
    public static void main(String args[]) throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            DS ds = DSFactory.newDS();
            Connection con = ds.getConnection();
            con.close();
            System.out.println("Tout fonctionne");
        } catch (Exception e) {
            System.out.println("Erreur de connexion");
            e.printStackTrace();
        }
    }
}