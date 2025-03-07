package view;

import javax.swing.*;
import java.awt.*;

public class ChatListScreen extends JPanel {
    private JList<String> chatList;
    private DefaultListModel<String> listModel;

    public interface ChatRoomSelectionListener {
        void onChatRoomSelected(String chatRoom);
    }
    
    private ChatRoomSelectionListener listener;
    
    public ChatListScreen(ChatRoomSelectionListener listener) {
        this.listener = listener;
        setLayout(new BorderLayout());
        
        listModel = new DefaultListModel<>();
        listModel.addElement("General Chat");
        listModel.addElement("Sports Chat");
        listModel.addElement("Movies Chat");
        listModel.addElement("Music Chat");
        
        chatList = new JList<>(listModel);
        chatList.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chatList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = chatList.getSelectedValue();
                if (selected != null && listener != null) {
                    listener.onChatRoomSelected(selected);
                }
            }
        });
        
        add(new JScrollPane(chatList), BorderLayout.CENTER);
    }
}
