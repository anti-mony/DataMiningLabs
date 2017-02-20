package KMeans;

/**
 * Created by Sushant Bansal, Pragya Chaturvedi, Ishan Tyagi
 * K Means Clustering
 *
 */
import java.io.*;
import java.util.*;


public class Main {

    public static List<Point> data;
    public static String[] args;
    public static int k;
    public static double sse = 0;
    public static List<Cluster> clusterList;
    public static int pointSize = 0;

    public static void main(String[] args) throws IOException {
        Main.args = args;
        if (args.length == 3) {
            start(args);

        }
        else{
            System.out.println("Usage: \n\tjava Main [dataset_file] [k] [output_filename]");
            System.exit(-1);
        }

    }

    /**
     * Sets up the intital random centroids and starts kMeans
     * @param args arguments received
     * @throws FileNotFoundException
     */
    public static void start(String[] args) throws FileNotFoundException {
        data = convertData(args[0]);

        //Receive k
        try{
            k = Integer.parseInt(args[1]);
            if (k <=0){
                System.out.println("k must be an integer > 0");
                System.exit(-1);
            }
        } catch (NumberFormatException e) {
            System.out.println("k must be an integer > 0");
            System.exit(-1);
        }

        //Create clusterList to store all the clusters
        clusterList = new ArrayList<Cluster>();

        //Shuffle dataSet to randomize
        Collections.shuffle(data);

        //Set random Centroids by taking the first k of the shuffled data
        for (int i = 0;i<k; i++) {
            Cluster cluster = new Cluster(i);
            Point centroid = new Point(data.get(0).getPoints(), data.get(0).getType());
            cluster.setCentroid(centroid);
            clusterList.add(cluster);
        }
        //Begin k-means clustering
        kMeans();

    }

    /**
     * Runs k-means clustering method
     */
    public static void kMeans() {
        boolean done = false;
        int count = 0;

        //
        while(!done) {
            //Reset clusters for the next iteration
            resetClusters();

            //Assign points to the closer cluster
            assignPointsToClusters();

            // Get the previous centroids and store to compare for later
            List<Point> lastCentroids = getCentroids();

            //Calculate new centroids and store them
            calculateCentroids();
            List<Point> currentCentroids = getCentroids();

            //Calculates total distance between new and old Centroids
            double distance = 0;
            for(int i = 0; i < lastCentroids.size(); i++) {
                distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
            }
            // if distance is 0 then no change in centroids so we are done!
            if(distance == 0) {
                done = true;

                // calculate the SSE for quality measures
                calculateQuality();
                // print output file
                printOutput(count);

            }

            //Update count
            count++;

        }
    }

    /**
     * Calculates quality measures such as SSE through cohesion
     */
    public static void calculateQuality() {
        double cohesion = 0;

        for(Cluster cluster : clusterList) {
            // calculate cohesion
            for (Point point : cluster.getPoints()){
                cohesion += point.getDistanceToCentroid();
            }
            cluster.setCohesion(cohesion);
            sse+= cohesion;
        }
    }

    /**
     * Clears out all the Clusters points to reset
     */
    public static void resetClusters() {
        for(Cluster cluster : clusterList) {
            cluster.clear();
        }
    }

    /**
     * Returns centroids
     * @return List<Points> of all centroids
     */
    public static List<Point> getCentroids() {
        List<Point> centroids = new ArrayList<Point>();
        for(Cluster cluster : clusterList) {
            Point cent = cluster.getCentroid();
            Point x = new Point(cent.getPoints(),cent.getType());
            centroids.add(x);
        }
        return centroids;
    }

    /**
     * Assigns Points to a cluster through its minimum centroid distance
     */
    public static void assignPointsToClusters() {
        double max = Double.MAX_VALUE;
        double min;
        int clusterID = 0;
        double distance;

        // Go through each point and find its closets centroid
        for(Point point : data) {
            // set min to max value to start off with
            min = max;

            // compare the point to each of the centroids to find its closest
            for(int i = 0; i < k; i++) {
                Cluster c = clusterList.get(i);
                // find distance between the point and the centroid
                distance = Point.distance(point, c.getCentroid());
                // if distance < min the update min
                if(distance < min){
                    min = distance;
                    clusterID = i;
                }
            }
            // add the point to the cluster
            clusterList.get(clusterID).addPoint(point);
            // set distance between the point and the centroid
            point.setDistanceToCentroid(min);
        }
    }

