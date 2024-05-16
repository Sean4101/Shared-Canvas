package com.shared_canvas.Networking;

import java.io.IOException;
import java.net.*;

public class Server implements Runnable {

    private ServerSocket serverSocket;

    public Server(int port) throws IOException{
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        startServer();
    }

    public void startServer() {
        System.out.println("Server started");
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServerSocket() {
        System.out.println("Server closed");
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
