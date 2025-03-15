package view;

import controller.FriendRequestController;
import model.FriendRequest;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class FriendRequestUI extends JPanel {
    private FriendRequestController controller;
    private int currentUserId; // Store logged-in user's ID

    private JPanel usersPanel;
    private JPanel requestsPanel;
    private JScrollPane usersScrollPane;
    private JScrollPane requestsScrollPane;
    public FriendRequestUI() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Friend Requests Panel", JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }
    
    public FriendRequestUI(int userId) {
        this.currentUserId = userId;
        this.controller = new FriendRequestController();
        initializeUI();
        loadUsers();
        loadFriendRequests();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("Friend Requests", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        headerLabel.setForeground(new Color(50, 205, 50));
        add(headerLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 10));

        usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        usersScrollPane = new JScrollPane(usersPanel);
        usersScrollPane.setBorder(BorderFactory.createTitledBorder("All Users"));
        contentPanel.add(usersScrollPane);

        requestsPanel = new JPanel();
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
        requestsScrollPane = new JScrollPane(requestsPanel);
        requestsScrollPane.setBorder(BorderFactory.createTitledBorder("Pending Requests"));
        contentPanel.add(requestsScrollPane);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void loadUsers() {
        usersPanel.removeAll();
        List<User> allUsers = controller.getAllUsersExcept(currentUserId);

        for (User user : allUsers) {
            JPanel userRow = new JPanel(new BorderLayout());
            userRow.setBorder(new EmptyBorder(5, 5, 5, 5));

            JLabel lblUser = new JLabel(user.getUsername() + " (ID: " + user.getId() + ")");
            JButton btnSend = createSmallButton("Send Request", new Color(70, 130, 180));
            btnSend.addActionListener(e -> sendFriendRequest(user.getId()));

            userRow.add(lblUser, BorderLayout.WEST);
            userRow.add(btnSend, BorderLayout.EAST);

            usersPanel.add(userRow);
        }

        usersPanel.revalidate();
        usersPanel.repaint();
    }

    private void loadFriendRequests() {
        requestsPanel.removeAll();
        List<FriendRequest> pendingRequests = controller.getPendingRequests(currentUserId);

        for (FriendRequest request : pendingRequests) {
            JPanel requestRow = new JPanel(new BorderLayout());
            requestRow.setBorder(new EmptyBorder(5, 5, 5, 5));

            JLabel lblRequest = new JLabel("From User ID: " + request.getSenderId());
            JButton btnAccept = createSmallButton("Accept", new Color(60, 179, 113));
            JButton btnReject = createSmallButton("Reject", new Color(220, 20, 60));

            btnAccept.addActionListener(e -> updateRequestStatus(request.getId(), "ACCEPTED"));
            btnReject.addActionListener(e -> updateRequestStatus(request.getId(), "REJECTED"));

            JPanel buttonsPanel = new JPanel();
            buttonsPanel.add(btnAccept);
            buttonsPanel.add(btnReject);

            requestRow.add(lblRequest, BorderLayout.WEST);
            requestRow.add(buttonsPanel, BorderLayout.EAST);

            requestsPanel.add(requestRow);
        }

        requestsPanel.revalidate();
        requestsPanel.repaint();
    }

    private JButton createSmallButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(120, 30));
        return button;
    }

    private void sendFriendRequest(int receiverId) {
        boolean success = controller.sendFriendRequest(currentUserId, receiverId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Friend request sent!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to send request.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRequestStatus(int requestId, String status) {
        boolean success = status.equals("ACCEPTED") ? controller.acceptFriendRequest(requestId)
                                                    : controller.rejectFriendRequest(requestId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Request " + status.toLowerCase() + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadFriendRequests(); // Refresh requests list
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update request.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
