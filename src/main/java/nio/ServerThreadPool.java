package nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerThreadPool {

    public static void main(String[] args) {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            RequestHandler requestHandler = new RequestHandler();
            ServerSocket serverSocket = new ServerSocket(1994);
            System.out.println("NIO Server has start,listen port " + serverSocket.getLocalSocketAddress());
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ClientHandler(requestHandler, clientSocket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
