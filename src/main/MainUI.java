package main;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class MainUI extends JTabbedPane {
    public MainUI() {
//        setLayout(null);
        addTab("Store", new ImageIcon("res/store_icon.png"),
            new JPanel() {{
                setBackground(Color.RED);
            }}
        );
        setToolTipTextAt(0, "Store (Ctrl + S)");
        setMnemonicAt(0, KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_S);

        addTab("Library", new ImageIcon("res/store_icon.png"),
            new JPanel() {{
                setBackground(Color.BLUE);
            }}
        );
        setToolTipTextAt(1, "Library (Ctrl + L)");
        setMnemonicAt(1, KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_L);

        addTab("Friends", new ImageIcon("res/store_icon.png"),
            new JPanel() {{
                setBackground(Color.GREEN);
            }}
        );
        setToolTipTextAt(2, "Friends (Ctrl + F)");
        setMnemonicAt(2, KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_F);

        addTab("Profile", new ImageIcon("res/store_icon.png"),
            new JPanel() {{
                setBackground(Color.YELLOW);
            }}
        );
        setToolTipTextAt(3, "Profile (Ctrl + P)");
        setMnemonicAt(3, KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_P);

        addTab("Wishlist", new ImageIcon("res/store_icon.png"),
            new JPanel() {{
                setBackground(Color.ORANGE);
            }}
        );
        setToolTipTextAt(4, "Wishlist (Ctrl + W)");
        setMnemonicAt(4, KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_W);

    }

    private void addTab() {

    }
}
