package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dto.Logs;
import utils.BAO;
import utils.DS;


public class LogsDAO {
    public static void insert(String pseudoLog, String textLog) {
        try (Connection con = DS.getConnection()) {
            String query = "INSERT INTO Logs (daction, pseudoLog, textlog) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setTimestamp(1, BAO.conversion(LocalDateTime.now()));
                pstmt.setString(2, pseudoLog);
                pstmt.setString(3, textLog);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur pendant l'insertion dans les Logs");
            e.printStackTrace();
        }
    }
    
    public static List<Logs> selectAll() {
        List<Logs> logs = new ArrayList<>();
            try (Connection con = DS.getConnection()) {
                String query = "SELECT * FROM Logs ORDER BY daction DESC";
                try (PreparedStatement pstmt = con.prepareStatement(query);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int lid = rs.getInt("lid");
                        LocalDateTime daction = BAO.conversion(rs.getTimestamp("daction"));
                        String pseudoLog = rs.getString("pseudoLog");
                        String textLog = rs.getString("textlog");
                        logs.add(new Logs(lid, daction, pseudoLog, textLog));
                    }
                }
            } catch (Exception e) {
                System.err.println("Erreur lors de la récupération des logs");
                e.printStackTrace();
            }
            return logs;
    }
}
