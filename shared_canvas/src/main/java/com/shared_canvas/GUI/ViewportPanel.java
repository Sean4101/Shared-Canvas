package com.shared_canvas.GUI;

import com.shared_canvas.Canvas.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class ViewportPanel extends JPanel implements ActionListener{

    private double scale;
    private double canvasTopLeftX;
    private double canvasTopLeftY;

    private SharedCanvas canvas;

    public ViewportPanel() {
        setPreferredSize(new Dimension(1100, 900));
        setBackground(Color.GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);

        bindActions();
    }

    public void createNewCanvas(int width, int height) {
        canvas = new SharedCanvas(width, height);

        scale = Math.min(getWidth() / (double) width, getHeight() / (double) height) * 0.9; // 0.9 to leave some margin
        canvasTopLeftX = (getWidth() - width * scale) / 2.0;
        canvasTopLeftY = (getHeight() - height * scale) / 2.0;

        repaint();
    }

    //TODO: You will use this method after opening the canvas opened from a file to load the canvas
    public void loadCanvas(SharedCanvas canvas) {
        this.canvas = canvas;

        scale = Math.min(getWidth() / (double) canvas.width, getHeight() / (double) canvas.height) * 0.9; // 0.9 to leave some margin
        canvasTopLeftX = (getWidth() - canvas.width * scale) / 2.0;
        canvasTopLeftY = (getHeight() - canvas.height * scale) / 2.0;

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (canvas == null) return;

        int startX = (int) canvasTopLeftX;
        if (startX < 0) startX = 0;
        int startY = (int) canvasTopLeftY;
        if (startY < 0) startY = 0;
        int endX = (int) (canvasTopLeftX + canvas.width * scale);
        if (endX > getWidth()) endX = getWidth();
        int endY = (int) (canvasTopLeftY + canvas.height * scale);
        if (endY > getHeight()) endY = getHeight();

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                int canvasX = (int) ((x - canvasTopLeftX) / scale);
                if (canvasX < 0 || canvasX >= canvas.width) continue;
                int canvasY = (int) ((y - canvasTopLeftY) / scale);
                if (canvasY < 0 || canvasY >= canvas.height) continue;
                g.setColor(canvas.getPixel(canvasX, canvasY));
                g.fillRect(x, y, 1, 1);
            }
        }
    }

    private void bindActions() {
        // Bind mouse scroll wheel to zoom in/out
        addMouseWheelListener(e -> {
            int notches = e.getWheelRotation();
            int mouseX = e.getX();
            int mouseY = e.getY();

            double oldScale = scale;

            if (notches < 0) {
                scale *= 1.1;
            } else {
                scale /= 1.1;
            }

            // Zoom in/out from the mouse position
            int x = (int) ((mouseX - canvasTopLeftX) / oldScale);
            int y = (int) ((mouseY - canvasTopLeftY) / oldScale);
            canvasTopLeftX = mouseX - x * scale;
            canvasTopLeftY = mouseY - y * scale;

            repaint();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}