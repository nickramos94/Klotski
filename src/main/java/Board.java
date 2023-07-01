import java.util.List;

/**
 * Implements the astraction of the checkerboard
 */
public class Board {

    private Piece[] pieces;
    private Piece selected_piece;
    private int selected_index;
    private int special_index;  // special piece (2*2 square) index
    private boolean hasWon;
    private int moves;
    final static int WIDTH = 4;
    final static int HEIGHT = 5;
    private final static int PIECES_NUMBER = 10;

    /**
     * Creates an empty board
     */
    public Board() {
        pieces = new Piece[PIECES_NUMBER];
        selected_piece = null;
        selected_index = -1;
        hasWon = false;
        moves = 0;
    }

    /** Builds a board with pieces on it
     * @param pieces array of pieces with their positions
     */
    public  Board(Piece[] pieces) {
        this();
        for(int i=0; i < PIECES_NUMBER; i++) {
            this.pieces[i] = new Piece(pieces[i]);
            trySetSpecial(i);
        }
    }

    /** Builds a board with pieces on it
     * @param pieces List of array of ints containing the size and position of the pieces
     */
    public Board(List<int[]> pieces) {
        this();
        int index = 0;
        for(int[] piece : pieces) {
            this.pieces[index] = new Piece(piece[0], piece[1], piece[2], piece[3]);
            trySetSpecial(index);
            index++;
        }
    }

    /** Builds a board with pieces on it
     * @param pieces List of array of ints containing the size and position of the pieces
     * @param move_num Number of moves performed
     */
    public Board(List<int[]> pieces, int move_num)
    {
        this(pieces);
        moves = move_num;
    }

    /**
     * Generates a board of randomly placed pieces
     */
    public void randomize()
    {
        int x, y, w, h;
        do {
            //creating big square's coordinates
            x = (int) Math.round(Math.random()*(WIDTH-2));       //big square's x, can have values from 0 to 2
            y = (int) Math.round(Math.random()*(HEIGHT-2));     //big square's y, can have values from 0 to 3
        }while (x==1 && y==3);                                  //if the 2x2 square is going to be in the winning position, change its coordinates

        pieces[0] = new Piece(x,y,2,2);              //the 2x2 square must always exist, but the position is random
        trySetSpecial(0);
        int rectangles_counter = 0;                      //there can't be more than 5 rectangles
        int pieces_counter = 1;
        while(pieces_counter<10)
        {
            if(rectangles_counter==5)      //if there are already 5 rectangles we can only generate 1x1 squares till the end
            {
                w = 1;
                h = 1;
            }
            else
            {
                w = (int) Math.round(Math.random() + 1);   //width = 1 or 2
                if(w==2){
                    h = 1;                                //if w == 2, height must me 1 (can't have more 2*2 squares)
                }
                else {
                    h = (int) Math.round(Math.random() + 1);        //1 or 2
                }
            }

            x = (int) Math.round(Math.random()*WIDTH-1);   //0 to 4
            y = (int) Math.round(Math.random()*HEIGHT-1);  //0 to 5

            if(!isOccupied(x,y) && !isOccupied(x+w-1,y) && !isOccupied(x,y+h-1) )  //we can only create the piece if all the position that it's going to occupy are free
            {
                try{
                    pieces[pieces_counter] = new Piece(x,y,w,h); //error if out of bounds (positions out of bounds are free so they pass the isOccupied() check, must manage them here)
                    pieces_counter++;
                    if(w==2 || h==2)
                        rectangles_counter++;
                }
                catch(IllegalArgumentException e)
                {}
            }


        }

    }

    /** Select a piece on the board based on its position
     * @param pos Position of the piece
     * @return a boolean that tells whether a piece containing that Position has been found or not
     */
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

    /** Select a piece on the board based on its index
     * @param piece_index piece's index
     */
    public void selectPiece(int piece_index)
    {
        selected_piece = pieces[piece_index];
        selected_index = piece_index;
    }

    /** Moves a piece on the board
     * @param direction int that speficies the direction (0 = down, 1 = right, 2 = up, 3 = left)
     * @return if false the piece cannot be moved because the space is occupied by another piece or it's going through the edge of the board
     */
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

    /** Receives a move that has to be repeated in the opposite direction
     * @param move
     * @return
     */
    public boolean invertedMove(Move move) //method called by Game.undo(). invertedMove
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

    /** Utilized by invertedMove, the piece goes in the opposite direction of the move that is being inverted
     * @param direction
     * @return returns the direction that needs to be performed
     */
    private int invertedDirection(int direction)
    {
        if (direction == 0 || direction == 1)
            return direction+2;
        else return direction-2;
    }

    /** Checks if a movement would make the piece go out of the Board
     * @param dir direction that the piece is about to do
     * @return if true the move will put the piece out bounds
     */
    private boolean out_of_bounds(int dir)  //called by Board.movePiece().
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

    /** Checks whether a space in the board is occupied by a piece
     * @param x position x
     * @param y position y
     * @return if true the space is occupied
     */
    private boolean isOccupied(int x, int y)
    {
        for(int i=0; i<PIECES_NUMBER; i++)
        {
            if(pieces[i]!=null && pieces[i].contains(x,y))
                return true;
        }
        return false;
    }

    /**
     * @param index
     */
    private void trySetSpecial(int index) {
        if(pieces[index].width == 2 && pieces[index].height == 2) {
            special_index = index;
        }
    }

    /**
     * @return
     */
    public boolean isSpecial() {
        return this.isSpecial(selected_index);
    }

    /**
     * @param index
     * @return
     */
    public boolean isSpecial(int index) {
        return index == special_index;
    }

    /** Tells that the game has been won
     * @return a true boolean
     */
    public boolean checkWin()
    {
        return hasWon;
    }

    /** Turns the hasWon variable back to false
     *
     */
    public void resetWin() {
        hasWon = false;
    }

    /** Returns the position of the selected piece
     * @return position of the selected piece
     */
    public Position getSelectedPos()
    {
        if(selected_piece == null)
            return null;
        return new Position(selected_piece.getX(), selected_piece.getY());
    }

    /** Returns the index of the selected piece
     * @return index of the piece
     */
    public int getSelectedIndex() {
        return selected_index;
    }

    /** Returns all the pieces of the board
     * @return array of the pieces
     */
    public Piece[] getPieces()
    {
        return pieces;
    }

    /** Returns the number of moves that have been performed so far
     * @return int of moves
     */
    public int getMoves()
    {
        return moves;
    }

    /** Checks whether the current board is identical to another board
     * @param other_board board to be compared
     * @return true is they are identical
     */
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

