package view;
import javax.swing.*;
import java.awt.*;

public class LoginUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(226, 218, 214));

        // Left Panel (For Image)
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 450, 500);
        leftPanel.setBackground(new Color(100, 130, 173)); 
        leftPanel.setLayout(null);
        frame.add(leftPanel);

       // Welcome Text on Left Panel
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

        // Right Panel (For Login Form)
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(450, 0, 450, 500);
        loginPanel.setLayout(null);
        frame.add(loginPanel);

        // Login Headline
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
        JTextField emailField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
                g.setColor(Color.WHITE);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        emailField.setBounds(75, 150, 300, 35);
        emailField.setBackground(Color.WHITE);
        emailField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(emailField);
        
        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordLabel.setBounds(75, 200, 80, 25);
        passwordLabel.setForeground(Color.BLACK);
        loginPanel.add(passwordLabel);
        
        // Password Field
        JPasswordField passwordField = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
                g.setColor(Color.WHITE);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        passwordField.setBounds(75, 230, 300, 35);
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        loginPanel.add(passwordField);
        
        // Login Button
        JButton loginButton = new JButton("Login") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(100, 130, 173)); // Blue shade
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g.setColor(Color.WHITE);
                g.setFont(new Font("SansSerif", Font.BOLD, 16));
                g.drawString("Login", getWidth() / 2 - 20, getHeight() / 2 + 5);
            }
        };
        loginButton.setBounds(75, 280, 300, 40);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginPanel.add(loginButton);
        
       // Register Label
       JLabel registerTextLabel = new JLabel("Don't have an account?");
       registerTextLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
       registerTextLabel.setBounds(130, 340, 150, 25);
       loginPanel.add(registerTextLabel);
       
       // Register Button
       JButton registerButton = new JButton("Sign in");
        registerButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        registerButton.setForeground(new Color(100, 130, 173));
        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setOpaque(false);
        registerButton.setBounds(280, 340, 80, 25);
        loginPanel.add(registerButton);
        frame.setVisible(true);
    }
}