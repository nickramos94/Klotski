import java.util.Stack;

public class MovesLog {
    private Stack<Move> moves_log;
    private Stack<Move> saved_moves_log;
    private int step;

    public MovesLog() {
        resetLog();
    }

    public void resetLog() {
        moves_log = new Stack<>();
        step = 0;
    }

    // push a move on the moves_log stack
    public void pushMove(int piece_index, int move_direction) {
        moves_log.push(new Move(step, piece_index, move_direction));
        step++;
    }

    // pop a move from the moves_log stack and return it
    public Move popMove() {
        step--;
        return moves_log.pop();
    }

    // check if moves_log is empty or null
    public boolean isEmpty() {
        return moves_log == null || moves_log.isEmpty();
    }

    // copy moves_log to saved_moves_log
    public void saveLog() {
        saved_moves_log = (Stack<Move>) moves_log.clone();
    }

    // copy saved_moves_log to moves_log
    public void loadLog() {
        if(!(saved_moves_log == null || saved_moves_log.isEmpty())) {
            moves_log = (Stack<Move>) saved_moves_log.clone();
            step = moves_log.peek().getStep();
        }
    }

    public int getStep() {
        return step;
    }
}
