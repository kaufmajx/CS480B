package feature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import geography.GeographicShape;
import geography.PieceWiseLinearCurve;

/**
 * Represents a street composed of one or more street segments.
 *
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public class Street
{
  private PieceWiseLinearCurve shape;
  private String category;
  private String code;
  private String name;
  private String prefix;
  private String suffix;
  private List<StreetSegment> segments;

  /**
   * Constructs a Street with the given name components and TIGER code.
   *
   * @param prefix
   *          the directional prefix (e.g. "N", "S")
   * @param name
   *          the street name (e.g. "Paul")
   * @param category
   *          the street type (e.g. "St", "Ave")
   * @param suffix
   *          the directional suffix (e.g. "E", "W")
   * @param code
   *          the TIGER road type code
   */
  public Street(final String prefix, final String name, final String category, final String suffix,
      final String code)
  {
    this.prefix = prefix;
    this.name = name;
    this.category = category;
    this.suffix = suffix;
    this.code = code;

    segments = new ArrayList<StreetSegment>();

    shape = new PieceWiseLinearCurve(createCanonicalName(prefix, name, category, suffix));
  }

  /**
   * Adds a segment to this street.
   *
   * @param segment
   *          the street segment to add
   */
  public void addSegment(final StreetSegment segment)
  {
    segments.add(segment);
  }

  /**
   * Returns the canonical name of a street from its name components. Blank components are omitted
   * so there are no extra spaces.
   *
   * @param prefix
   *          the directional prefix (e.g. "N")
   * @param name
   *          the street name (e.g. "Paul")
   * @param category
   *          the street type (e.g. "St")
   * @param suffix
   *          the directional suffix (e.g. "E")
   * @return the canonical name (e.g. "N Paul St")
   */
  public static String createCanonicalName(final String prefix, final String name,
      final String category, final String suffix)
  {
    String blankSpace = " ";
    StringBuilder sb = new StringBuilder();
    if (prefix != null && !prefix.isBlank())
      sb.append(prefix).append(blankSpace);
    if (name != null && !name.isBlank())
      sb.append(name).append(blankSpace);
    if (category != null && !category.isBlank())
      sb.append(category).append(blankSpace);
    if (suffix != null && !suffix.isBlank())
      sb.append(suffix);
    return sb.toString().trim();
  }

  /**
   * Returns all segments whose address range contains the given number.
   *
   * @param number
   *          the house number to search for
   * @return a list of matching street segments
   */
  public List<StreetSegment> getSegments(final int number)
  {
    return null;
  }

  /**
   * Returns an iterator over all segments in this street.
   *
   * @return an iterator over the street segments
   */
  public Iterator<StreetSegment> getSegments()
  {
    return segments.iterator();
  }

  /**
   * Returns the composite geographic shape of this street.
   *
   * @return the geographic shape
   */
  public GeographicShape getGeographicShape()
  {
    return shape;
  }

  /**
   * Returns the number of segments in this street.
   *
   * @return the segment count
   */
  public int getSize()
  {
    return segments.size();
  }

}
