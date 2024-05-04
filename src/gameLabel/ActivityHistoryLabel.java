package gameLabel;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class ActivityHistoryLabel extends JComponent {
    private final int id;
    private final String[] lines;

    public ActivityHistoryLabel(int id, String type, String date, String details) {
        this.id = id;
        lines = new String[3];
        lines[0] = "Type: "+type;
        lines[1] = "Date: "+date;
        lines[2] = "Details: "+details;
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        setMinimumSize(new Dimension(30, 80));
        setPreferredSize(new Dimension(30, 80));
        setMaximumSize(new Dimension(2000, 80));
    }

    public final int getActivityId() { return id; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font font = g.getFont();
        g.setFont(font);
        int y= 0;
        int dy = getFontMetrics(font).getHeight() + 3;
        for (String line : lines){
            y+= dy;
            g.drawString(line, 10, y);
        }
    }
}
