import java.awt.*;

/**
 * Implements the abstraction of each square on the checkerboard
 */
public class Position{
    int x;
    int y;
    final int MIN_MOUSE_DRAG = 3;

    /** initialize x and y checking if they are inside the board (in pixel)
     * @param x x value
     * @param y y value
     */

    public Position(int x, int y) {
        if(x < 0 || y < 0 || x > Window.BOARD_WIDTH || y > Window.BOARD_HEIGHT)
            throw new IllegalArgumentException("Position outside the board");
        this.x = x;
        this.y = y;
    }

    /** constructor with Point (mainly for the mouse getPoint() method that returns a point)
     * @param point
     */
    public Position(Point point) {
        this(point.x, point.y);
    }

    /** Returns the distance on the x coordinates between a Position passed as argument and the x value of the Position
     * @param final_pos Position
     * @return distance
     */
    private int xDistance(Position final_pos) {
        return final_pos.x - x;
    }

    /** Returns the distance on the y coordinates between a Position passed as argument and the y value of the Position
     * @param final_pos Position
     * @return distance
     */
    private int yDistance(Position final_pos) {
        return final_pos.y - y;
    }

    /** return the cardinal direction of the vector this -> final_pos approximate to: up=2, right=1, down=0, left=3
     * @param final_pos
     * @return
     */
    public int direction(Position final_pos) {
        int dx = xDistance(final_pos);
        int dy = yDistance(final_pos);
        if(Math.abs(dx) < MIN_MOUSE_DRAG || Math.abs(dy) < MIN_MOUSE_DRAG)  // no movement
            return -1;
        if(Math.abs(dx) > Math.abs(dy))     // x axis
            if(dx > 0)
                return 1;   // right
            else
                return 3;   // left
        else                                // y axis
            if(dy > 0)
                return 0;   //down
            else
                return 2;   //up
    }

    /** Checks if the current Position is the same as the one passed as argument
     * @param pos Position to compare
     * @return true if the positions are the same
     */
    public boolean equals(Position pos) {
        return xDistance(pos)==0 && yDistance(pos)==0;
    }

    /** Converts the pos from pixels the unit of measurement of the board
     * @return a Position
     */
    public Position unitConverter() {
        return new Position(x/Window.BLOCK_SIZE, y/Window.BLOCK_SIZE);
    }

    /** Converts the pos from the unit of measurement the board to pixels
     * @return Position
     */
    public Position pixelConverter() {
        return new Position(x*Window.BLOCK_SIZE, y*Window.BLOCK_SIZE);
    }

    /** Returns the coordinates of the position to a String
     * @return a String of coordinates
     */
    @Override
    public String toString() {
        return "[" + Integer.toString(x) + "," + Integer.toString(y) + "]";
    }
}
