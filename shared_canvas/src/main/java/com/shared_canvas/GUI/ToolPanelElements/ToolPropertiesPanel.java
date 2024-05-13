package com.shared_canvas.GUI.ToolPanelElements;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class ToolPropertiesPanel extends JPanel{
    
    public ToolPropertiesPanel() {
        setPreferredSize(new Dimension(250, 900));
        setBackground(Color.DARK_GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);
    }
}
