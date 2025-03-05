package modele.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dao.*;
import modele.dto.*;

public class MembresTest {
    private static final String SL = System.lineSeparator();
    private static final String rouge = "\u001B[31m";
    private static final String reset = "\u001B[0m";
    
    public static void main(String args[]) {
        try {
            List<Membre> membres = new ArrayList<>();
            MembresDAO membresDAO = new MembresDAO();
            Membre membreTest = creationMembreTest();
            membresDAO.insert(membreTest);
            System.out.println(rouge + "Membre inserted: " + reset + SL + membreTest);
            membres = membresDAO.selectAll();
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (Membre membre : membres) {
                System.out.println(membre);
            }
            int uidASupprimer = 5;
            int gidASupprimer = 1;
            System.out.println(rouge + "Membre deleted uid " + uidASupprimer + " gid " + gidASupprimer + " : " + reset);
            membreTest.setUid(uidASupprimer);
            membreTest.setGid(gidASupprimer);
            membresDAO.delete(membreTest);
            membres = membresDAO.selectAll();
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (Membre membre : membres) {
                System.out.println(membre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Membre creationMembreTest() {    
        Membre membre = new Membre();
        membre.setUid(5);
        membre.setGid(1);
        membre.setDjoin(LocalDateTime.now());
        return membre;
    }
}
