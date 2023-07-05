import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void testEquals() {
        Move move = new Move();
        move.setStep(3);
        move.setBlockIdx(5);
        move.setDirIdx(2);

        Move other_move = new Move(3, 5, 2);

        assertTrue(move.equals(other_move));
    }

    @Test
    void testToString() {
        Move move = new Move(3, 5, 2);

        assertEquals("3, 5, 2", move.toString());
    }
}