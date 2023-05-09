import java.awt.*;

public class Position extends Point {

    final int BOARD_LENGTH = 400;
    final int BOARD_HEIGHT = 500;
    final int MIN_MOUSE_DRAG = 5;

    public Position(int x, int y) {
        if(x < 0 || y < 0 || x > BOARD_LENGTH || y > BOARD_HEIGHT)
            throw new IllegalArgumentException("Position outside the board");
        this.x = x;
        this.y = y;
    }
    public Position(Point point) {
        this(point.x, point.y);
    }
    public Position() {
        this(0, 0);
    }

    public int xDistance(Position final_pos) {
        return final_pos.x - x;
    }
    public int yDistance(Position final_pos) {
        return final_pos.y - y;
    }

    // return the direction of the vector this->final_pos
    // approximate to:
    // up=0, right=1, down=2, left=3
    public int direction(Position final_pos) {
        int dx = xDistance(final_pos);
        int dy = yDistance(final_pos);
        if(Math.abs(dx) < MIN_MOUSE_DRAG || Math.abs(dy) < MIN_MOUSE_DRAG)  // no movement
            return -1;
        if(Math.abs(dx) > Math.abs(dy))     // y axis
            if(dx > 0)
                return 1;   // right
            else
                return 3;   // left
        else                                // x axis
            if(dy > 0)
                return 2;   //down
            else
                return 0;   //up
    }

    public boolean isEqual(Position pos) {
        return xDistance(pos)==0 && yDistance(pos)==0;
    }

    public int pixel_converter() {  //todo
        return 0;
    }
    public int unitConverter() {    //todo
        return 0;
    }
    public Position correspondantPiece() {  //todo
        return null;
    }
}