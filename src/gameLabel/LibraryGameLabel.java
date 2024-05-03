package gameLabel;

import main.VaporApp;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class LibraryGameLabel extends BaseGameLabel {
    private static final DecimalFormat PLAY_TIME_FORMAT = new DecimalFormat() {{
        setMaximumFractionDigits(2);
    }};

    private final JButton playButton;
    private final JLabel timeLabel;
    public LibraryGameLabel(int id, String title, String developer, String publisher,
                            String genre, String tags, String releaseDate, String description,
                            float price, String platforms, float avgRating) {
        super(id, title, developer, publisher, genre, tags, releaseDate, description, price, platforms, avgRating);
        add(playButton = new JButton() {{
            addActionListener(e -> {
                boolean gameSessionActive = false; // SQL: check if game session is active
                if (gameSessionActive) {
                    VaporApp.APP_SINGLETON.closeGame(id);
                } else {
                    VaporApp.APP_SINGLETON.launchGame(id);
                }
            });
        }});

        add(timeLabel = new JLabel() {{
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
        }});
    }

    protected void updateButtons(Graphics g) {
        int height = (getHeight() - 60) / 2;
        int width = height * 4;
        int x = getWidth() - (width) - 20;

        playButton.setBounds(x, 20, width, height);
        timeLabel.setBounds(x, 20 + height + 20, width, height);

        boolean gameSessionActive = false; // SQL: check if game session is active
        if (gameSessionActive) {
            playButton.setText("Close game");
            playButton.setBackground(Color.RED);
        } else {
            playButton.setText("Play game");
            playButton.setBackground(Color.GREEN);
        }

        if (gameSessionActive) {
            float sessionTimeInHours = 25.5f; // SQL: get game session time
            timeLabel.setText("Playing: "+PLAY_TIME_FORMAT.format(sessionTimeInHours)+" Hours");
        } else {
            float playTimeInHours = 25.5f; // SQL: get total play time
            timeLabel.setText("Play Time: "+PLAY_TIME_FORMAT.format(playTimeInHours)+" Hours");
        }
    }
}
