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
                    int uid = rs.getInt("uid");
                    int pid = rs.getInt("pid");
                    String nomGrp = rs.getString("nomGrp");
                    String description = rs.getString("description");
                    LocalDateTime dcreat = BAO.conversion(rs.getTimestamp("dcreat"));
                    groupes.add(new Groupe(gid, uid, pid, nomGrp, description, dcreat));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupes;
    }

    public void insert(Groupe groupe) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Groupes (uid, pid, nomGrp, description, dcreat) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getUid());
                pstmt.setInt(2, groupe.getPid());
                pstmt.setString(3, groupe.getNomGrp());
                pstmt.setString(4, groupe.getDescription());
                pstmt.setTimestamp(4, BAO.conversion(groupe.getDcreat()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Groupe groupe) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "UPDATE Groupes SET uid = ?, pid = ?, nomGrp = ?, description = ? WHERE gid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getUid());
                pstmt.setInt(2, groupe.getPid());
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
    
    public List<User> getGroupeMembers(int gid){
        List<User> membres = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT U.*  FROM Membres M JOIN Users U ON M.uid = U.uid WHERE M.gid = ? ORDER BY M.djoin DESC;";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, gid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int uid = rs.getInt("uid");
                        String idPseudo = rs.getString("idPseudo");
                        String pseudo = rs.getString("pseudo");
                        String prenom = rs.getString("prenom");
                        String nomUser = rs.getString("nomUser");
                        String email = rs.getString("email");
                        String mdp = rs.getString("mdp");
                        String bio = rs.getString("bio");
                        String pdp = rs.getString("pdp");
                        LocalDateTime dinsc = BAO.conversion(rs.getTimestamp("dinsc"));
                        LocalDate dnaiss = BAO.conversion(rs.getDate("dnaiss"));
                        String loca = rs.getString("loca");
                        String sexe = rs.getString("sexe");
                        String tel = rs.getString("tel");
                        String langue = rs.getString("langue");
                        boolean admin = rs.getBoolean("admin");
                        membres.add(new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, sexe, tel, langue, admin));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return membres;
    }
}