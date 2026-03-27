package geography;

/**
 * Interface for ThemeLibrary.
 *
 * @author Jelal Kaufman & Tenley Kennett
 */
public interface ThemeLibrary
{

  /**
   * Get the Theme to use for highlighted elements.
   * 
   * @return The Theme
   */
  Theme getHighlightTheme();

  /**
   * Get the Theme to use for the given code.
   * 
   * @param code
   *          The code
   * @return The Theme
   */
  Theme getTheme(String code);

}
