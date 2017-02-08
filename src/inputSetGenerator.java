import java.io.*;
import java.util.Random;

/**
 * Created by Sushant Bansal, Pragya Chaturvedi & Ishan Tyagi on 6/2/2017.
 * Input Generator for Association Rule Mining Algorithm
 */
public class inputSetGenerator {

    public static void main(String[] args) {
        FileWriter fileW;
        BufferedWriter bufferW;
        int noOfInputs;
        int maxItemTrans;
        String fileName;
        String tmpInput;
        BufferedReader inpTaker = new BufferedReader(new InputStreamReader(System.in));

        Random randomGenerator = new Random(System.currentTimeMillis());
        StringBuilder tmp = new StringBuilder();
        char tmpChar;

        System.out.print("Enter the number of transactions (Press enter for default): ");
        try {
            tmpInput = inpTaker.readLine();
            if (tmpInput.length() != 0)
                noOfInputs = Integer.parseInt(tmpInput);
            else
                noOfInputs = 10000;
            System.out.print("Enter the max number of items in each transactions (Press enter for default): ");
            tmpInput = inpTaker.readLine();
            if (tmpInput.length() != 0)
                maxItemTrans = Integer.parseInt(tmpInput);
            else
                maxItemTrans = 5;
            System.out.print("Enter the output file name (Press enter for default) : ");
            fileName = inpTaker.readLine();
            if (fileName.length() == 0)
                fileName = "retail_transactions.txt";
            inpTaker.close();
            System.out.println("Writing " + noOfInputs + " transactions with " + maxItemTrans + " items in each transaction to file " + fileName);

            fileW = new FileWriter(fileName);
            bufferW = new BufferedWriter(fileW);
            bufferW.write(String.valueOf(noOfInputs));
            for (int i = 0; i < noOfInputs; i++) {
                for (int j = 0; j < randomGenerator.nextInt(maxItemTrans) + 1; j++) {
                    tmpChar = (char) (randomGenerator.nextInt(24) + 65);
                    tmp.append(tmpChar);
                }
                bufferW.newLine();
                bufferW.write(tmp.toString());
                bufferW.flush();
                tmp.setLength(0);
            }
        } catch (IOException E) {
            System.out.println(E.toString());
        }
    }
}