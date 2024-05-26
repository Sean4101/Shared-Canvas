package com.shared_canvas.Canvas;

import java.awt.Color;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;

public class CanvasLayer {

    public int width;
    public int height;
    public boolean visible = true;

    public Color[][] pixels;
    //public int[][] pixelAlpha;

    public CanvasLayer(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new Color[width][height];
        //pixelAlpha = new int[width][height];
    }

    
}
