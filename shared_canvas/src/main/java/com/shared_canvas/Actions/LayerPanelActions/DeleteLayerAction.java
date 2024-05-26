package com.shared_canvas.Actions.LayerPanelActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.shared_canvas.GUI.ViewportPanel;

public class DeleteLayerAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Delete Layer Action");
        
        if (ViewportPanel.getCanvas() == null) {
            System.out.println("No canvas found");
            JOptionPane.showMessageDialog(null, "No canvas found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

}
