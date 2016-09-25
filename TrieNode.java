/**
 * Implementation of a node in a Trie
 * by: Lina Murary 10776389 and Joeri Sleegers 10631186
 */ 

import java.util.*;

public class TrieNode {

    ArrayList<TrieNode> children;
    String value;
    String sequence;
    boolean word;
    
    /**
     * constructor of TrieNode
     * Initializes children.
     */ 
    public TrieNode() {
        children = new ArrayList<TrieNode>();
    }
    
    /**
     * constructor of TrieNode
     * Takes a String inut as input
     * Initializes children.
     * Set value to input.
     */ 
    public TrieNode(String input) {
        children = new ArrayList<TrieNode>();
        value = input;
    }
    
    /**
     * constructor of TrieNode
     * Takes a String input and a String sequence as input.
     * Initializes childre,
     * set value to input and sequence of TrieNode to sequence.
     */ 
    public TrieNode(String input, String sequence) {
        children = new ArrayList<TrieNode>();
        value = input;
        this.sequence = sequence;
    }
    
    /**
     * Return the children of the TrieNode
     */ 
    public ArrayList<TrieNode> getChildren() {
        return children;
    }
    
    /**
     * return the value of the TrieNode
     */ 
    public String getValue() {
        return value;
    }
    
    /**
     * Takes a String input as input
     * Create a new TrieNode newNode using input
     * Add newNode to children
     * return newnode
     */ 
    public TrieNode add(String input, String sequence) {
        TrieNode newNode = new TrieNode(input, sequence);
        children.add(newNode);
        return newNode;
    }
    
    /**
     * Set word to true
     */ 
    public void setWord() {
        word = true;
    }
    
    /**
     * return true is word is true
     * else return false
     */ 
    public boolean isWord() {
        return word;
    }
}




