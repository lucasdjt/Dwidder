package modele.dao;

import java.time.*;
import java.sql.*;
import java.util.*;

import modele.dto.*;
import modele.utils.*;

public class ReactionsDAO {
    DS ds = new DataIUT();

    public List<Reaction> selectAll() {
        List<Reaction> reactions = new ArrayList<>();
        try (Connection con = ds.getConnection()) {
            String requete = "SELECT * FROM Reactions";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int uid = rs.getInt("uid");
                    int pid = rs.getInt("pid");
                    String type = rs.getString("type");
                    LocalDateTime dreact = BAO.conversion(rs.getTimestamp("dreact"));
                    reactions.add(new Reaction(uid, pid, type, dreact));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reactions;
    }

    public void insert(Reaction reaction) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "INSERT INTO Reactions (uid, pid, type, dreact) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, reaction.getUid());
                pstmt.setInt(2, reaction.getPid());
                pstmt.setString(3, reaction.getType());
                pstmt.setTimestamp(4, BAO.conversion(reaction.getDreact()));
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Reaction reaction) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "UPDATE Reactions SET type = ? WHERE uid = ? AND pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setString(1, reaction.getType());
                pstmt.setInt(2, reaction.getUid());
                pstmt.setInt(3, reaction.getPid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Reaction reaction) {
        try (Connection con = ds.getConnection()) {
            String requetePrepare = "DELETE FROM Reactions WHERE uid = ? AND pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, reaction.getUid());
                pstmt.setInt(2, reaction.getPid());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}