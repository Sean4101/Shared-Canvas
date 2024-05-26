package com.shared_canvas.Actions.ViewportActions;

import com.shared_canvas.Canvas.CanvasLayer;
import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;
import com.shared_canvas.GUI.CollabPanelElements.LayerPanel;
import com.shared_canvas.GUI.ToolPanelElements.ToolPropertiesPanel;

import java.awt.Color;
import java.awt.Point;

public class PencilAction extends AbstractViewPortAction {

    public int thickness = 5;

    private SharedCanvas sharedCanvas;
    private CanvasLayer currentLayer;

    private Point lastPoint;

    @Override
    public void mouseDown(Point point) {
        sharedCanvas = ViewportPanel.getCanvas();
        if (sharedCanvas == null) return;
        // Should access this way, but the function is not implemented
        //currentLayer = sharedCanvas.getCurrentLayer(); 
        currentLayer = LayerPanel.getInstance().activeLayer;
        Point p = ViewportPanel.getInstance().getCanvasCoordinates(point);
        
        currentLayer.pixels[p.x][p.y] = Color.BLACK;
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
    }

    private void drawDot(Point point) {
        int x = point.x;
        int y = point.y;

        // If the point is out of bounds, return
        if (x < 0 || x >= currentLayer.width || y < 0 || y >= currentLayer.height) return;

        for (int i = -thickness / 2; i < thickness / 2; i++) {
            for (int j = -thickness / 2; j < thickness / 2; j++) {
                if (x + i < 0 || x + i >= currentLayer.width || y + j < 0 || y + j >= currentLayer.height) continue;
                currentLayer.pixels[x + i][y + j] = ToolPropertiesPanel.getColor();
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
            drawDot(new Point(x0, y0));
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
            drawDot(new Point(x0, y0));
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
