package com.shared_canvas.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class ViewportPanel extends JPanel {

    public ViewportPanel() {
        setBackground(Color.GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);
    }
}