package ui;

import label.AchievementLabel;
import label.ActivityHistoryLabel;
import label.TransactionHistoryLabel;
import main.PercentConstraints;
import main.PercentLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.net.URL;

// Settings
// Transaction history
public class AchievementsUI extends JPanel {
    public static final ImageIcon ACHIEVEMENTS_ICON = new ImageIcon("res/store.png");

    private final JPanel achievementsPanel;

    public AchievementsUI() {
        setLayout(new PercentLayout());

        achievementsPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Achievements"));
        }};

        add(new JScrollPane() {{
            setViewportView(achievementsPanel);
        }}, new PercentConstraints(.05f, .05f, .9f, .9f));

        refresh();
    }

    public void refresh() {
        achievementsPanel.removeAll();
        for (int i = 0; i < 4; i ++) {
            // SQL: retrieve achievements
            achievementsPanel.add(new AchievementLabel(i, "Minecraft",
                    "achievementName", 5, "unlockCondition",
                    "This is a description", i % 2 == 0, "1/1/1111"));
        }
    }
}
