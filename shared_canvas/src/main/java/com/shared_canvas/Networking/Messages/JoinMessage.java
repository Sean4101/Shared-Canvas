package com.shared_canvas.Networking.Messages;

public class JoinMessage extends Message {
    private static final long serialVersionUID = 1L;

    public JoinMessage(String sender) {
        super(sender, MessageType.JOIN);
    }
}
