package com.shared_canvas.Actions.LayerPanelActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.shared_canvas.GUI.ViewportPanel;
import com.shared_canvas.GUI.CollabPanelElements.LayerPanel;
import com.shared_canvas.Networking.NetworkManager;
import com.shared_canvas.Networking.Messages.DeleteLayerMessage;

public class DeleteLayerAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Delete Layer Action");
        
        if (ViewportPanel.getCanvas() == null) {
            System.out.println("No canvas found");
            JOptionPane.showMessageDialog(null, "No canvas found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (ViewportPanel.getCanvas().layers.size() == 1) {
            JOptionPane.showMessageDialog(null, "Cannot delete the last layer", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this layer?", "Delete Layer", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.NO_OPTION) return;

        int index = ViewportPanel.getCanvas().layers.indexOf(LayerPanel.getInstance().activeLayer);
        ViewportPanel.getCanvas().layers.remove(LayerPanel.getInstance().activeLayer);

        LayerPanel.getInstance().activeLayer = ViewportPanel.getCanvas().layers.get(0);

        LayerPanel.getInstance().updateLayerElements(ViewportPanel.getCanvas());
        ViewportPanel.getInstance().repaint();

        if (NetworkManager.getClient() == null) return;
        String sender = NetworkManager.getClient().getUsername();
        NetworkManager.sendMessage(new DeleteLayerMessage(sender, index));
    }

}
