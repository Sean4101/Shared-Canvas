package com.shared_canvas.Actions.LayerPanelActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.shared_canvas.GUI.ViewportPanel;
import com.shared_canvas.GUI.CollabPanelElements.LayerPanel;
import com.shared_canvas.Networking.NetworkManager;
import com.shared_canvas.Networking.Messages.MergeLayerMessage;

public class MergeLayerAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Merge Layer Action");
        
        if (ViewportPanel.getCanvas() == null) {
            System.out.println("No canvas found");
            JOptionPane.showMessageDialog(null, "No canvas found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int index = ViewportPanel.getCanvas().layers.indexOf(LayerPanel.getInstance().activeLayer);

        if (index == 0) {
            JOptionPane.showMessageDialog(null, "Cannot merge the first layer", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to merge this layer with the previous layer?", "Merge Layer", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.NO_OPTION) return;

        ViewportPanel.getCanvas().mergeLayer(index);

        LayerPanel.getInstance().activeLayer = ViewportPanel.getCanvas().layers.get(index - 1);

        LayerPanel.getInstance().updateLayerElements(ViewportPanel.getCanvas());
        ViewportPanel.getInstance().repaint();

        if (NetworkManager.getClient() == null) return;
        String sender = NetworkManager.getClient().getUsername();
        NetworkManager.sendMessage(new MergeLayerMessage(sender, index));
    }

}
