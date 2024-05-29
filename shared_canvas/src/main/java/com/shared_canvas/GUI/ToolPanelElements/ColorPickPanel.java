package com.shared_canvas.GUI.ToolPanelElements;

import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;

public class ColorPickPanel extends JPanel {
    private Color selectedColor = Color.BLACK;
    private JButton colorChooserButton = new JButton("Color Chooser");
    private JPanel colorPreviewPanel = new JPanel();

    public ColorPickPanel() {
        setPreferredSize(new Dimension(300, 200));
        setBorder(BorderFactory.createEtchedBorder());
        setLayout(null);

        colorChooserButton.setBounds(10,70,120,20);
        colorPreviewPanel.setBounds(10, 100, 120, 90);
        colorPreviewPanel.setBackground(Color.BLACK);

        add(colorChooserButton);
        add(colorPreviewPanel);

        colorChooserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedColor = JColorChooser.showDialog(null, "Color Chooser", selectedColor);
                updateColorPreview();
            }
        });
    }

    private void updateColorPreview() {
        colorPreviewPanel.setBackground(selectedColor);
    }

    public Color getColor() {
        return selectedColor;
    }
}