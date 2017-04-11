package Zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by ikownacki on 05.04.2017.
 */
public class CentralServer {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;


    public CentralServer(String host, int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(host,port));

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        serviceConnections();
    }

    private void serviceConnections(){

        while (true) {
            try {

                selector.select();
                Set<SelectionKey> keys = selector.keys();
                ByteBuffer byteBuffer = ByteBuffer.allocate(512);

                Iterator<SelectionKey> iterator = keys.iterator();
                while ((iterator.hasNext())){
                    SelectionKey selectionKey = iterator.next();
                    keys.remove(selectionKey);

                    if(selectionKey.isAcceptable()){
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }
                    if(selectionKey.isReadable()){
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        client.read(byteBuffer);

                    }
                    if(selectionKey.isWritable()){

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

    }
}
