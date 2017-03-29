/**
 * Created by yuyuanliu on 2017-01-16.
 */
import edu.princeton.cs.algs4.*;
import java.util.Stack;

public class KdTree{
    private static final double XMIN = 0.0;
    private static final double YMIN = 0.0;
    private static final double XMAX = 1.0;
    private static final double YMAX = 1.0;
    private Node root;
    private int size;

    private class Node{
        private Point2D p;
        private Node left, right;
        private RectHV rect;
        private Node(Point2D p, RectHV rect){
            this.p = p;
            this.rect = rect;
            left = null;
            right = null;
        }
    }
    public KdTree(){
        size = 0;
    }                               // construct an empty set of points
    public boolean isEmpty() {
        return size ==0;
    }                     // is the set empty?
    public int size() {
        return size;
    }                         // number of points in the set
    public void insert(Point2D p){
        if(!contains(p))
            root = insert(root, p, XMIN, YMIN,XMAX, YMAX, 0);
    }              // add the point to the set (if it is not already in the set)
    private Node insert(Node x, Point2D p, double xmin, double ymin, double xmax, double ymax, int level){
        if(x == null){
            size ++;
            return new Node(p,new RectHV(xmin, ymin, xmax, ymax));
        }
        int cmp = cmp(p, x.p, level);
        if (cmp < 0){
            if (level % 2 ==0){
                x.left = insert(x.left, p, xmin,ymin,x.p.x(),ymax,level++);
            }else{
                x.left = insert(x.left, p, xmin,ymin,xmax,x.p.y(),level++);
            }
        }else if(cmp > 0){
            if (level % 2 == 0){
                x.right = insert(x.right, p, x.p.x(),ymin,xmax,ymax,level++);
            }else{
                x.right = insert(x.right, p, xmin,x.p.y(),xmax,ymax, level++);
            }
        }
        return x;
    }
    private int cmp(Point2D a, Point2D b, int level){
        if(level % 2 ==0){
            int cmpresult = Double.valueOf(a.x()).compareTo(Double.valueOf(b.x()));
            if (cmpresult == 0)
                return Double.valueOf(a.y()).compareTo(Double.valueOf(b.y()));
            return cmpresult;
        }else{
            int cmpResult = Double.valueOf(a.y()).compareTo(Double.valueOf(b.y()));
            if (cmpResult ==0)
                return Double.valueOf(a.x()).compareTo(Double.valueOf(b.x()));
            return cmpResult;
        }
    }
    public boolean contains(Point2D p){
        return (contains(root, p , 0) != null);
    }
    // does the set contain point p?
    private Point2D contains(Node d, Point2D p, int level){
        while( d!= null){
            int cmp = cmp(p, d.p, 0);
            if(cmp < 0){
                return contains(d.left, p , level++);
            }else if(cmp >0)
                return contains(d.right, p , level++);
            else
                return d.p;
        }
        return null;
    }
    public void draw(){
        StdDraw.clear();
        drawLine(root, 0);
    }                        // draw all points to standard draw
    private void drawLine(Node x, int level) {
        if (x != null) {
            drawLine(x.left, level + 1);

            StdDraw.setPenRadius();
            if (level % 2 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
            }

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            x.p.draw();

            drawLine(x.right, level + 1);
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack= new Stack<Point2D>();
        rangeAdd(root, rect, stack);
        return stack;
    }
    // all points that are inside the rectangle
    private void rangeAdd(Node d, RectHV rect, Stack<Point2D> stack){
        if( d !=null && rect.intersects(d.rect)){
            if(rect.contains(d.p)){
                stack.push(d.p);
            }
            rangeAdd(d.left, rect, stack);
            rangeAdd(d.right, rect, stack);
        }
    }
    public Point2D nearest(Point2D p){
        if(isEmpty())
            return null;
        return nearest(root, p, null);
    }            // a nearest neighbor in the set to point p; null if the set is empty
    private Point2D nearest(Node d, Point2D p, Point2D min){
        if(d != null){
            if(min == null){
                min = d.p;
            }
            if(min.distanceSquaredTo(p) >= d.rect.distanceSquaredTo(p)){
                if(d.p.distanceSquaredTo(p) < min.distanceSquaredTo(p))
                    min = d.p;
                if(d.right != null && d.right.rect.contains(p)){
                    min = nearest(d.right, p, min);
                    min = nearest(d.left, p , min);
                }else{
                    min = nearest(d.left, p , min);
                    min = nearest(d.right, p, min);
                }

            }
        }
        return min;
    }
    public static void main(String[] args){

    }                  // unit testing of the methods (optional)
}

