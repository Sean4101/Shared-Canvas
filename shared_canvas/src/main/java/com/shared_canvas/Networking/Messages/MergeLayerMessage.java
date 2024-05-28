package com.shared_canvas.Networking.Messages;

public class MergeLayerMessage extends Message {
    public int layerIndex;

    public MergeLayerMessage(String sender, int layerIndex) {
        super(sender, MessageType.MERGE_LAYER);
        this.layerIndex = layerIndex;
    }
}