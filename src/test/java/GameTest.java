import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void setBoard() {
        Game game = new Game();
        int level_number = 1;
        Board board = new Board(level_number);

        game.setBoard(level_number);

        assertTrue(board.equals(game.getBoard()));
    }

    @Test
    void move() {
        Game game = new Game();
        game.setBoard(1);
        int index_cannot_move = 2;
        int index_can_move = 4;
        int down_direction = 0;

        // piece that can NOT move down
        Piece initial_piece = new Piece(game.getBoard().getPieces()[index_cannot_move]);
        Position initial_window_pos = new Position(game.getPiece(index_cannot_move).getLocation());
        game.selectPiece(index_cannot_move);
        game.move(down_direction);
        Piece final_piece = new Piece(game.getBoard().getPieces()[index_cannot_move]);
        Position final_window_pos = new Position(game.getPiece(index_cannot_move).getLocation());

        assertEquals(initial_piece.getY(), final_piece.getY());   // y value will be the same
        assertEquals(0, game.getLog().getStep());   // the move isn't added to the log move
        assertEquals(initial_window_pos.y, final_window_pos.y); // the piece hasn't moved in the window

        // piece that can move down
        initial_piece = new Piece(game.getBoard().getPieces()[index_can_move]);
        initial_window_pos = new Position(game.getPiece(index_can_move).getLocation());
        game.selectPiece(index_can_move);
        game.move(down_direction);
        final_piece = new Piece(game.getBoard().getPieces()[index_can_move]);
        final_window_pos = new Position(game.getPiece(index_can_move).getLocation());

        assertEquals(initial_piece.getY() + 1, final_piece.getY());  // y value will be greater than 1
        assertEquals(1, game.getLog().getStep());   // the move is added to the log move
        assertEquals(initial_window_pos.y + Window.BLOCK_SIZE, final_window_pos.y); // the piece has moved in the window
    }

    @Test
    void reset() {
        Game game = new Game();
        game.setBoard(1);

        // make some moves to change position of pieces on the board
        game.makeMoves(10);

        // reset the game
        game.reset();

        assertTrue(game.getBoard().equals(new Board(1)));   // the board pieces will be the same as before
        assertEquals(0, game.getBoard().getMoves());    // moves will be 0
        assertTrue(game.getLog().isEmpty());    // the log of the moves will be empty
    }

    @Test
    void save_loadState() {
        Game game = new Game();
        game.setBoard(1);
        int moves_num = 10;

        // make some moves to change position of pieces on the board
        game.makeMoves(moves_num);

        Board initial_board = game.getBoard();

        game.saveState("save_test-Game.json");  // the game is saved
        game.reset();                               // resetted
        game.loadState("save_test-Game.json");  // then the previous game is loaded
        assertTrue(initial_board.equals(game.getBoard()));  // the board will be the same as before
        assertEquals(moves_num, game.getBoard().getMoves());    // the moves too
    }

    @Test
    void undo() {
    }

    @Test
    void solveAll() {
    }

    @Test
    void bestMove() {
    }

    @Test
    void checkWin() {
    }
}