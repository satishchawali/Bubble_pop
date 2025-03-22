package view;

import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {
    private JButton getStartedButton;

    public WelcomeScreen() {
        setTitle("Welcome - Flicksy");
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

        JLabel welcomeLabel = new JLabel("Welcome to Flicksy!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 150, 350, 50);
        leftPanel.add(welcomeLabel);

        JLabel taglineLabel = new JLabel("Make every moment special!", SwingConstants.CENTER);
        taglineLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        taglineLabel.setForeground(Color.WHITE);
        taglineLabel.setBounds(50, 200, 350, 50);
        leftPanel.add(taglineLabel);

        // Right Panel (Button Section)
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(450, 0, 450, 500);
        rightPanel.setBackground(new Color(226, 218, 214));
        rightPanel.setLayout(null);
        add(rightPanel);

        getStartedButton = new JButton("Get Started");
        getStartedButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        getStartedButton.setBounds(125, 200, 200, 50);
        getStartedButton.setBackground(new Color(100, 130, 173));
        getStartedButton.setFocusPainted(false);
        getStartedButton.setForeground(Color.WHITE);
        rightPanel.add(getStartedButton);

        getStartedButton.addActionListener(e -> switchToLogin());

        setVisible(true);
    }

    private void switchToLogin() {
        new LoginScreen();
        dispose();
    }

    public static void main(String[] args) {
        new WelcomeScreen();
    }
}
