package com.shared_canvas.Actions;

import java.awt.event.*;

import com.shared_canvas.GUI.CollabPanelElements.ChatPanel;
import com.shared_canvas.Networking.Messages.*;

public class ReceivedMessageAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // This function is not in use
    }

    public void messageReceived(ChatMessage message) {
        String sender = message.getSender();
        String messageString = message.getMessage();
        
        ChatPanel.getInstance().receiveMessage(sender, messageString);
    }
}