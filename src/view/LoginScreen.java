package view;
import controller.AuthController;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private AuthController authController;

    public LoginScreen() {
        authController = new AuthController();

        setTitle("Login - Bubble Pop");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Components
        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        add(loginButton);
        add(registerButton);

        // Login button action
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            User user = authController.login(email, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome, " + user.getUsername());
                new ProfilePage(user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Email or Password");
            }
        });

        // Register button action
        registerButton.addActionListener(e -> {
            new RegisterScreen();
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}
