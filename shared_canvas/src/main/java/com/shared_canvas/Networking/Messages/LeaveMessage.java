package com.shared_canvas.Networking.Messages;

public class LeaveMessage extends Message {
    private static final long serialVersionUID = 1L;

    public LeaveMessage(String sender) {
        super(sender, MessageType.LEAVE);
    }
}
