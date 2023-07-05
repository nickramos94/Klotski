import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.net.URL;


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

    /*
     * Window name
     */
    final static String winName = "Klotski";
    
    /*
     * Size of a 1x1 block in pixels
     */
    final static public int BLOCK_SIZE = 130;
    
    /*
     * Board width 
     */
    final static public int BOARD_WIDTH = Board.WIDTH * BLOCK_SIZE;
    
    /* 
     * Board height
     */
    final static public int BOARD_HEIGHT = Board.HEIGHT * BLOCK_SIZE + 5;

    /*
     * GitHub URL of the project
     */
    final static private String gitUrl = "https://github.com/nickramos94/Klotski.git";

    /*
     * Klotski Wikipedia page
     */
    final static private String klotskiPage = "https://en.wikipedia.org/wiki/Klotski";
    
    /*
     * Title popup window for the game info
     */
    final static private String infoTitle = "This project is the Klotsky game developed by 4 students.";
    
    /*
     * Game rules inside the info popup window
     */
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

    /* 
     * JPanel to handle the menu
    */
    protected JPanel menu;
    
    /* 
     * Top bar for the menu 
     */
    private final JMenuBar menuBar;

    /* 
     * JPanel to handle the board for the game
    */
    protected JPanel board_view;

    /* 
     * Top bar for the board view
     */
    private final JMenuBar boardBar;

    /*
     * Array that store every single piece as a JPanel
     */
    private JPanel[] pieces_view;

    /*
     * Window constructor where the JPanels of the Main Menu and of the Board View will be played are created
     */
    public Window() {

        // Set up the window: title, name, size, ecc..
        super(winName);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setWindowSize(BOARD_WIDTH, BOARD_HEIGHT);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        
        // Create the MENU
        menu = new JPanel();
        menu.setLayout(new BorderLayout());

        JLabel title = new JLabel("Klotski");
        title.setFont(new Font("Arial", Font.BOLD, 88));
        title.setBorder(new EmptyBorder(150, 10, 10, 10));
        title.setHorizontalAlignment(JLabel.CENTER);

        String[] levels = { "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Random" };
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

        // main menu bar
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);

        Button info = new Button("Info");
        info.addActionListener(e -> showInfo());

        Button github = new Button("GitHub");
        github.addActionListener(e -> openWebpage(gitUrl));

        menuBar.add(info);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(github);

        // Create the BOARD VIEW 
        board_view = new JPanel(null);
        board_view.setBackground(Theme.background);

        JButton b = new JButton("TEST");
        b.setBackground(Color.ORANGE);
        b.setBounds(0, 0,BOARD_WIDTH, BOARD_HEIGHT);
        board_view.add(b);

        // create the board bar
        boardBar = new JMenuBar();
        boardBar.setBackground(Color.WHITE);
        
        JMenu fileMenu = new JMenu("Settings");
        boardBar.putClientProperty("Settings", fileMenu);

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
        boardBar.putClientProperty("Levels", levelMenu);
        
        Button reset = new Button("Reset");
        boardBar.putClientProperty("Reset", reset);

        Button solveAll = new Button("Solve all");
        boardBar.putClientProperty("Solve all", solveAll);

        Button undo = new Button("Undo");
        boardBar.putClientProperty("Undo", undo);

        Button bestMove = new Button("Best move");
        boardBar.putClientProperty("Best move", bestMove);

        JLabel moves = new JLabel("Moves: 0");
        boardBar.putClientProperty("moves", moves);

        JMenuItem newItem = new JMenuItem("Save");
        JMenuItem openItem = new JMenuItem("Load");
        JMenuItem mainItem = new JMenuItem("Main menu");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(mainItem);
        boardBar.setVisible(true);

        JMenuItem level1 = new JMenuItem("Level 1");
        JMenuItem level2 = new JMenuItem("Level 2");
        JMenuItem level3 = new JMenuItem("Level 3");
        JMenuItem level4 = new JMenuItem("Level 4");
        JMenuItem level5 = new JMenuItem("Level 5");
        JMenuItem random = new JMenuItem("Random");
        levelMenu.add(level1);
        levelMenu.add(level2);
        levelMenu.add(level3);
        levelMenu.add(level4);
        levelMenu.add(level5);
        levelMenu.add(random);
        boardBar.setVisible(true);

        boardBar.add(fileMenu);
        boardBar.add(levelMenu);
        boardBar.add(reset);
        boardBar.add(moves);
        boardBar.add(Box.createHorizontalGlue());
        boardBar.add(solveAll);
        boardBar.add(undo);
        boardBar.add(bestMove);
    }

    /**
     * Sets the size of the window.
     *
     * @param width  the width of the window
     * @param height the height of the window
     */
    public void setWindowSize(int width, int height) {
        Container c = getContentPane();
        Dimension d = new Dimension(width, height);
        c.setPreferredSize(d);
    }

    /**
     * Displays the menu and hides the board view.
     */
    public void showMenu() {
        remove(board_view);
        setJMenuBar(menuBar);
        add(menu, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Displays the menu and hides the board view.
     */
    public void showBoard(Board board) {
        remove(menu);
        setJMenuBar(boardBar);
        pack();
        reloadBoard(board);
        add(board_view);
        revalidate();
        repaint();
    }

    /**
     * Reloads the game board with the provided board.
     *
     * @param board the game board to be reloaded
     */
    public void reloadBoard(Board board) {
        board_view.removeAll();
        setBoardView(board);
    }

    /**
     * Sets the view of the game board based on the provided board object.
     *
     * @param board the game board object
     */
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

    /**
     * Sets the number of moves in the game and updates the corresponding label in the board bar.
     *
     * @param m the number of moves
     */
    public void setMoves(int m) {
        getLabel(boardBar, "moves").setText("Moves: " + m);
    }

    /**
     * Moves the specified piece panel to the new coordinates (x, y).
     *
     * @param piece_index the index of the piece panel to move
     * @param x           the new x-coordinate
     * @param y           the new y-coordinate
     */
    public void movePiecePanel(int piece_index, int x, int y) {
        pieces_view[piece_index].setLocation(new Point(x, y));
    }

    /**
     * Displays a win message
     *
     * @return the user's choice, either 0 for restart or 1 for main menu
     */
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

    /**
     * Display a message with a title
     *
     * @param message the message to display
     * @param title the title to display
     */
    public void displayMessage(String title, String message) {
        JOptionPane.showMessageDialog(this,
                message,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Sets the background color of the specified piece panel to indicate it has been pressed.
     *
     * @param index the index of the piece panel
     */
    public void pressedPiece(int index) {
        pieces_view[index].setBackground(Theme.blockSelected);
    }

    /**
     * Resets the background color of the specified piece panel to indicate it has been released.
     *
     * @param index     the index of the piece panel
     * @param isSpecial a flag indicating if the piece is special
     */
    public void releasedPiece(int index, boolean isSpecial) {
        if(isSpecial) {
            pieces_view[index].setBackground(Theme.bigBlock);
        }
        else {
            pieces_view[index].setBackground(Theme.block);
        }
    }

    /**
     * Removes the specified panel from the container and refreshes the window.
     *
     * @param panel the panel to be removed
     */
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
        return (JMenuItem) boardBar.getMenu(menu_index).getItem(item_index);
    }

    public JButton getMenuBarButton(String key) {
        return (JButton) boardBar.getClientProperty(key);
    }

    /**
     * Displays an information message dialog with the game rules.
     */
    private void showInfo() {
        Object[] options = {"More Info"};
        int option = JOptionPane.showOptionDialog(this,
                infoGameRules,
                infoTitle,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
        if(option == JOptionPane.YES_OPTION)
            openWebpage(klotskiPage);
    }

    /**
     * Opens a web page based on the URL
     *
     * @param url the URL to open
     */
    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
