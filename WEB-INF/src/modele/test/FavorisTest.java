package modele.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dao.*;
import modele.dto.*;

public class FavorisTest {
    private static final String SL = System.lineSeparator();
    private static final String rouge = "\u001B[31m";
    private static final String reset = "\u001B[0m";

    public static void main(String args[]) {
        try {
            List<Favori> favoris = new ArrayList<>();
            FavorisDAO favorisDAO = new FavorisDAO();
            Favori favoriTest = creationFavoriTest();
            Favori favoriTest2 = creationFavoriTest2();

            if (favorisDAO.findByUidAndPid(favoriTest.getUid(), favoriTest.getPid()) != null) {
                favoris = favorisDAO.selectAll();
                System.out.println(rouge + "Test SelectAll :" + reset);
                for (Favori favori : favoris) {
                    System.out.println(favori);
                }
                favorisDAO.delete(favoriTest2);
                System.out.println(rouge + "Favori deleted: " + reset + SL + favoriTest2);
                favoris = favorisDAO.selectAll();
                System.out.println(rouge + "Test SelectAll :" + reset);
                for (Favori favori : favoris) {
                    System.out.println(favori);
                }
            } else {
                favoris = favorisDAO.selectAll();
                System.out.println(rouge + "Test SelectAll :" + reset);
                for (Favori favori : favoris) {
                    System.out.println(favori);
                }
                favorisDAO.insert(favoriTest);
                System.out.println(rouge + "Favori inserted: " + reset + SL + favoriTest);
                favoris = favorisDAO.selectAll();
                System.out.println(rouge + "Test SelectAll :" + reset);
                for (Favori favori : favoris) {
                    System.out.println(favori);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Favori creationFavoriTest() {
        Favori favori = new Favori();
        favori.setUid(5);
        favori.setPid(1);
        favori.setDfavori(LocalDateTime.now());
        return favori;
    }

    public static Favori creationFavoriTest2() {
        Favori favori = new Favori();
        favori.setUid(5);
        favori.setPid(1);
        favori.setDfavori(LocalDateTime.now());
        return favori;
    }
}