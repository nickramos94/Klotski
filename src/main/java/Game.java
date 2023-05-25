import com.fasterxml.jackson.core.JsonProcessingException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Stack;

public class Game extends Window {
    private Board board;
    private int level;
    private BoardParser bParser;
    private Solver solver;
    private int step;
    private Stack<Move> moves_log;
    private Stack<Move> saved_moves_log;
    private List<Move> best_moves;
    private Position press_position;
    private boolean pause_listener;
    final private String SAVE_FILE = "save.json";

    public Game() {
        super();

        getPlayButton("play_button").addActionListener(e -> {
            level = getComboBox("level_selection").getSelectedIndex() + 1;
            startGame(level);
        });

        // mouse listener for movePiece
        board_view.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseEntered(MouseEvent e) {
                pause_listener = false;
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                pause_listener = true;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                board_view.requestFocus();
                press_position = new Position(e.getPoint());
                Position unit_pos = press_position.unitConverter();
                if(board.selectPiece(unit_pos)) {
                    pressedPiece(board.getSelectedPiece().pixelConverter());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                board_view.requestFocus();
                if(board.getSelectedPiece() != null) {
                    Position piece_pos = board.getSelectedPiece().pixelConverter();
                    if (!pause_listener) {
                        int move_direction = press_position.direction(new Position(e.getPoint()));
                        if (board.movePiece(move_direction)) {
                            moves_log.push(new Move(step, board.getSelectedIndex(), move_direction));
                            setMoves(board.getMoves());
                            movePiecePanel(piece_pos, move_direction);
                            step++;
                        }
                    }
                    releasedPiece(board.getSelectedPiece().pixelConverter());
                    if (board.checkWin()) {
                        System.out.println("You Won");
                        displayWin();
                    }
                }
            }
        });

        getMenuItem(0, 0).addActionListener(e -> saveState(SAVE_FILE));  // save action listener
        getMenuItem(0, 1).addActionListener(e -> loadState(SAVE_FILE));  // load action listener
        getMenuItem(0, 2).addActionListener(e -> startMenu());   // return to main menu action listener
        getMenuItem(1, 0).addActionListener(e -> setBoard(1));  // level 1 action listener
        getMenuItem(1, 1).addActionListener(e -> setBoard(2));  // level 2 action listener
        getMenuBarButton("Reset").addActionListener(e -> reset());     // reset action listener
        getMenuBarButton("Undo").addActionListener(e -> undo());     // undo action listener
        getMenuBarButton("Best move").addActionListener(e -> bestMove());     // best move action listener

        startMenu();
    }

    private void startMenu() {
        showMenu();
    }

    private void startGame(int level) {
        board = new Board(level);
        setLog();
        showBoard(board);
    }

    private void setBoard(int level_number) {
        level = level_number;
        board = new Board(level_number);
        setLog();
        reloadBoard(board);
    }

    private void setBoard(Board board) {
        this.board = board;
        reloadBoard(board);
    }

    private void setLog() {
        moves_log = new Stack<>();
        step = 0;
    }

    private void reset() {
        board = new Board(level);
        reloadBoard(board);
    }

    //salva la configurazione attuale della scacchiera in un file JSON nel file-system locale
    private void saveState(String file) {
        bParser = new BoardParser();
        try {
            saved_moves_log = (Stack<Move>) moves_log.clone();
            bParser.saveState(board.getPieces(), file);
            bParser.exportBoard(board.getPieces());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadState(String file) {
        bParser = new BoardParser();
        try {
            moves_log = (Stack<Move>) saved_moves_log.clone();
            step = moves_log.peek().getStep();
            setBoard(new Board(bParser.importBoard(file), step+1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void undo() {
        if(moves_log != null && !moves_log.isEmpty()) {
            board.invertedMove(moves_log.pop());
            step--;
            reloadBoard(board);
        }

    }

    public void bestMove() {
        solve();
    }

    //Manda la configurazione della tastiera ad un server esterno che ritorna la lista delle mosse necessarie per vincere il gioco
    public void solve() {
        bParser = new BoardParser();
        solver = new Solver();

        try {
            best_moves = solver.sendToSolver(bParser.exportBoard(board.getPieces()));
            Move next_move = best_moves.get(0);
            System.out.println("mossa: " + next_move.getBlockIdx() + " " + next_move.getDirIdx());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}