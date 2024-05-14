package com.shared_canvas.Networking2;

import com.shared_canvas.Networking2.Messages.Message;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

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
            closeEverything(socket);
        }
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
            closeEverything(socket);
        }
    }

    public void listenForMessages() {
        new Thread() {
            @Override
            public void run() {
                Message messageFromServer;
                
                while (socket.isConnected()) {
                    try {
                        messageFromServer = (Message) objectInputStream.readObject();
                        System.out.println(messageFromServer.getSender() + ": " + messageFromServer.getMessage());
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
        }.start();
    }

    public void closeEverything(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("You are now connected to the server as " + username);

        Socket socket = new Socket("localhost", 8090);
        Client client = new Client(socket, username);
        client.listenForMessages();
        client.sendMessage();
        scanner.close();
    }
}
