import edu.princeton.cs.algs4.StdOut;
//
import java.util.Stack;
//
///**
// * Created by yuyuanliu on 2017-01-12.
// */
//public class Board {
//    private final int N;
//    private final int[][] blocks;
//
//    public Board(int[][] blocks) {
//        this.blocks = blocks;
//        this.N = blocks[0].length;
//    }          // construct a board from an n-by-n array of blocks
//    // (where blocks[i][j] = block in row i, column j)
//    public int dimension(){
//        return N;
//    }                 // board dimension n
//    private int getIndex(int x, int y){
//        if(x == N-1 && y== N -1 )
//            return 0;
//        return x * N + y + 1;
//    }
//    private int getX(int num){
//        return (num - 1) /N ;
//    }
//    private int getY(int num){
//        return num - 1 - N * getX(num);
//    }
//    public int hamming() {
//        int count = 0;
//        for(int i = 0; i < N; i++){
//            for (int j = 0; j < N; j++){
//                if( blocks[i][j] != 0 && blocks[i][j] != getIndex(i,j)){
//                    count++;
//                }
//            }
//        }
//        return count;
//    }                  // number of blocks out of place
//    public int manhattan(){
//        int count = 0;
//        for(int i = 0; i < N; i++){
//            for (int j = 0; j < N; j++){
//                if(blocks[i][j] != 0 && blocks[i][j] != getIndex(i,j)){
//                    int num = blocks[i][j];
//                    int x = getX(num);
//                    int y = getY(num);
//                    int dif =  Math.abs( x - i) + Math.abs( y - j);
//                    count += dif;
//                }
//            }
//        }
//        return count;
//
//    }                 // sum of Manhattan distances between blocks and goal
//    public boolean isGoal(){
//        return this.hamming()==0;
//    }                // is this board the goal board?
//    private Board copy(){
//        Board cloneBoard = new Board(new int[N][N]);
//        for(int x = 0; x< N ; x++){
//            for(int y = 0; y < N ; y++){
//                cloneBoard.blocks[x][y] = this.blocks[x][y];
//            }
//        }
//        return cloneBoard;
//    }
//    public Board twin(){
//        Board board = new Board(blocks);
//
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N - 1; j++) {
//                if (blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
//                    board.swap(i, j, i, j + 1);
//                    return board;
//                }
//            }
//        }
//
//        return board;
//
//    }
//    private boolean swap(int i, int j, int it, int jt) {
//        if (it < 0 || it >= N || jt < 0 || jt >= N) {
//            return false;
//        }
//        int temp = blocks[i][j];
//        blocks[i][j] = blocks[it][jt];
//        blocks[it][jt] = temp;
//        return true;
//    }
//    // a board that is obtained by exchanging any pair of blocks
//    public boolean equals(Object y) {
//        if(y == this)
//            return true;
//        else if(y == null)
//            return false;
//        else if(this.getClass()!= y.getClass())
//            return false;
//        else{
//            //if(((Board) y).blocks.equals(this.blocks))
//                //return true;
//                if(this.dimension()!=((Board) y).dimension())
//                    return false;
//                for(int i = 0; i < N; i++){
//                     for (int j = 0; j < N; j++){
//                       if(this.blocks[i][j] != ((Board) y).blocks[i][j]){
//                           return false;
//                       }
//
//                     }
//                 }
//                 return true;
//            }
//    }       // does this board equal y?
//    public Iterable<Board> neighbors() {
//        Stack<Board> boardStack = new Stack<Board>();
//        int[] point0 = new int[2];
//        for(int x = 0; x <N; x++){
//            for (int y = 0; y< N; y++){
//                if(blocks[x][y] == 0){
//                    point0[0] = x;
//                    point0[1] =y;
//                }
//            }
//        }
//        if(point0[0] > 0){
//            Board neighbor1 = copy();
//            int[] neiborPoint1 = {point0[0]-1, point0[1]};
//            neighbor1.changePoint(point0, neiborPoint1);
//            boardStack.push(neighbor1);
//        }
//        if(point0[0] + 1 < N){
//            Board neighbor2 = copy();
//            int[] neiborPoint2 = {point0[0]+ 1, point0[1]};
//            neighbor2.changePoint(point0, neiborPoint2);
//            boardStack.push(neighbor2);
//        }
//        if (point0[1] > 0){
//            Board neighbor3 = copy();
//            int[] neiborPoint3 = {point0[0], point0[1] - 1};
//            neighbor3.changePoint(point0, neiborPoint3);
//            boardStack.push(neighbor3);
//        }
//        if (point0[1] + 1 < N){
//            Board neighbor4 = copy();
//            int[] neiborPoint4 = {point0[0], point0[1] + 1};
//            neighbor4.changePoint(point0, neiborPoint4);
//            boardStack.push(neighbor4);
//        }
//        return boardStack;
//    }// all neighboring boards
//    private void changePoint(int[] point1, int[] point2){
//        int x1 = point1[0];
//        int x2 = point2[0];
//        int y1 = point1[1];
//        int y2 = point2[1];
//        int temp = blocks[x1][y1];
//        blocks[x1][y1] = blocks[x2][y2];
//        blocks[x2][y2] = temp;
//
//    }
//    public String toString(){
//        StringBuilder s = new StringBuilder();
//        s.append(N + "\n");
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                s.append(String.format("%2d ", blocks[i][j]));
//            }
//            s.append("\n");
//        }
//        return s.toString();
//    }               // string representation of this board (in the output format specified below)
//
////    public static void main(String[] args){
////
////        int[][] test1 = {
////                {0,2,4},
////                {1,3,6},
////                {8,7,5},
////                };
////        int[][] test2 = {
////                {0,2,4},
////                {1,3,6},
////                {8,7,5},
////        };
////        Board test = new Board(test1);
////        Board test3 = new Board(test2);
////        StdOut.println(test.equals(test3));
////        StdOut.println(test.toString());
////        Board twin = test.twin();
////        StdOut.println(twin.toString());
////        StdOut.println(test.hamming() + " " + test.manhattan());
////        StdOut.println("==================");
////        Iterable<Board>  a = test.neighbors();
////        for(Board id : a){
////            StdOut.println(id.toString());
////            StdOut.println(id.hamming() + " " + id.manhattan());
////            StdOut.println("==================");
////
////        }
////
////    } // unit tests (not graded)
//}
public class Board {
    private final int N;
    private final int[][] tiles;

    /*
     * construct a board from an N-by-N array of blocks (where blocks[i][j] =
     * block in row i, column j)
     */
    public Board(int[][] blocks) {
        N = blocks.length;
        tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }
    }

    private int[][] createGoalBoard() {
        int[][] array = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                array[i][j] = goalValueAt(i, j);
            }
        }

        return array;
    }

    /*
     * board dimension N
     */
    public int dimension() {
        return N;
    }

    /*
     * number of blocks out of place
     */
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != goalValueAt(i, j) && !isEnd(i, j)) {
                    sum++;
                }
            }
        }
        return sum;
    }

    private int goalValueAt(int i, int j) {
        if (isEnd(i, j)) {
            return 0;
        }
        return 1 + i * N + j;
    }

    private boolean isEnd(int i, int j) {
        return i == N - 1 && j == N - 1;
    }

    /*
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int value = tiles[i][j];
                if (value != 0 && value != goalValueAt(i, j)) {
                    int initialX = (value - 1) / N;
                    int initialY = value - 1 - initialX * N;
                    int distance = Math.abs(i - initialX)
                            + Math.abs(j - initialY);
                    sum += distance;
                }
            }
        }
        return sum;
    }

    /*
     * is this board the goal board?
     */
    public boolean isGoal() {
        return tilesEquals(this.tiles, createGoalBoard());
    }

    private boolean tilesEquals(int[][] first, int[][] second) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (first[i][j] != second[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * a board obtained by exchanging two adjacent blocks in the same row
     */
    public Board twin() {
        Board board = new Board(tiles);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (tiles[i][j] != 0 && tiles[i][j + 1] != 0) {
                    board.swap(i, j, i, j + 1);
                    return board;
                }
            }
        }

        return board;
    }

    private boolean swap(int i, int j, int it, int jt) {
        if (it < 0 || it >= N || jt < 0 || jt >= N) {
            return false;
        }
        int temp = tiles[i][j];
        tiles[i][j] = tiles[it][jt];
        tiles[it][jt] = temp;
        return true;
    }

    /*
     * does this board equal x? (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object x) {
        if (x == this)
            return true;
        if (x == null)
            return false;
        if (x.getClass() != this.getClass())
            return false;

        Board that = (Board) x;
        return this.N == that.N && tilesEquals(this.tiles, that.tiles);
    }

    /*
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        int i0 = 0, j0 = 0;
        boolean found = false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        Stack<Board> boards = new Stack<Board>();
        Board board = new Board(tiles);
        boolean isNeighbor = board.swap(i0, j0, i0 - 1, j0);
        if (isNeighbor) {
            boards.push(board);
        }
        board = new Board(tiles);
        isNeighbor = board.swap(i0, j0, i0, j0 - 1);
        if (isNeighbor) {
            boards.push(board);
        }
        board = new Board(tiles);
        isNeighbor = board.swap(i0, j0, i0 + 1, j0);
        if (isNeighbor) {
            boards.push(board);
        }
        board = new Board(tiles);
        isNeighbor = board.swap(i0, j0, i0, j0 + 1);
        if (isNeighbor) {
            boards.push(board);
        }

        return boards;
    }

    /*
     * string representation of the board (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}