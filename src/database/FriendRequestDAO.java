package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.FriendRequest;

public class FriendRequestDAO {
    
    // ✅ Create a new friend request
    public boolean createFriendRequest(FriendRequest request) {
        String sql = "INSERT INTO friend_requests (sender_id, receiver_id, status, request_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            stmt.setInt(1, request.getSenderId());
            stmt.setInt(2, request.getReceiverId());
            stmt.setString(3, request.getStatus());
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // Use current timestamp
    
            int affectedRows = stmt.executeUpdate();
    
            if (affectedRows == 0) {
                throw new SQLException("Creating friend request failed, no rows affected.");
            }
    
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    request.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating friend request failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Get all PENDING friend requests for a user
    public List<FriendRequest> getPendingRequestsForUser(int userId) {
        List<FriendRequest> pendingRequests = new ArrayList<>();
        String sql = "SELECT * FROM friend_requests WHERE receiver_id = ? AND status = 'PENDING'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, userId);
    
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pendingRequests.add(new FriendRequest(
                        rs.getInt("id"),
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getString("status"),
                        rs.getTimestamp("request_date")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pendingRequests;
    }

    // ✅ Update the status of a friend request
    public boolean updateFriendRequestStatus(int requestId, String newStatus) {
        String sql = "UPDATE friend_requests SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, requestId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Retrieve a specific friend request by ID
    public FriendRequest getFriendRequestById(int id) {
        String sql = "SELECT * FROM friend_requests WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new FriendRequest(
                        rs.getInt("id"),
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getString("status"),
                        rs.getTimestamp("request_date")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Delete a friend request
    public boolean deleteFriendRequest(int id) {
        String sql = "DELETE FROM friend_requests WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Get all friend requests for a specific user (sent or received)
    public List<FriendRequest> getFriendRequestsForUser(int userId) {
        List<FriendRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM friend_requests WHERE sender_id = ? OR receiver_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    requests.add(new FriendRequest(
                        rs.getInt("id"),
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getString("status"),
                        rs.getTimestamp("request_date")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    // ✅ Check if a pending request already exists between two users
    public boolean requestExists(int senderId, int receiverId) {
        String sql = "SELECT COUNT(*) FROM friend_requests WHERE sender_id = ? AND receiver_id = ? AND status = 'PENDING'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
