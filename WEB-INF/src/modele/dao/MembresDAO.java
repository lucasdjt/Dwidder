package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import utils.*;

public class MembresDAO {
    private DS ds;
    
    public MembresDAO(){
        ds = DSFactory.newDS();
    }

    /**
     * Récupère tous les membres enregistrés.
     *
     * @return Une liste d'objets {@code Membre}.
     */
    public List<Membre> selectAll() {
        List<Membre> membres = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Membres";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int uid = rs.getInt("uid");
                    int gid = rs.getInt("gid");
                    LocalDateTime djoin = BAO.conversion(rs.getTimestamp("djoin"));
                    membres.add(new Membre(uid, gid, djoin));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return membres;
    }

    /**
     * Insère un nouveau membre dans un groupe.
     *
     * @param membre L'objet {@code Membre} à ajouter.
     */
    public void insert(Membre membre) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Membres (uid, gid, djoin) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, membre.getUid());
                pstmt.setInt(2, membre.getGid());
                pstmt.setTimestamp(3, BAO.conversion(membre.getDjoin()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime un membre d'un groupe.
     *
     * @param membre L'objet {@code Membre} à retirer.
     */
    public void delete(Membre membre) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Membres WHERE uid = ? AND gid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, membre.getUid());
                pstmt.setInt(2, membre.getGid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}