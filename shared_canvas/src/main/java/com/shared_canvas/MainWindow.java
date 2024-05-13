package com.shared_canvas;

import com.shared_canvas.Actions.*;
import com.shared_canvas.GUI.*;
import java.awt.*;

import javax.swing.*;

public class MainWindow {

    private JFrame window = new JFrame("Shared Canvas");

    public AppMenuBar menuBar = new AppMenuBar();;
    public ToolPanel toolPanel = new ToolPanel();
    public CollabPanel collabPanel = new CollabPanel();
    public ViewportPanel viewportPanel = new ViewportPanel();

    private NewFileAction newFileAction = new NewFileAction();
    private HostAction hostAction = new HostAction();

    private static MainWindow instance;

    public static MainWindow getInstance() {
        if (instance == null) {
            throw new RuntimeException("MainWindow not initialized");
        }
        return instance;
    }

    public MainWindow() {
        instance = this;
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

        menuBar.hostItem.addActionListener(hostAction);
    }
}
