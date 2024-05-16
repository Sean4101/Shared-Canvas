package com.shared_canvas.Networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkManager {

    public Server server;
    public Client client;

    public void hostServer(int port) throws IOException {
        server = new Server(port);
        Thread serverThread = new Thread(server);
        serverThread.start();

        joinServer("localhost", port, "Host");
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
        client = new Client(socket, username);
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

    public void leaveServer() {
        if (client != null) {
            client.closeEverything();
        }
        client = null;
    }
}
