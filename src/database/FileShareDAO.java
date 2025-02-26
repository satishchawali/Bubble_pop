
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.FileShare;
import model.Notification;
import model.Post;

public class FileShareDAO {
    private Connection conn;

    public FileShareDAO(Connection conn) {
        this.conn = conn;
    }

    public void saveFile(FileShare file) throws Exception {
        String sql = "INSERT INTO files (fileName, filePath, userId) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, file.getFileName());
        stmt.setString(2, file.getFilePath());
        stmt.setInt(3, file.getUserId());
        stmt.executeUpdate();
    }
}
