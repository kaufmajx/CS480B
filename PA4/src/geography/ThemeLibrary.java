package geography;

import feature.StreetThemeLibrary;

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
   * @param code  The code
   * @return The Theme
   */
  Theme getTheme(String code);

}
