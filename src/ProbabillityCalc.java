/**
 * Class that calculates the probabbility of a String transformation to another
 * String using confusion matrices and addOneSmoothing smoothing.
 * The confusion matrices used in this implementation are from the paper:
 * "A Spelling Correction Program Based on a Noisy Channel Model",
 * Kemighan & Church & Gale.
 * 
 * @author Joeri Sleegers
 */ 

import java.io.*;
import java.util.*;

public class ProbabillityCalc {
    
    private static final String CONFUSION_MATRIX_PATH = "../data/confusionMatrices/";

    int[][] insTable;
    int[][] delTable;
    int[][] subTable;
    int[][] transTable;
    int insTotal;
    int delTotal;
    int subTotal;
    int transTotal;

    /**
     * Contructor of class probabillity. Reads in the confusion matrices.
     * Calculates the confusion totals of the matrices.
     */ 
    public ProbabillityCalc() {
        Reader rd = new Reader();
        insTable   = rd.readInConfusionMatrix(CONFUSION_MATRIX_PATH + "insertion.txt");
        delTable   = rd.readInConfusionMatrix(CONFUSION_MATRIX_PATH + "deletion.txt");
        subTable   = rd.readInConfusionMatrix(CONFUSION_MATRIX_PATH + "substitution.txt");
        transTable = rd.readInConfusionMatrix(CONFUSION_MATRIX_PATH + "transposition.txt");
        insTotal   = getTotal(insTable);
        delTotal   = getTotal(delTable);
        transTotal = getTotal(transTable);
        subTotal   = getTotal(subTable);
    }
    
    /**
     * Takes an int[][] matrix as input. Iterator through every element and 
     * add it to int total. Return total.
     *
     * @param  matrix two dimensional array of integers
     * @return        the sum of all integers in matrix
     */ 
    private int getTotal(int[][] matrix) {
        int total = 0;
        for (int i = 0; i < matrix.length; i++ ) {
            for (int j = 0; j < matrix[0].length; j++) {
                total = total + matrix[i][j];
            }
        }
        return total;
    }
    
    /**
     * Takes an int[][] matrix, a String string1 and a Strig string2 as input.
     * Set int x and int y to the coordinates of the bottom right corner of
     * matrix. Finds the path to the top left corner, by always choosing the
     * adjacent field with the lowest int. If it is one step to the left, that
     * corresponds the probabillity is calculated according to a deletions edit.
     * If it is one step to the top, that corresponds to an insertstion edit.
     * If the step is diagonal, check if it a substitution or not an edit, by 
     * comparing string1 and string2. Calculates the probabbility for every
     * step according to the edit. Return probabbility.
     *
     * @param matrix  floaded matrix by wagner-fischer algorithm
     * @param string1 The first string the matrix was used to fload
     * @param string2 the second string the matrix was used to fload
     * @return        probabillity of the transformatrion form string 1 to 2 
     */ 
    public double calcProbTransform(int[][] matrix, String string1, String string2) {
        int x = matrix.length-1;
        int y = matrix[0].length-1;
        double prob = 1;
        int del ;
        int ins;
        int sub;
        int trans;    
        String sequence1;
        String sequence2;
        String lowestEditName;
        boolean noEdit;
        boolean outOfBounds;

        while (x != 0 && y != 0) {
            noEdit = string1.substring(y-1, y).equals(string2.substring(x-1, x));
            if (noEdit){
                x = x-1;
                y = y-1;
                continue;
            }
            del = matrix[x][y-1];
            ins = matrix[x-1][y];
            sub = matrix[x-1][y-1];

            lowestEditName = "ins";
            int[] lowestCoordsAndValue = {x-1, y, matrix[x-1][y]};

            outOfBounds = (x <= 1 || y <= 1);
            if (!outOfBounds) {
                trans = matrix[x-2][y-2];
                sequence1 = string1.substring(y-2, y);
                sequence2 = string2.substring(x-1, x ) + string2.substring(x-2, x-1);
                if (sequence1.equals(sequence2) && trans < lowestCoordsAndValue[2]) {
                    lowestEditName = "trans";
                    lowestCoordsAndValue[0] = x-2;
                    lowestCoordsAndValue[1] = y-2;
                    lowestCoordsAndValue[2] = matrix[x-2][y-2];
                }              
            }
            if (del < lowestCoordsAndValue[2]) {
                lowestEditName = "del";
                lowestCoordsAndValue[0] = x;
                lowestCoordsAndValue[1] = y-1;
                lowestCoordsAndValue[2] = matrix[x][y-1];
            }
            if (sub < lowestCoordsAndValue[2]) {
                lowestEditName = "sub";
                lowestCoordsAndValue[0] = x-1;
                lowestCoordsAndValue[1] = y-1;
                lowestCoordsAndValue[2] = matrix[x-1][y-1];
            }
            x = lowestCoordsAndValue[0];
            y = lowestCoordsAndValue[1];
            prob = prob * calcProbEdit(lowestEditName, lowestCoordsAndValue, string1, string2);
        }
        return prob;
    }
    
