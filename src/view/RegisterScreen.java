package view;

import controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterScreen extends JFrame {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;
    private AuthController authController;

    public RegisterScreen() {
        authController = new AuthController();

        setTitle("Register - Bubble Pop");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Components
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");

        add(registerButton);
        add(backButton);

        // Register button action
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (authController.register(username, email, password)) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                new LoginScreen();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed!");
            }
        });

        // Back button action
        backButton.addActionListener(e -> {
            new LoginScreen();
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterScreen();
    }
}
