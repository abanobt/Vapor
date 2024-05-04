package label;

import main.VaporApp;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class FriendLabel extends JComponent {
    private static Font FriendUsernameFont = null;

    private final int id;
    private final String friendUsername;
    private final String[] lines;
    private final JButton messageButton;

    public FriendLabel(int id, String friendUsername, String friendStatus, String friendStartDate) {
        this.id = id;
        lines = new String[2];
        this.friendUsername = friendUsername;
        lines[0] = "Status: "+friendStatus;
        lines[1] = "Friends Since: "+friendStartDate;
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        setMinimumSize(new Dimension(30, 90));
        setPreferredSize(new Dimension(30, 90));
        setMaximumSize(new Dimension(2000, 90));

        add(messageButton = new JButton() {{
            setText("Chat");
            addActionListener(e -> {
                VaporApp.APP_SINGLETON.openMessages(id);
            });
        }});
    }

    public final int getFriendshipId() { return id; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (FriendUsernameFont == null) {
            FriendUsernameFont = g.getFont().deriveFont(30f);
        }

        Font font = g.getFont();
        g.setFont(FriendUsernameFont);
        int y = getFontMetrics(FriendUsernameFont).getHeight();
        g.drawString(friendUsername, 10, y);
        g.setFont(font);
        int dy = getFontMetrics(font).getHeight() + 3;
        for (String line : lines){
            y+= dy;
            g.drawString(line, 10, y);
        }

        int height = getHeight() - 40;
        int width = height * 2;
        int x = getWidth() - (width) - 20;

        messageButton.setBounds(x, 20, width, height);
    }
}
