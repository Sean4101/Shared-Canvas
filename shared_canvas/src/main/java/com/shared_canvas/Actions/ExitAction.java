package com.shared_canvas.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO: Check if the user wants to save the canvas before exiting

        System.exit(0);
    }
}
