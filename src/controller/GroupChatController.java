package controller;

import database.GroupChatDAO;
import model.GroupChat;
import model.Message;
import java.util.Date;
import java.util.List;

public class GroupChatController {
    private GroupChatDAO groupChatDAO;

    public GroupChatController() {
        this.groupChatDAO = new GroupChatDAO();
    }

    // Create a new group chat
    public boolean createGroupChat(String groupName, List<Integer> memberIds) {
        GroupChat groupChat = new GroupChat(0, groupName, memberIds);
        return groupChatDAO.createGroupChat(groupChat);
    }

    // Send a message in a group chat
    public boolean sendMessage(int groupId, int senderId, String content) {
        Message message = new Message(0, senderId, groupId, content, new Date(), false);
        return groupChatDAO.sendMessage(message);
    }

    // Retrieve messages from a group chat
    public List<Message> getMessages(int groupId) {
        return groupChatDAO.getMessages(groupId);
    }
}
