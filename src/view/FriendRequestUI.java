package view;

import controller.FriendRequestController;
import model.FriendRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FriendRequestUI extends JFrame {

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
        // Initialize the controller (assumes controller has the needed methods)
        controller = new FriendRequestController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Friend Request Manager");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a tabbed pane to separate functions
        JTabbedPane tabbedPane = new JTabbedPane();

        // ----------------- Send Request Panel -----------------
        JPanel panelSend = new JPanel(new GridLayout(3, 2, 10, 10));
        panelSend.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSend.add(new JLabel("Sender ID:"));
        txtSenderId = new JTextField();
        panelSend.add(txtSenderId);
        panelSend.add(new JLabel("Receiver ID:"));
        txtReceiverId = new JTextField();
        panelSend.add(txtReceiverId);
        btnSend = new JButton("Send Friend Request");
        panelSend.add(btnSend);
        panelSend.add(new JLabel()); // filler

        // ----------------- Update Request Panel -----------------
        JPanel panelUpdate = new JPanel(new GridLayout(2, 2, 10, 10));
        panelUpdate.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelUpdate.add(new JLabel("Request ID:"));
        txtRequestId = new JTextField();
        panelUpdate.add(txtRequestId);
        btnAccept = new JButton("Accept Request");
        btnReject = new JButton("Reject Request");
        panelUpdate.add(btnAccept);
        panelUpdate.add(btnReject);

        // ----------------- View Requests Panel -----------------
        JPanel panelView = new JPanel(new BorderLayout(10, 10));
        JPanel panelInput = new JPanel(new FlowLayout());
        panelInput.add(new JLabel("User ID:"));
        txtUserId = new JTextField(10);
        panelInput.add(txtUserId);
        btnView = new JButton("View Friend Requests");
        panelInput.add(btnView);
        panelView.add(panelInput, BorderLayout.NORTH);
        txtAreaResults = new JTextArea();
        txtAreaResults.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaResults);
        panelView.add(scrollPane, BorderLayout.CENTER);

        // Add all panels to the tabbed pane
        tabbedPane.addTab("Send Request", panelSend);
        tabbedPane.addTab("Update Request", panelUpdate);
        tabbedPane.addTab("View Requests", panelView);

        add(tabbedPane, BorderLayout.CENTER);

        // -------------- Event Handling using AWT Listeners --------------
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

    // Calls the controller to send a friend request.
    // Note: Since your current controller uses a null request date,
    // you might consider modifying it to use the current date (new Date()).
    private void sendFriendRequest() {
        try {
            int senderId = Integer.parseInt(txtSenderId.getText().trim());
            int receiverId = Integer.parseInt(txtReceiverId.getText().trim());
            // Optionally, set the request date in your controller or DAO.
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

    // Updates the friend request status (accept or reject) using the controller.
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

    // Views friend requests for a specific user.
    // (Assumes you add the corresponding method in the controller.)
    private void viewFriendRequests() {
        try {
            int userId = Integer.parseInt(txtUserId.getText().trim());
            // This method must be implemented in your FriendRequestController.
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

    public static void main(String[] args) {
        // Ensure thread safety with SwingUtilities.invokeLater
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FriendRequestUI().setVisible(true);
            }
        });
    }
}
