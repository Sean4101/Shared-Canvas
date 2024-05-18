package com.shared_canvas.Actions;

import java.awt.event.*;
import javax.swing.*;

import com.shared_canvas.GUI.ViewportPanel;

public class SaveAsFileAction implements ActionListener {

    private ViewportPanel viewportPanel;

    public SaveAsFileAction(ViewportPanel viewportPanel) {
        this.viewportPanel = viewportPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "NOT YET IMPLEMENTED", "Error", JOptionPane.ERROR_MESSAGE);

        // TODO: Implement the file chooser to save a file
        // you will need to access the SharedCanvas object from the viewportPanel
    }
}
