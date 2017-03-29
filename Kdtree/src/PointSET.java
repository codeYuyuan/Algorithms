/**
 * Created by yuyuanliu on 2017-01-16.
 */
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Comparator;
import java.util.Stack;
import java.util.TreeSet;

public class PointSET {
    private int size;
    private TreeSet<Point2D> set;
    public PointSET() {
        size = 0;
        set = new TreeSet<Point2D>();
    } ;                              // construct an empty set of points
    public boolean isEmpty() {
        return size==0;
    }                      // is the set empty?
    public int size() {
        return size;
    }                         // number of points in the set
    public void insert(Point2D p) {
        if(p == null)
            throw new NullPointerException();
        if(!contains(p)){
            set.add(p);
            size++;
        }
    }              // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p) {
        if(p == null)
            throw new NullPointerException();
        return set.contains(p);
    }            // does the set contain point p?
    public void draw() {
        for( Point2D id: set){
            id.draw();
        }
    }                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null)
            throw new NullPointerException();
        Stack<Point2D> points = new Stack<Point2D>();
        for(Point2D id : set){
            if(rect.contains(id)){
                points.push(id);
            }
        }
        return points;
    }            // all points that are inside the rectangle
    public Point2D nearest(Point2D p) {
        if(p == null)
            throw new NullPointerException();
        double distance, newdistance;
        Point2D nearest;
        distance = Double.MAX_VALUE;
        nearest = null;
        for(Point2D id:set){
            newdistance = p.distanceSquaredTo(id);
            if(newdistance < distance){
                nearest = id;
                distance = newdistance;
            }
        }
        return nearest;
    }            // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }                 // unit testing of the methods (optional)
}
