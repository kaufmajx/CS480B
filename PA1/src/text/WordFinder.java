package text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

/**
 * WordFinder class.
 *
 * @author Tenley Kennett & Jelal Kaufman
 * @version 2/5
 */
public final class WordFinder
{
  private TreeSet<String> words;

  /**
   * Constructor.
   * 
   * @param fileName
   */
  public WordFinder(final String fileName)
  {
    words = new TreeSet<String>();

    try (BufferedReader in = new BufferedReader(new FileReader(fileName)))
    {
      String line;

      // read file line by line and add them to the TreeSet
      while ((line = in.readLine()) != null)
      {
        words.add(line.trim());
      }
    }
    catch (IOException e)
    {
      // Handle later
    }
  }

  /**
   * Find the nearest word in the TreeSet from a prefix.
   * 
   * @param prefix
   * @return closest word
   */
  public String find(final String prefix)
  {
    String candidate = words.ceiling(prefix);
    if (candidate == null)
    {
      return null;
    }

    if (candidate.equals(prefix)) // If we already typed the whole word
    {
      // try the next one
      candidate = words.higher(candidate);
    }

    return candidate.startsWith(prefix) ? candidate.trim() : null;
  }
}
