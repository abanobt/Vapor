package main;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class IconLabel extends JComponent {
    private Image icon;
    private final boolean enforceAspectRatio;

    public IconLabel(boolean enforceAspectRatio) {
        this.enforceAspectRatio = enforceAspectRatio;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rectangle bounds = getBounds();
        if (enforceAspectRatio) {
            float aspectRatio = icon.getWidth(null) / (float)icon.getHeight(null);
            int width = (int)(bounds.height * aspectRatio);
            // TODO: find which dimension is the constraint
            g.drawImage(icon, 0, 0, width, bounds.height, null);
        } else {
            g.drawImage(icon, 0, 0, bounds.width, bounds.height, null);
        }
    }
}