    /**
     * Calculates new centroids by averaging all the Points of each cluster
     */
    public static void calculateCentroids() {
        // Go through one cluster at a time
        for (Cluster cluster : clusterList) {
            // maxType stores all the types of the points to later get the most prevalent type to assign to the centroid
            HashMap<String, Integer> maxType = new HashMap<String, Integer>();

            // avgPoints holds the new centroid values
            ArrayList<Double> avgPoints = new ArrayList<Double>(Collections.nCopies(pointSize, 0.0));

            List<Point> list = cluster.getPoints();
            int listSize = list.size();

            for (Point point : list) {

                // get the values
                ArrayList<Double> values = point.getPoints();
                // add all values of all the points

                for (int i = 0; i < pointSize; i++) {
                    double x = values.get(i);

                    // add to avgPoints
                    avgPoints.set(i, avgPoints.get(i) + x);

                    // updates maxType for the new type it sees
                    /*
                      For example, given : [Iris-virginica, 0]
                        if the point is of type Iris-virginica then it updates the valaue to 1.
                     */
                    maxType.put(point.getType(), maxType.containsKey(point.getType()) ? maxType.get(point.getType()) + 1 : 1);
                }
            }

            // Get the centroid of this cluster to update it
            Point centroid = cluster.getCentroid();

            // if there are no points in the cluster then no need to update
            if (listSize > 0) {

                //average all the points and update avgPoints
                for (int i = 0; i < avgPoints.size(); i++) {

                    avgPoints.set(i, (avgPoints.get(i)/listSize) );
                }

                // update centroid values
                centroid.setPoints(avgPoints);

                // set the class to be the mode of the points in the cluster
                int max = 0;
                String maxClass = "";
                for (String key : maxType.keySet()) {
                    if (maxType.get(key) > max) {
                        max = maxType.get(key);
                        maxClass = key;
                    }
                }
                centroid.setType(maxClass);
            }
            // repeat for next cluster
        }

    }


    /**
     * Converts raw data to Points( ArrayList<Double>, String )
     * returns converted data to List<Point> form
     */
    public static List<Point> convertData(String fileName) throws FileNotFoundException {
        Scanner dataReader = new Scanner(new FileReader(fileName));
        List<Point> dataSet = new ArrayList<Point>();
        ArrayList<Double> list;
        StringTokenizer st = new StringTokenizer("this is a test");
        String type = "";
        boolean valid = false;
        while(dataReader.hasNextLine()){
            String input = dataReader.nextLine();
            if (input.equals("")){
                continue;
            }
            String[] attr = null;
            if (input != null ) {
                attr = input.split("\t",-1);
            }
            if (attr != null){
                list = new ArrayList<>();
                for (String x: attr) {
                    if(isDouble(x)){
                        list.add(Double.parseDouble(x));
                    }
                    else {
                        type = x;
                    }
                }
                Point point = new Point(list, type);
                dataSet.add(point);
            }
        }
        if (dataSet.size() == 0) {
            System.out.println("File Error");
            System.exit(-1);
        }
        pointSize = dataSet.get(0).getPoints().size();
        return dataSet;
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Prints out the output file
     * @param iterations number of iterations k-means ran
     */
    public static void printOutput(int iterations) {
        //Print output of cluster results to output file
        File f = new File(args[2]);
        try {
            PrintWriter pw = new PrintWriter(f);

            pw.println("k: " + k);
            pw.println("SSE: " + sse);
            pw.println("Iterations: " + iterations);
            pw.println();

            for (Cluster cluster : clusterList){
                pw.println("Cluster: " + cluster.getId());
                pw.println("Cohesion: "+ cluster.getCohesion());
                pw.println("Size of Cluster: "+ cluster.getPoints().size() );
                pw.println("Centroid: " + cluster.getCentroid());
                pw.println("Points:");
                for(Point p : cluster.getPoints()) {
                    pw.println("\t"+p+"");
                }
                pw.println();
            }
            pw.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        System.exit(0);
    }
}
