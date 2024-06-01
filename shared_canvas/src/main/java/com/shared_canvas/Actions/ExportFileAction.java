package com.shared_canvas.Actions;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;

public class ExportFileAction implements ActionListener {

    private ViewportPanel viewportPanel;

    public ExportFileAction(ViewportPanel viewportPanel) {
        this.viewportPanel = viewportPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //JOptionPane.showMessageDialog(null, "NOT YET IMPLEMENTED", "Error", JOptionPane.ERROR_MESSAGE);
         System.out.println("Export File Action");
        FileDialog fileDialog = new FileDialog( new Frame() , "export file", FileDialog.SAVE );
		fileDialog.show();
		if(fileDialog.getFile()==null) return;
        String fileName = fileDialog.getDirectory()+fileDialog.getFile()+".jpg";
        
        try {
            SharedCanvas canvas = viewportPanel.getCanvas();
            canvas.saveAsJpg(fileName);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Unable to export File", JOptionPane.ERROR_MESSAGE);
        }
        // TODO: Implement the file chooser to export a file
        // You migh need to add a few more classes to handle the export, 
        // if you do, make sure to add them to the Canvas package and access them from here

        // For now, implement the export functionality for .bmp and maybe .jpg files
        // You can do .png files if you want to, but it will likely need to be changed later anyway because of the transparency
    }
}
