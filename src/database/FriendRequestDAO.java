package database;

import model.FriendRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.sql.Timestamp;   // Needed for converting java.util.Date to SQL Timestamp
import java.util.ArrayList;
import java.util.List;

/**
 * FriendRequestDAO handles CRUD operations for FriendRequest objects.
 * It interacts with the 'friend_requests' table in the database.
 */
public class FriendRequestDAO {

    /**
     * Creates a new friend request in the database.
     *
     * @param request A FriendRequest object containing senderId, receiverId, status, and requestDate.
     * @return true if the insertion was successful; false otherwise.
     */
    public boolean createFriendRequest(FriendRequest request) {
        String sql = "INSERT INTO friend_requests (sender_id, receiver_id, status, request_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, request.getSenderId());
            stmt.setInt(2, request.getReceiverId());
            stmt.setString(3, request.getStatus());
            // Convert the requestDate (java.util.Date) into a Timestamp (java.sql.Timestamp)
            stmt.setTimestamp(4, new Timestamp(request.getRequestDate().getTime()));

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

    /**
     * Updates the status of an existing friend request.
     *
     * @param requestId The ID of the friend request to update.
     * @param newStatus The new status (e.g., "accepted", "declined").
     * @return true if the update was successful; false otherwise.
     */
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

    /**
     * Retrieves a friend request by its ID.
     *
     * @param id The unique identifier of the friend request.
     * @return A FriendRequest object if found; otherwise, null.
     */
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

    /**
     * Deletes a friend request from the database.
     *
     * @param id The ID of the friend request to delete.
     * @return true if the deletion was successful; false otherwise.
     */
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

    /**
     * Retrieves all friend requests associated with a specific user.
     * This includes both requests sent by and received by the user.
     *
     * @param userId The user ID to fetch friend requests for.
     * @return A list of FriendRequest objects.
     */
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
}
