import java.util.Arrays;
import java.util.List;

public class Board {
    
    private Piece[] pieces;
    private Piece selected_piece;
    private int selected_index;
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
        }
    }

    public Board(List<int[]> pieces) {
        this();
        int index = 0;
        for(int[] piece : pieces) {
            this.pieces[index] = new Piece(piece[0], piece[1], piece[2], piece[3]);
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
        else if(configuration == 2) {       // TEMPORARY ADDITION: second configuration
                                            // to try level selection and win condition
            pieces[0] = new Piece(1, 2, 2, 2);   //quadrato 2x2
            pieces[1] = new Piece(0, 0, 1, 2);   //rettangolo 1x2
            pieces[2] = new Piece(3, 0, 1, 2);   //rettangolo 1x2
            pieces[3] = new Piece(0, 2, 1, 2);   //rettangolo 1x2
            pieces[4] = new Piece(3, 2, 1, 2);   //rettangolo 1x2
            pieces[5] = new Piece(1, 0, 1, 1);   //quadrato 1x1
            pieces[6] = new Piece(2, 0, 1, 1);   //quadrato 1x1
            pieces[7] = new Piece(1, 1, 1, 1);   //quadrato 1x1
            pieces[8] = new Piece(2, 1, 1, 1);   //quadrato 1x1
            pieces[9] = new Piece(1, 4, 1, 1);   //quadrato 1x1
        }
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
            throw new IllegalArgumentException("Direzione non valida");

        if(out_of_bounds(direction))
            return false;
        int x = selected_piece.getX();     //uso nomi variabili piu comodi
        int y = selected_piece.getY();
        int w = selected_piece.width;
        int h = selected_piece.height;

        //il resto del codice si occupa delle possibili sovrapposizioni fra pezzi
        int i;
        if(direction == 0) //down
        {
            for(i=x; i<=(x+w-1); i++)  //scorro orizzontalmente il pezzo
            {
                if(isOccupied(i,y+h))  // e controllo che sotto la parte piu bassa del pezzo sia libero
                    return false;
            }
        } else if(direction == 1) //right
        {
            for(i=y; i<=(y+h-1); i++)  //scorro verticalmente il pezzo
            {
                if(isOccupied((x+w),i))  // e controllo che accanto alla parte piu a destra del pezzo sia libero
                    return false;
            }
        } else if(direction == 2) //up
        {
            for(i=x; i<=(x+w-1); i++)  //scorro orizzontalmente il pezzo
            {
                if(isOccupied(i,y-1)) //e guardo se e libero sopra di 1
                    return false;
            }
        } else if(direction == 3) //left
        {
            for(i=y; i<=(y+h-1); i++)  //scorro verticalmente il pezzo
            {
                if(isOccupied((x-1),i))  // e controllo a sinistra di 1
                    return false;
            }
        }

        selected_piece.move(direction);
        moves++;
        if(pieces[0].getX() == 1 && pieces[0].getY() == 3) //caso in cui il pezzo 2x2 si trova all'uscita
        {
            hasWon = true;
        }
        return true;
    }

    public boolean invertedMove(Move move)
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

    private boolean out_of_bounds(int dir)  //metodo a parte per semplificare movePieces(). Si occupa di una parte dei movimenti non fattibili
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
            if(pieces[i].contains(x,y))   //scorro tutti e 10 i pezzi
                return true;
        }
        return false;
    }

    public boolean checkWin()
    {
        return hasWon;
    }

    public Position getSelectedPiece()
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

    public Piece getPiece(Position pos)
    {
        for(int i=0; i<PIECES_NUMBER; i++)
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
        return moves;
    }

    public boolean isEqual(Board other_board) {
        if(other_board == null)
            return false;
        for(int i=0; i < PIECES_NUMBER; i++) {
            if(!pieces[i].isEqual(other_board.getPieces()[i]))
                return false;
        }
        return true;
    }
}

