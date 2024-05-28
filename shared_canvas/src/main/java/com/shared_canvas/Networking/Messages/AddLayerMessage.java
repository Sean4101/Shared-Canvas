package com.shared_canvas.Networking.Messages;

public class AddLayerMessage extends Message {
    public int layerIndex;
    public String layerName;

    public AddLayerMessage(String sender, int layerIndex, String layerName) {
        super(sender, MessageType.ADD_LAYER);
        this.layerIndex = layerIndex;
        this.layerName = layerName;
    }
}
