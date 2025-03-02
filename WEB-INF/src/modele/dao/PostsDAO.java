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
}