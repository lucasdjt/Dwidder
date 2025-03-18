package utils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DS {

    public static Connection getConnection() throws Exception{
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("config.prop"));
            Class.forName(p.getProperty("driver"));
            String url = p.getProperty("urlIUT");
            String login = p.getProperty("loginIUT");
            String password = p.getProperty("password");
            return DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            return testIUT();
        }
    }

    public static Connection testIUT() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://psqlserv/but2";
            String login = "lucasdejesusteixeiraetu";
            String password = "moi";
            return DriverManager.getConnection(url, login, password);
        } catch (Exception ex) {
            throw new Exception("Connexion fail", ex);
        }            

    }

    public static Connection testHOME() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/reseau_social";
            String login = "postgres";
            String password = "moi";
            return DriverManager.getConnection(url, login, password);
        } catch (Exception ex) {
            throw new Exception("Connexion fail", ex);
        }
    }
}