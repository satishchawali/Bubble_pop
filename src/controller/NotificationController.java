package controller;

import database.NotificationDAO;
import model.Notification;
import java.sql.Connection;

public class NotificationController {
    private NotificationDAO notificationDAO;

    public NotificationController(Connection conn) {
        this.notificationDAO = new NotificationDAO(conn);
    }

    public void sendNotification(String message, int userId) {
        try {
            notificationDAO.saveNotification(new Notification(0, message, userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}