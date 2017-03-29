import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
/**
 * Created by yuyuanliu on 2017-02-10.
 */
public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray array = new CircularSuffixArray(s);
        for (int i = 0; i < array.length(); i++) {
            if (array.index(i) == 0) {
                BinaryStdOut.write(i, 32);
                break;
            }
        }
        for (int i = 0; i < array.length(); i++) {
            BinaryStdOut.write(s.charAt((array.index(i) + s.length() - 1) % s.length()));
        }
        BinaryStdOut.flush();

    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode(){
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int[] count = new int[257];
        int[] next = new int[s.length()];
        for (int i = 0; i < s.length(); i++) count[s.charAt(i) + 1]++;
        for (int i = 1; i <= 256; i++) count[i] += count[i - 1];
        for (int i = 0; i < s.length(); i++) {
            int index = count[s.charAt(i)]++;
            next[index] = i;
        }
        int i = 0;
        while(i < s.length()) {
            BinaryStdOut.write(s.charAt(next[first]));
            first = next[first];
            i++;
        }
        BinaryStdOut.flush();

    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args){
        if (args[0].equals("-")) BurrowsWheeler.encode();
        else BurrowsWheeler.decode();
    }
}
