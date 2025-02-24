package dao;

import java.sql.*;

public class DS {

    Connection getConnection() throws Exception{    
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://psqlserv/but2";
        String nom = "lucasdejesusteixeiraetu";
        String mdp = "moi";
        
        return DriverManager.getConnection(url,nom,mdp);
    }
        
}