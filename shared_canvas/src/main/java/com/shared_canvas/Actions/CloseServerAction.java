package com.shared_canvas.Actions;

import java.awt.event.*;

import javax.swing.*;

import com.shared_canvas.Networking.NetworkManager;

public class CloseServerAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Close Server Action");

        // Check if server is not running
        if (NetworkManager.getServer() == null) {
            JOptionPane.showMessageDialog(null, "No server running", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the server?", "Close Server", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.NO_OPTION) {
            return;
        }

        // Close server
        NetworkManager.closeServer();
    }
}
