package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class FeedPage extends JPanel {
    private JList<String> categoryList;
    private DefaultListModel<String> categoryListModel;
    private JPanel feedDisplayPanel;
    private JScrollPane feedScrollPane;

    public FeedPage() {
        setLayout(new BorderLayout());

        // Sidebar for Categories
        categoryListModel = new DefaultListModel<>();
        categoryListModel.addElement("All");
        categoryListModel.addElement("Trending");
        categoryListModel.addElement("Popular");
        categoryListModel.addElement("My Feed");
        categoryList = new JList<>(categoryListModel);
        categoryList.setFont(new Font("SansSerif", Font.PLAIN, 16));
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setSelectedIndex(0);
        JScrollPane categoryScrollPane = new JScrollPane(categoryList);
        categoryScrollPane.setPreferredSize(new Dimension(200, 0));

        feedDisplayPanel = new JPanel();
        feedDisplayPanel.setLayout(new BoxLayout(feedDisplayPanel, BoxLayout.Y_AXIS));
        feedDisplayPanel.setBackground(new Color(245, 245, 245));
        feedScrollPane = new JScrollPane(feedDisplayPanel);
        feedScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, categoryScrollPane, feedScrollPane);
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);

        categoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCategory = categoryList.getSelectedValue();
                updateFeedPosts(selectedCategory);
            }
        });

        updateFeedPosts("All");
    }

    private void updateFeedPosts(String category) {
        feedDisplayPanel.removeAll();
        List<String> posts = getFeedPostsForCategory(category);
        for (String post : posts) {
            JPanel postPanel = new JPanel();
            postPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            postPanel.setBackground(Color.WHITE);
            postPanel.setMaximumSize(new Dimension(750, 150));
            postPanel.add(new JLabel(post));
            feedDisplayPanel.add(postPanel);
            feedDisplayPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between posts
        }
        feedDisplayPanel.revalidate();
        feedDisplayPanel.repaint();
    }

    private List<String> getFeedPostsForCategory(String category) {
        List<String> posts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            posts.add(category + " post content number " + i);
        }
        return posts;
    }
}
