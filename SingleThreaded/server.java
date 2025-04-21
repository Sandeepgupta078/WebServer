package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

    // This is a single-threaded server that handles requests one at a time.
    public void run() throws IOException {
        int port = 8080;
        ServerSocket serverSocket  = new ServerSocket(port);
        serverSocket.setSoTimeout(10000); // Set timeout to 10 seconds
        while (true) {
            try {
                System.out.println("Server is listening on port " + port);
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted from client: " + clientSocket.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                toClient.println("Hello from the server!");
                System.out.println("Message sent to client: Hello from the server!");
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String clientMessage = fromClient.readLine();
                System.out.println("Message received from client: " + clientMessage);
                toClient.close();
                fromClient.close();
                clientSocket.close();
                System.out.println("Connection closed with client: " + clientSocket.getRemoteSocketAddress());
            } catch (IOException e) {
                System.out.println("Connection timed out or error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        server server = new server();
        try {
            server.run();
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Server is shutting down.");
    }
}