package Zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by ikownacki on 05.04.2017.
 */
public class CentralServer implements AvailableCommends {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private List<SocketChannel> allConnections;
    private Map<String, List<SocketChannel>> subjectSubscribersMap;
//
//


    public CentralServer(String host, int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(host, port));

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        subjectSubscribersMap = new HashMap<>();
        allConnections = new ArrayList<>();
        serviceAllConnections();

    }

    private void serviceAllConnections() {

        while (true) {
            try {

                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();


                Iterator<SelectionKey> iterator = keys.iterator();
                while ((iterator.hasNext())) {
                    SelectionKey selectionKey = iterator.next();


                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        allConnections.add(socketChannel);
                        socketChannel.register(selector, SelectionKey.OP_READ);

                        iterator.remove();
                    } else if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        serviceConnection(socketChannel);
                        iterator.remove();

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void serviceConnection(SocketChannel socketChannel) throws IOException {
        String[] request = getConnectionRequest(socketChannel);
        if (request == null) {
            return;
        }

        handleRequest(request, socketChannel);

    }

    private String[] getConnectionRequest(SocketChannel socketChannel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.clear();

        int in;
        String result = "";
        if ((in = socketChannel.read(byteBuffer)) > 0) {
            byte[] bytes = new byte[byteBuffer.position()];
            byteBuffer.flip();
            byteBuffer.get(bytes);
            result = new String(bytes, Charset.forName("UTF-8"));
            System.out.println(result);

        }
        if (in == -1) {
            socketChannel.close();
            return null;
        }
        return result.split(SEPARATOR);
    }

    private void handleRequest(String[] request, SocketChannel socketChannel) throws IOException {

        String command = request[0];

        if (command.equals(GET_ALL_SUBJECTS)) {

            Set<String> currentSubjects = subjectSubscribersMap.keySet();
            StringBuilder stringBuilder = new StringBuilder();

            if (currentSubjects.size() > 0) {
                for (String currentSubject : currentSubjects) {
                    stringBuilder.append(currentSubject);
                }
            } else {
                stringBuilder.append(NO_DATA);
            }

            String builtString = stringBuilder.toString();

            sendToClient(socketChannel, builtString);

        } else if (command.equals(ADD_SUBJECT)) {

            subjectSubscribersMap.put(request[1],new ArrayList<>());
            for (SocketChannel client : allConnections){
                sendToClient(client, ADD_SUBJECT+SEPARATOR+request[1]);
            }

        } else if (command.equals(DELETE_SUBJECT)) {

            subjectSubscribersMap.remove(request[1]);
            for (SocketChannel client : allConnections){
                sendToClient(client, DELETE_SUBJECT+SEPARATOR+request[1]);
            }

        } else if (command.equals(SEND_TO_SUBSCRIBERS)) {

            for (SocketChannel client : subjectSubscribersMap.get(request[1])){
                sendToClient(client,request[1],request[2]);
            }

        } else if (command.equals(SUBSCRIBE)) {

            if(subjectSubscribersMap.containsKey(request[1])){
                subjectSubscribersMap.get(request[1]).add(socketChannel);
            }else{
                sendToClient(socketChannel, NO_SUBJECT);
            }


        } else if (command.equals(UN_SUBSCRIBE)) {

            if(subjectSubscribersMap.containsKey(request[1])){
                subjectSubscribersMap.get(request[1]).remove(socketChannel);
            }else{
                sendToClient(socketChannel, NO_SUBJECT);
            }

        } else if (command.equals(BYE)) {
            allConnections.remove(socketChannel);
            socketChannel.close();
        }
    }


    private void sendToClient(SocketChannel socketChannel, String message) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes(Charset.forName("UTF-8")));

        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToClient(SocketChannel socketChannel, String subject, String message ) {
        String concatenatedString= subject+SEPARATOR+message;
        ByteBuffer byteBuffer = ByteBuffer.wrap(concatenatedString.getBytes(Charset.forName("UTF-8")));

        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CentralServer centralServer = new CentralServer("localhost", 49000);

    }
}
