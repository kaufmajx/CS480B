package gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import text.WordFinder;

// MODEL
/**
 * Autocomplete.
 *
 * @author Tenley Kennett & Jelal Kaufman
 * @version 2/5
 */
public class CompletingDocument extends PlainDocument
{
  private static final long serialVersionUID = 1L;

  private CompletingField field;
  private WordFinder finder;

  /**
   * Constructor.
   * 
   * @param field
   */
  public CompletingDocument(final CompletingField field)
  {
    this.field = field;
  }

  /**
   * Implements autocomplete.
   * 
   * @param offset
   * @param s
   * @param as
   */
  public void insertString(final int offset, final String s, final AttributeSet as)
      throws BadLocationException
  {
    if (offset < 0 || s == null || s.isEmpty())
      return;

    // inputs what the user actually typed
    super.insertString(offset, s, as);

    // only do autocomplete if there is a WordFinder
    if (finder == null)
    {
      return;
    }

    // get what user has typed so far
    String currText = getText(0, getLength());
    int userTypedAmt = currText.length();

    String matchedWord = finder.find(currText);

    if (matchedWord == null) 
    {
    	return;
    }
   
    remove(0, getLength()); // remove old typed stuff
    super.insertString(0, matchedWord, as); // put the new matched word
    field.setSelectionStart(userTypedAmt);
    field.setSelectionEnd(matchedWord.length());
    

  }

  /**
   * Set word list.
   * 
   * @param fileName
   */
  public void setWordList(final String fileName)
  {
    finder = new WordFinder(fileName);
  }
}
