package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Vertex s;
    private Vertex t;
    private boolean targetFound = false;
    private Maze maze;

    private class Vertex implements Comparable<Vertex>{
        int No, dist;
        boolean marked;
        Maze maze;
        public Vertex(int x, int y, int d, boolean m, Maze M){
            maze = M;
            marked = m;
            dist = d;
            No = M.xyTo1D(x,y);
        }
        public Vertex(int num, int d, boolean m, Maze M){
            maze = M;
            marked = m;
            dist = d;
            No = num;
        }
        @Override
        public int compareTo(Vertex o){
            return dist - o.dist;
        }
    }
    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = new Vertex(sourceX, sourceY, 0, true, m);
        t = new Vertex(targetX, targetY, m.N() * m.N(), true, m);
        distTo[s.No] = s.dist;
        edgeTo[s.No] = s.No;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        marked[s.No] = s.marked;
        announce();
        if (s.No == t.No) {
            targetFound = true;
        }
        if (targetFound) {
            return;
        }
        MinPQ<Vertex> pq = new MinPQ<>();
        pq.insert(s);
        while (!pq.isEmpty()){
            Vertex newvertex = pq.delMin();
            for (int vertex_num : maze.adj(newvertex.No)){
                if (!marked[vertex_num]) {
                    marked[vertex_num] = true;
                    edgeTo[vertex_num] = newvertex.No;
                    distTo[vertex_num] = newvertex.dist + 1;
                    announce();
                    if (vertex_num == t.No)
                        return;
                    Vertex adj = new Vertex(vertex_num, newvertex.dist + 1, true, maze);
                    pq.insert(adj);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

