package modele.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import modele.dto.*;
import utils.*;

public class ConversationsDAO {

    /**
     * Récupère toutes les conversations de la base de données.
     *
     * @return Une liste d'objets {@code Conversation}.
     */
    public List<Conversation> selectAll() {
        List<Conversation> conversations = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
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

    /**
     * Insère une nouvelle conversation dans la base de données.
     *
     * @param conversation L'objet {@code Conversation} à insérer.
     */
    public void insert(Conversation conversation) {
        try (Connection con = DS.getConnection()) {
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

    /**
     * Supprime une conversation de la base de données.
     *
     * @param conversation L'objet {@code Conversation} à supprimer.
     */
    public void delete(Conversation conversation) {
        try (Connection con = DS.getConnection()) {
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
    
    /**
     * Récupère les messages d'une conversation donnée.
     *
     * @param cid L'identifiant de la conversation.
     * @return Une liste d'objets {@code Message} associés à la conversation.
     */
    public List<Message> getMessageConv(int cid){
        List<Message> messages = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
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