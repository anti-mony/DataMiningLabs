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
        int noOfInputs, maxItemTrans, maxTotalNumberOfItems, totalItems = 0, tmpChar, sum = 0, inLoopVarChck = 0;
        double skewFactor;
        String fileName, tmpInput;
        FreqSet[] fCheck = new FreqSet[26];
        BufferedReader inpTaker = new BufferedReader(new InputStreamReader(System.in));
        Random randomGenerator = new Random(System.currentTimeMillis());
        StringBuilder tmp = new StringBuilder();
        ZipfGen zipF;

        try {
            System.out.print("Enter the skewFactor (Press enter for default): ");
            tmpInput = inpTaker.readLine();
            if (tmpInput.length() != 0)
                skewFactor = Double.parseDouble(tmpInput);
            else
                skewFactor = 0.1;
            zipF = new ZipfGen(26, skewFactor);


            System.out.print("Enter the number of transactions (Press enter for default): ");
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

            maxTotalNumberOfItems = noOfInputs * maxItemTrans;
            while (totalItems < (maxTotalNumberOfItems * 0.65))
                totalItems = randomGenerator.nextInt(maxTotalNumberOfItems - noOfInputs + 1) + noOfInputs;

//            System.out.println("Total Items:"+totalItems);
            for (int i = 0; i < 26; i++) {
                fCheck[i] = new FreqSet((char) (i + 65), (int) (zipF.getProbability(i + 1) * totalItems));
                sum += fCheck[i].frequency;
            }
           // System.out.println("Total Items Freq:" + sum);
//            for(int i = 0;i< 26;i++){
//                System.out.println(fCheck[i].toString());
//            }

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
                inLoopVarChck = randomGenerator.nextInt(maxItemTrans) + 1;
                if (i % 43 < 20 && i % 43 == 0)
                    inLoopVarChck = maxItemTrans;
                for (int j = 0; j < inLoopVarChck; j++) {
                    tmpChar = (randomGenerator.nextInt(26));
                    if (fCheck[tmpChar].frequency > 0) {
                        tmp.append(fCheck[tmpChar].Item);
                        fCheck[tmpChar].frequency--;
                    }
                }
                bufferW.newLine();
                bufferW.write(tmp.toString());
                bufferW.flush();
                tmp.setLength(0);
            }
//            System.out.println("Items Left now:" + returnSum(fCheck));
        } catch (IOException E) {
            System.out.println(E.toString());
        }
    }

    private static int returnSum(FreqSet[] f) {
        int sum = 0;
        for (int i = 0; i < 26; i++) {
            sum += f[i].frequency;
        }
        return sum;
    }
}

class ZipfGen {
    private int size;
    private double skew;
    private double bottom = 0;

    ZipfGen(int size, double skew) {
        this.size = size;
        this.skew = skew;
        for (int i = 1; i <= size; i++) {
            this.bottom += (1 / Math.pow(i, this.skew));
        }
    }

    // This method returns a probability that the given rank occurs.
    double getProbability(int rank) {
        return (1.0d / Math.pow(rank, this.skew)) / this.bottom;
    }
}

class FreqSet {
    char Item;
    int frequency;

    public FreqSet(char item, int frequency) {
        Item = item;
        this.frequency = frequency;
    }

    public FreqSet() {

    }

    @Override
    public String toString() {
        String print = "Item " + Item;
        print += " Frequency " + frequency;
        return print;
    }

}