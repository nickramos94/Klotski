import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends Window {
    private Board board;
    private int level;
    private BoardParser bParser;
    private Position press_position;
    private boolean pause_listener;

    public Game() throws IOException {
        super();

        // play button action listener
        getPlayButton("play_button").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level = getComboBox("level_selection").getSelectedIndex() + 1;
                System.out.println("Level number: " + (level));     //DEBUG: selected level number
                remove(menu);
                board = new Board(level);
                startGame();
            }
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
                        System.out.println("\npiece selected position: " + board.getSelectedPiece()); //DEBUG: interaction with board selection
                        System.out.println("position in pixel: " + piece_pos);
                        System.out.println("move direction: " + move_direction);   //DEBUG: direction
                        if (board.movePiece(move_direction)) {
                            System.out.println(" -> has moved");    //DEBUG: movePiece method
                            movePiecePanel(piece_pos, move_direction);
                        }
                    }
                    releasedPiece(board.getSelectedPiece().pixelConverter());
                    if (board.checkWin()) {
                        System.out.println("You Won");  //DEBUG: win condition
                        displayWin();
                    }
                }
            }
        });

        // saveState action listener
        getMenuItem(0, 0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveState();
            }
        });

        // reset button action listener
        getMenuBarButton("Reset").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        startMenu();
    }

    public void reset() {
        System.out.println("Level number: " + (level));     //DEBUG: selected level number
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

    void startMenu() {
        showMenu();
    }

    void startGame() {
        showBoard(board);
    }
}