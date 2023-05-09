public class Board {
    private Piece[] pieces;
    private Piece selected_piece;
    private boolean hasWon;

    public Board(int configuration)
    {
        pieces = new Piece[10];
        selected_piece = null;
        hasWon = false;
        if (configuration == 1) {
            pieces[0] = new Piece(1, 0, 2, 2);   //quadrato 2x2
            pieces[1] = new Piece(0, 0, 1, 2);   //rettangolo 1x2
            pieces[2] = new Piece(3, 0, 1, 2);   //rettangolo 1x2
            pieces[3] = new Piece(0, 2, 1, 2);   //rettangolo 1x2
            pieces[4] = new Piece(3, 2, 1, 2);   //rettangolo 1x2
            pieces[5] = new Piece(1, 2, 1, 1);   //quadrato 1x1
            pieces[6] = new Piece(2, 2, 1, 1);   //quadrato 1x1
            pieces[7] = new Piece(1, 3, 1, 1);   //quadrato 1x1
            pieces[8] = new Piece(2, 3, 1, 1);   //quadrato 1x1
            pieces[9] = new Piece(1, 4, 2, 1);   //rettangolo 2x1
        }
    }

    public Piece getPiece(int x, int y) //METODO INUTILE ?
    {
        for(int i=0; i<10; i++)
        {
            if(pieces[i].contains(x,y))
            {
                return pieces[i];
            }
        }
        return null;
    }

    public boolean selectPiece(int x, int y)
    {
        for(int i=0; i<10; i++)
        {
            if(pieces[i].contains(x,y))
            {
                selected_piece = pieces[i];
                return true;
            }
        }
        selected_piece = null;
        return false;
    }

    public boolean movePiece(int direction)
    {
        if(selected_piece == null)
            return false;

        if(direction>3 || direction <0)
            throw new IllegalArgumentException();

        if(selected_piece == pieces[0] && pieces[0].x == 1 && pieces[0].y == 3 && direction == 3) //caso in cui il pezzo 2x2 si trova l'uscita e voglio spingerlo giu
        {
            hasWon = true;
            return true;
        }

        if(out_of_bounds(direction))
            return false;
        int x = selected_piece.x;     //uso nomi variabili piu comodi
        int y = selected_piece.y;
        int w = selected_piece.width;
        int h = selected_piece.height;

        //il resto del codice si occupa delle possibili sovrapposizioni fra pezzi
        int i;
        if(direction == 0)
        {
         for(i=x; i<=(x+w-1); i++)  //scorro orizzontalmente il pezzo
         {
             if(isOccupied(i,y-1)) //e guardo se e libero sopra di 1
                 return false;
         }
        } else if(direction == 1)
        {
           for(i=y; i<=(y+h-1); i++)  //scorro verticalmente il pezzo
           {
               if(isOccupied((x+w),i))  // e controllo che accanto alla parte piu a destra del pezzo sia libero
                   return false;
           }
        } else if(direction == 2)
        {
            for(i=x; i<=(x+w-1); i++)  //scorro orizzontalmente il pezzo
            {
                if(isOccupied(i,y+h))  // e controllo che sotto la parte piu bassa del pezzo sia liberp
                    return false;
            }
        } else if(direction == 3)
        {
            for(i=y; i<=(y+h-1); i++)  //scorro verticalmente il pezzo
            {
                if(isOccupied((x-1),i))  // e controllo a sinistra di 1
                    return false;
            }
        }

        selected_piece.move(direction);
        return true;
    }

    private boolean out_of_bounds(int dir)  //metodo a parte per semplificare movePieces(). Si occupa di una parte dei movimenti non fattibili
    {
        boolean out_of_bounds = false;
        if(dir==0 && selected_piece.y==0)
            out_of_bounds = true;
        if(dir==1 && (selected_piece.x + selected_piece.width - 1)==3)
            out_of_bounds = true;
        if(dir==2 && (selected_piece.y + selected_piece.height -1)==4)
            out_of_bounds = true;
        if(dir==3 && selected_piece.x==0)
            out_of_bounds = true;
        return out_of_bounds;
    }

    private boolean isOccupied(int x, int y)
    {
        for(int i=0; i<10;i++)
        {
            if(pieces[0].contains(x,y))   //scorro tutti e 10 i pezzi
                return true;
        }
        return false;
    }
    public boolean checkWin()
    {
        return hasWon;
    }
}

