package com.shared_canvas.GUI;

import javax.swing.*;

public class AppMenuBar extends JMenuBar {

    public JMenu fileMenu = new JMenu("File");
    public JMenuItem newFileItem = new JMenuItem("New");
    public JMenuItem openFileItem = new JMenuItem("Open");
    public JMenuItem saveFileItem = new JMenuItem("Save");
    public JMenuItem saveAsFileItem = new JMenuItem("Save As");
    public JMenuItem exitFileItem = new JMenuItem("Exit");

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
        fileMenu.add(openFileItem);
        fileMenu.add(saveFileItem);
        fileMenu.add(saveAsFileItem);
        fileMenu.addSeparator();
        fileMenu.add(exitFileItem);
        newFileItem.setAccelerator(KeyStroke.getKeyStroke("control N"));

        // Connect Menu
        cnctMenu.setMnemonic('C');
        cnctMenu.add(hostServerItem);
        cnctMenu.add(closeServerItem);
        cnctMenu.addSeparator();
        cnctMenu.add(joinServerItem);
        cnctMenu.add(leaveServerItem);
        hostServerItem.setAccelerator(KeyStroke.getKeyStroke("control H"));

    }
}
