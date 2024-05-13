package com.shared_canvas.GUI.CollabPanelElements;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class LayerPanel extends JPanel {
    
    public LayerPanel() {
        setBackground(Color.DARK_GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);
    }
}