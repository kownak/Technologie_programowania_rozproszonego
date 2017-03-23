package zad1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ikownacki on 20.03.2017.
 */

public class ClientLogic {
    private int outPort;

    public ClientLogic(int outPort) {
        this.outPort = outPort;
    }

    public String translateWord(String word, String langCode, int inPort) {
        final String[] translation = {null};

        try (Socket socket = new Socket("localhost", outPort);
             BufferedWriter bufferedWriter = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream()))) {

            bufferedWriter.write(langCode);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            bufferedWriter.write(word);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            bufferedWriter.write(String.valueOf(inPort));
            bufferedWriter.newLine();
            bufferedWriter.flush();

            ServerSocket serverSocket = new ServerSocket(inPort);
            Socket socket1 = serverSocket.accept();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(socket1.getInputStream()));

            translation[0] = bufferedReader.readLine();

            bufferedWriter.close();
            socket.close();
            bufferedReader.close();
            serverSocket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return translation[0];
    }
}
