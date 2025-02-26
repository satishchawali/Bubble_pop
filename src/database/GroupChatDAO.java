package database;

import model.GroupChat;
import model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupChatDAO {
    private Connection conn;

    // Constructor - Establish Database Connection
    public GroupChatDAO() {
        try {
            this.conn = DatabaseConnection.getConnection(); // Assuming DatabaseConnection.java handles DB connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create a new group chat
    public boolean createGroup(GroupChat groupChat) {
        String sql = "INSERT INTO groups (group_name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, groupChat.getGroupName());
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int groupId = generatedKeys.getInt(1);
                    addGroupMembers(groupId, groupChat.getMemberIds());
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Add members to a group
    public boolean addGroupMembers(int groupId, List<Integer> memberIds) {
        String sql = "INSERT INTO group_members (group_id, user_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int memberId : memberIds) {
                stmt.setInt(1, groupId);
                stmt.setInt(2, memberId);
                stmt.addBatch();
            }
            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetch messages from a group chat
    public List<Message> getGroupMessages(int groupId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM group_messages WHERE group_id = ? ORDER BY timestamp ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, groupId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("id"),
                        rs.getInt("sender_id"),
                        groupId,
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

    // Save a message in a group chat
    public boolean saveGroupMessage(Message message) {
        String sql = "INSERT INTO group_messages (group_id, sender_id, content, timestamp, is_read) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, message.getReceiverId()); // Assuming receiverId stores the group ID
            stmt.setInt(2, message.getSenderId());
            stmt.setString(3, message.getContent());
            stmt.setTimestamp(4, new Timestamp(message.getTimestamp().getTime())); // Convert Date to Timestamp
            stmt.setBoolean(5, message.isRead());

            return stmt.executeUpdate() > 0; // Returns true if the insertion was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
