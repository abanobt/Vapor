package com.vaporapp.screen;

import com.vaporapp.database.UserSettingsDAO;
import com.loginapp.util.Pair;
import com.vaporapp.ui.label.ActivityHistoryLabel;
import com.vaporapp.ui.main.PercentConstraints;
import com.vaporapp.ui.main.PercentLayout;
import com.vaporapp.ui.label.TransactionHistoryLabel;
import com.vaporapp.ui.main.IconLabel;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.io.IOException;
import java.net.URL;

// Settings
// Transaction history
public class ProfileUI extends JPanel {
    public static final ImageIcon PROFILE_ICON = new ImageIcon("res/profile.png");

    private final JLabel infoLabel;
    private final IconLabel profilePic;
    private final JComboBox<String> languagePreference;
    private final JComboBox<String> displayPreference;
    private final JCheckBox notificationPreference;
    private final JPanel activityPanel;
    private final JPanel transactionPanel;

    public ProfileUI() {
        setLayout(new PercentLayout());

        add(profilePic = new IconLabel(true), new PercentConstraints(0.05f, 0.05f, 0.2f, 0.35f));

        add(infoLabel = new JLabel("") {{
            setHorizontalAlignment(SwingConstants.LEFT);
            setVerticalAlignment(SwingConstants.TOP);
            setFont(getFont().deriveFont(20f));
        }}, new PercentConstraints(0.05f, 0.45f, .2f, 0.2f));

        String[] supportedLanguages = { "English" };
        add(languagePreference = new JComboBox<String>(supportedLanguages) {{
            setBorder(BorderFactory.createTitledBorder("Language Preference"));
            addActionListener(e -> {
                updatePreferences();
            });
        }}, new PercentConstraints(0.05f, 0.7f, .2f, 0.07f));

        String[] displayOptions = { "1920x1080", "3840x2160" };
        add(displayPreference = new JComboBox<String>(displayOptions) {{
            setBorder(BorderFactory.createTitledBorder("Display Preference"));
            addActionListener(e -> {
                updatePreferences();
            });
        }}, new PercentConstraints(0.05f, 0.8f, .2f, 0.07f));

        add(notificationPreference = new JCheckBox("Notifications") {{
            boolean allowNotifications = true;
            setSelected(allowNotifications);
            addActionListener(e -> {
                updatePreferences();
            });
        }}, new PercentConstraints(0.05f, 0.87f, .2f, 0.07f));

        transactionPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Transaction History"));
        }};

        activityPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Activity History"));
        }};

        add(new JScrollPane() {{
            setViewportView(transactionPanel);
        }}, new PercentConstraints(.3f, .05f, .65f, .425f));

        add(new JScrollPane() {{
            setViewportView(activityPanel);
        }}, new PercentConstraints(.3f, .525f, .65f, .425f));

        refresh();
    }

    private void updatePreferences() {
        UserSettingsDAO.updateUserSettings((String)languagePreference.getSelectedItem(),
                notificationPreference.isSelected() ? "true" : "false", (String)displayPreference.getSelectedItem());
    }

    public void refresh() {
        Pair<String, String> userInfo = UserSettingsDAO.getUserInfo();
        try {
            profilePic.setIcon(ImageIO.read(new URL(userInfo.secondItem).openStream()));
        } catch (Exception e) {
            try {
                profilePic.setIcon(ImageIO.read(new URL("https://einercial.com/wp-content/uploads/2018/04/Facebook-no-profile-picture-icon-620x389.jpg").openStream()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        infoLabel.setText(userInfo.firstItem);

        transactionPanel.removeAll();
        for (TransactionHistoryLabel transaction : UserSettingsDAO.getTransactionHistory()) {
            transactionPanel.add(transaction);
        }

        activityPanel.removeAll();
        for (ActivityHistoryLabel activity : UserSettingsDAO.getActivityHistory()) {
            activityPanel.add(activity);
        }
    }


}
