package testing;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.text.Document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.CompletingDocument;
import gui.CompletingField;

class CompletingFieldTest
{
  private CompletingField field;
  
  @BeforeEach
  void setUp() {
    field = new CompletingField();
  }
  
  @Test
  void testCreateDefaultModel() {
    // The document should be created automatically
    Document doc = field.getDocument();
    
    assertNotNull(doc);
    field.setWordList("street-names.txt");
  }

}
