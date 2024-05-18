package com.shared_canvas.Actions;

import java.awt.event.*;
import javax.swing.*;

import com.shared_canvas.GUI.ViewportPanel;

public class ExportFileAction implements ActionListener {

    private ViewportPanel viewportPanel;

    public ExportFileAction(ViewportPanel viewportPanel) {
        this.viewportPanel = viewportPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "NOT YET IMPLEMENTED", "Error", JOptionPane.ERROR_MESSAGE);

        // TODO: Implement the file chooser to export a file
        // You migh need to add a few more classes to handle the export, 
        // if you do, make sure to add them to the Canvas package and access them from here

        // For now, implement the export functionality for .bmp and maybe .jpg files
        // You can do .png files if you want to, but it will likely need to be changed later anyway because of the transparency
    }
}
