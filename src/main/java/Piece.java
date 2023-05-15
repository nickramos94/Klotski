public class Piece {
    Position position;
    int width;
    int height;

    public Piece(int x, int y, int w, int h)
    {
        if (is_legal(x,y,w,h))  //il controllo della posizione non posso farlo in Position, in quanto la validita della posizione dipende dalla forma
        {
            position = new Position(x,y);
            width = w;
            height = h;
        }
        else
            throw new IllegalArgumentException("Forma o posizione del pezzo non valida");
    }

    private boolean is_legal(int x, int y, int w, int h) //METODO INUTILE? visto che siamo noi in Board() a creare i pezzi e sicuramente non sbagliamo
    {
        if(w==1 && h==1) //caso quadrato 1x1
        {
            if(x>=0 && x<=3 && y>=0 && y<=4)
                return true;
        }
        if(w==2 && h==2) //caso quadrato 2x2
        {
            if(x>=0 && x<=2 && y>=0 && y<=3)
                return true;
        }
        if(w==1 && h==2) //caso rettangolo 1x2
        {
            if(x>=0 && x<=3 && y>=0 && y<=3)
                return true;
        }
        if(w==2 && h==1) //caso rettangolo 2x1
        {
            if(x>=0 && x<=2 && y>=0 && y<=4)
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
        if (direction == 0) // up
            position.y--;
        else if (direction == 1) // right
            position.x++;
        else if (direction == 2) // down
            position.y++;
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

}