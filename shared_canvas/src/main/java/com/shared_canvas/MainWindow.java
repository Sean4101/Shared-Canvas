package com.shared_canvas;

import com.shared_canvas.Actions.*;
import com.shared_canvas.GUI.*;
import com.shared_canvas.Networking.*;

import java.awt.*;

import javax.swing.*;

public class MainWindow {

    private JFrame window = new JFrame("Shared Canvas");

    // GUI components
    public AppMenuBar menuBar = new AppMenuBar();;
    public ToolPanel toolPanel = new ToolPanel();
    public CollabPanel collabPanel = new CollabPanel();
    public ViewportPanel viewportPanel = new ViewportPanel();

    // Network
    private NetworkManager networkManager = new NetworkManager();

    // Actions
    private NewFileAction newFileAction = new NewFileAction();

    private HostServerAction hostAction = new HostServerAction(networkManager);
    private CloseServerAction closeServerAction = new CloseServerAction(networkManager);
    private JoinServerAction joinServerAction = new JoinServerAction(networkManager);
    private LeaveServerAction leaveServerAction = new LeaveServerAction(networkManager);

    private ReceivedMessageAction receivedMessageAction = new ReceivedMessageAction(collabPanel.chatPanel.chatArea);

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
    }

    public void show() {
        window.setVisible(true);
    }

    private void bindActions() {
        menuBar.newFileItem.addActionListener(newFileAction);

        menuBar.hostServerItem.addActionListener(hostAction);
        menuBar.closeServerItem.addActionListener(closeServerAction);
        menuBar.joinServerItem.addActionListener(joinServerAction);
        menuBar.leaveServerItem.addActionListener(leaveServerAction);

        //TODO: This is just a placeholder, need to implement the actual action
        collabPanel.chatPanel.chatInput.addActionListener(e -> {
            String message = collabPanel.chatPanel.chatInput.getText();
            collabPanel.chatPanel.chatInput.setText("");

            if (message == null || message.isEmpty()) {
                return;
            }

            if (networkManager.client == null) {
                JOptionPane.showMessageDialog(null, "Not connected to server", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            networkManager.sendChatMessage(message);
        });

        networkManager.addChatMessageListener(receivedMessageAction);
    }
}
