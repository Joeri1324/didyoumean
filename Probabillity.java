/**
 * Class that calculates the probabbility of a String transformation to another
 * String using confusion matrices and add1 smoothing.
 * The confusion matrices used in this implementation are from the paper:
 * "A Spelling Correction Program Based on a Noisy Channel Model", Kemighan & Church & Gale.
 * by Lina Murady 10776389 and Joeri Sleegers 10631186
 */ 

import java.io.*;
import java.util.*;

public class Probabillity {
    
    int[][] insTable;
    int[][] delTable;
    int[][] subTable;
    int[][] transTable;
    int insTotal;
    int delTotal;
    int subTotal;
    int transTotal;
    
    /**
     * Contructor of class probabillity
     * Reads in the confusion matrices.
     * Calculates the confusion totals of the matrices.
     */ 
    public Probabillity() {
    	Read rd = new Read();
    	insTable = rd.readInMatrix("insertion.txt");
    	delTable = rd.readInMatrix("deletion.txt");
        subTable = rd.readInMatrix("substitution.txt");
        transTable = rd.readInMatrix("transposition.txt");
        insTotal = getTotal(insTable);
        delTotal = getTotal(delTable);
        transTotal = getTotal(transTable);
        subTotal = getTotal(subTable);
    }
    
    /**
     * Takes an int[][] matrix as input.
     * Iterator through every element and add it to int total.
     * Return total.
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
     * step according to the edit.
     * Return probabbility.
     */ 
    public double probabillity (int[][] matrix, String string1, String string2) {
		int x = matrix.length-1;
		int y = matrix[0].length-1;
		int del ;
		int ins;
		int sub;
		int trans;	
		String sequence1;
    	String sequence2;
		double prob = 1;
		while (x != 0 && y != 0) {
			del = matrix[x][y-1];
			ins = matrix[x-1][y];
			sub = matrix[x-1][y-1];
			String low1 = "ins";
			int[] low2 = {x-1, y, matrix[x-1][y]};
			if (string1.substring(y-1, y).equals(string2.substring(x-1, x))){
				x = x-1;
				y = y-1;
				continue;
			}
			if (x > 1 && y > 1 && 
				!(string1.substring(y-1, y).equals(string2.substring(x-1, x)))) {
			    trans = matrix[x-2][y-2];
				sequence1 = string1.substring(y-2, y);
    			sequence2 = string2.substring(x-1, x ) + string2.substring(x-2, x-1);
    			if (sequence1.equals(sequence2)) {
    				if (trans < low2[2]) {
	    				low1 = "trans";
	    				low2[0] = x-2;
	    				low2[1] = y-2;
	    				low2[2] = matrix[x-2][y-2];
	    			}
    			}
    			
			}
			if (del < low2[2]) {
				low1 = "del";
				low2[0] = x;
    			low2[1] = y-1;
    			low2[2] = matrix[x][y-1];
			}
			if (sub < low2[2]) {
				low1 = "sub";
				low2[0] = x-1;
				low2[1] = y-1;
				low2[2] = matrix[x-1][y-1];
			}
			x = low2[0];
			y = low2[1];
			prob = prob * calcProb(low1, low2, string1, string2);
		}
		return prob;
	}
	
	/**
	 * Takes a String edit, an int[] lowest, a String string1, and a String string2
	 * as input. Check what kind of edit, String edit is. Get the appropriate
	 * count for that edit. Calculate this probabillity using add1 smoothing.
	 * Return this probabbility.
	 */ 
	private double calcProb(String edit, int[] lowest, String string1, 
		String string2) {
		double count = 0;
		int x = lowest[0];
		int y = lowest[1];
		if (edit.equals("ins")) {
			count = getCount(string2.charAt(x), string1.charAt(y-1), insTable);
			return add1(count, insTotal);
		}
		if (edit.equals("del")) {
			count = getCount(string1.charAt(y), string2.charAt(x-1), delTable);
			return add1(count, delTotal);
		}
		if (edit.equals("sub")) {
			count = count = getCount(string1.charAt(y), string2.charAt(x), 
				subTable);
			return add1(count, subTotal);
		}
		if (edit.equals("trans")) {
			String sequence1 = string1.substring(y, y+2);
    		String sequence2 = string2.substring(x+1, x+2) 
    			+ string2.substring(x, x+2);
			count = (getCount(sequence1.charAt(0), sequence2.charAt(1), transTable));
			return add1(count, transTotal);
		}
		return 1;
	}
	
	/**
	 * Takes a double c and an int v as input.
	 * Calculate the probabbility using add1-smoothing., therefor add 1 to c.
	 * Because every object in v occurs only ones we can multiply it by two.
	 * return this probabbility.
	 */ 
	private double add1(double c, int v) {
	    return (c+1)/((double)v*2);
	}
	
	/**
	 * Takes a char char1. a char char3 and an int[][] matrix as input.
	 * If char1 and char2 aren't both letters return 0.
	 * Calculate the coordinates by subtracting 97 from char and char2.
	 * If x or y is below 0 return 0.
	 * Get the count from matrix using the coordinates.
	 * return the count.
	 */ 
	private double getCount(char char1, char char2, int[][] matrix) {
	    if (!isLetter(char1, char2)) {
	        return 0.0;
	    }
	    int x = (int)char1-97;
	    int y = (int)char2-97;
	    if ( x < 0 || y < 0) {
	    	return 0;
	    }
	    return (double) matrix[x][y];
	}
	
	/**
	 * Takes a char char2 and a char char2 as input.
	 * return true if char1 and char2 are letters.
	 * return false if not.
	 */ 
	private boolean isLetter (char char1, char char2) {
	    return (Character.isLetter(char1) && Character.isLetter(char1));
	}
}
