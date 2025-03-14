package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class HomePage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePage::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Flicksy Home Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE); // Background color changed to WHITE

        // Top Panel (Header)
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 900, 100);
        topPanel.setBackground(new Color(100, 130, 173));
        topPanel.setLayout(null);
        frame.add(topPanel);

        // Welcome Text
        JLabel welcomeLabel = new JLabel("Welcome, TimePass!!", SwingConstants.RIGHT);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(500, 20, 350, 30);
        topPanel.add(welcomeLabel);

        JLabel subLabel = new JLabel("What's up", SwingConstants.RIGHT);
        subLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        subLabel.setForeground(new Color(245, 237, 237));
        subLabel.setBounds(650, 50, 200, 20);
        topPanel.add(subLabel);

        // Tagline
        JLabel tagline = new JLabel("Make a moment special with Flicksy", SwingConstants.LEFT);
        tagline.setFont(new Font("SansSerif", Font.BOLD, 14));
        tagline.setForeground(new Color(50, 50, 100));
        tagline.setBounds(20, 110, 300, 20);
        frame.add(tagline);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(50, 150, 400, 200);
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(2, 2, 20, 20));
        buttonsPanel.setBackground(Color.WHITE);
        frame.add(buttonsPanel);

        // Buttons
        String[] buttonLabels = {"Login", "Chats", "Profile", "Settings"};
        for (String text : buttonLabels) {
            RoundedButton button = new RoundedButton(text);
            buttonsPanel.add(button);
        }

        // Rounded Image Panel
        RoundedImagePanel imagePanel = new RoundedImagePanel("C:\\Users\\ADMIN\\OneDrive\\Desktop\\project\\Bubble_pop\\src\\Home_page.jpg");
        imagePanel.setBounds(500, 130, 350, 250);
        frame.add(imagePanel);

        frame.setVisible(true);
    }
}

// Custom Rounded Button Class
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

        // Draw Rounded Background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No border
    }
}

// Custom Rounded Image Panel
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

        // Clip to rounded rectangle
        Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 50, 50);
        g2.setClip(clip);

        // Draw Image
        g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        g2.dispose();
    }
}