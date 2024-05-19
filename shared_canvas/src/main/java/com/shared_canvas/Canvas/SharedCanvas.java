package com.shared_canvas.Canvas;

import java.awt.Color;
import java.util.ArrayList;

public class SharedCanvas {

    public int width;
    public int height;
    public Color backgroundColor;
    public ArrayList<CanvasLayer> layers;

    public SharedCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.backgroundColor = Color.WHITE;
        this.layers = new ArrayList<CanvasLayer>();

        addLayer(new CanvasLayer(width, height));
    }

    public void addLayer(CanvasLayer layer) {
        layers.add(layer);
    }

    public void removeLayer(CanvasLayer layer) {
        layers.remove(layer);
    }

    public CanvasLayer getLayer(int index) {
        return layers.get(index);
    }

    public Color getPixel(int x, int y) {
        for (int i = layers.size() - 1; i >= 0; i--) {
            CanvasLayer layer = layers.get(i);
            if (x < 0 || x >= layer.width || y < 0 || y >= layer.height) continue;
            if (layer.pixels[x][y] != null) return layer.pixels[x][y];
        }
        return backgroundColor;
    }
}
