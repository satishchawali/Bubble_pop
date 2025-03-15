package view;

import model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
        setTitle("Chats - Bubble Pop");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar for Friends List
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setPreferredSize(new Dimension(250, 500));
        userPanel.setBackground(new Color(100, 130, 173));
        userPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel userLabel = new JLabel("Friends", SwingConstants.CENTER);
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        userLabel.setForeground(Color.WHITE);
        userPanel.add(userLabel, BorderLayout.NORTH);

        userModel = new DefaultListModel<>();
        userList = new JList<>(userModel);
        userList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane userScroll = new JScrollPane(userList);
        userPanel.add(userScroll, BorderLayout.CENTER);
        add(userPanel, BorderLayout.WEST);

        // Chat Area
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBackground(Color.WHITE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        chatArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane chatScroll = new JScrollPane(chatArea);
        chatPanel.add(chatScroll, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        messageField = new JTextField();
        messageField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        sendButton.setBackground(new Color(37, 211, 102));
        sendButton.setForeground(Color.WHITE);
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        add(chatPanel, BorderLayout.CENTER);
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

    private void updateUserList(String users) {
        userModel.clear();
        for (String username : users.split(",")) {
            if (!username.equals(this.user.getUsername())) {
                userModel.addElement(username);
            }
        }
    }
}
