import edu.princeton.cs.algs4.StdOut;

/**
 * Created by yuyuanliu on 2017-01-10.
 */
public class BruteCollinearPoints {
    private Point[] points;
    public BruteCollinearPoints(Point[] points) {
        checkDuplicatedEntries(points);
        this.points = points;
        if(points == null)
            throw new NullPointerException();
        else{
            for(Point id : points)
                if(id == null)
                    throw new NullPointerException();
        }
    }   // finds all line segments containing 4 points
    public int numberOfSegments(){
        return this.segments().length;
    }        // the number of line segments
    private Point findmin(Point[] subset){
        Point min = subset[0];
        for (int i = 0; i<subset.length; i++){
            if(min.compareTo(subset[i]) == 1)
                min = subset[i];
        }
        return min;
    }
    private Point findmax(Point[] subset){
        Point max = subset[0];
        for (int i = 0; i<subset.length; i++){
            if(max.compareTo(subset[i]) == -1)
                max = subset[i];
        }
        return max;
    }
    public LineSegment[] segments(){
        LineSegment[] lineSegments;
        lineSegments = new LineSegment[points.length];
        for(int i = 0; i < points.length - 3; i++){
            Point p = points[i];
            for(int j = i+1; j < points.length - 2; j++){
                Point q = points[j];
                for(int k = j+1; k < points.length - 1; k++){
                    Point r = points[k];
                    for(int l = k + 1; l < points.length; l ++){
                        Point s = points[l];
                        //StdOut.println(i + ": "+ p.toString() + j + ": "+ q.toString() + k + ": " + r.toString() + l + ": "+ s.toString());
                        double slope[] = new double[3];
                        slope[0] = p.slopeTo(q);
                        slope[1] = p.slopeTo(r);
                        slope[2] = p.slopeTo(s);
                        for(double id:slope){
                            //StdOut.println(id);
                            if(id == Double.NEGATIVE_INFINITY){
                                throw new IllegalArgumentException();
                            }
                        }
                        if(slope[0] == slope[1] && slope[0] == slope[2]){
                            Point[] subset = new Point[4];
                            subset[0] = p;
                            subset[1] = q;
                            subset[2] = r;
                            subset[3] = s;
                            Point min = findmin(subset);
                            Point max = findmax(subset);
                            int index = 0;
                            while(lineSegments[index]!=null){
                                index++;
                            }

                            lineSegments[index] = new LineSegment(min,max);
                        }

                    }
                }
            }
        }
        int index = 0;
        while(lineSegments[index]!=null)
            index++;
        LineSegment[] res = new LineSegment[index];
        for(int id = 0 ; id < index;id++ )
            res[id] = lineSegments[id];
        return res;
    }
    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
    }
    // the line segments
//    public static void main(String[] args){
//        Point[] a = new Point[8];
//        a[7] = new Point(1,2);
//        a[4] = new Point(2,4);
//        a[3] = new Point(3,6);
//        a[2] = new Point(4,8);
//        a[6] = new Point(1,3);
//        a[0] = new Point(2,6);
//        a[5] = new Point(3,9);
//        a[1] = new Point(4, 12);
//        BruteCollinearPoints test1 = new BruteCollinearPoints(a);
//        StdOut.println("res: " + test1.numberOfSegments());
//        for(LineSegment b: test1.segments()){
//            StdOut.println(b.toString());
//        }
//    }

}
