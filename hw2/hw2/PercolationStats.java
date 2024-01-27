package hw2;

public class PercolationStats {
    private int Times, size;
    private double[] results;

    private static int Experiment(Percolation p, int n){
        int cnt = 0;
        while (!p.percolates()){
            int row = edu.princeton.cs.introcs.StdRandom.uniform(n), col = edu.princeton.cs.introcs.StdRandom.uniform(n);
            if (!p.isOpen(row, col)){
                p.open(row, col);
                cnt++;
            }
        }
        return cnt;
    }
    public PercolationStats(int N, int T, PercolationFactory pf){
        size = N;
        Times = T;
        results = new double[T];
        for (int i = 0; i < T; i++){
            Percolation p = pf.make(N);
            int cnt = Experiment(p, size);
            results[i] = cnt / (double)(N * N);
        }
    }
    public double mean(){
        return edu.princeton.cs.introcs.StdStats.mean(results);
    }
    public double stddev(){
        return edu.princeton.cs.introcs.StdStats.stddev(results);
    }
    public double confidenceLow() {
        double u = mean(), o = stddev();
        return u - 1.96 * o / Math.sqrt(Times);
    }
    public double confidenceHigh(){
        double u = mean(), o = stddev();
        return u + 1.96 * o / Math.sqrt(Times);
    }

}
