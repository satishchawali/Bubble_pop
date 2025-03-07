package view;

import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {
    private JButton getStartedButton;

    public WelcomeScreen() {
        setTitle("Welcome - Bubble Pop");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false); // Prevent maximizing
        setLocationRelativeTo(null); // Center the window on screen

        JLabel welcomeLabel = new JLabel("WELCOME to Bubble Pop!!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        welcomeLabel.setForeground(new Color(255, 105, 180));
        welcomeLabel.setBounds(150, 100, 500, 50);
        add(welcomeLabel);

        JLabel taglineLabel = new JLabel("Make every moment special!", SwingConstants.CENTER);
        taglineLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
        taglineLabel.setForeground(Color.GRAY);
        taglineLabel.setBounds(150, 150, 500, 30);
        add(taglineLabel);

        getStartedButton = new JButton("Get Started");
        getStartedButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        getStartedButton.setBounds(300, 250, 200, 50);
        getStartedButton.setBackground(new Color(50, 205, 50));
        getStartedButton.setForeground(Color.WHITE);
        add(getStartedButton);

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