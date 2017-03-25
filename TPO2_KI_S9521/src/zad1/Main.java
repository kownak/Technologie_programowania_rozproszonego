package zad1;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * Created by ikownacki on 18.03.2017.
 */


public class Main extends SimpleFileVisitor<Path> {

    public static void main(String[] args) {

        MFileVisitor mFileVisitor = new MFileVisitor();
        try {
            Files.walkFileTree(Paths.get("bin/zad1/dictionaries"), mFileVisitor);
            List<Map<String, String>> dictionariesMapsList = mFileVisitor.getDictionariesMapsList();
            Map<String, Integer> avaiableLanguages = new HashMap<>();
            String message = "";

            Integer clientOutPort = 60111;

            for (Map<String, String> map : dictionariesMapsList) {
                int port = Integer.parseInt(map.remove("port"));
                String languageCode = map.remove("jezyk");

                if (!avaiableLanguages.containsValue(port) && port!=clientOutPort) {
                    avaiableLanguages.put(languageCode, port);
                    DictionaryServer dictionaryServer = new DictionaryServer(port, map);
                    dictionaryServer.startServer();
                } else {
                    message += "Serwer " + languageCode + " ma złą konfigurację";
                }

            }

            List<Integer> usedPorts = new ArrayList<>(avaiableLanguages.values());
            usedPorts.add(clientOutPort);

            CentralServer centralServer = new CentralServer(clientOutPort, avaiableLanguages);
            centralServer.startServer();

            Object[] temp = avaiableLanguages.keySet().toArray();
            String[] lanCode = convertObjectArrayToStringArray(temp);

            ClientGui clientGui = new ClientGui(clientOutPort, lanCode, message, usedPorts);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String[] convertObjectArrayToStringArray(Object[] temp) {
        String[] strings = new String[temp.length];
        for (int i = 0; i < temp.length; i++) {
            strings[i] = (String) temp[i];
        }
        return strings;
    }

}
