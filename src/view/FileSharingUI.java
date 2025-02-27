package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import controller.FileShareController;
import database.DatabaseConnection;

public class FileSharingUI extends JFrame {
    private JTextField userIdField;
    private JLabel fileLabel;
    private File selectedFile;
    private FileShareController fileShareController;

    public FileSharingUI() {
        setTitle("File Sharing System");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        try {
            Connection conn = DatabaseConnection.getConnection();
            fileShareController = new FileShareController(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // UI Components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userIdLabel = new JLabel("User ID:");
        userIdField = new JTextField();

        JLabel fileLabelTitle = new JLabel("Selected File:");
        fileLabel = new JLabel("No file chosen");

        JButton browseButton = new JButton("Choose File");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });

        JButton uploadButton = new JButton("Upload File");
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadFile();
            }
        });

        // Adding components
        panel.add(userIdLabel);
        panel.add(userIdField);
        panel.add(fileLabelTitle);
        panel.add(fileLabel);
        panel.add(browseButton);
        panel.add(uploadButton);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileLabel.setText(selectedFile.getName());
        }
    }

    private void uploadFile() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please select a file first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid User ID!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Create the directory if it doesn't exist
            File destDir = new File("assets/shared_files");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            // Copy the file to the destination folder
            File destFile = new File(destDir, selectedFile.getName());
            Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Save file data to database
            fileShareController.uploadFile(selectedFile.getName(), destFile.getAbsolutePath(), userId);
            JOptionPane.showMessageDialog(this, "File uploaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "File upload failed!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileSharingUI());
    }
}
