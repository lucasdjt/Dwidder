package modele.dao;

import java.time.LocalDateTime;
import java.sql.*;
import java.util.*;

import modele.dto.Favori;
import modele.utils.BAO;

public class FavorisDAO {
    DS ds = new DataIUT();

    public List<Favori> selectAll() {
        List<Favori> favoris = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Favoris";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int uid = rs.getInt("uid");
                    int pid = rs.getInt("pid");
                    LocalDateTime dateFavori = BAO.conversion(rs.getTimestamp("dateFavori"));
                    favoris.add(new Favori(uid, pid, dateFavori));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favoris;
    }

    public void insert(Favori favori) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Favoris (uid, pid, date_favori) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, favori.getUid());
                pstmt.setInt(2, favori.getPid());
                pstmt.setTimestamp(3, BAO.conversion(favori.getDateFavori()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Favori favori) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Favoris WHERE uid = ? AND pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, favori.getUid());
                pstmt.setInt(2, favori.getPid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}