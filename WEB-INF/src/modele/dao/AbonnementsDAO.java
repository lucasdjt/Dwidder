package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import modele.utils.*;

public class AbonnementsDAO {
    DS ds = new DataIUT();

    public List<Abonnement> selectAll() {
        List<Abonnement> abonnements = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Abonnements";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int uidAbonne = rs.getInt("uidAbonne");
                    int uidAbonnement = rs.getInt("uidAbonnement");
                    LocalDateTime dateAbonnement = BAO.conversion(rs.getTimestamp("dateAbonnement"));
                    abonnements.add(new Abonnement(uidAbonne, uidAbonnement, dateAbonnement));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return abonnements;
    }

    public void insert(Abonnement abonnement) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Abonnements (uid_abonne, uid_abonnement, date_abonnement) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, abonnement.getUidAbonne());
                pstmt.setInt(2, abonnement.getUidAbonnement());
                pstmt.setTimestamp(3, BAO.conversion(abonnement.getDateAbonnement()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Abonnement abonnement) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Abonnements WHERE uid_abonne = ? AND uid_abonnement = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, abonnement.getUidAbonne());
                pstmt.setInt(2, abonnement.getUidAbonnement());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}