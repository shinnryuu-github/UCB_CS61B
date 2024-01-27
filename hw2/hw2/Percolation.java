package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private WeightedQuickUnionUF sets;
    private site[][] sites;
    private class site{
        private int state, num, row, col;
        public site(int i, int j, int size, int s){
            state = s;
            num = i * size + j;
            row = i;
            col = j;
        }
    }
    public Percolation(int N){
        if (N <= 0)
            throw new IllegalArgumentException();
        else {
            sites = new site[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    sites[i][j] = new site(i, j, N, 0);
                }
            }
            size = N;
            sets = new WeightedQuickUnionUF(N * N);
        }
    }

    private void connect(int row, int col){
        if (sites[row][col].state == 2 || sites[row][col].state == 1){
            if (row != 0){
                if (sites[row - 1][col].state == 2 || sites[row - 1][col].state == 1){
                    sets.union(sites[row][col].num, sites[row - 1][col].num);
                }
            }
            if (row != size - 1){
                if (sites[row + 1][col].state == 2 || sites[row + 1][col].state == 1){
                    sets.union(sites[row][col].num, sites[row + 1][col].num);
                }
            }
            if (col != 0){
                if (sites[row][col - 1].state == 2 || sites[row][col - 1].state == 1){
                    sets.union(sites[row][col].num, sites[row][col - 1].num);
                }
            }
            if (col != size - 1){
                if (sites[row][col + 1].state == 2 || sites[row][col + 1].state == 1){
                    sets.union(sites[row][col].num, sites[row][col + 1].num);
                }
            }
        }
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (sites[i][j].state == 1){
                    for (int k = 0; k < size; k++){
                        if (sites[0][k].state == 2 && sets.connected(k, sites[i][j].num)) {
                            sites[i][j].state = 2;
                            break;
                        }
                    }
                }
            }
        }
    }
    public void open(int row, int col){
        if (row >= size || col >= size)
            throw new IndexOutOfBoundsException();
        else if (row < 0 || col < 0)
            throw new IllegalArgumentException();
        else{
            sites[row][col].state = 1;
            if (row == 0)
                sites[row][col].state = 2;
            connect(row, col);
            for (int i = 0; i < size; i++){
                if (sites[0][i].state == 2 && sets.connected(i, sites[row][col].num)) {
                    sites[row][col].state = 2;
                    break;
                }
            }
        }
    }
    public boolean isOpen(int row, int col) {
        if (row >= size || col >= size)
            throw new IndexOutOfBoundsException();
        else if (row < 0 || col < 0)
            throw new IllegalArgumentException();
        else{
            return sites[row][col].state == 2 || sites[row][col].state == 1;
        }
    }
    public boolean isFull(int row, int col){
        if (row >= size || col >= size)
            throw new IndexOutOfBoundsException();
        else if (row < 0 || col < 0)
            throw new IllegalArgumentException();
        else{
            return sites[row][col].state == 2;
        }
    }
    public int numberOfOpenSites(){
        int cnt = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (sites[i][j].state == 2)
                    cnt++;
            }
        }
        return cnt;
    }
    public boolean percolates(){
        for (int i = 0; i < size; i++){
            if (sites[size - 1][i].state == 2)
                return true;
        }
        return false;
    }
}
