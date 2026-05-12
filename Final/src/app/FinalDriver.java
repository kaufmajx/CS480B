package app;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

/**
 * The main class for the Final Project.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public class FinalDriver 
{
  /**
   * The entry point for the application.
   * 
   * @param args The command-line arguments (IGNORED)
   * @throws InterruptedException if something goes wrong with Swing
   * @throws InvocationTargetException if something goes wrong with Swing
   */
  public static void main(final String[] args) 
      throws InterruptedException, InvocationTargetException 
  {
    SwingUtilities.invokeAndWait(new FinalApp());
  }
}