package Zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by ikownacki on 05.04.2017.
 */
public class AdminLogic extends AbstractServerClient {
    AdminGui adminGui;

    public AdminLogic(String host, int port, String incomingHost, int incomingPort) throws IOException {
        super(host, port, incomingHost, incomingPort);
        adminGui = new AdminGui(this);
    }

    public void addSubject(String subject) throws IOException {
        sendToServer(ADD_SUBJECT, subject);
    }

    public void deleteSubject(String subject) {
        sendToServer(DELETE_SUBJECT, subject);
    }

    public void sendMessageToSubscribers(String subject, String message) {
        if(subject != null && message != null) {
            sendToServer(SEND_TO_SUBSCRIBERS, subject, message);
        }
    }

    @Override
    public void handleRequest(String[] request) {
        for (String s : request){
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        try {
            AdminLogic adminLogic = new AdminLogic("localhost", 45000, "localhost", 46000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
