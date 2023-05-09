import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends Window {
    Board board;
    // Position press_position;
    boolean pause_listener = false;

    public Game() {
        this(1);
    }

    public Game(int configuration) {
        super();
        showMenu();
        /*
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
                // press_position = new Position(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!pause_listener) {
                    //board.selectPiece(press_position.correspondantPiece().unitConverter());
                    // int move_direction = press_position.direction(new Position(e.getPoint()));
                    // System.out.print(move_direction);
                    //if(board.movePiece(move_direction))
                    //    window.movePiece(piece_position, move_direction);
                }
            }
        });
        */

        ((JButton)menu.getClientProperty("play_button")).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menu.removeAll();
                menu.repaint();
            }
        });
    }
}