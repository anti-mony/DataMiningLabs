import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Sushant Bansal, Pragya Chaturvedi & Ishan Tyagi on 2/6/2017.
 * Input Generator for Association Rule Mining Algorithm
 */
public class inputSetGenerator {
    private static FileWriter fileW;
    private static BufferedWriter bufferW;

    public static void main(String[] args) {
        int noOfInputs = 100;
        try {
            fileW = new FileWriter("retail.txt");
            bufferW = new BufferedWriter(fileW);
            bufferW.write(String.valueOf(noOfInputs));
//            bufferW.newLine();
        } catch (IOException E) {
            System.out.println(E.toString());
        }

        Random randomGenerator = new Random(System.currentTimeMillis());
        StringBuilder tmp = new StringBuilder();
        char tmpChar;
        for (int i = 0; i < noOfInputs; i++) {
            for (int j = 0; j < randomGenerator.nextInt(5) + 1; j++) {
                tmpChar = (char) (randomGenerator.nextInt(24) + 65);
                tmp.append(tmpChar);
            }
            try {
                bufferW.newLine();
                bufferW.write(tmp.toString());
                bufferW.flush();
            } catch (IOException E) {
                System.out.println(E.toString());
            }
            tmp.setLength(0);
        }
    }
}
