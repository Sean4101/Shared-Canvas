package com.shared_canvas.Actions;

import java.awt.event.*;

import javax.swing.JOptionPane;

import com.shared_canvas.Networking.*;

public class LeaveServerAction implements ActionListener {

    private NetworkManager networkManager;

    public LeaveServerAction(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Leave Server Action");

        // Check if is hosting a server
        if (networkManager.server != null) {
            JOptionPane.showMessageDialog(null, "Cannot leave server while hosting, close server instead", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if connected to a server
        if (networkManager.client == null) {
            JOptionPane.showMessageDialog(null, "Not connected to a server", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Disconnect from server
        networkManager.leaveServer();
    }
}
