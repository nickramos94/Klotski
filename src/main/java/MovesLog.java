import java.util.Stack;

/**
 * Stores the number of moves that are performed during gameplay
 */
public class MovesLog {
    private Stack<Move> moves_log;
    private Stack<Move> saved_moves_log;
    private int step;

    /**
     * Resets the counter
     */
    public MovesLog() {
        resetLog();
    }

    /**
     * Resets the counter and its related data structures
     */
    public void resetLog() {
        moves_log = new Stack<>();
        step = 0;
    }

    /** Push a new move on the moves_log stack
     * @param piece_index index of the moving piece
     * @param move_direction direction of movement of the moving piece
     */
    //
    public void pushMove(int piece_index, int move_direction) {
        moves_log.push(new Move(step, piece_index, move_direction));
        step++;
    }

    /** Pop a move from the moves_log stack and return it
     * @return
     */
    public Move popMove() {
        step--;
        return moves_log.pop();
    }

    /** Check if moves_log is empty or null
     * @return true if empty
     */
    public boolean isEmpty() {
        return moves_log == null || moves_log.isEmpty();
    }

    /**
     * Copy moves_log to saved_moves_log
     */
    public void saveLog() {
        saved_moves_log = (Stack<Move>) moves_log.clone();
    }

    /**
     * Copy saved_moves_log to moves_log
     */
    public void loadLog() {
        if(!(saved_moves_log == null || saved_moves_log.isEmpty())) {
            moves_log = (Stack<Move>) saved_moves_log.clone();
            step = moves_log.peek().getStep();
        }
    }

    /** Gets the current step
     * @return int step
     */
    public int getStep() {
        return step;
    }
}
