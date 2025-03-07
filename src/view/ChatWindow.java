package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChatWindow extends JPanel {
    private JLabel chatRoomLabel;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    
    public ChatWindow() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 50, 50));
        chatRoomLabel = new JLabel("Select a chat room", SwingConstants.CENTER);
        chatRoomLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        chatRoomLabel.setForeground(Color.WHITE);
        headerPanel.add(chatRoomLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        
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
        
        sendButton.addActionListener(this::sendMessage);
        messageField.addActionListener(this::sendMessage);
    }
    
    public void setChatRoom(String chatRoom) {
        chatRoomLabel.setText(chatRoom + " Room");
        chatArea.setText("");
    }
    
    private void sendMessage(ActionEvent e) {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            chatArea.append("You: " + message + "\n");
            messageField.setText("");
        }
    }
}
