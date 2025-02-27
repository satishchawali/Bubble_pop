package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;   // Needed for converting java.util.Date to SQL Timestamp
import java.util.Date;
import java.util.List;
import model.FriendRequest;

public class FriendRequestDAO {
    public boolean createFriendRequest(FriendRequest request) {
        String sql = "INSERT INTO friend_requests (sender_id, receiver_id, status, request_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            stmt.setInt(1, request.getSenderId());
            stmt.setInt(2, request.getReceiverId());
            stmt.setString(3, request.getStatus());
    
            // Handle null requestDate by setting it to the current timestamp
            Date currentDate = request.getRequestDate() != null ? request.getRequestDate() : new Date();
            stmt.setTimestamp(4, new Timestamp(currentDate.getTime()));
    
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

    public boolean updateFriendRequestStatus(int requestId, String newStatus) {
        String sql = "UPDATE friend_requests SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, requestId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public FriendRequest getFriendRequestById(int id) {
        String sql = "SELECT * FROM friend_requests WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // rs.getTimestamp returns a java.sql.Timestamp, which is a subclass of java.util.Date
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

    public boolean deleteFriendRequest(int id) {
        String sql = "DELETE FROM friend_requests WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
                        rs.getTimestamp("request_date")
                    );
                    requests.add(request);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public boolean requestExists(int senderId, int receiverId) {
        String sql = "SELECT COUNT(*) FROM friend_requests WHERE sender_id = ? AND receiver_id = ? AND status = 'PENDING'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // If count > 0, request already exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
}
