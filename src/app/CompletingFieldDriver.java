package app;

import gui.*;
import text.WordFinder;

import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

/**
 * A driver that can be used to test the CompletingField and CompletingDocument classes.
 *
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class CompletingFieldDriver
{
  /**
   * The entry point of the application.
   *
   * @param args
   *          The command line arguments
   * @throws InterruptedException
   *           if something goes wrong
   * @throws InvocationTargetException
   *           if something goes wrong
   */
  public static void main(final String[] args)
      throws InterruptedException, InvocationTargetException
  {
    String fn = "street-names.txt";
    if (args.length == 1)
    {
      fn = args[0];
    }

    SwingUtilities.invokeAndWait(new CompletingFieldApp(fn));
  }
}
