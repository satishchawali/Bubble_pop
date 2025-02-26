
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Notification;

public class NotificationDAO {
    private Connection conn;

    public NotificationDAO(Connection conn) {
        this.conn = conn;
    }
    public void saveNotification(Notification notification) throws Exception {
        String sql = "INSERT INTO notifications (message, userId) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, notification.getMessage());
        stmt.setInt(2, notification.getUserId());
        stmt.executeUpdate();
    }
    

    public List<Notification> getNotificationsByUser(int userId) throws Exception {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE userId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            notifications.add(new Notification(rs.getInt("id"), rs.getString("message"), rs.getInt("userId")));
        }
        return notifications;
    }
}
