package modele.dao;

import java.sql.*;
import java.util.*;

import modele.dto.*;
import utils.*;

public class ReactionsDAO {

    /**
     * Récupère toutes les réactions de la base de données.
     * @return Liste des réactions.
     */
    public List<Reaction> selectAll() {
        List<Reaction> reactions = new ArrayList<>();
        try (Connection con = DS.getConnection()) {
            String requete = "SELECT * FROM Reactions";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(requete)) {
                while (rs.next()) {
                    int uid = rs.getInt("uid");
                    int pid = rs.getInt("pid");
                    String type = rs.getString("type");
                    reactions.add(new Reaction(uid, pid, type));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reactions;
    }

    /**
     * Insère une nouvelle réaction dans la base de données.
     * @param reaction La réaction à ajouter.
     */
    public void insert(Reaction reaction) {
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "INSERT INTO Reactions (uid, pid, type) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, reaction.getUid());
                pstmt.setInt(2, reaction.getPid());
                pstmt.setString(3, reaction.getType());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour le type d'une réaction existante.
     * @param reaction La réaction mise à jour.
     */
    public void update(Reaction reaction) {
        try (Connection con = DS.getConnection()) {
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
    
    /**
     * Supprime une réaction spécifique de la base de données.
     * @param reaction La réaction à supprimer.
     */
    public void delete(Reaction reaction) {
        try (Connection con = DS.getConnection()) {
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

    /**
     * Trouve une réaction spécifique à partir du uid et pid.
     * @param uid L'identifiant de l'utilisateur.
     * @param pid L'identifiant du post.
     * @return La réaction trouvée ou null si aucune réaction n'est trouvée.
     */
    public Reaction findByUidAndPid(int uid, int pid) {
        Reaction reaction = null;
        try (Connection con = DS.getConnection()) {
            String requetePrepare = "SELECT * FROM Reactions WHERE uid = ? AND pid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(requetePrepare)) {
                pstmt.setInt(1, uid);
                pstmt.setInt(2, pid);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String type = rs.getString("type");
                        reaction = new Reaction(uid, pid, type);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reaction;
    }
}