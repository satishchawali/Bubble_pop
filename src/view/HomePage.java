package view;

import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {
    private User user;
    private JTabbedPane tabbedPane;

    public HomePage(User user) {
        this.user = user;
        setTitle("Home - Bubble Pop");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Home", createHomeTab());
        tabbedPane.addTab("Chats", createChatsTab());
        tabbedPane.addTab("Feeds", createFeedsTab());
        tabbedPane.addTab("Profile", createProfileTab());
        tabbedPane.addTab("File Sharing", createFileSharingTab());
        tabbedPane.addTab("Friend Requests", createFriendRequestsTab());
        tabbedPane.addTab("Settings", createSettingsTab());

        add(tabbedPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createHomeTab() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(23, 23, 23);
                Color color2 = new Color(50, 50, 50);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("🎉 Welcome, " + user.getUsername() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JLabel subtitleLabel = new JLabel("🚀 Explore new adventures and features!", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        subtitleLabel.setForeground(new Color(230, 230, 230));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(welcomeLabel);
        textPanel.add(subtitleLabel);

        JButton exploreButton = new JButton("✨ Explore Now ✨");
        exploreButton.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        exploreButton.setBackground(new Color(255, 69, 0));
        exploreButton.setForeground(Color.WHITE);
        exploreButton.setFocusPainted(false);
        exploreButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        exploreButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        exploreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exploreButton.setBackground(new Color(255, 99, 71));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exploreButton.setBackground(new Color(255, 69, 0));
            }
        });

        exploreButton.addActionListener(
                e -> JOptionPane.showMessageDialog(panel, "🌟 Welcome to Bubble Pop! Start your journey now!"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0)); // Bottom Padding
        buttonPanel.add(exploreButton);

        panel.add(textPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createChatsTab() {
        JPanel chatsPanel = new JPanel(new BorderLayout());

        JPanel chatWindowPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 50, 50));
        JLabel chatRoomLabel = new JLabel("Select a chat room", SwingConstants.CENTER);
        chatRoomLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        chatRoomLabel.setForeground(Color.WHITE);
        headerPanel.add(chatRoomLabel);
        chatWindowPanel.add(headerPanel, BorderLayout.NORTH);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        JScrollPane chatAreaScrollPane = new JScrollPane(chatArea);
        chatWindowPanel.add(chatAreaScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField messageField = new JTextField();
        messageField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        sendButton.setBackground(new Color(37, 211, 102));
        sendButton.setForeground(Color.WHITE);
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        chatWindowPanel.add(inputPanel, BorderLayout.SOUTH);

        ChatListScreen chatListPanel = new ChatListScreen(selectedChatRoom -> {
            chatRoomLabel.setText(selectedChatRoom + " Room");
            chatArea.setText("");
        });

        sendButton.addActionListener(e -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                chatArea.append("You: " + message + "\n");
                messageField.setText("");
            }
        });
        messageField.addActionListener(e -> sendButton.doClick());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatListPanel, chatWindowPanel);
        splitPane.setDividerLocation(200);
        chatsPanel.add(splitPane, BorderLayout.CENTER);

        return chatsPanel;
    }

    private JPanel createFeedsTab() {
        JPanel feedsPanel = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Feeds", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        headerLabel.setForeground(new Color(50, 205, 50));
        feedsPanel.add(headerLabel, BorderLayout.NORTH);

        FeedPage feedPage = new FeedPage();
        feedsPanel.add(feedPage, BorderLayout.CENTER);

        return feedsPanel;
    }

    private JPanel createProfileTab() {
        return new ProfilePage(user);
    }

    private JPanel createFileSharingTab() {
        JPanel fileSharingPanel = new JPanel(new BorderLayout());
        fileSharingPanel.add(new FileSharingUI(), BorderLayout.CENTER);
        return fileSharingPanel;
    }

    private JPanel createFriendRequestsTab() {
        JPanel friendRequestsPanel = new JPanel(new BorderLayout());
        friendRequestsPanel.add(new FriendRequestUI(), BorderLayout.CENTER);
        return friendRequestsPanel;
    }

    private JPanel createSettingsTab() {
        JPanel settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.add(new SettingsPage(), BorderLayout.CENTER);
        return settingsPanel;
    }

    public static void main(String[] args) {
    }
}
