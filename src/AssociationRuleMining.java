import java.io.*;
import java.util.*;

/**
 * Created by Sushant Bansal, Pragya Chaturvedi, Ishan Tyagi
 * Association Rule Mining
 *
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

    private int minThresh;
    private int numberOfTransactions;
    private int biggestTransaction;
    private Character[][] transactions;
    private Set<kItemSet> priorSet = new HashSet<>();
    private Set<kItemSet> laterSet = new HashSet<>();
    private Set<Character> candidate_set = new HashSet<>();
    private int outputToFile = 0;

    public static void main(String[] args) {
        String outputCheck;
        double start = 0, end;
        String fileName, outputFilename = "processed_retail.txt";
        boolean check = true;
        AssociationRuleMining aRM = new AssociationRuleMining();
        String tmp;
        Scanner inputScanner = new Scanner(System.in);
        while (check) {
            try {
                System.out.print("Enter the minimum threshold (Integer) (Press enter for default:3) : ");
                tmp = inputScanner.nextLine();
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

        while (!check) {
            try {
                System.out.print("Enter input file name: ");
                fileName = bR.readLine();
                System.out.print("Do you want to out in a file( N for console) ? Y/N : ");
                outputCheck = bR.readLine();
                if (outputCheck.equals("Y") || outputCheck.equals("y")) {
                    aRM.setOutputToFile(1);
                    System.out.print("Enter output file name(Press enter for default) : ");
                    outputFilename = bR.readLine();
                    if (outputFilename.length() < 1)
                        outputFilename = "processed_retail.txt";
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
        aRM.fillPriorSet();
        if (aRM.getOutputToFile() == 1) {
            try {
                FileWriter writeToFile = new FileWriter(outputFilename);
                BufferedWriter bWrite = new BufferedWriter(writeToFile);
                if (aRM.getOutputToFile() == 1) {
                    bWrite.write("\n-----> INITIAL LIST <-----\n");
                    for (kItemSet t : aRM.getPriorSet()) {
                        bWrite.write(t.itemset + " : " + t.support + "\n");
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
            aRM.coupleAndCount();
        }
        end = System.nanoTime();
        System.out.println("\n** Time taken to execute the program: " + (end - start) / 1000000000 + " seconds **");
        System.out.println("\n** Memory Used: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024) + " KB **");
    }

    private void setMinThresh(int minThresh) {
        this.minThresh = minThresh;
    }

    private void setOutputToFile(int outputToFile) {
        this.outputToFile = outputToFile;
    }

    private Set<kItemSet> getPriorSet() {
        return priorSet;
    }

    private int getOutputToFile() {
        return outputToFile;
    }

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

    private void coupleAndCount(BufferedWriter bW) throws IOException {
        int passNumber = 0;
        boolean toBeContinued = true;
        char element;
        int size = 1;
        Set<Set<Character>> candidates = new HashSet<>();
        pruneAlgorithm(++passNumber, bW);
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

            pruneAlgorithm(++passNumber, bW);
            if (laterSet.size() <= 1) {
                toBeContinued = false;
            }
            size++;
        }
        bW.write("\n-----> Completed <-----");
        bW.flush();
    }

    private void coupleAndCount() {
        int passNumber = 0;
        boolean toBeContinued = true;
        char element;
        int size = 1;
        Set<Set<Character>> candidates = new HashSet<>();
        pruneAlgorithm(++passNumber);
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

