package com.shared_canvas.Networking.Messages;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sender;
    private MessageType type;

    public Message(String sender, MessageType type) {
        this.sender = sender;
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public MessageType getType() {
        return type;
    }

    public enum MessageType {
        JOIN,
        LEAVE,
        CHAT,
        SYNC,
    }
}
