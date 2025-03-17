package modele.test;

import modele.dao.PostsDAO;
import modele.dto.Post;
import modele.dto.PostDetails;
import java.time.LocalDateTime;

public class TestRecup {
    public static void main(String args[]) throws Exception {
        PostsDAO postsDAO = new PostsDAO();
        LocalDateTime dpub = LocalDateTime.now().plusDays(10);
        LocalDateTime dfin = LocalDateTime.now();
        postsDAO.insert(new Post(0, 1, null, null, "testAncienPost2", null, dpub, dfin));
        for(PostDetails p : postsDAO.selectAll()) {
            System.out.println(p);
        }
    }
}
