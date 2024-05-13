package com.shared_canvas;

import com.shared_canvas.Actions.*;
import com.shared_canvas.GUI.*;
import com.shared_canvas.Networking.*;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.*;

public class MainWindow {

    private JFrame window = new JFrame("Shared Canvas");

    // GUI components
    public AppMenuBar menuBar = new AppMenuBar();;
    public ToolPanel toolPanel = new ToolPanel();
    public CollabPanel collabPanel = new CollabPanel();
    public ViewportPanel viewportPanel = new ViewportPanel();

    // Actions
    private NewFileAction newFileAction = new NewFileAction();
    private HostAction hostAction = new HostAction();

    // Network
    private Server server;

    public MainWindow() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1600, 900);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        window.setJMenuBar(menuBar);
        window.add(toolPanel, BorderLayout.WEST);
        window.add(collabPanel, BorderLayout.EAST);
        window.add(viewportPanel, BorderLayout.CENTER);

        bindActions();
        initNetwork();
    }

    public void show() {
        window.setVisible(true);
    }

    private void bindActions() {
        menuBar.newFileItem.addActionListener(newFileAction);

        menuBar.hostItem.addActionListener(hostAction);
    }

    private void initNetwork() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(8090);
        } 
        catch (IOException e) {
            System.out.println("Failed to create server socket");
            return;
        }
        server = new Server(serverSocket);
        server.startServer();
    }
}
