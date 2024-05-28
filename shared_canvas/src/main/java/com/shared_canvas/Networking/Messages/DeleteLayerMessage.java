package com.shared_canvas.Networking.Messages;

public class DeleteLayerMessage extends Message {
    public int layerIndex;

    public DeleteLayerMessage(String sender, int layerIndex) {
        super(sender, MessageType.DELETE_LAYER);
        this.layerIndex = layerIndex;
    }
}
