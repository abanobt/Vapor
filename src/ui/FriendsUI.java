package ui;

import label.FriendLabel;
import label.MessageLabel;
import main.PercentConstraints;
import main.PercentLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Settings
// Transaction history
public class FriendsUI extends JPanel {
    public static final ImageIcon FRIENDS_ICON = new ImageIcon("res/store.png");

    private int openMessagesFriendshipId;
    private final JPanel friendsPanel;
    private final JPanel messagesPanel;
    private final JTextArea messageArea;
    private final JButton sendMessageButton;

    public FriendsUI() {
        setLayout(new PercentLayout());

        friendsPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Friends"));
        }};

        messagesPanel = new JPanel() {{
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createTitledBorder("Messages"));
        }};

        add(new JScrollPane() {{
            setViewportView(friendsPanel);
        }}, new PercentConstraints(.05f, .05f, .3f, .9f));

        add(new JScrollPane() {{
            setViewportView(messagesPanel);
            JScrollBar vertical = getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        }}, new PercentConstraints(.4f, .05f, .55f, .7f));

        add(messageArea = new JTextArea() {{
            setBorder((BorderFactory.createTitledBorder("New Message")));
        }}, new PercentConstraints(0.4f, .8f, 0.45f, 0.15f));

        add(sendMessageButton = new JButton("Send") {{
            addActionListener(e -> {
                String message = messageArea.getText();
                messageArea.setText("");
                // SQL: send message
                refresh();
            });
        }}, new PercentConstraints(0.87f, .8f, 0.08f, 0.15f));

        refresh();

        openMessagesFriendshipId = -1;
    }

    public void refresh() {
        friendsPanel.removeAll();
        for (int i = 0; i < 4; i ++) {
            // SQL: retrieve friends
            friendsPanel.add(new FriendLabel(i, "Friend Username",
                    "Friend Status", "1/1/1111"));
        }

        messagesPanel.removeAll();
        if (openMessagesFriendshipId < 0) { return; }
        for (int i = 0; i < 4; i ++) {
            // SQL: retrieve messages
            messagesPanel.add(new MessageLabel(i, "Friend Name",
                    i % 2 == 0, "This is a message.", "1/1/1111"));
        }
    }

    public void openMessages(int friendshipId) {
        openMessagesFriendshipId = friendshipId;
        refresh();
    }
}
