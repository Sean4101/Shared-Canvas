package com.shared_canvas.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import com.shared_canvas.GUI.CollabPanelElements.*;

public class CollabPanel extends JPanel {

    public LayerPanel layerPanel = new LayerPanel();
    public ChatPanel chatPanel = new ChatPanel();

    public CollabPanel() {
        setPreferredSize(new Dimension(300, 900));
        setBackground(Color.GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);

        setLayout(new GridLayout(2, 1));
        add(layerPanel);
        add(chatPanel);
    }
}
