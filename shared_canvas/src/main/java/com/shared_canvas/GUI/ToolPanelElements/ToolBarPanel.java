package com.shared_canvas.GUI.ToolPanelElements;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import com.shared_canvas.Actions.ViewportActions.*;

public class ToolBarPanel extends JPanel {

    public ArrayList<JButton> toolButtons = new ArrayList<JButton>();
    public JButton handButton = new JButton();
    public JButton pencilButton = new JButton();
    public JButton brushButton = new JButton();
    public JButton eraserButton = new JButton();

    public JButton activeButton;
    public AbstractViewPortAction activeAction;

    private static ToolBarPanel instance;

    public static ToolBarPanel getInstance() {
        return instance;
    }

    public ToolBarPanel() {
        instance = this;

        setPreferredSize(new Dimension(50, 900));
        setBackground(Color.DARK_GRAY);
        
        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);

        setLayout(new FlowLayout());

        handButton.setPreferredSize(new Dimension(40, 40));
        handButton.setIcon(new ImageIcon("shared_canvas/resources/tool_icons/hand_icon.png"));
        handButton.setName("hand");
        add(handButton);

        pencilButton.setPreferredSize(new Dimension(40, 40));
        pencilButton.setIcon(new ImageIcon("shared_canvas/resources/tool_icons/pencil_icon.png"));
        pencilButton.setName("pencil");
        add(pencilButton);

        brushButton.setPreferredSize(new Dimension(40, 40));
        // brushButton.setIcon(new ImageIcon("shared_canvas/resources/tool_icons/brush_icon.png")); (icon not yet available)
        brushButton.setName("brush");
        add(brushButton);

        eraserButton.setPreferredSize(new Dimension(40, 40));
        // eraserButton.setIcon(new ImageIcon("shared_canvas/resources/tool_icons/eraser_icon.png")); (icon not yet available)
        eraserButton.setName("eraser");
        add(eraserButton);

        toolButtons.add(handButton);
        toolButtons.add(pencilButton);
        toolButtons.add(brushButton);
        toolButtons.add(eraserButton);

        for (JButton button : toolButtons) {
            button.addActionListener(e -> {
                setActiveButton(button);
                switch (button.getName()) {
                    case "hand":
                        ToolSpecificOptionPanel.setHandToolPanel();
                        break;
                    case "pencil":
                        ToolSpecificOptionPanel.setPencilToolPanel();
                        break;
                    default:
                        break;
                }
            });
        }

        setActiveButton(handButton);
    }

    public void setActiveButton(JButton button) {
        activeButton = button;
        for (JButton b : toolButtons) {
            if (b == button) {
                b.setBackground(Color.GRAY);
            } else {
                b.setBackground(Color.WHITE);
            }
        }

        switch (button.getName()) {
            case "hand":
                activeAction = new HandAction();
                break;
            case "pencil":
                activeAction = new PencilAction();
                break;
            case "brush":
                //activeAction = new BrushAction();
                break;
            case "eraser":
                //activeAction = new EraserAction();
                break;
        }
    }

    public AbstractViewPortAction getActiveToolAction() {
        return activeAction;
    }

    public static JButton getActiveButton() {
        return instance.activeButton;
    }

    public static String getActiveTool() {
        return instance.activeButton.getName();
    }
}
