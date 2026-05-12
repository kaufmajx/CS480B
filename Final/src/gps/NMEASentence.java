package gps;

/**
 * NMEA Sentence class.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public abstract class NMEASentence
{
  /**
   * Add a string to the checksum.
   * 
   * @param s
   *          string
   * @param originalChecksum
   *          checksum number
   * @return adjusted checksum
   */
  public static int addToChecksum(final String s, final int originalChecksum)
  {
    // Taken straight from slides
    int current, i, length;
    int checksum = originalChecksum;

    length = s.length();
    for (i = 0; i <= length - 1; i++)
    {
      current = (int) (s.charAt(i)); // Get a char
      checksum ^= current; // xor the char into the checksum
    }

    return checksum;
  }

  /**
   * Converts an NMEA-formatted latitude string (ddmm.mmmm) to decimal degrees.
   *
   * @param latitudeString
   *          the NMEA latitude string to convert (e.g., "3723.2475")
   * @return the latitude in decimal degrees
   */
  public static double convertLatitude(final String latitudeString)
  {
    // "3723.2475" -> degrees = 37, minutes = 23.2475
    int degrees = Integer.parseInt(latitudeString.substring(0, 2));
    double minutes = Double.parseDouble(latitudeString.substring(2));
    return degrees + (minutes / 60);
  }

  /**
   * Converts an NMEA-formatted longitude string (dddmm.mmmm) to decimal degrees. (note 3 ds).
   *
   * @param longitudeString
   *          the NMEA latitude string to convert (e.g., "37123.2475")
   * @return the longitude in decimal degrees
   */
  public static double convertLongitude(final String longitudeString)
  {
    // "3723.2475" -> degrees = 37, minutes = 23.2475
    int degrees = Integer.parseInt(longitudeString.substring(0, 3));
    double minutes = Double.parseDouble(longitudeString.substring(3));
    return degrees + (minutes / 60);
  }
}
