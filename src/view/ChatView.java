package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;

public class ChatView extends JFrame {
    private User user;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JList<String> userList;
    private DefaultListModel<String> userModel;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String selectedUser = null;

    public ChatView(User user) {
        this.user = user;

        setTitle("Chat - " + user.getUsername());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // User list panel
        userModel = new DefaultListModel<>();
        userList = new JList<>(userModel);
        JScrollPane userScroll = new JScrollPane(userList);
        add(userScroll, BorderLayout.WEST);

        userList.addListSelectionListener(e -> selectedUser = userList.getSelectedValue());

        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Send");
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());

        connectToServer();
        setVisible(true);
    }

    private void connectToServer() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(user.getUsername());

            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        if (serverMessage.startsWith("USERS:")) {
                            updateUserList(serverMessage.substring(6));
                        } else {
                            chatArea.append(serverMessage + "\n");
                        }
                    }
                } catch (IOException e) {
                    chatArea.append("Disconnected from server.\n");
                }
            }).start();
        } catch (IOException e) {
            chatArea.append("Error connecting to server.\n");
        }
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            if (selectedUser != null) {
                out.println("@" + selectedUser + " " + message);
            } else {
                out.println(message);
            }
            messageField.setText("");
        }
    }

    private void updateUserList(String users) {
        userModel.clear();
        for (String user : users.split(",")) {
            if (!user.equals(this.user.getUsername())) {
                userModel.addElement(user);
            }
        }
    }
}
