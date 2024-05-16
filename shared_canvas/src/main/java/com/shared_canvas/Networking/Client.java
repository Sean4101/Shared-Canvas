package com.shared_canvas.Networking;

import com.shared_canvas.Networking.Messages.*;

import java.io.*;
import java.net.*;

public class Client implements Runnable {

    private NetworkManager networkManager;
    private Socket socket;
    private String username;
    
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public Client(NetworkManager networkManager, Socket socket, String username) {
        try {
            this.networkManager = networkManager;
            this.socket = socket;
            this.username = username;
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            closeEverything();
        }
    }

    @Override
    public void run() {
        sendJoinMessage();
        listenForMessages();
    }

    private void sendJoinMessage() {
        try {
            Message joinMessage = new JoinMessage(username);
            objectOutputStream.writeObject(joinMessage);
            objectOutputStream.flush();
        }
        catch (IOException e) {
            closeEverything();
        }
    }

    public void sendMessage(Message message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }
        catch (IOException e) {
            closeEverything();
        }
    }

    public void listenForMessages() {
        new Thread() {
            @Override
            public void run() {
                Message messageFromServer;
                
                System.out.println("Listening for messages from server");
                while (socket.isConnected()) {
                    try {
                        messageFromServer = (Message) objectInputStream.readObject();
                        networkManager.handleMessages(messageFromServer);
                    }
                    catch (IOException e) {
                        closeEverything();
                        break;
                    } 
                    catch (ClassNotFoundException e) {
                        closeEverything();
                        break;
                    }
                }
            }
        }.start();
    }

    public void closeEverything() {
        try {
            if (socket != null) {
                socket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }
}
