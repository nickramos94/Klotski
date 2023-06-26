import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;


/**
 * The Theme class represents a collection of colors used for application.
 */
class Theme {
    /**
     * The color for the big block.
     */
    static Color bigBlock = Color.decode("#001C30");

    /**
     * The color for a regular block.
     */
    static Color block = Color.decode("#176B87");

    /**
     * The color for a selected block.
     */
    static Color blockSelected = Color.decode("#64CCC5");

    /**
     * The color for the border of a block.
     */
    static Color blockBorder = Color.decode("#FFFFFF");

    /**
     * The color for an end point to win the game.
     */
    static Color endPoint = Color.RED;

    /**
     * The background color.
     */
    static Color background = Color.decode("#DAFFFB");

    /**
     * The color for a menu.
     */
    static Color menu = Color.WHITE;

    /**
     * The color for menu text.
     */
    static Color menuText = Color.BLACK;

    /**
     * The color for menu text whene the mouse is on hover.
     */
    static Color menuTextHover = Color.ORANGE;
}


/**
 * This class represents a customized button that extends the JButton class.
 * It provides a default style for buttons with specific colors.
 */
class Button extends JButton {

    /**
     * Constructs a Button with the specified label.
     * @param str The label text for the button.
     */
    Button(String str) {
        super(str);
        setBackground(Theme.menu);
        setForeground(Theme.menuText);
        setBorderPainted(false);
        setFocusable(false);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setForeground(Theme.menuTextHover);
            }
        
            public void mouseExited(MouseEvent e) {
                setForeground(Theme.menuText);
            }
        });
    }
}

public class Window extends JFrame  {

    final static String winName = "Klotski";
    final static public int BLOCK_SIZE = 130;
    final static public int BOARD_WIDTH = Board.WIDTH * BLOCK_SIZE;
    final static public int BOARD_HEIGHT = Board.HEIGHT * BLOCK_SIZE + 5;
    final static private String infoTitle = "This project is the Klotsky game developed by 4 students.";
    final static private String infoGameRules = "GOAL:\n" +
    "Among the blocks, there is a special one (the 2x2 red) which\n" +
    "must be moved to a special area, where there is the red line.\n" +
    "RULES:\n" +
    "The player may only slide blocks horizontally and vertically,\n" +
    "cannot bring pieces outside the board and, for that, when you\n" +
    "leave the board with the mouse the action will be cancelled.\n" +
    "HELP:\n" +
    "With the button \"Best move\" the best move will be made,\n" +
    "with the button \"Solve all\" all the puzzle will be solved";

    protected JPanel menu;
    private JMenuBar mainBar;
    private JMenuBar menuBar;
    protected JPanel board_view;
    private JPanel[] pieces_view;

