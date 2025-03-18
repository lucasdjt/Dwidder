package modele.test;

import modele.dao.PostsDAO;
import modele.dto.PostDetails;
import utils.BAO;

import java.util.List;

public class TestRecup {
    
    public static void main(String[] args) {
        try {
            PostsDAO pDao = new PostsDAO();
            List<PostDetails> monObjet = pDao.selectAll();
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
}
