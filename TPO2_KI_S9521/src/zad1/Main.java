package zad1;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ikownacki on 18.03.2017.
 */


public class Main {
    public static void main(String[] args) {

        Map<String,Integer> servers = new HashMap<>();
        servers.put("ANG",60113);
        CentralServer centralServer = new CentralServer(60111, servers);
        centralServer.startServer();

        Map<String,String> dictionary = new HashMap<>();
        dictionary.put("dupa","ass");
        DictionaryServer dictionaryServer = new DictionaryServer(60113, dictionary);
        dictionaryServer.startServer();


        ClientGui clientGui = new ClientGui(60111, new String[]{"ANG"});
    }

}
