package com.shared_canvas.Actions.ViewportActions;

import java.awt.Point;

import com.shared_canvas.GUI.ViewportPanel;

public class HandAction extends AbstractViewPortAction {

    Point lastPoint;

    @Override
    public void mouseDown(Point point) {
        lastPoint = point;
    }

    @Override
    public void mouseDragged(Point point) {
        Point currentPoint = point;
        int dx = currentPoint.x - lastPoint.x;
        int dy = currentPoint.y - lastPoint.y;

        ViewportPanel.getInstance().moveViewport(dx, dy);

        lastPoint = currentPoint;
    }

    @Override
    public void mouseReleased(Point point) {
        
    }
}
