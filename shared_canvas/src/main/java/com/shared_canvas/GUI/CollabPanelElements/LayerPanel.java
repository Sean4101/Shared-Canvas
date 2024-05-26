package com.shared_canvas.GUI.CollabPanelElements;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import com.shared_canvas.Canvas.CanvasLayer;
import com.shared_canvas.Canvas.SharedCanvas;
import com.shared_canvas.GUI.ViewportPanel;
import com.shared_canvas.GUI.ToolPanelElements.ColorPickPanel;

public class LayerPanel extends JPanel {

    private JScrollPane layerScrollPane = new JScrollPane();
    private JPanel layerElementsPanel = new JPanel();
    private JPanel layerOptionsPanel = new JPanel();

    private ArrayList<LayerElement> layerElements = new ArrayList<>();
    private BackGroundLayerElement backGroundLayerElement;

    public JButton addLayerButton = new JButton("Add Layer");
    public JButton mergeLayerButton = new JButton("Merge Layer");
    public JButton deleteLayerButton = new JButton("Delete Layer");

    public CanvasLayer activeLayer;

    private static LayerPanel instance;

    public static LayerPanel getInstance() {
        return instance;
    }
    
    public LayerPanel() {
        setBackground(Color.DARK_GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);

        initLayerElementsPanel();
        initLayerOptionsPanel();

        setLayout(new BorderLayout());
        
        add(layerScrollPane, BorderLayout.CENTER);
        add(layerOptionsPanel, BorderLayout.SOUTH);

        instance = this;
    }

    private void initLayerElementsPanel() {
        layerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        layerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        layerScrollPane.setViewportView(layerElementsPanel);

        layerElementsPanel.setLayout(new BoxLayout(layerElementsPanel, BoxLayout.Y_AXIS));
    }

    private void initLayerOptionsPanel() {
        layerOptionsPanel.setPreferredSize(new Dimension(300, 50));
        layerOptionsPanel.setBackground(new Color(220,220,220));

        layerOptionsPanel.setLayout(new GridLayout(1, 3));

        layerOptionsPanel.add(addLayerButton);
        layerOptionsPanel.add(mergeLayerButton);
        layerOptionsPanel.add(deleteLayerButton);
    }

    public void updateLayerElements(SharedCanvas canvas) {
        layerElementsPanel.removeAll();
        for (int i = 0; i < 12 - canvas.layers.size() - 1; i++) {
            layerElementsPanel.add(new EmptyLayerElement());
        }
        for (int i = canvas.layers.size() - 1; i >= 0; i--) {
            LayerElement layerElement = new LayerElement(canvas.layers.get(i));
            layerElements.add(layerElement);
            layerElementsPanel.add(layerElement);
        }
        backGroundLayerElement = new BackGroundLayerElement();
        layerElementsPanel.add(backGroundLayerElement);
        layerElementsPanel.revalidate();
        JScrollBar vertical = layerScrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());

        if (activeLayer == null) {
            setActiveLayer(canvas.layers.get(0));
        }
        else {
            setActiveLayer(activeLayer);
        }
    }

    public void setActiveLayer(CanvasLayer layer) {
        activeLayer = layer;
        for (LayerElement layerElement : layerElements) {
            if (layerElement.layer == layer) {
                layerElement.setBackground(new Color(200,200,200));
            } else {
                layerElement.setBackground(new Color(220,220,220));
            }
        }
    }

    public void changeVisibility(CanvasLayer layer, boolean visible) {
        layer.visible = visible;
        ViewportPanel.getInstance().repaint();
    }

    public void moveLayerUp(CanvasLayer layer) {
        int index = ViewportPanel.getCanvas().layers.indexOf(layer);
        if (index == ViewportPanel.getCanvas().layers.size() - 1) return; // Already at the top

        ViewportPanel.getCanvas().layers.remove(index);
        ViewportPanel.getCanvas().layers.add(index + 1, layer);
        ViewportPanel.getInstance().repaint();
        updateLayerElements(ViewportPanel.getCanvas());
    }

    public void moveLayerDown(CanvasLayer layer) {
        int index = ViewportPanel.getCanvas().layers.indexOf(layer);
        if (index == 0) return; // Already at the bottom

        ViewportPanel.getCanvas().layers.remove(index);
        ViewportPanel.getCanvas().layers.add(index - 1, layer);
        ViewportPanel.getInstance().repaint();
        updateLayerElements(ViewportPanel.getCanvas());
    }

    public class LayerElement extends JPanel {
        private JLabel layerNameLabel = new JLabel();
        private JCheckBox visibilityCheckBox = new JCheckBox();
        private JPanel moveLayerPanel = new JPanel();

        private JButton JButtonmoveLayerUpButton = new JButton("Up");
        private JButton JButtonmoveLayerDownButton = new JButton("Down");

        private CanvasLayer layer;

        public LayerElement(CanvasLayer layer) {
            setSize(new Dimension(300, 50));

            setLayout(new GridLayout(1, 3));
            setBackground(new Color(220,220,220));

            this.layer = layer;

            layerNameLabel.setText(layer.name);
            visibilityCheckBox.setSelected(layer.visible);
            moveLayerPanel.setLayout(new GridLayout(2, 1));
            moveLayerPanel.add(JButtonmoveLayerUpButton);
            moveLayerPanel.add(JButtonmoveLayerDownButton);

            add(layerNameLabel);
            add(visibilityCheckBox);
            add(moveLayerPanel);

            layerNameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    clicked();
                }
            });
            visibilityCheckBox.addActionListener(e -> changeVisibility());
            JButtonmoveLayerUpButton.addActionListener(e -> moveLayerUp());
            JButtonmoveLayerDownButton.addActionListener(e -> moveLayerDown());
        }

        public void clicked() {
            LayerPanel.getInstance().setActiveLayer(layer);
        }

        public void changeVisibility() {
            LayerPanel.getInstance().changeVisibility(layer, visibilityCheckBox.isSelected());
        }

        public void moveLayerUp() {
            LayerPanel.getInstance().moveLayerUp(layer);
        }

        public void moveLayerDown() {
            LayerPanel.getInstance().moveLayerDown(layer);
        }
    }

    public class EmptyLayerElement extends JPanel {
        public EmptyLayerElement() {
            setSize(new Dimension(300, 50));
            setBackground(new Color(220,220,220));
        }
    }

    public class BackGroundLayerElement extends JPanel {
        private JLabel layerNameLabel = new JLabel();
        private JCheckBox visibilityCheckBox = new JCheckBox();
        private JPanel colorPreviewPanel = new JPanel();

        public BackGroundLayerElement() {
            setSize(new Dimension(300, 50));

            setLayout(new GridLayout(1, 3));
            setBackground(new Color(220,220,220));

            layerNameLabel.setText("Background");
            visibilityCheckBox.setSelected(true);
            colorPreviewPanel.setBackground(ViewportPanel.getCanvas().backgroundColor);

            add(layerNameLabel);
            add(visibilityCheckBox);
            add(colorPreviewPanel);

            colorPreviewPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    bgColorPreviewClicked();
                }
            });
        }

        public void bgColorPreviewClicked() {
            Color newColor;

            try {
                ColorPickPanel colorPickPanel = new ColorPickPanel();
                JOptionPane.showMessageDialog(null, colorPickPanel, "Choose Background Color", JOptionPane.PLAIN_MESSAGE);
                newColor = colorPickPanel.getColor();
            }
            catch (Exception e) {
                return;
            }

            ViewportPanel.getCanvas().backgroundColor = newColor;
            ViewportPanel.getInstance().repaint();
            colorPreviewPanel.setBackground(newColor);
        }
    }
}