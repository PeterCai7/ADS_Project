import java.nio.file.Paths;
import java.util.*;
import java.io.*;
public class hashtagcounter {
    public static void  main(String[] args) throws IOException{
        boolean printToConsole = false;
        String inputFilename;
        String outputFilename = null;
        PrintWriter printWriter = null;
        // check if there are specified input file and output file
        if (args.length == 0) {
            System.out.println("Please provide an input file at least!");
        }
        else {
            if (args.length == 1) {
                inputFilename = args[0]; // only hava inputFile name
            }
            else {
                inputFilename = args[0];
                outputFilename = args[1];
            }
            if (outputFilename != null) {
                FileWriter writer = new FileWriter(outputFilename);
                printWriter = new PrintWriter(writer);
            }
            Scanner input = new Scanner(Paths.get(inputFilename));
            //initialize a maxFibonacciHeap Object to do the main tasks;
            MaxFibonacciHeap maxFiheap = new MaxFibonacciHeap();
            // read file one line a time
            while(input.hasNextLine()){
                String line = input.nextLine();
                if (line.equals("stop")) {
                    // quit
                    break;
                }
                else if (line.length() <= 2) {
                    //query
                    int num = Integer.parseInt(line);
                    datafield[] d = new datafield[num];
                    for (int i = 0; i < num; i++) {
                        d[i] = maxFiheap.RemoveMax();
                        if (d[i] == null) {
                            break;
                        }
                        if (i == num - 1) {
                            if (printWriter != null) {
                                printWriter.println(d[i].getHashtag());
                            }
                            else {
                                System.out.println(d[i].getHashtag());
                                //  + "(" + d[i].getAmount() + ")"
                            }
                        }
                        else {
                            if (printWriter != null) {
                                printWriter.print(d[i].getHashtag() + ",");
                            }
                            else {
                                System.out.print(d[i].getHashtag() + ",");
                            }
                        }
                    }
                    // because we actually do query, so we need to add them back
                    for (int i = 0; i < num; i++) {
                        if (d[i] != null) {
                            maxFiheap.Insert(d[i].getHashtag(), d[i].getAmount());
                        }
                    }
                }
                else {
                    //do adding amount
                    String[] sline = line.split("#| ");
                    int amount = Integer.parseInt(sline[2]);
                    maxFiheap.addData(sline[1], amount);
                }
            }
        }
        if (printWriter != null) {
            printWriter.close();
        }
    }
}