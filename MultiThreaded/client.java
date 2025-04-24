package MultiThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class client {

    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    runClient();
                } catch (Exception e) {
                    System.out.println("Error in client thread: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
    }

    public void runClient() throws IOException {
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
        for (int i = 0; i < 10; i++) {
            try {
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            } catch (Exception e) {
                System.out.println("Error starting the client thread: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
