package label;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

public abstract class BaseGameLabel extends GameLabel {
    private static Font TitleFont = null;

    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat() {{
        setMaximumFractionDigits(2);
        setMinimumIntegerDigits(2);
    }};
    private static final DecimalFormat RATING_FORMAT = new DecimalFormat() {{
        setMaximumFractionDigits(2);
    }};

    private final int id;
    protected final String title;
    private final String[] lines;

    // These are used for searching
    private final String genre;
    private final String tags;
    private final String description;

    public BaseGameLabel(int id, String title, String developer, String publisher,
                         String genre, String tags, String releaseDate, String description,
                         float price, String platforms, float avgRating) {
        this.id = id;
        this.title = title;
        this.genre = genre.toLowerCase();
        this.tags = tags.toLowerCase();
        this.description = description.toLowerCase();
        lines = new String[5];
        lines[0] = "Price: $"+MONEY_FORMAT.format(price) + " | Rating: "+RATING_FORMAT.format(avgRating);
        lines[1] = "Genre: "+genre + " | Tags: "+tags;
        lines[2] = "Release: "+releaseDate + " | Platforms: "+platforms;
        lines[3] = "Developer: "+developer + " | Publisher: "+publisher;
        lines[4] = description;
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        setMinimumSize(new Dimension(100, 160));
        setPreferredSize(new Dimension(1000, 160));
        setMaximumSize(new Dimension(2000, 160));
    }

    public final int getGameId() { return id; }

    /**
     * Checks if the game's title, genre, tags, or description contain the query
     * @param query A search query
     * @return true if the game's title, genre, tags, or description contains the query, false otherwise
     */
    @Override
    public boolean contains(String query) {
        return title.toLowerCase().contains(query)
                || genre.contains(query)
                || tags.contains(query)
                || description.contains(query);
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
        for (String line : lines){
            y+= dy;
            g.drawString(line, 10, y);
        }

        updateButtons(g);
    }

    protected abstract void updateButtons(Graphics g);
}
