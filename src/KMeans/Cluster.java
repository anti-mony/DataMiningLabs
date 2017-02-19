package KMeans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sushant Bansal, Pragya Chaturvedi, Ishan Tyagi
 * K Means Clustering
 *
 */

public class Cluster {

    private List<Point> points;
    private Point centroid;
    private int id;
    private double cohesion = 0;


    public Cluster(int id) {
        this.id = id;
        this.points = new ArrayList<Point>();
        this.centroid = null;
        this.cohesion = 0;
    }

    public int getId(){
        return id;
    }

    public double getCohesion(){
        return cohesion;
    }

    public void setCohesion(double cohesion) {
        this.cohesion = cohesion;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    /**
     * Clears out the points to reset the Cluster
     */
    public void clear() {
        points.clear();
    }

}

