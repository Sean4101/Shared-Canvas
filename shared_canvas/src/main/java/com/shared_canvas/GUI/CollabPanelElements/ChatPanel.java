package com.shared_canvas.GUI.CollabPanelElements;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class ChatPanel extends JPanel {

    public JTextArea chatArea = new JTextArea();
    public TextField chatInput = new TextField();
    
    public ChatPanel() {
        setBackground(Color.DARK_GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);

        setLayout(new BorderLayout());
        chatArea.setEditable(false);
        chatArea.setBackground(Color.LIGHT_GRAY);
        add(chatArea, BorderLayout.CENTER);
        add(chatInput, BorderLayout.SOUTH);
    }
}
