package view;

import controller.FriendRequestController;
import model.FriendRequest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class FriendRequestUI extends JPanel {
    private FriendRequestController controller;
    private JTextField txtSenderId, txtReceiverId, txtRequestId, txtUserId;
    private JButton btnSend, btnAccept, btnReject, btnView;
    private JTextArea txtAreaResults;

    public FriendRequestUI() {
        controller = new FriendRequestController();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("Friend Requests", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        headerLabel.setForeground(new Color(50, 205, 50));
        add(headerLabel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Send Request", createSendPanel());
        tabbedPane.addTab("Manage Requests", createManagePanel());
        tabbedPane.addTab("View Requests", createViewPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createSendPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        txtSenderId = new JTextField(10);
        txtReceiverId = new JTextField(10);
        btnSend = createSmallButton("Send", new Color(70, 130, 180));

        btnSend.addActionListener(e -> sendFriendRequest());

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Sender ID:"), gbc);
        gbc.gridx = 1;
        panel.add(txtSenderId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Receiver ID:"), gbc);
        gbc.gridx = 1;
        panel.add(txtReceiverId, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(btnSend, gbc);

        return panel;
    }

    private JPanel createManagePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        txtRequestId = new JTextField(10);
        btnAccept = createSmallButton("Accept", new Color(60, 179, 113));
        btnReject = createSmallButton("Reject", new Color(220, 20, 60));

        btnAccept.addActionListener(e -> updateRequestStatus("ACCEPTED"));
        btnReject.addActionListener(e -> updateRequestStatus("REJECTED"));

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Request ID:"), gbc);
        gbc.gridx = 1;
        panel.add(txtRequestId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(btnAccept, gbc);
        gbc.gridx = 1;
        panel.add(btnReject, gbc);

        return panel;
    }

    private JPanel createViewPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        JPanel panelInput = new JPanel(new FlowLayout());
        panelInput.setBackground(Color.WHITE);

        txtUserId = new JTextField(10);
        btnView = createSmallButton("View", new Color(70, 130, 180));

        btnView.addActionListener(e -> viewFriendRequests());

        panelInput.add(new JLabel("User ID:"));
        panelInput.add(txtUserId);
        panelInput.add(btnView);
        panel.add(panelInput, BorderLayout.NORTH);

        txtAreaResults = new JTextArea(10, 30);
        txtAreaResults.setEditable(false);
        txtAreaResults.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(txtAreaResults);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JButton createSmallButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 30)); // Small size
        return button;
    }

    private void sendFriendRequest() {
        try {
            int senderId = Integer.parseInt(txtSenderId.getText().trim());
            int receiverId = Integer.parseInt(txtReceiverId.getText().trim());
            boolean success = controller.sendFriendRequest(senderId, receiverId);
            showMessage(success, "Friend request sent successfully!", "Failed to send friend request.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric IDs.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRequestStatus(String newStatus) {
        try {
            int requestId = Integer.parseInt(txtRequestId.getText().trim());
            boolean success = newStatus.equals("ACCEPTED") ? controller.acceptFriendRequest(requestId) : controller.rejectFriendRequest(requestId);
            showMessage(success, "Friend request " + newStatus.toLowerCase() + " successfully.", "Failed to update friend request.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid request ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewFriendRequests() {
        try {
            int userId = Integer.parseInt(txtUserId.getText().trim());
            txtAreaResults.setText(""); // Clear previous results
            List<FriendRequest> requests = controller.getFriendRequestsForUser(userId);
            if (requests.isEmpty()) {
                txtAreaResults.setText("No friend requests found for user ID: " + userId);
            } else {
                StringBuilder sb = new StringBuilder();
                for (FriendRequest req : requests) {
                    sb.append(req.toString()).append("\n");
                }
                txtAreaResults.setText(sb.toString());
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid user ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMessage(boolean success, String successMsg, String errorMsg) {
        JOptionPane.showMessageDialog(this, success ? successMsg : errorMsg, "Notification", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}
