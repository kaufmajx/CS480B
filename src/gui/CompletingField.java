package gui;

import javax.swing.text.Document;
import javax.swing.JTextField;

// VIEW / CONTROLLER
public class CompletingField extends JTextField {
    private static final long serialVersionUID = 1L;
    
    private CompletingDocument document;
    
    @Override
    protected Document createDefaultModel() {
      document = new CompletingDocument(this);
      return document;
    }
    
    public void setWordList(String fileName) {
      document.setWordList(fileName);
    }
}