package com.vaporapp.ui.label;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class TransactionHistoryLabel extends JComponent {
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat() {{
        setMaximumFractionDigits(2);
        setMinimumIntegerDigits(2);
    }};

    private final int id;
    private final String[] lines;

    public TransactionHistoryLabel(int id, String gameTitle, String date, float price, String  paymentMethod) {
        this.id = id;
        lines = new String[3];
        lines[0] = "Game: "+gameTitle;
        lines[1] = "Date: "+date;
        lines[2] = "Amount: $"+MONEY_FORMAT.format(price) + " | Method: " + paymentMethod;
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createBevelBorder(BevelBorder.RAISED)));
        setMinimumSize(new Dimension(30, 80));
        setPreferredSize(new Dimension(30, 80));
        setMaximumSize(new Dimension(2000, 80));
    }

    public final int getTransactionId() { return id; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font font = g.getFont();
        g.setFont(font);
        int y= 0;
        int dy = getFontMetrics(font).getHeight() + 3;
        for (String line : lines){
            y+= dy;
            g.drawString(line, 10, y);
        }
    }
}
