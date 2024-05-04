package ui;

import label.ActivityHistoryLabel;
import label.TransactionHistoryLabel;
import main.IconLabel;
import main.PercentConstraints;
import main.PercentLayout;

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

        String[] supportedLanguages = { "English" }; // SQL: get supported languages
        add(languagePreference = new JComboBox<String>(supportedLanguages) {{
            setBorder(BorderFactory.createTitledBorder("Language Preference"));
            addActionListener(e -> {
                // SQL: update language preference
            });
        }}, new PercentConstraints(0.05f, 0.7f, .2f, 0.07f));

        String[] displayOptions = { "1920x1080", "3840x2160" }; // SQL: get supported languages
        add(displayPreference = new JComboBox<String>(displayOptions) {{
            setBorder(BorderFactory.createTitledBorder("Display Preference"));
            addActionListener(e -> {
                // SQL: update display preference
            });
        }}, new PercentConstraints(0.05f, 0.8f, .2f, 0.07f));

        add(notificationPreference = new JCheckBox("Notifications") {{
            boolean allowNotifications = true; // SQL: get notification preference
            setSelected(allowNotifications);
            addActionListener(e -> {
                // SQL: update notification preference
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

    public void refresh() {
        // SQL: get user profile pic url
        String profilePicUrl = "https://einercial.com/wp-content/uploads/2018/04/Facebook-no-profile-picture-icon-620x389.jpg";
        try {
            profilePic.setIcon(ImageIO.read(new URL(profilePicUrl).openStream()));
        } catch (Exception e) {
            System.out.println(e);
        }

        // SQL: get username, email, registrationDate, and role
        String info =
                "<html>"
                + "Username: " + "USERNAME" + "<br>"
                + "Email: " + "EMAIL" + "<br>"
                + "RegistrationDate: " + "1/1/1111" + "<br>"
                + "Role: " + "User" + "<br>"
                +"</html>";
        infoLabel.setText(info);

        transactionPanel.removeAll();
        for (int i = 0; i < 4; i ++) {
            // SQL: retrieve transaction history
            transactionPanel.add(new TransactionHistoryLabel(i, SampleData.TITLES[i],
                    SampleData.RELEASE_DATE[i], SampleData.PRICE[i], "Credit Card"));
        }

        activityPanel.removeAll();
        for (int i = 0; i < 4; i ++) {
            // SQL: retrieve activity history
            activityPanel.add(new ActivityHistoryLabel(i, "Purchase",
                    SampleData.RELEASE_DATE[i], "Minecraft"));
        }
    }
}
