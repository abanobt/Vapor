import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

public class VaporApp extends JFrame {
    public static void main(String[] args) {
        new VaporApp();
    }

    public static final int INIT_WIDTH = 1280;
    public static final int INIT_HEIGHT = 720;

    private JTabbedPane contentPane;
    public VaporApp() {
        super("Vapor");

        setLayout(null);
        setSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
//        setLocation(X, (Toolkit.getDefaultToolkit().getScreenSize().height - H) / 2);
        contentPane = new JTabbedPane();
        setContentPane(contentPane);
//        setBackground(Palette.COLOR_B);
        setIconImage(new ImageIcon("res/icon.png").getImage());
//        getContentPane().setBackground(Palette.COLOR_B);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }


    public void init() {
        contentPane.addTab("Store", new ImageIcon("res/store_icon.png"),
            new JPanel() {{
                setBackground(Color.RED);
            }}
        );
        contentPane.setToolTipTextAt(0, "Store (Ctrl + S)");
        contentPane.setMnemonicAt(0, KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_S);

        contentPane.addTab("Library", new ImageIcon("res/store_icon.png"),
            new JPanel() {{
                setBackground(Color.BLUE);
            }}
        );
        contentPane.setToolTipTextAt(1, "Library (Ctrl + L)");
        contentPane.setMnemonicAt(1, KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_L);

        contentPane.addTab("Friends", new ImageIcon("res/store_icon.png"),
            new JPanel() {{
                setBackground(Color.GREEN);
            }}
        );
        contentPane.setToolTipTextAt(2, "Friends (Ctrl + F)");
        contentPane.setMnemonicAt(2, KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_F);

        contentPane.addTab("Profile", new ImageIcon("res/store_icon.png"),
            new JPanel() {{
                setBackground(Color.YELLOW);
            }}
        );
        contentPane.setToolTipTextAt(3, "Profile (Ctrl + P)");
        contentPane.setMnemonicAt(3, KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_P);

        contentPane.addTab("Wishlist", new ImageIcon("res/store_icon.png"),
            new JPanel() {{
                setBackground(Color.ORANGE);
            }}
        );
        contentPane.setToolTipTextAt(4, "Wishlist (Ctrl + W)");
        contentPane.setMnemonicAt(4, KeyEvent.CTRL_DOWN_MASK | KeyEvent.VK_W);
//        tadd(new TabBar(), new PercentConstraints(0,0,1f, .05f));
//
//        add(new JPanel() {{
//            setBackground(Color.RED);
//        }}, new PercentConstraints(0f, 0.05f, .2f, .95f));
//
//        add(new JPanel() {{
//            setBackground(Color.BLUE);
//        }}, new PercentConstraints(0.2f, 0.05f, .8f, .95f));

        setVisible(true);
    }
}
