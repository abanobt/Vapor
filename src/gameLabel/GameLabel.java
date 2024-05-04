package gameLabel;

import javax.swing.JComponent;

public abstract class GameLabel extends JComponent {
    public abstract boolean contains(String query);
}
