public class Piece {
    private int x;  //per ora non uso class Position
    private int y;  //per ora non uso class Position
    private int width;
    private int height;

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

    private boolean is_legal(int x, int y, int w, int h) //si considera (0;0) il punto in alto a sinistra della board, (3;4) quello in basso a destra
    {
        if(w==1 && h==1) //caso quadrato 1x1
        {
            if(x>=0 || x<=3 || y>=0 || y<=3)
                return true;
        }
        if(w==2 && h==2) //caso quadrato 2x2
        {
            if(x>=0 || x<=2 || y>=0 || y<=3)
                return true;
        }
        if(w==1 && h==2) //caso rettangolo 1x2
        {
            if(x>=0 || x<=3 || y>=0 || y<=3)
                return true;
        }
        return false; //tutti gli altri casi non sono validi (o per posizioni non valide oppure per forme/dimensioni non valide)
    }

    public boolean isAbove(Piece p)     //TODO
    {
        return true;      //ancora da implementare
    }

    public int[] getProperties()
    {
        int[] properties = {x, y, width, height};
        return properties;
    }

}