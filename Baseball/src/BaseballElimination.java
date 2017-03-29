import edu.princeton.cs.algs4.*;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by yuyuanliu on 2017-01-31.
 */
public class BaseballElimination {
    private int N;
    private String[] teams;
    private int[] win, lose, remaining;
    private int[][] versus;
    private HashMap<String, Integer> nameToNum;

    public BaseballElimination(String filename){
        In in = new In(filename);
        N = Integer.parseInt(in.readLine());
        teams = new String[N];
        win = new int[N];
        lose = new int[N];
        remaining = new int[N];
        versus = new int[N][N];
        nameToNum = new HashMap<String, Integer>();
        for (int i=0; i< N ; i++){
            if(in.hasNextLine()){
                String[] data = in.readLine().trim().split("\\s+");
                teams[i] = data[0];
                nameToNum.put(teams[i], i);
                win[i]= Integer.parseInt(data[1]);
                lose[i]= Integer.parseInt(data[2]);
                remaining[i]= Integer.parseInt(data[3]);
                for (int j=0; j < N;j++){
                    versus[i][j] = Integer.parseInt(data[j+4]);
                }
            }
        }

    }                    // create a baseball division from given filename in format specified below
    public int numberOfTeams(){
        return N;

    }                        // number of teams
    public Iterable<String> teams(){
        Stack<String> teamstack = new Stack<String>();
        for (int i=0; i<N; i++)
            teamstack.push(teams[i]);
        return teamstack;
    }                                // all teams
    public int wins(String team){
        if(!nameToNum.containsKey(team))
            throw new IllegalArgumentException();
        int num = nameToNum.get(team);
        return win[num];
    }                      // number of wins for given team
    public int losses(String team){
        if(!nameToNum.containsKey(team))
            throw new IllegalArgumentException();
        int num = nameToNum.get(team);
        return lose[num];
    }                 // number of losses for given team
    public int remaining(String team){
        if(!nameToNum.containsKey(team))
            throw new IllegalArgumentException();
        int num = nameToNum.get(team);
        return remaining[num];
    }                 // number of remaining games for given team
    public int against(String team1, String team2){
        if(!nameToNum.containsKey(team1) || !nameToNum.containsKey(team2))
            throw new IllegalArgumentException();
        int num1 = nameToNum.get(team1);
        int num2 = nameToNum.get(team2);
        return versus[num1][num2];
    }    // number of remaining games between team1 and team2
    public boolean isEliminated(String team){
        if(!nameToNum.containsKey(team))
            throw new IllegalArgumentException();
        int target = nameToNum.get(team);
        for(int v=0; v< N;v++){
            if(win[target]+remaining[target] - win[v]<0)
                return true;
        }
        FlowNetwork fn = flowNetwork(team);
        int game = N * (N - 1) /2;
        int V = N + game + 2; //2 + N*(N - 1)/2;
        int s = N + game;
        int t = s + 1;
        new FordFulkerson(fn, s ,t);
        for(FlowEdge sto : fn.adj(s)){
            if (sto.flow()!=sto.capacity())
                return true;
        }
        return false;
    }              // is given team eliminated?
    private FlowNetwork flowNetwork(String team){
        if(!nameToNum.containsKey(team))
            throw new IllegalArgumentException();
        FlowNetwork fn;
        int target = nameToNum.get(team);
        int game = N * (N - 1) /2;
        int V = N + game + 2; //2 + N*(N - 1)/2;
        int s = N + game;
        int t = s + 1;
        int gameVertice = 0;
        fn = new FlowNetwork(V);
        for (int v = 0; v < N; v++){
            if(win[target]+remaining[target] - win[v]<0)
                return null;
            for (int w = v + 1; w < N; w++) {
                FlowEdge sTo = new FlowEdge(s,gameVertice, versus[v][w]);
                FlowEdge gameTo1 = new FlowEdge(gameVertice, game+v, Double.POSITIVE_INFINITY);
                FlowEdge gameTo2 = new FlowEdge(gameVertice, game+w, Double.POSITIVE_INFINITY);
                fn.addEdge(sTo);
                fn.addEdge(gameTo1);
                fn.addEdge(gameTo2);
                gameVertice++;
            }
            FlowEdge tot = new FlowEdge(game + v , t, win[target]+remaining[target] - win[v]);
            fn.addEdge(tot);
        }
        return fn;
    }
    public Iterable<String> certificateOfElimination(String team){
        if(!nameToNum.containsKey(team))
            throw new IllegalArgumentException();
        Stack<String> certificate = new Stack<String>();
        if (isEliminated(team)){
            int target = nameToNum.get(team);
            for(int v=0; v< N;v++){
                if(win[target]+remaining[target] - win[v]<0){
                    certificate.push(teams[v]);
                    return certificate;
                }
            }
            FlowNetwork fn = flowNetwork(team);
            int game = N * (N - 1) /2;
            int s = N + game;
            int t = s + 1;
            FordFulkerson ff = new FordFulkerson(fn, s ,t);
            for(int i = game; i< s; i ++){
                if (ff.inCut(i)){
                    String name = teams[i - game];
                    certificate.push(name);
                }
            }
            return certificate;
        }
        return null;
    }  // subset R of teams that eliminates given team; null if not eliminated
//    public static void main(String[] args) {
//        BaseballElimination division = new BaseballElimination("/teams29.txt");
//        for (String name : division.teams()){
//            StdOut.println(name);
//        }
//        for (String team : division.teams()) {
//            if (division.isEliminated(team)) {
//                StdOut.print(team + " is eliminated by the subset R = { ");
//                for (String t : division.certificateOfElimination(team)) {
//                    StdOut.print(t + " ");
//                }
//                StdOut.println("}");
//            }
//            else {
//                StdOut.println(team + " is not eliminated");
//            }
//        }
//
//    }
}
