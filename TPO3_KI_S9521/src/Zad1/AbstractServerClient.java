package Zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikownacki on 15.04.2017.
 */
public abstract class AbstractServerClient implements AvailableCommends {
    SocketChannel socketChannel;

    public AbstractServerClient(String host, int port) throws IOException {

        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);
        socketChannel.connect(new InetSocketAddress(host, port));

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

            String[] result = new String(bytes,Charset.forName("UTF-8")).split(SEPARATOR);


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
        sendToServer(BYE);
        socketChannel.close();
    }


}
