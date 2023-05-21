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

        getMenuItem(0, 0).addActionListener(e -> saveState());  // save action listener
        getMenuItem(0, 2).addActionListener(e -> startMenu());   // return to main menu action listener
        getMenuItem(1, 0).addActionListener(e -> setLevel(1));  // level 1 action listener
        getMenuItem(1, 1).addActionListener(e -> setLevel(2));  // level 2 action listener
        getMenuBarButton("Reset").addActionListener(e -> reset());     // reset action listener

        startMenu();
    }

    private void startMenu() {
        showMenu();
    }

    private void startGame(int level) {
        board = new Board(level);
        showBoard(board);
    }

    private void setLevel(int level_number) {
        level = level_number;
        board = new Board(level_number);
        reloadBoard(board);
    }

    private void reset() {
        board = new Board(level);
        reloadBoard(board);
    }

    private void saveState() {
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