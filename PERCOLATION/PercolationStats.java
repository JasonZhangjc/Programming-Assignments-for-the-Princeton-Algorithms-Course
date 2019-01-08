import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	 
	private final double mean;
	private final double stddev;
	private final double confLo;
	private final double confHi;
	
	public PercolationStats(int n, int t) {
		if(n <= 0) {
            throw new IllegalArgumentException("The grid size must be bigger than zero");
        }
        if(t <= 0) {
            throw new IllegalArgumentException("The number of experiments must be bigger than zero");
        }

		double[] threshold = new double [t];
		
		for(int i = 0; i < t; i++) {			
			Percolation experiment = new Percolation(n);
			int run = 0;
			
			while(!experiment.percolates()) {
				int row;
				int col;
				
				do {
					row = 1 + StdRandom.uniform(n);
					col = 1 + StdRandom.uniform(n);
				} while(experiment.isOpen(row,col));
				
				experiment.open(row,col);
				run++;
			}
			
			threshold[i] = run / (double) (n * n);
		}
		
		mean   = StdStats.mean(threshold);                    // These two methods use the name of an array!
		stddev = StdStats.stddev(threshold);
		double confFraction = (1.96 * stddev()) / Math.sqrt(t);
        confLo = mean - confFraction;
        confHi = mean + confFraction;
	}
	
	public double mean() {
		return mean;
	}
	
	public double stddev() {
		return stddev;
	}
	
	public double confidenceLo() {
		return confLo;
	}
	
	public double confidenceHi() {
		return confHi;
	}
	
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);                          // Guarantee the inputs be int.  
        int t = Integer.parseInt(args[1]);
		
		PercolationStats stats = new PercolationStats (n,t);
		
		System.out.println ("The mean is "                    + stats.mean());
		System.out.println ("The standard deviation is "      + stats.stddev());
		System.out.println ("The 95% confidence interval is " + stats.confidenceLo() + ", " + stats.confidenceHi());
	}
}