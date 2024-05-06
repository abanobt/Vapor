package com.vaporapp.ui.label;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Dimension;

public class MessageLabel extends JTextArea {
    private static final Color USER_MESSAGE_COLOR = Color.BLUE;
    private static final Color FRIEND_MESSAGE_COLOR = new Color(50, 200, 90);
    private final int id;

    public MessageLabel(int id, String username, boolean isUserSender, String message, String date) {
        this.id = id;

        String messageTitle = (isUserSender ? "You" : username) + " (" + date + ")";
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),
                messageTitle, TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION, null,
                isUserSender ? USER_MESSAGE_COLOR : FRIEND_MESSAGE_COLOR));
        setEditable(false);
        setText(message);
        setBackground(null);
        setMinimumSize(new Dimension(30, 90));
        setPreferredSize(new Dimension(30, 90));
        setMaximumSize(new Dimension(2000, 90));
    }

    public final int getMessageId() { return id; }
}
