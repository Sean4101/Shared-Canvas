package com.shared_canvas.Actions;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import com.shared_canvas.Networking.*;

public class HostServerAction implements ActionListener {

    private NetworkManager networkManager;

    public HostServerAction(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Host Action");

        // Check if server is already running
        if (networkManager.server != null) {
            JOptionPane.showMessageDialog(null, "Server already running", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get port number, throw error if invalid
        String portString = JOptionPane.showInputDialog(null, "Enter port number (1024-65535):", "Host Server", JOptionPane.QUESTION_MESSAGE);
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

        // Start server
        try {
            networkManager.hostServer(port);
        } 
        catch (IOException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to start server", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Server started on port " + port, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
