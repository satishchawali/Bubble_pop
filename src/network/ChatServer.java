package network;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Map<String, ClientHandler> clients = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server Started, Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // First message from client is the username
                username = in.readLine();
                synchronized (clients) {
                    clients.put(username, this);
                    broadcastUserList();
                }

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("@")) {
                        sendPrivateMessage(message);
                    } else {
                        broadcastMessage(username + ": " + message);
                    }
                }
            } catch (IOException e) {
                System.out.println(username + " disconnected.");
            } finally {
                try {
                    socket.close();
                    synchronized (clients) {
                        clients.remove(username);
                        broadcastUserList();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcastMessage(String message) {
            synchronized (clients) {
                for (ClientHandler client : clients.values()) {
                    client.out.println(message);
                }
            }
        }

        private void sendPrivateMessage(String message) {
            String[] parts = message.split(" ", 2);
            if (parts.length < 2) return;

            String targetUser = parts[0].substring(1);
            String privateMessage = "[Private] " + username + ": " + parts[1];

            synchronized (clients) {
                ClientHandler target = clients.get(targetUser);
                if (target != null) {
                    target.out.println(privateMessage);
                    this.out.println(privateMessage);
                } else {
                    this.out.println("User " + targetUser + " is not online.");
                }
            }
        }

        private void broadcastUserList() {
            String userList = "USERS:" + String.join(",", clients.keySet());
            for (ClientHandler client : clients.values()) {
                client.out.println(userList);
            }
        }
    }
}
