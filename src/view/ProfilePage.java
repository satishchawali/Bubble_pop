package view;

import controller.ProfileController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class ProfilePage extends JPanel {
    private JTextField usernameField, emailField;
    private JButton updateButton, deleteButton, logoutButton, saveUpdate;
    private ProfileController profileController;
    private User user;

    public ProfilePage(User user) {
        this.user = user;
        this.profileController = new ProfileController();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Profile", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        headerLabel.setForeground(new Color(50, 205, 50));
        add(headerLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(user.getUsername(), 20);
        usernameField.setEditable(false);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(user.getEmail(), 20);
        emailField.setEditable(false);
        formPanel.add(emailField, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        updateButton = new JButton("Update Profile");
        deleteButton = new JButton("Delete Account");
        logoutButton = new JButton("Logout");
        saveUpdate = new JButton("Save");

        updateButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        deleteButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        logoutButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        saveUpdate.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            usernameField.setEditable(true);
            emailField.setEditable(true);
            if (!isButtonInPanel(buttonPanel, saveUpdate)) {
                buttonPanel.add(saveUpdate);
                buttonPanel.revalidate();
                buttonPanel.repaint();
            }
        });

        saveUpdate.addActionListener(e -> {
            String newUsername = usernameField.getText();
            String newEmail = emailField.getText();
            boolean updated = profileController.updateProfile(user.getId(), newUsername, newEmail, user.getPassword());
            if (updated) {
                JOptionPane.showMessageDialog(this, "Profile Updated Successfully!");
                usernameField.setEditable(false);
                emailField.setEditable(false);
                buttonPanel.remove(saveUpdate);
                buttonPanel.revalidate();
                buttonPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Profile Update Failed!");
            }
        });

        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = profileController.deleteProfile(user.getId());
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Account Deleted Successfully!");
                    new LoginScreen();
                    SwingUtilities.getWindowAncestor(this).dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to Delete Account!");
                }
            }
        });

        logoutButton.addActionListener(e -> {
            new LoginScreen();
            SwingUtilities.getWindowAncestor(this).dispose();
        });
    }
    private boolean isButtonInPanel(JPanel panel, JButton button) {
        for (Component comp : panel.getComponents()) {
            if (comp.equals(button)) {
                return true;
            }
        }
        return false;
    }
}
