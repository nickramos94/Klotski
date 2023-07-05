import com.fasterxml.jackson.core.JsonProcessingException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Manages the gameplay
 */
public class Game extends Window {
    private Board board;
    private Board temp_board;
    private int level;
    private BoardParser bParser;
    final private MovesLog log;
    private List<Move> best_moves;
    private Position press_position;
    private boolean pause_listener;
    private boolean stop_solving;
    final private String SAVE_FILE = "save.json";

    /**
     * initialize the game window with the Window() constructor
     */
    public Game() {

        super();

        // initialize the moves log
        log = new MovesLog();

        // initialize the temporary board
        temp_board = null;

        // add action listener to play button, when pressed start game with the selected level
        getPlayButton("play_button").addActionListener(e -> {
            level = getComboBox("level_selection").getSelectedIndex() + 1;
            startGame(level);
        });

        // mouse listener to move pieces on board (Board) and on the board_view (JPanel)
        board_view.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                pause_listener = false;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pause_listener = true;
            }

            /** Selects the piece in the board that corresponds to the mouse pressed position,
             * changes the color of the pressed piece to signal the piece on the board_view
             * @param e the event to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                board_view.requestFocus();
                press_position = new Position(e.getPoint());
                Position unit_pos = press_position.unitConverter();
                if(board.selectPiece(unit_pos)) {
                    pressedPiece(board.getSelectedIndex());
                }
            }

            /** Moves the selected piece in the cardinal direction given by the vector that
             * links the point where the mouse is released and the initial pressed position,
             * changes color of the released piece in the board_view with the default color
             * @param e the event to be processed
             */
            /* mouseReleased event
            *  */
            @Override
            public void mouseReleased(MouseEvent e) {
                board_view.requestFocus();
                if(board.getSelectedIndex() != -1) {
                    if (!pause_listener) {
                        int move_direction = press_position.direction(new Position(e.getPoint()));
                        move(move_direction);
                    }
                    releasedPiece(board.getSelectedIndex(), board.isSpecial());
                    checkWin();
                }
            }
        });

        // actions of the menuBar items and buttons
        getMenuItem(0, 0).addActionListener(e -> saveState(SAVE_FILE));  // save action listener
        getMenuItem(0, 1).addActionListener(e -> loadState(SAVE_FILE));  // load action listener
        getMenuItem(0, 2).addActionListener(e -> showMenu());   // return to main menu action listener
        getMenuItem(1, 0).addActionListener(e -> setBoard(1));  // level 1 action listener
        getMenuItem(1, 1).addActionListener(e -> setBoard(2));  // level 2 action listener
        getMenuItem(1, 2).addActionListener(e -> setBoard(3));  // level 3 action listener
        getMenuItem(1, 3).addActionListener(e -> setBoard(4));  // level 4 action listener
        getMenuItem(1, 4).addActionListener(e -> setBoard(5));  // level 5 action listener
        getMenuItem(1, 5).addActionListener(e -> setBoard(6));  // level 6 (level random) action listener
        getMenuBarButton("Reset").addActionListener(e -> reset());     // reset action listener
        getMenuBarButton("Undo").addActionListener(e -> undo());     // undo action listener
        getMenuBarButton("Best move").addActionListener(e -> bestMove());     // best move action listener

        // solve all action listener, the button become a stop button when pressed
        JButton solve_button = getMenuBarButton("Solve all");
        solve_button.addActionListener(e -> {
            if (solve_button.getText().equals("Solve all")) {
                solve_button.setText("Stop");
                stop_solving = false;
                solveAll();
            } else if (solve_button.getText().equals("Stop")) {
                solve_button.setText("Solve all");
                stop_solving = true;
            }
        });


        showMenu();
    }

    /** Initializes board and shows it in the window
     * @param level level selector
     */
    private void startGame(int level) {
        if(level==6) {
            setRandomBoard();
        }
        else {
            loadState("level" + level + ".json");
        }
        showBoard(board);

    }

    /** sets the board with the level number, removing the previous board_view
     * @param level_number level selector
     */
    //
    protected void setBoard(int level_number) {
        level = level_number;
        if(level==6) {
            setRandomBoard();
        }
        else {
            loadState("level" + level_number + ".json");
        }
        log.resetLog();
        reloadBoard(board);
    }

    /** Sets the board with another board, removing the previous board_view, but keeping the previous log
     * @param board
     */
    private void setBoard(Board board) {
        this.board = board;
        reloadBoard(board);
    }

    /**
     * Sets the board with a random placement of pieces
     */
    private void setRandomBoard() {
        bParser = new BoardParser();
        Solver solver = new Solver();
        boolean is_connected = true;
        do {
            board = new Board();
            board.randomize();
            try {
                best_moves = solver.sendToSolver(bParser.exportBoard(board.getPieces(), log.getStep()));
            } catch (RuntimeException e) {
                displayMessage("unsafe random level", "A solution is not guaranteed for this level" +
                        "\nCheck your internet!");
                is_connected = false;
            } catch (MalformedURLException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        while(best_moves == null && is_connected);
    }

    /** Moves the selected piece in the move_direction: the piece moves in the board and in the board_view
     * @param move_direction int that speficies the direction (0 = down, 1 = right, 2 = up, 3 = left)
     */
    protected void move(int move_direction) {
        int piece_index = board.getSelectedIndex();
        if (board.movePiece(move_direction)) {
            log.pushMove(board.getSelectedIndex(), move_direction);
            setMoves(board.getMoves());
            Position new_pos = board.getSelectedPos().pixelConverter();
            movePiecePanel(piece_index, new_pos.x, new_pos.y);
        }
    }

    /**
     * Resets the board and the log, removing the previous board_view
     */
    protected void reset() {
        setBoard(level);
    }

    /** Saves the current board configuration and moves it in a JSON file
     * @param file file path
     */
    protected void saveState(String file) {
        bParser = new BoardParser();
        try {
            log.saveLog();
            bParser.saveState(board.getPieces(), file , log.getStep());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Loads the moves and the board configuration saved in a JSON file
     * @param file file path
     */
    protected void loadState(String file) {
        bParser = new BoardParser();
        try {
            log.loadLog();
            List<int[]> pieces = bParser.importBoard(file);
            int moves = pieces.remove(0)[0];    // first array in the list contains the moves number
            setBoard(new Board(pieces, moves));
        } catch (IOException e) {
            displayMessage("load error...", "You have to save first!");
        }
    }

    /**
     *  Undoes the last move done using the MovesLog and invertedMove method
     */
    protected void undo() {
        if(!log.isEmpty()) {
            board.invertedMove(log.popMove());
            reloadBoard(board);
        }
    }

    /**
     * Solves the whole level with a delay of 0.5 seconds per move
     */
    protected void solveAll() {
        Thread solvingThread = new Thread(() -> {
            bestMove();
            while (!best_moves.isEmpty() && !stop_solving) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if(!stop_solving)
                    bestMove();
            }
        });
        solvingThread.start();
    }

    /**
     * best move using makeBestMove() if the configuration of the board hasn't changed, solve() otherwise
     */
    protected void bestMove() {
        if(!board.checkWin()) {
            if(board.equals(temp_board)) {
                if (best_moves == null) {
                    if(getMenuBarButton("Solve all").getText().equals("Stop"))
                        getMenuBarButton("Solve all").setText("Solve all");
                    displayMessage("internet disconnected...", "Really?" +
                            "\nWithout internet you have to solve it on your own...");
                } else {
                    makeBestMove();
                }
            }
            else
                solve();
            temp_board = new Board(board.getPieces());
        }
        checkWin();
    }

    protected void makeMoves(List<Move> moves) {
        best_moves = moves;
        while(!best_moves.isEmpty()) {
            makeBestMove();
        }
    }

    /**
     * Sends the board config to an external server that returns the list of moves to win the game
     */
    private void solve() {
        bParser = new BoardParser();
        Solver solver = new Solver();
        try {
            best_moves = solver.sendToSolver(bParser.exportBoard(board.getPieces(), log.getStep()));
            makeBestMove();
        } catch (RuntimeException e) {
            if(getMenuBarButton("Solve all").getText().equals("Stop"))
                getMenuBarButton("Solve all").setText("Solve all");
            displayMessage("internet disconnected...", "Without internet connection we can't solve it." +
                    "\nCheck your internet!");
        } catch (JsonProcessingException | MalformedURLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Makes the best move using (and removing) the first element of the list best_moves
     */
    //
    private void makeBestMove() {
        if(!best_moves.isEmpty()) {
            Move next_move = best_moves.remove(0);
            board.selectPiece(next_move.getBlockIdx());
            move(next_move.getDirIdx());
        }
    }

    /**
     * Checks if the game has been won and displays the Pane of the win
     */
    private void checkWin() {
        if(board.checkWin()) {
            getMenuBarButton("Solve all").setText("Solve all");
            stop_solving = true;
            int win_option = displayWin();
            if(win_option == JOptionPane.YES_OPTION) {
                reset();
            }
            if(win_option == JOptionPane.NO_OPTION) {
                showMenu();
            }
            board.resetWin();
        }
    }

    /** Selects an index given its index
     * @param piece_index
     */
    protected void selectPiece(int piece_index) {
        board.selectPiece(piece_index);
    }

    /** Returns the board's configuration
     * @return board
     */
    protected Board getBoard() {
        return board;
    }

    /** Returns the moves' log
     * @return log
     */
    protected MovesLog getLog() {
        return log;
    }
}