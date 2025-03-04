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

    public void insert(Post post) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Posts (uid, gid, pidParent, contenu, media, dpub, dureePost) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, post.getUid());
                pstmt.setInt(2, post.getGid());
                pstmt.setInt(3, post.getPidParent());
                pstmt.setString(4, post.getContenu());
                pstmt.setString(5, post.getMedia());
                pstmt.setTimestamp(6, BAO.conversion(post.getDpub()));
                pstmt.setString(7, BAO.conversionDurationToInterval(post.getDureePost()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public List<Reaction> getPostReactions(int pid) {
        List<Reaction> reactions = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "SELECT * FROM Reactions WHERE pid = 1 ORDER BY dreact DESC";
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

    public List<Post> selectAllPublic(boolean triParReactions) {
        List<Post> posts = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "";
            if (triParReactions) {
                requete = "SELECT P.*, (SELECT COUNT(*) FROM Reactions R WHERE R.pid = P.pid) AS nbReact FROM Posts P WHERE P.gid IS NULL AND P.pidParent IS NULL ORDER BY nbReact DESC";
            } else {
                requete = "SELECT * FROM Posts WHERE gid IS NULL AND pidParent IS NULL ORDER BY P.dpub DESC";
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

    public List<Post> selectFromGroup(int gid, boolean triParReactions) {
        List<Post> posts = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "";
            if (triParReactions) {
                requetePrepare = "SELECT P.*, (SELECT COUNT(*) FROM Reactions R WHERE R.pid = P.pid) AS nbReact FROM Posts P WHERE P.gid = ? ORDER BY nbReact DESC";
            } else {
                requetePrepare = "SELECT * FROM Posts WHERE gid = ? AND pidParent IS NULL ORDER BY P.dpub DESC";
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