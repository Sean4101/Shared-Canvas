package com.shared_canvas.Actions.LayerPanelActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.shared_canvas.Canvas.CanvasLayer;
import com.shared_canvas.GUI.ViewportPanel;
import com.shared_canvas.GUI.CollabPanelElements.LayerPanel;

public class AddLayerAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Add Layer Action");

        if (ViewportPanel.getCanvas() == null) {
            System.out.println("No canvas found");
            JOptionPane.showMessageDialog(null, "No canvas found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String layerName = JOptionPane.showInputDialog(null, "Enter layer name", "Add Layer", JOptionPane.QUESTION_MESSAGE);
        if (layerName == null) return;

        int width = ViewportPanel.getCanvas().width;
        int height = ViewportPanel.getCanvas().height;

        CanvasLayer newLayer = new CanvasLayer(layerName, width, height);
        ViewportPanel.getCanvas().addLayer(newLayer);
        LayerPanel.getInstance().updateLayerElements(ViewportPanel.getCanvas());
    }

}