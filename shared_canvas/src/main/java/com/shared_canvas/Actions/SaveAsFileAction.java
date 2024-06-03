 package com.shared_canvas.Actions;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;

import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;

public class SaveAsFileAction implements ActionListener {

    private ViewportPanel viewportPanel;

    public SaveAsFileAction(ViewportPanel viewportPanel) {
        this.viewportPanel = viewportPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Save File Action");

        FileDialog fileDialog = new FileDialog(new Frame(), "Save file", FileDialog.SAVE);
        fileDialog.setFile("*.obj");  // Default file extension filter
        fileDialog.setVisible(true);
        if (fileDialog.getFile() == null) return;
        String fileName = fileDialog.getDirectory() + fileDialog.getFile() + ".obj";

        SharedCanvas canvas = ViewportPanel.getCanvas();

        try {
            canvas.saveToFile(fileName);
            System.out.println("File saved successfully: " + fileName);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed to save file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        };
    }
}
