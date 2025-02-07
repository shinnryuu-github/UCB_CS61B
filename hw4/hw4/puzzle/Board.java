package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{

    private int size;
    private int[][] tile;
    public Board(int[][] tiles){
        size = tiles[0].length;
        tile = new int[size][size];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++)
                tile[i][j] = tiles[i][j];
        }
    }
    public int tileAt(int i, int j){
        if (i < 0 || i > size - 1 || j < 0 || j > size - 1)
            throw new IndexOutOfBoundsException();
        return tile[i][j];
    }
    public int size(){
        return size;
    }
    @Override
    public Iterable<WorldState> neighbors(){
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }
    public int hamming(){
        int cnt = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                int val = tileAt(i, j);
                if (val != 0){
                    int shouldx = 0, shouldy = 0;
                    while (val > size){
                        val -= size;
                        shouldx++;
                    }
                    shouldy = val - 1;
                    if (shouldx != i || shouldy != j)
                        cnt++;
                }
            }
        }
        return cnt;
    }
    public int manhattan(){
        int cnt = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                int val = tileAt(i, j);
                if (val != 0){
                    int shouldx = 0, shouldy = 0;
                    while (val > size){
                        val -= size;
                        shouldx++;
                    }
                    shouldy = val - 1;
                    cnt += Math.abs(i - shouldx) + Math.abs(j - shouldy);
                }
            }
        }
        return cnt;
    }
    public int estimatedDistanceToGoal(){
        int cnt = 0;
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                int val = tileAt(i, j);
                if (val != 0){
                    int shouldx = 0, shouldy = 0;
                    while (val > size){
                        val -= size;
                        shouldx++;
                    }
                    shouldy = val - 1;
                    cnt += Math.abs(i - shouldx) + Math.abs(j - shouldy);
                }
            }
        }
        return cnt;
    }
    @Override
    public boolean equals(Object y){
        if (y == null)
            return false;
        if (y == this)
            return true;
        if (y.getClass() != getClass())
            return false;
        if (((Board) y).size() != size())
            return false;
        for (int i = 0; i < size(); i++){
            for (int j = 0; j < size(); j++){
                if (tileAt(i, j) != ((Board) y).tileAt(i, j))
                    return false;
            }
        }
        return true;
    }
    @Override
    public int hashCode(){
        return super.hashCode();
    }
    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
