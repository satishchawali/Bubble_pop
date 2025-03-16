package network;

import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private BufferedReader inputConsole;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ChatClient(String address, int port) {
        try {
            // Take username input dynamically
            inputConsole = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter your username: ");
            username = inputConsole.readLine();

            socket = new Socket(address, port);
            System.out.println("Connected to the chat server");

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send username to server
            out.println(username);

            // Start a new thread to listen for messages from the server
            Thread listenerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        if (serverMessage.startsWith("USERS:")) {
                            System.out.println("Online Users: " + serverMessage.substring(6));
                        } else {
                            System.out.println(serverMessage);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Server connection lost.");
                } finally {
                    closeResources();
                }
            });
            listenerThread.start();

            // Read user input and send messages
            String userMessage;
            while (true) {
                userMessage = inputConsole.readLine();
                if (userMessage.equalsIgnoreCase("exit")) {
                    out.println("EXIT:" + username);  // Notify server
                    break;
                }
                out.println(userMessage);
            }

            closeResources();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void closeResources() {
        try {
            if (socket != null) socket.close();
            if (inputConsole != null) inputConsole.close();
            if (out != null) out.close();
            if (in != null) in.close();
        } catch (IOException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        new ChatClient("127.0.0.1", 5000);
    }
}
