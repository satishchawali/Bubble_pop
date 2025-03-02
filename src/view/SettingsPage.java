package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPage extends JPanel {

    public SettingsPage() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header Label
        JLabel headerLabel = new JLabel("Settings", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        headerLabel.setForeground(new Color(50, 205, 50));
        add(headerLabel, BorderLayout.NORTH);

        // Content Panel for settings controls
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // 1. Theme Selection Panel
        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        themePanel.setBackground(Color.WHITE);
        JLabel themeLabel = new JLabel("Theme:");
        themeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        themePanel.add(themeLabel);
        String[] themes = {"Light", "Dark", "System Default"};
        JComboBox<String> themeCombo = new JComboBox<>(themes);
        themeCombo.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        themePanel.add(themeCombo);

        // 2. Notifications Panel
        JPanel notificationsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        notificationsPanel.setBackground(Color.WHITE);
        JCheckBox notificationsCheckBox = new JCheckBox("Enable Notifications");
        notificationsCheckBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        notificationsCheckBox.setBackground(Color.WHITE);
        notificationsPanel.add(notificationsCheckBox);

        // 3. Privacy Panel
        JPanel privacyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        privacyPanel.setBackground(Color.WHITE);
        JLabel privacyLabel = new JLabel("Profile Visibility:");
        privacyLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        privacyPanel.add(privacyLabel);
        JRadioButton publicBtn = new JRadioButton("Public");
        publicBtn.setBackground(Color.WHITE);
        publicBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        JRadioButton friendsOnlyBtn = new JRadioButton("Friends Only");
        friendsOnlyBtn.setBackground(Color.WHITE);
        friendsOnlyBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        JRadioButton privateBtn = new JRadioButton("Private");
        privateBtn.setBackground(Color.WHITE);
        privateBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        ButtonGroup privacyGroup = new ButtonGroup();
        privacyGroup.add(publicBtn);
        privacyGroup.add(friendsOnlyBtn);
        privacyGroup.add(privateBtn);
        privacyPanel.add(publicBtn);
        privacyPanel.add(friendsOnlyBtn);
        privacyPanel.add(privateBtn);

        // Add settings panels to contentPanel with spacing
        contentPanel.add(themePanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(notificationsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(privacyPanel);

        add(contentPanel, BorderLayout.CENTER);

        // Footer Panel with Save Settings button
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        JButton saveButton = new JButton("Save Settings");
        saveButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        saveButton.setBackground(new Color(60, 179, 113));
        saveButton.setForeground(Color.WHITE);
        footerPanel.add(saveButton);
        add(footerPanel, BorderLayout.SOUTH);

        // TODO: Add action listeners for saveButton to process the settings.
    }
}
