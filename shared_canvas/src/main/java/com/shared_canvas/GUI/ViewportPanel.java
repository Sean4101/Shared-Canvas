package com.shared_canvas.GUI;

import com.shared_canvas.Canvas.*;
import com.shared_canvas.GUI.CollabPanelElements.LayerPanel;
import com.shared_canvas.GUI.ToolPanelElements.ToolBarPanel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

public class ViewportPanel extends JPanel{

    private double scale;
    private double canvasTopLeftX;
    private double canvasTopLeftY;

    private SharedCanvas canvas;
    private CanvasLayer tempLayer;
    private ViewportMouseTracker mouseTracker;

    private static ViewportPanel instance;

    public static boolean renderFromTempLayerFlag = false;

    public static ViewportPanel getInstance() {
        return instance;
    }

    public static SharedCanvas getCanvas() {
        return instance.canvas;
    }

    public static CanvasLayer getTempLayer() {
        return instance.tempLayer;
    }

    public ViewportPanel() {
        instance = this;

        setPreferredSize(new Dimension(1100, 900));
        setBackground(Color.GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);

        bindActions();
    }

    public void createNewCanvas(int width, int height) {
        canvas = new SharedCanvas(width, height);
        tempLayer = new CanvasLayer("Temp Layer", width, height);
        LayerPanel.getInstance().activeLayer = canvas.getLayer(0);

        scale = Math.min(getWidth() / (double) width, getHeight() / (double) height) * 0.9; // 0.9 to leave some margin
        canvasTopLeftX = (getWidth() - width * scale) / 2.0;
        canvasTopLeftY = (getHeight() - height * scale) / 2.0;

        repaint();
    }

    public void loadCanvas(SharedCanvas canvas) {
        this.canvas = canvas;
        tempLayer = new CanvasLayer("Temp Layer", canvas.width, canvas.height);
        if (canvas.layers.size() == 0)
            canvas.addLayer(new CanvasLayer("Layer 1", canvas.width, canvas.height));
        LayerPanel.getInstance().activeLayer = canvas.getLayer(0);

        scale = Math.min(getWidth() / (double) canvas.width, getHeight() / (double) canvas.height) * 0.9; // 0.9 to leave some margin
        canvasTopLeftX = (getWidth() - canvas.width * scale) / 2.0;
        canvasTopLeftY = (getHeight() - canvas.height * scale) / 2.0;

        repaint();

        LayerPanel.getInstance().updateLayerElements(canvas);
    }

    public void moveViewport(int dx, int dy) {
        canvasTopLeftX += dx;
        canvasTopLeftY += dy;
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
                int canvasY = (int) ((y - canvasTopLeftY) / scale);

                Color color = viewportGetPixel(canvasX, canvasY);
                g.setColor(color);
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

    private Color viewportGetPixel(int x, int y) {
        if (canvas == null) return null;
        if (x < 0 || x >= canvas.width || y < 0 || y >= canvas.height) return null;

        if (renderFromTempLayerFlag) {
            for (int i = canvas.layers.size() - 1; i >= 0; i--) {
                CanvasLayer layer = canvas.layers.get(i);
                if (LayerPanel.getInstance().activeLayer == layer) {
                    if (tempLayer.pixels[x][y] != null) return tempLayer.pixels[x][y];
                }
                else {
                    if (x < 0 || x >= layer.width || y < 0 || y >= layer.height) continue;
                    if (layer.pixels[x][y] != null && layer.visible) return layer.pixels[x][y];
                }
            }
            return canvas.backgroundColor;
        } 
        else {
            return canvas.getPixel(x, y);
        }
    }

    public Point getCanvasCoordinates(Point point) {
        int x = (int) ((point.x - canvasTopLeftX) / scale);
        int y = (int) ((point.y - canvasTopLeftY) / scale);
        return new Point(x, y);
    }

    public void mousePressed(MouseEvent e) {
        ToolBarPanel.getInstance().getActiveToolAction().mouseDown(e.getPoint());
        Thread thread = new Thread(mouseTracker = new ViewportMouseTracker(this, 60.0));
        thread.start();
    }

    public void mouseDragging(Point point) {
        ToolBarPanel.getInstance().getActiveToolAction().mouseDragged(point);
    }

    public void mouseReleased(MouseEvent e) {
        ToolBarPanel.getInstance().getActiveToolAction().mouseReleased(e.getPoint());
        mouseTracker.stop();
    }

    public class ViewportMouseTracker implements Runnable {

        private ViewportPanel viewportPanel;
        private Double fps;
        private boolean running = true;

        private Point lastMousePosition = null;

        public ViewportMouseTracker(ViewportPanel viewportPanel, Double fps) {
            this.viewportPanel = viewportPanel;
            this.fps = fps;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep((long) (1000.0 / fps));
                    Point mousePosition = MouseInfo.getPointerInfo().getLocation();
                    SwingUtilities.convertPointFromScreen(mousePosition, viewportPanel);
                    if (mousePosition.equals(lastMousePosition)) continue; // Skip if the mouse position hasn't changed
                    lastMousePosition = mousePosition;
                    viewportPanel.mouseDragging(mousePosition);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stop() {
            running = false;
        }
    }


}