package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import modele.dto.Membre;
import utils.BAO;
import utils.DS;

public class MembresDAO {

    public void insert(Membre membre) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "INSERT INTO Membres (uid, gid, djoin) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, membre.getUid());
                pstmt.setInt(2, membre.getGid());
                pstmt.setTimestamp(3, BAO.conversion(membre.getDjoin()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'insertion d'un membre");
            e.printStackTrace();
        }
    }

    public void delete(Membre membre) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "DELETE FROM Membres WHERE uid = ? AND gid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, membre.getUid());
                pstmt.setInt(2, membre.getGid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la suppression d'un membre");
            e.printStackTrace();
        }
    }
}