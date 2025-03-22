package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class SettingsPage {
    private User user;
    private JLabel profilePic;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField aboutField;
    private JPanel contentPanel;
    private JFrame frame;
    private String profilePicPath;

    public SettingsPage(User user) {
        this.user = user;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Flicksy - Settings");
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(100, 130, 173));

        JPanel sidebar = new JPanel();
        sidebar.setBounds(0, 0, 200, 500);
        sidebar.setBackground(new Color(245, 237, 237));
        sidebar.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        JLabel settingsTitle = new JLabel("Settings");
        settingsTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        sidebar.add(settingsTitle);

        JButton accountButton = createSidebarButton("Account");
        JButton notificationsButton = createSidebarButton("Notifications");
        JButton logoutButton = createSidebarButton("Logout");

        sidebar.add(accountButton);
        sidebar.add(notificationsButton);
        sidebar.add(logoutButton);
        frame.add(sidebar);

        contentPanel = new JPanel();
        contentPanel.setBounds(220, 30, 550, 400);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(null);
        frame.add(contentPanel);

        accountButton.addActionListener(e -> showPanel("Account"));
        notificationsButton.addActionListener(e -> showPanel("Notifications"));
        logoutButton.addActionListener(e -> logout());

        showPanel("Account");
        frame.setVisible(true);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void showPanel(String panelName) {
        contentPanel.removeAll();

        JLabel title = new JLabel(panelName);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBounds(20, 10, 200, 30);
        contentPanel.add(title);

        if (panelName.equals("Account")) {
            JLabel nameLabel = new JLabel("Name:");
            nameLabel.setBounds(50, 50, 100, 30);
            contentPanel.add(nameLabel);

            nameField = new JTextField(user.getUsername());
            nameField.setBounds(150, 50, 200, 30);
            contentPanel.add(nameField);

            JLabel emailLabel = new JLabel("Email:");
            emailLabel.setBounds(50, 100, 100, 30);
            contentPanel.add(emailLabel);

            emailField = new JTextField(user.getEmail());
            emailField.setBounds(150, 100, 200, 30);
            contentPanel.add(emailField);

            JLabel aboutLabel = new JLabel("About:");
            aboutLabel.setBounds(50, 150, 100, 30);
            contentPanel.add(aboutLabel);

            aboutField = new JTextField(user.getAbout());
            aboutField.setBounds(150, 150, 200, 60);
            contentPanel.add(aboutField);

            JLabel profileLabel = new JLabel("Profile Picture:");
            profileLabel.setBounds(50, 230, 120, 30);
            contentPanel.add(profileLabel);

            profilePic = new JLabel();
            profilePic.setBounds(300, 230, 80, 80);
            profilePic.setOpaque(true);
            profilePic.setBackground(Color.LIGHT_GRAY);
            profilePic.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

            loadProfilePicture();

            profilePic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "jpeg"));
                    int option = fileChooser.showOpenDialog(null);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        if (UserController.saveProfilePicture(user.getId(), file)) {
                            loadProfilePicture();
                            JOptionPane.showMessageDialog(frame, "Profile picture updated!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to update profile picture.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            contentPanel.add(profilePic);
            // Back Button
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
            backButton.setBounds(20, 20, 100, 40); // Adjust position and size if needed
            backButton.setBackground(new Color(100, 130, 173));
            backButton.setFocusPainted(false);
            backButton.setForeground(Color.WHITE);
            backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            backButton.addActionListener(e -> {
                frame.dispose();
                new HomePage(user);
            });
            frame.add(backButton);

            JButton updateButton = new JButton("Update");
            updateButton.setBounds(150, 330, 100, 30);
            updateButton.addActionListener(e -> {
                String newName = nameField.getText();
                String newEmail = emailField.getText();
                String newAbout = aboutField.getText();

                if (UserController.updateUserInfo(user.getId(), newName, newEmail, newAbout)) {
                    JOptionPane.showMessageDialog(frame, "Profile updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to update profile.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            contentPanel.add(updateButton);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to logout?", "Logout",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            frame.dispose();
            new LoginScreen();
        }
    }

    private void loadProfilePicture() {
        profilePicPath = "profile_pics/" + user.getId() + ".jpg";
        File profileFile = new File(profilePicPath);
        if (profileFile.exists()) {
            ImageIcon icon = new ImageIcon(
                    new ImageIcon(profilePicPath).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            profilePic.setIcon(icon);
        } else {
            profilePic.setIcon(null);
        }
    }
}
