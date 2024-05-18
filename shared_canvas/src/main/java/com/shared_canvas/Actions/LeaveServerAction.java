package com.shared_canvas.Actions;

import java.awt.event.*;

import javax.swing.JOptionPane;

import com.shared_canvas.Networking.*;

public class LeaveServerAction implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Leave Server Action");

        // Check if is hosting a server
        if (NetworkManager.getServer() != null) {
            JOptionPane.showMessageDialog(null, "Cannot leave server while hosting, close server instead", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if connected to a server
        if (NetworkManager.getClient() == null) {
            JOptionPane.showMessageDialog(null, "Not connected to a server", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Disconnect from server
        NetworkManager.leaveServer();
    }
}
