package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import utils.*;

public class FavorisDAO {
    private DS ds;
    
    public FavorisDAO(){
        ds = DSFactory.newDS();
    }

    /**
     * Récupère tous les favoris de la base de données.
     *
     * @return Une liste d'objets {@code Favori}.
     */
    public List<Favori> selectAll() {
        List<Favori> favoris = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Favoris";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int uid = rs.getInt("uid");
                    int pid = rs.getInt("pid");
                    LocalDateTime dfavori = BAO.conversion(rs.getTimestamp("dfavori"));
                    favoris.add(new Favori(uid, pid, dfavori));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favoris;
    }

    /**
     * Insère un nouveau favori dans la base de données.
     *
     * @param favori L'objet {@code Favori} à insérer.
     */
    public void insert(Favori favori) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Favoris (uid, pid, dfavori) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, favori.getUid());
                pstmt.setInt(2, favori.getPid());
                pstmt.setTimestamp(3, BAO.conversion(favori.getDfavori()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime un favori de la base de données.
     *
     * @param favori L'objet {@code Favori} à supprimer.
     */
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
    
    /**
     * Récupèrer un favori de la base de données en fonction de son {@code uid} et de son {@code pid}.
     *
     * @param uid L'identifiant {@code uid} à chercher.
     * @param pid L'identifiant {@code pid} à chercher.
     * @return Une liste d'objets {@code Favori}.
     */
    public Favori findByUidAndPid(int uid, int pid) {
        Favori favori = null;
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT * FROM Favoris WHERE uid = ? AND pid = ?";
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
            e.printStackTrace();
        }
        return favori;
    }
}