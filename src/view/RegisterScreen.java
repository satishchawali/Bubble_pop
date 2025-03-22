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

        // Left Panel (Branding)
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 450, 500);
        leftPanel.setBackground(new Color(100, 130, 173));
        leftPanel.setLayout(null);
        add(leftPanel);

        JLabel welcomeLabel = new JLabel("Welcome to Flicksy", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 150, 350, 50);
        leftPanel.add(welcomeLabel);

        JLabel sloganLabel = new JLabel("Flick. Chat. Connect.", SwingConstants.CENTER);
        sloganLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        sloganLabel.setForeground(Color.WHITE);
        sloganLabel.setBounds(50, 200, 350, 50);
        leftPanel.add(sloganLabel);

        // Right Panel (Register Form)
        JPanel registerPanel = new JPanel();
        registerPanel.setBounds(450, 0, 450, 500);
        registerPanel.setLayout(null);
        add(registerPanel);

        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        signUpLabel.setBounds(175, 50, 120, 40);
        registerPanel.add(signUpLabel);

        // Username Label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameLabel.setBounds(75, 120, 80, 25);
        registerPanel.add(usernameLabel);

        // Username Field
        usernameField = new JTextField();
        usernameField.setBounds(75, 150, 300, 35);
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        registerPanel.add(usernameField);

        // Email Label
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailLabel.setBounds(75, 200, 80, 25);
        registerPanel.add(emailLabel);

        // Email Field
        emailField = new JTextField();
        emailField.setBounds(75, 230, 300, 35);
        emailField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        registerPanel.add(emailField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordLabel.setBounds(75, 280, 80, 25);
        registerPanel.add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(75, 310, 300, 35);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        registerPanel.add(passwordField);

        // Sign Up Button
        registerButton = new JButton("Sign Up");
        registerButton.setBounds(75, 360, 300, 40);
        registerButton.setBackground(new Color(100, 130, 173));
        registerButton.setForeground(Color.WHITE);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerPanel.add(registerButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBounds(10, 10, 80, 25);
        backButton.setFocusPainted(false);
        registerPanel.add(backButton);

        // Action Listeners
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
}
