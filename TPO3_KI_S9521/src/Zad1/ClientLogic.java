package Zad1;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by ikownacki on 14.04.2017.
 */
public class ClientLogic extends AbstractServerClient{


    public ClientLogic(String host, int port) throws IOException {
        super(host, port);



    }
    public void subscribe(String subject){
        sendToServer(SUBSCRIBE,subject);
    }
    public void unSubscribe(String subject){
        sendToServer(UN_SUBSCRIBE,subject);
    }

    public void handleIncomingConnections(){
        new Thread();// TODO: 17.04.2017  
    }

}
