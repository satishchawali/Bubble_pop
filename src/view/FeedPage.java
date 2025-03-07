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
        List<FeedPost> posts = getFeedPostsForCategory(category);
        for (FeedPost post : posts) {
            JPanel postPanel = createPostPanel(post);
            feedDisplayPanel.add(postPanel);
            feedDisplayPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between posts
        }
        feedDisplayPanel.revalidate();
        feedDisplayPanel.repaint();
    }

    private JPanel createPostPanel(FeedPost post) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(750, 150));

        JLabel userLabel = new JLabel(post.getUsername());
        userLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        userLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(userLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(post.getContent());
        contentArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setBackground(Color.WHITE);
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(contentArea, BorderLayout.CENTER);

        JLabel timeLabel = new JLabel(post.getTimestamp(), SwingConstants.RIGHT);
        timeLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 12));
        timeLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(timeLabel, BorderLayout.SOUTH);

        return panel;
    }

    private List<FeedPost> getFeedPostsForCategory(String category) {
        List<FeedPost> posts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            posts.add(new FeedPost("User" + i, category + " post content number " + i, "10:" + (i < 10 ? "0" + i : i) + " AM"));
        }
        return posts;
    }

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
