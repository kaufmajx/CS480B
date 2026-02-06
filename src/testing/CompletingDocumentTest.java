package testing;

import gui.*;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.FileSystemLoopException;

import javax.swing.text.BadLocationException;

import org.junit.jupiter.api.Test;

class CompletingDocumentTest {

	@Test
	void InsertStringTest() {
		CompletingField cF = new CompletingField();
		cF.setText("Madi");
		CompletingDocument cD = new CompletingDocument(cF);

		try {
			cD.insertString(0, null, null);
			cD.insertString(-1, null, null);
			cD.insertString(-1, "", null);
			cD.insertString(0, "", null);
			cD.insertString(0, "", null);
			cD.setWordList("street-names.txt");
			cD.insertString(0, "so", null);
			cD.insertString(0, "", null);
		} catch (BadLocationException e) {
			fail();
		}
	}
	
	@Test
	void FindTest() {
		
		CompletingField cF = new CompletingField();
		cF.setText("Ma");
//		cF.setWordList("street-names.txt");
		CompletingDocument cD = new CompletingDocument(cF);

		try {			
			cD.insertString(0, "", null);
			cD.setWordList("street-names.txt");
			cD.insertString(0, "d", null);
			cD.insertString(0, "ison", null);
			cF.setText(null);
			cF.setText("103");
			cD.insertString(0, "1", null);
			System.out.print(cF.getText());
		} catch (BadLocationException e) {
			fail();
		}
		
    }
}
