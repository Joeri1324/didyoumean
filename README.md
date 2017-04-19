# didyoumean

### What is this repository for? ###
In this repository resides a program to give a suggestion to a user based on an error the user made in entering a URL. An example dictionary is used of government URLs of the United States of America. 
### How does it work? ###
For this program, a Trie(https://en.wikipedia.org/wiki/Trie) data structure is used to store all the URLs. The program works by traversing through the Trie. While it traverses through the Trie, it keeps all the options that do not exceed the maximum edit distance(currently set at 7). Maximum edit distance is the distance between two strings based on the number of keyboard edits that need to be made to transform the first string into the second. Possible edits are Insertions, Deletions, Transposition, and Substitutions. The edit distance is calculated by using the Levenshtein algorithm(https://en.wikipedia.org/wiki/Levenshtein_distance). This results in a two-dimensional array where the final edit-distance can be viewed. An example of this array can be seen on the Wikipedia page. Finally, A probability is calculated for all the suggestions and the top 3 are presented to the user. The probability is calculated by traversing the resulting array of the Levenshtein algorithm backward and looking up te probability of each edit. The probabilities were taken from the paper: http://acl-arc.comp.nus.edu.sg/archives/acl-arc-090501d3/data/pdf/anthology-PDF/C/C90/C90-2036.pdf. To account for unusual edit's add one smoothing is applied to the probability.

### How to run? ###
- Clone the repository
- Navigate to folder
- Navigate to src/ folder
- Compile with "javac DidYouMean.java"
- Run with "java DidYouMean"

