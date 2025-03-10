package modele.test;

import modele.dao.PostsDAO;
import modele.dto.PostDetails;


public class TestRecup {
    private static final String rouge = "\u001B[31m";
    private static final String reset = "\u001B[0m";
    public static void main(String args[]) {
        try {
            PostsDAO postsDAO = new PostsDAO();
            System.out.println(rouge + "Test selectAllPublic :" + reset);
            for (PostDetails p : postsDAO.selectAllPublic(true)) {
                System.out.println(p);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
