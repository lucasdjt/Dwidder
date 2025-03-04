package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import modele.utils.*;

public class MessagesDAO {
    DS ds = new DataIUT();

    public List<Message> selectAll() {
        List<Message> messages = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Reactions";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int mid = rs.getInt("mid");
                    int cid = rs.getInt("cid");
                    int uid = rs.getInt("uid");
                    String corps = rs.getString("corps");
                    LocalDateTime dmess = BAO.conversion(rs.getTimestamp("dmess"));
                    messages.add(new Message(mid, cid, uid, corps, dmess));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void insert(Message message) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Messages (cid, uid, corps, dmess) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, message.getCid());
                pstmt.setInt(2, message.getUid());
                pstmt.setString(3, message.getCorps());
                pstmt.setTimestamp(4, BAO.conversion(message.getDmess()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Message message) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Messages WHERE mid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, message.getMid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}