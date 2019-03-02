package nio;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private final RequestHandler requestHandler;
    private final Socket clientSocket;

    public ClientHandler(RequestHandler requestHandler, Socket clientSocket) {
        this.requestHandler = requestHandler;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("Connection from client " + clientSocket.getRemoteSocketAddress());
        Scanner input = null;
        try {
            input = new Scanner(clientSocket.getInputStream());
            while (true) {
                String request = input.nextLine();
                if ("quit".equals(request)){
                    break;
                }
                System.out.println("Client data : " + request);
                System.out.println("handler...");
                String response = requestHandler.doRequest(request);
                clientSocket.getOutputStream().write(response.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
