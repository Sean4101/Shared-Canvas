package com.shared_canvas.Actions;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image.*;

import com.shared_canvas.Canvas.CanvasLayer;
import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;
import com.shared_canvas.GUI.CollabPanelElements.LayerPanel;


public class OpenFileAction implements ActionListener {

    private ViewportPanel viewportPanel;

    public OpenFileAction(ViewportPanel viewportPanel) {
        this.viewportPanel = viewportPanel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //JOptionPane.showMessageDialog(null, "NOT YET IMPLEMENTED", "Error", JOptionPane.ERROR_MESSAGE);

        // TODO: Implement the file chooser to open a file
        System.out.println("Open File Action");

        FileDialog fileDialog = new FileDialog(new Frame(), "Open file", FileDialog.LOAD);
        fileDialog.setFile("*.obj;*.jpg");  // Allow both .obj and .jpg files
        fileDialog.setVisible(true);
        if (fileDialog.getFile() == null) return;
        String fileName = fileDialog.getDirectory() + fileDialog.getFile();

        try {
            if (fileName.toLowerCase().endsWith(".obj")) {
                // Load .obj file
                SharedCanvas canvas = SharedCanvas.loadFromFile(fileName);
                viewportPanel.loadCanvas(canvas);
                System.out.println("OBJ file loaded successfully: " + fileName);
            } else if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
                // Load .jpg or .jpeg files
                BufferedImage image = ImageIO.read(new File(fileName));
                    if (image != null) {
                        SharedCanvas canvas = new SharedCanvas(image.getWidth(), image.getHeight());
                        canvas.loadFromBufferedImage(image);
                        viewportPanel.loadCanvas(canvas);
                        SaveFileAction.SAVE_FILE_PATH = fileName;
                    }
            } else {
                JOptionPane.showMessageDialog(null, "Unsupported file format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Failed to load file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();

        }
        LayerPanel.getInstance().updateLayerElements(ViewportPanel.getCanvas());
    }
    
}
