package com.shared_canvas.Actions.ViewportActions;

import java.awt.Point;

import com.shared_canvas.Canvas.CanvasLayer;
import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;
import com.shared_canvas.GUI.CollabPanelElements.LayerPanel;
import com.shared_canvas.GUI.ToolPanelElements.ToolPropertiesPanel;
import com.shared_canvas.GUI.ToolPanelElements.ToolSpecificOptionPanel;
import com.shared_canvas.Networking.NetworkManager;
import com.shared_canvas.Networking.Messages.UpdateLayerMessage;

public class EraserAction extends AbstractViewPortAction {

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
        
        strokeSize = ToolSpecificOptionPanel.getEraserStrokeSize();
        strokeShape = ToolSpecificOptionPanel.getEraserStrokeShape();
        eraseStroke(p);
        ViewportPanel.getInstance().repaint();
        lastPoint = point;
    }

    @Override
    public void mouseDragged(Point point) {
        if (sharedCanvas == null) return;
        Point p = ViewportPanel.getInstance().getCanvasCoordinates(point);
        eraseLine(ViewportPanel.getInstance().getCanvasCoordinates(lastPoint), p);
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

    private void eraseStroke(Point point) {
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
                    tempLayer.pixels[x + i][y + j] = null;
                }
            }
        }
    }

    private void eraseLine(Point point1, Point point2) {
        int x1 = point1.x;
        int y1 = point1.y;
        int x2 = point2.x;
        int y2 = point2.y;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;
        int e2;

        while (true) {
            eraseStroke(new Point(x1, y1));

            if (x1 == x2 && y1 == y2) break;

            e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }
}
