package bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BIOServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1994);
            System.out.println("BIOServer has started and listening on port : " + serverSocket.getLocalSocketAddress());
                //监听客户端的socket请求
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection from client " + clientSocket.getRemoteSocketAddress());
                Scanner input = new Scanner(clientSocket.getInputStream());

                //Server Client 循环交互
                while (true) {
                    String request = input.nextLine();
                    if ("quit".equals(request)){
                        break;
                    }
                    System.out.println("Client data : " + request);
                    String response = "Server say hello " + request + "\n";
                    clientSocket.getOutputStream().write(response.getBytes());
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
