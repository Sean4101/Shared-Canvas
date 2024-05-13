package com.shared_canvas.Actions;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewFileAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("New File Action");

        JTextField text0 = new JTextField();
        JTextField text1 = new JTextField();

        JPanel popupPanel = new JPanel(new GridLayout(0, 2));
        popupPanel.add(new JLabel("String 0: "));
        popupPanel.add(text0);
        popupPanel.add(new JLabel("String 1: "));
        popupPanel.add(text1);

        int result = JOptionPane.showConfirmDialog(null, popupPanel, "New File", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            System.out.println("String 0: " + text0.getText());
            System.out.println("String 1: " + text1.getText());
        }
    }
}
