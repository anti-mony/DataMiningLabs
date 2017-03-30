package KMeans;
import java.util.ArrayList;

/**
 * Created by Sushant Bansal, Pragya Chaturvedi, Ishan Tyagi
 * K Means Clustering
 */

public class Point {

    private ArrayList<Double> points;
    private String type = "";
    private double distanceToCentroid = 0;

    public Point(ArrayList<Double> x, String type)
    {
        this.setPoints(x);
        this.setType(type);
        this.distanceToCentroid = 0;
    }

    /**
     * Calculates the distance between two points using Euclidean distance
     *
     * @param p<Point>,        a given Point
     * @param centroid<Point>, to compare with a given Centroid
     * @return distance<Double>, between the Point and the Centroid
     */

    protected static double distance(Point p, Point centroid) {
        double sum = 0.0;
        for (int i = 0; i < p.getPoints().size(); i++) {
            sum += Math.pow((p.getPoints().get(i) - centroid.getPoints().get(i)), 2.0);
        }
        return Math.sqrt(sum);
    }

    public double getDistanceToCentroid(){
        return distanceToCentroid;
    }

    public void setDistanceToCentroid(double distanceToCentroid) {
        this.distanceToCentroid = distanceToCentroid;
    }

    public ArrayList<Double> getPoints()  {
        return this.points;
    }

    public void setPoints(ArrayList<Double> x) {
        this.points = x;
    }

    public String getType()  {
        return this.type;
    }

    public void setType(String x) {
        this.type = x;
    }

    public String toString() {
        int i, j = 0;
        String out = "";
        for (i = 0; i<points.size(); i++){
            out += "" + points.get(i) + " ";
        }
//        out+="]";
        return out;
    }
}

