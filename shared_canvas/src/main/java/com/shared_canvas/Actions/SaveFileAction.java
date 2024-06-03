package com.shared_canvas.Actions;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;

import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;

public class SaveFileAction implements ActionListener {

    public static String SAVE_FILE_PATH = "";

    private ViewportPanel viewportPanel;

    public SaveFileAction(ViewportPanel viewportPanel) {
        this.viewportPanel = viewportPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Save File Action");

        if (SAVE_FILE_PATH != "") {
            SharedCanvas canvas = ViewportPanel.getCanvas();
            try {
                canvas.saveToFile(SAVE_FILE_PATH);
                System.out.println("File saved successfully: " + SAVE_FILE_PATH);
                return;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to save file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            };
        }

        FileDialog fileDialog = new FileDialog(new Frame(), "Save file", FileDialog.SAVE);
        fileDialog.setFile("*.obj");  // Default file extension filter
        fileDialog.setVisible(true);
        if (fileDialog.getFile() == null) return;
        String fileName = fileDialog.getDirectory() + fileDialog.getFile();

        SharedCanvas canvas = ViewportPanel.getCanvas();

        try {
            canvas.saveToFile(fileName);
            System.out.println("File saved successfully: " + fileName);
            SAVE_FILE_PATH = fileName;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed to save file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        };
    }
}
