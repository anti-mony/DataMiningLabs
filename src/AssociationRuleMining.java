import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Sushant Bansal, Pragya Chaturvedi, Ishan Tyagi
 * Association Rule Mining
 *
 */
public class AssociationRuleMining {

    private int minThresh;
    private int numberOfTransactions;
    private Character[][] transactions;

    public static void main(String[] args) {
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
                aRM.readInput(fileName);
                check = true;
            } catch (IOException E) {
                System.out.println("Bad Input File, please enter the file name again! ");
            }
        }
        aRM.printInput();
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
            transactions[i] = new Character[tmp.length()];
            charArray = tmp.toCharArray();
            for (j = 0; j < tmp.length(); j++) {
                transactions[i][j] = charArray[j];
//                System.out.print(" "+charArray[j]);
            }
            i++;
        }
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
}
