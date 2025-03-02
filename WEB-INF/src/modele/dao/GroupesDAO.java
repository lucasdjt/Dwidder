package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import modele.utils.*;

public class GroupesDAO {
    DS ds = new DataIUT();

    public List<Groupe> selectAll() {
        List<Groupe> groupes = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Groupes";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int gid = rs.getInt("gid");
                    int uidAdmin = rs.getInt("uid_admin");
                    int pidEpingle = rs.getInt("pid_epingle");
                    String nomGrp = rs.getString("nom_grp");
                    String description = rs.getString("description");
                    LocalDateTime dateCreation = BAO.conversion(rs.getTimestamp("date_creation"));
                    groupes.add(new Groupe(gid, uidAdmin, pidEpingle, nomGrp, description, dateCreation));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupes;
    }

    public void insert(Groupe groupe) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Groupes (uid_admin, pid_epingle, nom_grp, description, date_creation) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getUidAdmin());
                pstmt.setInt(2, groupe.getPidEpingle());
                pstmt.setString(3, groupe.getNomGrp());
                pstmt.setString(4, groupe.getDescription());
                pstmt.setTimestamp(4, BAO.conversion(groupe.getDateCreation()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Groupe groupe) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "UPDATE Groupes SET uid_admin = ?, pid_epingle = ?, nom_grp = ?, description = ? WHERE gid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getUidAdmin());
                pstmt.setInt(2, groupe.getPidEpingle());
                pstmt.setString(3, groupe.getNomGrp());
                pstmt.setString(4, groupe.getDescription());
                pstmt.setInt(5, groupe.getGid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Groupe groupe) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Groupes WHERE gid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getGid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}