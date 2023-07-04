import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovesLogTest {

    @Test
    void resetLog() {
        MovesLog moves = new MovesLog();

        // push some moves in the MovesLog
        moves.pushMove(3, 2);
        moves.pushMove(2, 0);
        moves.pushMove(5, 1);
        moves.pushMove(9, 3);

        moves.resetLog();   // with reset moves should be empty

        assertTrue(moves.isEmpty());
    }

    @Test
    void push_popMove() {
        MovesLog moves = new MovesLog();

        // push some moves in the MovesLog
        moves.pushMove(3, 2);   // step = 0
        moves.pushMove(2, 0);   // step = 1
        moves.pushMove(5, 1);   // step = 2
        moves.pushMove(9, 3);   // step = 3

        assertTrue(moves.popMove().equals(new Move(3, 9, 3)));
        assertTrue(moves.popMove().equals(new Move(2, 5, 1)));
        assertTrue(moves.popMove().equals(new Move(1, 2, 0)));
        assertTrue(moves.popMove().equals(new Move(0, 3, 2)));
    }

    @Test
    void isEmpty() {
        MovesLog moves = new MovesLog();

        assertTrue(moves.isEmpty());
    }

    @Test
    void save_loadLog() {
        MovesLog moves = new MovesLog();

        // push 2 moves in the MovesLog
        moves.pushMove(3, 2);
        moves.pushMove(2, 0);

        moves.saveLog();    // save the Log

        // push other 2 moves in the MovesLog
        moves.pushMove(5, 1);
        moves.pushMove(9, 3);

        moves.loadLog();    // load the previous Log

        // the moves in the Log will be just the first 2 inserted before the save
        assertTrue(moves.popMove().equals(new Move(1, 2, 0)));
        assertTrue(moves.popMove().equals(new Move(0, 3, 2)));
    }

    @Test
    void getStep() {
        MovesLog moves = new MovesLog();

        // push some moves in the MovesLog
        moves.pushMove(3, 2);   // step = 0
        moves.pushMove(2, 0);   // step = 1
        moves.pushMove(5, 1);   // step = 2
        moves.pushMove(9, 3);   // step = 3

        // last step move was 3, but the step is the number of moves, then now it's 4
        assertEquals(4, moves.getStep());
    }
}