package com.shared_canvas.GUI.ToolPanelElements;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class ToolPropertiesPanel extends JPanel{

    private ColorPickPanel colorPickPanel = new ColorPickPanel();
    private ToolSpecificOptionPanel toolSpecificOptionPanel = new ToolSpecificOptionPanel();

    private static ToolPropertiesPanel instance;

    public static ToolPropertiesPanel getInstance() {
        return instance;
    }
    
    public ToolPropertiesPanel() {
        setPreferredSize(new Dimension(300, 900));
        setBackground(Color.DARK_GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);
        
        setLayout(new BorderLayout());

        add(toolSpecificOptionPanel, BorderLayout.CENTER);
        add(colorPickPanel, BorderLayout.SOUTH);

        instance = this;
    }

    public static Color getColor() {
        return instance.colorPickPanel.getColor();
    }
}
