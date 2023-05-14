import com.fasterxml.jackson.core.JsonProcessingException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;

public class Game extends Window {
    private Board board;

    private BoardParser bParser;

    private Solver solver;
    private Position press_position;
    private boolean pause_listener;

    public Game() throws IOException {
        super();

        getButton("play_button").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int level = getComboBox("level_selection").getSelectedIndex();
                level++;
                System.out.println("Level number: " + (level));     //DEBUG: selected level number
                remove(menu);
                board = new Board(level);
                try {
                    startGame();
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

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
                press_position = new Position(e.getPoint());
                Position unit_pos = press_position.unitConverter();
                if(board.selectPiece(unit_pos)) {
                    pressedPiece(board.getSelectedPiece().pixelConverter());
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                Position piece_pos = board.getSelectedPiece().pixelConverter();
                if (!pause_listener) {
                    int move_direction = press_position.direction(new Position(e.getPoint()));
                    System.out.println("\npiece selected position: " + board.getSelectedPiece()); //DEBUG: interaction with board selection
                    System.out.println("position in pixel: " + piece_pos);
                    System.out.println("move direction: " + move_direction);   //DEBUG: direction
                    if(board.movePiece(move_direction))
                    {
                        System.out.println(" -> has moved");    //DEBUG: movePiece method
                        movePiecePanel(piece_pos, move_direction);
                    }
                }
                releasedPiece(board.getSelectedPiece().pixelConverter());
                if(board.checkWin())
                {
                    System.out.println("You Win");  //DEBUG: win condition
                    displayWin();
                }
            }
        });

        startMenu();

    }

    public void saveState() {

//
//       bParser.exportBoard(board.getPieces());
//
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

    void startGame() throws JsonProcessingException, MalformedURLException {
        showBoard(board);
        bParser = new BoardParser();

        solver = new Solver();
        try {
            solver.sendToSolver(bParser.exportBoard(board.getPieces()));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        board.getPieces();
    }
}