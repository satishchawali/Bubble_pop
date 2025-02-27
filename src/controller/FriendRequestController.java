package controller;

import database.DatabaseConnection;
import database.FriendRequestDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.FriendRequest;

public class FriendRequestController {
    private FriendRequestDAO friendRequestDAO;

    public FriendRequestController() {
        this.friendRequestDAO = new FriendRequestDAO();
    }

    // Send a friend request
    public boolean sendFriendRequest(int senderId, int receiverId) {
        if (friendRequestDAO.requestExists(senderId, receiverId)) {
            System.out.println("Friend request already sent.");
            return false;
        }
        FriendRequest request = new FriendRequest(0, senderId, receiverId, "PENDING", null);
        return friendRequestDAO.createFriendRequest(request);
    }

    // Accept a friend request
    public boolean acceptFriendRequest(int requestId) {
        return friendRequestDAO.updateFriendRequestStatus(requestId, "ACCEPTED");
    }

    // Reject a friend request
    public boolean rejectFriendRequest(int requestId) {
        return friendRequestDAO.updateFriendRequestStatus(requestId, "REJECTED");
    }



public List<FriendRequest> getFriendRequestsForUser(int userId) {
    List<FriendRequest> requests = new ArrayList<>();
    String sql = "SELECT * FROM friend_requests WHERE sender_id = ? OR receiver_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, userId);
        stmt.setInt(2, userId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                FriendRequest request = new FriendRequest(
                    rs.getInt("id"),
                    rs.getInt("sender_id"),
                    rs.getInt("receiver_id"),
                    rs.getString("status"),
                    rs.getTimestamp("request_date") // Converts SQL Timestamp to Java Date
                );
                requests.add(request);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return requests;
}

}
   
