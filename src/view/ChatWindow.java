package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatWindow extends JPanel {
    private JLabel chatRoomLabel;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    
    public ChatWindow() {
        setLayout(new BorderLayout());
        
        // Header with chat room name
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 50, 50));
        chatRoomLabel = new JLabel("Select a chat room", SwingConstants.CENTER);
        chatRoomLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        chatRoomLabel.setForeground(Color.WHITE);
        headerPanel.add(chatRoomLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Chat area for messages
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);
        
        // Input area for new messages
        JPanel inputPanel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        messageField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        sendButton.setBackground(new Color(37, 211, 102));
        sendButton.setForeground(Color.WHITE);
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
        
        // Action listeners for sending messages
        ActionListener sendAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        };
        sendButton.addActionListener(sendAction);
        messageField.addActionListener(sendAction);
    }
    
    public void setChatRoom(String chatRoom) {
        chatRoomLabel.setText(chatRoom + " Room");
        chatArea.setText(""); // Optionally clear or load chat history
    }
    
    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            chatArea.append("You: " + message + "\n");
            messageField.setText("");
            // TODO: Integrate real-time messaging logic here (e.g., sending via WebSocket)
        }
    }
}
