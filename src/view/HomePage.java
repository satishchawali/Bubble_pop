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

    // Home Tab: Simple welcome screen.
    private JPanel createHomeTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(50, 205, 50));
        panel.add(welcomeLabel, BorderLayout.CENTER);
        return panel;
    }

    // Chats Tab: Contains a chat list and a chat window (split view).
    private JPanel createChatsTab() {
        JPanel chatsPanel = new JPanel(new BorderLayout());

        // Left panel: Chat list
        DefaultListModel<String> chatListModel = new DefaultListModel<>();
        chatListModel.addElement("General Chat");
        chatListModel.addElement("Sports Chat");
        chatListModel.addElement("Movies Chat");
        chatListModel.addElement("Music Chat");
        JList<String> chatList = new JList<>(chatListModel);
        chatList.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane chatListScrollPane = new JScrollPane(chatList);
        chatListScrollPane.setPreferredSize(new Dimension(200, 0));

        // Right panel: Chat window
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

        // Update chat window when a chat room is selected.
        chatList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedChat = chatList.getSelectedValue();
                if (selectedChat != null) {
                    chatRoomLabel.setText(selectedChat + " Room");
                    chatArea.setText(""); // Clear previous messages.
                    // TODO: Load chat history for the selected room.
                }
            }
        });

        // Action to send message.
        ActionListener sendAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText().trim();
                if (!message.isEmpty()) {
                    chatArea.append("You: " + message + "\n");
                    messageField.setText("");
                    // TODO: Integrate real-time messaging by sending the message via WebSocket or
                    // similar.
                }
            }
        };
        sendButton.addActionListener(sendAction);
        messageField.addActionListener(sendAction);

        // Combine chat list and chat window in a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatListScrollPane, chatWindowPanel);
        splitPane.setDividerLocation(200);
        chatsPanel.add(splitPane, BorderLayout.CENTER);
        return chatsPanel;
    }

    // Feeds Tab: Feed content with FeedPage panel.
    private JPanel createFeedsTab() {
        JPanel feedsPanel = new JPanel(new BorderLayout());

        // Optional: Add a header label for the feeds tab.
        JLabel headerLabel = new JLabel("Feeds", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        headerLabel.setForeground(new Color(50, 205, 50));
        feedsPanel.add(headerLabel, BorderLayout.NORTH);

        // Add the FeedPage panel to display feed posts.
        FeedPage feedPage = new FeedPage();
        feedsPanel.add(feedPage, BorderLayout.CENTER);

        return feedsPanel;
    }

    // Profile Tab: Display ProfilePanel in HomePage.
    private JPanel createProfileTab() {
        // Assuming 'user' is a member variable in HomePage.
        return new ProfilePage(user);
    }

    // File Sharing Tab: Display FileSharingPanel in HomePage.
    private JPanel createFileSharingTab() {
        JPanel fileSharingPanel = new JPanel(new BorderLayout());
        fileSharingPanel.add(new FileSharingUI(), BorderLayout.CENTER);
        return fileSharingPanel;
    }
// Friend Requests Tab: Display FriendRequestsPanel in HomePage.
private JPanel createFriendRequestsTab() {
    JPanel friendRequestsPanel = new JPanel(new BorderLayout());
    friendRequestsPanel.add(new FriendRequestUI(), BorderLayout.CENTER);
    return friendRequestsPanel;
}

// Settings Tab: Display SettingsPage in HomePage.
private JPanel createSettingsTab() {
    JPanel settingsPanel = new JPanel(new BorderLayout());
    settingsPanel.add(new SettingsPage(), BorderLayout.CENTER);
    return settingsPanel;
}


    public static void main(String[] args) {
        // For demonstration, create a dummy user. Adjust this according to your actual
        // User class.
        User dummyUser = new User();
        new HomePage(dummyUser);
    }
}
