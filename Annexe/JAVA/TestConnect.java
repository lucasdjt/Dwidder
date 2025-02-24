package Annexe.JAVA;

import java.sql.*;

public class TestConnect {

    public static void main(String args[]) throws Exception {
    Class.forName("org.postgresql.Driver");
    Connection con = DriverManager.getConnection("jdbc:postgresql://psqlserv/but2","lucasdejesusteixeiraetu","moi"); // IUT
    // Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/reseau_social","postgres","moi"); // HOME
    con.close();
    System.out.println("Tout fonctionne");

    }
}