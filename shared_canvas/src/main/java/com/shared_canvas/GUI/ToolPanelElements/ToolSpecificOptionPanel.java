package com.shared_canvas.GUI.ToolPanelElements;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

public class ToolSpecificOptionPanel extends JPanel {

    private static ToolSpecificOptionPanel instance;
    private static JPanel handToolPanel;
    private static JPanel pencilToolPanel;
    private static JPanel eraserToolPanel;

    public static ToolSpecificOptionPanel getInstance() {
        return instance;
    }

    public ToolSpecificOptionPanel() {
        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);
        setPreferredSize(new Dimension(200, 900));

        initHandToolLayout();
        initPencilToolLayout();
        initEraserToolLayout();

        instance = this;

        setHandToolPanel();
    }

    public enum PencilStrokeShape {
        ROUND, SQUARE, CALLIGRAPHIC
    }

    public static PencilStrokeShape pencilStrokeShape = PencilStrokeShape.ROUND;
    public static int pencilStrokeSize = 5;

    public enum EraserStrokeShape {
        ROUND, SQUARE
    }

    public static EraserStrokeShape eraserStrokeShape = EraserStrokeShape.ROUND;
    public static int eraserStrokeSize = 5;

    public static void setHandToolPanel() {
        instance.removeAll();
        instance.add(handToolPanel);
        instance.revalidate();
        instance.repaint();
    }

    public static void setPencilToolPanel() {
        instance.removeAll();
        instance.add(pencilToolPanel);
        instance.revalidate();
        instance.repaint();
    }

    public static void setEraserToolPanel() {
        instance.removeAll();
        instance.add(eraserToolPanel);
        instance.revalidate();
        instance.repaint();
    }

    public static void initHandToolLayout() {
        handToolPanel = new JPanel();
        handToolPanel.setPreferredSize(new Dimension(150, 900));
        handToolPanel.setLayout(null);
    }

    public static void initPencilToolLayout() {
        pencilToolPanel = new JPanel();
        pencilToolPanel.setPreferredSize(new Dimension(150, 900));
        Border border = BorderFactory.createEtchedBorder();
        
        JPanel strokeShapePanel = new JPanel();
        strokeShapePanel.setBorder(border);
        strokeShapePanel.setLayout(new BorderLayout());
        JLabel strokeShapeLabel = new JLabel("Stroke Shape");
        strokeShapePanel.add(strokeShapeLabel, BorderLayout.NORTH);
        JPanel strokeShapeChoices = new JPanel();
        setPencilStrokeShapeLayout(strokeShapeChoices);
        strokeShapePanel.add(strokeShapeChoices, BorderLayout.CENTER);
        
        JPanel strokeSizePanel = new JPanel();
        strokeSizePanel.setBorder(border);
        strokeSizePanel.setLayout(new BorderLayout());
        JPanel strokeSizeChoices = new JPanel();
        setPencilStrokeSizeLayout(strokeSizeChoices);
        strokeSizePanel.add(strokeSizeChoices, BorderLayout.CENTER);

        pencilToolPanel.setLayout(new GridLayout(2, 1));
        pencilToolPanel.add(strokeShapePanel);
        pencilToolPanel.add(strokeSizePanel);
    }

    public static void setPencilStrokeShapeLayout(JPanel strokeShapeChoices) {
        strokeShapeChoices.setLayout(new FlowLayout());
        for (PencilStrokeShape shape : PencilStrokeShape.values()) {
            JButton shapeButton = new JButton(shape.toString());
            shapeButton.setPreferredSize(new Dimension(120, 30));
            shapeButton.addActionListener(e -> {
                pencilStrokeShape = shape;
            });
            strokeShapeChoices.add(shapeButton);
        }
    }

    private static void setPencilStrokeSizeLayout(JPanel strokeSizeChoices) {
        strokeSizeChoices.setLayout(new FlowLayout());

        strokeSizeChoices.add(new JLabel("Stroke Size"));
        JTextField strokeSizeValue = new JTextField(Integer.toString(pencilStrokeSize));
        JSlider strokeSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, pencilStrokeSize);

        strokeSizeValue.setPreferredSize(new Dimension(50, 30));
        strokeSizeValue.addActionListener(e -> {
            try {
                pencilStrokeSize = Integer.parseInt(strokeSizeValue.getText());
                if (pencilStrokeSize < 1) {
                    pencilStrokeSize = 1;
                }
                if (pencilStrokeSize > 100) {
                    pencilStrokeSize = 100;
                }
            } catch (NumberFormatException ex) {
                strokeSizeValue.setText(Integer.toString(pencilStrokeSize));
            }
            strokeSizeSlider.setValue(pencilStrokeSize);
        });
        strokeSizeChoices.add(strokeSizeValue);

        strokeSizeSlider.setPreferredSize(new Dimension(120, 50));
        strokeSizeSlider.setMinorTickSpacing(1);
        strokeSizeSlider.setPaintTicks(true);
        strokeSizeSlider.setPaintLabels(true);
        strokeSizeSlider.addChangeListener(e -> {
            pencilStrokeSize = strokeSizeSlider.getValue();
            strokeSizeValue.setText(Integer.toString(pencilStrokeSize));
        });
        strokeSizeChoices.add(strokeSizeSlider);
    }

    public static int getPencilStrokeSize() {
        return pencilStrokeSize;
    }

    public static int[][] getPencilStrokeShape() {

        int[][] strokeShape = new int[pencilStrokeSize][pencilStrokeSize];
        for (int i = 0; i < pencilStrokeSize; i++) {
            for (int j = 0; j < pencilStrokeSize; j++) {
                switch (pencilStrokeShape) {
                    case ROUND:
                        if (isWithinCircle(pencilStrokeSize, i, j)) {
                            strokeShape[i][j] = 1;
                        }
                        break;
                    case SQUARE:
                        if (isWithinSquare(pencilStrokeSize, i, j)) {
                            strokeShape[i][j] = 1;
                        }
                        break;
                    case CALLIGRAPHIC:
                        if (isWithinDiagonalEllipse(pencilStrokeSize, i, j)) {
                            strokeShape[i][j] = 1;
                        }
                        break;
                }
            }
        }
        return strokeShape;
    }

    public static void initEraserToolLayout() {
        eraserToolPanel = new JPanel();
        eraserToolPanel.setPreferredSize(new Dimension(150, 900));
        Border border = BorderFactory.createEtchedBorder();

        JPanel strokeShapePanel = new JPanel();
        strokeShapePanel.setBorder(border);
        strokeShapePanel.setLayout(new BorderLayout());
        JLabel strokeShapeLabel = new JLabel("Stroke Shape");
        strokeShapePanel.add(strokeShapeLabel, BorderLayout.NORTH);
        JPanel strokeShapeChoices = new JPanel();
        setEraserStrokeShapeLayout(strokeShapeChoices);
        strokeShapePanel.add(strokeShapeChoices, BorderLayout.CENTER);
        
        JPanel strokeSizePanel = new JPanel();
        strokeSizePanel.setBorder(border);
        strokeSizePanel.setLayout(new BorderLayout());
        JPanel strokeSizeChoices = new JPanel();
        setEraserStrokeSizeLayout(strokeSizeChoices);
        strokeSizePanel.add(strokeSizeChoices, BorderLayout.CENTER);

        eraserToolPanel.setLayout(new GridLayout(2, 1));
        eraserToolPanel.add(strokeShapePanel);
        eraserToolPanel.add(strokeSizePanel);
    }

    public static void setEraserStrokeShapeLayout(JPanel strokeShapeChoices) {
        strokeShapeChoices.setLayout(new FlowLayout());
        for (EraserStrokeShape shape : EraserStrokeShape.values()) {
            JButton shapeButton = new JButton(shape.toString());
            shapeButton.setPreferredSize(new Dimension(120, 30));
            shapeButton.addActionListener(e -> {
                eraserStrokeShape = shape;
            });
            strokeShapeChoices.add(shapeButton);
        }
    }

    private static void setEraserStrokeSizeLayout(JPanel strokeSizeChoices) {
        strokeSizeChoices.setLayout(new FlowLayout());

        strokeSizeChoices.add(new JLabel("Stroke Size"));
        JTextField strokeSizeValue = new JTextField(Integer.toString(eraserStrokeSize));
        JSlider strokeSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, eraserStrokeSize);

        strokeSizeValue.setPreferredSize(new Dimension(50, 30));
        strokeSizeValue.addActionListener(e -> {
            try {
                eraserStrokeSize = Integer.parseInt(strokeSizeValue.getText());
                if (eraserStrokeSize < 1) {
                    eraserStrokeSize = 1;
                }
                if (eraserStrokeSize > 100) {
                    eraserStrokeSize = 100;
                }
            } catch (NumberFormatException ex) {
                strokeSizeValue.setText(Integer.toString(eraserStrokeSize));
            }
            strokeSizeSlider.setValue(eraserStrokeSize);
        });
        strokeSizeChoices.add(strokeSizeValue);

        strokeSizeSlider.setPreferredSize(new Dimension(120, 50));
        strokeSizeSlider.setMinorTickSpacing(1);
        strokeSizeSlider.setPaintTicks(true);
        strokeSizeSlider.setPaintLabels(true);
        strokeSizeSlider.addChangeListener(e -> {
            eraserStrokeSize = strokeSizeSlider.getValue();
            strokeSizeValue.setText(Integer.toString(eraserStrokeSize));
        });
        strokeSizeChoices.add(strokeSizeSlider);
    }

    public static int getEraserStrokeSize() {
        return eraserStrokeSize;
    }

    public static int[][] getEraserStrokeShape() {

        int[][] strokeShape = new int[eraserStrokeSize][eraserStrokeSize];
        for (int i = 0; i < eraserStrokeSize; i++) {
            for (int j = 0; j < eraserStrokeSize; j++) {
                switch (eraserStrokeShape) {
                    case ROUND:
                        if (isWithinCircle(eraserStrokeSize, i, j)) {
                            strokeShape[i][j] = 1;
                        }
                        break;
                    case SQUARE:
                        if (isWithinSquare(eraserStrokeSize, i, j)) {
                            strokeShape[i][j] = 1;
                        }
                        break;
                }
            }
        }
        return strokeShape;
    }

    private static boolean isWithinSquare(int strokeSize, int x, int y) {
        return x >= 0 && x < strokeSize && y >= 0 && y < strokeSize;
    }

    private static boolean isWithinCircle(int strokeSize, int x, int y) {
        int radius = strokeSize / 2;
        return Math.pow(x - radius, 2) + Math.pow(y - radius, 2) < Math.pow(radius, 2);
    }

    private static boolean isWithinDiagonalEllipse(int strokeSize, int x, int y) {
        int a = (int) (strokeSize * 0.7);
        int b = (int) (strokeSize * 0.2);
        int center = strokeSize / 2;
        int traslatedX = x - center;
        int traslatedY = y - center;

        double theta135 = Math.toRadians(135);
        double cosTheta135 = Math.cos(theta135);
        double sinTheta135 = Math.sin(theta135);

        double x_rot = traslatedX * cosTheta135 + traslatedY * sinTheta135;
        double y_rot = -traslatedX * sinTheta135 + traslatedY * cosTheta135;

        return Math.pow(x_rot, 2) / Math.pow(a, 2) + Math.pow(y_rot, 2) / Math.pow(b, 2) <= 1;
    }
}