    /**
     * Takes a String edit, an int[] lowest, a String string1, and a String string2
     * as input. Check what kind of edit, String edit is. Get the appropriate
     * count for that edit. Calculate this probabillity using addOneSmoothing smoothing.
     * Return this probabbility.
     *
     * @param edit    type of edit
     * @param lowest  coordinates and value of lowest edit
     * @param string1 the first string tranformed from
     * @param string2 the second string transformed to
     */ 
    private double calcProbEdit(String edit, int[] lowest, String string1, 
        String string2) {
        double count = 0;
        int x = lowest[0];
        int y = lowest[1];
        if (edit.equals("ins")) {
            count = getCount(string2.charAt(x), string1.charAt(y-1), insTable);
            return addOneSmoothing(count, insTotal);
        }
        if (edit.equals("del")) {
            count = getCount(string1.charAt(y), string2.charAt(x-1), delTable);
            return addOneSmoothing(count, delTotal);
        }
        if (edit.equals("sub")) {
            count = count = getCount(string1.charAt(y), string2.charAt(x), 
                subTable);
            return addOneSmoothing(count, subTotal);
        }
        if (edit.equals("trans")) {
            String sequence1 = string1.substring(y, y+2);
            String sequence2 = string2.substring(x+1, x+2) 
                + string2.substring(x, x+2);
            count = (getCount(sequence1.charAt(0), sequence2.charAt(1), transTable));
            return addOneSmoothing(count, transTotal);
        }
        return 1;
    }
    
    /**
     * Takes a double count and an int total as input.
     * Calculate the probabbility using addOneSmoothing-smoothing., therefor add 1 to count.
     * Because every object in v occurs only ones we can multiply it by two.
     * return this probabbility.
     *
     * @param count count of specific edit
     * @param total count of total edits
     * @return  
     */ 
    private double addOneSmoothing(double count, int total) {
        return (count+1)/((double)total*2);
    }
    
    /**
     * Takes a char char1. a char char3 and an int[][] matrix as input.
     * If char1 and char2 aren't both letters return 0.
     * Calculate the coordinates by subtracting 97 from char and char2.
     * If x or y is below 0 return 0. Get the count from matrix using the 
     * coordinates. Return the count.
     *
     * @param char1   charachter the edit is from
     * @param char2   charachter the edit is to
     * @param matrix  matrix containing the count of edits
     * @return        return specific count for the edit
     */ 
    private double getCount(char char1, char char2, int[][] matrix) {
        if (!isLetter(char1, char2)) {
            return 0.0;
        }
        int x = (int)char1-97;
        int y = (int)char2-97;
        if (x < 0 || y < 0) {
            return 0;
        }
        return (double)matrix[x][y];
    }
    
    /**
     * Takes a char char2 and a char char2 as input. Return true if char1 and 
     * char2 are letters. return false if not.
     *
     * @param char1 first charachter to check if it is a letter
     * @param char2 second charachter to check if it is a letter
     * @return      true if char1 and char2 are letters false if not
     */ 
    private boolean isLetter(char char1, char char2) {
        return (Character.isLetter(char1) && Character.isLetter(char1));
    }
}
