package zad1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ikownacki on 20.03.2017.
 */

public class ClientLogic {
    private int inPort;
    private int outPort;

    public ClientLogic(int inPort, int outPort) throws IOException {
        this.inPort = inPort;
        this.outPort = outPort;
    }

    public String translateWord(String word, String langCode) {
        final String[] translation = {null};
        new Thread(() -> {

            try (Socket socket = new Socket("localhost", outPort);
                 BufferedWriter bufferedWriter = new BufferedWriter(
                         new OutputStreamWriter(socket.getOutputStream()))) {

                bufferedWriter.write(langCode);
                bufferedWriter.write(word);
                bufferedWriter.write(inPort);

                Thread.sleep(200);

                try (ServerSocket serverSocket = new ServerSocket(inPort);
                     Socket socket1 = serverSocket.accept();
                     BufferedReader bufferedReader = new BufferedReader(
                             new InputStreamReader(socket1.getInputStream()))) {
                    translation[0] = bufferedReader.readLine();
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        return translation[0];
    }
}