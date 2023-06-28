import java.util.List;

public class Board {

    private Piece[] pieces;
    private Piece selected_piece;
    private int selected_index;
    private int special_index;  // to have the special piece (square 2*2) index
    private boolean hasWon;
    private int moves;
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
            trySetSpecial(i);
        }
    }

    public Board(List<int[]> pieces) {
        this();
        int index = 0;
        for(int[] piece : pieces) {
            this.pieces[index] = new Piece(piece[0], piece[1], piece[2], piece[3]);
            trySetSpecial(index);
            index++;
        }
    }

    public Board(List<int[]> pieces, int move_num)
    {
        this(pieces);
        moves = move_num;
    }

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
                w = (int) Math.round(Math.random() + 1);   //width= 1 or 2
                if(w==2){
                    h = 1;                                //if it's 2, height must me 1 (can't have more big squares)
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
                    pieces[pieces_counter] = new Piece(x,y,w,h);     //throws error if out of bounds (positions out of bounds are free so they pass the isOccupied() check, must manage them here)
                    pieces_counter++;
                    if(w==2 || h==2)
                        rectangles_counter++;
                }
                catch(IllegalArgumentException e)
                {}
            }


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

    public boolean invertedMove(Move move) //method called by Game.undo(). invertedMove receives the move that has to be repeated in the opposite direction
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

    private boolean out_of_bounds(int dir)  //called by Board.movePiece(). Checks only if the movement would make the piece go out of the Board
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
            if(pieces[i]!=null && pieces[i].contains(x,y))
                return true;
        }
        return false;
    }

    private void trySetSpecial(int index) {
        if(pieces[index].width == 2 && pieces[index].height == 2) {
            special_index = index;
        }
    }

    public boolean isSpecial() {
        return this.isSpecial(selected_index);
    }
    public boolean isSpecial(int index) {
        return index == special_index;
    }

    public boolean checkWin()
    {
        return hasWon;
    }

    public void resetWin() {
        hasWon = false;
    }

    public Position getSelectedPos()
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

    public int getMoves()
    {
        return moves;
    }

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

