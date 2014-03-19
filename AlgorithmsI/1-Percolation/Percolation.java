public class Percolation {
    
    private int size;
    private boolean[][] grid;
    private int top;
    private int bottom;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uftoponly;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        size = N;
        grid = new boolean[size][size];
        top = size*size;
        bottom = size*size+1;
        uf = new WeightedQuickUnionUF(size*size+2);
        uftoponly = new WeightedQuickUnionUF(size*size+1);
    }
    
    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            int row = i-1, col = j-1;
            grid[row][col] = true;
            if (row-1 >= 0 && isOpen(i-1, j)) {
                uf.union(size*row+col, size*(row-1)+col);
                uftoponly.union(size*row+col, size*(row-1)+col);
            }
            if (row+1 < size && isOpen(i+1, j)) {
                uf.union(size*row+col, size*(row+1)+col);
                uftoponly.union(size*row+col, size*(row+1)+col);
            }
            if (col-1 >= 0 && isOpen(i, j-1)) {
                uf.union(size*row+col, size*row+(col-1));                                
                uftoponly.union(size*row+col, size*row+(col-1));                                
            }
            if (col+1 < size && isOpen(i, j+1)) {
                uf.union(size*row+col, size*row+(col+1));
                uftoponly.union(size*row+col, size*row+(col+1));
            }
            if (row == 0) {
                uf.union(top, size*row+col);
                uftoponly.union(top, size*row+col);
            }
            if (row == size-1) {
                uf.union(bottom, size*row+col);
            }
        }
    }
   
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if ((i < 1) || (i > size) || (j < 1) || (j > size))
            throw new java.lang.IndexOutOfBoundsException();
        return grid[i-1][j-1];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if ((i < 1) || (i > size) || (j < 1) || (j > size))
            throw new java.lang.IndexOutOfBoundsException();
        return uftoponly.connected(top, size*(i-1)+(j-1));
    }
    
    // does the system percolate?
    public boolean percolates() {
        return uf.connected(top, bottom);
    }
}