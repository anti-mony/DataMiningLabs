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
public class AssociationRuleMining {

    private int minThresh;
    private int numberOfTransactions;
    private int biggestTransaction;
    private Character[][] transactions;
    private HashMap<Character, Integer> priorHashMap = new HashMap<>();
    private HashMap<Character, Integer> laterHashMap = new HashMap<>();
    Set<Character> candidateSet = new HashSet<>();


    public static void main(String[] args) {
        double start = 0, end;
//        System.out.println(System.getProperty("user.dir"));
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
                System.out.println("Please enter an integer ");
            }
        }
//        System.out.println("Minimum Threshold : "+aRM.minThresh);
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
        //aRM.printInput();
        //aRM.dataMine();
        end = System.nanoTime();
        System.out.println("Time taken to execute the program: " + (end - start) / 1000000 + " milliseconds");
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
//                System.out.print(" "+charArray[j]);
            }
            i++;
        }



        for(int m=0; m < transactions.length; m++){
            for (int n=0; n < transactions[m].length; n++){
                candidateSet.add(transactions[m][n]);
            }

        }
        bfR.close();
        System.out.println("Candidate set: " + candidateSet);


        for (i = 0; i < transactions.length; i++) {
            for (j = 0; j < transactions[i].length; j++) {
                    if (priorHashMap.containsKey(transactions[i][j])){
                        priorHashMap.put(transactions[i][j],priorHashMap.get(transactions[i][j]) + 1);
                    }
                    else{
                        //System.out.println("else:" +temp);
                        priorHashMap.put(transactions[i][j],1);
                    }
            }
        }
        System.out.println("Prior hashmap:" +priorHashMap);
        pruneAlgorithm();
        associationRuleMining();

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



    private void pruneAlgorithm(){

    }

    private void associationRuleMining(){

    }



}
