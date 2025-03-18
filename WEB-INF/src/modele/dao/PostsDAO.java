package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dto.Post;
import modele.dto.PostDetails;
import modele.dto.Reaction;
import utils.BAO;
import utils.DS;

public class PostsDAO {

    public void insert(Post post) { 
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "INSERT INTO Posts (uid, gid, pidParent, contenu, media, dpub, dfin) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, post.getUid());
                if (post.getGid() != null) pstmt.setInt(2, post.getGid());
                else pstmt.setNull(2, java.sql.Types.INTEGER);
                if (post.getPidParent() != null) pstmt.setInt(3, post.getPidParent());
                else pstmt.setNull(3, java.sql.Types.INTEGER);
                pstmt.setString(4, post.getHTMLContenu());
                pstmt.setString(5, post.getMedia());
                pstmt.setTimestamp(6, BAO.conversion(post.getDpub()));
                pstmt.setTimestamp(7, BAO.conversion(post.getDfin()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'insertion d'un post");
            e.printStackTrace();
        }
    }

    public PostDetails findPostDetails(int pid) {
        PostDetails post = null;
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT * FROM PostDetails WHERE pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, pid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Integer gid = rs.getInt("gid");
                        String nomGrp = rs.getString("nomGrp");
                        Integer pidParent = rs.getInt("pidParent");
                        String contenu = rs.getString("contenu");
                        String media = rs.getString("media");
                        LocalDateTime dpub = BAO.conversion(rs.getTimestamp("dpub"));
                        LocalDateTime dfin = BAO.conversion(rs.getTimestamp("dfin"));
                        String pdp = rs.getString("pdp");
                        String pseudo = rs.getString("pseudo");
                        int uid = rs.getInt("uid");
                        int uidAdmin = rs.getInt("uidAdmin");
                        int nbLikes = rs.getInt("nbLikes");
                        int nbComm = rs.getInt("nbComm");
                        String idPseudo = rs.getString("idPseudo");
                        post = new PostDetails(pid, gid, nomGrp, pidParent, contenu, media, dpub, dfin, pdp, pseudo, uid, uidAdmin, nbLikes, nbComm, idPseudo);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la recherche des détails d'un post");
            e.printStackTrace();
        }
        return post;
    }

    public void delete(int pid) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "DELETE FROM Posts WHERE pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, pid);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la suppression d'un post");
            e.printStackTrace();
        }
    }

    public List<Reaction> getListReactionsOfPost(int pid) {
        List<Reaction> reactions = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT * FROM Reactions WHERE pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, pid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int uid = rs.getInt("uid");
                        String type = rs.getString("type");
                        reactions.add(new Reaction(uid, pid, type));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des réactions d'un post");
            e.printStackTrace();
        }
        return reactions;
    }

    public List<PostDetails> getListPostsInPublic(boolean triParDate) {
        List<PostDetails> posts = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requete = "";
            if (triParDate) {
                requete = "SELECT * FROM PostDetails WHERE gid IS NULL AND pidParent IS NULL ORDER BY dpub DESC";
            } else {
                requete = "SELECT * FROM PostDetails WHERE gid IS NULL AND pidParent IS NULL ORDER BY nbLikes DESC";
            }
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int pid = rs.getInt("pid");
                    Integer gid = rs.getInt("gid");
                    String nomGrp = rs.getString("nomGrp");
                    Integer pidParent = rs.getInt("pidParent");
                    String contenu = rs.getString("contenu");
                    String media = rs.getString("media");
                    LocalDateTime dpub = BAO.conversion(rs.getTimestamp("dpub"));
                    LocalDateTime dfin = BAO.conversion(rs.getTimestamp("dfin"));
                    String pdp = rs.getString("pdp");
                    String pseudo = rs.getString("pseudo");
                    int uid = rs.getInt("uid");
                    int uidAdmin = rs.getInt("uidAdmin");
                    int nbLikes = rs.getInt("nbLikes");
                    int nbComm = rs.getInt("nbComm");
                    String idPseudo = rs.getString("idPseudo");
                    posts.add(new PostDetails(pid, gid, nomGrp, pidParent, contenu, media, dpub, dfin, pdp, pseudo, uid, uidAdmin, nbLikes, nbComm, idPseudo));       
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des posts dans le fil public");
            e.printStackTrace();
        }
        return posts;
    }

    public List<PostDetails> getListPostsOfGroup(int gid, boolean triParDate) {
        List<PostDetails> posts = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "";
            if (triParDate) {
                requetePrepare = "SELECT * FROM PostDetails WHERE gid = ? AND pidParent IS NULL ORDER BY dpub DESC";
            } else {
                requetePrepare = "SELECT * FROM PostDetails WHERE gid = ? ORDER BY nbLikes DESC";
            }
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, gid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int pid = rs.getInt("pid");
                        String nomGrp = rs.getString("nomGrp");
                        Integer pidParent = rs.getInt("pidParent");
                        String contenu = rs.getString("contenu");
                        String media = rs.getString("media");
                        LocalDateTime dpub = BAO.conversion(rs.getTimestamp("dpub"));
                        LocalDateTime dfin = BAO.conversion(rs.getTimestamp("dfin"));
                        String pdp = rs.getString("pdp");
                        String pseudo = rs.getString("pseudo");
                        int uid = rs.getInt("uid");
                        int uidAdmin = rs.getInt("uidAdmin");
                        int nbLikes = rs.getInt("nbLikes");
                        int nbComm = rs.getInt("nbComm");
                        String idPseudo = rs.getString("idPseudo");
                        posts.add(new PostDetails(pid, gid, nomGrp, pidParent, contenu, media, dpub, dfin, pdp, pseudo, uid, uidAdmin, nbLikes, nbComm, idPseudo));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des posts dans le fil de groupe");
            e.printStackTrace();
        }
        return posts;
    }

    public List<PostDetails> getListPostsOfPostParent(int pidParent) {
        List<PostDetails> posts = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT * FROM PostDetails WHERE pidParent = ? ORDER BY nbLikes DESC";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, pidParent);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int pid = rs.getInt("pid");
                        Integer gid = rs.getInt("gid");
                        String nomGrp = rs.getString("nomGrp");
                        String contenu = rs.getString("contenu");
                        String media = rs.getString("media");
                        LocalDateTime dpub = BAO.conversion(rs.getTimestamp("dpub"));
                        LocalDateTime dfin = BAO.conversion(rs.getTimestamp("dfin"));
                        String pdp = rs.getString("pdp");
                        String pseudo = rs.getString("pseudo");
                        int uid = rs.getInt("uid");
                        int uidAdmin = rs.getInt("uidAdmin");
                        int nbLikes = rs.getInt("nbLikes");
                        int nbComm = rs.getInt("nbComm");
                        String idPseudo = rs.getString("idPseudo");
                            posts.add(new PostDetails(pid, gid, nomGrp, pidParent, contenu, media, dpub, dfin, pdp, pseudo, uid, uidAdmin, nbLikes, nbComm, idPseudo));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans l'obtention de la liste des posts dans le fil des réponses à un post");
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Supprime les posts expirés de la base de données.
     */
    public void deleteAllExpiredPosts() {
        try (Connection con = DS.getConnection()) {
            String requete = "DELETE FROM Posts WHERE dfin IS NOT NULL AND dfin < CURRENT_TIMESTAMP";
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate(requete);
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la suppression des posts expirés");
            e.printStackTrace();
        }
    }
}