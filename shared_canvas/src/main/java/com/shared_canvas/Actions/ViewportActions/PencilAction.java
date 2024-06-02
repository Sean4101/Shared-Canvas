package com.shared_canvas.Actions.ViewportActions;

import com.shared_canvas.Canvas.CanvasLayer;
import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;
import com.shared_canvas.GUI.CollabPanelElements.LayerPanel;
import com.shared_canvas.GUI.ToolPanelElements.ToolPropertiesPanel;
import com.shared_canvas.GUI.ToolPanelElements.ToolSpecificOptionPanel;
import com.shared_canvas.Networking.NetworkManager;
import com.shared_canvas.Networking.Messages.UpdateLayerMessage;

import java.awt.Point;

public class PencilAction extends AbstractViewPortAction {
    
    public int strokeSize;
    public int[][] strokeShape;

    private SharedCanvas sharedCanvas;
    private CanvasLayer currentLayer;
    private CanvasLayer tempLayer;

    private Point lastPoint;

    @Override
    public void mouseDown(Point point) {
        sharedCanvas = ViewportPanel.getCanvas();
        if (sharedCanvas == null) return;
        ViewportPanel.renderFromTempLayerFlag = true;
        currentLayer = LayerPanel.getInstance().activeLayer;
        tempLayer = ViewportPanel.getTempLayer();
        tempLayer.copyFrom(currentLayer);
        Point p = ViewportPanel.getInstance().getCanvasCoordinates(point);
        
        strokeSize = ToolSpecificOptionPanel.getPencilStrokeSize();
        strokeShape = ToolSpecificOptionPanel.getPencilStrokeShape();
        drawStroke(p);
        ViewportPanel.getInstance().repaint();
        lastPoint = point;
    }

    @Override
    public void mouseDragged(Point point) {
        if (sharedCanvas == null) return;
        Point p = ViewportPanel.getInstance().getCanvasCoordinates(point);
        drawLine(ViewportPanel.getInstance().getCanvasCoordinates(lastPoint), p);
        lastPoint = point;
        ViewportPanel.getInstance().repaint();
    }

    @Override
    public void mouseReleased(Point point) {
        if (sharedCanvas == null) return;

        currentLayer.copyFrom(tempLayer);
        ViewportPanel.getInstance().repaint();
        tempLayer.clear();

        ViewportPanel.renderFromTempLayerFlag = false;

        if (NetworkManager.getClient() == null) return;
        String sender = NetworkManager.getClient().getUsername();
        int index = sharedCanvas.layers.indexOf(currentLayer);
        CanvasLayer messageLayer = new CanvasLayer(currentLayer);
        UpdateLayerMessage message = new UpdateLayerMessage(sender, index, messageLayer);
        NetworkManager.getClient().sendMessage(message);
    }

    private void drawStroke(Point point) {
        int x = point.x;
        int y = point.y;

        if (strokeSize == 1) {
            tempLayer.pixels[x][y] = ToolPropertiesPanel.getColor();
            return;
        }

        // If the point is out of bounds, return
        if (x < 0 || x >= currentLayer.width || y < 0 || y >= currentLayer.height) return;

        for (int i = -strokeSize / 2; i < strokeSize / 2; i++) {
            for (int j = -strokeSize / 2; j < strokeSize / 2; j++) {
                if (x + i < 0 || x + i >= currentLayer.width || y + j < 0 || y + j >= currentLayer.height) continue;
                if (strokeShape[i + strokeSize / 2][j + strokeSize / 2] == 1) {
                    tempLayer.pixels[x + i][y + j] = ToolPropertiesPanel.getColor();
                }
            }
        }
    }

    private void drawLine(Point start, Point end) {
        int x0 = start.x;
        int y0 = start.y;
        int x1 = end.x;
        int y1 = end.y;

        // If the start and end points are the same, draw a dot
        if (x0 == x1 && y0 == y1) {
            drawStroke(new Point(x0, y0));
            return;
        }
        // If the start point is out of bounds, return
        if (x0 < 0 || x0 >= currentLayer.width || y0 < 0 || y0 >= currentLayer.height) return;
        // If the end point is out of bounds, set it to the closest in-bounds point
        if (x1 < 0) x1 = 0;
        if (x1 >= currentLayer.width) x1 = currentLayer.width - 1;
        if (y1 < 0) y1 = 0;
        if (y1 >= currentLayer.height) y1 = currentLayer.height - 1;

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;
        int e2;

        // Bresenham's line algorithm
        while (true) {
            drawStroke(new Point(x0, y0));
            if (x0 == x1 && y0 == y1) break;
            e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }
}
