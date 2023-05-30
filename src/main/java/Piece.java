public class Piece {
    Position position;
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
            throw new IllegalArgumentException("Forma o posizione del pezzo non valida");
    }

    public Piece(Piece piece) {
        this(piece.getX(), piece.getY(), piece.width, piece.height);
    }

    private boolean is_legal(int x, int y, int w, int h) //METODO INUTILE? visto che siamo noi in Board() a creare i pezzi e sicuramente non sbagliamo
    {
        if(w==1 && h==1) //caso quadrato 1x1
        {
            if(x>=0 && x<Board.WIDTH && y>=0 && y<Board.HEIGHT)
                return true;
        }
        if(w==2 && h==2) //caso quadrato 2x2
        {
            if(x>=0 && x<(Board.WIDTH-1) && y>=0 && y<(Board.HEIGHT-1))
                return true;
        }
        if(w==1 && h==2) //caso rettangolo 1x2
        {
            if(x>=0 && x<Board.WIDTH && y>=0 && y<(Board.HEIGHT-1))
                return true;
        }
        if(w==2 && h==1) //caso rettangolo 2x1
        {
            if(x>=0 && x<(Board.WIDTH-1) && y>=0 && y<Board.HEIGHT)
                return true;
        }
        return false; //tutti gli altri casi non sono validi (o per posizioni non valide oppure per forme/dimensioni non valide)
    }

    public boolean contains(int x, int y)    //verifico se questo Piece contiene il punto (x;y)
    {
        return (x >= position.x && y >= position.y &&
                x < (position.x + this.width) && y < (position.y + this.height));
    }

    public void move(int direction)  //funzione chiamata da movePiece() di Board, dove sara fatto anche il controllo della validita del valore di direction
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