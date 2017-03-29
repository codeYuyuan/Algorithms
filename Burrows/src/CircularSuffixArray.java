import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by yuyuanliu on 2017-02-09.
 */
public class CircularSuffixArray {
    private String input;
    private Integer[] index;
    public CircularSuffixArray(String s){
        if  (s == null)
            throw new NullPointerException();
        this.input = s;
        index = new Integer[s.length()];
        for(int i = 0; i< s.length();i++){
            index[i] = i;
        }
        Arrays.sort(index, new Csacomparator());

    }  // circular suffix array of s
    private class Csacomparator implements Comparator<Integer>{
        public int compare(Integer first, Integer second){
            int firstIndex = first;
            int secondIndex = second;
            // for all characters
            for (int i = 0; i < input.length(); i++) {
                // if out of the last char then start from beginning
                if (firstIndex > input.length() - 1)
                    firstIndex = 0;
                if (secondIndex > input.length() - 1)
                    secondIndex = 0;
                // if first string > second
                if (input.charAt(firstIndex) < input.charAt(secondIndex))
                    return -1;
                else if (input.charAt(firstIndex) > input.charAt(secondIndex))
                    return 1;
                // watch next chars
                firstIndex++;
                secondIndex++;
            }
            // equal strings
            return 0;

        }
    }
    public int length(){
        return input.length();
    }                   // length of s
    public int index(int i){
        if (i < 0 || i >= length()) throw new IndexOutOfBoundsException();
        return index[i];
    }               // returns index of ith sorted suffix
    public static void main(String[] args){
        String test = "ABRACADABRA!";
        CircularSuffixArray csu = new CircularSuffixArray(test);
        for(int i = 0; i< csu.length();i++){
            System.out.print(csu.index(i)+"\t");
        }
        System.out.println();
        for (int i=0; i<csu.length();i++){
            System.out.print(test.charAt(csu.index(i)));
        }

    }// unit testing of the methods (optional)
}