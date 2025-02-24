package modele.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import modele.dto.User;

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
                    Date date_insc = rs.getDate("date_insc");
                    Date date_naiss = rs.getDate("date_naiss");
                    String loca = rs.getString("loca");
                    String sexe = rs.getString("sexe");
                    String num_tel = rs.getString("num_tel");
                    String langue = rs.getString("langue");
                    String admin = rs.getString("admin");
                    users.add(new User(uid, id_pseudo, pseudo, prenom, nom_user, email, mdp, bio, pdp, date_insc, date_naiss, loca, sexe, num_tel, langue, admin));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User findById_pseudo(String id_pseudo) {
        User user = null;
        try (Connection conn = ds.getConnection()) {
            String sql = "SELECT * FROM Users WHERE id_pseudo = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
                        Date date_insc = rs.getDate("date_insc");
                        Date date_naiss = rs.getDate("date_naiss");
                        String loca = rs.getString("loca");
                        String sexe = rs.getString("sexe");
                        String num_tel = rs.getString("num_tel");
                        String langue = rs.getString("langue");
                        String admin = rs.getString("admin");
                        user = new User(uid, id_pseudo, pseudo, prenom, nom_user, email, mdp, bio, pdp, date_insc, date_naiss, loca, sexe, num_tel, langue, admin);
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
        try (Connection conn = ds.getConnection()) {
            String sql = "SELECT * FROM Users WHERE email = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
                        Date date_insc = rs.getDate("date_insc");
                        Date date_naiss = rs.getDate("date_naiss");
                        String loca = rs.getString("loca");
                        String sexe = rs.getString("sexe");
                        String num_tel = rs.getString("num_tel");
                        String langue = rs.getString("langue");
                        String admin = rs.getString("admin");
                        user = new User(uid, id_pseudo, pseudo, prenom, nom_user, email, mdp, bio, pdp, date_insc, date_naiss, loca, sexe, num_tel, langue, admin);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void update(User user) {
        try (Connection conn = ds.getConnection()) {
            String sql = "UPDATE Users SET id_pseudo = ?, pseudo = ?, prenom = ?, nom_user = ?, email = ?, mdp = ?, bio = ?, pdp = ?, date_naiss = ?, loca = ?, sexe = ?, num_tel = ?, langue = ? WHERE uid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, user.getId_pseudo());
                pstmt.setString(2, user.getPseudo());
                pstmt.setString(3, user.getPrenom());
                pstmt.setString(4, user.getNom_user());
                pstmt.setString(5, user.getEmail());
                pstmt.setString(6, user.getMdp());
                pstmt.setString(7, user.getBio());
                pstmt.setString(8, user.getPdp());
                pstmt.setDate(9, user.getDate_naiss());
                pstmt.setString(10, user.getLoca());
                pstmt.setString(11, user.getSexe());
                pstmt.setString(12, user.getNum_tel());
                pstmt.setString(13, user.getLangue());
                pstmt.setInt(14, user.getUid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(User user) {
        try (Connection conn = ds.getConnection()) {
            String sql = "INSERT INTO Users (id_pseudo, pseudo, prenom, nom_user, email, mdp, bio, pdp, date_insc, date_naiss, loca, sexe, num_tel, langue, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, user.getId_pseudo());
                pstmt.setString(2, user.getPseudo());
                pstmt.setString(3, user.getPrenom());
                pstmt.setString(4, user.getNom_user());
                pstmt.setString(5, user.getEmail());
                pstmt.setString(6, user.getMdp());
                pstmt.setString(7, user.getBio());
                pstmt.setString(8, user.getPdp());
                pstmt.setDate(9, user.getDate_insc());
                pstmt.setDate(10, user.getDate_naiss());
                pstmt.setString(11, user.getLoca());
                pstmt.setString(12, user.getSexe());
                pstmt.setString(13, user.getNum_tel());
                pstmt.setString(14, user.getLangue());
                pstmt.setString(15, user.getAdmin());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(User user) {
        try (Connection conn = ds.getConnection()) {
            String sql = "DELETE FROM Users WHERE id_pseudo = id_pseudo";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, user.getId_pseudo());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}