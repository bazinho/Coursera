import java.util.ArrayDeque;

public class Board {
    
    private int N;
    private int[][] board;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        board = new int[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                board[i][j] = blocks[i][j];
            }
        }
    }
    
    // copy constructor
    private Board(Board that) {
        this.N = that.N;
        this.board = new int[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                this.board[i][j] = that.board[i][j];
            }
        }
    }
    
    // board dimension N
    public int dimension() {
        return N;
    }
    
    // number of blocks out of place
    public int hamming() {
        int hammingdist = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (board[i][j] != 0) {
                    if (board[i][j] != (i * N + j + 1)) {
                        ++hammingdist;
                    }
                }
            }
        }
        return hammingdist;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattandist = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (board[i][j] != 0) {
                    int expectedrow = (board[i][j] - 1) / N;
                    int expectedcol = (board[i][j] - 1) % N;
                    manhattandist +=  
                        Math.abs(expectedrow - i) + Math.abs(expectedcol - j);
                }
            }
        }
        return manhattandist;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        Board temp = new Board(this);
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N - 1; ++j) {
                if (temp.board[i][j] != 0 && temp.board[i][j + 1] != 0) {
                    int t = temp.board[i][j];
                    temp.board[i][j] = temp.board[i][j + 1];
                    temp.board[i][j + 1] = t;
                    return temp;
                }
            }
        }
        return null;
    }
    

    private boolean equals(Board that) {
        if (that == null) return false;
        if (this.N != that.N) return false;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (!(y instanceof Board))
            return false;
        // Better not use "instanceof" due to symmetry broken with subclasses.
        // Use follow code instead:
        // if (this == null) return false;
        // if (getClass() != y.getClass()) return false;
        Board that = (Board) y;
        return this.equals(that);
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        int row = 0;
        int col = 0;
        boolean found = false;
        for (int i = 0; i < N && !found; ++i) {
            for (int j = 0; j < N && !found; ++j) {
                if (this.board[i][j] == 0) {
                    row = i;
                    col = j;
                    found = true;
                }
            }
        }
        ArrayDeque<Board> q = new ArrayDeque<Board>();
        if (row > 0) {
            Board temp = new Board(this);
            temp.board[row][col] = temp.board[row - 1][col]; 
            temp.board[row - 1][col] = 0;
            q.add(temp);
        }
        if (row < N - 1) {
            Board temp = new Board(this);
            temp.board[row][col] = temp.board[row + 1][col]; 
            temp.board[row + 1][col] = 0;
            q.add(temp);
        }
        if (col > 0) {
            Board temp = new Board(this);
            temp.board[row][col] = temp.board[row][col - 1]; 
            temp.board[row][col - 1] = 0;
            q.add(temp);
        }
        if (col < N - 1) {
            Board temp = new Board(this);
            temp.board[row][col] = temp.board[row][col + 1]; 
            temp.board[row][col + 1] = 0;
            q.add(temp);
        }
        return q;
    }
    
    // string representation of the board (in the output format specified below)
    public String toString() {
        String print;
        print = "\n" + N;
        for (int i = 0; i < N; ++i) {
            print += "\n";
            for (int j = 0; j < N; ++j) {
                print += " " + board[i][j];
            }
        }
        return print;
    }
    
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board board = new Board(blocks);
        StdOut.println("Board:");
        StdOut.println(board.toString());
        StdOut.printf("Hamming distance: %d\n", board.hamming());
        StdOut.printf("Manhattan distance: %d\n", board.manhattan());

        Board tw = board.twin();
        StdOut.println("Twin board:");
        StdOut.println(tw);
        StdOut.printf("Hamming distance: %d\n", tw.hamming());
        StdOut.printf("Manhattan distance: %d\n", tw.manhattan());

        if (board.isGoal())
            StdOut.println("Board is goal.");
        else
            StdOut.println("Board is not goal.");
        
        StdOut.println("\nBoard neighbors:");
        for (Board b : board.neighbors()) {
            StdOut.println(b.toString());
        }
    }
}