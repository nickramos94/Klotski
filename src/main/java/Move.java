/**
 * A class that implements the abstraction of a piece's position
 */
public class Move {

    private int step, blockIdx, dirIdx;

    /**
     * Generates a null move
     */
    public Move() {
        step = -1;
        blockIdx = -1;
        dirIdx = -1;
    }

    /** Generates a move
     * @param s number of steps to be performed
     * @param b index of the block to be moved
     * @param d direction of movement
     */
    public Move(int s, int b, int d){
        step = s;
        blockIdx = b;
        dirIdx = d;
    }

    /** Sets the value of a step
     * @param step number of steps
     */
    public void setStep(int step) {
        this.step = step;
    }

    /** Sets the index of the block to be moved
     * @param blockIdx index of the block
     */
    public void setBlockIdx(int blockIdx) {
        this.blockIdx = blockIdx;
    }

    /** Set the direction of movement
     * @param dirIdx direction
     */
    public void setDirIdx(int dirIdx) {
        this.dirIdx = dirIdx;
    }

    /** Returns the index of a block
     * @return index
     */
    public int getBlockIdx() {
        return blockIdx;
    }

    /** Returns a direction
     * @return direction index
     */
    public int getDirIdx() {
        return dirIdx;
    }

    /** Gets the number of steps
     * @return number of steps
     */
    public int getStep() {
        return step;
    }

    /** Returns a move to a string
     * @return string containing the move
     */
    public String toString() {
        return step + ", " + blockIdx + ", " + dirIdx;
    }
}
