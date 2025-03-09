package utils;

import java.sql.*;

public class DataHome implements DS{

    @Override
    public Connection getConnection() throws Exception{    
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/reseau_social";
        String nom = "postgres";
        String mdp = "moi";
        
        return DriverManager.getConnection(url,nom,mdp);
    }
        
}