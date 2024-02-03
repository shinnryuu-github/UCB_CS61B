package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean find;
    private int[] nodesTo;

    public MazeCycles(Maze m) {
        super(m);
        find = false;
        nodesTo = new int[m.N() * m.N()];
    }
    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(0, -1);
    }

    // Helper methods go here
    private void dfs(int v, int pre) {
        if (find)
            return;
        marked[v] = true;
        nodesTo[v] = pre;
        announce();
        for (int w : maze.adj(v)) {
            if (marked[w] && w != pre){
                find = true;
                edgeTo[w] = v;
                announce();
                for (int x = v; x != w; x = nodesTo[x]){
                    edgeTo[x] = nodesTo[x];
                    announce();
                }
                return;
            }
        }
        for (int w : maze.adj(v)) {
            if (!marked[w])
                dfs(w, v);
        }
    }
}

