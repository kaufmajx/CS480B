package gui;

import javax.swing.text.Document;
import javax.swing.JTextField;

// VIEW / CONTROLLER
/**
 * Autocomplete view/controller.
 *
 * @author Tenley Kennett
 * @version 2/5
 */
public class CompletingField extends JTextField
{
  private static final long serialVersionUID = 1L;

  private CompletingDocument document;

  @Override
  protected Document createDefaultModel()
  {
    document = new CompletingDocument(this);
    return document;
  }

  /**
   * Set up word finder.
   * 
   * @param fileName
   */
  public void setWordList(final String fileName)
  {
    document.setWordList(fileName);
  }
}
