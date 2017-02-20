package AssociationRuleMining;

import java.io.*;
import java.util.*;

/**
 * Created by Sushant Bansal, Pragya Chaturvedi, Ishan Tyagi
 * Association Rule Mining
 *
 */

/*
 * Class to make a set of characters and
 * support is the frequency
 */
class kItemSet {
    Set<Character> itemset;
    int support;

    kItemSet(Set<Character> s, int i) {
        itemset = s;
        support = i;
    }

    @Override
    public String toString() {

        String op = "[";
        for (int temp : itemset) {

            op += (temp + " ");
        }
        op += "]";
        return op;
    }
}


public class AssociationRuleMining {
    /*
    * Main method class which reads the input data set and
    * implements Apriori Algorithm for Association Rule Mining
    */

    private int minThresh;  // minimum threshold for the algorithm
    private int numberOfTransactions;   // Total number of transactions
    private int biggestTransaction;
    private Character[][] transactions; // Array to store the transaction
    private Set<kItemSet> priorSet = new HashSet<>();  // Hash set to store item set before the pass
    private Set<kItemSet> laterSet = new HashSet<>(); // Hash set to store item set after the pass
    private Set<Character> candidate_set = new HashSet<>(); // Hash set to store distinct item set
    private int outputToFile = 0;

