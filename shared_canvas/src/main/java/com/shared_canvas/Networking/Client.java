package com.shared_canvas.Networking;

import com.shared_canvas.Networking.Messages.Message;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client implements Runnable {

    private Socket socket;
    private String username;
    
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            this.username = username;
        }
        catch (IOException e) {
            closeEverything();
        }
    }

    @Override
    public void run() {
        listenForMessages();
        sendMessage();
    }

    public void sendMessage() {
        try {
            Message joinMessage = new Message(username, "(joined the canvas)");
            objectOutputStream.writeObject(joinMessage);
            objectOutputStream.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                Message message = new Message(username, messageToSend);
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            }
            scanner.close();
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
                        System.out.println(messageFromServer.getSender() + ": " + messageFromServer.getMessage());
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
}
