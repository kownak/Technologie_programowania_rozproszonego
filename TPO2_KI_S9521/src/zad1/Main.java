package zad1;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static java.nio.file.FileVisitResult.CONTINUE;

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


            for (Map<String, String> map : dictionariesMapsList) {
                int port = Integer.parseInt(map.remove("port"));
                String languageCode = map.remove("jezyk");

                avaiableLanguages.put(languageCode, port);

                DictionaryServer dictionaryServer = new DictionaryServer(port, map);
                dictionaryServer.startServer();
            }

            int clientOutPort = 60111;

            CentralServer centralServer = new CentralServer(clientOutPort, avaiableLanguages);
            centralServer.startServer();

            Object[] temp =  avaiableLanguages.keySet().toArray();
            String[] lanCode = new String[temp.length];

            for (int i = 0; i < temp.length; i++) {
                lanCode[i] = (String) temp[i];
            }

            ClientGui clientGui = new ClientGui(clientOutPort, lanCode);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
