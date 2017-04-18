/**
 * Implementation of a Trie datastructure with an addition of a method which
 * is able to get all the values in the trie which don't exceed a given edit
 * distance
 * 
 * @author Joeri Sleegers
 */ 

import java.util.*;

public class Trie {
    
    TrieNode topNode;
    
    public Trie() {
        topNode = new TrieNode();
    }
    
    /**
     * Takes a String word as input. Initialize nodeIterator with topNode. 
     * Iterate through word in iterations of letter's as a substring. Check if 
     * nodeIterator has a child with current letter. If not add a new TrieNode 
     * to children of nodeIterator. Set nodeIterator to the child whith the 
     * value of the letter. Set the boolean word of the node of the last letter 
     * to true.
     *
     * @param word word to be inserted in the Trie
     */ 
    public void put(String word) {
        TrieNode nodeIterator = topNode;

        for (int i = 0; i < word.length(); i++) {
            boolean containsSequence = false;
            String letter = word.substring(i, i+1);
            ArrayList<TrieNode> children = nodeIterator.getChildren();

            for (int j = 0; j < children.size(); j++) {
                String childLetter = children.get(j).getValue();
                if (childLetter.equals(letter)){
                    containsSequence = true;
                    nodeIterator = children.get(j);
                }    
            }

            if (!containsSequence) {
                String sequence = "";
                if (!(nodeIterator.sequence == null)) {
                    sequence = nodeIterator.sequence;
                }
                nodeIterator = nodeIterator.add(letter, sequence + letter);
            }
        }
        nodeIterator.setWord();
    }
    
    /**
     * Takes a String word as input. Initialize nodeIterator with topNode. 
     * Iterate through word in iterations of letter's as a substring. Check if
     * nodeIterator has a child with current letter, If not return false,
     * If the TrieNode of the last letter is a word return true,  If not 
     * return false.
     *
     * @param  word word to be checked if Trie contains it
     * @return      true if Trie contains word, false if Trie doesn't
     */ 
    public boolean contains(String word) {
        TrieNode nodeIterator = topNode;

        for (int i = 0; i < word.length(); i++) {
            boolean containsSequence = false;
            String letter = word.substring(i, i+1);
            ArrayList<TrieNode> children = nodeIterator.getChildren();

            for (int j = 0; j < children.size(); j++) {
                String childLetter = children.get(j).getValue();
                if (childLetter.equals(letter)){
                    containsSequence = true;
                    nodeIterator = children.get(j);
                }
            }    
            if (!containsSequence) {
                return false;
            }
        }
        if (nodeIterator.isWord()) {
            return true;
        }
        return false;
    }
    
    /**
     * Takes a String input and an int maxEdit as input. Iterate trough input 
     * letter by letter. Insert the first row of the Trie into 
     * ArrayList<TrieNode> toBeSearched, for every letter, if the sequence up
     * untill that letter, doesn't exceed the max edit distance, put the 
     * children into toBeSearched. If the sequence is a word, add it to result.
     * After the iterator over the word is completed. Continue this process
     * untill toBeSearched is empty. Return result.
     *
     * @param userInput
     */ 
    public ArrayList<String> getOptions(String userInput, int maxEdit) {
        WagnerFischer wf = new WagnerFischer();
        ArrayList<TrieNode> toBeSearched = new ArrayList<TrieNode>();
        ArrayList<TrieNode> children     = topNode.getChildren();
        ArrayList<TrieNode> temp         = new ArrayList<TrieNode>();
        ArrayList<String> result         = new ArrayList<String>();
        toBeSearched.addAll(children);
        String sequence = "";
        int editDistance;

        for (int i = 0; i < userInput.length(); i++) {
            sequence = sequence.concat(userInput.substring(i, i+1));
            temp = new ArrayList<TrieNode>();
            for (int j = 0; j < toBeSearched.size(); j++) {
                editDistance = wf.editDistance(sequence,
                                               toBeSearched.get(j).sequence);
                if (editDistance <= maxEdit) {
                    temp.addAll(toBeSearched.get(j).getChildren());
                    editDistance = wf.editDistance(userInput, 
                                                   toBeSearched.get(j).sequence);
                    if (editDistance <= maxEdit && toBeSearched.get(j).isWord()) {
                        result.add(toBeSearched.get(j).sequence);
                    }
                }
            }
            toBeSearched = temp;
        }

        while (toBeSearched.size() > 0) {
            temp = new ArrayList<TrieNode>();
            for (int j = 0; j < toBeSearched.size(); j++) {
                editDistance = wf.editDistance(toBeSearched.get(j).sequence, 
                                               userInput);
                if (editDistance <= maxEdit) {
                    temp.addAll(toBeSearched.get(j).getChildren());
                    if (toBeSearched.get(j).isWord()) {
                       result.add(toBeSearched.get(j).sequence);
                    }
                }
            }    
            toBeSearched = temp;
        }
        return result;
    }
}
