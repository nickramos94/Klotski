import java.awt.*;

public class Position{
    int x;
    int y;
    final int MIN_MOUSE_DRAG = 3;

    // initialize x and y checking if they are inside the board (in pixel)
    public Position(int x, int y) {
        if(x < 0 || y < 0 || x > Window.BOARD_WIDTH || y > Window.BOARD_HEIGHT)
            throw new IllegalArgumentException("Position outside the board");
        this.x = x;
        this.y = y;
    }
    // constructor with Point (mainly for the mouse getPoint() method that returns a point)
    public Position(Point point) {
        this(point.x, point.y);
    }

    // x and y distance
    public int xDistance(Position final_pos) {
        return final_pos.x - x;
    }
    public int yDistance(Position final_pos) {
        return final_pos.y - y;
    }

    // return the cardinal direction of the vector this -> final_pos
    // approximate to:
    // up=2, right=1, down=0, left=3
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
                return 0;   //down
            else
                return 2;   //up
    }

    public boolean equals(Position pos) {
        return xDistance(pos)==0 && yDistance(pos)==0;
    }

    // convert the pos from pixels to unit of the board
    public Position unitConverter() {
        return new Position(x/Window.BLOCK_SIZE, y/Window.BLOCK_SIZE);
    }

    // convert the pos from unit of the board to pixels
    public Position pixelConverter() {
        return new Position(x*Window.BLOCK_SIZE, y*Window.BLOCK_SIZE);
    }

    @Override
    public String toString() {
        return "[" + Integer.toString(x) + "," + Integer.toString(y) + "]";
    }
}
