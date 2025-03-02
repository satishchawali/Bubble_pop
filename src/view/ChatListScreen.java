package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChatListScreen extends JPanel {
    private JList<String> chatList;
    private DefaultListModel<String> listModel;

    // Listener interface for chat room selection
    public interface ChatRoomSelectionListener {
        void onChatRoomSelected(String chatRoom);
    }
    
    private ChatRoomSelectionListener listener;
    
    public ChatListScreen(ChatRoomSelectionListener listener) {
        this.listener = listener;
        setLayout(new BorderLayout());
        
        listModel = new DefaultListModel<>();
        // Example chat rooms; these could be dynamically loaded
        listModel.addElement("General Chat");
        listModel.addElement("Sports Chat");
        listModel.addElement("Movies Chat");
        listModel.addElement("Music Chat");
        
        chatList = new JList<>(listModel);
        chatList.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chatList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selected = chatList.getSelectedValue();
                    if (selected != null && listener != null) {
                        listener.onChatRoomSelected(selected);
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(chatList);
        add(scrollPane, BorderLayout.CENTER);
    }
}
