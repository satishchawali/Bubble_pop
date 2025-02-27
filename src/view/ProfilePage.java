package view;

import controller.ProfileController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class ProfilePage extends JFrame {
    private JTextField usernameField, emailField;
    private JButton updateButton, deleteButton, logoutButton, saveUpdate;
    private ProfileController profileController;
    private User user;

    public ProfilePage(User user) {
        this.user = user;
        this.profileController = new ProfileController();

        setTitle("Profile - Bubble Pop");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Components
        add(new JLabel("Username:"));
        usernameField = new JTextField(user.getUsername());
        usernameField.setEditable(false);
        add(usernameField);

        add(new JLabel("Email:"));
        emailField = new JTextField(user.getEmail());
        emailField.setEditable(false);
        add(emailField);

        updateButton = new JButton("Update Profile");
        deleteButton = new JButton("Delete Account");
        logoutButton = new JButton("Logout");
        saveUpdate = new JButton("Save");

        add(updateButton);
        add(deleteButton);
        add(logoutButton);

        // Update button action
        updateButton.addActionListener(e -> {
            add(saveUpdate);
            usernameField.setEditable(true);
            emailField.setEditable(true);
        });
        saveUpdate.addActionListener(e ->{
            String newUsername = usernameField.getText();
            String newEmail = emailField.getText();

            boolean updated = profileController.updateProfile(user.getId(), newUsername, newEmail, user.getPassword());
            if (updated) {
                JOptionPane.showMessageDialog(this, "Profile Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Profile Update Failed!");
            }
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = profileController.deleteProfile(user.getId());
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Account Deleted Successfully!");
                    new LoginScreen();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to Delete Account!");
                }
            }
        });

        // Logout button action
        logoutButton.addActionListener(e -> {
            new LoginScreen();
            dispose();
        });

        setVisible(true);
    }
}
