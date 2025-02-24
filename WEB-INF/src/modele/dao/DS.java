package modele.dao;

import java.sql.Connection;

public interface DS {
    Connection getConnection() throws Exception; 
}
