package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
//import java.awt.geom.RoundRectangle2D;

public class SignInUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignInUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sign In Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(226, 218, 214));

        // Left Panel (For Branding)
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 450, 500);
        leftPanel.setBackground(new Color(100, 130, 173));
        leftPanel.setLayout(null);
        frame.add(leftPanel);

        // Welcome Text
        JLabel welcomeLabel = new JLabel("CONNECT WITH US!!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 120, 350, 50);
        leftPanel.add(welcomeLabel);

        JLabel sloganLabel = new JLabel("with Flicksy", SwingConstants.CENTER);
        sloganLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        sloganLabel.setForeground(Color.WHITE);
        sloganLabel.setBounds(50, 170, 350, 50);
        leftPanel.add(sloganLabel);

        // Right Panel (For Sign-In Form)
        JPanel signInPanel = new JPanel();
        signInPanel.setBounds(450, 0, 450, 500);
        signInPanel.setBackground(new Color(226, 218, 214));
        signInPanel.setLayout(null);
        frame.add(signInPanel);

        // Sign In Headline
        JLabel signInLabel = new JLabel("Sign Up");
        signInLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        signInLabel.setBounds(175, 40, 120, 40);
        signInPanel.add(signInLabel);

        // Username Label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameLabel.setBounds(75, 100, 80, 25);
        signInPanel.add(usernameLabel);

        // Username TextField (Curved)
        JTextField usernameField = new RoundJTextField(20);
        usernameField.setBounds(75, 130, 300, 40);
        signInPanel.add(usernameField);

        // Email Label
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        emailLabel.setBounds(75, 180, 80, 25);
        signInPanel.add(emailLabel);

        // Email TextField (Curved)
        JTextField emailField = new RoundJTextField(20);
        emailField.setBounds(75, 210, 300, 40);
        signInPanel.add(emailField);

        // Password Label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordLabel.setBounds(75, 260, 80, 25);
        signInPanel.add(passwordLabel);

        // Password Field (Curved)
        JPasswordField passwordField = new RoundJPasswordField(20);
        passwordField.setBounds(75, 290, 300, 40);
        signInPanel.add(passwordField);

        // Sign In Button (Curved)
        JButton signInButton = new RoundJButton("Sign Up");
        signInButton.setBounds(75, 350, 300, 45);
        signInButton.setBackground(new Color(100, 130, 173));
        signInButton.setForeground(Color.WHITE);
        signInPanel.add(signInButton);

        frame.setVisible(true);
    }
}

// Custom Rounded Text Field
class RoundJTextField extends JTextField {
    private int radius;

    public RoundJTextField(int radius) {
        super();
        this.radius = radius;
        setOpaque(false);
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(200, 200, 200));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }
}

// Custom Rounded Password Field
class RoundJPasswordField extends JPasswordField {
    private int radius;

    public RoundJPasswordField(int radius) {
        super();
        this.radius = radius;
        setOpaque(false);
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(200, 200, 200));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }
}

// Custom Rounded Button
class RoundJButton extends JButton {
    private int radius = 30;

    public RoundJButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setFont(new Font("SansSerif", Font.BOLD, 16));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }
}
