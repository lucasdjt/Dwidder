package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import utils.*;

public class PostsDAO {

    /**
     * Récupère tous les posts de la base de données.
     * @return Liste des posts.
     */
    public List<PostDetails> selectAll() {
        List<PostDetails> posts = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requete = "SELECT * FROM PostDetails";
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
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Insère un nouveau post dans la base de données.
     * @param post Le post à insérer.
     * @throws Exception Si une erreur survient lors de l'insertion. 
     */
    public void insert(Post post) throws Exception { 
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
        }
    }

     /**
     * Recherche un post par son identifiant.
     * @param pid L'ID du post.
     * @return Le post trouvé ou null si inexistant.
     */
    public PostDetails findByPid(int pid) {
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
            e.printStackTrace();
        }
        return post;
    }

    /**
     * Supprime un post de la base de données.
     * @param post Le post à supprimer.
     */
    public void delete(int pid) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "DELETE FROM Posts WHERE pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, pid);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère les réactions associées à un post.
     * @param pid L'ID du post.
     * @return Liste des réactions.
     */
    public List<Reaction> getPostReactions(int pid) {
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
            e.printStackTrace();
        }
        return reactions;
    }

    /**
     * Récupère tous les posts publics, triés par popularité ou par date.
     * @param triParDate Indique si le tri doit être fait par date ou par nombre de réactions.
     * @return Liste des posts publics.
     */
    public List<PostDetails> selectAllPublic(boolean triParDate) {
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
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Récupère les posts d'un groupe spécifique, triés par popularité ou par date.
     * @param gid L'ID du groupe.
     * @param triParReactions Indique si le tri doit être fait par la date.
     * @return Liste des posts du groupe.
     */
    public List<PostDetails> selectFromGroup(int gid, boolean triParDate) {
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
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Récupère les réponses à un post parent, triées par popularité.
     * @param pidParent L'ID du post parent.
     * @return Liste des réponses associées.
     */
    public List<PostDetails> selectFromPostParent(int pidParent) {
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
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Supprime les posts expirés de la base de données.
     */
    public void deleteExpired() {
        try (Connection con = DS.getConnection()) {
            String requete = "DELETE FROM Posts WHERE dfin IS NOT NULL AND dfin < CURRENT_TIMESTAMP";
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate(requete);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}