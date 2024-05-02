package gameLabel;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class GameLabelSearchField extends JTextField {
    private JPanel gameListUI;

    public GameLabelSearchField(JPanel gameListUI) {
        this.gameListUI = gameListUI;
        addActionListener(this::onSearch);
        setBorder(BorderFactory.createTitledBorder("Search"));
    }

    private void onSearch(ActionEvent e) {
        String query = getText().toLowerCase();
        for (Component comp : gameListUI.getComponents()) {
            if (comp instanceof BaseGameLabel) {
                BaseGameLabel label = (BaseGameLabel) comp;
                label.setVisible(query.isEmpty() || label.contains(query));
            }
        }
    }
}
