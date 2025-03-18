package modele.test;

import java.sql.*;
import java.util.List;
import modele.dao.PostsDAO;
import modele.dto.PostDetails;
import utils.*;

public class Test {

    public static void BDD(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DS.getConnection();
            con.close();
            System.out.println("Tout fonctionne");
        } catch (Exception e) {
            System.out.println("Erreur de connexion");
            e.printStackTrace();
        }
    }

    public static void testJson(){
        try {
            PostsDAO pDao = new PostsDAO();
            List<PostDetails> monObjet = pDao.getListPostsInPublic(true);
            String json = "";
            for (PostDetails p : monObjet) {
                json += BAO.toJson(p) + ",\n";
            }
            if (json.endsWith(",\n")) {
                json = json.substring(0, json.length() - 2);
            }
            System.out.println("JSON généré : " + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        BDD();
    }
}