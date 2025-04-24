package MultiThreaded;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class server {
    // This is a multi-threaded server that handles requests concurrently.

    public Consumer<Socket> getConsumer() {

        return new Consumer<Socket>() {

            // This method is called when a client connects to the server.
            // It sends a welcome message to the client and closes the connection.
            // The Consumer interface is used to handle the client socket.
            // The accept method is overridden to define the behavior when a client connects.
            // The method takes a Socket object as an argument, which represents the client connection.
            // The method is defined as a lambda expression, which is a concise way to implement the Consumer interface.
            @Override
            public void accept(Socket clientSocket) {
                try {
                    PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                    toClient.println("Hello from the server!");
                    toClient.println("You are connected to the server.");
                    toClient.close();
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error handling client: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        // Alternatively, you can use a lambda expression directly:

        // return (clientSocket) -> {
        //     try {
        //         PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
        //         toClient.println("Hello from the server!");
        //         toClient.println("You are connected to the server.");
        //         toClient.close();
        //         clientSocket.close();
        //     }catch (IOException e) {
        //         System.out.println("Error handling client: " + e.getMessage());
        //         e.printStackTrace();
        //     };
        // };
    }
    
    public void run() throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000); // Set timeout to 10 seconds
        System.out.println("Server is listening on port " + port);
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted from client: " + clientSocket.getRemoteSocketAddress());
                // Create a new thread to handle the client
                Thread thread = new Thread(() -> {
                    getConsumer().accept(clientSocket);
                });
                thread.start();
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
