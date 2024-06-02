package com.shared_canvas.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import com.shared_canvas.GUI.ToolPanelElements.*;

public class ToolPanel extends JPanel {

    public ToolPropertiesPanel toolPropertiesPanel = new ToolPropertiesPanel();
    public ToolBarPanel toolBarPanel = new ToolBarPanel();

    public ToolPanel() {
        setPreferredSize(new Dimension(200, 900));
        setBackground(Color.GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);

        setLayout(new BorderLayout());
        add(toolBarPanel, BorderLayout.WEST);
        add(toolPropertiesPanel, BorderLayout.CENTER);
    }
}