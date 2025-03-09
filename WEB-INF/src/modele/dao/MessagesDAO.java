package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import utils.*;

public class MessagesDAO {
    private DS ds;
    
    public MessagesDAO(){
        ds = DSFactory.newDS();
    }

    /**
     * Récupère tous les messages de la base de données.
     * @return Liste des messages.
     */
    public List<Message> selectAll() {
        List<Message> messages = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Messages";
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

    /**
     * Insère un nouveau message dans la base de données.
     * @param message Le message à insérer.
     */
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

    /**
     * Supprime un message de la base de données en fonction de son ID.
     * @param message Le message à supprimer.
     */
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

    /**
     * Trouve un message spécifique à partir de son mid.
     * @param mid L'identifiant du message.
     * @return Le message trouvé ou null si aucun message n'est trouvé.
     */
    public Message findByMid(int mid) {
        Message message = null;
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT * FROM Messages WHERE mid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, mid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int cid = rs.getInt("cid");
                        int uid = rs.getInt("uid");
                        String corps = rs.getString("corps");
                        LocalDateTime dmess = BAO.conversion(rs.getTimestamp("dmess"));
                        message = new Message(mid, cid, uid, corps, dmess);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}