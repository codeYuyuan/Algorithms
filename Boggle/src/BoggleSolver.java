/**
 * Created by yuyuanliu on 2017-02-05.
 */
import java.util.HashSet;
//import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdOut;
public class BoggleSolver {
    private TrieSET trie;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){
        trie = new TrieSET();
        for(String id: dictionary){
            trie.add(id);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        int row = board.rows();
        int col = board.cols();
        HashSet<String> results = new HashSet<>();
        for(int i = 0; i<row;i++){
            for(int j=0; j<col;j++){
                boolean[][] marked = new boolean[row][col];
                String letter = "";
                dfs(marked, i, j, letter, results, board);
            }
        }
        return results;
    }

    private void dfs(boolean[][] marked, int i, int j,String letters ,HashSet<String> result, BoggleBoard board){
        char letter = board.getLetter(i,j);
        String letter1;
        if (letter == 'Q')
            letter1 =letters + "QU";
        else
            letter1 = letters+letter;
        if(marked[i][j])
            return;
        if (trie.contains(letter1)&&letter1.length()>2)
            result.add(letter1);
        if(!trie.hasPrefix(letter1))
            return;
        marked[i][j] =true;
        int iStart = -1, iEnd = 1, jStart = -1, jEnd = 1;
        if( i == 0 ) iStart = 0;
        if( i == board.rows() -1) iEnd = 0;
        if (j==0) jStart = 0;
        if(j == board.cols()-1) jEnd = 0;
        for(int id = iStart; id<=iEnd; id++){
            for(int jd = jStart; jd<=jEnd; jd++){
                if(id ==0 && jd ==0)
                    continue;
                if (i + id >=0 && i+id <board.rows() && j+jd>=0 &&j+jd<board.cols()) {
                    if (!marked[i + id][j + jd]){
                        dfs(marked, i + id, j + jd, letter1, result, board);
                    }
                }
            }
        }
        marked[i][j] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        if (!trie.contains(word))
            return 0;
        int len = word.length();
        if(len <= 2)  return 0;
        else if(len <=4) return 1;
        else if(len<=6) return len-3;
        else if(len==7) return 5;
        return 11;
    }
//    public static void main(String[] args) {
//        In in = new In(args[0]);
//        String[] dictionary = in.readAllStrings();
//        BoggleSolver solver = new BoggleSolver(dictionary);
//        BoggleBoard test = new BoggleBoard("/board-rotavator.txt");
//        BoggleBoard board = new BoggleBoard(args[1]);
//        int score = 0;
//        for (String word : solver.getAllValidWords(board)) {
//            StdOut.println(word);
//            score += solver.scoreOf(word);
//        }
//        StdOut.println("Score = " + score);
//
//   }


}

