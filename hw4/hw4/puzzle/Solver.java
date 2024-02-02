package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    private class searchnode implements Comparable<searchnode>{
        WorldState world;
        int dist, realdist;
        searchnode prev;
        public searchnode(WorldState w, int d, searchnode n) {
            world = w;
            dist = d;
            prev = n;
            realdist = dist + w.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(searchnode o) {
            return realdist - o.realdist;
        }
    }

    private searchnode start;
    private int min_dist;
    private Deque<WorldState> path;
    public Solver(WorldState initial){
        MinPQ<searchnode> pq = new MinPQ<>();
        Set<WorldState> visited = new HashSet<>();
        start = new searchnode(initial, 0, null);
        pq.insert(start);
        while (!pq.isEmpty()){
            searchnode newnode = pq.delMin();
            visited.add(newnode.world);
            if (newnode.world.isGoal()){
                min_dist = newnode.dist;
                searchnode tmp = newnode;
                path = new ArrayDeque<>();
                while (tmp != null){
                    path.addFirst(tmp.world);
                    tmp = tmp.prev;
                }
                break;
            }
            for (WorldState w : newnode.world.neighbors()){
                if (!visited.contains(w) && newnode.prev == null || !newnode.prev.world.equals(w)){
                    searchnode add = new searchnode(w, newnode.dist + 1, newnode);
                    pq.insert(add);
                }
            }
        }
    }
    public int moves(){
        return min_dist;
    }
    public Iterable<WorldState> solution(){
        return path;
    }
}