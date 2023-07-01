/**
 * Implements the abstraction of a piece on the Board
 */
public class Piece {
    Position position; // coordinates of the top left part of the piece.
    int width;
    int height;

    /**
     * Generates a piece according the specified size and position
     * @param x x coordinate
     * @param y y coordinate
     * @param w width of the piece
     * @param h height of the piece
     */
    public Piece(int x, int y, int w, int h)
    {
        if (is_legal(x,y,w,h))
        {
            position = new Position(x,y);
            width = w;
            height = h;
        }
        else
            throw new IllegalArgumentException("Invalid form or position");
    }

    /** Generates a piece identical to another one
     * @param piece a piece
     */
    public Piece(Piece piece) {
        this(piece.getX(), piece.getY(), piece.width, piece.height);
    }

    /**
     * Generates an empty piece
     */
    public Piece()
    {
        position = null;
        width = 0;
        height = 0;
    }

    /**
     *  Checks if the Piece's Position is valid according to its shape
     * @param x x coordinate
     * @param y y coordinate
     * @param w width of the piece
     * @param h height of the piece
     * @return true if valid
     */
    private boolean is_legal(int x, int y, int w, int h)
    {
        if(w==1 && h==1) //1x1 square
        {
            if(x>=0 && x<Board.WIDTH && y>=0 && y<Board.HEIGHT)
                return true;
        }
        if(w==2 && h==2) //2x2 square
        {
            if(x>=0 && x<(Board.WIDTH-1) && y>=0 && y<(Board.HEIGHT-1))
                return true;
        }
        if(w==1 && h==2) //1x2 rectangle
        {
            if(x>=0 && x<Board.WIDTH && y>=0 && y<(Board.HEIGHT-1))
                return true;
        }
        if(w==2 && h==1) //2x1 rectangle
        {
            if(x>=0 && x<(Board.WIDTH-1) && y>=0 && y<Board.HEIGHT)
                return true;
        }
        return false; //every other case in not valid (invalid positions or invalid dimensions)
    }

    /** verify if this piece occupies (x;y).
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the coordinates correspond
     */
    public boolean contains(int x, int y)   // Method called by Board.isOccupied(int x, int y)
    {
        return (x >= position.x && y >= position.y &&
                x < (position.x + this.width) && y < (position.y + this.height));
    }

    /** moves the Piece
     * @param direction int that speficies the direction (0 = down, 1 = right, 2 = up, 3 = left)
     */
    public void move(int direction)  //method called by Board.movePiece(int direction) which will check if the move is possible
    {
        if (direction == 0) // down
            position.y++;
        else if (direction == 1) // right
            position.x++;
        else if (direction == 2) // up
            position.y--;
        else if (direction == 3) // left
            position.x--;
    }

    /** Get the X value of a piece's position
     * @return  X coordinate
     */
    public int getX()
    {
        return position.x;
    }

    /** Get the Y value of a piece's position
     * @return  Y coordinate
     */
    public int getY()
    {
        return position.y;
    }

    /** Get a Piece's attributes
     * @return an array of ints containing the attributes
     */
    public int[] getProperties()
    {
        return new int[]{position.x, position.y, width, height};
    }

    /** Checks if a piece is equal to the piece passed as argument
     * @param other_piece Piece to compare
     * @return true if they're equivalent
     */
    public boolean equals(Piece other_piece) {
        if (other_piece == null)
        return false;
        return position.equals(other_piece.position) &&
                width == other_piece.width &&
                height == other_piece.height;
    }
}