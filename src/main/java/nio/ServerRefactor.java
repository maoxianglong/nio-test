package nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRefactor {

    public static void main(String[] args) {
        RequestHandler requestHandler = new RequestHandler();
        try {
            ServerSocket serverSocket = new ServerSocket(1994);
            System.out.println("NIOServer has started and listening on port : " + serverSocket.getLocalSocketAddress());
            while (true){
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(requestHandler,clientSocket).run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
