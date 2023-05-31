public class Piece {
    Position position; // coordinates of the top left part of the piece.
    int width;
    int height;

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

    public Piece(Piece piece) {
        this(piece.getX(), piece.getY(), piece.width, piece.height);
    }
    public Piece()
    {
        position = null;
        width = 0;
        height = 0;
    }

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
        return false; //tutti gli altri casi non sono validi (o per posizioni non valide oppure per forme/dimensioni non valide)
    }

    public boolean contains(int x, int y)    //verify if this piece occupies (x;y). Method called by Board.isOccupied(int x, int y)
    {
        return (x >= position.x && y >= position.y &&
                x < (position.x + this.width) && y < (position.y + this.height));
    }

    public void move(int direction)  //method called by Board.movePiece(int direction), which controls if the direction is valid and if the movement is possible
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
    public int getX()
    {
        return position.x;
    }
    public int getY()
    {
        return position.y;
    }

    public int[] getProperties()
    {
        return new int[]{position.x, position.y, width, height};
    }

    public boolean equals(Piece other_piece) {
        if (other_piece == null)
        return false;
        return position.equals(other_piece.position) &&
                width == other_piece.width &&
                height == other_piece.height;
    }
}