package modele.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dao.GroupesDAO;
import modele.dto.Groupe;

public class GroupesTest {
    private static final String SL = System.lineSeparator();
    private static final String rouge = "\u001B[31m";
    private static final String reset = "\u001B[0m";
    
    public static void main(String args[]) {
        try {
            List<Groupe> groupes = new ArrayList<>();
            GroupesDAO groupesDAO = new GroupesDAO();
            Groupe groupeTest = creationGroupeTest();
            groupesDAO.insert(groupeTest);
            System.out.println(rouge + "Groupe inserted: " + reset + SL + groupeTest);
            groupes = groupesDAO.selectAll();
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (Groupe groupe : groupes) {
                System.out.println(groupe);
            }
            int gidAUpdate = 13;
            groupeTest.setGid(gidAUpdate);
            System.out.println(rouge + "Groupe update gid " + gidAUpdate + " : " + reset);
            groupeTest.setPid(2);
            groupesDAO.update(groupeTest);
            System.out.println(groupesDAO.findByGid(gidAUpdate));
            System.out.println(rouge + "Groupe deleted gid " + gidAUpdate + " : " + reset);
            groupesDAO.delete(groupeTest);
            groupes = groupesDAO.selectAll();
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (Groupe groupe : groupes) {
                System.out.println(groupe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Groupe creationGroupeTest() {    
        Groupe groupe = new Groupe();
        groupe.setUid(4);
        groupe.setPid(null);
        groupe.setNomGrp("Test Group2");
        groupe.setDescription("This is a test group");
        groupe.setDcreat(LocalDateTime.now());
        return groupe;
    }
}
