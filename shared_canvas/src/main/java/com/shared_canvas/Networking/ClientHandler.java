package com.shared_canvas.Networking;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import com.shared_canvas.GUI.ViewportPanel;
import com.shared_canvas.Networking.Messages.*;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
    private Socket socket;
    private String username;
    
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());

            Message joinMessage = (Message) objectInputStream.readObject();
            this.username = joinMessage.getSender();
            clientHandlers.add(this);
            broadcastMessage(joinMessage);

            // If the client is not self, send the canvas to the client
            if (clientHandlers.size() > 1 && ViewportPanel.getCanvas() != null) {
                sendClientSpecificMessage(new SyncCanvasMessage(username));
            }
        }
        catch (IOException e) {
            closeEverything(socket);
        } 
        catch (ClassNotFoundException e) {
            closeEverything(socket);
        }
    }

    @Override
    public void run() {
        Message messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = (Message) objectInputStream.readObject();
                broadcastMessage(messageFromClient);
            }
            catch (IOException e) {
                closeEverything(socket);
                break;
            }
            catch (ClassNotFoundException e) {
                closeEverything(socket);
                break;
            }
        }
    }

    public void broadcastMessage(Message message) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                clientHandler.objectOutputStream.writeObject(message);
                clientHandler.objectOutputStream.flush();
            }
            catch (IOException e) {
                closeEverything(socket);
            }
        }
    }

    public void sendClientSpecificMessage(Message message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }
        catch (IOException e) {
            closeEverything(socket);
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        Message removeMessage = new LeaveMessage(username);
        broadcastMessage(removeMessage);
    }

    private void closeEverything(Socket socket) {
        removeClientHandler();
        try {
            if (socket != null) {
                socket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
