package modele.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dao.*;
import modele.dto.*;

public class ReactionsTest {
    private static final String SL = System.lineSeparator();
    private static final String rouge = "\u001B[31m";
    private static final String reset = "\u001B[0m";
    public static void main(String args[]) {
        try {
            List<Reaction> reactions = new ArrayList<>();
            ReactionsDAO reactionsDAO = new ReactionsDAO();
            Reaction reactTest = creationReactTest();
            Reaction reactTest2 = creationReactTest2();
            if(reactionsDAO.findByUidAndPid(reactTest.getUid(), reactTest.getPid()) != null) {
                reactionsDAO.update(reactTest2);
                System.out.println(rouge + "Reaction modified: " + reset + SL + reactTest2);
                reactions = reactionsDAO.selectAll();
                System.out.println(rouge + "Test SelectAll :" + reset);
                for (Reaction react : reactions) {
                    System.out.println(react);
                }
                reactionsDAO.delete(reactTest2);
                System.out.println(rouge + "Reaction deleted: " + reset + SL + reactTest2);
            } else {
                reactionsDAO.insert(reactTest);
                System.out.println(rouge + "Reaction inserted: " + reset + SL + reactTest);
                reactions = reactionsDAO.selectAll();
                System.out.println(rouge + "Test SelectAll :" + reset);
                for (Reaction react : reactions) {
                    System.out.println(react);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Reaction creationReactTest() {    
        Reaction reaction = new Reaction();
        reaction.setUid(5);
        reaction.setPid(1);
        reaction.setType("LIKES");
        reaction.setDreact(LocalDateTime.now());
        return reaction;
    }

    public static Reaction creationReactTest2() {
            Reaction reaction = new Reaction();
            reaction.setUid(5);
            reaction.setPid(1);
            reaction.setType("LOVES");
            reaction.setDreact(LocalDateTime.now());
            return reaction;
    }
}