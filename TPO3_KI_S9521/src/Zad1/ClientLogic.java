package Zad1;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by ikownacki on 14.04.2017.
 */
public class ClientLogic extends AbstractServerClient {
    ClientGui clientGui;
    String incomingHost;
    int incomingPort;

    public ClientLogic(String host, int port, String incomingHost, int incomingPort) throws IOException {
        super(host, port, incomingHost, incomingPort);
        handleIncomingConnections(incomingHost, incomingPort);
        clientGui = new ClientGui(this);
        this.incomingHost = incomingHost;
        this.incomingPort = incomingPort;
        registerIncomingServerChanel(incomingHost, incomingPort);
    }

    public void subscribe(String subject) {
        sendToServer(SUBSCRIBE, incomingHost, String.valueOf(incomingPort), subject);
    }

    public void unSubscribe(String subject) {
        sendToServer(UN_SUBSCRIBE,incomingHost, String.valueOf(incomingPort), subject);
    }

    @Override
    public void handleRequest(String[] request) {
        String command = request[0];

        if(command.equals(ADD_SUBJECT)){
            String subject = request[1];
            clientGui.addSubject(subject);
        }
        else if (command.equals(DELETE_SUBJECT)){
            String subject = request[1];
            clientGui.deleteSubject(subject);
        }
        else if(command.equals(SEND_TO_SUBSCRIBERS)){
            String subject = request[1];
            String message = request[2];
            clientGui.showMessage("TEMAT: "+ subject, message);
        }


        for (String s : request){
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        try {
            ClientLogic clientLogic = new ClientLogic("localhost", 45000, "localhost", 47000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
