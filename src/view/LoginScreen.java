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

        setTitle("Login - Bubble Pop");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Login to Bubble Pop", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        titleLabel.setForeground(new Color(255, 105, 180));
        titleLabel.setBounds(200, 50, 400, 50);
        add(titleLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        emailLabel.setBounds(200, 150, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        emailField.setBounds(350, 150, 250, 30);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        passwordLabel.setBounds(200, 200, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        passwordField.setBounds(350, 200, 250, 30);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        loginButton.setBounds(250, 300, 150, 40);
        loginButton.setBackground(new Color(50, 205, 50));
        loginButton.setForeground(Color.WHITE);
        add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        registerButton.setBounds(450, 300, 150, 40);
        registerButton.setBackground(new Color(30, 144, 255));
        registerButton.setForeground(Color.WHITE);
        add(registerButton);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        backButton.setBounds(350, 380, 150, 40);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        add(backButton);

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