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
        return null;
        /*
- On peut obtenir les messages privés d une conversation trié par date
SELECT * FROM UserMessages WHERE cid = 1 ORDER BY date_mess DESC;
        */
    }
    
    public Message getInfoMessage(int mid){ // Message remplacé par une Classe de Vue
        return null;
        /*
- On peut obtenir les infos d un message
SELECT * FROM UserMessages WHERE mid = 1;
        */
    }
}