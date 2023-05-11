import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame  {
    final static String winName = "Klotski";
    final static int MENU_WIDTH = 800;
    final static int MENU_HEIGHT = 600;
    final static int BLOCK_SIZE = 120;

    static public int BOARD_WIDTH = Board.WIDTH * BLOCK_SIZE;
    static public int BOARD_HEIGHT = Board.HEIGHT * BLOCK_SIZE;

    protected JPanel menu;
    protected JPanel board_view;
    private JMenuBar menuBar;

    public Window() {
        super(winName);
        setSize(MENU_WIDTH, MENU_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        // Create MENU
        menu = new JPanel();
        menu.setLayout(new BorderLayout());

        JLabel title = new JLabel("Klotski");
        title.setFont(new Font("Arial", Font.BOLD, 88));
        title.setBorder(new EmptyBorder(150, 10, 10, 10));
        title.setHorizontalAlignment(JLabel.CENTER);

        String[] levels = { "Level 1", "Level 2", "Level 3", "Level 4" };
        JComboBox<String>  selectLevel = new JComboBox<String>(levels);
        menu.putClientProperty("level_selection", selectLevel);
        selectLevel.setPreferredSize(new Dimension(100, 50));

        JButton playBtn = new JButton("Play");
        menu.putClientProperty("play_button", playBtn);
        playBtn.setPreferredSize(new Dimension(100, 50));
        playBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel levelMenu = new JPanel();
        levelMenu.add(selectLevel);
        levelMenu.add(playBtn);

        menu.add(title, BorderLayout.NORTH);
        menu.add(levelMenu, BorderLayout.CENTER);

        // Create BOARD
        board_view = new JPanel(null);
        board_view.setBackground(Color.GRAY);

        // Menu bar
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Settings");
        JMenu fileUndo = new JMenu("Undo");
        menuBar.add(fileMenu);
        menuBar.add(fileUndo);

        JMenuItem newItem = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Load");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        menuBar.

        setVisible(true);
    }

    public void setWindowSize(int width, int height) {
        Container c = getContentPane();
        Dimension d = new Dimension(width, height);
        c.setPreferredSize(d);
    }

    public void showMenu() {
        setSize(MENU_WIDTH, MENU_HEIGHT);
        remove(board_view);
        add(menu, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showBoard(Board board) {
        setWindowSize(BOARD_WIDTH, BOARD_HEIGHT);
        pack();

        // setJMenuBar(menuBar);

        remove(menu);
        setBoard(board);
        add(board_view);
        setVisible(true);
    }

    public void setBoard(Board board) {

        Piece[] pieces = board.getPieces();

        for(int i = 0; i < pieces.length; i++) {
            JButton b = new JButton();
            int[] prop = pieces[i].getProperties();
            b.setBounds(prop[0] * BLOCK_SIZE, prop[1] * BLOCK_SIZE, prop[2] * BLOCK_SIZE, prop[3] * BLOCK_SIZE);
            b.setBackground(Color.ORANGE);
            b.setMargin(new Insets(0, 0, 0, 0));
            b.setFocusPainted(false);
            board_view.add(b);
        }

        revalidate();
        repaint();
    }

    public void remove(JPanel panel) {
        super.remove(panel);
        revalidate();
        repaint();
    }

    public JButton getButton(String key) {
        return ((JButton)menu.getClientProperty(key));
    }
}
