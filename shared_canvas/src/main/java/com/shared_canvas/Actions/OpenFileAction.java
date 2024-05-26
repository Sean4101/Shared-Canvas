package com.shared_canvas.Actions;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image.*;

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

        FileDialog fileDialog = new FileDialog(new Frame(), "Open File", FileDialog.LOAD);
        fileDialog.setVisible(true);
        if (fileDialog.getFile() == null) return;
        String fileName = fileDialog.getDirectory() + fileDialog.getFile();

        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            if (image != null) {
                SharedCanvas canvas = new SharedCanvas(image.getWidth(), image.getHeight());
                canvas.loadFromBufferedImage(image);
                viewportPanel.loadCanvas(canvas);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Unable to Open File", JOptionPane.ERROR_MESSAGE);
        }
       
        
        // TODO: Load the canvas from the file, see ViewportPanel.loadCanvas(SharedCanvas canvas)

        LayerPanel.getInstance().updateLayerElements(ViewportPanel.getCanvas());
    }
    
}
