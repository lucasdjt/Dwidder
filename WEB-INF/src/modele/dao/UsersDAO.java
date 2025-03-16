package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import utils.*;

public class UsersDAO {

    /**
     * Récupère tous les utilisateurs de la base de données.
     * @return Liste des utilisateurs.
     */
    public List<User> selectAll() {
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
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Recherche un utilisateur par son identifiant de pseudo.
     * @param idPseudo Identifiant du pseudo à rechercher.
     * @return L'utilisateur correspondant à l'idPseudo, ou null si non trouvé.
     */
    public User findByIdPseudo(String idPseudo) {
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
            e.printStackTrace();
        }
        return user;
    }

        /**
     * Recherche un utilisateur par son identifiant de pseudo.
     * @param idPseudo Identifiant du pseudo à rechercher.
     * @return L'utilisateur correspondant à l'idPseudo, ou null si non trouvé.
     */
    public User findByUid(int uid) {
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
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Recherche un utilisateur par son adresse email.
     * @param email Adresse email de l'utilisateur.
     * @return L'utilisateur correspondant à l'email, ou null si non trouvé.
     */
    public User findByEmail(String email) {
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
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Insère un nouvel utilisateur dans la base de données.
     * @param user L'utilisateur à insérer.
     */
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
            e.printStackTrace();
        }
    }

    /**
     * Met à jour les informations d'un utilisateur dans la base de données.
     * @param user L'utilisateur avec les informations mises à jour.
     */
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
            e.printStackTrace();
        }
    }

    /**
     * Supprime un utilisateur de la base de données par son identifiant de pseudo.
     * @param idPseudo Identifiant du pseudo à supprimer.
     */
    public void delete(String idPseudo) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "DELETE FROM Users WHERE idPseudo = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, idPseudo);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère les groupes d'un utilisateur à partir de son identifiant.
     * @param uid Identifiant de l'utilisateur.
     * @return Liste des groupes auxquels l'utilisateur appartient.
     */
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
            e.printStackTrace();
        }
        return groupes;
    }

    /**
     * Récupère les utilisateurs suivis par un utilisateur.
     * @param uid Identifiant de l'utilisateur.
     * @return Liste des utilisateurs suivis.
     */
    public List<User> getUserFollows(int uidChercher){
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
            e.printStackTrace();
        }
        return followers;
    }

    /**
     * Récupère les utilisateurs qui suivent un utilisateur.
     * @param uid Identifiant de l'utilisateur.
     * @return Liste des utilisateurs qui suivent l'utilisateur.
     */
    public List<User> getUserFollowers(int uidChercher){
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
            e.printStackTrace();
        }
        return followers;
    }
    
    /**
     * Récupère les favoris d'un utilisateur.
     * @param uid Identifiant de l'utilisateur.
     * @return Liste des favoris de l'utilisateur.
     */
    public List<Favori> getUserFavoris(int uid){
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
            e.printStackTrace();
        }
        return favoris;
    }

    /**
     * Récupère les conversations entre deux utilisateurs.
     * @param uid1 Identifiant du premier utilisateur.
     * @param uid2 Identifiant du second utilisateur.
     * @return Liste des messages échangés entre les deux utilisateurs.
     */
    public List<Message> getUserMessages(int uid1, int uid2) {
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
            e.printStackTrace();
        }
        return mess;
    }

    /**
     * Récupère les utilisateurs avec lesquels un utilisateur a échangé des messages.
     * @param uid Identifiant de l'utilisateur.
     * @return Liste des utilisateurs avec lesquels des messages ont été échangés.
     */
    public List<User> getUsersWithMessages(int userID) {
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
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Récupère les posts d'un utilisateur avec un tri spécifique.
     * @param uid Identifiant de l'utilisateur.
     * @param tri Indicateur de tri (true pour tri par date de publication, false pour tri par nombre de réactions).
     * @return Liste des posts de l'utilisateur.
     */
    public List<PostDetails> getUsersPosts(int uid, boolean tri) {
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
                        long duree = (dfin != null) ? Duration.between(dpub, dfin).toHours() : 720;
                        String pdp = rs.getString("pdp");
                        String pseudo = rs.getString("pseudo");
                        int nbLikes = rs.getInt("nbLikes");
                        int nbComm = rs.getInt("nbComm");
                        String idPseudo = rs.getString("idPseudo");
                        posts.add(new PostDetails(pid, gid, nomGrp, pidParent, contenu, media, dpub, dfin, duree, pdp, pseudo, uid, nbLikes, nbComm, idPseudo));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }
}