import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends Window {
    
    private Board board;
    private int level;
    private BoardParser bParser;
    private Position press_position;
    private boolean pause_listener;

    public Game() {
        super();

        getPlayButton("play_button").addActionListener(e -> {
            level = getComboBox("level_selection").getSelectedIndex() + 1;
            remove(menu);
            board = new Board(level);
            startGame();
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
                            setMoves(board.getMoves());
                            movePiecePanel(piece_pos, move_direction);
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

        getMenuItem(0, 0).addActionListener(e -> { saveState(); });
        getMenuBarButton("Reset").addActionListener(e -> { reset(); });

        startMenu();
    }

    void startMenu() {
        showMenu();
    }

    void startGame() {
        showBoard(board);
    }

    public void reset() {
        board = new Board(level);
        reloadBoard(board);
    }

    public void saveState() {
        bParser = new BoardParser();
        try {
            bParser.saveState(board.getPieces());
            bParser.importBoard("save.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadState() { }

    public void undo() { }

    public void bestMove() { }
}