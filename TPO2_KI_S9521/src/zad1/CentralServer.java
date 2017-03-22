package zad1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * Created by ikownacki on 19.03.2017.
 */
public class CentralServer {
    int inPort;
    Map<String,Integer> avableLanguageServers;

    public CentralServer(int inPort,  Map<String, Integer> avableLanguageServers) {
        this.inPort = inPort;
        this.avableLanguageServers = avableLanguageServers;
    }

    public void startServer() {
        new Thread(() -> {

            try (ServerSocket serverSocket = new ServerSocket(inPort)){

                while(true) {
                    Socket socketIn = serverSocket.accept();
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(socketIn.getInputStream()));

                    String languageCode = bufferedReader.readLine();
                    String message = bufferedReader.readLine();
                    int recipientPort = Integer.valueOf(bufferedReader.readLine());

                    int languageServerPort = avableLanguageServers.get(languageCode);

                    try (Socket socketOut = new Socket("localhost", languageServerPort);
                         BufferedWriter bufferedWriter = new BufferedWriter(
                                 new OutputStreamWriter(socketOut.getOutputStream()))) {
                        bufferedWriter.write(message);
                        bufferedWriter.newLine();
                        bufferedWriter.write(String.valueOf(recipientPort));
                        bufferedWriter.newLine();
                    }
                    bufferedReader.close();
                    socketIn.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

    }
}

