package com.shared_canvas.Networking;

import java.io.IOException;
import java.net.*;

import javax.swing.event.EventListenerList;

import com.shared_canvas.Actions.ReceivedMessageAction;
import com.shared_canvas.Networking.Messages.*;
import com.shared_canvas.Networking.Messages.Message.MessageType;

public class NetworkManager {

    public Server server;
    public Client client;

    private EventListenerList chatMessageListeners = new EventListenerList();

    public void hostServer(int port, String hostName) throws IOException {
        server = new Server(port);
        Thread serverThread = new Thread(server);
        serverThread.start();

        joinServer("localhost", port, hostName);
    }

    public void closeServer() {
        leaveServer();
        if (server != null) {
            server.closeServerSocket();
        }
        server = null;
    }

    public void joinServer(String address, int port, String username) throws UnknownHostException, IOException {
        Socket socket = new Socket(address, port);
        client = new Client(this, socket, username);
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

    public void leaveServer() {
        if (client != null) {
            client.closeEverything();
        }
        client = null;
    }

    public void sendChatMessage(String message) {
        if (client != null) {
            ChatMessage chatMessage = new ChatMessage(client.getUsername(), message);
            client.sendMessage(chatMessage);
        }
    }

    public void sendMessage(Message message) {
        if (client != null) {
            client.sendMessage(message);
        }
    }

    public void handleMessages(Message message) {
        MessageType messageType = message.getType();

        switch (messageType) {
            case JOIN:
                System.out.println(message.getSender() + " has joined the server");
                break;
            case LEAVE:
                System.out.println(message.getSender() + " has left the server");
                break;
            case CHAT:
                ChatMessage chatMessage = (ChatMessage) message;
                System.out.println(message.getSender() + ": " + chatMessage.getMessage());
                fireChatMessageReceived(chatMessage);
                break;
            default:
                break;
        }
    }

    public void addChatMessageListener(ReceivedMessageAction listener) {
        chatMessageListeners.add(ReceivedMessageAction.class, listener);
    }

    public void removeChatMessageListener(ReceivedMessageAction listener) {
        chatMessageListeners.remove(ReceivedMessageAction.class, listener);
    }

    public void fireChatMessageReceived(ChatMessage chatMessage) {
        ReceivedMessageAction[] listeners = chatMessageListeners.getListeners(ReceivedMessageAction.class);
        for (ReceivedMessageAction listener : listeners) {
            listener.messageReceived(chatMessage);
        }
    }
}
