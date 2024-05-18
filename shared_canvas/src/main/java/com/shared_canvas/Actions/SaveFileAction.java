package com.shared_canvas.Actions;

import java.awt.event.*;
import javax.swing.*;

import com.shared_canvas.GUI.ViewportPanel;

public class SaveFileAction implements ActionListener {

    private ViewportPanel viewportPanel;

    public SaveFileAction(ViewportPanel viewportPanel) {
        this.viewportPanel = viewportPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "NOT YET IMPLEMENTED", "Error", JOptionPane.ERROR_MESSAGE);

        // TODO: We will implement SaveAsFileAction first, ignore this for now
    }
}
