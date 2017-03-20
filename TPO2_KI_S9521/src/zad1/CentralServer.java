package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ikownacki on 19.03.2017.
 */
public class CentralServer {
    int inPort;
    int outPort;

    public CentralServer(int inPort, int outPort) {
        this.inPort = inPort;
        this.outPort = outPort;
    }

    private void runServer() {
        new Thread(() -> {

            while (true) {

                try (ServerSocket serverSocket = new ServerSocket(inPort);
                     Socket socket = serverSocket.accept();
                     BufferedReader bufferedReader = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {



                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

