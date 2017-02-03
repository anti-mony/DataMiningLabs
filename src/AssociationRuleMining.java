import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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


    public static void main(String[] args) {
        double start = 0, end;
        String fileName;
        boolean check = true;
        AssociationRuleMining aRM = new AssociationRuleMining();
        Scanner inputScanner = new Scanner(System.in);
        while (check) {
            try {
                System.out.print("Enter the minimum threshold: ");
                aRM.setMinThresh(inputScanner.nextInt());
                check = false;
            } catch (InputMismatchException E) {
                inputScanner.nextLine();
                System.out.println("Please enter an Character ");
            }
        }

        BufferedReader bR = new BufferedReader(new InputStreamReader(System.in));

        while (!check) {
            try {
                System.out.print("Enter file name: ");
                fileName = bR.readLine();
//                System.out.println(fileName);
                start = System.nanoTime();
                aRM.readInput(fileName);
                check = true;
            } catch (IOException E) {
                System.out.println("Bad Input File, please enter the file name again! ");
            }
        }

        aRM.fillPriorSet();
//        aRM.printInput();
        aRM.pruneAlgorithm();
        aRM.generateFrequentItemsets();
        end = System.nanoTime();
        System.out.println("Time taken to execute the program: " + (end - start) / 1000000000 + " seconds");
        System.out.println("Memory Used: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024) + " KB");
    }

    private void setMinThresh(int minThresh) {
        this.minThresh = minThresh;
    }

    private void readInput(String fileName) throws IOException {
        String tmp;
        int i = 0, j;
        char[] charArray;
        FileReader fRead = new FileReader(fileName);
        BufferedReader bfR = new BufferedReader(fRead);
        tmp = bfR.readLine();
        numberOfTransactions = Integer.parseInt(tmp);
//        System.out.println("No:" + numberOfTransactions);
        transactions = new Character[numberOfTransactions][];
        while ((tmp = bfR.readLine()) != null) {
            //System.out.println("Tmp: "+tmp);
            if (tmp.length() > biggestTransaction)
                biggestTransaction = tmp.length();
            transactions[i] = new Character[tmp.length()];
            charArray = tmp.toCharArray();
            for (j = 0; j < tmp.length(); j++) {
                transactions[i][j] = charArray[j];
                candidate_set.add(charArray[j]);
//                System.out.print(" "+charArray[j]);
            }
            i++;
        }
        bfR.close();
    }

    private void printInput() {
        System.out.println("List of transactions:");
        for (int i = 0; i < numberOfTransactions; i++) {
            System.out.print("T" + (i + 1) + ": ");
            for (int j = 0; j < transactions[i].length; j++) {
                System.out.print(transactions[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void pruneAlgorithm() {
        laterSet.clear();
        for (kItemSet t : priorSet) {
            if (t.support >= minThresh) {
                laterSet.add(t);
            }
        }
        System.out.println("\n=+= PASS Results =+=");
        if (laterSet.isEmpty()) {
            System.out.println("No item-sets present after this pass \n");
            System.out.println("------------> EXITING <------------");
        } else {
            for (kItemSet t : laterSet) {
                System.out.println(t.itemset + " : " + t.support);
            }
        }
    }

    private void generateFrequentItemsets() {
        boolean toBeContinued = true;
        char element;
        int size = 1;
        Set<Set<Character>> candidates = new HashSet<>();

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
//                System.out.println("T1 Outer Loop, Temp(Character Set):"+ temp);
                it2 = laterSet.iterator();
                while (it2.hasNext()) {
                    t2 = it2.next();
//                    System.out.println("1st Inside Loop Iterator on L, kItemSet t2: "+ t2);
                    it3 = t2.itemset.iterator();
                    while (it3.hasNext()) {
                        try {
//                            System.out.println("2nd Inside Loop , 3rd iterator on L");
                            element = it3.next();
//                            System.out.println("Element: "+ element);
                        } catch (ConcurrentModificationException e) {
                            // Sometimes this Exception gets thrown, so simply break in that case.
                            break;
                        }
                        temp.add(element);
//                        System.out.println("2nd Inside Loop, temp(Character Set): "+ temp);
                        if (temp.size() != size) {
                            int_arr = temp.toArray(new Character[0]);
                            temp2 = new HashSet<>();
                            for (Character x : int_arr) {
                                temp2.add(x);
//                                System.out.println("Hashset of Characters, Temp2: "+ temp2);
                            }
                            candidates.add(temp2);
                            temp.remove(element);
                        }
//                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    }
                }
            }
            candidates_iterator = candidates.iterator();
            while (candidates_iterator.hasNext()) {
                s = candidates_iterator.next();
                // These lines cause warnings, as the candidate_set Set stores a raw set.
                priorSet.add(new kItemSet(s, count(s)));
            }

            pruneAlgorithm();
            if (laterSet.size() <= 1) {
                toBeContinued = false;
            }
            size++;
        }
//        System.out.println("\n=+= FINAL LIST =+=");
//        for(kItemSet t : laterSet) {
//            System.out.println(t.itemset + " : " + t.support);
//        }
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
        System.out.println("\n=+= INITIAL LIST =+=");
        for (kItemSet t : priorSet) {
            System.out.println(t.itemset + " : " + t.support);
        }
    }
}


