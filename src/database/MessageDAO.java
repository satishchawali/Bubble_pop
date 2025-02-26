package database;

import model.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private Connection conn;

    // Constructor - Establishes Database Connection
    public MessageDAO() {
        try {
            this.conn = DatabaseConnection.getConnection(); // Assuming DatabaseConnection.java handles DB connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Save a message to the database
    public boolean saveMessage(Message message) {
        String sql = "INSERT INTO messages (sender_id, receiver_id, content, timestamp, is_read) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, message.getSenderId());
            stmt.setInt(2, message.getReceiverId());
            stmt.setString(3, message.getContent());
            stmt.setTimestamp(4, new Timestamp(message.getTimestamp().getTime())); // Convert Date to Timestamp
            stmt.setBoolean(5, message.isRead());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // If at least one row is inserted, return true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Retrieve chat history between two users
    public List<Message> getMessagesBetweenUsers(int user1Id, int user2Id) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY timestamp ASC";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user1Id);
            stmt.setInt(2, user2Id);
            stmt.setInt(3, user2Id);
            stmt.setInt(4, user1Id);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("id"),
                    rs.getInt("sender_id"),
                    rs.getInt("receiver_id"),
                    rs.getString("content"),
                    rs.getTimestamp("timestamp"),
                    rs.getBoolean("is_read")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
