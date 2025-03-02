package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import modele.utils.*;

public class PostsDAO {
    DS ds = new DataIUT();

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
                    Integer pidParent = rs.getInt("pid_parent");
                    String contenu = rs.getString("contenu");
                    String media = rs.getString("media");
                    LocalDateTime datePub = BAO.conversion(rs.getTimestamp("date_pub"));
                    Duration dureePost = BAO.conversionIntervalToDuration(rs.getString("duree_post"));
                    posts.add(new Post(pid, uid, gid, pidParent, contenu, media, datePub, dureePost));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public void insert(Post post) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Posts (uid, gid, pid_parent, contenu, media, date_pub, duree_post) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, post.getUid());
                pstmt.setInt(2, post.getGid());
                pstmt.setInt(3, post.getPidParent());
                pstmt.setString(4, post.getContenu());
                pstmt.setString(5, post.getMedia());
                pstmt.setTimestamp(6, BAO.conversion(post.getDatePub()));
                pstmt.setString(7, BAO.conversionDurationToInterval(post.getDureePost()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    /*
- Les infos complet d un post
SELECT * FROM PostDetails WHERE pid = 1;

- On peut obtenir la liste des posts
SELECT * FROM PostDetails WHERE uid = 1 ORDER BY date_pub DESC; -- trié par date
SELECT * FROM PostDetails WHERE uid = 1 ORDER BY nb_reactions DESC; -- trié par réactions
SELECT COUNT(*) FROM PostDetails WHERE uid = 1; -- le nombre de posts

- On peut obtenir le nombre de favoris et de réactions sur un post
SELECT nb_favoris FROM PostDetails WHERE pid = 1;
SELECT nb_reactions FROM PostDetails WHERE pid = 1;

- On peut obtenir le liste des réactions sur un post avec les infos de ses réactions
SELECT * FROM PostReactions WHERE pid = 1 ORDER BY date_react DESC;

- Du fil public sans parent/groupe
SELECT * FROM PostDetails WHERE gid IS NULL AND pid_parent IS NULL ORDER BY date_pub DESC; -- trié par date
SELECT * FROM PostDetails WHERE gid IS NULL AND pid_parent IS NULL ORDER BY nb_reactions, nb_favoris DESC; -- trié par réactions
- D un groupe particulier sans parent
SELECT * FROM PostDetails WHERE gid = 1 AND pid_parent IS NULL ORDER BY date_pub DESC; -- trié par date
SELECT * FROM PostDetails WHERE gid = 1 AND pid_parent IS NULL ORDER BY nb_reactions, nb_favoris DESC; -- trié par réactions
SELECT COUNT(*) FROM PostDetails WHERE gid = 1; -- nombre de posts du groupe
- Réponses d un post particulier trié par réaction
SELECT * FROM PostDetails WHERE pid_parent = 1 ORDER BY nb_reactions DESC;

     */
}