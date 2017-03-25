package zad1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * Created by ikownacki on 19.03.2017.
 */
public class CentralServer {
    private int inPort;
    private Map<String, Integer> avableLanguageServers;

    public CentralServer(int inPort, Map<String, Integer> avableLanguageServers) {
        this.inPort = inPort;
        this.avableLanguageServers = avableLanguageServers;
    }

    public void startServer() {

        new Thread(() -> {

            try (ServerSocket serverSocket = new ServerSocket(inPort)) {

                while (true) {
                    handleConection(serverSocket);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleConection(ServerSocket serverSocket) {

        try (Socket socketIn = serverSocket.accept();
             BufferedReader bufferedReader = new BufferedReader(
                     new InputStreamReader(socketIn.getInputStream()))){


            String languageCode = bufferedReader.readLine();
            String message = bufferedReader.readLine();
            int recipientPort = Integer.valueOf(bufferedReader.readLine());

            int languageServerPort = avableLanguageServers.get(languageCode);

            Socket socketOut = new Socket("localhost", languageServerPort);
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(socketOut.getOutputStream()));

            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            bufferedWriter.write(String.valueOf(recipientPort));
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException | NumberFormatException e ) {
            e.printStackTrace();
        }
    }
}

