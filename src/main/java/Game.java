import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends Window {
    private Board board;

    private BoardParser bParser;
    private Position press_position;
    private boolean pause_listener;

    public Game() throws IOException {
        super();

        board = new Board(1);

        getButton("play_button").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
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
                press_position = new Position(e.getPoint());}
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!pause_listener) {
                    Position unit_pos = press_position.unitConverter();
                    board.selectPiece(unit_pos);
                    int move_direction = press_position.direction(new Position(e.getPoint()));
                    System.out.println("\npiece selected position: " + board.getSelectedPiece()); //DEBUG: interaction with board selection
                    System.out.println("move direction: " + move_direction);   //DEBUG: direction
                    if(board.movePiece(move_direction))
                    {
                        System.out.println(" -> has moved");    //DEBUG: movePiece method
                        // moveLabel(unit_pos.pixelConverter(), move_direction);
                    }
                }
            }
        });

        startMenu();

       bParser = new BoardParser();
//
//        bParser.exportBoard(board.getPieces());

        try {
            bParser.importBoard();
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