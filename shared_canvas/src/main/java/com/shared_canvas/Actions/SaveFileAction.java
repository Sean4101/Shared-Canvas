package com.shared_canvas.Actions;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;

public class SaveFileAction implements ActionListener {

    private ViewportPanel viewportPanel;

    public SaveFileAction(ViewportPanel viewportPanel) {
        this.viewportPanel = viewportPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            SharedCanvas canvas = viewportPanel.getCanvas();
            canvas.save();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Unable to Save File", JOptionPane.ERROR_MESSAGE);
        }
    }
}
