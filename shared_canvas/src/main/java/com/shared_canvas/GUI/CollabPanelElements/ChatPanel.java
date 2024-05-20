package com.shared_canvas.GUI.CollabPanelElements;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import com.shared_canvas.Networking.NetworkManager;

public class ChatPanel extends JPanel {

    // TODO: Make this prettier

    public JTextArea chatArea = new JTextArea();
    public TextField chatInput = new TextField();

    private static ChatPanel instance;

    public static ChatPanel getInstance() {
        return instance;
    }
    
    public ChatPanel() {
        setBackground(Color.DARK_GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);

        setLayout(new BorderLayout());
        chatArea.setEditable(false);
        chatArea.setBackground(Color.LIGHT_GRAY);
        add(chatArea, BorderLayout.CENTER);
        add(chatInput, BorderLayout.SOUTH);

        instance = this;
    }

    public void joinedServerMessage(String username) {
        // TODO: You may need to change this while implementing the GUI

        chatArea.append(username + " has joined the canvas\n");
    }

    public void leftServerMessage(String username) { 
        // TODO: You may need to change this while implementing the GUI

        chatArea.append(username + " has left the canvas\n");
    }

    public void receiveMessage(String sender, String message) {
        // TODO: You may need to change this while implementing the GUI

        boolean senderIsMe = sender.equals(NetworkManager.getClient().getUsername());
        if (senderIsMe) {
            sender = "(Me) " + sender;
        }
        chatArea.append(sender + ": " + message + "\n");
    }
}
