package Zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by ikownacki on 15.04.2017.
 */
public abstract class AbstractServerClient implements AvailableCommends {
    protected SocketChannel socketChannel;
    private volatile boolean isIncoming;
    volatile ServerSocketChannel serverSocketChannel;
    volatile Selector selector;

    public AbstractServerClient(String serverHost, int serverPort, String incomingHost, int incomingPort)
            throws IOException {

        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);
        socketChannel.connect(new InetSocketAddress(serverHost, serverPort));
        isIncoming = true;


    }

    public void registerIncomingServerChanel(String incomingHost, int incomingPort){
        sendToServer(REGISTER, incomingHost, String.valueOf(incomingPort));
    }

    public void sendToServer(String command, String... args) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(command);
        for (String arg : args) {
            stringBuilder.append(SEPARATOR);
            stringBuilder.append(arg);
        }

        String builtString = stringBuilder.toString();
        ByteBuffer byteBuffer = ByteBuffer.wrap(builtString.getBytes(Charset.forName("UTF-8")));

        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getCurrentSubjects() {
        List<String> currentSubjects = new ArrayList<>();
        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        byteBuffer.clear();

        sendToServer(GET_ALL_SUBJECTS);
        try {

            socketChannel.read(byteBuffer);
            byte[] bytes = new byte[byteBuffer.position()];
            byteBuffer.flip();
            byteBuffer.get(bytes);

            String[] result = new String(bytes, Charset.forName("UTF-8")).split(SEPARATOR);


            if (NO_DATA.equals(result[0])) {
                return currentSubjects;
            }

            for (String string : result) {
                System.out.println(string);
                currentSubjects.add(string);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentSubjects;
    }

    public void closeConnection() throws IOException {
        isIncoming = false;
        sendToServer(BYE);
        socketChannel.close();
    }

    public void handleIncomingConnections(String incomingHost, int incomingPort) {
        new Thread(() -> {
            try {
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.socket().bind(new InetSocketAddress(incomingHost, incomingPort));

                selector = Selector.open();
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                while (isIncoming) {

                    selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator =  keys.iterator();

                    while(iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();

                        if (selectionKey.isAcceptable()) {

                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);

                        } else if (selectionKey.isReadable()) {

                            String[] request = getConnectionRequest((SocketChannel) selectionKey.channel());
                            handleRequest(request);
                        }
                    }
                }
                serverSocketChannel.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private String[] getConnectionRequest(SocketChannel socketChannel) throws IOException {
        ByteBuffer byteBuffer;
        byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.clear();

        int in;
        String result = "";
        if ((in = socketChannel.read(byteBuffer)) > 0) {
            byte[] bytes;
            bytes = new byte[byteBuffer.position()];
            byteBuffer.flip();
            byteBuffer.get(bytes);
            result = new String(bytes, Charset.forName("UTF-8"));

        }
        if (in == -1) {
            socketChannel.close();
            return null;
        }
        return result.split(SEPARATOR);
    }

    public abstract void handleRequest(String[] request);
}
