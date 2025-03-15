package view;

import controller.AuthController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, backButton;
    private AuthController authController;

    public LoginScreen() {
        authController = new AuthController();

        setTitle("Login - Flicksy");
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

        JLabel welcomeLabel = new JLabel("Welcome Back!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 150, 350, 50);
        leftPanel.add(welcomeLabel);

        // Right Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(450, 0, 450, 500);
        loginPanel.setBackground(new Color(226, 218, 214));
        loginPanel.setLayout(null);
        add(loginPanel);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        loginLabel.setBounds(175, 50, 100, 40);
        loginPanel.add(loginLabel);

        emailField = new JTextField();
        emailField.setBounds(75, 150, 300, 35);
        loginPanel.add(emailField);

        passwordField = new JPasswordField();
        passwordField.setBounds(75, 230, 300, 35);
        loginPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(75, 280, 300, 40);
        loginButton.setBackground(new Color(100, 130, 173));
        loginButton.setForeground(Color.WHITE);
        loginPanel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(160, 340, 120, 30);
        registerButton.setForeground(new Color(100, 130, 173));
        loginPanel.add(registerButton);

        backButton = new JButton("Back");
        backButton.setBounds(350, 400, 100, 30);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        loginPanel.add(backButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            User user = authController.login(email, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome, " + user.getUsername());
                new HomePage(user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Email or Password");
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterScreen();
            dispose();
        });

        backButton.addActionListener(e -> {
            new WelcomeScreen();
            dispose();
        });

        setVisible(true);
    }
}
