package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import utils.*;

public class MessagesDAO {

    /**
     * Récupère tous les messages de la base de données.
     * @return Liste des messages.
     */
    public List<Message> selectAll() {
        List<Message> messages = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requete = "SELECT * FROM Messages";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int mid = rs.getInt("mid");
                    int uidEnvoyeur = rs.getInt("uidEnvoyeur");
                    int uidReceveur = rs.getInt("uidReceveur");
                    String corps = rs.getString("corps");
                    String imgMess = rs.getString("imgMess");
                    LocalDateTime dmess = BAO.conversion(rs.getTimestamp("dmess"));
                    messages.add(new Message(mid, uidEnvoyeur, uidReceveur, corps, imgMess, dmess));
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
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "INSERT INTO Messages (uidEnvoyeur, uidReceveur, corps, imgMess, dmess) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, message.getUidEnvoyeur());
                pstmt.setInt(2, message.getUidReceveur());
                pstmt.setString(3, message.getHTMLCorps());
                pstmt.setString(4, message.getImgMess());
                pstmt.setTimestamp(5, BAO.conversion(message.getDmess()));
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
        try (Connection con = DS.getConnection()) {
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