package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import modele.dto.Abonnement;
import utils.BAO;
import utils.DS;

public class AbonnementsDAO {

    public void insert(Abonnement abonnement) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "INSERT INTO Abonnements (uidAbonne, uidAbonnement, dabonnement) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, abonnement.getUidAbonne());
                pstmt.setInt(2, abonnement.getUidAbonnement());
                pstmt.setTimestamp(3, BAO.conversion(abonnement.getDabonnement()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'insertion d'un abonnement");
            e.printStackTrace();
        }
    }

    public void delete(Abonnement abonnement) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "DELETE FROM Abonnements WHERE uidAbonne = ? AND uidAbonnement = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, abonnement.getUidAbonne());
                pstmt.setInt(2, abonnement.getUidAbonnement());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la suppression d'un abonnement");
            e.printStackTrace();
        }
    }
    
    public Abonnement findAbonnement(int uidAbonne, int uidAbonnement) {
        Abonnement abonnement = null;
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT dabonnement FROM Abonnements WHERE uidAbonne = ? AND uidAbonnement = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uidAbonne);
                pstmt.setInt(2, uidAbonnement);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        LocalDateTime dabonnement = BAO.conversion(rs.getTimestamp("dabonnement"));
                        abonnement = new Abonnement(uidAbonne, uidAbonnement, dabonnement);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la recherche d'un abonnement");
            e.printStackTrace();
        }
        return abonnement;
    }
}