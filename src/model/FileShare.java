package model;

public class FileShare {
    private int id;
    private String fileName;
    private String filePath;
    private int userId;

    public FileShare(int id, String fileName, String filePath, int userId) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.userId = userId;
    }

    public int getId() { return id; }
    public String getFileName() { return fileName; }
    public String getFilePath() { return filePath; }
    public int getUserId() { return userId; }
}
