package com.shared_canvas.Actions;

import java.awt.event.*;

import javax.swing.*;

import com.shared_canvas.Networking.NetworkManager;

public class JoinServerAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Join Server Action");

        // Check if already connected to a server
        if (NetworkManager.getClient() != null) {
            JOptionPane.showMessageDialog(null, "Already connected to a server", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get host IP address
        String host = JOptionPane.showInputDialog(null, "Enter host IP address:", "Join Server", JOptionPane.QUESTION_MESSAGE);
        if (host == null) { // Cancel button
            return;
        }

        // Get port number, throw error if invalid
        String portString = JOptionPane.showInputDialog(null, "Enter host port number (1024-65535):", "Join Server", JOptionPane.QUESTION_MESSAGE);
        int port = 0;
        if (portString == null) { // Cancel button
            return;
        }
        try {
            port = Integer.parseInt(portString);
            if (port < 1024 || port > 65535) {
                JOptionPane.showMessageDialog(null, "Invalid port number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid port number", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get username
        String username = JOptionPane.showInputDialog(null, "Enter username:", "Join Server", JOptionPane.QUESTION_MESSAGE);
        if (username == null) { // Cancel button
            return;
        }

        // Connect to server
        try {
            NetworkManager.joinServer(host, port, username);
        } 
        catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to server", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}
