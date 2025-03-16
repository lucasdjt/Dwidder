package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import utils.*;

public class GroupesDAO {

    /**
     * Récupère tous les groupes de la base de données.
     *
     * @return Une liste d'objets {@code Groupe}.
     */
    public List<Groupe> selectAll() {
        List<Groupe> groupes = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requete = "SELECT * FROM Groupes";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int gid = rs.getInt("gid");
                    int uid = rs.getInt("uid");
                    String pdpGrp = rs.getString("pdpGrp");
                    String nomGrp = rs.getString("nomGrp");
                    String description = rs.getString("description");
                    LocalDateTime dcreat = BAO.conversion(rs.getTimestamp("dcreat"));
                    groupes.add(new Groupe(gid, uid, pdpGrp, nomGrp, description, dcreat));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupes;
    }

    /**
     * Insère un nouveau groupe dans la base de données.
     *
     * @param groupe L'objet {@code Groupe} à insérer.
     */
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
            e.printStackTrace();
        }
    }

    /**
     * Met à jour les informations d'un groupe.
     *
     * @param groupe L'objet {@code Groupe} à mettre à jour.
     */
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
            e.printStackTrace();
        }
    }

    /**
     * Supprime un groupe de la base de données.
     *
     * @param groupe L'objet {@code Groupe} à supprimer.
     */
    public void delete(Groupe groupe) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "DELETE FROM Groupes WHERE gid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getGid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Récupère les membres d'un groupe donné.
     *
     * @param gid L'identifiant du groupe.
     * @return Une liste d'objets {@code User} membres du groupe.
     */
    public List<User> getGroupeMembers(int gid){
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
            e.printStackTrace();
        }
        return membres;
    }
    
    /**
     * Trouve un groupe spécifique à partir de son gid.
     * @param gid L'identifiant du groupe.
     * @return Le groupe trouvé ou null si aucun groupe n'est trouvé.
     */
    public Groupe findByGid(int gid) {
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
            e.printStackTrace();
        }
        return groupe;
    }

    public Groupe findByName(String nomGrp){
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
            e.printStackTrace();
        }
        return groupe;
    }
}