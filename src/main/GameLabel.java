package main;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.Random;

public class GameLabel extends JPanel {
    private static Font TitleFont = null;

    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat() {{
        setMaximumFractionDigits(2);
        setMinimumIntegerDigits(2);
    }};
    private static final DecimalFormat RATING_FORMAT = new DecimalFormat() {{
        setMaximumFractionDigits(2);
    }};

    private final int id;
    private final String title;
    private final String genre;
    private final String[] lines;
    private final JButton purchaseButton;
    private final JButton wishlistButton;
    public GameLabel(int id, String title, String developer, String publisher,
                     String genre, String releaseDate, String description,
                     float price, String platforms, float avgRating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        lines = new String[4];
        lines[0] = "Price: $"+MONEY_FORMAT.format(price) + " | Rating: "+RATING_FORMAT.format(avgRating) + " | Genre: "+genre;
        lines[1] = "Release: "+releaseDate + " | Platforms: "+platforms;
        lines[2] = "Developer: "+developer + " | Publisher: "+publisher;
        lines[3] = description;
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        setMinimumSize(new Dimension(100, 160));
        setPreferredSize(new Dimension(100, 140));
        add(purchaseButton = new JButton() {{
            addActionListener(e -> {
                //TODO: add/remove item to/from cart
            });
        }});
        add(wishlistButton = new JButton() {{
            addActionListener(e -> {
                //TODO: add/remove item to/from wishlist
            });
        }});
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
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

        int height = (getHeight() - 60) / 2;
        int width = height * 4;
        int x = getWidth() - (width) - 20;

        purchaseButton.setBounds(x, 20, width, height);
        wishlistButton.setBounds(x, 20 + height + 20, width, height);

        if (VaporApp.REMOVE_ME.nextBoolean()) { // TODO: check if game is in cart
            purchaseButton.setText("Remove from Cart");
            purchaseButton.setBackground(Color.RED);
        } else {
            purchaseButton.setText("Add from Cart");
            purchaseButton.setBackground(Color.GREEN);
        }

        if (VaporApp.REMOVE_ME.nextBoolean()) { // TODO: check if game is in wishlist
            wishlistButton.setText("Remove from Wishlist");
            wishlistButton.setBackground(Color.RED);
        } else {
            wishlistButton.setText("Add from Wishlist");
            wishlistButton.setBackground(Color.GREEN);
        }
    }
}
