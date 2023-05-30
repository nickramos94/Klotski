import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

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
        assertTrue(piece.getX()==2 && piece.getY()==3); //square 2x2 goes out of bounds but move() doesn't throw an error. Board.Movepiece will make sure that this move won't happen, not move()
        assertFalse(piece.getX()==2 && piece.getY()==2);
        piece.move(1); //right
        assertTrue(piece.getX()==3 && piece.getY()==3); //same there
        piece.move(2); //up
        assertTrue(piece.getX()==3 && piece.getY()==2);
        piece.move(3); //left
        assertTrue(piece.getX()==2 && piece.getY()==2);
    }

    @Test
    void getX() {
    }

    @Test
    void getY() {
    }

    @Test
    void getProperties() {
    }

    @Test
    void isEqual() {
    }
}