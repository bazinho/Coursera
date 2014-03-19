public class PercolationStats {
    
    private double[] threshold;
    private int numsimulations;
    
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if ((N <= 0) || (T <= 0)) throw new java.lang.IllegalArgumentException();
        numsimulations = T;
        threshold = new double[numsimulations];
        for (int simulation = 0; simulation < numsimulations; ++simulation) {
            int opensites = 0;
            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                int rand = StdRandom.uniform(N*N);
                int i = rand/N+1;
                int j = rand % N+1;
                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                    ++opensites;
                }
            }
            threshold[simulation]  = (double) opensites/(double) (N*N);
        }
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }
    
    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean()-(1.96*stddev()/Math.sqrt((double) numsimulations));
    }
    
    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean()+(1.96*stddev()/Math.sqrt((double) numsimulations));
    }
    
    // test client, described below
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percstats = new PercolationStats(N, T);
        StdOut.printf("mean                    = %f\n", percstats.mean());
        StdOut.printf("stddev                  = %f\n", percstats.stddev());
        StdOut.printf("95%% confidence interval = %f %f\n",
                      percstats.confidenceLo(), percstats.confidenceHi());
    }
}