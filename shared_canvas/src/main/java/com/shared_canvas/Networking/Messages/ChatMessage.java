package com.shared_canvas.Networking.Messages;

public class ChatMessage extends Message {
    private static final long serialVersionUID = 1L;

    private String message;

    public ChatMessage(String sender, String message) {
        super(sender, MessageType.CHAT);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
