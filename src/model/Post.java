
package model;

public class Post {
    private int id;
    private String content;
    private int userId;

    public Post(int id, String content, int userId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
    }

    public int getId() { return id; }
    public String getContent() { return content; }
    public int getUserId() { return userId; }
}