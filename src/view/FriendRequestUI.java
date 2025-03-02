package view;

import controller.FriendRequestController;
import model.FriendRequest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FriendRequestUI extends JPanel {
    private FriendRequestController controller;

    // Components for sending a friend request
    private JTextField txtSenderId;
    private JTextField txtReceiverId;
    private JButton btnSend;

    // Components for updating (accept/reject) a friend request
    private JTextField txtRequestId;
    private JButton btnAccept;
    private JButton btnReject;

    // Components for viewing friend requests
    private JTextField txtUserId;
    private JButton btnView;
    private JTextArea txtAreaResults;

    public FriendRequestUI() {
        // Initialize the controller (assumes your controller has the needed methods)
        controller = new FriendRequestController();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Label
        JLabel headerLabel = new JLabel("Friend Requests", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        headerLabel.setForeground(new Color(50, 205, 50));
        add(headerLabel, BorderLayout.NORTH);

        // Create a tabbed pane to separate functionalities
        JTabbedPane tabbedPane = new JTabbedPane();

        // ----------------- Send Request Panel -----------------
        JPanel panelSend = new JPanel(new GridLayout(3, 2, 10, 10));
        panelSend.setBackground(Color.WHITE);
        panelSend.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelSend.add(new JLabel("Sender ID:"));
        txtSenderId = new JTextField();
        panelSend.add(txtSenderId);
        panelSend.add(new JLabel("Receiver ID:"));
        txtReceiverId = new JTextField();
        panelSend.add(txtReceiverId);
        btnSend = new JButton("Send Friend Request");
        btnSend.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        btnSend.setBackground(new Color(70, 130, 180));
        btnSend.setForeground(Color.WHITE);
        panelSend.add(btnSend);
        panelSend.add(new JLabel()); // filler

        // ----------------- Update Request Panel -----------------
        JPanel panelUpdate = new JPanel(new GridLayout(2, 2, 10, 10));
        panelUpdate.setBackground(Color.WHITE);
        panelUpdate.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelUpdate.add(new JLabel("Request ID:"));
        txtRequestId = new JTextField();
        panelUpdate.add(txtRequestId);
        btnAccept = new JButton("Accept Request");
        btnAccept.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        btnAccept.setBackground(new Color(60, 179, 113));
        btnAccept.setForeground(Color.WHITE);
        btnReject = new JButton("Reject Request");
        btnReject.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        btnReject.setBackground(new Color(220, 20, 60));
        btnReject.setForeground(Color.WHITE);
        panelUpdate.add(btnAccept);
        panelUpdate.add(btnReject);

        // ----------------- View Requests Panel -----------------
        JPanel panelView = new JPanel(new BorderLayout(10, 10));
        panelView.setBackground(Color.WHITE);
        JPanel panelInput = new JPanel(new FlowLayout());
        panelInput.setBackground(Color.WHITE);
        panelInput.add(new JLabel("User ID:"));
        txtUserId = new JTextField(10);
        txtUserId.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        panelInput.add(txtUserId);
        btnView = new JButton("View Friend Requests");
        btnView.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        btnView.setBackground(new Color(70, 130, 180));
        btnView.setForeground(Color.WHITE);
        panelInput.add(btnView);
        panelView.add(panelInput, BorderLayout.NORTH);
        txtAreaResults = new JTextArea();
        txtAreaResults.setEditable(false);
        txtAreaResults.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(txtAreaResults);
        panelView.add(scrollPane, BorderLayout.CENTER);

        // Add all panels to the tabbed pane
        tabbedPane.addTab("Send Request", panelSend);
        tabbedPane.addTab("Update Request", panelUpdate);
        tabbedPane.addTab("View Requests", panelView);

        add(tabbedPane, BorderLayout.CENTER);

        // -------------- Event Handling --------------
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendFriendRequest();
            }
        });

        btnAccept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRequestStatus("ACCEPTED");
            }
        });

        btnReject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateRequestStatus("REJECTED");
            }
        });

        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewFriendRequests();
            }
        });
    }

    // Method to send a friend request.
    private void sendFriendRequest() {
        try {
            int senderId = Integer.parseInt(txtSenderId.getText().trim());
            int receiverId = Integer.parseInt(txtReceiverId.getText().trim());
            boolean success = controller.sendFriendRequest(senderId, receiverId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Friend request sent successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to send friend request (it may already exist).");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric IDs for sender and receiver.");
        }
    }

    // Method to update the status of a friend request.
    private void updateRequestStatus(String newStatus) {
        try {
            int requestId = Integer.parseInt(txtRequestId.getText().trim());
            boolean success;
            if ("ACCEPTED".equals(newStatus)) {
                success = controller.acceptFriendRequest(requestId);
            } else { // REJECTED
                success = controller.rejectFriendRequest(requestId);
            }
            if (success) {
                JOptionPane.showMessageDialog(this, "Friend request " + newStatus.toLowerCase() + " successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update friend request.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid request ID.");
        }
    }

    // Method to view friend requests for a specific user.
    private void viewFriendRequests() {
        try {
            int userId = Integer.parseInt(txtUserId.getText().trim());
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
            JOptionPane.showMessageDialog(this, "Please enter a valid user ID.");
        }
    }
}
