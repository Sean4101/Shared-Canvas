package com.shared_canvas.Actions;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;
import com.shared_canvas.GUI.CollabPanelElements.LayerPanel;
import com.shared_canvas.Networking.NetworkManager;
import com.shared_canvas.Networking.Messages.SyncCanvasMessage;

public class NewFileAction implements ActionListener {

    private ViewportPanel viewportPanel;

    public NewFileAction(ViewportPanel viewportPanel) {
        this.viewportPanel = viewportPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("New File Action");

        // Create a popup to get the new canvas size
        JTextField canvasXInput = new JTextField();
        JTextField canvasYInput = new JTextField();
        JPanel popupPanel = new JPanel(new GridLayout(5, 0));
        popupPanel.add(new JLabel("Size of the new canvas:"));
        popupPanel.add(new JLabel("X:"));
        popupPanel.add(canvasXInput);
        popupPanel.add(new JLabel("Y:"));
        popupPanel.add(canvasYInput);
        int result = JOptionPane.showConfirmDialog(null, popupPanel, "New File", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.CANCEL_OPTION) return;

        // Parse the input
        int canvasX;
        int canvasY;
        try {
            canvasX = Integer.parseInt(canvasXInput.getText());
            canvasY = Integer.parseInt(canvasYInput.getText());
        } 
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (canvasX <= 0 || canvasY <= 0 || canvasX > 3840 || canvasY > 3840) {
            JOptionPane.showMessageDialog(null, "Input out of bounds, must be between 1 and 3840", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create the new canvas
        viewportPanel.createNewCanvas(canvasX, canvasY);
        SharedCanvas canvas = ViewportPanel.getCanvas();
        LayerPanel.getInstance().updateLayerElements(canvas);

        // If it is the host send the new canvas to the clients
        if (NetworkManager.getServer() != null) {
            SyncCanvasMessage syncCanvasMessage = new SyncCanvasMessage(NetworkManager.getClient().getUsername());
            NetworkManager.sendMessage(syncCanvasMessage);
        }
    }
}
