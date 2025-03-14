package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class SettingsPage {
    private static JLabel profilePic;
    private static JPanel contentPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SettingsPage::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Settings Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // Background Color (rgb(100, 130, 173))
        frame.getContentPane().setBackground(new Color(100, 130, 173));

        // Sidebar Panel (rgb(245, 237, 237))
        JPanel sidebar = new JPanel();
        sidebar.setBounds(0, 0, 200, 500);
        sidebar.setBackground(new Color(245, 237, 237));
        sidebar.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

        JLabel settingsTitle = new JLabel("Settings");
        settingsTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        settingsTitle.setForeground(Color.BLACK);
        sidebar.add(settingsTitle);

        JButton accountButton = createSidebarButton("Account");
        JButton notificationsButton = createSidebarButton("Notifications");
        JButton privacyButton = createSidebarButton("Privacy");
        JButton languagesButton = createSidebarButton("Languages");
        JButton helpButton = createSidebarButton("Help");

        sidebar.add(accountButton);
        sidebar.add(notificationsButton);
        sidebar.add(privacyButton);
        sidebar.add(languagesButton);
        sidebar.add(helpButton);

        frame.add(sidebar);

        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setBounds(220, 30, 550, 400);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(null);
        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 200, 210), 2));
        frame.add(contentPanel);

        accountButton.addActionListener(e -> showPanel("Account"));
        notificationsButton.addActionListener(e -> showPanel("Notifications"));
        privacyButton.addActionListener(e -> showPanel("Privacy"));
        languagesButton.addActionListener(e -> showPanel("Languages"));
        helpButton.addActionListener(e -> showPanel("Help"));

        showPanel("Account"); // Show Account panel by default

        frame.setVisible(true);
    }

    private static JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setForeground(Color.BLACK);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

    private static void showPanel(String panelName) {
        contentPanel.removeAll();
        JLabel title = new JLabel(panelName);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBounds(20, 10, 200, 30);
        contentPanel.add(title);

        if (panelName.equals("Account")) {
            JLabel profileLabel = new JLabel("Profile Photo");
            profileLabel.setBounds(400, 5, 100, 20);
            contentPanel.add(profileLabel);

            profilePic = new JLabel();
            profilePic.setBounds(400, 30, 80, 80);
            profilePic.setOpaque(true);
            profilePic.setBackground(Color.LIGHT_GRAY);
            profilePic.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            profilePic.setIcon(new ImageIcon(new ImageIcon("profile.jpg").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));

            profilePic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    int option = fileChooser.showOpenDialog(null);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        profilePic.setIcon(new ImageIcon(new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
                    }
                }
            });
            contentPanel.add(profilePic);

            JLabel nameLabel = new JLabel("Name:");
            nameLabel.setBounds(20, 50, 100, 30);
            contentPanel.add(nameLabel);
            JSeparator separator1 = new JSeparator();
            separator1.setBounds(20, 80, 150, 10);
            contentPanel.add(separator1);

            JLabel emailLabel = new JLabel("Gmail:");
            emailLabel.setBounds(20, 100, 100, 30);
            contentPanel.add(emailLabel);
            JSeparator separator2 = new JSeparator();
            separator2.setBounds(20, 130, 150, 10);
            contentPanel.add(separator2);

            JLabel aboutLabel = new JLabel("About:");
            aboutLabel.setBounds(20, 150, 100, 30);
            contentPanel.add(aboutLabel);
            JSeparator separator3 = new JSeparator();
            separator3.setBounds(20, 180, 150, 10);
            contentPanel.add(separator3);

            RoundedButton updateButton = new RoundedButton("Update");
            updateButton.setBounds(370, 250, 150, 40); // Right-aligned
            contentPanel.add(updateButton);

        } else if (panelName.equals("Notifications")) {
            JLabel bellIcon = new JLabel(new ImageIcon(new ImageIcon("C:\\Users\\ADMIN\\OneDrive\\Desktop\\project\\Bubble_pop\\bell2.png")
                    .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            bellIcon.setBounds(225, 100, 100, 100);
            contentPanel.add(bellIcon);

            JLabel noNotifications = new JLabel("No Notifications Yet!!");
            noNotifications.setFont(new Font("SansSerif", Font.BOLD, 18));
            noNotifications.setBounds(190, 220, 200, 30);
            contentPanel.add(noNotifications);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}

// Custom Rounded Button
class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setFont(new Font("SansSerif", Font.BOLD, 14));
        setBackground(new Color(70, 130, 180));
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No border needed
    }
}