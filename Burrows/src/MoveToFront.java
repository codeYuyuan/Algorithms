/**
 * Created by yuyuanliu on 2017-02-11.
 */
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] dict = initDict();
        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int index = findIndex(dict, c);
            BinaryStdOut.write(index, 8);
        }
        BinaryStdOut.flush();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] dict = initDict();
        while(!BinaryStdIn.isEmpty()) {
            int index = BinaryStdIn.readInt(8);
            BinaryStdOut.write(dict[index], 8);
            moveToFront(dict, index);
        }
        BinaryStdOut.flush();
    }

    private static void moveToFront(char[] dict, int index) {
        char c = dict[index];
        for(int i = index; i > 0; i--) dict[i] = dict[i - 1];
        dict[0] = c;
    }

    private static char[] initDict() {
        char[] dict = new char[256];
        for(int i = 0; i < 256; i++) dict[i] = (char)i;
        return dict;
    }

    private static int findIndex(char[] dict, char c) {
        int i = 0;
        for(; i < 256; i++) {
            if(dict[i] == c) break;
        }
        moveToFront(dict, i);
        return i;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else decode();
    }
}