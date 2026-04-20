package gps;

/**
 * GPGGA Sentence class.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public class GPGGASentence extends NMEASentence
{
  private String time;
  private double longitude;
  private double latitude;
  private int fixType;
  private int satellites;
  private double dilution;
  private double altitude;
  private String altitudeUnits;
  private double seaLevel;
  private String geoidUnits;

  /**
   * Consuruct a GPGGA Sentence.
   *
   * @param time
   *          UTC form
   * @param latitude
   *          dddmm.mmmm
   * @param longitude
   *          ddmm.mmmm
   * @param fixtype
   *          0 = no fix, 1 = GPS, 2 = DGPS
   * @param satellites
   *          number of satellites
   * @param dilution
   *          horizontal dilution of precision
   * @param altitude
   *          altitude above sea level
   * @param altitudeUnits
   *          ex. m for meters
   * @param seaLevel
   *          geoid separation from sea level
   * @param geoidUnits
   *          units of geoid separation (e.g. "M" for meters)
   */
  public GPGGASentence(final String time, final double latitude, final double longitude,
      final int fixType, final int satellites, final double dilution, final double altitude,
      final String altitudeUnits, final double seaLevel, final String geoidUnits)
  {
    this.time = time;
    this.latitude = latitude;
    this.longitude = longitude;
    this.fixType = fixType;
    this.satellites = satellites;
    this.dilution = dilution;
    this.altitude = altitude;
    this.altitudeUnits = altitudeUnits;
    this.seaLevel = seaLevel;
    this.geoidUnits = geoidUnits;
  }

  // all getters

  /**
   * Get the time.
   * 
   * @return time string in HHMMSS format
   */
  public String getTime()
  {
    return time;
  }

  /**
   * Get the longitude in decimal degrees.
   * 
   * @return longitude (negative = West)
   */
  public double getLongitude()
  {
    return longitude;
  }

  /**
   * Get the latitude in decimal degrees.
   * 
   * @return latitude (negative = South)
   */
  public double getLatitude()
  {
    return latitude;
  }

  /**
   * Get the fix type.
   * 
   * @return fix type (0 = invalid, 1 = GPS, 2 = DGPS)
   */
  public int getFixType()
  {
    return fixType;
  }

  /**
   * Get the number of satellites in use.
   * 
   * @return satellite count
   */
  public int getSatellites()
  {
    return satellites;
  }

  /**
   * Get the horizontal dilution of precision.
   * 
   * @return dilution
   */
  public double getDilution()
  {
    return dilution;
  }

  /**
   * Get the altitude above sea level.
   * 
   * @return altitude
   */
  public double getAltitude()
  {
    return altitude;
  }

  /**
   * Get the altitude units.
   * 
   * @return altitude units (e.g. "M" for meters)
   */
  public String getAltitudeUnits()
  {
    return altitudeUnits;
  }

  /**
   * Get the geoid separation from sea level.
   * 
   * @return sea level
   */
  public double getSeaLevel()
  {
    return seaLevel;
  }

  /**
   * Get the geoid units.
   * 
   * @return geoid units (e.g. "M" for meters)
   */
  public String getGeoidUnits()
  {
    return geoidUnits;
  }

  /**
   * Parse a raw GPGGA sentence string into a GPGGA sentence object.
   * 
   * ex. $GPGGA,210230,3855.4487,N,09446.0071,W,1,07,1.1,370.5,M,-29.5,M,,*7A
   * 
   * @param s
   * @return
   */
  public static GPGGASentence parseGPGGA(String s)
  {
   
    // Split checksum
    String[] parts = s.split("\\*");
    String data = parts[0]; // everything before *
    String checksumStr = parts.length > 1 ? parts[1] : "0";

    // OPTIONAL: validate checksum
    try
    {
      int expected = Integer.parseInt(checksumStr, 16);
      int actual = 0;

      // Skip the '$' when computing checksum
      actual = NMEASentence.addToChecksum(data.substring(1), 0);

      if (actual != expected)
      {
        // Bad checksum → ignore sentence
        return null;
      }
    }
    catch (Exception e)
    {
      return null; // bad checksum format
    }

    // Split fields (keep empty ones!)
    String[] f = data.split(",", -1);

    if (f.length < 15)
    {
      return null;
    }

    // Safe parsing helpers
    java.util.function.Function<String, Double> d = str -> str.isEmpty() ? 0.0
        : Double.parseDouble(str);

    java.util.function.Function<String, Integer> i = str -> str.isEmpty() ? 0
        : Integer.parseInt(str);

    try
    {
      String time = f[1].isEmpty() ? "0" : f[1];

      double lat = f[2].isEmpty() ? 0 : NMEASentence.convertLatitude(f[2]);
      if ("S".equals(f[3]))
        lat *= -1;

      double lon = f[4].isEmpty() ? 0 : NMEASentence.convertLongitude(f[4]);
      if ("W".equals(f[5]))
        lon *= -1;

      int fixType = i.apply(f[6]);
      int satellites = i.apply(f[7]);
      double hdop = d.apply(f[8]);
      double altitude = d.apply(f[9]);
      String altitudeUnits = f[10].isEmpty() ? "0" : f[10];
      double geoidSep = d.apply(f[11]);
      String geoidUnits = f[12].isEmpty() ? "0" : f[12];

      // Ignored but safely parsed
      double dgpsAge = d.apply(f[13]);
      int dgpsStation = i.apply(f[14]);

      return new GPGGASentence(time, lat, lon, fixType, satellites, hdop, altitude, altitudeUnits,
          geoidSep, geoidUnits);
    }
    catch (Exception e)
    {
      return null;
    }
  }
}
