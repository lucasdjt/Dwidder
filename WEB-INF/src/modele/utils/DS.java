package modele.utils;

import java.sql.Connection;

public interface DS {
    Connection getConnection() throws Exception; 
}
