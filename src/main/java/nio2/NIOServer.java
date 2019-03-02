package nio2;

import nio.RequestHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(1994));
            System.out.println("NIO Server has start,listening port " + serverChannel.getLocalAddress());
            Selector selector = Selector.open();
            //初始化的时候将通道设置为ACCEPT状态
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            RequestHandler requestHandler = new RequestHandler();
            while (true) {
                int select = selector.select();
                if (select == 0) {
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterable = selectionKeys.iterator();
                while (iterable.hasNext()) {
                    SelectionKey selectionKey = iterable.next();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel clientChannel = channel.accept();
                        System.out.println("Connection form " + clientChannel.getRemoteAddress());
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);
                    }

                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        channel.read(byteBuffer);
                        String request = new String(byteBuffer.array()).trim();
                        byteBuffer.clear();
                        System.out.println("Request form " + channel.getRemoteAddress() + " request data " + request);
                        String response = requestHandler.doRequest(request);
                        channel.write(ByteBuffer.wrap(response.getBytes()));
                    }

                    iterable.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
