package modele.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modele.dao.*;
import modele.dto.*;

public class MessagesTest {
    private static final String SL = System.lineSeparator();
    private static final String rouge = "\u001B[31m";
    private static final String reset = "\u001B[0m";
    
    public static void main(String args[]) {
        try {
            List<Message> messages = new ArrayList<>();
            MessagesDAO messagesDAO = new MessagesDAO();
            Message messageTest = creationMessageTest();
            messagesDAO.insert(messageTest);
            System.out.println(rouge + "Message inserted: " + reset + SL + messageTest);
            messages = messagesDAO.selectAll();
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (Message msg : messages) {
                System.out.println(msg);
            }
            int idASupprimer = 5;
            System.out.println(rouge + "Message deleted n" + idASupprimer +" : " + reset);
            messageTest.setMid(idASupprimer);
            messagesDAO.delete(messageTest);
            messages = messagesDAO.selectAll();
            System.out.println(rouge + "Test SelectAll :" + reset);
            for (Message msg : messages) {
                System.out.println(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Message creationMessageTest() {    
        Message message = new Message();
        message.setCid(1);
        message.setUid(5);
        message.setCorps("This is a test message");
        message.setDmess(LocalDateTime.now());
        return message;
    }
}
