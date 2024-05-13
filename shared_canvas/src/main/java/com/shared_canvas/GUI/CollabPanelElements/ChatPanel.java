package com.shared_canvas.GUI.CollabPanelElements;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class ChatPanel extends JPanel {
    
    public ChatPanel() {
        setBackground(Color.DARK_GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);
    }
}
