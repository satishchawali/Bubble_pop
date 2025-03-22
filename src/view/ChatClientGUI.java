package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.User;
import network.ChatClient;

public class ChatClientGUI extends JFrame {
    private JTextArea messageArea;
    private JTextField textField;
    private JButton sendButton, exitButton, attachButton;
    private JList<String> userList;
    private DefaultListModel<String> userModel;
    private ChatClient client;
    private User user;
    private Map<String, StringBuilder> chatHistory = new HashMap<>();
    private Map<String, String> lastMessageTime = new HashMap<>();
    private String selectedUser = null;

    public ChatClientGUI(User user) {
        super("Chat Application");
        this.user = user;
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Left Panel (User List)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(250, 500));
        leftPanel.setBackground(new Color(50, 60, 90));

        JLabel usersLabel = new JLabel(" Online Users", SwingConstants.LEFT);
        usersLabel.setForeground(Color.WHITE);
        usersLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        usersLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.add(usersLabel, BorderLayout.NORTH);

        userModel = new DefaultListModel<>();
        userList = new JList<>(userModel);
        userList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userList.setSelectionBackground(new Color(80, 100, 150));
        userList.setSelectionForeground(Color.WHITE);
        JScrollPane userScrollPane = new JScrollPane(userList);
        leftPanel.add(userScrollPane, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        // Chat Area
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane chatScrollPane = new JScrollPane(messageArea);
        chatScrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(chatScrollPane, BorderLayout.CENTER);

        // Bottom Panel (Input & Buttons)
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        textField = new JTextField();
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bottomPanel.add(textField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(100, 130, 173));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.setBounds(20, 20, 100, 40); // Adjust position and size if needed
        backButton.setBackground(new Color(100, 130, 173));
        backButton.setFocusPainted(false);
        backButton.setForeground(Color.WHITE);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            dispose();
            new HomePage(user);
        });
        leftPanel.add(backButton, BorderLayout.SOUTH);

        // attachButton = new JButton("Attach");
        // attachButton.setBackground(new Color(60, 179, 113));
        // attachButton.setForeground(Color.WHITE);
        // attachButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        // bottomPanel.add(attachButton, BorderLayout.WEST);

        add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        // attachButton.addActionListener(e -> attachFile());
        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedUser = userList.getSelectedValue();
                loadChatHistory();
            }
        });

        try {
            this.client = new ChatClient("127.0.0.1", 5000, this::onMessageReceived);
            client.startClient();
            client.sendMessage("USER:" + user.getUsername());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to the server", "Connection error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void sendMessage() {
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Select a user first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String messageContent = textField.getText().trim();
        if (!messageContent.isEmpty()) {
            String message = "PRIVATE:" + selectedUser + ":" + messageContent;
            client.sendMessage(message);
            chatHistory.putIfAbsent(selectedUser, new StringBuilder());
            chatHistory.get(selectedUser).append("You: " + messageContent + "\n");
            lastMessageTime.put(selectedUser, "Now");
            textField.setText("");
            loadChatHistory();
        }
    }

    private void attachFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Files", "png", "jpg", "mp4", "pdf"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "File Selected: " + selectedFile.getName());
        }
    }

    private void onMessageReceived(String message) {
        SwingUtilities.invokeLater(() -> {
            if (message.startsWith("USERS:")) {
                updateUserList(message.substring(6));
            } else if (message.startsWith("[Private] ")) {
                String[] parts = message.split(" ", 3);
                if (parts.length >= 3) {
                    String sender = parts[1].replace(":", "").trim();
                    String msgContent = parts[2].trim();
                    chatHistory.putIfAbsent(sender, new StringBuilder());
                    chatHistory.get(sender).append(sender + ": " + msgContent + "\n");
                    lastMessageTime.put(sender, "Now");
                    if (selectedUser != null && selectedUser.equals(sender)) {
                        loadChatHistory();
                    }
                }
            }
        });
    }

    private void updateUserList(String users) {
        userModel.clear();
        for (String username : users.split(",")) {
            if (!username.isEmpty() && !username.equals(user.getUsername())) {
                userModel.addElement(username);
            }
        }
    }

    private void loadChatHistory() {
        messageArea.setText(
                selectedUser != null ? chatHistory.getOrDefault(selectedUser, new StringBuilder()).toString() : "");
    }
}