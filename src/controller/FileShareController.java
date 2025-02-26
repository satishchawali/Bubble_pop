package controller;

import java.sql.Connection;
import database.FileShareDAO;
import model.FileShare;

public class FileShareController {
    private FileShareDAO fileShareDAO;

    public FileShareController(Connection conn) {
        this.fileShareDAO = new FileShareDAO(conn);
    }

    public void uploadFile(String fileName, String filePath, int userId) {
        try {
            fileShareDAO.saveFile(new FileShare(0, fileName, filePath, userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
