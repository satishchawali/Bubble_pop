
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Post;

public class PostDAO {
    private Connection conn;

    public PostDAO(Connection conn) {
        this.conn = conn;
    }

    public void addPost(Post post) throws Exception {
        String sql = "INSERT INTO posts (userId, content) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, post.getUserId());
        stmt.setString(2, post.getContent());
        stmt.executeUpdate();
    }
}
