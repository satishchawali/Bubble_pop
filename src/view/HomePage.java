package view;

import model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;

public class HomePage {
    private User user;
    private JFrame frame;

    public HomePage(User user) {
        this.user = user;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Flicksy - Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);

        // Top Panel (Header)
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 900, 100);
        topPanel.setBackground(new Color(100, 130, 173));
        topPanel.setLayout(null);
        frame.add(topPanel);

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername() + "!", SwingConstants.RIGHT);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(500, 20, 350, 30);
        topPanel.add(welcomeLabel);

        JLabel subLabel = new JLabel("Explore Flicksy", SwingConstants.RIGHT);
        subLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        subLabel.setForeground(new Color(245, 237, 237));
        subLabel.setBounds(650, 50, 200, 20);
        topPanel.add(subLabel);

        // Tagline
        JLabel tagline = new JLabel("Stay connected with Flicksy!", SwingConstants.LEFT);
        tagline.setFont(new Font("SansSerif", Font.BOLD, 14));
        tagline.setForeground(new Color(50, 50, 100));
        tagline.setBounds(20, 110, 300, 20);
        frame.add(tagline);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(50, 150, 400, 250);
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(3, 2, 20, 20)); // Adjusted for 6 buttons
        frame.add(buttonsPanel);

        // Menu Buttons
        String[] buttonLabels = {"Chats", "Settings"};
        for (String text : buttonLabels) {
            RoundedButton button = new RoundedButton(text);
            button.addActionListener(e -> openTab(text));
            buttonsPanel.add(button);
        }

        // Image Section
        RoundedImagePanel imagePanel = new RoundedImagePanel("src/Home_page.jpg");
        imagePanel.setBounds(500, 130, 350, 250);
        frame.add(imagePanel);

        frame.setVisible(true);
    }

    private void openChat() {
        SwingUtilities.invokeLater(() -> new ChatClientGUI(user).setVisible(true));
    }

    private void openTab(String tab) {
        frame.dispose(); // Close home page

        switch (tab) {
            case "Chats":
                openChat();
                break;
            case "Feeds":
                new FeedPage();
                break;
            case "File Sharing":
                new FileSharingUI();
                break;
            case "Friend Requests":
                new FriendRequestUI();
                break;
            case "Settings":
                new SettingsPage(user); // Pass user to SettingsPage
                break;
        }
    }
}

// **Rounded Button Class**
class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setForeground(Color.WHITE);
        setBackground(new Color(100, 130, 173));
        setFocusPainted(false);
        setOpaque(false);
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
        super.paintComponent(g);
        g2.dispose();
    }
}

// **Rounded Image Panel**
class RoundedImagePanel extends JPanel {
    private Image image;

    public RoundedImagePanel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        this.image = icon.getImage().getScaledInstance(350, 250, Image.SCALE_SMOOTH);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 50, 50);
        g2.setClip(clip);
        g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g2.dispose();
    }
}
