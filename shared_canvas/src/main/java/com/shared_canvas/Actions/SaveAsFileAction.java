 package com.shared_canvas.Actions;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;

public class SaveAsFileAction implements ActionListener {

    private ViewportPanel viewportPanel;

    public SaveAsFileAction(ViewportPanel viewportPanel) {
        this.viewportPanel = viewportPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // JOptionPane.showMessageDialog(null, "NOT YET IMPLEMENTED", "Error", JOptionPane.ERROR_MESSAGE);

        System.out.println("Save File Action");
        FileDialog fileDialog = new FileDialog(new Frame(), "Save file", FileDialog.SAVE);
        fileDialog.setFile("*.obj");  // Default file extension filter
        fileDialog.setVisible(true);
        if (fileDialog.getFile() == null) return;
        String fileName = fileDialog.getDirectory() + fileDialog.getFile() + ".obj";

        // Access the SharedCanvas object from the viewportPanel
        SharedCanvas canvas = viewportPanel.getCanvas();

        try {
            canvas.saveToFile(fileName);
            System.out.println("File saved successfully: " + fileName);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed to save file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        };
        
        
        
        // you will need to access the SharedCanvas object from the viewportPanel
    }
}
