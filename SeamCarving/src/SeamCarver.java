/**
 * Created by yuyuanliu on 2017-01-26.
 */
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;
import java.awt.*;

public class SeamCarver {
    private Picture pic;
    private int[] edgeTo;
    private double[] distanceTo;
    private double[] energe;
    public SeamCarver(Picture picture){
        pic = new Picture(picture);
    }                // create a seam carver object based on the given picture
    public Picture picture(){
        return new Picture(pic);
    }                          // current picture
    public     int width(){
        return pic.width();
    }                         // width of current picture
    public     int height(){
        return pic.height();
    }                           // height of current picture
    public  double energy(int x, int y){
        if(x > pic.width() -1 || y > pic.height()-1 || x < 0|| y < 0)
            throw new IndexOutOfBoundsException("energy method outbounds");
        if(x == pic.width() -1 || y ==pic.height()-1 || x == 0|| y==0)
            return 1000.00;
        return Math.sqrt(squareX(x,y) + squareY(x,y));
    }               // energy of pixel at column x and row y
    private double squareX(int x, int y){
        int difR = pic.get(x + 1, y).getRed() - pic.get(x - 1, y).getRed();
        int difG = pic.get(x + 1, y).getGreen() - pic.get(x - 1, y).getGreen();
        int difB = pic.get(x + 1, y).getBlue() - pic.get(x - 1, y).getBlue();
        double energe = difR * difR + difG * difG + difB * difB;
        return energe;
    }
    private double squareY(int x, int y){
        int difR = pic.get(x, y + 1).getRed() - pic.get(x, y - 1).getRed();
        int difG = pic.get(x, y + 1).getGreen() - pic.get(x, y - 1).getGreen();
        int difB = pic.get(x, y + 1).getBlue() - pic.get(x, y - 1).getBlue();
        double energe = difR * difR + difG * difG + difB * difB;
        return energe;
    }
    private int position(int x, int y){
        return x + y*width();
    }
    private int positionX(int position){
        return position%width();
    }
    private int positionY(int position){
        return position/width();
    }
    private void relax(int from ,int to){
        if(distanceTo[to] > distanceTo[from] + energe[to]){
            distanceTo[to] = distanceTo[from] + energe[to];
            edgeTo[to] = from;
        }
    }
    public   int[] findHorizontalSeam(){
        Picture trans = transpose(pic);
        SeamCarver transSeamCarver = new SeamCarver(trans);
        return transSeamCarver.findVerticalSeam();
    }               // sequence of indices for horizontal seam
    private Picture transpose(Picture picture){
        Picture transPic = new Picture(picture.height(),picture.width());
        for(int x =0; x<transPic.width();x++){
            for (int y =0; y <transPic.height(); y++){
                transPic.set(x ,y , picture.get(y,x));
            }
        }
        return transPic;
    }
    public   int[] findVerticalSeam() {
        int size = width()*height();
        energe = new double[size];
        edgeTo = new int[size];
        distanceTo = new double[size];
        int point;
        for (int x = 0; x < width();x++){
            for (int y = 0; y < height(); y++){
                point  = position(x, y);
                if (y == 0){
                    distanceTo[point]= 0;
                }else
                    distanceTo[point] = Double.POSITIVE_INFINITY;
                edgeTo[point] = -1;
                energe[point] = energy(x,y);
            }
        }
        for (int y = 0; y<height() - 1;y++){
            for (int x = 0; x < width(); x++){
                point = position(x,y);
                if(x - 1 >= 0)
                    relax(point, position(x - 1,y + 1));
                if (x + 1 < width())
                    relax(point, position(x + 1, y + 1));
                relax(point,position(x, y + 1));
            }
        }
        double shortest = Double.POSITIVE_INFINITY;
        int endPoint = 0;
        for (int x = 0; x < width(); x++){
            if (distanceTo[position(x, height()-1)]<shortest){
                shortest = distanceTo[position(x, height()-1)];
                endPoint = position(x, height()-1);
            }
        }
        return verticalSeam(endPoint);
    }
    private int[] verticalSeam(int end){
        int[] verticalSeam = new int[height()];
        int endPoint = end;
        while(endPoint >= 0) {
            verticalSeam[positionY(endPoint)] = positionX(endPoint);
            endPoint = edgeTo[endPoint];
        }
        return  verticalSeam;

    }



    public    void removeHorizontalSeam(int[] seam){
        if(seam == null)
            throw new NullPointerException();
        if (seam.length!= width() || height() <=1)
            throw new IllegalArgumentException();
        for (int id: seam)
            if (id < 0 || id > height()-1)
                throw new IllegalArgumentException();
        for (int i =0; i<seam.length - 1;i++){
            if (seam[i+1] - seam[i] > 1 || seam[i+1] - seam[i] < -1)
                throw new IllegalArgumentException();
        }
        Picture newPic = new Picture(pic.width(),pic.height() - 1);
        for(int x = 0;x <newPic.width();x++){
            for (int y =0; y <newPic.height(); y++){
                if (y < seam[x])
                    newPic.set(x,y,pic.get(x,y));
                else
                    newPic.set(x,y,pic.get(x,y+1));
            }
        }
        pic = newPic;
        distanceTo =null;
        edgeTo =null;
        energe = null;
    }   // remove horizontal seam from current picture
    public    void removeVerticalSeam(int[] seam){
        if(seam == null)
            throw new NullPointerException();
        if (seam.length!=height() || width()<=1)
            throw new IllegalArgumentException();
        for (int id: seam)
            if (id < 0 || id > width()-1)
                throw new IllegalArgumentException();
        for (int i =0; i<seam.length - 1;i++){
            if (seam[i+1] - seam[i] > 1 || seam[i+1] - seam[i] < -1)
                throw new IllegalArgumentException();
        }
        Picture newPic = new Picture(pic.width() - 1,pic.height());
        for (int y =0; y <newPic.height(); y++){
            for(int x = 0;x <newPic.width();x++) {
                if (x < seam[y])
                    newPic.set(x,y,pic.get(x,y));
                else
                    newPic.set(x,y,pic.get(x+1,y));
            }
        }
        pic = newPic;
        distanceTo =null;
        edgeTo =null;
        energe = null;
    }    // remove vertical seam from current picture
}
