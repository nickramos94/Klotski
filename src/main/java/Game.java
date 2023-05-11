import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends Window {
    Board board;
    Position press_position;

    BoardParser board_P;
    boolean pause_listener;


    public Game() throws IOException {
        super();
        board = new Board(1);
        showMenu();
        ((JButton)menu.getClientProperty("play_button")).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(menu);
                showBoard();
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
                    board.selectPiece(press_position.unitConverter());
                    int move_direction = press_position.direction(new Position(e.getPoint()));
                    System.out.print("\n\npiece selected position: " + board.getSelectedPiece()); //DEBUG: interaction with board selection
                    System.out.print("\nmove direction: " + move_direction);   //DEBUG: direction
                    if(board.movePiece(move_direction))
                    {
                        System.out.print("\n -> has moved");    //DEBUG: movePiece method
                    //    board_view.move(press_position, move_direction);
                    }
                }
            }
        });

        // Sezione di prova per il parser, dopo dovremmo aggiungere un ascoltatore appropriato
        board_P = new BoardParser();
        try {
            board_P.parseBoard(board.getPieces());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Fine sezione di prova per il parser
    }
}