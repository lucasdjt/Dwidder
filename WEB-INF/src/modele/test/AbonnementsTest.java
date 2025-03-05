package modele.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dao.*;
import modele.dto.*;

public class AbonnementsTest {
    private static final String SL = System.lineSeparator();
    private static final String rouge = "\u001B[31m";
    private static final String reset = "\u001B[0m";
    
    public static void main(String args[]) {
        try {
            List<Abonnement> abonnements = new ArrayList<>();
            AbonnementsDAO abonnementsDAO = new AbonnementsDAO();
            Abonnement abonnementTest = creationAbonnementTest();
            abonnementsDAO.insert(abonnementTest);
            System.out.println(rouge + "Abonnement inserted: " + reset + SL + abonnementTest);
            abonnements = abonnementsDAO.selectAll();
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (Abonnement abonnement : abonnements) {
                System.out.println(abonnement);
            }
            System.out.println(rouge + "Abonnement deleted uidAbonne 5 uidAbonnement 4 : " + reset);
            System.out.println(abonnementsDAO.findByFollowAndFollowers(5, 4));
            abonnementsDAO.delete(abonnementTest);
            abonnements = abonnementsDAO.selectAll();
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (Abonnement abonnement : abonnements) {
                System.out.println(abonnement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Abonnement creationAbonnementTest() {    
        Abonnement abonnement = new Abonnement();
        abonnement.setUidAbonne(5);
        abonnement.setUidAbonnement(4);
        abonnement.setDabonnement(LocalDateTime.now());
        return abonnement;
    }
}
