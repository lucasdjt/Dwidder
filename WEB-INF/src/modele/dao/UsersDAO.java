package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dto.Favori;
import modele.dto.Groupe;
import modele.dto.Message;
import modele.dto.User;
import modele.dto.PostDetails;
import modele.dto.Reaction;
import utils.BAO;
import utils.DS;

public class UsersDAO {

    public List<User> listUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
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
                    boolean admin = rs.getBoolean("admin");
                    users.add(new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, admin));
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la sélection de tous les utilisateurs");
            e.printStackTrace();
        }
        return users;
    }

    public User findUserByPseudo(String idPseudo) {
        User user = null;
        try (Connection con = DS.getConnection()) {
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
                        boolean admin = rs.getBoolean("admin");
                        user = new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, admin);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la recherche d'un utilisateur par son idPseudo");
            e.printStackTrace();
        }
        return user;
    }

    public User findUserByUid(int uid) {
        User user = null;
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT * FROM Users WHERE uid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
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
                        user = new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, admin);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la recherche d'un utilisateur par son uid");
            e.printStackTrace();
        }
        return user;
    }

    public User findUserByEmail(String email) {
        User user = null;
        try (Connection con = DS.getConnection()) {
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
                        boolean admin = rs.getBoolean("admin");
                        user = new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, admin);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la recherche d'un utilisateur par son email");
            e.printStackTrace();
        }
        return user;
    }

    public void insert(User user) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "INSERT INTO Users (idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, user.getIdPseudo());
                pstmt.setString(2, user.getHTMLPseudo());
                pstmt.setString(3, user.getHTMLPrenom());
                pstmt.setString(4, user.getHTMLNomUser());
                pstmt.setString(5, user.getHTMLEmail());
                pstmt.setString(6, user.getHTMLMdp());
                pstmt.setString(7, user.getHTMLBio());
                pstmt.setString(8, user.getPdp());
                pstmt.setTimestamp(9, BAO.conversion(user.getDinsc()));
                pstmt.setDate(10, BAO.conversion(user.getDnaiss()));
                pstmt.setString(11, user.getHTMLLoca());
                pstmt.setBoolean(12, user.isAdmin());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'insertion d'un utilisateur");
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "UPDATE Users SET idPseudo = ?, pseudo = ?, prenom = ?, nomUser = ?, email = ?, mdp = ?, bio = ?, pdp = ?, dnaiss = ?, loca = ? WHERE uid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, user.getIdPseudo());
                pstmt.setString(2, user.getHTMLPseudo());
                pstmt.setString(3, user.getHTMLPrenom());
                pstmt.setString(4, user.getHTMLNomUser());
                pstmt.setString(5, user.getHTMLEmail());
                pstmt.setString(6, user.getHTMLMdp());
                pstmt.setString(7, user.getHTMLBio());
                pstmt.setString(8, user.getPdp());
                pstmt.setDate(9, BAO.conversion(user.getDnaiss()));
                pstmt.setString(10, user.getHTMLLoca());
                pstmt.setInt(11, user.getUid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la modification d'un utilisateur");
            e.printStackTrace();
        }
    }

    public void delete(String idPseudo) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "DELETE FROM Users WHERE idPseudo = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, idPseudo);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la suppression d'un utilisateur");
            e.printStackTrace();
        }
    }

    public List<Groupe> getUserGroups(int uid) {
        List<Groupe> groupes = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT G.* FROM GROUPES G INNER JOIN Membres M ON G.gid = M.gid WHERE M.uid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int gid = rs.getInt("gid");
                        int uidAdmin = rs.getInt("uid");
                        String pdpGrp = rs.getString("pdpGrp");
                        String nomGrp = rs.getString("nomGrp");
                        String description = rs.getString("description");
                        LocalDateTime dcreat = BAO.conversion(rs.getTimestamp("dcreat"));
                        groupes.add(new Groupe(gid, uidAdmin, pdpGrp, nomGrp, description, dcreat));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des groupes d'un utilisateur");
            e.printStackTrace();
        }
        return groupes;
    }

    public List<User> getListFollowsOfUser(int uidChercher){
        List<User> followers = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT U.* FROM Abonnements A JOIN Users U ON A.uidAbonnement = U.uid WHERE A.uidAbonne = ? ORDER BY A.dabonnement DESC";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uidChercher);
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
                        followers.add(new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, admin));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des abonnements d'un utilisateur");
            e.printStackTrace();
        }
        return followers;
    }

    public List<User> getListFollowersOfUser(int uidChercher){
        List<User> followers = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT U.* FROM Abonnements A JOIN Users U ON A.uidAbonne = U.uid WHERE A.uidAbonnement = ? ORDER BY A.dabonnement DESC";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uidChercher);
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
                        followers.add(new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, admin));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des abonnés d'un utilisateur");
            e.printStackTrace();
        }
        return followers;
    }
    
    public List<Favori> getListFavorisOfUser(int uid){
        List<Favori> favoris = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
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
            System.err.println("Erreur dans l'obtention de la liste des favoris d'un utilisateur");
            e.printStackTrace();
        }
        return favoris;
    }

    public List<Message> getListMessagesOf2Users(int uid1, int uid2) {
        List<Message> mess = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT * FROM Messages WHERE (uidEnvoyeur = ? AND uidReceveur = ?) OR (uidEnvoyeur = ? AND uidReceveur = ?) ORDER BY dmess";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid1);
                pstmt.setInt(2, uid2);
                pstmt.setInt(3, uid2);
                pstmt.setInt(4, uid1);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int uidEnvoyeur = rs.getInt("uidEnvoyeur");
                        int uidReceveur = rs.getInt("uidReceveur");
                        String corps = rs.getString("corps");
                        String imgMess = rs.getString("imgMess");
                        LocalDateTime dmess = BAO.conversion(rs.getTimestamp("dmess"));
                        mess.add(new Message(0, uidEnvoyeur, uidReceveur, corps, imgMess, dmess));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des messages entre 2 utilisateurs");
            e.printStackTrace();
        }
        return mess;
    }

    public List<User> getListConversationsOfUser(int userID) {
        List<User> users = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT DISTINCT U.* FROM Users U " +
                                    "JOIN Messages M ON (U.uid = M.uidEnvoyeur OR U.uid = M.uidReceveur) " +
                                    "WHERE (M.uidEnvoyeur = ? OR M.uidReceveur = ?) AND U.uid != ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, userID);
                pstmt.setInt(2, userID);
                pstmt.setInt(3, userID);
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
                        users.add(new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, admin));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des utilisateurs avec qui l'utilisateur a conversé");
            e.printStackTrace();
        }
        return users;
    }

    public List<PostDetails> getListPostsOfUser(int uid, boolean tri) {
        List<PostDetails> posts = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "";
            if(tri){
                requetePrepare = "SELECT * FROM PostDetails WHERE uid = ? ORDER BY dpub DESC";
            } else {
                requetePrepare = "SELECT * FROM PostDetails WHERE uid = ? ORDER BY nbLikes DESC";
            }
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int pid = rs.getInt("pid");
                        String nomGrp = rs.getString("nomGrp");
                        Integer pidParent = rs.getInt("pidParent");
                        Integer gid = rs.getInt("gid");
                        String contenu = rs.getString("contenu");
                        String media = rs.getString("media");
                        LocalDateTime dpub = BAO.conversion(rs.getTimestamp("dpub"));
                        LocalDateTime dfin = BAO.conversion(rs.getTimestamp("dfin"));
                        String pdp = rs.getString("pdp");
                        String pseudo = rs.getString("pseudo");
                        int uidAdmin = rs.getInt("uidAdmin");
                        int nbLikes = rs.getInt("nbLikes");
                        int nbComm = rs.getInt("nbComm");
                        String idPseudo = rs.getString("idPseudo");
                        posts.add(new PostDetails(pid, gid, nomGrp, pidParent, contenu, media, dpub, dfin, pdp, pseudo, uid, uidAdmin, nbLikes, nbComm, idPseudo));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des posts d'un utilisateur");
            e.printStackTrace();
        }
        return posts;
    }

    public List<Reaction> getListReactionsOfUser(int uid) {
        List<Reaction> reaction = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT * FROM Reactions WHERE uid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int pid = rs.getInt("pid");
                        String type = rs.getString("type");
                        reaction.add(new Reaction(uid, pid, type));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des réactions d'un utilisateur");
            e.printStackTrace();
        }
        return reaction;
    }

    public List<User> getListUsersWithEquivalentKey(String keyword) {
        List<User> users = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT * FROM Users WHERE LOWER(idPseudo) LIKE LOWER(?) OR LOWER(pseudo) LIKE LOWER(?) OR LOWER(bio) LIKE LOWER(?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                String searchPattern = "%" + keyword + "%";
                pstmt.setString(1, searchPattern);
                pstmt.setString(2, searchPattern);
                pstmt.setString(3, searchPattern);
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
                        users.add(new User(uid, idPseudo, pseudo, prenom, nomUser, email, mdp, bio, pdp, dinsc, dnaiss, loca, admin));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la recherche d'utilisateurs avec un mot équivalent");
            e.printStackTrace();
        }
        return users;
    }
}