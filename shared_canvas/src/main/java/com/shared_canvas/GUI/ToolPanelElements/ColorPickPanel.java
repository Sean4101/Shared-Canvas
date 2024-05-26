package com.shared_canvas.GUI.ToolPanelElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ColorPickPanel extends JPanel {

    //TODO: YOU CAN CHANGE EVERYTHING AS LONG AS getColor() METHOD RETURNS THE SELECTED COLOR
    //     YOU CAN CHANGE THE LAYOUT, COLORS, TEXTS, ETC.
    //     SUPPORTING BOTH RGB AND HSV WOULD BE A BONUS
    //     IM TIRED 😭😭😭 
    //     GOOD LUCK 😊😊😊

    private JLabel redLabel = new JLabel("Red:");
    private JLabel greenLabel = new JLabel("Green:");
    private JLabel blueLabel = new JLabel("Blue:");

    private JTextField redTextField = new JTextField();
    private JTextField greenTextField = new JTextField();
    private JTextField blueTextField = new JTextField();

    private JPanel colorPreviewPanel = new JPanel();

    public ColorPickPanel() {
        setPreferredSize(new Dimension(300, 200));

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);

        init();
    }

    public void init() {
        setLayout(null);

        redLabel.setBounds(10, 10, 50, 20);
        greenLabel.setBounds(10, 40, 50, 20);
        blueLabel.setBounds(10, 70, 50, 20);

        redTextField.setBounds(70, 10, 50, 20);
        greenTextField.setBounds(70, 40, 50, 20);
        blueTextField.setBounds(70, 70, 50, 20);

        colorPreviewPanel.setBounds(10, 100, 110, 90);
        colorPreviewPanel.setBackground(Color.BLACK);

        add(redLabel);
        add(greenLabel);
        add(blueLabel);

        add(redTextField);
        add(greenTextField);
        add(blueTextField);

        add(colorPreviewPanel);

        redTextField.setText("0");
        greenTextField.setText("0");
        blueTextField.setText("0");

        redTextField.addActionListener(e -> onColorTextFieldChanged(redTextField));
        greenTextField.addActionListener(e -> onColorTextFieldChanged(greenTextField));
        blueTextField.addActionListener(e -> onColorTextFieldChanged(blueTextField));
    }

    private void onColorTextFieldChanged(JTextField textField) {
        String text = textField.getText();
        text.replaceAll("[^0-9]", "");
        textField.setText(text);

        if (text.isEmpty()) {
            textField.setText("0");
        }

        if (Integer.parseInt(text) > 255) {
            textField.setText("255");
        }

        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
        updateColorPreview();
    }

    private void updateColorPreview() {
        int red = Integer.parseInt(redTextField.getText());
        int green = Integer.parseInt(greenTextField.getText());
        int blue = Integer.parseInt(blueTextField.getText());

        colorPreviewPanel.setBackground(new Color(red, green, blue));
    }

    //TODO: YOU CAN CHANGE EVERYTHING AS LONG AS getColor() METHOD RETURNS THE SELECTED COLOR
    public Color getColor() {
        int red = Integer.parseInt(redTextField.getText());
        int green = Integer.parseInt(greenTextField.getText());
        int blue = Integer.parseInt(blueTextField.getText());

        return new Color(red, green, blue);
    }
}
