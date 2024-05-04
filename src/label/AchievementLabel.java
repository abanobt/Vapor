package label;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class AchievementLabel extends JComponent {
    private final int id;
    private final String[] lines;

    public AchievementLabel(int id, String gameTitle, String achievementName,
                            int achievementPoints, String unlockCondition, String description,
                            boolean isUnlocked, String unlockedDate) {
        this.id = id;
        lines = new String[3];
        lines[0] = "Achievement: "+achievementName + " | Game: " + gameTitle + " | Points: " +achievementPoints;
        lines[1] = "Unlocked: "+(isUnlocked ? "Yes (" + unlockedDate + ")" : "No") +
                " | Condition: " + unlockCondition;
        lines[2] = description;
        setBackground(isUnlocked ? Color.GREEN : Color.RED);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        setMinimumSize(new Dimension(30, 80));
        setPreferredSize(new Dimension(30, 80));
        setMaximumSize(new Dimension(2000, 80));
    }

    public final int getAchievementId() { return id; }

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
