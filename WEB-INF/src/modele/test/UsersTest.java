package modele.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dao.*;
import modele.dto.*;

public class UsersTest {
    private static final String SL = System.lineSeparator();
    private static final String rouge = "\u001B[31m";
    private static final String reset = "\u001B[0m";
    public static void main(String args[]) {
        try {
            List<User> users = new ArrayList<>();
            UsersDAO userDAO = new UsersDAO();
            users = userDAO.selectAll();
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (User user : users) {
                System.out.println(user);
            }
            System.out.println(rouge + "Test getUserGroups :" + reset);
            for (Groupe group : userDAO.getUserGroups(1)) {
                System.out.println(group);
            }
            System.out.println(rouge + "Test getUserFollows :" + reset);
            for (User user : userDAO.getUserFollows(1)) {
                System.out.println(user);
            }
            System.out.println(rouge + "Test getUserFollowers :" + reset);
            for (User user : userDAO.getUserFollowers(1)) {
                System.out.println(user);
            }
            System.out.println(rouge + "Test getUserFavoris :" + reset);
            for (User user : userDAO.getUserFollowers(1)) {
                System.out.println(user);
            }
            System.out.println(rouge + "Test getUserConversations :" + reset);
            for (Conversation conv : userDAO.getUserConversations(1)) {
                System.out.println(conv);
            }
            System.out.println(rouge + "Test getUsersPosts Tri par date:" + reset);
            for (Post post : userDAO.getUsersPosts(2,true)) {
                System.out.println(post);
            }
            System.out.println(rouge + "Test getUsersPosts Tri par réaction:" + reset);
            for (Post post : userDAO.getUsersPosts(2,false)) {
                System.out.println(post);
            }
            User userTest = creationUserTest();
            User userTest2 = creationUserTest2();
            if(userDAO.findByIdPseudo(userTest.getIdPseudo()) != null) {
                System.out.println(rouge + "Test findByIdPseudo :" + reset);
                userTest2.setUid(userDAO.findByEmail(userTest.getEmail()).getUid());
                userDAO.update(userTest2);
                System.out.println(rouge + "User modified: " + reset + SL + userTest2);
            } else if(userDAO.findByIdPseudo(userTest2.getIdPseudo()) != null){
                userDAO.delete(userTest2.getIdPseudo());
                System.out.println(rouge + "User deleted: " + reset + SL + userTest2);
            } else {
                userDAO.insert(userTest);
                System.out.println(rouge + "User inserted: " + reset + SL + userTest);
                userTest = userDAO.findByIdPseudo(userTest.getIdPseudo());
                userTest.setUid(userDAO.findByEmail(userTest.getEmail()).getUid());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User creationUserTest() {
        User user = new User();
        user.setIdPseudo("testId");
        user.setPseudo("testPseudo");
        user.setPrenom("TestPrenom");
        user.setNomUser("TestNom");
        user.setEmail("test@example.com");
        user.setMdp("testPassword");
        user.setBio("This is a test bio");
        user.setPdp("testPdp.png");
        user.setDinsc(LocalDateTime.now());
        user.setDnaiss(LocalDate.of(2005, 5, 10));
        user.setLoca("TestLocation");
        user.setSexe("M");
        user.setTel("1234567890");
        user.setLangue("FR");
        user.setAdmin(false);
        return user;
    }

    public static User creationUserTest2() {
        User user = new User();
        user.setIdPseudo("test2id");
        user.setPseudo("test2Pseudo");
        user.setPrenom("Test2Prenom");
        user.setNomUser("Test2Nom");
        user.setEmail("test2@example.com");
        user.setMdp("test2Password");
        user.setBio("This is a test2 bio");
        user.setPdp("test2Pdp.png");
        user.setDinsc(LocalDateTime.now());
        user.setDnaiss(LocalDate.of(2005, 5, 10));
        user.setLoca("Test2Location");
        user.setSexe("F");
        user.setTel("2234567890");
        user.setLangue("EN");
        user.setAdmin(false);
        return user;
    }
}