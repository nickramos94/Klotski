import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void setBoard() {
        Game game = new Game();
        int level_number = 1;
        Piece[] pieces = new Piece[10];
        // level 1 pieces
        pieces[0] = new Piece(1, 0, 2, 2);
        pieces[1] = new Piece(0, 0, 1, 1);
        pieces[2] = new Piece(3, 0, 1, 1);
        pieces[3] = new Piece(0, 2, 1, 2);
        pieces[4] = new Piece(3, 2, 1, 2);
        pieces[5] = new Piece(1, 2, 2, 1);
        pieces[6] = new Piece(0, 1, 1, 1);
        pieces[7] = new Piece(1, 3, 1, 2);
        pieces[8] = new Piece(2, 3, 1, 2);
        pieces[9] = new Piece(3, 1, 1, 1);

        Board board = new Board(pieces);

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
        Board initial_board = game.getBoard();

        // set some legal moves to do with the level 1
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(1, 3, 2));
        moves.add(new Move(2, 4, 2));
        moves.add(new Move(3, 5, 3));
        moves.add(new Move(4, 1, 1));

        // make moves to change position of pieces on the board
        game.makeMoves(moves);

        // reset the game
        game.reset();

        assertTrue(initial_board.equals(game.getBoard()));   // the board pieces will be the same as before
        assertEquals(0, game.getBoard().getMoves());    // moves will be 0
        assertTrue(game.getLog().isEmpty());    // the log of the moves will be empty
    }

    @Test
    void save_loadState() {
        Game game = new Game();
        game.setBoard(1);

        // set some legal moves to do with the level 1
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(1, 3, 0));
        moves.add(new Move(2, 4, 0));
        moves.add(new Move(3, 5, 1));
        moves.add(new Move(4, 6, 0));
        moves.add(new Move(5, 6, 1));
        moves.add(new Move(6, 1, 0));


        // save the number of moves that will be done
        int moves_num = moves.size();

        // make moves to change position of pieces on the board
        game.makeMoves(moves);

        Board temp_board = game.getBoard();

        game.saveState("save_test-Game.json");  // the game is saved
        game.reset();                               // resetted
        game.loadState("save_test-Game.json");  // then the previous game is loaded
        assertTrue(temp_board.equals(game.getBoard()));  // the board will be the same as before
        assertEquals(moves_num, game.getBoard().getMoves());    // the moves too
    }

    @Test
    void undo() {
        Game game = new Game();
        game.setBoard(1);

        // set some legal moves to do with the level 1
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(1, 3, 0));
        moves.add(new Move(2, 4, 0));
        moves.add(new Move(3, 5, 1));
        moves.add(new Move(4, 6, 0));

        // save the number of moves done
        int moves_num = moves.size();

        // make moves to change position of pieces on the board
        game.makeMoves(moves);

        Board temp_board = game.getBoard();

        // set other 2 moves
        moves.add(new Move(5, 6, 1));
        moves.add(new Move(6, 1, 0));
        // and make them
        game.makeMoves(moves);

        // return back with 2 undo
        game.undo();
        game.undo();

        assertTrue(temp_board.equals(game.getBoard()));  // the board will be the same as before the moves
        assertEquals(moves_num, game.getBoard().getMoves());    // the number of moves too
    }
}