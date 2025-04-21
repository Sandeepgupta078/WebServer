package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class client {
    public void run() throws IOException {
        int port = 8080;
        InetAddress serverAddress = InetAddress.getByName("localhost");
        Socket clientSocket = new Socket(serverAddress, port);
        System.out.println("Connected to server at " + clientSocket.getRemoteSocketAddress());
        PrintWriter toServer = new PrintWriter(clientSocket.getOutputStream(), true);
        toServer.println("Hello from the client!");
        System.out.println("Message sent to server: Hello from the client!");
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String serverMessage = fromServer.readLine();
        System.out.println("Message received from server: " + serverMessage);
        toServer.close();
        fromServer.close();
        clientSocket.close();
        System.out.println("Connection closed.");
    }

    public static void main(String[] args) {
        client client = new client();
        try {
            client.run();
        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Client is shutting down.");
    }
}
