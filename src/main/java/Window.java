import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


class Button extends JButton {
    Button(String str) {
        super(str);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setBorderPainted(false);
        setFocusable(false);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.ORANGE);
            }
        
            public void mouseExited(MouseEvent e) {
                setForeground(Color.WHITE);
            }
        });
    }
}

public class Window extends JFrame  {

    final static String winName = "Klotski";
    final static public int BLOCK_SIZE = 120;
    final static public int BOARD_WIDTH = Board.WIDTH * BLOCK_SIZE;
    final static public int BOARD_HEIGHT = Board.HEIGHT * BLOCK_SIZE + 5;

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
        board_view.setBackground(Color.GRAY);

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
        exit.setBackground(Color.RED);
        exit.setEnabled(false); // Set the button as disabled
        exit.setBounds(BLOCK_SIZE, BOARD_HEIGHT - 5, BLOCK_SIZE * 2, 5);
        board_view.add(exit);

        for(int i = 0; i < pieces.length; i++) {
            int[] prop = pieces[i].getProperties();
            pieces_view[i] = new JPanel();
            pieces_view[i].setBounds(prop[0] * BLOCK_SIZE, prop[1] * BLOCK_SIZE, prop[2] * BLOCK_SIZE, prop[3] * BLOCK_SIZE);
            pieces_view[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));
            if(board.isSpecial(i)) {
                pieces_view[i].setBackground(Color.RED);
            }
            else {
                pieces_view[i].setBackground(Color.ORANGE);
            }
            board_view.add(pieces_view[i]);
        }

        revalidate();
        repaint();
    }

    public void setMoves(int m) {
        getLabel(menuBar, "moves").setText("Moves: " + m);
    }

    public void movePiecePanel(Position piece_pos, int direction) {
        int x_temp = piece_pos.x;
        int y_temp = piece_pos.y;

        if(direction == 2) {
            getPiece(piece_pos).setLocation(new Point(x_temp, y_temp - BLOCK_SIZE));
        }
        else if(direction == 1) {
            getPiece(piece_pos).setLocation(new Point(x_temp + BLOCK_SIZE, y_temp));
        }
        else if(direction == 0) {
            getPiece(piece_pos).setLocation(new Point(x_temp, y_temp + BLOCK_SIZE));
        }
        else if(direction == 3) {
            getPiece(piece_pos).setLocation(new Point(x_temp - BLOCK_SIZE, y_temp));
        }
        else {
            throw new IllegalArgumentException("wrong direction use: 0 up, 1 right, 2 down, 3 left");
        }
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

    public void pressedPiece(Position pos) {
        getPiece(pos).setBackground(Color.BLUE);
    }

    public void releasedPiece(Position pos, boolean isSpecial) {
        if(isSpecial) {
            getPiece(pos).setBackground(Color.RED);
        }
        else {
            getPiece(pos).setBackground(Color.ORANGE);
        }
    }

    public void remove(JPanel panel) {
        super.remove(panel);
        revalidate();
        repaint();
    }

    private JPanel getPiece(Position piece_pos) {
        for (int i = 0; i < pieces_view.length; i++) {
            if(piece_pos.equals(new Position(pieces_view[i].getLocation()))) {
                return pieces_view[i];
            }
        }
        return null;
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
                "GOAL:\n" +
                        "Among the blocks, there is a special one (the 2x2 red) which\n" +
                        "must be moved to a special area, where there is the red line.\n" +
                        "RULES:\n" +
                        "The player may only slide blocks horizontally and vertically,\n" +
                        "cannot bring pieces outside the board and, for that, when you\n" +
                        "leave the board with the mouse the action will be cancelled.\n" +
                        "HELP:\n" +
                        "With the button \"Best move\" the best move will be made,\n" +
                        "with the button \"Solve all\" all the puzzle will be solved",
                "This project is the Klotsky game developed by 4 students.",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
