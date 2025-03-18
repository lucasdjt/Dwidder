package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modele.dto.Reaction;
import utils.DS;

public class ReactionsDAO {

    public void insert(Reaction reaction) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "INSERT INTO Reactions (uid, pid, type) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, reaction.getUid());
                pstmt.setInt(2, reaction.getPid());
                pstmt.setString(3, reaction.getType());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'insertion d'une réaction");
            e.printStackTrace();
        }
    }

    public void update(Reaction reaction) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "UPDATE Reactions SET type = ? WHERE uid = ? AND pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, reaction.getType());
                pstmt.setInt(2, reaction.getUid());
                pstmt.setInt(3, reaction.getPid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la modification d'une réaction");
            e.printStackTrace();
        }
    }
    
    public void delete(Reaction reaction) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "DELETE FROM Reactions WHERE uid = ? AND pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, reaction.getUid());
                pstmt.setInt(2, reaction.getPid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la suppression d'une réaction");
            e.printStackTrace();
        }
    }

    public Reaction findReaction(int uid, int pid) {
        Reaction reaction = null;
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT * FROM Reactions WHERE uid = ? AND pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                pstmt.setInt(2, pid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String type = rs.getString("type");
                        reaction = new Reaction(uid, pid, type);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la recherche d'une réaction");
            e.printStackTrace();
        }
        return reaction;
    }
}