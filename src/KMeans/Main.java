package KMeans;

/**
 * Created by Sushant Bansal, Pragya Chaturvedi, Ishan Tyagi
 * K Means Clustering
 */
import java.io.*;
import java.util.*;

public class Main {

    static List<Point> data;
    static String[] args;
    static int k;
    static double sse = 0, startTime = 0, endTime = 0;
    static List<Cluster> clusterList;
    static int pointSize = 0, noOfCLusters;
    static String inputFile, ouputFile;
    public static void main(String[] args) throws IOException {
        start(args);
    }

    /**
     * Sets up the intital random centroids and starts kMeans
     * @param args arguments received
     * @throws FileNotFoundException
     */
    static void start(String[] args) throws FileNotFoundException {
        int iCheck = 0, oCheck = 0, kcheck = 0;
        System.out.println("Executing...");
        startTime = System.nanoTime();
        if (args.length >= 2) {
            if (args[0].equals("-f")) {
                inputFile = args[1];
                iCheck = 1;
            } else if (args[0].equals("-o")) {
                ouputFile = args[1];
                oCheck = 1;
            } else if (args[0].equals("-k")) {
                noOfCLusters = Integer.parseInt(args[1]);
                kcheck = 1;
            }
        }
        if (args.length >= 4) {
            if (args[2].equals("-f")) {
                inputFile = args[3];
                iCheck = 1;
            } else if (args[2].equals("-o")) {
                ouputFile = args[3];
                oCheck = 1;
            } else if (args[2].equals("-k")) {
                noOfCLusters = Integer.parseInt(args[3]);
                kcheck = 1;
            }
        }
        if (args.length >= 6) {
            if (args[4].equals("-f")) {
                inputFile = args[5];
                iCheck = 1;
            } else if (args[4].equals("-o")) {
                ouputFile = args[5];
                oCheck = 1;
            } else if (args[4].equals("-k")) {
                noOfCLusters = Integer.parseInt(args[5]);
                kcheck = 1;
            }
        }

        if (kcheck != 1) {
            noOfCLusters = 3;
        }
        if (oCheck != 1) {
            ouputFile = "processed_data.txt";
        }
        if (iCheck != 1) {
            inputFile = "climate_data.txt";
        }
        System.out.println("Input Params(else default): ");
        System.out.println("No of Clusters: " + noOfCLusters);
        System.out.println("Input Data Set: " + inputFile);
        System.out.println("Output Data File: " + ouputFile);

        data = convertData(inputFile);
        try{
            k = noOfCLusters;
            if (k <=0){
                System.out.println("k must be an integer > 0");
                System.exit(-1);
            }
        } catch (NumberFormatException e) {
            System.out.println("k must be an integer > 0");
            System.exit(-1);
        }

        clusterList = new ArrayList<Cluster>(); //Create clusterList to store all the clusters
        Collections.shuffle(data);          //Shuffle dataSet to randomize
        //Set random Centroids by taking the first k of the shuffled data
        for (int i = 0;i<k; i++) {
            Cluster cluster = new Cluster(i);
            Point centroid = new Point(data.get(0).getPoints(), data.get(0).getType());
            cluster.setCentroid(centroid);
            clusterList.add(cluster);
        }
        kMeans();
    }

    /**
     * Runs k-means clustering method
     */
    static void kMeans() {
        boolean isCompleted = false;
        int noOfIterations = 0;
        while (!isCompleted) {
            resetClusters();                //Reset clusters for the next iteration
            assignPointsToClusters();             //Assign points to the closer cluster
            List<Point> lastCentroids = getCentroids();             // Get the previous centroids and store to compare for later
            calculateCentroids();             //Calculate new centroids and store them
            List<Point> currentCentroids = getCentroids();
            //Calculates total distance between new and old Centroids
            double distance = 0;
            for(int i = 0; i < lastCentroids.size(); i++) {
                distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
            }
            if(distance == 0) {
                isCompleted = true;                 // calculate the SSE for quality measures
//                calculateQuality();                 // print output file
                printOutput(noOfIterations);
            }
            noOfIterations++;
        }
    }
    /**
     * Clears out all the Clusters points to reset
     */
    static void resetClusters() {
        for(Cluster cluster : clusterList) {
            cluster.clear();
        }
    }

