public class Piece {
    //variabili protected e non private perche usate in Board
    protected int x;  //per ora non uso class Position
    protected int y;  //per ora non uso class Position
    protected int width;
    protected int height;

    public Piece(int x, int y, int width, int height)
    {
        if (is_legal(x,y,width,height))  //il controllo della posizione non posso farlo in Position, in quanto la validita della posizione dipende dalla forma
        {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        else
            throw new IllegalArgumentException();
    }

    private boolean is_legal(int x, int y, int w, int h) //METODO INUTILE? visto che siamo noi in Board a creare i pezzi e sicuramente non sbaglio
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
        return (x >= this.x && y >= this.y &&
                x < (this.x + this.width) && y < (this.y + this.height));
    }

    public void move(int direction)  //funzione chiamata da movePiece() di Board, dove sara fatto anche il controllo della validita del valore di direction
    {
        if (direction == 0) // up
            y--;
        else if (direction == 1) // right
            x++;
        else if (direction == 2) // down
            y++;
        else if (direction == 3) // left
            x--;
    }

    public int[] getProperties()
    {
        int[] properties = {x, y, width, height};
        return properties;
    }

}