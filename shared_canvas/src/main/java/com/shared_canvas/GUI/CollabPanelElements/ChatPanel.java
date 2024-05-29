package com.shared_canvas.GUI.CollabPanelElements;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import com.shared_canvas.Networking.NetworkManager;

public class ChatPanel extends JPanel {

    private JPanel messagesPanel;
    private JScrollPane scrollPane;
    public TextField chatInput = new TextField();

    private static ChatPanel instance;

    public static ChatPanel getInstance() {
        return instance;
    }
    
    public ChatPanel() {
        setBackground(Color.DARK_GRAY);

        Border border = BorderFactory.createEtchedBorder();
        setBorder(border);

        setLayout(new BorderLayout());

        messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setBackground(new Color(220,220,220));

        scrollPane = new JScrollPane(messagesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBackground(new Color(220,220,220));
        
        add(scrollPane, BorderLayout.CENTER);
        add(chatInput, BorderLayout.SOUTH);

        instance = this;
    }

    public void joinedServerMessage(String username) {
        boolean souldBeLocal = username.equals(NetworkManager.getClient().getUsername());
        if (souldBeLocal){
            for(int i=0; i<9; i++){
                appendMessage("\n", new Color(220,220,220), true, "local");
            }
        }

        if(username.isEmpty()){
            username = "unknown";
        }
        appendMessage("user " + username + " has joined the canvas now\n", Color.WHITE, true, "center");
    }

    public void leftServerMessage(String username) { 
        if(username.isEmpty()){
            username = "unknown";
        }
        appendMessage("user " + username + " has left the canvas now\n", Color.WHITE, true, "center");
    }

    public void receiveMessage(String sender, String message) {
        boolean senderIsMe = sender.equals(NetworkManager.getClient().getUsername());
        if (senderIsMe) {
            appendMessage(sender + ": " + message + "\n", Color.BLACK, false, "right");
        }
        else{
            appendMessage(sender + ": " + message + "\n", Color.BLACK, false, "left");
        }
    }

    private boolean scroll;
    private void appendMessage(String message, Color color, boolean isBold, String alignment) {
        scroll = true;
        MessageBubble bubble = new MessageBubble(message, color, isBold, alignment);
        messagesPanel.add(bubble);
        messagesPanel.add(Box.createVerticalStrut(5));
        messagesPanel.revalidate();
    }
    
    private void scrollToBottom() {
        JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    } 

    class MessageBubble extends JPanel {
        private String message;
        private Color color;
        private boolean isBold;
        private String alignment;

        public MessageBubble(String message, Color color, boolean isBold, String alignment) {
            this.message = message;
            this.color = color;
            this.isBold = isBold;
            this.alignment = alignment;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int arcSize = 15;

            int bubbleWidth = getPreferredSize().width;
            int bubbleHeight = getPreferredSize().height;

            if (alignment.equals("right")) {
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(width - bubbleWidth, 0, bubbleWidth, bubbleHeight, arcSize, arcSize);
            } else if (alignment.equals("center")) {
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRoundRect((width - bubbleWidth) / 2, 0, bubbleWidth, bubbleHeight, arcSize, arcSize);
            } else if (alignment.equals("left")){
                g2d.setColor(new Color(170,170,170));
                g2d.fillRoundRect(0, 0, bubbleWidth, bubbleHeight, arcSize, arcSize);
            }
            else{
                g2d.setColor(new Color(220,220,220));
                g2d.fillRoundRect((width - bubbleWidth) / 2, 0, bubbleWidth, bubbleHeight, arcSize, arcSize);
            }

            g2d.setColor(color);
            Font font = isBold ? g2d.getFont().deriveFont(Font.BOLD) : g2d.getFont();
            g2d.setFont(font);

            FontMetrics fm = g2d.getFontMetrics();
            int textX = alignment.equals("right") ? width - bubbleWidth + 5 : (alignment.equals("center") ? (width - bubbleWidth) / 2 + 5 : 5);
            int textY = fm.getAscent() + 5;
            int maxWidth = bubbleWidth - 10;

            String[] words = message.split(" ");
            StringBuilder line = new StringBuilder();
            int lineWidth = 0;

            for (String word : words) {
                int wordWidth = fm.stringWidth(word + " ");
                if (lineWidth + wordWidth > maxWidth) {
                    g2d.drawString(line.toString(), textX, textY);
                    textY += fm.getHeight();
                    line = new StringBuilder(word + " ");
                    lineWidth = wordWidth;
                } else {
                    line.append(word).append(" ");
                    lineWidth += wordWidth;
                }
            }
            g2d.drawString(line.toString(), textX, textY);
            if(scroll){
                scrollToBottom();
                scroll = false;
            }
        }

        @Override
        public Dimension getPreferredSize() {
            FontMetrics fm = getGraphics().getFontMetrics();
            int maxWidth = 190;
            int lineHeight = fm.getHeight();
            int lineWidth = 0;
            int height = 0;
            int width = 0;


            String[] words = message.split(" ");
            for (String word : words) {
                int wordWidth = fm.stringWidth(word + " ");
                if (lineWidth + wordWidth > maxWidth) {
                    height += lineHeight;
                    width = Math.max(width, lineWidth);
                    lineWidth = wordWidth;
                } else {
                    lineWidth += wordWidth;
                }
            }
            height += lineHeight;
            width = Math.max(width, lineWidth);
            height += 10;

            return new Dimension(width + 10, height);
        }
    }
}