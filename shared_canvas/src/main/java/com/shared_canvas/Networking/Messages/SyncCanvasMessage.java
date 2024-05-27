package com.shared_canvas.Networking.Messages;

import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;

public class SyncCanvasMessage extends Message {
    private static final long serialVersionUID = 1L;

    public SharedCanvas canvas;

    public SyncCanvasMessage(String sender) {
        super(sender, MessageType.SYNC);

        this.canvas = ViewportPanel.getCanvas();
    }
}
