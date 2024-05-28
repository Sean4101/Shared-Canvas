package com.shared_canvas.Networking.Messages;

public class MoveLayerMessage extends Message {

    public int layerIndex;
    public Direction dir;

    public MoveLayerMessage(String sender, int layerIndex, Direction dir) {
        super(sender, MessageType.MOVE_LAYER);
        this.layerIndex = layerIndex;
        this.dir = dir;
    }
    
    public enum Direction {
        UP,
        DOWN
    };
}