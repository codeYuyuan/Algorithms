/**
 * Created by yuyuanliu on 2017-01-23.
 */
public class Outcast {
    private WordNet wordNet;
    public Outcast(WordNet wordnet){
        this.wordNet = wordnet;
    }         // constructor takes a WordNet object
    public String outcast(String[] nouns){
        int max = 0;
        String target = null;
        for(String nouni: nouns){
            int dis = 0;
            for (String noun : nouns){
                dis += wordNet.distance(nouni,noun);
            }
            if (dis > max){
                max = dis;
                target = nouni;
            }
        }
        return target;
    }   // given an array of WordNet nouns, return an outcast
    public static void main(String[] args){

    }  // see test client below
}