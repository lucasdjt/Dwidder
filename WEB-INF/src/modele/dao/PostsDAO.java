package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import utils.*;

public class PostsDAO {
    private DS ds;
    
    public PostsDAO(){
        ds = DSFactory.newDS();
    }

    /**
     * Récupère tous les posts de la base de données.
     * @return Liste des posts.
     */
    public List<Post> selectAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Posts";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int pid = rs.getInt("pid");
                    int uid = rs.getInt("uid");
                    Integer gid = rs.getInt("gid");
                    Integer pidParent = rs.getInt("pidParent");
                    String contenu = rs.getString("contenu");
                    String media = rs.getString("media");
                    LocalDateTime dpub = BAO.conversion(rs.getTimestamp("dpub"));
                    Duration dureePost = BAO.conversionIntervalToDuration(rs.getString("dureePost"));
                    posts.add(new Post(pid, uid, gid, pidParent, contenu, media, dpub, dureePost));
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
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Posts (uid, gid, pidParent, contenu, media, dpub, dureePost) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, post.getUid());
                if (post.getGid() != null) pstmt.setInt(2, post.getGid());
                else pstmt.setNull(2, java.sql.Types.INTEGER);
                if (post.getPidParent() != null) pstmt.setInt(3, post.getPidParent());
                else pstmt.setNull(3, java.sql.Types.INTEGER);
                pstmt.setString(4, post.getContenu());
                pstmt.setString(5, post.getMedia());
                pstmt.setTimestamp(6, BAO.conversion(post.getDpub()));
                pstmt.setObject(7, BAO.conversionDurationToInterval(post.getDureePost()));
                pstmt.executeUpdate();
            }
        }
    }

     /**
     * Recherche un post par son identifiant.
     * @param pid L'ID du post.
     * @return Le post trouvé ou null si inexistant.
     */
    public Post findByPid(int pid) {
        Post post = null;
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT * FROM Posts WHERE pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, pid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int uid = rs.getInt("uid");
                        Integer gid = rs.getInt("gid");
                        Integer pidParent = rs.getInt("pidParent");
                        String contenu = rs.getString("contenu");
                        String media = rs.getString("media");
                        LocalDateTime dpub = BAO.conversion(rs.getTimestamp("dpub"));
                        Duration dureePost = BAO.conversionIntervalToDuration(rs.getString("dureePost"));
                        post = new Post(pid, uid, gid, pidParent, contenu, media, dpub, dureePost);
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
    public void delete(Post post) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Posts WHERE pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, post.getPid());
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
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT * FROM Reactions WHERE pid = ? ORDER BY dreact DESC";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, pid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int uid = rs.getInt("uid");
                        String type = rs.getString("type");
                        LocalDateTime dreact = BAO.conversion(rs.getTimestamp("dreact"));
                        reactions.add(new Reaction(uid, pid, type, dreact));
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
    public List<Post> selectAllPublic(boolean triParDate) {
        List<Post> posts = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "";
            if (triParDate) {
                requete = "SELECT * FROM Posts WHERE gid IS NULL AND pidParent IS NULL ORDER BY dpub DESC";
            } else {
                requete = "SELECT P.*, (SELECT COUNT(*) FROM Reactions R WHERE R.pid = P.pid) AS nbReact FROM Posts P WHERE P.gid IS NULL AND P.pidParent IS NULL ORDER BY nbReact DESC";
            }
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int pid = rs.getInt("pid");
                    int uid = rs.getInt("uid");
                    Integer gid = rs.getInt("gid");
                    Integer pidParent = rs.getInt("pidParent");
                    String contenu = rs.getString("contenu");
                    String media = rs.getString("media");
                    LocalDateTime dpub = BAO.conversion(rs.getTimestamp("dpub"));
                    Duration dureePost = BAO.conversionIntervalToDuration(rs.getString("dureePost"));
                    posts.add(new Post(pid, uid, gid, pidParent, contenu, media, dpub, dureePost));
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
    public List<Post> selectFromGroup(int gid, boolean triParDate) {
        List<Post> posts = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "";
            if (triParDate) {
                requetePrepare = "SELECT * FROM Posts WHERE gid = ? AND pidParent IS NULL ORDER BY dpub DESC";
            } else {
                requetePrepare = "SELECT P.*, (SELECT COUNT(*) FROM Reactions R WHERE R.pid = P.pid) AS nbReact FROM Posts P WHERE P.gid = ? ORDER BY nbReact DESC";
            }
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, gid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int pid = rs.getInt("pid");
                        Integer uid = rs.getInt("uid");
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

    /**
     * Récupère les réponses à un post parent, triées par popularité.
     * @param pidParent L'ID du post parent.
     * @return Liste des réponses associées.
     */
    public List<Post> selectFromPostParent(int pidParent) {
        List<Post> posts = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT P.*, (SELECT COUNT(*) FROM Reactions R WHERE R.pid = P.pid) AS nbReact FROM Posts P WHERE P.pidParent = ? ORDER BY nbReact DESC";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, pidParent);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int pid = rs.getInt("pid");
                        Integer uid = rs.getInt("uid");
                        Integer gid = rs.getInt("gid");
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