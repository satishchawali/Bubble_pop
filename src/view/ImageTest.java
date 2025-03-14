package view;

import javax.swing.*;

public class ImageTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Try loading image
        ImageIcon icon = new ImageIcon("LoginPage.jpg"); // Make sure it's in the project root
        JLabel label = new JLabel(icon);
        
        System.out.println("Image width: " + icon.getIconWidth()); // Debugging
        System.out.println("Image height: " + icon.getIconHeight());

        if (icon.getIconWidth() == -1) {
            System.out.println("Error: Image not found! Check the path.");
        }

        label.setBounds(0, 0, 400, 400);
        frame.add(label);

        frame.setVisible(true);
    }
}
