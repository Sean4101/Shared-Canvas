package com.shared_canvas.Actions;

import java.awt.event.*;
import javax.swing.*;

import com.shared_canvas.GUI.CollabPanelElements.ChatPanel;
import com.shared_canvas.Networking.NetworkManager;

public class SendChatMessageAction implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the message from the chat input
        // TODO: You may need to change this while implementing the GUI
        String message = ChatPanel.getInstance().chatInput.getText();
        ChatPanel.getInstance().chatInput.setText("");

        // Do noting if the message is empty
        if (message == null || message.isEmpty()) {
            return;
        }

        // Check if the client is connected to the server
        if (NetworkManager.getClient() == null) {
            JOptionPane.showMessageDialog(null, "Not connected to server", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Send the message
        NetworkManager.sendChatMessage(message);
    }

}
