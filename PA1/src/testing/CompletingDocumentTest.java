package testing;

import gui.*;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.FileSystemLoopException;

import javax.swing.text.BadLocationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompletingDocumentTest
{

  private CompletingField field;

  @BeforeEach
  void setUp()
  {
    field = new CompletingField();
  }

  @Test
  void InsertStringTest()
  {
    field = new CompletingField();
    field.setText("Madi");
    CompletingDocument cD = new CompletingDocument(field);

    try
    {
      cD.insertString(0, null, null);
      cD.insertString(-1, null, null);
      cD.insertString(0, "", null);
      cD.insertString(-1, "", null);
      cD.insertString(0, "", null);

      cD.setWordList("street-names.txt");

      cD.insertString(0, "so", null);
      cD.insertString(0, "", null);
    }
    catch (BadLocationException e)
    {
      fail();
    }
  }

  @Test
  void FindTest()
  {

    field = new CompletingField();
    field.setText("Ma");
    // cF.setWordList("street-names.txt");
    CompletingDocument cD = new CompletingDocument(field);

    try
    {
      cD.insertString(0, "", null);
      cD.setWordList("street-names.txt");
      cD.insertString(0, "d", null);
      cD.insertString(0, "ison", null);
      field.setText(null);
      field.setText("103");
      cD.insertString(0, "1", null);
    }
    catch (BadLocationException e)
    {
      fail();
    }
  }
  
  @Test
  void NoOptionTest()
  {

    field = new CompletingField();
    field.setText("MFDS124fae3a");
    CompletingDocument cD = new CompletingDocument(field);

    try
    {
      cD.insertString(0, "", null);
      cD.setWordList("street-names.txt");
      cD.insertString(0, "d", null);
      cD.insertString(0, "ison", null);
    }
    catch (BadLocationException e)
    {
      fail();
    }
  }


  @Test
  void NoMatchFoundTest()
  {
    // for where matchedWord is null
    CompletingField field = new CompletingField();
    field.setWordList("street-names.txt");

    try
    {
      // 'type' a string that won't match anything in street-names.txt
      field.setText("zzzzzzz");

      // text should remain as typed (no autocomplete)
      assertEquals("zzzzzzz", field.getText());
    }
    catch (Exception e)
    {
      fail();
    }
  }

  @Test
  void ExactMatchTest()
  {
    // for where matchedWord.equals(currText) is true
    CompletingField field = new CompletingField();
    field.setWordList("street-names.txt");

    try
    {
      // Type a complete word that exists in street-names.txt
      field.setText("Zynel");
      // The text should be exactly "Zynel" with no selection
      assertEquals("Zynel", field.getText());
    }
    catch (Exception e)
    {
      fail();
    }
  }
}
