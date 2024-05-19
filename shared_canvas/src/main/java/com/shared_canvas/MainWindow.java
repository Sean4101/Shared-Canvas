package com.shared_canvas;

import com.shared_canvas.Actions.*;
import com.shared_canvas.GUI.*;
import com.shared_canvas.Networking.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class MainWindow {

    private JFrame window = new JFrame("Shared Canvas");

    // GUI components
    public AppMenuBar menuBar = new AppMenuBar();;
    public ToolPanel toolPanel = new ToolPanel();
    public CollabPanel collabPanel = new CollabPanel();
    public ViewportPanel viewportPanel = new ViewportPanel();

    // Network
    public NetworkManager networkManager = new NetworkManager();

    // Actions
    private NewFileAction newFileAction = new NewFileAction(viewportPanel);
    private OpenFileAction openFileAction = new OpenFileAction(viewportPanel); // TODO: Implement this
    private SaveFileAction saveFileAction = new SaveFileAction(viewportPanel);
    private SaveAsFileAction saveAsFileAction = new SaveAsFileAction(viewportPanel); // TODO: Implement this
    private ExportFileAction exportFileAction = new ExportFileAction(viewportPanel); // TODO: Implement this
    private ExitAction exitAction = new ExitAction();

    private HostServerAction hostAction = new HostServerAction();
    private CloseServerAction closeServerAction = new CloseServerAction();
    private JoinServerAction joinServerAction = new JoinServerAction();
    private LeaveServerAction leaveServerAction = new LeaveServerAction();

    private SendChatMessageAction sendChatMessageAction = new SendChatMessageAction();
    private ReceivedMessageAction receivedMessageAction = new ReceivedMessageAction();

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
        menuBar.openFileItem.addActionListener(openFileAction);
        menuBar.saveFileItem.addActionListener(saveFileAction);
        menuBar.saveAsFileItem.addActionListener(saveAsFileAction);
        menuBar.exportFileItem.addActionListener(exportFileAction);
        menuBar.exitItem.addActionListener(exitAction);

        menuBar.hostServerItem.addActionListener(hostAction);
        menuBar.closeServerItem.addActionListener(closeServerAction);
        menuBar.joinServerItem.addActionListener(joinServerAction);
        menuBar.leaveServerItem.addActionListener(leaveServerAction);

        collabPanel.chatPanel.chatInput.addActionListener(sendChatMessageAction);

        viewportPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                viewportPanel.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                viewportPanel.mouseReleased(e);
            }
        });

        NetworkManager.addChatMessageListener(receivedMessageAction);
    }
}
