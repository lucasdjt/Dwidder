package modele.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import modele.dto.*;
import modele.utils.*;

public class ConversationsDAO {
    DS ds = new DataIUT();

    public List<Conversation> selectAll() {
        List<Conversation> conversations = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Conversations";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int cid = rs.getInt("cid");
                    int uidEnvoyeur = rs.getInt("uidEnvoyeur");
                    int uidReceveur = rs.getInt("uidReceveur");
                    conversations.add(new Conversation(cid, uidEnvoyeur, uidReceveur));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conversations;
    }

    public void insert(Conversation conversation) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Conversations (uidEnvoyeur, uidReceveur) VALUES (?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, conversation.getUidEnvoyeur());
                pstmt.setInt(2, conversation.getUidReceveur());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Conversation conversation) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Conversations WHERE uidEnvoyeur = ? AND uidReceveur = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, conversation.getUidEnvoyeur());
                pstmt.setInt(2, conversation.getUidReceveur());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Message> getMessageConv(int cid){
        List<Message> messages = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT * FROM Messages WHERE cid = ? ORDER BY dmess DESC";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, cid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int mid = rs.getInt("mid");
                        int uid = rs.getInt("uid");
                        String corps = rs.getString("corps");
                        LocalDateTime dmess = BAO.conversion(rs.getTimestamp("dmess"));
                        messages.add(new Message(mid, cid, uid, corps, dmess));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }
}