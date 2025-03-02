package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import modele.utils.*;

public class UsersDAO {
    DS ds = new DataIUT();

    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Users";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int uid = rs.getInt("uid");
                    String id_pseudo = rs.getString("id_pseudo");
                    String pseudo = rs.getString("pseudo");
                    String prenom = rs.getString("prenom");
                    String nom_user = rs.getString("nom_user");
                    String email = rs.getString("email");
                    String mdp = rs.getString("mdp");
                    String bio = rs.getString("bio");
                    String pdp = rs.getString("pdp");
                    LocalDateTime dateInsc = BAO.conversion(rs.getTimestamp("date_insc"));
                    LocalDate dateNaiss = BAO.conversion(rs.getDate("date_naiss"));
                    String loca = rs.getString("loca");
                    String sexe = rs.getString("sexe");
                    String num_tel = rs.getString("num_tel");
                    String langue = rs.getString("langue");
                    boolean admin = rs.getBoolean("admin");
                    users.add(new User(uid, id_pseudo, pseudo, prenom, nom_user, email, mdp, bio, pdp, dateInsc, dateNaiss, loca, sexe, num_tel, langue, admin));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User findById_pseudo(String id_pseudo) {
        User user = null;
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT * FROM Users WHERE id_pseudo = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, id_pseudo);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int uid = rs.getInt("uid");
                        String pseudo = rs.getString("pseudo");
                        String prenom = rs.getString("prenom");
                        String nom_user = rs.getString("nom_user");
                        String email = rs.getString("email");
                        String mdp = rs.getString("mdp");
                        String bio = rs.getString("bio");
                        String pdp = rs.getString("pdp");
                        LocalDateTime dateInsc = BAO.conversion(rs.getTimestamp("date_insc"));
                        LocalDate dateNaiss = BAO.conversion(rs.getDate("date_naiss"));
                        String loca = rs.getString("loca");
                        String sexe = rs.getString("sexe");
                        String num_tel = rs.getString("num_tel");
                        String langue = rs.getString("langue");
                        boolean admin = rs.getBoolean("admin");
                        user = new User(uid, id_pseudo, pseudo, prenom, nom_user, email, mdp, bio, pdp, dateInsc, dateNaiss, loca, sexe, num_tel, langue, admin);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User findByEmail(String email) {
        User user = null;
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT * FROM Users WHERE email = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, email);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int uid = rs.getInt("uid");
                        String id_pseudo = rs.getString("id_pseudo");
                        String pseudo = rs.getString("pseudo");
                        String prenom = rs.getString("prenom");
                        String nom_user = rs.getString("nom_user");
                        String mdp = rs.getString("mdp");
                        String bio = rs.getString("bio");
                        String pdp = rs.getString("pdp");
                        LocalDateTime dateInsc = BAO.conversion(rs.getTimestamp("date_insc"));
                        LocalDate dateNaiss = BAO.conversion(rs.getDate("date_naiss"));
                        String loca = rs.getString("loca");
                        String sexe = rs.getString("sexe");
                        String num_tel = rs.getString("num_tel");
                        String langue = rs.getString("langue");
                        boolean admin = rs.getBoolean("admin");
                        user = new User(uid, id_pseudo, pseudo, prenom, nom_user, email, mdp, bio, pdp, dateInsc, dateNaiss, loca, sexe, num_tel, langue, admin);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void insert(User user) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Users (id_pseudo, pseudo, prenom, nom_user, email, mdp, bio, pdp, date_insc, date_naiss, loca, sexe, num_tel, langue, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, user.getIdPseudo());
                pstmt.setString(2, user.getPseudo());
                pstmt.setString(3, user.getPrenom());
                pstmt.setString(4, user.getNomUser());
                pstmt.setString(5, user.getEmail());
                pstmt.setString(6, user.getMdp());
                pstmt.setString(7, user.getBio());
                pstmt.setString(8, user.getPdp());
                pstmt.setTimestamp(9, BAO.conversion(user.getDateInsc()));
                pstmt.setDate(10, BAO.conversion(user.getDateNaiss()));
                pstmt.setString(11, user.getLoca());
                pstmt.setString(12, user.getSexe());
                pstmt.setString(13, user.getNumTel());
                pstmt.setString(14, user.getLangue());
                pstmt.setBoolean(15, user.getAdmin());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "UPDATE Users SET id_pseudo = ?, pseudo = ?, prenom = ?, nom_user = ?, email = ?, mdp = ?, bio = ?, pdp = ?, date_naiss = ?, loca = ?, sexe = ?, num_tel = ?, langue = ? WHERE uid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, user.getIdPseudo());
                pstmt.setString(2, user.getPseudo());
                pstmt.setString(3, user.getPrenom());
                pstmt.setString(4, user.getNomUser());
                pstmt.setString(5, user.getEmail());
                pstmt.setString(6, user.getMdp());
                pstmt.setString(7, user.getBio());
                pstmt.setString(8, user.getPdp());
                pstmt.setDate(9, BAO.conversion(user.getDateNaiss()));
                pstmt.setString(10, user.getLoca());
                pstmt.setString(11, user.getSexe());
                pstmt.setString(12, user.getNumTel());
                pstmt.setString(13, user.getLangue());
                pstmt.setInt(14, user.getUid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delete(User user) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Users WHERE id_pseudo = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, user.getIdPseudo());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}