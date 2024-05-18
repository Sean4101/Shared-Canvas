package com.shared_canvas.Actions;

import java.awt.event.*;
import javax.swing.*;

import com.shared_canvas.GUI.ViewportPanel;

public class OpenFileAction implements ActionListener {

    private ViewportPanel viewportPanel;

    public OpenFileAction(ViewportPanel viewportPanel) {
        this.viewportPanel = viewportPanel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "NOT YET IMPLEMENTED", "Error", JOptionPane.ERROR_MESSAGE);

        // TODO: Implement the file chooser to open a file


        // TODO: Load the canvas from the file, see ViewportPanel.loadCanvas(SharedCanvas canvas)


    }
}
