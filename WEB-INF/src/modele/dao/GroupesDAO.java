package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dto.Groupe;
import modele.dto.User;
import utils.BAO;
import utils.DS;

public class GroupesDAO {

    public void insert(Groupe groupe) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "INSERT INTO Groupes (uid, nomGrp, pdpGrp, description, dcreat) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getUid());
                pstmt.setString(2, groupe.getHTMLNomGrp());
                pstmt.setString(3, groupe.getPdpGrp());
                pstmt.setString(4, groupe.getHTMLDescription());
                pstmt.setTimestamp(5, BAO.conversion(groupe.getDcreat()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'insertion d'un groupe");
            e.printStackTrace();
        }
    }

    public void update(Groupe groupe) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "UPDATE Groupes SET uid = ?, pdpGrp = ?, nomGrp = ?, description = ? WHERE gid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getUid());
                pstmt.setString(2, groupe.getPdpGrp());
                pstmt.setString(3, groupe.getHTMLNomGrp());
                pstmt.setString(4, groupe.getHTMLDescription());
                pstmt.setInt(5, groupe.getGid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la modification d'un groupe");
            e.printStackTrace();
        }
    }

    public void delete(Groupe groupe) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "DELETE FROM Groupes WHERE gid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getGid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la suppression d'un groupe");
            e.printStackTrace();
        }
    }
    
    public List<User> getListUsersOfAGroup(int gid){
        List<User> membres = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
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
                        boolean admin = rs.getBoolean("admin");
                        membres.add(new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, admin));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des utilisateurs d'un groupe");
            e.printStackTrace();
        }
        return membres;
    }
    
    public Groupe findGroupByGid(int gid) {
        Groupe groupe = null;
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT * FROM Groupes WHERE gid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, gid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int uid = rs.getInt("uid");
                        String pdpGrp = rs.getString("pdpGrp");
                        String nomGrp = rs.getString("nomGrp");
                        String description = rs.getString("description");
                        LocalDateTime dcreat = BAO.conversion(rs.getTimestamp("dcreat"));
                        groupe = new Groupe(gid, uid, pdpGrp, nomGrp, description, dcreat);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la recherche d'un groupe grâce à son gid");
            e.printStackTrace();
        }
        return groupe;
    }

    public Groupe findGroupByName(String nomGrp){
        Groupe groupe = null;
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT * FROM Groupes WHERE nomGrp = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, nomGrp);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int gid = rs.getInt("gid");
                        int uid = rs.getInt("uid");
                        String pdpGrp = rs.getString("pdpGrp");
                        String description = rs.getString("description");
                        LocalDateTime dcreat = BAO.conversion(rs.getTimestamp("dcreat"));
                        groupe = new Groupe(gid, uid, pdpGrp, nomGrp, description, dcreat);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la recherche d'un groupe grâce à son nomGrp");
            e.printStackTrace();
        }
        return groupe;
    }
}