package theknife.gui;

import java.awt.*;
import javax.swing.*;

public class GradientMenuBar extends JMenuBar {
    private Color startColor;
    private Color endColor;

    public GradientMenuBar(Color startColor, Color endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        setOpaque(false);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, startColor, 0, height, endColor);
        g2.setPaint(gp);
        g2.fillRect(0, 0, width, height);
        g2.dispose();
    }
}
