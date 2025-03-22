package controller;

import model.User;
import database.DatabaseConnection;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserController {

    public static boolean updateUserInfo(int userId, String newUsername, String newEmail, String about) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET username = ?, email = ?, about = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newUsername);
            stmt.setString(2, newEmail);
            stmt.setString(3, about);
            stmt.setInt(4, userId);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveProfilePicture(int userId, File selectedFile) {
        try {
            // Define the folder to store images
            File destFolder = new File("profile_pics/");
            if (!destFolder.exists()) {
                destFolder.mkdirs();
            }

            // Rename file to user ID
            File destFile = new File(destFolder, userId + ".jpg");
            Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Update database
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "UPDATE users SET profile_picture = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, userId + ".jpg");
                stmt.setInt(2, userId);

                return stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getProfilePicture(int userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT profile_picture FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("profile_picture");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
