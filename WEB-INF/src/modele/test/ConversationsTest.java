package modele.test;

import java.util.ArrayList;
import java.util.List;

import modele.dao.*;
import modele.dto.*;

public class ConversationsTest {
    private static final String SL = System.lineSeparator();
    private static final String rouge = "\u001B[31m";
    private static final String reset = "\u001B[0m";

    public static void main(String args[]) {
        try {
            List<Conversation> conversations = new ArrayList<>();
            ConversationsDAO conversationsDAO = new ConversationsDAO();
            Conversation conversationTest = creationConversationTest();
            conversationsDAO.insert(conversationTest);
            System.out.println(rouge + "Conversation inserted: " + reset + SL + conversationTest);
            conversations = conversationsDAO.selectAll();
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (Conversation conv : conversations) {
                System.out.println(conv);
            }
            System.out.println(rouge + "Tout les mess de la conv 1 : " + reset);
            for (Message mess : conversationsDAO.getMessageConv(1)) {
                System.out.println(mess);
            }
            System.out.println(rouge + "Groupe deleted cid : " + reset);
            conversationsDAO.delete(conversationTest);
            conversations = conversationsDAO.selectAll();
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (Conversation conv : conversations) {
                System.out.println(conv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Conversation creationConversationTest() {
        return new Conversation(0, 1, 4);
    }
}
