/**
 * Implementation of a node in a Trie
 * 
 * @author Joeri Sleegers
 */ 

import java.util.*;

public class TrieNode {

    ArrayList<TrieNode> children;
    String value;
    String sequence;
    boolean word;
    
    /**
     * Constructor of TrieNode. Initializes children.
     */ 
    public TrieNode() {
        children = new ArrayList<TrieNode>();
    }
    
    /**
     * Constructor of TrieNode. Takes a String inut as input. Initializes 
     * children. Set value to word.
     *
     * @param word word to set the TrieNode to.
     */ 
    public TrieNode(String word) {
        children = new ArrayList<TrieNode>();
        value = word;
    }
    
    /**
     * Constructor of TrieNode. Takes a String input and a String sequence as 
     * input. Initializes childre. Set value to input and sequence of TrieNode
     * to sequence.
     *
     * @param word     value of TrieNode to be set
     * @param sequence sequence of TrieNode to be set
     */ 
    public TrieNode(String word, String sequence) {
        children = new ArrayList<TrieNode>();
        value = word;
        this.sequence = sequence;
    }
    
    /**
     * Returns the children of the TrieNode.
     *
     * @return Children of the trienode
     */ 
    public ArrayList<TrieNode> getChildren() {
        return children;
    }
    
    /**
     * Returns the value of the TrieNode.
     *
     * @return value of the TrieNode
     */ 
    public String getValue() {
        return value;
    }
    
    /**
     * Takes a String word as input. Create a new TrieNode newNode using word
     * Add newNode to children. Returns newnode.
     *
     * @param  word     word to add to the Trie
     * @param  sequence sequence to add to the Trie
     * @return          TrieNode that was added to the Trie.
     */ 
    public TrieNode add(String word, String sequence) {
        TrieNode newNode = new TrieNode(word, sequence);
        children.add(newNode);
        return newNode;
    }
    
    /**
     * Set word to True.
     */ 
    public void setWord() {
        word = true;
    }
    
    /**
     * Returns true is word is true else return false.
     *
     * @return Returns wether the node is a word or not.
     */ 
    public boolean isWord() {
        return word;
    }
}
