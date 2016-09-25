/**
 * Read class where all read in functions are combined
 * by: Lina Murardy 10776389 and Joeri Sleegers 10631186
 */ 

import java.io.*;
import java.util.*;

public class Read {
    
    /**
     * Takes a String fileName as input.
     * Creates an int[][] table from fileName.
     * returns table
     */ 
    public int[][] readInMatrix(String fileName) {
        int matrixSize = countLines(fileName);
        int[][] table = new int[matrixSize][26];
    	try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			int lineNumber = 0;
			String[] lineArray; 
			while((line = br.readLine()) != null) {
				lineArray = line.split("\\s+");
				for (int i = 0; i < lineArray.length; i++) {
					table[lineNumber][i] = Integer.parseInt(lineArray[i]);
				}
				lineNumber++;
			}	
    	} catch (IOException e) {
			System.err.println("Error: " + e);
		}
		return table;
    }
    
    /**
     * Takes a String fileName as input.
     * Returns the number of lines of that file.
     */ 
    private int countLines(String fileName) {
        int lineNumber = 0;
        try {
    		BufferedReader br = new BufferedReader(new FileReader(fileName));
    		String line;
    		String[] lineArray; 
    		while((line = br.readLine()) != null) {
    			lineNumber++;
    		}	
    	} catch (IOException e) {
    		System.err.println("Error: " + e);
    	}
    	return lineNumber;
    }
    
    /**
     * Takes a String fileName as input.
     * Create a Trie dictionary.
     * Put every line of filName in dictionary.
     * return dictionary.
     */ 
	public Trie readInFile(String fileName) {
		Trie dictionary = new Trie();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			Integer mapInt = 0;
			while((line = br.readLine()) != null) {
				int spaceIndex = line.indexOf(" ");
				if (!(line.equals(""))){
					String trimmedLine = line.trim().replaceAll("\n ", "");
					dictionary.put(trimmedLine);
					mapInt ++;
				}	
			}
		} catch (IOException e) {
			System.err.println("Error: " + e);
		}
		return dictionary;
	}
	
    /**
	 * Uses a BuffereadReader and an InputStreamReader to get input
	 * from the user. 
	 * return this input.
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
