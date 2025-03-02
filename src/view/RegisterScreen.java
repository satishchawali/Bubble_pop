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

        setTitle("Register - Bubble Pop");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel titleLabel = new JLabel("Create Your Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        titleLabel.setForeground(new Color(255, 105, 180));
        titleLabel.setBounds(150, 50, 500, 50);
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        usernameLabel.setBounds(200, 120, 150, 30);
        add(usernameLabel);
        
        usernameField = new JTextField();
        usernameField.setBounds(350, 120, 250, 30);
        add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        emailLabel.setBounds(200, 170, 150, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(350, 170, 250, 30);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        passwordLabel.setBounds(200, 220, 150, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(350, 220, 250, 30);
        add(passwordField);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        registerButton.setBounds(250, 300, 150, 50);
        registerButton.setBackground(new Color(50, 205, 50));
        registerButton.setForeground(Color.WHITE);
        add(registerButton);

        backButton = new JButton("Back to Login");
        backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        backButton.setBounds(450, 300, 200, 50);
        backButton.setBackground(Color.LIGHT_GRAY);
        add(backButton);

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