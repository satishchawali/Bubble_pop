package
view;import javax.swing.*;
import java.awt.*;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import java.util.ArrayList;

public class GroupChatUI extends JPanel {
    private JFrame parentFrame;

    public GroupChatUI(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);

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

        JButton getStartedButton = new JButton("Get Started");
        getStartedButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        getStartedButton.setBounds(300, 250, 200, 50);
        getStartedButton.setBackground(new Color(50, 205, 50));
        getStartedButton.setForeground(Color.WHITE);
        add(getStartedButton);

        getStartedButton.addActionListener(e -> switchToNewPanel());
    }

    private void switchToNewPanel() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new WelcomePanel(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bubble Pop UI");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        GroupChatUI panel = new GroupChatUI(frame);
        frame.add(panel);
        frame.setVisible(true);
    }
}

// Welcome Panel
class WelcomePanel extends JPanel {
    private JFrame parentFrame;

    public WelcomePanel(JFrame frame) {
        this.parentFrame = frame;
        setLayout(null);

        JLabel welcomeLabel = new JLabel("Create Your Group!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        welcomeLabel.setBounds(200, 100, 400, 50);
        welcomeLabel.setForeground(new Color(255, 105, 180));
        add(welcomeLabel);

        JButton groupButton = new JButton("Create Group!");
        groupButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        groupButton.setBounds(300, 200, 200, 50);
        groupButton.setBackground(new Color(255, 105, 180));
        groupButton.setForeground(Color.WHITE);
        add(groupButton);

        groupButton.addActionListener(e -> openGroupCreationForm());
    }

    private void openGroupCreationForm() {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new GroupCreationPanel(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}

// Group Creation Panel
class GroupCreationPanel extends JPanel {
    private JTextField groupNameField;
    private DefaultListModel<String> participantListModel;
    private JTextField participantField;
    private JFrame parentFrame;
    private ArrayList<String> groups;

    public GroupCreationPanel(JFrame frame) {
        this.parentFrame = frame;
        this.groups = new ArrayList<>();
        setLayout(null);

        JLabel nameLabel = new JLabel("Group Name:");
        nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        nameLabel.setBounds(100, 50, 150, 30);
        add(nameLabel);

        groupNameField = new JTextField();
        groupNameField.setBounds(250, 50, 300, 30);
        add(groupNameField);

        JLabel participantsLabel = new JLabel("Add Participants:");
        participantsLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        participantsLabel.setBounds(100, 100, 180, 30);
        add(participantsLabel);

        participantField = new JTextField();
        participantField.setBounds(250, 100, 200, 30);
        add(participantField);

        JButton addButton = new JButton("Add");
        addButton.setBounds(460, 100, 80, 30);
        add(addButton);

        participantListModel = new DefaultListModel<>();
        JList<String> participantList = new JList<>(participantListModel);
        JScrollPane scrollPane = new JScrollPane(participantList);
        scrollPane.setBounds(250, 140, 300, 100);
        add(scrollPane);

        addButton.addActionListener(e -> {
            String name = participantField.getText().trim();
            if (!name.isEmpty()) {
                participantListModel.addElement(name);
                participantField.setText("");
            }
        });

        JButton createGroupButton = new JButton("Create Group");
        createGroupButton.setBounds(300, 270, 200, 50);
        createGroupButton.setBackground(new Color(255, 105, 180));
        createGroupButton.setForeground(Color.WHITE);
        add(createGroupButton);

        createGroupButton.addActionListener(e -> {
            String groupName = groupNameField.getText().trim();
            if (groupName.isEmpty() || participantListModel.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Please enter a group name and add at least one participant.");
            } else {
                groups.add(groupName);
                JOptionPane.showMessageDialog(parentFrame, "Group '" + groupName + "' created!");
                parentFrame.getContentPane().removeAll();
                parentFrame.add(new ChatInterface(parentFrame, groups));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }
}

//Chat Interface
class ChatInterface extends JPanel {
    private JTextArea chatArea;
    private JTextField chatInput;
    private DefaultListModel<String> groupListModel;
    private ArrayList<String> groups;

    public ChatInterface(JFrame parentFrame, ArrayList<String> existingGroups) {
        this.groups = existingGroups;
        setLayout(null);

        //Group List Sidebar
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BorderLayout());
        groupPanel.setBounds(10, 10, 150, 450);
        groupPanel.setBackground(new Color(240, 240, 240));
        add(groupPanel);

        JLabel groupLabel = new JLabel("Groups", SwingConstants.CENTER);
        groupLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        groupPanel.add(groupLabel, BorderLayout.NORTH);

        groupListModel = new DefaultListModel<>();
        for (String group : groups) {
            groupListModel.addElement(group);
        }

        JList<String> groupList = new JList<>(groupListModel);
        groupList.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        JScrollPane groupScroll = new JScrollPane(groupList);
        groupPanel.add(groupScroll, BorderLayout.CENTER);

        //Chat Area
        chatArea = new JTextArea();
        chatArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        chatArea.setEditable(false);
        JScrollPane chatScroll = new JScrollPane(chatArea);
        chatScroll.setBounds(170, 10, 470, 350);
        add(chatScroll);

        //Chat Input
        chatInput = new JTextField();
        chatInput.setBounds(170, 370, 370, 40);
        add(chatInput);

        //Send Button
        JButton sendButton = new JButton("Send");
        sendButton.setBounds(550, 370, 90, 40);
        sendButton.setBackground(new Color(50, 205, 50));
        sendButton.setForeground(Color.WHITE);
        add(sendButton);

        sendButton.addActionListener(e -> {
            String message = chatInput.getText().trim();
            if (!message.isEmpty()) {
                chatArea.append("You: " + message + "\n");
                chatInput.setText("");
            }
        });
    }
}
