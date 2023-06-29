import org.junit.jupiter.api.*;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;


class BoardTest {
    static Piece[] pieces = new Piece[10];
    @BeforeAll
    static void setUp(){
        System.out.println("Creating level 1");
        pieces[0] = new Piece(1, 0, 2, 2);   //2x2 square
        pieces[1] = new Piece(0, 0, 1, 2);   //1x2 rectangle
        pieces[2] = new Piece(3, 0, 1, 2);   //1x2 rectangle
        pieces[3] = new Piece(0, 2, 1, 2);   //1x2 rectangle
        pieces[4] = new Piece(3, 2, 1, 2);   //1x2 rectangle
        pieces[5] = new Piece(1, 2, 1, 1);   //1x1 square
        pieces[6] = new Piece(2, 2, 1, 1);   //1x1 square
        pieces[7] = new Piece(1, 3, 1, 1);   //1x1 square
        pieces[8] = new Piece(2, 3, 1, 1);   //1x1 square
        pieces[9] = new Piece(1, 4, 2, 1);   //2x1 rectangle

    }
    @AfterAll
    static void tearDown(){
        System.out.println("Clearing Piece[] pieces");
        Arrays.fill(pieces,null);
    }

    @Test
    void Boolean_selectPiece() {
        Board board = new Board(pieces); //LIVELLO 1
        //select the big square clicking on its bottom left (1,1). Then verify that the selected's position is big square's position (1,0)
        assertTrue(board.selectPiece(new Position(1,1)) && board.getSelectedPos().equals(new Position(1,0)));
    }

    @Test
    void Void_selectPiece() {
        Board board = new Board(pieces); //LIVELLO 1
        board.selectPiece(0); // select big square
        assertEquals(0, board.getSelectedIndex());
        for (int i=1; i<10 ; i++)
            assertNotEquals(i, board.getSelectedIndex());
        assertTrue((new Position(1,0)).equals(board.getSelectedPos())); // selected piece's position == (1,0)
    }

    @Test
    void movePiece() {
        Board board = new Board(pieces); //LIVELLO 1
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
        Board board = new Board(pieces); //LIVELLO 1
        board.selectPiece(9); //rectangle at the bottom (free to move left and right)
        board.movePiece(1);
        assertEquals(1,board.getMoves());
        Move move = new Move (0,9,1); //step=0 , block index=9, direction=1
        assertTrue(board.invertedMove(move));  // same as selected=piece[9] then movePiece(3).
        assertEquals(0,board.getMoves()); //check if moves went from 1 to 0
        assertTrue(board.equals(new Board(pieces))); //check if the Board returned to the past configuration, testing also equals() method //LIVELLO 1
        board = new Board();
        assertFalse(board.invertedMove(move));
    }

    @Test
    void isSpecial(){                                //there's isSpecial()  and isSpecial(int intex)
        Board board = new Board();
        board.randomize();
        assertTrue(board.isSpecial(0));
        for (int i=1; i<10 ; i++)
            assertFalse(board.isSpecial(i));
        board.selectPiece(0);
        assertTrue(board.isSpecial());
    }

    @Test
    void checkWin() {
        //creating an easy configuration to solve, with the big square and 9 1x1 squares
        Piece[] pieces_easy = new Piece[10];
        pieces_easy[0] = new Piece(0,3,2,2); //big square on the bottom left
        pieces_easy[1] = new Piece(0,0,1,1);
        pieces_easy[2] = new Piece(1,0,1,1);
        pieces_easy[3] = new Piece(2,0,1,1);
        pieces_easy[4] = new Piece(3,0,1,1);
        pieces_easy[5] = new Piece(0,1,1,1);
        pieces_easy[6] = new Piece(1,1,1,1);
        pieces_easy[7] = new Piece(2,1,1,1);
        pieces_easy[8] = new Piece(3,1,1,1);
        pieces_easy[9] = new Piece(3,4,1,1); //first two rows and the bottom right corner are now occupied by 1*1 squares
        Board board = new Board(pieces_easy);
        assertFalse(board.checkWin());
        board.selectPiece(0); //select the big square
        board.movePiece(1); //and move it right
        assertTrue(board.checkWin());
        board.resetWin(); //let's also test the resetWin() method
        assertFalse(board.checkWin());
    }
}