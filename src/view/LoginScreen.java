package view;

import controller.AuthController;
import java.awt.*;
import javax.swing.*;
import model.User;

public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, backButton;
    private AuthController authController;

    public LoginScreen() {
        authController = new AuthController();
        
        // Create the main frame
        JFrame frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(226, 218, 214));
        frame.setLocationRelativeTo(null);

        // Left Panel (For Image & Branding)
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 450, 500);
        leftPanel.setBackground(new Color(100, 130, 173)); 
        leftPanel.setLayout(null);
        frame.add(leftPanel);

        // Welcome Text
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

        // Right Panel (Login Form)
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(450, 0, 450, 500);
        loginPanel.setLayout(null);
        frame.add(loginPanel);

        // Login Title
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        loginLabel.setBounds(175, 50, 100, 40);
        loginPanel.add(loginLabel);

        // Email Label
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailLabel.setBounds(75, 120, 80, 25);
        emailLabel.setForeground(Color.BLACK);
        loginPanel.add(emailLabel);

        // Email TextField
        emailField = new JTextField();
        emailField.setBounds(75, 150, 300, 35);
        emailField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(emailField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordLabel.setBounds(75, 200, 80, 25);
        passwordLabel.setForeground(Color.BLACK);
        loginPanel.add(passwordLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(75, 230, 300, 35);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(passwordField);

        loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(100, 130, 173));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(getText(), x, y);
            }
        };
        loginButton.setBounds(75, 280, 300, 40);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginPanel.add(loginButton);
        
        
        

        // Register Label
        JLabel registerTextLabel = new JLabel("Don't have an account?");
        registerTextLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        registerTextLabel.setBounds(130, 340, 150, 25);
        loginPanel.add(registerTextLabel);

        // Register Button
        registerButton = new JButton("Sign up");
        registerButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        registerButton.setForeground(new Color(100, 130, 173));
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setOpaque(false);
        registerButton.setBounds(280, 340, 80, 25);
        loginPanel.add(registerButton);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBounds(10, 10, 80, 25);
        loginPanel.add(backButton);

        frame.setVisible(true);

        // Action Listeners
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            User user = authController.login(email, password);
            if (user != null) {
                JOptionPane.showMessageDialog(frame, "Login Successful! Welcome, " + user.getUsername());
                new HomePage(user);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Email or Password");
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterScreen();
            frame.dispose();
        });

        backButton.addActionListener(e -> {
            new WelcomeScreen();
            frame.dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::new);
    }
}
