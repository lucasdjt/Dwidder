package utils;

import java.sql.*;

/*
 * Test de votre configuration de base de données
 */
public class Test {

    public static void testBDD(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DS.getConnection();
            con.close();
            System.out.println("Vous êtes connectés à votre base de données");
        } catch (Exception e) {
            System.out.println("Erreur de connexion");
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        testBDD();
    }
}