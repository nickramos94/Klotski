import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends Window {
    Board board;
    Position press_position;
    boolean pause_listener;

    public Game() {
        super();
        showMenu();


        ((JButton)menu.getClientProperty("play_button")).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int level = ((JComboBox)menu.getClientProperty("level_selection")).getSelectedIndex();
                level++;
                System.out.println("Level number: " + (level));     //DEBUG: selected level number
                remove(menu);
                board = new Board(level);
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
                    Position unit_pos = press_position.unitConverter();
                    board.selectPiece(unit_pos);
                    int move_direction = press_position.direction(new Position(e.getPoint()));
                    System.out.println("\npiece selected position: " + board.getSelectedPiece()); //DEBUG: interaction with board selection
                    System.out.println("move direction: " + move_direction);   //DEBUG: direction
                    if(board.movePiece(move_direction))
                    {
                        System.out.println(" -> has moved");    //DEBUG: movePiece method
                        moveLabel(unit_pos.pixelConverter(), move_direction);
                    }
                }
            }
        });
    }
}