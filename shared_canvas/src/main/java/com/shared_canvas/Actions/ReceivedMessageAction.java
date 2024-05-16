package com.shared_canvas.Actions;

import java.awt.event.*;

import javax.swing.JTextArea;

import com.shared_canvas.Networking.Messages.*;

public class ReceivedMessageAction implements ActionListener {

    private JTextArea chatArea;

    public ReceivedMessageAction(JTextArea chatArea) {
        this.chatArea = chatArea;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // This function is not in use
    }

    public void messageReceived(ChatMessage message) {
        String sender = message.getSender();
        String messageString = message.getMessage();

        chatArea.append(sender + ": " + messageString + "\n");
    }
}