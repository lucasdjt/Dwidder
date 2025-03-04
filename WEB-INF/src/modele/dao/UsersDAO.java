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
                    users.add(new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, sexe, tel, langue, admin));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User findById_pseudo(String idPseudo) {
        User user = null;
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT * FROM Users WHERE idPseudo = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, idPseudo);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int uid = rs.getInt("uid");
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
                        user = new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, sexe, tel, langue, admin);
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
                        String idPseudo = rs.getString("idPseudo");
                        String pseudo = rs.getString("pseudo");
                        String prenom = rs.getString("prenom");
                        String nomUser = rs.getString("nomUser");
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
                        user = new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, sexe, tel, langue, admin);
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
            String requetePrepare = "INSERT INTO Users (idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, sexe, tel, langue, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, user.getIdPseudo());
                pstmt.setString(2, user.getPseudo());
                pstmt.setString(3, user.getPrenom());
                pstmt.setString(4, user.getNomUser());
                pstmt.setString(5, user.getEmail());
                pstmt.setString(6, user.getMdp());
                pstmt.setString(7, user.getBio());
                pstmt.setString(8, user.getPdp());
                pstmt.setTimestamp(9, BAO.conversion(user.getDinsc()));
                pstmt.setDate(10, BAO.conversion(user.getDnaiss()));
                pstmt.setString(11, user.getLoca());
                pstmt.setString(12, user.getSexe());
                pstmt.setString(13, user.getTel());
                pstmt.setString(14, user.getLangue());
                pstmt.setBoolean(15, user.isAdmin());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "UPDATE Users SET idPseudo = ?, pseudo = ?, prenom = ?, nomUser = ?, email = ?, mdp = ?, bio = ?, pdp = ?, dnaiss = ?, loca = ?, sexe = ?, tel = ?, langue = ? WHERE uid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, user.getIdPseudo());
                pstmt.setString(2, user.getPseudo());
                pstmt.setString(3, user.getPrenom());
                pstmt.setString(4, user.getNomUser());
                pstmt.setString(5, user.getEmail());
                pstmt.setString(6, user.getMdp());
                pstmt.setString(7, user.getBio());
                pstmt.setString(8, user.getPdp());
                pstmt.setDate(9, BAO.conversion(user.getDnaiss()));
                pstmt.setString(10, user.getLoca());
                pstmt.setString(11, user.getSexe());
                pstmt.setString(12, user.getTel());
                pstmt.setString(13, user.getLangue());
                pstmt.setInt(14, user.getUid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delete(String idPseudo) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Users WHERE idPseudo = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, idPseudo);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Groupe> getUserGroups(int uid) {
        List<Groupe> groupes = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT G.* FROM GROUPES G INNER JOIN Membres M ON G.gid = M.gid WHERE M.uid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int gid = rs.getInt("gid");
                        int uidAdmin = rs.getInt("uid");
                        int pid = rs.getInt("pid");
                        String nomGrp = rs.getString("nomGrp");
                        String description = rs.getString("description");
                        LocalDateTime dcreat = BAO.conversion(rs.getTimestamp("dcreat"));
                        groupes.add(new Groupe(gid, uidAdmin, pid, nomGrp, description, dcreat));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupes;
    }

    public List<User> getUserFollows(int uid){
        List<User> followers = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT U.* FROM Abonnements A JOIN Users U ON A.uidAbonnement = U.uid WHERE A.uidAbonne = ? ORDER BY A.dabonnement DESC";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
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
                        followers.add(new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, sexe, tel, langue, admin));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return followers;
    }

    public List<User> getUserFollowers(int uid){
        List<User> followers = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT U.* FROM Abonnements A JOIN Users U ON A.uidAbonne = U.uid WHERE A.uidAbonnement = ? ORDER BY A.dabonnement DESC";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
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
                        followers.add(new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, sexe, tel, langue, admin));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return followers;
    }
    
    public List<Favori> getUserFavoris(int uid){
        List<Favori> favoris = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT * FROM Favoris WHERE uid = ? ORDER BY dfavori DESC";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int pid = rs.getInt("pid");
                        LocalDateTime dfavori = BAO.conversion(rs.getTimestamp("dfavori"));
                        favoris.add(new Favori(uid, pid, dfavori));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favoris;
    }

    public List<Conversation> getUserConversations(int uid){
        List<Conversation> conversations = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT DISTINCT C.* FROM Conversations C WHERE C.uidEnvoyeur = ? OR C.uidReceveur = ? ORDER BY C.cid DESC";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                pstmt.setInt(2, uid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int cid = rs.getInt("cid");
                        int uidEnvoyeur = rs.getInt("uidEnvoyeur");
                        int uidReceveur = rs.getInt("uidReceveur");
                        conversations.add(new Conversation(cid, uidEnvoyeur, uidReceveur));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conversations;
    }


    public List<Post> getUsersPosts(int uid, boolean tri) {
        List<Post> posts = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "";
            if(tri){
                requetePrepare = "SELECT * FROM Posts WHERE uid = ? ORDER BY date_pub DESC";
            } else {
                requetePrepare = "SELECT * FROM Posts WHERE uid = ? ORDER BY nb_reactions DESC";
            }
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int pid = rs.getInt("pid");
                        Integer gid = rs.getInt("gid");
                        Integer pidParent = rs.getInt("pidParent");
                        String contenu = rs.getString("contenu");
                        String media = rs.getString("media");
                        LocalDateTime dpub = BAO.conversion(rs.getTimestamp("dpub"));
                        Duration dureePost = BAO.conversionIntervalToDuration(rs.getString("dureePost"));
                        posts.add(new Post(pid, uid, gid, pidParent, contenu, media, dpub, dureePost));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }
}