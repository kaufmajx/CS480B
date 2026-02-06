package testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import text.WordFinder;

class WordFinderTest
{

  WordFinder wf;

  @Test
  void testTest()
  {
    assertTrue("This will succeed.", true);
    // assertTrue("This will fail!", false);

    assertFalse("This will succeed.", false);
    // assertFalse("This will fail!", true);

    assertEquals(1, 1);
  }

  @Test
  void constructorPass()
  {
    // try {
    wf = new WordFinder("street-names.txt");
    // }
    // catch (IOException e) { fail(); }
  }

  // @Test
  // void constructorFail() {
  //// try
  //// {
  // wf = new WordFinder("fake-file.txt");
  // fail();
  //// }
  //// catch (IOException e) { /* Pass */}
  // }
  //
}
