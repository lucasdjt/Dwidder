package modele.dao;

import java.sql.*;
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
                    int uidEnvoyeur = rs.getInt("uid_envoyeur");
                    int uidReceveur = rs.getInt("uid_receveur");
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
            String requetePrepare = "INSERT INTO Conversations (uid_envoyeur, uid_receveur) VALUES (?, ?)";
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
            String requetePrepare = "DELETE FROM Conversations WHERE uid_envoyeur = ? AND uid_receveur = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, conversation.getUidEnvoyeur());
                pstmt.setInt(2, conversation.getUidReceveur());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}