package com.shared_canvas.GUI;

import javax.swing.*;

public class AppMenuBar extends JMenuBar {

    public JMenu fileMenu = new JMenu("File");
    public JMenuItem newFileItem = new JMenuItem("New");
    public JMenuItem openFileItem = new JMenuItem("Open");
    public JMenuItem saveFileItem = new JMenuItem("Save");
    public JMenuItem saveAsFileItem = new JMenuItem("Save As");
    public JMenuItem exportFileItem = new JMenuItem("Export");
    public JMenuItem exitItem = new JMenuItem("Exit");

    public JMenu editMenu = new JMenu("Edit");
    
    public JMenu viewMenu = new JMenu("View");

    public JMenu cnctMenu = new JMenu("Connect");
    public JMenuItem hostServerItem = new JMenuItem("Host");
    public JMenuItem closeServerItem = new JMenuItem("Close");
    public JMenuItem joinServerItem = new JMenuItem("Join");
    public JMenuItem leaveServerItem = new JMenuItem("Leave");

    public JMenu helpMenu = new JMenu("Help");

    public AppMenuBar() {
        super();

        initMenu();

        add(fileMenu);
        add(editMenu);
        add(viewMenu);
        add(cnctMenu);
        add(helpMenu);
    }

    private void initMenu() {
        // File Menu
        fileMenu.setMnemonic('F');
        fileMenu.add(newFileItem);
        newFileItem.setAccelerator(KeyStroke.getKeyStroke("control N"));
        fileMenu.add(openFileItem);
        openFileItem.setAccelerator(KeyStroke.getKeyStroke("control O"));
        fileMenu.addSeparator();
        fileMenu.add(saveFileItem);
        saveFileItem.setAccelerator(KeyStroke.getKeyStroke("control S"));
        fileMenu.add(saveAsFileItem);
        saveAsFileItem.setAccelerator(KeyStroke.getKeyStroke("control shift S"));
        fileMenu.add(exportFileItem);
        exportFileItem.setAccelerator(KeyStroke.getKeyStroke("control E"));
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Connect Menu
        cnctMenu.setMnemonic('C');
        cnctMenu.add(hostServerItem);
        hostServerItem.setAccelerator(KeyStroke.getKeyStroke("control H"));
        cnctMenu.add(closeServerItem);
        cnctMenu.addSeparator();
        cnctMenu.add(joinServerItem);
        cnctMenu.add(leaveServerItem);

    }
}
