package Zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by ikownacki on 05.04.2017.
 */
public class AdminLogic extends AbstractServerClient {


    public AdminLogic(String host, int port) throws IOException {
        super(host, port);
    }

    public void addSubject(String subject) {
        sendToServer(ADD_SUBJECT, subject);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] bytes = new byte[byteBuffer.position()];
        byteBuffer.flip();
        byteBuffer.get(bytes);
        String[] result = new String(bytes, Charset.forName("UTF-8")).split(SEPARATOR);
        System.out.println(result[0]+" "+result[1]);
    }

    public void deleteSubject(String subject) {
        sendToServer(DELETE_SUBJECT, subject);
    }

    public void sendMessageToSubscribers(String subject, String message) {
        sendToServer(SEND_TO_SUBSCRIBERS, subject, message);
    }




}
