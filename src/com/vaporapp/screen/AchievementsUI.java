package com.vaporapp.screen;

import com.vaporapp.database.AchievementsDAO;
import com.vaporapp.ui.label.AchievementLabel;
import com.vaporapp.ui.main.PercentConstraints;
import com.vaporapp.ui.main.PercentLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// Settings
// Transaction history
public class AchievementsUI extends JPanel {
    public static final ImageIcon ACHIEVEMENTS_ICON = new ImageIcon("res/achievements.png");

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
        for (AchievementLabel label : AchievementsDAO.getAchievementsWithGameTitlesAndUnlockStatus()) {
            achievementsPanel.add(label);
        }
    }
}
