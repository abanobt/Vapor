package label;

import com.loginapp.database.UserLibraryDAO;
import main.VaporApp;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class LibraryGameLabel extends GameLabel {
    private static Font TitleFont = null;

    private static final DecimalFormat PLAY_TIME_FORMAT = new DecimalFormat() {{
        setMaximumFractionDigits(2);
    }};

    private final int id;
    protected final String title;
    private final JButton playButton;

    public LibraryGameLabel(int id, String title) {
        this.id = id;
        this.title = title;

        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        setMinimumSize(new Dimension(100, 80));
        setPreferredSize(new Dimension(1000, 80));
        setMaximumSize(new Dimension(2000, 80));

        add(playButton = new JButton() {{
            addActionListener(e -> {
                boolean gameSessionActive = UserLibraryDAO.isSessionActive(id);
                if (gameSessionActive) {
                    VaporApp.APP_SINGLETON.closeGame(id);
                } else {
                    VaporApp.APP_SINGLETON.launchGame(id);
                }
            });
        }});
    }

    public final int getGameId() { return id; }

    /**
     * Checks if the game's title contains the query
     * @param query A search query
     * @return true if the game's title contains the query, false otherwise
     */
    @Override
    public boolean contains(String query) {
        // TODO: maybe use sql to get these values instead of storing them locally
        return title.toLowerCase().contains(query);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (TitleFont == null) {
            TitleFont = g.getFont().deriveFont(30f);
        }

        Font font = g.getFont();
        g.setFont(TitleFont);
        int y = getFontMetrics(TitleFont).getHeight();
        g.drawString(title, 10, y);

        g.setFont(font);
        int dy = getFontMetrics(font).getHeight() + 3;

        String playTime;
        boolean gameSessionActive = UserLibraryDAO.isSessionActive(id);
        if (gameSessionActive) {
            float sessionTimeInHours = UserLibraryDAO.getSessionTime(getGameId());
            playTime = "Playing: "+PLAY_TIME_FORMAT.format(sessionTimeInHours)+" Hours";
        } else {
            float playTimeInHours = UserLibraryDAO.getTotalPlaytime(getGameId());
            playTime = "Play Time: "+PLAY_TIME_FORMAT.format(playTimeInHours)+" Hours";
        }

        g.drawString(playTime, 10, y + dy);

        int height = getHeight() - 40;
        int width = height * 4;
        int x = getWidth() - (width) - 20;

        playButton.setBounds(x, 20, width, height);

        if (gameSessionActive) {
            playButton.setText("Close game");
            playButton.setBackground(Color.RED);
        } else {
            playButton.setText("Play game");
            playButton.setBackground(Color.GREEN);
        }
    }
}