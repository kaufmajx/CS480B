
package text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

public final class WordFinder {
  private TreeSet<String> words;
    
    public WordFinder(String fileName) throws IOException {
      words = new TreeSet<String>();
      
      try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
        String line;
        
        // Read file line by line and add them to the TreeSet
        while ((line = in.readLine()) != null) {
          words.add(line);
        }
      }
    }
    
    public String find(String prefix) {
      TreeSet tailSet = (TreeSet<String>) words.tailSet(prefix);
      if (tailSet.isEmpty()) {
        return null;
      }
      
      Iterator<String> it = tailSet.iterator();
      String firstWord = it.next();
      
      if (firstWord.startsWith(prefix)) {
        return firstWord;
      }
      
      return null;
    }
}
