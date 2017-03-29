/**
 * Created by yuyuanliu on 2017-01-22.
 */
import edu.princeton.cs.algs4.*;

import java.util.HashMap;

public class WordNet {
    private HashMap<Integer, String> id2Noun;
    private HashMap<String, Bag<Integer>> noun2Id;
    private Digraph digraph;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if(synsets == null || hypernyms ==null)
            throw new NullPointerException();
        id2Noun = new HashMap<Integer, String>();
        noun2Id = new HashMap<String, Bag<Integer>>();
        In inSynsets = new In(synsets);
        while(inSynsets.hasNextLine()){
            String[] lineField = inSynsets.readLine().split(",");
            int id = Integer.parseInt(lineField[0]);
            String synset = lineField[1];
            String[] nouns = synset.split(" ");
            id2Noun.put(id,synset);
            for(String noun : nouns){
                if(noun2Id.containsKey(noun)){
                    Bag<Integer> bag = noun2Id.get(noun);
                    bag.add(id);
                    noun2Id.put(noun,bag);
                }else{
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(new Integer(id));
                    noun2Id.put(noun,bag);
                }
            }
        }
        digraph = new Digraph(id2Noun.size());
        In inHypernyms = new In(hypernyms);
        while(inHypernyms.hasNextLine()){
            String[] lineField = inHypernyms.readLine().split(",");
            int id = Integer.parseInt(lineField[0]);
            Bag<Integer> bag = new Bag<Integer>();
            for(int i = 1; i < lineField.length; i++){
                int hypernymId = Integer.parseInt(lineField[i]);
                bag.add(new Integer(hypernymId));
                digraph.addEdge(id,hypernymId);
            }
        }
        DirectedCycle cyc = new DirectedCycle(digraph);
        if(cyc.hasCycle())
            throw new IllegalArgumentException("Not acyclic");
        int root = 0;
        for (int i = 0; i < digraph.V();i++){
            if(!digraph.adj(i).iterator().hasNext())
                root++;
        }
        if(root!=1){
            throw new IllegalArgumentException("Not a rooted DAG");
        }

    }
    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return noun2Id.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word ==null)
            throw new NullPointerException();
        return noun2Id.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if (!isNoun(nounA)||!isNoun(nounB))
            throw new IllegalArgumentException();
        Bag<Integer> idsA = noun2Id.get(nounA);
        Bag<Integer> idsB = noun2Id.get(nounB);
        SAP sap = new SAP(digraph);
        return sap.length(idsA,idsB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if (!isNoun(nounA)||!isNoun(nounB))
            throw new IllegalArgumentException();
        Bag<Integer> idsA = noun2Id.get(nounA);
        Bag<Integer> idsB = noun2Id.get(nounB);
        SAP sap = new SAP(digraph);
        int ancestor = sap.ancestor(idsA,idsB);
        return id2Noun.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet w = new WordNet("/synsets.txt", "/hypernyms.txt");
        System.out.println(w.sap("Rameses_the_Great", "Henry_Valentine_Miller"));
        System.out.println(w.distance("Rameses_the_Great", "Henry_Valentine_Miller"));
    }
}