package modele.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dao.*;
import modele.dto.*;

public class PostsTest {
    private static final String SL = System.lineSeparator();
    private static final String rouge = "\u001B[31m";
    private static final String reset = "\u001B[0m";
    public static void main(String args[]) {
        try {
            List<Post> posts = new ArrayList<>();
            PostsDAO postsDAO = new PostsDAO();
            posts = postsDAO.selectAll();
            System.out.println(rouge + "Test getPostReactions :" + reset);
            for (Reaction reaction : postsDAO.getPostReactions(1)) {
                System.out.println(reaction);
            }
            System.out.println(rouge + "Test selectAllPublic Tri par réaction:" + reset);
            for (Post post : postsDAO.selectAllPublic(false)) {
                System.out.println(post);
            }
            System.out.println(rouge + "Test selectAllPublic Tri par date:" + reset);
            for (Post post : postsDAO.selectAllPublic(true)) {
                System.out.println(post);
            }
            System.out.println(rouge + "Test selectFromGroup Tri par date:" + reset);
            for (Post post : postsDAO.selectFromGroup(1, false)) {
                System.out.println(post);
            }
            System.out.println(rouge + "Test selectFromGroup Tri par réaction:" + reset);
            for (Post post : postsDAO.selectFromGroup(1, true)) {
                System.out.println(post);
            }
            System.out.println(rouge + "Test selectFromPostParent :" + reset);
            for (Post post : postsDAO.selectFromPostParent(1)) {
                System.out.println(post);
            }
            System.out.println(rouge + "Post found by PID: " + reset + SL + postsDAO.findByPid(1));
            Post postTest = creationPostTest();
            postsDAO.insert(postTest);
            System.out.println(rouge + "Post inserted: " + reset + SL + postTest);
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (Post post : posts) {
                System.out.println(post);
            }
            int idASupprimer = 5;
            System.out.println(rouge + "Posts deleted n" + idASupprimer +" : " + reset);
            postTest.setPid(idASupprimer);
            postsDAO.delete(postTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Post creationPostTest() {
        Post post = new Post();
        post.setUid(5);
        post.setGid(null);
        post.setPidParent(null);
        post.setContenu("This is a test post");
        post.setMedia("testMedia.png");
        post.setDpub(LocalDateTime.now());
        post.setDfin(LocalDateTime.now().plusHours(1));
        return post;
    }
}
