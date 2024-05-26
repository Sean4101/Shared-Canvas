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

    
}
