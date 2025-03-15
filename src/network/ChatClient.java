package network;

import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private BufferedReader inputConsole;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ChatClient(String address, int port, String username) {
        this.username = username;
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to the chat server");

            inputConsole = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send username to server
            out.println(username);

            // Start a new thread to listen for messages from the server
            new Thread(() -> {
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
                    System.out.println("Disconnected from server.");
                }
            }).start();

            String userMessage;
            while (!(userMessage = inputConsole.readLine()).equalsIgnoreCase("exit")) {
                out.println(userMessage);
            }

            socket.close();
            inputConsole.close();
            out.close();
            in.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        new ChatClient("127.0.0.1", 5000, "User1");
    }
}
