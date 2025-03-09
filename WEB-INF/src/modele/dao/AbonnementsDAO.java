package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import utils.*;

public class AbonnementsDAO {
    private DS ds;
    
    public AbonnementsDAO(){
        ds = DSFactory.newDS();
    }

    /**
     * Récupère toutes les abonnements de la base de données.
     *
     * @return Une liste d'objets {@code Abonnement}.
     */
    public List<Abonnement> selectAll() {
        List<Abonnement> abonnements = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Abonnements";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int uidAbonne = rs.getInt("uidAbonne");
                    int uidAbonnement = rs.getInt("uidAbonnement");
                    LocalDateTime dabonnement = BAO.conversion(rs.getTimestamp("dabonnement"));
                    abonnements.add(new Abonnement(uidAbonne, uidAbonnement, dabonnement));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return abonnements;
    }

    /**
     * Insère un nouvel abonnement dans la base de données.
     *
     * @param abonnement L'objet {@code Abonnement} à insérer.
     */
    public void insert(Abonnement abonnement) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Abonnements (uidAbonne, uidAbonnement, dabonnement) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, abonnement.getUidAbonne());
                pstmt.setInt(2, abonnement.getUidAbonnement());
                pstmt.setTimestamp(3, BAO.conversion(abonnement.getDabonnement()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime un abonnement de la base de données.
     *
     * @param abonnement L'objet {@code Abonnement} à supprimer.
     */
    public void delete(Abonnement abonnement) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Abonnements WHERE uidAbonne = ? AND uidAbonnement = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, abonnement.getUidAbonne());
                pstmt.setInt(2, abonnement.getUidAbonnement());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Récupère un abonnement de la base de données en fonction de son {@code uidAbonne} et de son {@code uidAbonnement}.
     *
     * @param uidAbonne L'identifiant {@code uidAbonne} à chercher.
     * @param uidAbonnement L'identifiant {@code uidAbonnement} à chercher.
     * @return L'objet {@code Abonnement} correspondant.
     */
    public Abonnement findByFollowAndFollowers(int uidAbonne, int uidAbonnement) {
        Abonnement abonnement = null;
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT * FROM Abonnements WHERE uidAbonne = ? AND uidAbonnement = ?";
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
            e.printStackTrace();
        }
        return abonnement;
    }
}