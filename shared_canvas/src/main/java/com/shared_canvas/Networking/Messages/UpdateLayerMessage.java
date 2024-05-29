package com.shared_canvas.Networking.Messages;

import com.shared_canvas.Canvas.CanvasLayer;

public class UpdateLayerMessage extends Message {
    private static final long serialVersionUID = 1L;

    public int layerIndex;
    public CanvasLayer layer;

    public UpdateLayerMessage(String sender, int layerIndex, CanvasLayer layer) {
        super(sender, MessageType.UPDATE_LAYER);
        this.layerIndex = layerIndex;
        this.layer = layer;
    }
}
