package gui;

import java.io.IOException;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import text.WordFinder;


// MODEL
public class CompletingDocument extends PlainDocument {
    private static final long serialVersionUID = 1L;
    
    private CompletingField field;
    private WordFinder finder;
    
    public CompletingDocument(CompletingField field) {
        this.field = field;
    }
    
//    @Override
 // TODO: Implements autocomplete
    public void insertString(int offset, String s, AttributeSet as) throws BadLocationException {
      if (s == null || offset < 0 || s.isEmpty()) return;
      
      // inputs what the user actually typed
      super.insertString(offset, s, as);
      
      // only do autocomplete if there is a WordFinder
      if (finder == null) {
        return;
      }
      
      // get what user has typed so far 
      String currText = getText(0, getLength());
      int userTypedAmt = currText.length();

      String matchedWord = finder.find(currText);
      
      if (matchedWord != null && !matchedWord.equals(currText)) {
        remove(0, getLength()); // remove old typed stuff
        super.insertString(0, matchedWord, as); // put the new matched word
        field.setSelectionStart(userTypedAmt);
        field.setSelectionEnd(matchedWord.length());
      }
      
    }
    
    /*
     * Set up word finder
     */
    public void setWordList(String fileName) {
      try {
        finder = new WordFinder(fileName);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
}