public class Solver {
    
    private SearchNode solutionnode;
    
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int nummoves;
        private int priority;
        private SearchNode previous;
        
        public SearchNode(Board initialboard) {
            board = initialboard;
            nummoves = 0;
            priority = board.manhattan();
            previous = null;
        }

        public SearchNode(Board initialboard, int moves, SearchNode parent) {
            board = initialboard;
            nummoves = moves;
            priority = board.manhattan() + nummoves;
            previous = parent;
        }
        
        public int compareTo(SearchNode that) {
            if (this.priority < that.priority)
                return -1;
            if (this.priority > that.priority)
                return +1;
            return 0;
        }
    }
        
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqtwin = new MinPQ<SearchNode>();
        SearchNode initialsearchnode = new SearchNode(initial);
        SearchNode initialtwinnode = new SearchNode(initial.twin());
        pq.insert(initialsearchnode);     
        pqtwin.insert(initialtwinnode);     
        //for (int i = 0; i < 5; ++i) {
        while (!pq.isEmpty() && !pqtwin.isEmpty()) {
            SearchNode minsearchnode = pq.delMin();
            SearchNode mintwinnode = pqtwin.delMin();
            if (!minsearchnode.board.isGoal()) {
                for (Board neighborboard : minsearchnode.board.neighbors()) {
                    SearchNode neighborsearchnode = 
                        new SearchNode(neighborboard, 
                                       minsearchnode.nummoves + 1, 
                                       minsearchnode);
                    if (minsearchnode.previous == null 
                       || !neighborboard.equals(minsearchnode.previous.board)) {
                        pq.insert(neighborsearchnode);              
                    }
                }
            } else {
                solutionnode = minsearchnode;
                return;
            }
            if (!mintwinnode.board.isGoal()) {
                for (Board twinneighborboard : mintwinnode.board.neighbors()) {
                    SearchNode neighbortwinnode = 
                        new SearchNode(twinneighborboard, 
                                       mintwinnode.nummoves + 1, 
                                       mintwinnode);
                    if (mintwinnode.previous == null 
                            || 
                        !twinneighborboard.equals(mintwinnode.previous.board)) {
                        pqtwin.insert(neighbortwinnode);              
                    }
                }
            } else {
                solutionnode = null;
                return;
            }
        }
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return solutionnode != null;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (solutionnode == null)
            return -1;
        return solutionnode.nummoves;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        Stack<Board> solutionboards = new Stack<Board>();
        if (solutionnode == null)
            return null;
        SearchNode currentnode = solutionnode;
        while (currentnode != null) {
            solutionboards.push(currentnode.board);
            currentnode = currentnode.previous;
        }
        return solutionboards;
    }
        
    // solve a slider puzzle (given below)
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
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}