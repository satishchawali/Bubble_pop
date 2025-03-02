package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FeedPage extends JPanel {
    private JList<String> categoryList;
    private DefaultListModel<String> categoryListModel;
    private JPanel feedDisplayPanel;
    private JScrollPane feedScrollPane;

    public FeedPage() {
        setLayout(new BorderLayout());

        // Left Panel: Feed Categories
        categoryListModel = new DefaultListModel<>();
        categoryListModel.addElement("All");
        categoryListModel.addElement("Trending");
        categoryListModel.addElement("Popular");
        categoryListModel.addElement("My Feed");
        categoryList = new JList<>(categoryListModel);
        categoryList.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setSelectedIndex(0);
        JScrollPane categoryScrollPane = new JScrollPane(categoryList);
        categoryScrollPane.setPreferredSize(new Dimension(200, 0));

        // Right Panel: Feed Posts
        feedDisplayPanel = new JPanel();
        feedDisplayPanel.setLayout(new BoxLayout(feedDisplayPanel, BoxLayout.Y_AXIS));
        feedDisplayPanel.setBackground(new Color(245, 245, 245));
        feedScrollPane = new JScrollPane(feedDisplayPanel);
        feedScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Combine panels using a split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, categoryScrollPane, feedScrollPane);
        splitPane.setDividerLocation(200);
        add(splitPane, BorderLayout.CENTER);

        // Listener to update feed posts when a category is selected
        categoryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCategory = categoryList.getSelectedValue();
                updateFeedPosts(selectedCategory);
            }
        });

        // Initially load posts for the default category "All"
        updateFeedPosts("All");
    }

    // Refresh the feed posts based on the selected category.
    private void updateFeedPosts(String category) {
        feedDisplayPanel.removeAll();
        List<FeedPost> posts = getFeedPostsForCategory(category);
        for (FeedPost post : posts) {
            JPanel postPanel = createPostPanel(post);
            feedDisplayPanel.add(postPanel);
            feedDisplayPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between posts
        }
        feedDisplayPanel.revalidate();
        feedDisplayPanel.repaint();
    }

    // Creates a panel representing an individual feed post.
    private JPanel createPostPanel(FeedPost post) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(750, 150));

        // Header: Username
        JLabel userLabel = new JLabel(post.getUsername());
        userLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        userLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(userLabel, BorderLayout.NORTH);

        // Post content area
        JTextArea contentArea = new JTextArea(post.getContent());
        contentArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBackground(Color.WHITE);
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(contentArea, BorderLayout.CENTER);

        // Footer: Timestamp
        JLabel timeLabel = new JLabel(post.getTimestamp(), SwingConstants.RIGHT);
        timeLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 12));
        timeLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(timeLabel, BorderLayout.SOUTH);

        return panel;
    }

    // Dummy method to simulate fetching posts for a given category.
    private List<FeedPost> getFeedPostsForCategory(String category) {
        List<FeedPost> posts = new ArrayList<>();
        // In a real application, you'd fetch posts from a backend or database.
        for (int i = 1; i <= 5; i++) {
            posts.add(new FeedPost("User" + i, category + " post content number " + i, "10:" + (i < 10 ? "0" + i : i) + " AM"));
        }
        return posts;
    }

    // Dummy FeedPost class; in a complete project, this would likely be in its own file.
    private class FeedPost {
        private String username;
        private String content;
        private String timestamp;

        public FeedPost(String username, String content, String timestamp) {
            this.username = username;
            this.content = content;
            this.timestamp = timestamp;
        }

        public String getUsername() { return username; }
        public String getContent() { return content; }
        public String getTimestamp() { return timestamp; }
    }
}
