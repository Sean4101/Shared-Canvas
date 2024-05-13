package com.shared_canvas.GUI.ToolPanelElements;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class ToolBarPanel extends JPanel {

    public JButton brushButton = new JButton();
    public JButton eraserButton = new JButton();

    public ToolBarPanel() {
        setPreferredSize(new Dimension(50, 900));
        setBackground(Color.DARK_GRAY);
        
        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);

        setLayout(new FlowLayout());

        brushButton.setPreferredSize(new Dimension(40, 40));
        add(brushButton);

        eraserButton.setPreferredSize(new Dimension(40, 40));
        add(eraserButton);
    }
}
