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
        board.selectPiece(9); //rectangle at the bottom (free to move left and right)
        assertTrue(board.movePiece(1)); //right. Now the rectangle can move left twice
        assertTrue(board.movePiece(3));
        assertTrue(board.movePiece(3));
        assertFalse(board.movePiece(3)); //can't move left anymore
        assertEquals(3,board.getMoves()); //test getMoves() method. We did 3 valid moves so we expect it returns 3
        Board board2 = new Board(); // selected == null
        assertFalse(board2.movePiece(2));
    }

    @Test
    void invertedMove() {
        Board board = new Board(1);
        board.selectPiece(9); //rectangle at the bottom (free to move left and right)
        board.movePiece(1);
        assertEquals(1,board.getMoves());
        Move move = new Move (0,9,1); //step=0 , block index=9, direction=1
        assertTrue(board.invertedMove(move));  // same as selected=piece[9] then movePiece(3).
        assertEquals(0,board.getMoves()); //check if moves went from 1 to 0
        assertTrue(board.equals(new Board(1))); //check if the Board returned to the past configuration testing also equals() method
        board = new Board();
        assertFalse(board.invertedMove(move));
    }

    @Test
    void checkWin() {
        //creating an easy configuration to solve, with the big square and 9 1x1 squares
        Piece[] pieces = new Piece[10];
        pieces[0] = new Piece(0,3,2,2); //big square on the bottom left
        pieces[1] = new Piece(0,0,1,1);
        pieces[2] = new Piece(1,0,1,1);
        pieces[3] = new Piece(2,0,1,1);
        pieces[4] = new Piece(3,0,1,1);
        pieces[5] = new Piece(0,1,1,1);
        pieces[6] = new Piece(1,1,1,1);
        pieces[7] = new Piece(2,1,1,1);
        pieces[8] = new Piece(3,1,1,1);
        pieces[9] = new Piece(3,4,1,1); //first two rows and the bottom right corner are now occupied by 1*1 squares
        Board board = new Board(pieces);
        assertFalse(board.checkWin());
        board.selectPiece(0); //select the big square
        board.movePiece(1); //and move it right
        assertTrue(board.checkWin());
        board.resetWin(); //let's also test the resetWin() method
        assertFalse(board.checkWin());
    }
}