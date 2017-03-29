/**
 * Created by yuyuanliu on 2017-01-07.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int dimension;
    private double[] prop;
    private int trials;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        dimension = n;
        this.trials = trials;
        prop = new double[trials];
        for (int run = 0; run < trials;run++) {
            Percolation modle = new Percolation(dimension);
            while (!modle.percolates()) {
                int row = StdRandom.uniform(1, dimension + 1);
                int col = StdRandom.uniform(1, dimension + 1);
                if (!modle.isOpen(row, col))
                    modle.open(row, col);
                else
                    continue;

            }
            prop[run] = (double) modle.numberOfOpenSites() / (double) (dimension*dimension);
        } // test client (optional)
    }    // perform trials independent experiments on an n-by-n grid
    public double mean() {
        return StdStats.mean(this.prop);
    }                          // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.prop);

    }                        // sample standard deviation of percolation threshold
    public double confidenceLo() {
        double confidenceLo;
        confidenceLo = mean() - 1.96 * stddev()/ Math.sqrt((double) trials);
        return confidenceLo;
    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi() {
        double confidenceHi;
        confidenceHi = mean() + 1.96 * stddev()/ Math.sqrt((double) trials);
        return confidenceHi;
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args) {
//        PercolationStats test = new PercolationStats(200, 10000);
//        System.out.println("mean =" + test.mean());
//        System.out.println("stddev =" + test.stddev());
//        System.out.println("95% confidence interval = [" + test.confidenceLo() + "," + test.confidenceHi() + "]");

    }        // test client (described below)
}
