public class Board {
    private Piece[] pieces;
    private Piece selected_piece;
    private boolean hasWon;
    private int moves_counter;
    public final static int WIDTH = 4;
    public final static int HEIGHT = 5;

    public Board(int configuration)
    {
        pieces = new Piece[10];
        selected_piece = null;
        hasWon = false;
        moves_counter = 0;
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

    public boolean selectPiece(Position pos)
    {
        for(int i=0; i<10; i++)
        {
            if(pieces[i].contains(pos.x,pos.y))
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
        if(selected_piece == null || direction == -1)   //empty space selection and no movement cases
            return false;

        if(direction>3 || direction <0)
            throw new IllegalArgumentException();

        if(selected_piece == pieces[0] && pieces[0].getX() == 1 && pieces[0].getY() == 3 && direction == 3) //caso in cui il pezzo 2x2 si trova l'uscita e voglio spingerlo giu
        {
            hasWon = true;
            return true;
        }

        if(out_of_bounds(direction))
            return false;
        int x = selected_piece.getX();     //uso nomi variabili piu comodi
        int y = selected_piece.getY();
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
        moves_counter++;
        return true;
    }

    private boolean out_of_bounds(int dir)  //metodo a parte per semplificare movePieces(). Si occupa di una parte dei movimenti non fattibili
    {
        boolean out_of_bounds = false;
        if(dir==0 && selected_piece.getY()==0)
            out_of_bounds = true;
        if(dir==1 && (selected_piece.getX() + selected_piece.width) == WIDTH)
            out_of_bounds = true;
        if(dir==2 && (selected_piece.getY() + selected_piece.height) == HEIGHT)
            out_of_bounds = true;
        if(dir==3 && selected_piece.getX()==0)
            out_of_bounds = true;
        return out_of_bounds;
    }

    private boolean isOccupied(int x, int y)
    {
        for(int i=0; i<10;i++)
        {
            if(pieces[i].contains(x,y))   //scorro tutti e 10 i pezzi
                return true;
        }
        return false;
    }

    public boolean checkWin()
    {
        return hasWon;
    }

    public Position getSelectedPiece() {
        if(selected_piece == null)
            return null;
        return new Position(selected_piece.getX(), selected_piece.getY());
    }

    public Piece[] getPieces()
    {
        return pieces;
    }

    public Piece getPiece(Position pos)
    {
        for(int i=0; i<10; i++)
        {
            if(pieces[i].contains(pos.x,pos.y))
            {
                return pieces[i];
            }
        }
        return null;
    }

    public int getMoves()
    {
        return moves_counter;
    }

   /* public String toString()                    METODO SCRAUSO GIUSTO PER AVERE UNA VISIONE GRAFICA DI QUELLO CHE SUCCEDE, USATO SOLO PER PROVARE A FARE QUALCHE TEST, METODO CHE HO CHIAMATO DOPO OGNI movePiece()
    {
        char[][] board = new char[LENGTH][HEIGHT];
        int x=pieces[0].getX();
        int y=pieces[0].getY();
        board[x][y]='Q'; board[x+1][y]='Q'; board[x][y+1]='Q'; board[x+1][y+1]='Q';
        x=pieces[5].getX();
        y=pieces[5].getY();
        board[x][y]='a';
        x=pieces[6].getX();
        y=pieces[6].getY();
        board[x][y]='b';
        x=pieces[7].getX();
        y=pieces[7].getY();
        board[x][y]='c';
        x=pieces[8].getX();
        y=pieces[8].getY();
        board[x][y]='d';
        x=pieces[1].getX();
        y=pieces[1].getY();
        board[x][y]='e';board[x][y+1]='e';
        x=pieces[2].getX();
        y=pieces[2].getY();
        board[x][y]='f';board[x][y+1]='f';
        x=pieces[3].getX();
        y=pieces[3].getY();
        board[x][y]='g';board[x][y+1]='g';
        x=pieces[4].getX();
        y=pieces[4].getY();
        board[x][y]='h';board[x][y+1]='h';
        x=pieces[9].getX();
        y=pieces[9].getY();
        board[x][y]='i';board[x+1][y]='i';

        String grafico="";
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<4;j++)
            {
                grafico = grafico + ' ' + board[j][i];
            }
            grafico += '\n';
        }
        return grafico;
    } */
}