    public static void main(String[] args) {
        String outputCheck;
        double start = 0, end;
        String fileName, outputFilename = "processed_retail.txt"; // input data set file
        boolean check = true;
        AssociationRuleMining aRM = new AssociationRuleMining();
        String tmp;
        Scanner inputScanner = new Scanner(System.in);
        while (check) {
            try {
                System.out.print("Enter the minimum threshold (Integer) (Press enter for default:3) : ");
                tmp = inputScanner.nextLine(); // get minimum threshold from the user
                if (tmp.length() == 0)
                    aRM.setMinThresh(3);
                else if (Integer.parseInt(tmp) <= 0)
                    throw new ArithmeticException();
                else
                    aRM.setMinThresh(Integer.parseInt(tmp));
                check = false;
            } catch (Exception E) {
                System.out.println("Bad Input, Integer(>0) is required");
            }
        }

        BufferedReader bR = new BufferedReader(new InputStreamReader(System.in));

        while (!check) {   // other inputs from the user
            try {
                System.out.print("Enter input file name: ");
                fileName = bR.readLine();   // get input data set file from the user
                if (fileName.length() < 1) {
                    fileName = "retail_transactions.txt";  //default
                }
                System.out.println("Reading from file- "+fileName);
                System.out.print("Do you want to out in a file( N for console) ? Y/N : ");
                outputCheck = bR.readLine();
                if (outputCheck.equals("Y") || outputCheck.equals("y")) {
                    aRM.setOutputToFile(1);
                    System.out.print("Enter output file name(Press enter for default) : ");
                    outputFilename = bR.readLine();  // get output file from the user
                    if (outputFilename.length() < 1)
                        outputFilename = "processed_retail.txt"; //default
                    System.out.println("Writing output to: " + outputFilename);
                } else {
                    System.out.println("Printing output to console:");
                }
                start = System.nanoTime();
                aRM.readInput(fileName);
                check = true;
                bR.close();
            } catch (IOException E) {
                System.out.println("Bad Input File, please enter the file name again! ");
            }
        }
        aRM.fillPriorSet(); // filling prior hash set for the first pass
        if (aRM.getOutputToFile() == 1) {
            try {
                FileWriter writeToFile = new FileWriter(outputFilename);
                BufferedWriter bWrite = new BufferedWriter(writeToFile);
                if (aRM.getOutputToFile() == 1) {
                    bWrite.write("\n-----> INITIAL LIST <-----\n");
                    for (kItemSet t : aRM.getPriorSet()) {
                        bWrite.write(t.itemset + " : " + t.support + "\n");  // write the initial input transactions in the file
                    }
                }
                bWrite.flush();
                aRM.coupleAndCount(bWrite);
                bWrite.close();
            } catch (IOException E) {
                System.out.println(E.toString());
                System.out.println("Please restart the program again: ");
            }
        } else {
            aRM.coupleAndCount(); // generate frequent item sets
        }
        end = System.nanoTime();
        System.out.println("\n** Time taken to execute the program: " + (end - start) / 1000000000 + " seconds **"); // time taken during execution
        System.out.println("\n** Memory Used: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024) + " KB **"); // memory used during execution
    }

    private void setMinThresh(int minThresh) {
        this.minThresh = minThresh;
    }

    private Set<kItemSet> getPriorSet() {
        return priorSet;
    }

    private int getOutputToFile() {
        return outputToFile;
    }

    private void setOutputToFile(int outputToFile) {
        this.outputToFile = outputToFile;
    }

    /*
    * Method to read input from the file
    * Input : filename of the input data set
    * Return : -
    */
    private void readInput(String fileName) throws IOException {
        String tmp;
        int i = 0, j;
        char[] charArray;
        FileReader fRead = new FileReader(fileName);
        BufferedReader bfR = new BufferedReader(fRead);
        tmp = bfR.readLine();
        numberOfTransactions = Integer.parseInt(tmp);
        transactions = new Character[numberOfTransactions][];
        while ((tmp = bfR.readLine()) != null) {
            if (tmp.length() > biggestTransaction)
                biggestTransaction = tmp.length();
            transactions[i] = new Character[tmp.length()];
            charArray = tmp.toCharArray();
            for (j = 0; j < tmp.length(); j++) {
                transactions[i][j] = charArray[j];
                candidate_set.add(charArray[j]);
            }
            i++;
        }
        bfR.close();
    }

    /*
    * Method to eliminate data items with frequency less than min support
    * Input : number of the pass
    * Return : -
    */
    private void pruneAlgorithm(int i) {
        laterSet.clear();
        for (kItemSet t : priorSet) {
            if (t.support >= minThresh) {
                laterSet.add(t);
            }
        }
        System.out.println("\n=+= PASS " + i + " Results =+=");
        if (laterSet.isEmpty()) {
            System.out.println("No item-sets present after this pass \n");

        } else {
            for (kItemSet t : laterSet) {
                System.out.println(t.itemset + " : " + t.support);
            }
        }
    }

    /*
   * Method to eliminate data items with frequency less than min support
   * Input : number of the pass, buffer writer object
   * Return : -
   */
    private void pruneAlgorithm(int i, BufferedWriter bW) throws IOException {
        laterSet.clear();
        for (kItemSet t : priorSet) {
            if (t.support >= minThresh) {
                laterSet.add(t);
            }
        }
        bW.write("\n-----> PASS " + i + " Results <-----\n");
        if (laterSet.isEmpty()) {
            bW.write("No item-sets qualify after this pass \n");
//            bW.write("\n------------> Completed <------------\n");
        } else {
            for (kItemSet t : laterSet) {
                bW.write(t.itemset + " : " + t.support + "\n");
            }
        }
        bW.flush();
    }

    /*
   * Method to generate frequent item sets after different passes
   * Input : buffer writer object
   * Return : -
   */
    private void coupleAndCount(BufferedWriter bW) throws IOException {
        int passNumber = 0;
        boolean toBeContinued = true;
        char element;
        int size = 1;
        Set<Set<Character>> candidates = new HashSet<>(); // hash set to store distinct data items
        pruneAlgorithm(++passNumber, bW); // call to prune for the next pass
        while (toBeContinued) {
            candidates.clear();
            priorSet.clear();
            kItemSet t1; // data structure of AssociationRuleMining.kItemSet type
            Set<Character> temp;
            Iterator<kItemSet> it2; // for iteration over it2
            kItemSet t2;
            Iterator<Character> it3;
            Character[] int_arr; // to store items in one transaction
            Set<Character> temp2;
            Set<Character> s;
            Iterator<Set<Character>> candidates_iterator;
            Iterator<kItemSet> iterator = laterSet.iterator();
            while (iterator.hasNext()) { // while true keep iterating for maximum 5 passes
                t1 = iterator.next();
                temp = t1.itemset;
                it2 = laterSet.iterator();
                while (it2.hasNext()) {
                    t2 = it2.next();
                    it3 = t2.itemset.iterator();
                    while (it3.hasNext()) {
                        try {
                            element = it3.next();
                        } catch (ConcurrentModificationException e) {
                            // Sometimes this Exception gets thrown, so simply break in that case.
                            break;
                        }
                        temp.add(element);
                        if (temp.size() != size) {
                            int_arr = temp.toArray(new Character[0]);
                            temp2 = new HashSet<>();
                            for (Character x : int_arr) {
                                temp2.add(x);
                            }
                            candidates.add(temp2);
                            temp.remove(element);
                        }
                    }
                }
            }
            candidates_iterator = candidates.iterator();
            while (candidates_iterator.hasNext()) {
                s = candidates_iterator.next();
                // These lines cause warnings, as the candidate_set Set stores a raw set.
                priorSet.add(new kItemSet(s, count(s)));
            }

            pruneAlgorithm(++passNumber, bW);
            if (laterSet.size() <= 1) {
                toBeContinued = false;
            }
            size++;
        }
        bW.write("\n-----> Completed <-----");
        bW.flush();
    }

    /*
    * Method to generate frequent item sets after different passes
    * Input : -
    * Return : -
    */
    private void coupleAndCount() {
        int passNumber = 0;
        boolean toBeContinued = true;
        char element;
        int size = 1;
        Set<Set<Character>> candidates = new HashSet<>(); // Hash set for distinct data items
        pruneAlgorithm(++passNumber); // call to prune for the next pass
        while (toBeContinued) {
            candidates.clear();
            priorSet.clear();
            kItemSet t1;
            Set<Character> temp;
            Iterator<kItemSet> it2;
            kItemSet t2;
            Iterator<Character> it3;
            Character[] int_arr;
            Set<Character> temp2;
            Set<Character> s;
            Iterator<Set<Character>> candidates_iterator;
            Iterator<kItemSet> iterator = laterSet.iterator();
            while (iterator.hasNext()) {
                t1 = iterator.next();
                temp = t1.itemset;
                it2 = laterSet.iterator();
                while (it2.hasNext()) {
                    t2 = it2.next();
                    it3 = t2.itemset.iterator();
                    while (it3.hasNext()) {
                        try {
                            element = it3.next();
                        } catch (ConcurrentModificationException e) {
                            // Sometimes this Exception gets thrown, so simply break in that case.
                            break;
                        }
                        temp.add(element);
                        if (temp.size() != size) {
                            int_arr = temp.toArray(new Character[0]);
                            temp2 = new HashSet<>();
                            for (Character x : int_arr) {
                                temp2.add(x);
                            }
                            candidates.add(temp2);
                            temp.remove(element);
                        }
                    }
                }
            }
            candidates_iterator = candidates.iterator();
            while (candidates_iterator.hasNext()) {
                s = candidates_iterator.next();
                // These lines cause warnings, as the candidate_set Set stores a raw set.
                priorSet.add(new kItemSet(s, count(s)));
            }

            pruneAlgorithm(++passNumber);
            if (laterSet.size() <= 1) {
                toBeContinued = false;
            }
            size++;
        }
        System.out.println("\n-----> Completed <-----");
    }

    /*
   * Method to generate frequency of items in the input set after
   * Input : Set of data items
   * Return : frequency
   */
    private int count(Set<Character> inputSet) {
        int i, k;
        int support = 0;
        int count;
        boolean containsElement;
        for (i = 0; i < transactions.length; i++) {
            count = 0;
            for (int element : inputSet) {
                containsElement = false;
                for (k = 0; k < transactions[i].length; k++) {
                    if (element == transactions[i][k]) {
                        containsElement = true;
                        count++;
                        break;
                    }
                }
                if (!containsElement) {
                    break;
                }
            }
            if (count == inputSet.size()) {
                support++;
            }
        }
        return support;
    }

    /*
   * Method to generate frequency of items in the input set after
   * Input : Set of data items
   * Return : frequency
   */
    private int frequencyCount(char c) {
        int count = 0;
        for (int i = 0; i < transactions.length; i++) {
            for (int j = 0; j < transactions[i].length; j++) {
                if (transactions[i][j] == c) {
                    count++;
                }
            }
        }
        return count;
    }

    /*
   * Method to generate items in the hashset for the first pass
   * Input : -
   * Return : -
   */
    private void fillPriorSet() {
        Iterator<Character> iterator = candidate_set.iterator();
        char tmp;
        while (iterator.hasNext()) {
            TreeSet<Character> s = new TreeSet<>();
            tmp = iterator.next();
            s.add(tmp);
            kItemSet t = new kItemSet(s, frequencyCount(tmp));
            priorSet.add(t);
        }
        if (outputToFile == 0) {
            System.out.println("\n -----> INITIAL LIST <-----");
            for (kItemSet t : priorSet) {
                System.out.println(t.itemset + " : " + t.support);
            }
        }

    }
}

