import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void Boolean_selectPiece() {
        Board board = new Board(1);
        //select the big square clicking on its bottom left (1,1). Then verify that the selected's position is big square's position (1,0)
        assertTrue(board.selectPiece(new Position(1,1)) && board.getSelectedPiece().equals(new Position(1,0)));
    }

    @Test
    void Void_selectPiece() {
        Board board = new Board(1);
        board.selectPiece(0); // select big square
        assertEquals(0, board.getSelectedIndex());
        assertTrue((new Position(1,0)).equals(board.getSelectedPiece())); // selected piece's position == (1,0)
    }

    @Test
    void movePiece() {
        Board board = new Board(1);
        board.selectPiece(0);
        assertFalse(board.movePiece(0) && board.movePiece(2)); //impossible to move the big square
        assertFalse(board.movePiece(1) && board.movePiece(3)); //impossible to move the big square
        assertThrows(IllegalArgumentException.class, ()->{boolean b = board.movePiece(5);});
        board.selectPiece(new Position(2,4)); //rectangle at the bottom (free to move left and right)
        assertTrue(board.movePiece(1)); //right. Now the rectangle can move left twice
        assertTrue(board.movePiece(3));
        assertTrue(board.movePiece(3));
        assertFalse(board.movePiece(3)); //can't move left anymore
        Board board2 = new Board(); // selected == null
        assertFalse(board2.movePiece(2));


    }

    @Test
    void invertedMove() {
    }

    @Test
    void checkWin() {
    }

    @Test
    void resetWin() {
    }

    @Test
    void getSelectedPiece() {
    }

    @Test
    void getSelectedIndex() {
    }

    @Test
    void getPieces() {
    }

    @Test
    void getPiece() {
    }

    @Test
    void getMoves() {
    }

    @Test
    void equals() {
    }
}