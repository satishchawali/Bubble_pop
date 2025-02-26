
package controller;

import database.PostDAO;
import model.Post;
import java.sql.Connection;

public class PostController {
    private PostDAO postDAO;

    public PostController(Connection conn) {
        this.postDAO = new PostDAO(conn);
    }

    public void createPost(String content, int userId) {
        try {
            postDAO.addPost(new Post(0, content, userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
