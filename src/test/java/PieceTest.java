import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void contains() {
        Piece piece = new Piece(2,2,2,2);
        assertFalse(piece.contains(4,5));
        assertFalse(piece.contains(-1,-1));
        assertTrue(piece.contains(2,2));
        assertTrue(piece.contains(2,3));
        assertTrue(piece.contains(3,2));
        assertTrue(piece.contains(3,3));
    }

    @Test
    void move() {
        Piece piece = new Piece(2,2,2,2);
        piece.move(0); //down
        assertTrue(piece.getX()==2 && piece.getY()==3);
        assertFalse(piece.getX()==2 && piece.getY()==2);
        piece.move(1); //right
        assertTrue(piece.getX()==3 && piece.getY()==3);//out of bounds but move() doesn't throw any error. Board.movePiece will make sure that this move won't happen, not Piece.move()
        piece.move(2); //up
        assertTrue(piece.getX()==3 && piece.getY()==2);
        piece.move(3); //left
        assertTrue(piece.getX()==2 && piece.getY()==2);
    }


    @Test
    void getProperties() {
        Piece piece = new Piece(0,0,2,1);
        piece.move(0); //down
        assertArrayEquals(piece.getProperties(), new int[]{0, 1, 2, 1});
        piece.move(1); //right
        assertArrayEquals(piece.getProperties(), new int[]{1, 1, 2, 1});
        piece.move(2); //up
        assertArrayEquals(piece.getProperties(), new int[]{1, 0, 2, 1});
        piece.move(3); //left
        assertArrayEquals(piece.getProperties(), new int[]{0, 0, 2, 1});

        Piece piece2 = new Piece(piece);
        assertArrayEquals(piece2.getProperties(), piece.getProperties());
    }

    @Test
    void equals() {
        Piece p1 = new Piece(0,0,2,1);
        Piece p2 = new Piece(p1);
        assertTrue(p1.equals(p2));
        p1.move(1);
        assertFalse(p2.equals(p1));
        p2.move(1);
        assertTrue(p1.equals(p2));
        assertTrue(new Piece(1,0,2,1).equals(p2));
        assertFalse(p1.equals(null));
    }
}