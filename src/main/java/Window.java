import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JPanel[] pieces_view;

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
        pieces_view = new JPanel[pieces.length];
        for(int i = 0; i < pieces.length; i++) {
            int[] prop = pieces[i].getProperties();
            pieces_view[i] = new JPanel();
            pieces_view[i].setBounds(prop[0] * BLOCK_SIZE, prop[1] * BLOCK_SIZE, prop[2] * BLOCK_SIZE, prop[3] * BLOCK_SIZE);
            pieces_view[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
            pieces_view[i].setBackground(Color.ORANGE);
            // possibile aggiunta di listener se si vuole fare che quando si passa sopra ad un pezzo
            // con il mouse cambi colore, problema di conflitto con il listener della board
            /*JPanel temp = pieces_view[i];
            pieces_view[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    temp.setBackground(Color.BLUE);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    temp.setBackground(Color.ORANGE);;
                }
            });*/
            board_view.add(pieces_view[i]);
        }
        revalidate();
        repaint();
    }

    public void movePiecePanel(Position piece_pos, int direction) {
        int x_temp = piece_pos.x;
        int y_temp = piece_pos.y;
        if(direction == 0) {
            getPiece(piece_pos).setLocation(new Point(x_temp, y_temp - BLOCK_SIZE));
        }
        else if(direction == 1) {
            getPiece(piece_pos).setLocation(new Point(x_temp + BLOCK_SIZE, y_temp));
        }
        else if(direction == 2) {
            getPiece(piece_pos).setLocation(new Point(x_temp, y_temp + BLOCK_SIZE));
        }
        else if(direction == 3) {
            getPiece(piece_pos).setLocation(new Point(x_temp - BLOCK_SIZE, y_temp));
        }
        else {
            throw new IllegalArgumentException("wrong direction use: 0 up, 1 right, 2 down, 3 left");
        }
    }

    public void displayWin() {  //todo

    }

    public void pressedPiece(Position pos) {
        getPiece(pos).setBackground(Color.BLUE);
    }

    public void releasedPiece(Position pos) {
        getPiece(pos).setBackground(Color.ORANGE);
    }

    public void remove(JPanel panel) {
        super.remove(panel);
        revalidate();
        repaint();
    }

    private JPanel getPiece(Position piece_pos) {
        for (int i = 0; i < pieces_view.length; i++) {
            if(piece_pos.isEqual(new Position(pieces_view[i].getLocation()))) {
                return pieces_view[i];
            }
        }
        return null;
    }

    public JButton getButton(String key) {
        return ((JButton)menu.getClientProperty(key));
    }
    public JComboBox getComboBox(String key) {
        return (JComboBox) menu.getClientProperty("level_selection");
    }
}
