public class Move {

    private int step, blockIdx, dirIdx;
    public Move() {
        step = -1;
        blockIdx = -1;
        dirIdx = -1;
    }

    public Move(int s, int b, int d){
        step = s;
        blockIdx = b;
        dirIdx = d;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setBlockIdx(int blockIdx) {
        this.blockIdx = blockIdx;
    }

    public void setDirIdx(int dirIdx) {
        this.dirIdx = dirIdx;
    }

    public int getBlockIdx() {
        return blockIdx;
    }

    public int getDirIdx() {
        return dirIdx;
    }

    public int getStep() {
        return step;
    }

    public String toString() {
        return step + ", " + blockIdx + ", " + dirIdx;
    }
}
