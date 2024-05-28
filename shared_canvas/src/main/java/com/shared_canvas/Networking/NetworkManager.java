package com.shared_canvas.Networking;

import java.io.IOException;
import java.net.*;

import javax.swing.event.EventListenerList;

import com.shared_canvas.Actions.ReceivedMessageAction;
import com.shared_canvas.Canvas.CanvasLayer;
import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;
import com.shared_canvas.GUI.CollabPanelElements.ChatPanel;
import com.shared_canvas.GUI.CollabPanelElements.LayerPanel;
import com.shared_canvas.Networking.Messages.*;
import com.shared_canvas.Networking.Messages.Message.MessageType;

public class NetworkManager {

    public Server server;
    public Client client;

    private EventListenerList chatMessageListeners = new EventListenerList();

    private static NetworkManager instance;

    public NetworkManager() {
        instance = this;
    }

    public static NetworkManager getInstance() {
        return instance;
    }

    public static Client getClient() {
        if (instance == null) {
            return null;
        }
        if (instance.client == null) {
            return null;
        }
        return instance.client;
    }

    public static Server getServer() {
        if (instance == null) {
            return null;
        }
        if (instance.server == null) {
            return null;
        }
        return instance.server;
    }

    public static void hostServer(int port, String hostName) throws IOException {
        instance.server = new Server(port);
        Thread serverThread = new Thread(instance.server);
        serverThread.start();

        joinServer("localhost", port, hostName);
    }

    public static void closeServer() {
        leaveServer();
        if (instance.server != null) {
            instance.server.closeServerSocket();
        }
        instance.server = null;
    }

    public static void joinServer(String address, int port, String username) throws UnknownHostException, IOException {
        Socket socket = new Socket(address, port);
        instance.client = new Client(instance, socket, username);
        Thread clientThread = new Thread(instance.client);
        clientThread.start();
    }

    public static void leaveServer() {
        if (instance.client != null) {
            instance.client.closeEverything();
        }
        instance.client = null;
    }

    public static void sendChatMessage(String message) {
        if (instance.client != null) {
            ChatMessage chatMessage = new ChatMessage(instance.client.getUsername(), message);
            instance.client.sendMessage(chatMessage);
        }
    }

    public static void sendMessage(Message message) {
        if (instance.client != null) {
            instance.client.sendMessage(message);
        }
    }

    public void handleMessages(Message message) {
        MessageType messageType = message.getType();

        switch (messageType) {
            case JOIN:
                System.out.println(message.getSender() + " has joined the server");
                ChatPanel.getInstance().joinedServerMessage(message.getSender());
                break;
            case LEAVE:
                System.out.println(message.getSender() + " has left the server");
                ChatPanel.getInstance().leftServerMessage(message.getSender());
                break;
            case CHAT:
                ChatMessage chatMessage = (ChatMessage) message;
                System.out.println(message.getSender() + ": " + chatMessage.getMessage());
                fireChatMessageReceived(chatMessage);
                break;
            case SYNC:
                SyncCanvasMessage syncCanvasMessage = (SyncCanvasMessage) message;
                handleSyncCanvasMessage(syncCanvasMessage);
                break;
            case ADD_LAYER:
                AddLayerMessage addLayerMessage = (AddLayerMessage) message;
                handleAddLayerMessage(addLayerMessage);
                break;
            case DELETE_LAYER:
                DeleteLayerMessage deleteLayerMessage = (DeleteLayerMessage) message;
                handleDeleteLayerMessage(deleteLayerMessage);
                break;
            case MERGE_LAYER:
                MergeLayerMessage mergeLayerMessage = (MergeLayerMessage) message;
                handleMergeLayerMessage(mergeLayerMessage);
                break;
            case MOVE_LAYER:
                MoveLayerMessage moveLayerMessage = (MoveLayerMessage) message;
                handleMoveLayerMessage(moveLayerMessage);
                break;
            default:
                break;
        }
    }

    public static void addChatMessageListener(ReceivedMessageAction listener) {
        instance.chatMessageListeners.add(ReceivedMessageAction.class, listener);
    }

    public static void removeChatMessageListener(ReceivedMessageAction listener) {
        instance.chatMessageListeners.remove(ReceivedMessageAction.class, listener);
    }

    public static void fireChatMessageReceived(ChatMessage chatMessage) {
        ReceivedMessageAction[] listeners = instance.chatMessageListeners.getListeners(ReceivedMessageAction.class);
        for (ReceivedMessageAction listener : listeners) {
            listener.messageReceived(chatMessage);
        }
    }

    public static void handleSyncCanvasMessage(SyncCanvasMessage message) {
        System.out.println("Syncing canvas");

        SharedCanvas canvas = message.canvas;

        ViewportPanel.getInstance().loadCanvas(canvas);
    }

    public static void handleAddLayerMessage(AddLayerMessage message) {
        if (message.getSender().equals(NetworkManager.getClient().getUsername())) {
            return; // Do not add layer if the message is from the local user
        }
        System.out.println("Adding layer");

        SharedCanvas canvas = ViewportPanel.getCanvas();
        if (message.layerIndex == -1) {
            canvas.addLayer(new CanvasLayer(message.layerName, canvas.width, canvas.height));
            ViewportPanel.getInstance().repaint();
            return;
        }
        canvas.addLayerIn(message.layerIndex + 1, new CanvasLayer(message.layerName, canvas.width, canvas.height));
        ViewportPanel.getInstance().repaint();
        LayerPanel.getInstance().updateLayerElements(canvas);
    }

    public static void handleDeleteLayerMessage(DeleteLayerMessage message) {
        if (message.getSender().equals(NetworkManager.getClient().getUsername())) {
            return; // Do not delete layer if the message is from the local user
        }
        System.out.println("Deleting layer");

        SharedCanvas canvas = ViewportPanel.getCanvas();
        canvas.layers.remove(message.layerIndex);
        if (canvas.layers.size() == 0) {
            LayerPanel.getInstance().activeLayer = null;
        } else {
            LayerPanel.getInstance().activeLayer = canvas.layers.get(0);
        }
        LayerPanel.getInstance().updateLayerElements(canvas);
        ViewportPanel.getInstance().repaint();
    }

    public static void handleMergeLayerMessage(MergeLayerMessage message) {
        if (message.getSender().equals(NetworkManager.getClient().getUsername())) {
            return; // Do not merge layer if the message is from the local user
        }
        System.out.println("Merging layer");

        SharedCanvas canvas = ViewportPanel.getCanvas();
        canvas.mergeLayer(message.layerIndex);
        LayerPanel.getInstance().activeLayer = canvas.layers.get(message.layerIndex - 1);
        LayerPanel.getInstance().updateLayerElements(canvas);
        ViewportPanel.getInstance().repaint();
    }

    public static void handleMoveLayerMessage(MoveLayerMessage message) {
        if (message.getSender().equals(NetworkManager.getClient().getUsername())) {
            return; // Do not move layer if the message is from the local user
        }
        System.out.println("Moving layer");

        SharedCanvas canvas = ViewportPanel.getCanvas();
        if (message.dir == MoveLayerMessage.Direction.UP) {
            LayerPanel.getInstance().moveLayerUp(message.layerIndex);
        } else {
            LayerPanel.getInstance().moveLayerDown(message.layerIndex);
        }
        LayerPanel.getInstance().updateLayerElements(canvas);
        ViewportPanel.getInstance().repaint();
    }
}
