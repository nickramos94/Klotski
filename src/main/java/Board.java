import java.util.List;

public class Board {
    
    private Piece[] pieces;
    private Piece selected_piece;
    private int selected_index;
    private int special_index;  // to have the special piece (square 2*2) index
    private boolean hasWon;
    private int moves;
    private int configuration;
    final static int WIDTH = 4;
    final static int HEIGHT = 5;
    private final static int PIECES_NUMBER = 10;

    public Board() {
        pieces = new Piece[PIECES_NUMBER];
        selected_piece = null;
        selected_index = -1;
        hasWon = false;
        moves = 0;
    }

    public  Board(Piece[] pieces) {
        this();
        for(int i=0; i < PIECES_NUMBER; i++) {
            this.pieces[i] = new Piece(pieces[i]);
            trySetSpecial(i);
        }
    }

    public Board(List<int[]> pieces) {
        this();
        int index = 0;
        for(int[] piece : pieces) {
            this.pieces[index] = new Piece(piece[0], piece[1], piece[2], piece[3]);
            trySetSpecial(index);
            index++;
        }
    }

    public Board(List<int[]> pieces, int move_num)
    {
        this(pieces);
        moves = move_num;
    }

    public Board(int config)
    {
        this();
        configuration = config;
        if (configuration == 1) {
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
        else if(configuration == 2) {       // TEMPORARY ADDITION: second configuration
                                            // to try level selection and win condition
            pieces[0] = new Piece(1, 2, 2, 2);   //2x2 square
            pieces[1] = new Piece(0, 0, 1, 2);   //1x2 rectangle
            pieces[2] = new Piece(3, 0, 1, 2);   //1x2 rectangle
            pieces[3] = new Piece(0, 2, 1, 2);   //1x2 rectangle
            pieces[4] = new Piece(3, 2, 1, 2);   //1x2 rectangle
            pieces[5] = new Piece(1, 0, 1, 1);   //1x1 square
            pieces[6] = new Piece(2, 0, 1, 1);   //1x1 square
            pieces[7] = new Piece(1, 1, 1, 1);   //1x1 square
            pieces[8] = new Piece(2, 1, 1, 1);   //1x1 square
            pieces[9] = new Piece(1, 4, 1, 1);   //1x1 square
        }
        special_index = 0;
    }

    public boolean selectPiece(Position pos)
    {
        for(int i=0; i<PIECES_NUMBER; i++)
        {
            if(pieces[i].contains(pos.x,pos.y))
            {
                selectPiece(i);
                return true;
            }
        }
        selected_piece = null;
        return false;
    }

    public void selectPiece(int piece_index)
    {
        selected_piece = pieces[piece_index];
        selected_index = piece_index;
    }

    public boolean movePiece(int direction)
    {
        if(selected_piece == null || direction == -1)   //empty space selection and no movement cases
            return false;

        if(direction>3 || direction <0)
            throw new IllegalArgumentException("Invalid direction");

        if(out_of_bounds(direction)) //this method checks if the movement would make the piece go out of bounds and...
            return false;
        int x = selected_piece.getX();     //using simplified variable's names
        int y = selected_piece.getY();
        int w = selected_piece.width;
        int h = selected_piece.height;

        //...the rest of the code controls potential overlap
        int i;
        if(direction == 0) //down
        {
            for(i=x; i<=(x+w-1); i++)  //scrolling the piece horizontally
            {
                if(isOccupied(i,y+h))  //and controlling if the part under the piece (i;y+h) is free
                    return false;
            }
        } else if(direction == 1) //right
        {
            for(i=y; i<=(y+h-1); i++)  //scrolling the piece vertically
            {
                if(isOccupied((x+w),i))  //and controlling if the part on it's right(x+w;i) is free
                    return false;
            }
        } else if(direction == 2) //up
        {
            for(i=x; i<=(x+w-1); i++)  //scrolling the piece horizontally
            {
                if(isOccupied(i,y-1)) //and controlling if the part over it (i;y-1) is free
                    return false;
            }
        } else if(direction == 3) //left
        {
            for(i=y; i<=(y+h-1); i++)  //scrolling the piece vertically
            {
                if(isOccupied((x-1),i))  //and controlling if the part on it's left(x-1;i) is free
                    return false;
            }
        }

        selected_piece.move(direction);
        moves++;
        if(pieces[0].getX() == 1 && pieces[0].getY() == 3) //checking if the big square is on the bottom middle
        {
            hasWon = true;
        }
        return true;
    }

    public boolean invertedMove(Move move) //method called by Game.undo(). invertedMove receives the move that has to be repeated in the opposite direction
    {
        if(moves<1)
            return false;
        selected_piece = pieces[move.getBlockIdx()];
        boolean b = this.movePiece(invertedDirection(move.getDirIdx()));
        if(b)
        {
            moves = move.getStep();
        }
        return b;
    }

    private int invertedDirection(int direction)
    {
        if (direction == 0 || direction == 1)
            return direction+2;
        else return direction-2;
    }

    private boolean out_of_bounds(int dir)  //called by Board.movePiece(). Checks only if the movement would make the piece go out of the Board
    {
        boolean out_of_bounds = false;
        if(dir==0 && (selected_piece.getY() + selected_piece.height) == HEIGHT)
            out_of_bounds = true;
        if(dir==1 && (selected_piece.getX() + selected_piece.width) == WIDTH)
            out_of_bounds = true;
        if(dir==2 && selected_piece.getY()==0)
            out_of_bounds = true;
        if(dir==3 && selected_piece.getX()==0)
            out_of_bounds = true;
        return out_of_bounds;
    }

    private boolean isOccupied(int x, int y)
    {
        for(int i=0; i<PIECES_NUMBER; i++)
        {
            if(pieces[i].contains(x,y))
                return true;
        }
        return false;
    }

    private void trySetSpecial(int index) {
        if(pieces[index].width == 2 && pieces[index].height == 2) {
            special_index = index;
        }
    }

    public boolean isSpecial() {
        return this.isSpecial(selected_index);
    }
    public boolean isSpecial(int index) {
        return index == special_index;
    }

    public boolean checkWin()
    {
        return hasWon;
    }

    public void resetWin() {
        hasWon = false;
    }

    public Position getSelectedPos()
    {
        if(selected_piece == null)
            return null;
        return new Position(selected_piece.getX(), selected_piece.getY());
    }

    public int getSelectedIndex() {
        return selected_index;
    }

    public Piece[] getPieces()
    {
        return pieces;
    }

    public int getMoves()
    {
        return moves;
    }

    public boolean equals(Board other_board) {
        if(other_board == null)
            return false;
        for(int i=0; i < PIECES_NUMBER; i++) {
            if(!pieces[i].equals(other_board.getPieces()[i]))
                return false;
        }
        return true;
    }
}

