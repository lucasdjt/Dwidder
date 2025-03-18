package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import modele.dto.Message;
import utils.BAO;
import utils.DS;

public class MessagesDAO {

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
            System.err.println("Erreur dans l'insertion d'un message");
            e.printStackTrace();
        }
    }
}