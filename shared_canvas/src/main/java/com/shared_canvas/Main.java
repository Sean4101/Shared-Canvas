package com.shared_canvas;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        SwingUtilities.invokeLater(() -> mainWindow.show());
    }
}