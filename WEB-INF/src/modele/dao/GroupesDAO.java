package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import modele.utils.*;

public class GroupesDAO {
    DS ds = new DataIUT();

    public List<Groupe> selectAll() {
        List<Groupe> groupes = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Groupes";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int gid = rs.getInt("gid");
                    int uid = rs.getInt("uid");
                    int pid = rs.getInt("pid");
                    String nomGrp = rs.getString("nomGrp");
                    String description = rs.getString("description");
                    LocalDateTime dcreat = BAO.conversion(rs.getTimestamp("dcreat"));
                    groupes.add(new Groupe(gid, uid, pid, nomGrp, description, dcreat));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupes;
    }

    public void insert(Groupe groupe) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Groupes (uid, pid, nomGrp, description, dcreat) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getUid());
                pstmt.setInt(2, groupe.getPid());
                pstmt.setString(3, groupe.getNomGrp());
                pstmt.setString(4, groupe.getDescription());
                pstmt.setTimestamp(4, BAO.conversion(groupe.getDcreat()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Groupe groupe) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "UPDATE Groupes SET uid = ?, pid = ?, nomGrp = ?, description = ? WHERE gid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getUid());
                pstmt.setInt(2, groupe.getPid());
                pstmt.setString(3, groupe.getNomGrp());
                pstmt.setString(4, groupe.getDescription());
                pstmt.setInt(5, groupe.getGid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Groupe groupe) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Groupes WHERE gid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, groupe.getGid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Membre> getGroupeMembers(int gid){
        return null;
        /*
SELECT * FROM GroupMembers WHERE gid = 1 ORDER BY date_join DESC; -- obtenir la liste des membres d un groupe trié par date
SELECT COUNT(*) FROM GroupMembers WHERE gid = 1; -- le nombre de membres d'un groupe
        */
    }

    public Post getGroupePost(int pid){
        return null;
        /*
SELECT P.* FROM Posts P JOIN Groupes G ON P.pid = G.pid_epingle WHERE G.gid = 1; -- post épinglé
        */
    }

    public Post getGroupeAdmin(int uid){
        return null;
        /*
SELECT U.* FROM Users U JOIN Groupes G ON U.uid = G.uid_admin WHERE G.gid = 1; -- l'admin du groupe
        */
    }
}