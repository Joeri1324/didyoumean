/**
 * Search implements a URL-checker. It reads in all the files and user input. 
 * Then computes the edit distance and the probabilities. And returns the
 * the most likely options. 
 * by Lina Murady 10776389 and Joeri Sleegers 10631186
 */ 
import java.io.*;
import java.util.*;
import sun.audio.*;

public class Search {
    
    private static final int MAX_EDIT_DISTANCE = 3;
	
    Trie dictionary;
    Read rd;
    WagnerFischer wf;
    Probabillity calc;
    
    public static void main(String[] args) {
    	Search s = new Search();
        s.run();
    }
    
    /**
     * Method that runs all the different parts of the program.
     */
     private void run() {
    	setup();
    	String answer = rd.readUserInput();
       	if (dictionary.contains(answer)) {
       		System.out.println(true);
       	} else {
       		System.out.println(false);
       	
       		System.out.println("Finding options...");
       	    ArrayList<String> options = candidateSelection(answer);
       		ArrayList<Double> probs = getProbs(options, answer);
       		ArrayList<String> result = sort(options, probs);
       		printResult(result);
       	}
    }
    
    /**
     * Takes an ArrayList<String> options and a String answer as input.
     * Iterate through options, calculate the probability for each String given
     * answer and add it to ArrayList<Double> probs.
     * Return probs.
     */ 
    private ArrayList<Double> getProbs(ArrayList<String> options,String answer) {
        ArrayList<Double> probs = new ArrayList<Double>();
        for (int i = 0; i < options.size(); i++) {
   			int[][] matrix = wf.wagnerFischer(answer, options.get(i));
   			Double prob  = calc.probabillity(matrix, answer, options.get(i));
   			probs.add(prob);
   		}
   		return probs;
    }
    
    /**
     * Takes a String answer as input.
     * Selects the apropriate candidates for answer according to MAX_EDIT_DISTANCE
     * If no candidates are found, try to find candites for increments of 1 
     * to MAX_EDIT_DISTANCE. If it exceeds 8 exit the program. 
     * Return options.
     */ 
    private ArrayList<String> candidateSelection(String answer) {
        int maxEdit = MAX_EDIT_DISTANCE;
        ArrayList<String> options = dictionary.getOptions(answer, maxEdit);
        while (options.isEmpty()) {
            maxEdit++;
                if (maxEdit > 8) {
                    System.out.println("Sorry, we couldn't find the requested URL");
                    System.exit(0);
                }
            options = dictionary.getOptions(answer, maxEdit);
        }
        return options;
    }
    
    /**
     * Takes an ArrayList<String> result as input.
     * Outputs the first 3 elements of result.
     */ 
    private void printResult(ArrayList<String> result) {
        System.out.println("What do you mean?: ");
        for (int i = 0; i < result.size(); i++) {
   			if (i > 2) {
   				break;
   			}
       	System.out.println( " - " + result.get(i));
       	}
    }
    
    /**
     * Instantiates rd, dictionary calc and wf.
     */ 
    private void setup() {
    	rd = new Read();
    	dictionary = rd.readInFile("governmentURLs.txt");
    	calc = new Probabillity();
    	wf = new WagnerFischer();
    }
    
    /**
     * Takes an ArrayList<String> options and an ArrayList<Double> probs as input
     * Iterate through probs, for every Double iterate throughprobs,
     * if the double from the first iteration is higher then the second iteration
     * swap them, and swap the corresponding elements in options.
     * return options.
     */ 
    private ArrayList<String> sort(ArrayList<String> options, ArrayList<Double> probs) {
        System.out.println("Sorting...");
    	for (int i = 0; i < probs.size(); i++) {
    		for (int j = 0; j < probs.size(); j++) {
    			if (probs.get(i) > probs.get(j)) {
    				Collections.swap(options, i, j);
    				Collections.swap(probs, i, j);
    			}
    		}
    	}
    	return options;
    }
}
