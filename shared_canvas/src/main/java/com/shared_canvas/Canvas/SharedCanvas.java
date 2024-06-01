package com.shared_canvas.Canvas;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SharedCanvas implements Serializable{

    public int width;
    public int height;
    public Color backgroundColor;
    public ArrayList<CanvasLayer> layers;
    private String currentFilePath;

    public SharedCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.backgroundColor = Color.WHITE;
        this.layers = new ArrayList<CanvasLayer>();

        addLayer(new CanvasLayer("Layer 1", width, height));
    }

    public void addLayer(CanvasLayer layer) {
        layers.add(layer);
    }

    public void addLayerIn(int index, CanvasLayer layer) {
        layers.add(index, layer);
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
            if (layer.pixels[x][y] != null && layer.visible) return layer.pixels[x][y];
        }
        return backgroundColor;
    }

    public void loadFromBufferedImage(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.layers.clear();
        CanvasLayer layer = new CanvasLayer("jpg", width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                layer.pixels[x][y] = new Color(image.getRGB(x, y));
            }
        }
        addLayer(layer);
    }

    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(this);
            out.flush();
            out.close();
        }
    }

    public static SharedCanvas loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (SharedCanvas) in.readObject();
        }
    }
    

    public void mergeLayer(int index) {
        CanvasLayer layer = layers.remove(index); // extract layer
        CanvasLayer bottomLayer = layers.get(index - 1); // get the layer below

        for (int x = 0; x < layer.width; x++) {
            for (int y = 0; y < layer.height; y++) {
                if (layer.pixels[x][y] != null) {
                    bottomLayer.pixels[x][y] = layer.pixels[x][y];
                }
            }
        }
    }

    public void saveAsJpg(String filePath) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // Fill the background
        g.setColor(backgroundColor);
        g.fillRect(0, 0, width, height);

        // Draw each layer
        for (CanvasLayer layer : layers) {
            if (layer.visible) {
                for (int x = 0; x < layer.width; x++) {
                    for (int y = 0; y < layer.height; y++) {
                        Color color = layer.pixels[x][y];
                        if (color != null) {
                            g.setColor(color);
                            g.fillRect(x, y, 1, 1);
                        }
                    }
                }
            }
        }

        g.dispose();

        File file = new File(filePath);
        ImageIO.write(image, "jpg", file);
    }
}
