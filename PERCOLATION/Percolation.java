import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// This class is created by Jason.
// Copy first, create next!

public class Percolation {                           // Don't forget to change the file's name！！！！
	private final boolean grid [][];
	private final         WeightedQuickUnionUF ufTest;
	private final         WeightedQuickUnionUF ufFull;
	private final int           gridLength;
	private final int           virtualTop;
	private final int           virtualBottom;
	private int           openSites;
	
	public Percolation(int n) {
		if(n <= 0) {
			throw new java.lang.IllegalArgumentException("n must be at least 1");
		}
		grid          = new boolean [n][n];
		ufTest        = new WeightedQuickUnionUF(n*n + 2);
		ufFull        = new WeightedQuickUnionUF(n*n + 1);
		gridLength    = n;
		virtualTop    = 0;
		virtualBottom = n*n + 1;
		openSites     = 0;
	}
	
	public void open(int i, int j) {
		if(!isOpen(i,j)) {
			int inUF = getIndexInUF(i,j);
			openSites = openSites + 1;
			
			if (i == 1) {
				ufTest.union(virtualTop, inUF);
				ufFull.union(virtualTop, inUF);
			}
			if(i == gridLength) {
				ufTest.union(virtualBottom, inUF);
			}
			
			unionIfOpen(inUF, i-1, j);
			unionIfOpen(inUF, i+1, j);
			unionIfOpen(inUF, i, j-1);
			unionIfOpen(inUF, i, j+1);
			
			grid [i-1][j-1] = true;
			
		}
	}
	
	public boolean isOpen(int i, int j) {
		return grid [i-1][j-1];
	}
	
	public boolean isFull(int i, int j) {
		if(isOpen(i,j)) {
			int ijInUF = getIndexInUF(i,j);
			return ufFull.connected(virtualTop, ijInUF);       
		}
		return false;
	}
	
	public int numberOfOpenSites() { 
		return openSites;
	}
	
	public boolean percolates() {
		return ufTest.connected(virtualTop, virtualBottom);                       // Don't use if for this method, directly return.
	}
	
	private void unionIfOpen(int inUF, int i, int j) {
		try {
			if(isOpen(i,j)) {
				int neibourInUF = getIndexInUF(i,j);
				ufTest.union(neibourInUF, inUF);
				ufFull.union(neibourInUF, inUF);
			}
		} catch(IndexOutOfBoundsException e) {;}             // Learn to use 'try' and 'catch'!
	}
	
	private int getIndexInUF(int i, int j) {
		return (i - 1) * gridLength + j;                       // If I use (i-1) * gridLength + j, 
	}	                                                     // then virtualTop = 0, virtualBottom = n*n + 1.
															 // But if I use (i-1) * gridLength + (j-1),
															 // then virtualTop = n*n + 1, virtualBottom = n*n + 2.
}