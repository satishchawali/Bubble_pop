package view;

import controller.AuthController;
import javax.swing.*;
import java.awt.*;

public class RegisterScreen extends JFrame {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;
    private AuthController authController;

    public RegisterScreen() {
        authController = new AuthController();

        setTitle("Register - Flicksy");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 450, 500);
        leftPanel.setBackground(new Color(100, 130, 173));
        leftPanel.setLayout(null);
        add(leftPanel);

        JLabel welcomeLabel = new JLabel("Join Flicksy!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 150, 350, 50);
        leftPanel.add(welcomeLabel);

        // Right Panel
        JPanel registerPanel = new JPanel();
        registerPanel.setBounds(450, 0, 450, 500);
        registerPanel.setBackground(new Color(226, 218, 214));
        registerPanel.setLayout(null);
        add(registerPanel);

        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        signUpLabel.setBounds(175, 40, 120, 40);
        registerPanel.add(signUpLabel);

        usernameField = new JTextField();
        usernameField.setBounds(75, 130, 300, 40);
        registerPanel.add(usernameField);

        emailField = new JTextField();
        emailField.setBounds(75, 210, 300, 40);
        registerPanel.add(emailField);

        passwordField = new JPasswordField();
        passwordField.setBounds(75, 290, 300, 40);
        registerPanel.add(passwordField);

        registerButton = new JButton("Sign Up");
        registerButton.setBounds(75, 350, 300, 45);
        registerButton.setBackground(new Color(100, 130, 173));
        registerButton.setForeground(Color.WHITE);
        registerPanel.add(registerButton);

        backButton = new JButton("Back");
        backButton.setBounds(350, 400, 100, 30);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        registerPanel.add(backButton);

        registerButton.addActionListener(e -> {
            new LoginScreen();
            dispose();
        });

        backButton.addActionListener(e -> {
            new LoginScreen();
            dispose();
        });

        setVisible(true);
    }
}
