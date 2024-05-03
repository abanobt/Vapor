package main;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

public class GameInstance extends JFrame {
    private static final int INIT_WIDTH = 1280;
    private static final int INIT_HEIGHT = 720;

    public GameInstance(int id, String title, Consumer<Integer> onGameExit) {
        super(title);

        setSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                onGameExit.accept(id);
            }
        });

        setVisible(true);
    }
}
