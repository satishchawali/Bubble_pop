package controller;

import model.Message;
import database.MessageDAO;
import java.util.List;
import java.util.Date;

public class ChatController {
    private MessageDAO messageDAO; // Database access object

    // Constructor
    public ChatController() {
        this.messageDAO = new MessageDAO();
    }

    // ✅ Send a private message between two users
    public boolean sendMessage(int senderId, int receiverId, String content) {
        // Create a new Message object
        Message message = new Message(0, senderId, receiverId, content, new Date(), false); // ID is 0, assuming auto-generated
        return messageDAO.saveMessage(message);
    }

    // ✅ Retrieve chat history between two users
    public List<Message> getChatHistory(int user1Id, int user2Id) {
        return messageDAO.getMessagesBetweenUsers(user1Id, user2Id);
    }
}
