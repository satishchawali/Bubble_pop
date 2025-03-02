package view;

import controller.FileShareController;
import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.List;

public class FileSharingUI extends JPanel {
    private JTextField userIdField;
    private JLabel fileLabel;
    private File selectedFile;
    private FileShareController fileShareController;
    private JButton browseButton;
    private JButton uploadButton;
    private JPanel dropZonePanel;

    public FileSharingUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Label
        JLabel headerLabel = new JLabel("File Sharing", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        headerLabel.setForeground(new Color(50, 205, 50));
        add(headerLabel, BorderLayout.NORTH);

        // Initialize FileShareController using a database connection.
        try {
            Connection conn = DatabaseConnection.getConnection();
            fileShareController = new FileShareController(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Main Content Panel using GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // User ID Field
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(userIdLabel, gbc);

        userIdField = new JTextField();
        userIdField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPanel.add(userIdField, gbc);

        // File Label
        JLabel fileLabelTitle = new JLabel("Selected File:");
        fileLabelTitle.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(fileLabelTitle, gbc);

        fileLabel = new JLabel("No file chosen");
        fileLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        fileLabel.setForeground(Color.GRAY);
        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPanel.add(fileLabel, gbc);

        // Drop Zone Panel for drag & drop file selection
        dropZonePanel = new JPanel();
        dropZonePanel.setPreferredSize(new Dimension(300, 100));
        dropZonePanel.setBackground(new Color(230, 230, 250));
        dropZonePanel.setBorder(BorderFactory.createDashedBorder(new Color(150, 150, 150), 2, 5, 2, true));
        JLabel dropLabel = new JLabel("Drag & Drop File Here", SwingConstants.CENTER);
        dropLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 16));
        dropZonePanel.setLayout(new BorderLayout());
        dropZonePanel.add(dropLabel, BorderLayout.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        contentPanel.add(dropZonePanel, gbc);
        gbc.gridwidth = 1;

        // Add DropTarget listener to enable drag & drop functionality.
        new DropTarget(dropZonePanel, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable transferable = dtde.getTransferable();
                    DataFlavor[] flavors = transferable.getTransferDataFlavors();
                    for (DataFlavor flavor : flavors) {
                        if (flavor.isFlavorJavaFileListType()) {
                            List<File> files = (List<File>) transferable.getTransferData(flavor);
                            if (!files.isEmpty()) {
                                selectedFile = files.get(0);
                                fileLabel.setText(selectedFile.getName());
                                dropZonePanel.setBackground(new Color(144, 238, 144));
                                break;
                            }
                        }
                    }
                    dtde.dropComplete(true);
                } catch (Exception ex) {
                    dtde.dropComplete(false);
                    ex.printStackTrace();
                }
            }
        });

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);

        browseButton = new JButton("Choose File");
        browseButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        browseButton.setBackground(new Color(70, 130, 180));
        browseButton.setForeground(Color.WHITE);
        browseButton.addActionListener(e -> selectFile());

        uploadButton = new JButton("Upload File");
        uploadButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        uploadButton.setBackground(new Color(60, 179, 113));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.addActionListener(e -> uploadFile());

        buttonsPanel.add(browseButton);
        buttonsPanel.add(uploadButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        contentPanel.add(buttonsPanel, gbc);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileLabel.setText(selectedFile.getName());
            dropZonePanel.setBackground(new Color(144, 238, 144));
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
            // Create destination directory if it doesn't exist.
            File destDir = new File("assets/shared_files");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            // Copy the file to the destination folder.
            File destFile = new File(destDir, selectedFile.getName());
            Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            // Save file data to the database.
            fileShareController.uploadFile(selectedFile.getName(), destFile.getAbsolutePath(), userId);
            JOptionPane.showMessageDialog(this, "File uploaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "File upload failed!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
