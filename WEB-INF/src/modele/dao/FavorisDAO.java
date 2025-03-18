package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import modele.dto.Favori;
import utils.BAO;
import utils.DS;

public class FavorisDAO {
    
    public Favori findFavori(int uid, int pid) {
        Favori favori = null;
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT dfavori FROM Favoris WHERE uid = ? AND pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                pstmt.setInt(2, pid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        LocalDateTime dfavori = BAO.conversion(rs.getTimestamp("dfavori"));
                        favori = new Favori(uid, pid, dfavori);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la recherche d'un favori");
            e.printStackTrace();
        }
        return favori;
    }

    public void insert(Favori favori) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "INSERT INTO Favoris (uid, pid, dfavori) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, favori.getUid());
                pstmt.setInt(2, favori.getPid());
                pstmt.setTimestamp(3, BAO.conversion(favori.getDfavori()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'insertion d'un favori");
            e.printStackTrace();
        }
    }

    public void delete(Favori favori) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "DELETE FROM Favoris WHERE uid = ? AND pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, favori.getUid());
                pstmt.setInt(2, favori.getPid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la suppression d'un favori");
            e.printStackTrace();
        }
    }
}