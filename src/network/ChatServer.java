package network;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static List<ClientHandler> clients = new ArrayList<>();
    private static Map<String, List<String>> messageHistory = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started. Waiting for clients...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientThread = new ClientHandler(clientSocket, clients);
            clients.add(clientThread);
            new Thread(clientThread).start();
        }
    }

    public static void sendPrivateMessage(String sender, String recipient, String message) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(recipient)) {
                client.sendMessage("[Private] " + sender + ": " + message);

                // Store message in history
                messageHistory.putIfAbsent(sender + ":" + recipient, new ArrayList<>());
                messageHistory.putIfAbsent(recipient + ":" + sender, new ArrayList<>());
                messageHistory.get(sender + ":" + recipient).add("[You -> " + recipient + "]: " + message);
                messageHistory.get(recipient + ":" + sender).add("[From " + sender + "]: " + message);
                return;
            }
        }
    }

    public static List<String> getChatHistory(String user1, String user2) {
        return messageHistory.getOrDefault(user1 + ":" + user2, new ArrayList<>());
    }

    public static void updateUsers() {
        StringBuilder userList = new StringBuilder("USERS:");
        for (ClientHandler client : clients) {
            userList.append(client.getUsername()).append(",");
        }

        for (ClientHandler client : clients) {
            client.sendMessage(userList.toString());
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<ClientHandler> clients;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket, List<ClientHandler> clients) throws IOException {
        this.clientSocket = socket;
        this.clients = clients;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getUsername() {
        return username;
    }

    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("USER:")) {
                    username = inputLine.substring(5);
                    ChatServer.updateUsers();
                } else if (inputLine.startsWith("PRIVATE:")) {
                    String[] parts = inputLine.split(":", 3);
                    if (parts.length == 3) {
                        String recipient = parts[1];
                        String message = parts[2];
                        ChatServer.sendPrivateMessage(username, recipient, message);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("User disconnected: " + username);
        }
    }
}