    public Window() {
        super(winName);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setWindowSize(BOARD_WIDTH, BOARD_HEIGHT);
        pack();
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
        // selectLevel.setBackground(Theme.menu);
        // selectLevel.setForeground(Theme.menuText);
        menu.putClientProperty("level_selection", selectLevel);
        selectLevel.setPreferredSize(new Dimension(100, 50));

        JButton playBtn = new JButton("Play");
        menu.putClientProperty("play_button", playBtn);
        playBtn.setPreferredSize(new Dimension(100, 50));
        playBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel levelSelection = new JPanel();
        levelSelection.add(selectLevel);
        levelSelection.add(playBtn);

        menu.add(title, BorderLayout.NORTH);
        menu.add(levelSelection, BorderLayout.CENTER);

        // Create BOARD
        board_view = new JPanel(null);
        board_view.setBackground(Theme.background);

        JButton b = new JButton("TEST");
        b.setBackground(Color.ORANGE);
        // button.setEnabled(false); // Set the button as disabled
        b.setBounds(0, 0,BOARD_WIDTH, BOARD_HEIGHT);
        board_view.add(b);

        // Menu bar
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        
        JMenu fileMenu = new JMenu("Settings");
        menuBar.putClientProperty("Settings", fileMenu);

        fileMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fileMenu.setForeground(Theme.menuTextHover);
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
                fileMenu.setForeground(Theme.menuText);
            }
        });

        JMenu levelMenu = new JMenu("Levels");
        menuBar.putClientProperty("Levels", levelMenu);
        
        Button reset = new Button("Reset");
        menuBar.putClientProperty("Reset", reset);

        Button solveAll = new Button("Solve all");
        menuBar.putClientProperty("Solve all", solveAll);

        Button undo = new Button("Undo");
        menuBar.putClientProperty("Undo", undo);

        Button bestMove = new Button("Best move");
        menuBar.putClientProperty("Best move", bestMove);

        JLabel moves = new JLabel("Moves: 0");
        menuBar.putClientProperty("moves", moves);

        // fileMenu items
        JMenuItem newItem = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Load");
        JMenuItem mainItem = new JMenuItem("Main menu");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(mainItem);
        menuBar.setVisible(true);

        // levelMenu items
        JMenuItem level1 = new JMenuItem("Level 1");
        JMenuItem level2 = new JMenuItem("Level 2");
        levelMenu.add(level1);
        levelMenu.add(level2);
        menuBar.setVisible(true);

        // add to menu and buttons to menu bar
        menuBar.add(fileMenu);
        menuBar.add(levelMenu);
        menuBar.add(reset);
        menuBar.add(moves);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(solveAll);
        menuBar.add(undo);
        menuBar.add(bestMove);


        // main menu bar
        mainBar = new JMenuBar();
        mainBar.setBackground(Color.WHITE);

        Button info = new Button("Info");
        info.addActionListener(e -> showInfo());

        mainBar.add(Box.createHorizontalGlue());
        mainBar.add(info);
    }

    public void setWindowSize(int width, int height) {
        Container c = getContentPane();
        Dimension d = new Dimension(width, height);
        c.setPreferredSize(d);
    }

    public void showMenu() {
        remove(board_view);
        setJMenuBar(mainBar);
        add(menu, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showBoard(Board board) {
        remove(menu);
        setJMenuBar(menuBar);
        pack();
        reloadBoard(board);
        add(board_view);
        revalidate();
        repaint();
    }

    public void reloadBoard(Board board) {
        board_view.removeAll();
        setBoardView(board);
    }

    public void setBoardView(Board board) {
        setMoves(board.getMoves());
        Piece[] pieces = board.getPieces();
        pieces_view = new JPanel[pieces.length];
        
        JButton exit = new JButton();
        exit.setBackground(Theme.endPoint);
        exit.setEnabled(false); // Set the button as disabled
        exit.setBounds(BLOCK_SIZE, BOARD_HEIGHT - 5, BLOCK_SIZE * 2, 5);
        board_view.add(exit);

        for(int i = 0; i < pieces.length; i++) {
            int[] prop = pieces[i].getProperties();
            pieces_view[i] = new JPanel();
            pieces_view[i].setBounds(prop[0] * BLOCK_SIZE, prop[1] * BLOCK_SIZE, prop[2] * BLOCK_SIZE, prop[3] * BLOCK_SIZE);
            pieces_view[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Theme.blockBorder));
            if(board.isSpecial(i)) {
                pieces_view[i].setBackground(Theme.bigBlock);
            }
            else {
                pieces_view[i].setBackground(Theme.block);
            }
            board_view.add(pieces_view[i]);
        }

        revalidate();
        repaint();
    }

    public void setMoves(int m) {
        getLabel(menuBar, "moves").setText("Moves: " + m);
    }

    public void movePiecePanel(int piece_index, int x, int y) {
        pieces_view[piece_index].setLocation(new Point(x, y));
    }

    public int displayWin() {
        Object[] options = {"Restart", "Main Menu"};
        return JOptionPane.showOptionDialog(this,
                "You Won!",
                "level completed",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
    }

    public void pressedPiece(int index) {
        pieces_view[index].setBackground(Theme.blockSelected);
    }

    public void releasedPiece(int index, boolean isSpecial) {
        if(isSpecial) {
            pieces_view[index].setBackground(Theme.bigBlock);
        }
        else {
            pieces_view[index].setBackground(Theme.block);
        }
    }

    public void remove(JPanel panel) {
        super.remove(panel);
        revalidate();
        repaint();
    }

    public JPanel getPiece(int index) {
        return pieces_view[index];
    }

    public JButton getPlayButton(String key) {
        return ((JButton)menu.getClientProperty(key));
    }

    public JLabel getLabel(JComponent c, String key) {
        return ((JLabel) c.getClientProperty(key));
    }

    public JComboBox getComboBox(String key) {
        return (JComboBox) menu.getClientProperty(key);
    }

    public JMenuItem getMenuItem(int menu_index, int item_index) {
        return (JMenuItem) menuBar.getMenu(menu_index).getItem(item_index);
    }

    public JButton getMenuBarButton(String key) {
        return (JButton) menuBar.getClientProperty(key);
    }

    private void showInfo() {
        JOptionPane.showMessageDialog(this,
                infoGameRules,
                infoTitle,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
