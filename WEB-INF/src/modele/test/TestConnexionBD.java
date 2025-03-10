package modele.test;

import java.sql.*;

import utils.*;

public class TestConnexionBD {
    public static void main(String args[]) throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DS.getConnection();
            con.close();
            System.out.println("Tout fonctionne");
        } catch (Exception e) {
            System.out.println("Erreur de connexion");
            e.printStackTrace();
        }
    }
}