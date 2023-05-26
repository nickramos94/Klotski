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

    public void pushMove(int piece_index, int move_direction) {
        moves_log.push(new Move(step, piece_index, move_direction));
        step++;
    }

    public Move popMove() {
        step--;
        return moves_log.pop();
    }

    public boolean isEmpty() {
        return moves_log == null || moves_log.isEmpty();
    }

    public void saveLog() {
        saved_moves_log = (Stack<Move>) moves_log.clone();
    }

    public void loadLog() {
        moves_log = (Stack<Move>) saved_moves_log.clone();
        step = moves_log.peek().getStep();
    }

    public int getStep() {
        return step;
    }
}
