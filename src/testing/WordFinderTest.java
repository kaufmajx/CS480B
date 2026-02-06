package testing;

import static org.junit.Assert.assertEquals;
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
    wf = new WordFinder("street-names.txt");
  }

  @Test
  void constructorIOEx()
  {
    wf = new WordFinder("fake-names.txt");
  }

  @Test
  void findTest()
  {
    wf = new WordFinder("street-names.txt");
    String match = wf.find("Ma");
    assertEquals("Mabe", match);

    String match2 = wf.find("Mini");
    assertNotEquals("Miniland", match2);

    // tailSet will with "Mac" (which doesn't start with "Mabz")
    String betweenWords = wf.find("Mabz");
    assertEquals(null, betweenWords);

    String noMatch = wf.find("unreal st");
    assertEquals(null, noMatch);

  }

}
