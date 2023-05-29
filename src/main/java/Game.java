import com.fasterxml.jackson.core.JsonProcessingException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class Game extends Window {
    private Board board;
    private Board temp_board;
    private int level;
    private BoardParser bParser;
    private Solver solver;
    private MovesLog log;
    private List<Move> best_moves;
    private Position press_position;
    private boolean pause_listener;
    final private String SAVE_FILE = "save.json";

    public Game() {
        // initialize the game window with the Window() constructor
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

            /* mousePressed event
            * select the piece in the board that correspond to the mouse press position,
            * change color of the pressed piece to signal the piece on the board_view */
            @Override
            public void mousePressed(MouseEvent e) {
                board_view.requestFocus();
                press_position = new Position(e.getPoint());
                Position unit_pos = press_position.unitConverter();
                if(board.selectPiece(unit_pos)) {
                    pressedPiece(board.getSelectedPiece().pixelConverter());
                }
            }

            /* mouseReleased event
            * move the selected piece in the cardinal direction given by the vector that
            * link the point where the mouse is released and the initial pressed position,
            * change color of the released piece in the board_view with the default color */
            @Override
            public void mouseReleased(MouseEvent e) {
                board_view.requestFocus();
                if(board.getSelectedPiece() != null) {
                    if (!pause_listener) {
                        int move_direction = press_position.direction(new Position(e.getPoint()));
                        move(move_direction);
                    }
                    releasedPiece(board.getSelectedPiece().pixelConverter());
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
        getMenuBarButton("Reset").addActionListener(e -> reset());     // reset action listener
        getMenuBarButton("Solve all").addActionListener(e -> solveAll());   // solve all action lister
        getMenuBarButton("Undo").addActionListener(e -> undo());     // undo action listener
        getMenuBarButton("Best move").addActionListener(e -> bestMove());     // best move action listener

        showMenu();
    }

    // initialize board and show it in the window
    private void startGame(int level) {
        board = new Board(level);
        showBoard(board);
    }

    // set the board with the level number, removing previous board_view
    private void setBoard(int level_number) {
        level = level_number;
        board = new Board(level_number);
        log.resetLog();
        reloadBoard(board);
    }

    // set the board with another board, removing previous board_view, but keeping the previous log
    private void setBoard(Board board) {
        this.board = board;
        reloadBoard(board);
    }

    // move piece selected in the move_direction: the piece moves in the board and in the board_view
    private void move(int move_direction) {
        Position piece_pos = board.getSelectedPiece().pixelConverter();
        if (board.movePiece(move_direction)) {
            log.pushMove(board.getSelectedIndex(), move_direction);
            setMoves(board.getMoves());
            movePiecePanel(piece_pos, move_direction);
        }
    }

    // reset the board and the log, removing previous board_view
    public void reset() {
        board = new Board(level);
        log.resetLog();
        reloadBoard(board);
    }

    // save the current board configuration and moves in a JSON file
    private void saveState(String file) {
        bParser = new BoardParser();
        try {
            log.saveLog();
            bParser.saveState(board.getPieces(), file , log.getStep()+1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // load the moves and the board configuration saved in a JSON file
    public void loadState(String file) {
        bParser = new BoardParser();
        try {
            log.loadLog();
            List<int[]> pieces = bParser.importBoard(file);
            int moves = pieces.remove(0)[0];    // first array in the list contains the moves number
            setBoard(new Board(pieces, moves));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // undo the last move done using the MovesLog and invertedMove method
    public void undo() {
        if(!log.isEmpty()) {
            board.invertedMove(log.popMove());
            reloadBoard(board);
        }
    }

    // solve all the level with a delay of *** per move
    public void solveAll() {
        pause_listener = true;
        while(!bestMove()) {    //todo
            /*try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }*/
        }
    }

    // best move using makeBestMove() if the configuration of the board hasn't changed, solve() otherwise
    public boolean bestMove() {
        if(!board.checkWin()) {
            if (board.isEqual(temp_board))
                makeBestMove();
            else
                solve();
            temp_board = new Board(board.getPieces());
        }
        return checkWin();
    }

    // send the board config to an extern server that return the list of moves to win the game
    public void solve() {
        bParser = new BoardParser();
        solver = new Solver();
        try {
            best_moves = solver.sendToSolver(bParser.exportBoard(board.getPieces(), log.getStep()));
            makeBestMove();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // make the best move using (and removing) the first element of the list best_moves
    public void makeBestMove() {
        if(best_moves.isEmpty())
            throw new IllegalArgumentException("");
        Move next_move = best_moves.remove(0);
        System.out.println("mossa: " + next_move.getBlockIdx() + " " + next_move.getDirIdx());
        board.selectPiece(next_move.getBlockIdx());
        move(next_move.getDirIdx());
    }

    // check the win and display the Pane of the win
    public boolean checkWin() {
        if(board.checkWin()) {
            int win_option = displayWin();
            if(win_option == JOptionPane.YES_OPTION) {
                reset();
            }
            if(win_option == JOptionPane.NO_OPTION) {
                showMenu();
            }
            board.resetWin();
            return true;
        }
        return false;
    }
}