import java.awt.*;

public class Position{
    int x;
    int y;
    final int MIN_MOUSE_DRAG = 5;

    public Position(int x, int y) {
        if(x < 0 || y < 0 || x > Window.BOARD_WIDTH || y > Window.BOARD_HEIGHT)
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

    public Position pixel_converter() {
        return null;
    }
    public Position unitConverter() {
        return new Position(x/Window.BLOCK_SIZE, y/Window.BLOCK_SIZE);
    }

    public Position pixelConverter() {
        return new Position(x*Window.BLOCK_SIZE, y*Window.BLOCK_SIZE);
    }

    @Override
    public String toString() {
        return "[" + Integer.toString(x) + "," + Integer.toString(y) + "]";
    }
}
