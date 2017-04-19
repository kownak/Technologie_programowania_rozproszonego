package Zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by ikownacki on 05.04.2017.
 */
public class CentralServer implements AvailableCommends {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private Map<String, SocketChannel> allListenersConnections;
    private Map<String, Map<String, SocketChannel>> subjectSubscribersMap;


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
        allListenersConnections = new HashMap<>();
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
                    stringBuilder.append(SEPARATOR);
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            } else {
                stringBuilder.append(NO_DATA);
            }
            String builtString = stringBuilder.toString();
            sendToClient(socketChannel, builtString);


        } else if (command.equals(REGISTER)) {

            String host = request[1];
            int port = Integer.parseInt(request[2]);
            String name = host + SEPARATOR + port;
            SocketChannel sc = SocketChannel.open();
            sc.connect(new InetSocketAddress(host, port));
            allListenersConnections.put(name, sc);

        } else if (command.equals(ADD_SUBJECT)) {

            subjectSubscribersMap.put(request[1], new HashMap<>());
            for (SocketChannel client : allListenersConnections.values()) {
                sendToClient(client, ADD_SUBJECT + SEPARATOR + request[1]);
            }

        } else if (command.equals(DELETE_SUBJECT)) {

            subjectSubscribersMap.remove(request[1]);
            for (SocketChannel client : allListenersConnections.values()) {
                sendToClient(client, DELETE_SUBJECT + SEPARATOR + request[1]);
            }

        } else if (command.equals(SEND_TO_SUBSCRIBERS)) {

            String subject = request[1];
            Map<String, SocketChannel> map = subjectSubscribersMap.get(subject);
            for (SocketChannel client : map.values()) {
                if (client != null) {
                    sendToClient(client, SEND_TO_SUBSCRIBERS + SEPARATOR + subject + SEPARATOR + request[2]);
                }
            }

        } else if (command.equals(SUBSCRIBE)) {
            String host = request[1];
            int port = Integer.parseInt(request[2]);
            String subject = request[3];
            String name = host + SEPARATOR + port;
            SocketChannel sc = allListenersConnections.get(name);
            subjectSubscribersMap.get(subject).put(name, sc);
            sendToClient(socketChannel, SUBSCRIBE);


        } else if (command.equals(UN_SUBSCRIBE)) {
            String host = request[1];
            int port = Integer.parseInt(request[2]);
            String subject = request[3];
            String name = host + SEPARATOR + port;
            subjectSubscribersMap.get(subject).remove(name);
            sendToClient(socketChannel, UN_SUBSCRIBE);


        } else if (command.equals(BYE)) {
            closeConnection(socketChannel);
        }
    }


    private void sendToClient(SocketChannel socketChannel, String message) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes(Charset.forName("UTF-8")));

        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection(socketChannel);
        }
    }


    private void closeConnection(SocketChannel socketChannel) {
        allListenersConnections.remove(socketChannel);
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CentralServer centralServer = new CentralServer("localhost", 45000);
    }
}
