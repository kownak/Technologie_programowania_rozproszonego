package zad1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * Created by ikownacki on 19.03.2017.
 */
public class DictionaryServer {
    private int inPort;
    private Map<String, String> dictionary;

    public DictionaryServer(int inPort, Map<String, String> dictionary) {
        this.inPort = inPort;
        this.dictionary = dictionary;
    }

    public void startServer() {

        new Thread(() -> {

            try (ServerSocket serverSocket = new ServerSocket(inPort)) {

                while (true) {

                    Socket socketIn = serverSocket.accept();
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(socketIn.getInputStream()));

                    String wordToTranslate = bufferedReader.readLine();
                    int recipientPort = Integer.valueOf(bufferedReader.readLine());

                    String translatedWord = dictionary.containsKey(wordToTranslate) ?
                            dictionary.get(wordToTranslate) : "Brak tłumaczenia w słowniiku";

                    Socket socketOut = new Socket("localhost", recipientPort);
                    BufferedWriter bufferedWriter = new BufferedWriter(
                            new OutputStreamWriter(socketOut.getOutputStream()));

                    bufferedWriter.write(translatedWord);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    bufferedReader.close();
                    socketIn.close();
                    bufferedWriter.close();
                    socketOut.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
