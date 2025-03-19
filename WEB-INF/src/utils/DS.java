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
            String url = p.getProperty("url");
            String login = p.getProperty("login");
            String password = p.getProperty("password");
            return DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            return configProp();
        }
    }

    public static Connection configProp() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://psqlserv/but2"; // A changer selon vos configurations de base de données
            String login = "lucasdejesusteixeiraetu"; // A changer selon vos configurations de base de données
            String password = "moi"; // A changer selon vos configurations de base de données
            return DriverManager.getConnection(url, login, password);
        } catch (Exception ex) {
            throw new Exception("Connexion fail", ex);
        }
    }
}