    /**
     * Returns centroids
     * @return List<Points> of all centroids
     */
    static List<Point> getCentroids() {
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
    static void assignPointsToClusters() {
        double max = Double.MAX_VALUE;
        double min;
        int clusterID = 0;
        double distance;

        // Go through each point and find its closets centroid
        for(Point point : data) {
            min = max;
            for(int i = 0; i < k; i++) {
                Cluster c = clusterList.get(i);
                // find distance between the point and the centroid
                distance = Point.distance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    clusterID = i;
                }
            }
            clusterList.get(clusterID).addPoint(point);
            point.setDistanceToCentroid(min);
        }
    }

    /**
     * Calculates new centroids by averaging all the Points of each cluster
     */
    static void calculateCentroids() {
        // Go through one cluster at a time
        for (Cluster cluster : clusterList) {
            // mostClosePoints stores all the types of the points to later get the most prevalent type to assign to the centroid
            HashMap<String, Integer> mostClosePoints = new HashMap<>();
            ArrayList<Double> averagedPoints = new ArrayList<>(Collections.nCopies(pointSize, 0.0)); // averagedPoints holds the new centroid values
            List<Point> list = cluster.getPoints();
            int listSize = list.size();
            for (Point point : list) {
                ArrayList<Double> values = point.getPoints();   // get the values
                // add all values of all the points
                for (int i = 0; i < pointSize; i++) {
                    double x = values.get(i);
                    // add to averagedPoints
                    averagedPoints.set(i, averagedPoints.get(i) + x);
                    // updates mostClosePoints for the new type it sees
                    mostClosePoints.put(point.getType(), mostClosePoints.containsKey(point.getType()) ? mostClosePoints.get(point.getType()) + 1 : 1);
                }
            }
            // Get the centroid of this cluster to update it
            Point centroid = cluster.getCentroid();
            // if there are no points in the cluster then no need to update
            if (listSize > 0) {
                //average all the points and update averagedPoints
                for (int i = 0; i < averagedPoints.size(); i++) {

                    averagedPoints.set(i, (averagedPoints.get(i) / listSize));
                }
                // update centroid values
                centroid.setPoints(averagedPoints);
                // set the class to be the mode of the points in the cluster
                int max = 0;
                String maxClass = "";
                for (String key : mostClosePoints.keySet()) {
                    if (mostClosePoints.get(key) > max) {
                        max = mostClosePoints.get(key);
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
    static List<Point> convertData(String fileName) throws FileNotFoundException {
        Scanner dataReader = new Scanner(new FileReader(fileName));
        List<Point> dataSet = new ArrayList<>();
        ArrayList<Double> list;
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

    static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * Prints out the output file
     */
    static void printOutput(int iterations) {
        //Print output of cluster results to output file
        File f = new File(ouputFile);
        try {
            PrintWriter pw = new PrintWriter(f);

            pw.println("k: " + k);
            pw.println("Iterations: " + iterations);
            pw.println("*************");

            for (Cluster cluster : clusterList){
                pw.println("Cluster: " + cluster.getId());
                pw.println("Size of Cluster: "+ cluster.getPoints().size() );
                pw.println("Centroid: " + cluster.getCentroid());
                pw.println("Points:");
                for(Point p : cluster.getPoints()) {
                    pw.println("\t"+p+"");
                }
                pw.println();
            }
            pw.close();
        } catch (FileNotFoundException E) {
            System.out.println(E.toString());
        }
        endTime = System.nanoTime();
        System.out.println("** Completed  **");
        System.out.printf("\n** Time taken to execute the program: %.2f seconds **", (endTime - startTime) / 1000000000); // time taken during execution
        System.out.println("\n** Memory Used: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + " MB **"); // memory used during execution
        System.exit(0);
    }
}
