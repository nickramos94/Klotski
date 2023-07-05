import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void direction() {
        // the direction of the vector that connects the two positions is DOWN = 0
        Position initialPos_down = new Position(50,50);
        Position finalPos_down = new Position(87,100);     // y is greater than x

        // the direction of the vector that connects the two positions is RIGHT = 1
        Position initialPos_right = new Position(50,50);
        Position finalPos_right = new Position(100,87);     // x is greater than y

        // the direction of the vector that connects the two positions is UP = 2
        Position initialPos_up = new Position(50,50);
        Position finalPos_up = new Position(3,0);     // y is smaller than x

        // the direction of the vector that connects the two positions is LEFT = 3
        Position initialPos_left = new Position(50,50);
        Position finalPos_left = new Position(0,3);     // x is smaller than y

        // the direction of the vector that connects the two positions is NULL = -1
        Position initialPos_null = new Position(50,50);
        Position finalPos_null = new Position(48,53);     // x and y are too little different from the initials


        assertEquals(0, initialPos_down.direction(finalPos_down));
        assertEquals(1, initialPos_right.direction(finalPos_right));
        assertEquals(2, initialPos_up.direction(finalPos_up));
        assertEquals(3, initialPos_left.direction(finalPos_left));
        assertEquals(-1, initialPos_null.direction(finalPos_null));
    }

    @Test
    void testEquals() {
        Position pos_1 = new Position(136,78);
        Position pos_2 = new Position(357,576);
        Position pos_3 = new Position(6,13);

        assertTrue(pos_1.equals(new Position(136,78)));
        assertTrue(pos_2.equals(new Position(357,576)));
        assertTrue(pos_3.equals(new Position(6,13)));
    }

    @Test
    void unitConverter() {
        Position pos_1 = new Position(137, 342);  // will be 1 and 2 in board units
        Position pos_2 = new Position(458, 129);  // will be 3 and 0 in board units
        Position pos_3 = new Position(499, 563);  // will be 3 and 4 in board units

        assertTrue(pos_1.unitConverter().equals(new Position(1, 2)));
        assertTrue(pos_2.unitConverter().equals(new Position(3, 0)));
        assertTrue(pos_3.unitConverter().equals(new Position(3, 4)));
    }

    @Test
    void pixelConverter() {
        Position pos_1 = new Position(3, 2);  // will be 390 and 260 in pixels
        Position pos_2 = new Position(2, 4);  // will be 260 and 520 in pixel
        Position pos_3 = new Position(1, 0);  // will be 130 and 0 in pixels

        assertTrue(pos_1.pixelConverter().equals(new Position(390, 260)));
        assertTrue(pos_2.pixelConverter().equals(new Position(260, 520)));
        assertTrue(pos_3.pixelConverter().equals(new Position(130, 0)));
    }

    @Test
    void testToString() {
        Position pos_1 = new Position(136,78);
        Position pos_2 = new Position(357,576);
        Position pos_3 = new Position(6,13);

        assertEquals("[136,78]", pos_1.toString());
        assertEquals("[357,576]", pos_2.toString());
        assertEquals("[6,13]", pos_3.toString());
    }
}