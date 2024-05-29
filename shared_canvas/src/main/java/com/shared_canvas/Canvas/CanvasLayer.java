package com.shared_canvas.Canvas;

import java.awt.Color;
import java.io.Serializable;

public class CanvasLayer implements Serializable{

    public String name;
    public int width;
    public int height;
    public boolean visible = true;

    public Color[][] pixels;
    //public int[][] pixelAlpha;

    public CanvasLayer(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        pixels = new Color[width][height];
        //pixelAlpha = new int[width][height];
    }

    public CanvasLayer(CanvasLayer layer) {
        this.name = layer.name;
        this.width = layer.width;
        this.height = layer.height;
        pixels = new Color[width][height];
        copyFrom(layer);
    }

    public void copyFrom(CanvasLayer layer) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = layer.pixels[i][j];
                //pixelAlpha[i][j] = layer.pixelAlpha[i][j];
            }
        }
    }

    public void clear() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = null;
                //pixelAlpha[i][j] = 0;
            }
        }
    }
}
