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
  void ConstructorPass()
  {
    wf = new WordFinder("street-names.txt");
  }

  @Test
  void ConstructorIOEx()
  {
    wf = new WordFinder("fake-names.txt");
  }

  @Test
  void FindTest()
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
