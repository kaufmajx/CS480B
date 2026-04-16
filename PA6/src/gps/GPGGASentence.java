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
    String stripped = s.contains("*") ? s.substring(0, s.indexOf("*")) : s;
    String[] fields = stripped.split(",");

    String time = fields[1];
    double lat = convertLatitude(fields[2]);
    if (fields[3].equals("S"))
    {
      lat *= -1;
    }
    double longitude = convertLongitude(fields[4]);
    if (fields[5].equals("W"))
    {
      longitude *= -1;
    }

    int fixType = Integer.parseInt(fields[6]);
    int satellites = Integer.parseInt(fields[7]);
    double dilution = Double.parseDouble(fields[8]);
    double altitude = Double.parseDouble(fields[9]);
    String altitudeUnits = fields[10];
    double seaLevel = Double.parseDouble(fields[11]);
    String geoidUnits = fields[12];

    GPGGASentence g = new GPGGASentence(time, lat, longitude, fixType, satellites, dilution,
        altitude, altitudeUnits, seaLevel, geoidUnits);
    return g;

  }

}
