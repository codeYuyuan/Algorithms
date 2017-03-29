import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;
//
///**
// * Created by yuyuanliu on 2017-01-12.
// */
//public class Solver {
//    private int moves;
//    private boolean issolveable;
//    private Deque<Board> routes;
//    private Stack<searchNode> originalStack = new Stack<searchNode>();
//    private Stack<searchNode> twinStack = new Stack<searchNode>();
//    public Solver(Board initial){
//        routes = new ArrayDeque<Board>();
//        Board twin = initial.twin();
//        MinPQ<searchNode> originalPQ = new MinPQ<searchNode>();
//        MinPQ<searchNode> twinPQ = new MinPQ<searchNode>();
//        searchNode initCurrent = new searchNode(initial,null,0);
//        searchNode twinCurrent = new searchNode(twin, null,0);
//        originalStack.push(initCurrent);
//        twinStack.push(twinCurrent);
//        originalPQ.insert(initCurrent);
//        twinPQ.insert(twinCurrent);
//        for (Board id:initial.neighbors()){
//            searchNode searchid = new searchNode(id, initCurrent,1);
//            originalPQ.insert(searchid);
//        }
//        for (Board id:twin.neighbors()){
//            searchNode searchTwinid = new searchNode(id,twinCurrent, 1);
//            twinPQ.insert(searchTwinid);
//        }
//
//        moves++;
//
//        while(true){
//            searchNode prevOriginal = originalStack.peek();
//            searchNode prevTwin = twinStack.peek();
//            if(initCurrent.board.isGoal()){
//                issolveable = true;
//                originalStack.push(initCurrent);
//                routes.push(initCurrent.board);
//                while (initCurrent.prev != null) {
//                    initCurrent = initCurrent.prev;
//                    routes.push(initCurrent.board);
//                }
//                break;
//            }else if(twinCurrent.board.isGoal()){
//                issolveable = false;
//                twinStack.push(twinCurrent);
//                break;
//            }
//            else{
//
//                for(Board id:initCurrent.board.neighbors()){
//                    if(!id.equals(prevOriginal.board)){
//                        searchNode nextOri = new searchNode(id, initCurrent ,initCurrent.moves + 1);
//                        originalPQ.insert(nextOri);
//                    }
//                }
//                for (Board id:twinCurrent.board.neighbors()){
//                    if (!id.equals(prevTwin.board)){
//                        searchNode nextTwin = new searchNode(id , twinCurrent, twinCurrent.moves + 1);
//                        twinPQ.insert(nextTwin);
//                    }
//                }
//                originalStack.push(initCurrent);
//                twinStack.push(twinCurrent);
//                initCurrent = originalPQ.delMin();
//                twinCurrent = twinPQ.delMin();
//            }
//            moves++;
//        }
//
//    }// find a solution to the initial board (using the A* algorithm)
//    private class searchNode implements Comparable<searchNode>{
//        private Board board;
//        private int moves;
//        searchNode prev;
//        searchNode(Board board, searchNode prev, int moves){
//            this.board = board;
//            this.moves = moves;
//            this.prev = prev;
//        }
//        public int compareTo(searchNode w){
//            return this.board.manhattan() + this.moves - w.moves - w.board.manhattan();
//        }
//    }
//
//
//
//    public boolean isSolvable(){
//        return issolveable;
//    }            // is the initial board solvable?
//    public int moves(){
//        if(isSolvable())
//            return routes.size() - 1;
//        return -1;
//    }                     // min number of moves to solve initial board; -1 if unsolvable
//    public Iterable<Board> solution(){
//        if(issolveable){
//            return routes;
//        }
//        if(moves == 0 ){
//            return routes;
//        }
//        return null;
//
//    }      // sequence of boards in a shortest solution; null if unsolvable
////    public static void main(String[] args) {
////
////        // for each command-line argument
////
////            int[][] tiles = {
////                    {11,  0,  4,  7},
////                    {2, 15,  1,  8},
////                    {5, 14,  9,  3},
////                    {13,  6, 12, 10}
////            };
////            // solve the slider puzzle
////            Board initial = new Board(tiles);
////            Solver solver = new Solver(initial);
////            StdOut.println( "moves: " + solver.moves());
////            for (Board id: solver.solution()){
////                StdOut.println(id.toString());
////            }
////
////    }
//}
public class Solver {
    private final Deque<Board> boards;
    private int moves;
    private boolean isSolvable;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode previous;
        private int cachedPriority = -1;

        SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        private int priority() {
            if (cachedPriority == -1) {
                cachedPriority = moves + board.manhattan();
            }
            return cachedPriority;
        }

        @Override
        public int compareTo(SearchNode that) {
            if (this.priority() < that.priority()) {
                return -1;
            }
            if (this.priority() > that.priority()) {
                return +1;
            }
            return 0;
        }
    }

    /*
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        boards = new ArrayDeque<>();
        if (initial.isGoal()) {
            isSolvable = true;
            this.boards.push(initial);
            return;
        }
        if (initial.twin().isGoal()) {
            isSolvable = false;
            return;
        }

        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>();
        moves = 0;
        Board board = initial;
        Board boardTwin = initial.twin();
        SearchNode node = new SearchNode(board, 0, null);
        SearchNode nodeTwin = new SearchNode(boardTwin, 0, null);
        minPQ.insert(node);
        minPQTwin.insert(nodeTwin);
        while (moves < 100) {
            node = minPQ.delMin();
            nodeTwin = minPQTwin.delMin();
            board = node.board;
            boardTwin = nodeTwin.board;
            if (boardTwin.isGoal()) {
                isSolvable = false;
                return;
            }
            if (board.isGoal()) {
                isSolvable = true;
                this.boards.push(board);
                while (node.previous != null) {
                    node = node.previous;
                    this.boards.push(node.board);
                }
                return;
            }
            node.moves++;
            nodeTwin.moves++;
            Iterable<Board> neighbors = board.neighbors();
            for (Board neighbor : neighbors) {
                if (node.previous != null
                        && neighbor.equals(node.previous.board)) {
                    continue;
                }
                SearchNode newNode = new SearchNode(neighbor, node.moves, node);
                minPQ.insert(newNode);
            }
            Iterable<Board> neighborsTwin = boardTwin.neighbors();
            for (Board neighbor : neighborsTwin) {
                if (nodeTwin.previous != null
                        && neighbor.equals(nodeTwin.previous.board)) {
                    continue;
                }
                SearchNode newNode = new SearchNode(neighbor, nodeTwin.moves,
                        nodeTwin);
                minPQTwin.insert(newNode);
            }
        }
    }

    /*
     * is the initial board solvable?
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /*
     * min number of moves to solve initial board; -1 if no solution
     */
    public int moves() {
        if (isSolvable) {
            return boards.size() - 1;
        } else {
            return -1;
        }
    }

    /*
     * sequence of boards in a shortest solution; null if no solution
     */
    public Iterable<Board> solution() {
        if (isSolvable) {
            return boards;
        } else {
            return null;
        }
    }

    /*
     * solve a slider puzzle (given below)
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
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