/**
 * Reader class where all read in functions are combined
 * 
 * @author Joeri Sleegers
 */ 

import java.io.*;
import java.util.*;

public class Reader {
    
    private static final int ALPHABET_SIZE = 26;
    
    /**
     * Takes a String fileName as input. Creates an int[][] confusionMatrix from 
     * fileName. Returns confusionMatrix.
     *
     * @param  fileName filename of file contain confusion matrix 
     * @return          confusion matrix from fileName as int[][]
     */ 
    public int[][] readInConfusionMatrix(String fileName) {
        int matrixSize = countLines(fileName);
        int[][] confusionMatrix = new int[matrixSize][ALPHABET_SIZE];
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            int lineNumber = 0;
            String[] splittedLine;
            int currentNumber;

            while((line = br.readLine()) != null) {
                splittedLine = line.split("\\s+");
                for (int i = 0; i < splittedLine.length; i++) {
                    currentNumber = Integer.parseInt(splittedLine[i]);
                    confusionMatrix[lineNumber][i] = currentNumber;
                }
                lineNumber++;
            }    
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return confusionMatrix;
    }

    /**
     * Takes a String fileName as input. Returns the number of lines of that 
     * file.
     *
     * @param fileName fileName of file to count to lines from
     * @return         amount of lines in filename
     */ 
    private int countLines(String fileName) {
        int lineNumber = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            while((line = br.readLine()) != null) {
                lineNumber++;
            }    
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return lineNumber;
    }
    
    /**
     * Takes a String fileName as input. Create a Trie dictionary. Put every
     * line of fileName in dictionary. Return dictionary.
     * 
     * @param  fileName filename of file containing URL's
     * @return          dictionary as Trie of all the URL's in filename         
     */ 
    public Trie readInFile(String fileName) {
        Trie dictionary = new Trie();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            boolean blankLine;
            String trimmedLine;

            while((line = br.readLine()) != null) {
                blankLine = line.equals("");
                if (blankLine) {
                    continue;
                }        
                trimmedLine = line.trim().replaceAll("\n ", "");
                dictionary.put(trimmedLine);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return dictionary;
    }

    /**
     * Uses a BuffereadReader and an InputStreamReader to get input from the 
     * user. Returns this input.
     *
     * @return The answer the user inputed to stdin as a string
     */
    public String readUserInput() {
        BufferedReader readUser = new BufferedReader(new InputStreamReader(System.in));
        String userAnswer =  "";  
        System.out.println("Enter name of URL: ");
        try {
            userAnswer = readUser.readLine();
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
        return userAnswer;
    }
}
