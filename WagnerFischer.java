/**
 * Implemenation of the Wagner-Fischer algorithm 
 * For implementing this algorithm the paper "On approximate string matching"
 * was used.  
 * by Lina Murady 10776389 and Joeri Sleegers 10631186
 */ 

public class WagnerFischer {

    /**
     * Takes a String string1 and a String string2 as input.
     * Create an int[][] matrix with the size of string2 and string 1.
     * Fill the first row and colum of the matrix with ints from 0 to
     * the length of the row's and colums respectively. 
     * Fill the array by iterating over string2 and string 1.
     * If the charachters are equal, the number from the field diagonally
     * up to the left is copied to the apropriate field in the matrix.
     * If not, the edit with the lowest value is choosen, and one is added to it
     * A horizontal step corresponds to a deletion, a vertical step to an 
     * insertion and a diagonal to a substitution.
     * return the matrix.
     */ 
    public int[][] wagnerFischer(String string1, String string2) {
    	int[][] matrix = new int[string2.length()+1][string1.length()+1];
    	for (int i = 0; i < string2.length()+1; i++) {
    		matrix[i][0] = i;
    	}
    	for (int i = 0; i < string1.length()+1; i++) {
    		matrix[0][i] = i;
    	}
    	int indexI; 
    	int indexJ;
    	for (int j = 0; j < string1.length(); j++) {
    		indexJ = j+1;
    		String first = string1.substring(j, j+1);
    		for (int i = 0; i < string2.length(); i++) {
    			indexI = i+1;
    			String second = string2.substring(i, i+1);
    			if (first.equals(second)) {
    				matrix[indexI][indexJ] = matrix[indexI-1][indexJ-1];  
    			} else {
    				int deletion = matrix[indexI-1][indexJ] + 1;
    				int insertion = matrix[indexI][indexJ-1] + 1;
    				int subtitution =  matrix[indexI-1][indexJ-1] + 1;
    				int tempMin = Math.min(deletion, insertion);
    				int min = Math.min(tempMin, subtitution);
    				if (j > 0 && i > 0 ) {
    					String sequence1 = string1.substring(j-1, j+1);
    					String sequence2 = string2.substring(i, i+1) + string2.substring(i-1,i);
	    				if (sequence1.equals(sequence2)) {
	    					int transposition = matrix[indexI-2][indexJ-2] + 1;
	    					min = Math.min(min, transposition);
	    				}
    				}   	
    				matrix[indexI][indexJ] = min;
    			    }
    		    }
    	    }
        return matrix;
    }

    /**
     * Takes a String string1 and a String string2 as input.
     * Creates a matrix using wagner-fischer.
     * returns the bottom right field of that matrix.
     */ 
    public int editDistance(String string1, String string2) {
        int[][] matrix = wagnerFischer(string1, string2);
        return matrix[matrix.length-1][matrix[0].length-1];
    }
}
