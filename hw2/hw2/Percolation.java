package hw2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashMap;

public class Percolation {

    private int size, OpenNum;
    private WeightedQuickUnionUF sets, backwash;
    private site[][] sites;

    private class site{
        private int num;
        private boolean is_open;
        public site(int i, int j, int size, boolean open){
            is_open = open;
            num = i * size + j;
        }
    }
    public Percolation(int N){
        if (N <= 0)
            throw new IllegalArgumentException();
        else {
            sites = new site[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    sites[i][j] = new site(i, j, N, false);
                }
            }
            size = N;
            OpenNum = 0;
            sets = new WeightedQuickUnionUF(N * N + 2);
            backwash = new WeightedQuickUnionUF(N * N + 1);
        }
    }

    private void connect(int row, int col){
        if (row == 0){
            backwash.union(sites[row][col].num, size * size);
        }
        if (row != 0){
            if (sites[row - 1][col].is_open){
                backwash.union(sites[row][col].num, sites[row - 1][col].num);
            }
        }
        if (row != size - 1){
            if (sites[row + 1][col].is_open){
                backwash.union(sites[row][col].num, sites[row + 1][col].num);
            }
        }
        if (col != 0){
            if (sites[row][col - 1].is_open){
                backwash.union(sites[row][col].num, sites[row][col - 1].num);
            }
        }
        if (col != size - 1){
            if (sites[row][col + 1].is_open){
                backwash.union(sites[row][col].num, sites[row][col + 1].num);
            }
        }
        if (row == 0){
            sets.union(sites[row][col].num, size * size);
        }
        if (row == size - 1){
            sets.union(sites[row][col].num, size * size + 1);
        }
        if (row != 0){
            if (sites[row - 1][col].is_open){
                sets.union(sites[row][col].num, sites[row - 1][col].num);
            }
        }
        if (row != size - 1){
            if (sites[row + 1][col].is_open){
                sets.union(sites[row][col].num, sites[row + 1][col].num);
            }
        }
        if (col != 0){
            if (sites[row][col - 1].is_open){
                sets.union(sites[row][col].num, sites[row][col - 1].num);
            }
        }
        if (col != size - 1){
            if (sites[row][col + 1].is_open){
                sets.union(sites[row][col].num, sites[row][col + 1].num);
            }
        }
    }
    public void open(int row, int col){
        if (row >= size || col >= size || row < 0 || col < 0)
            throw new IndexOutOfBoundsException();
        else{
            sites[row][col].is_open = true;
            OpenNum++;
            connect(row, col);
        }
    }
    public boolean isOpen(int row, int col) {
        if (row >= size || col >= size || row < 0 || col < 0)
            throw new IndexOutOfBoundsException();
        else{
            return sites[row][col].is_open;
        }
    }
    public boolean isFull(int row, int col){
        if (row >= size || col >= size || row < 0 || col < 0)
            throw new IndexOutOfBoundsException();
        else{
            return sets.connected(sites[row][col].num, size * size) && backwash.connected(sites[row][col].num, size * size);
        }
    }
    public int numberOfOpenSites(){
        return OpenNum;
    }
    public boolean percolates(){
        return sets.connected(size * size + 1, size * size);
    }

    public static void main(String[] args){
        In in = new In(args[0]);
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        System.out.println(perc.percolates());
    }
}
