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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Settings
// Transaction history
public class FriendsUI extends JPanel {
    public static final ImageIcon FRIENDS_ICON = new ImageIcon("res/friends.png");

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
                sendMessage(openMessagesFriendshipId, message); // Assuming you have the friend's ID
                refresh();
            });
        }}, new PercentConstraints(0.87f, .8f, 0.08f, 0.15f));

        refresh();

        openMessagesFriendshipId = -1;
    }

    private void sendMessage(int friendshipId, String message) {
        String sql = "INSERT INTO Messages (friendshipId, messageDetails, messageDate) VALUES (?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, friendshipId);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void refresh() {
        friendsPanel.removeAll();
        fetchFriends();

        messagesPanel.removeAll();
        if (openMessagesFriendshipId >= 0) {
            fetchMessages(openMessagesFriendshipId);
        }
    }

    private void fetchFriends() {
        String sql = "SELECT friendshipId, username, status, lastUpdated FROM Friends WHERE userId = ?"; // Adjust based on your schema
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, currentUserId); // Assuming you have a way to get the current user's ID
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int friendshipId = rs.getInt("friendshipId");
                String username = rs.getString("username");
                String status = rs.getString("status");
                String lastUpdated = rs.getString("lastUpdated");
                friendsPanel.add(new FriendLabel(friendshipId, username, status, lastUpdated));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void fetchMessages(int friendshipId) {
        String sql = "SELECT messageId, senderId, messageDetails, messageDate FROM Messages WHERE friendshipId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, friendshipId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int messageId = rs.getInt("messageId");
                boolean isSender = rs.getInt("senderId") == currentUserId; // Adjust depending on how you store sender
                String message = rs.getString("messageDetails");
                String date = rs.getString("messageDate");
                messagesPanel.add(new MessageLabel(messageId, "Friend Name", isSender, message, date));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    public void openMessages(int friendshipId) {
        openMessagesFriendshipId = friendshipId;
        refresh();
        }
    }
}