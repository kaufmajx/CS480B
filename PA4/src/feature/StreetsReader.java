package feature;

import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import geography.GeographicShape;
import gui.CartographyDocument;

/**
 * Reads a .str file and constructs a CartographyDocument of StreetSegments.
 *
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public class StreetsReader
{
  private BufferedReader in;
  private CartographyDocument<GeographicShape> geographicShapes;

  /**
   * Constructs a StreetsReader for the given input stream and geographic shapes.
   *
   * @param is
   *          the input stream of the .str file
   * @param shapes
   *          the geographic shapes document used to look up segment geometry
   */
  public StreetsReader(final InputStream is, final CartographyDocument<GeographicShape> shapes)
  {
    InputStreamReader isr = new InputStreamReader(is);
    in = new BufferedReader(isr);

    this.geographicShapes = shapes;
  }

  /**
   * Reads the .str file and populates the given streets map. Each line is parsed into a
   * StreetSegment and added to its corresponding Street.
   *
   * @param streets
   *          an empty map that will be populated with canonical street names mapped to their Street
   *          objects
   * @return a CartographyDocument containing all parsed StreetSegments and their bounds
   * @throws IOException
   *           if an error occurs while reading the file
   */
  public CartographyDocument<StreetSegment> read(final Map<String, Street> streets)
      throws IOException
  {
    // streets = new HashMap<>();

    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxX = -Double.MAX_VALUE;
    double maxY = -Double.MAX_VALUE;

    Map<String, StreetSegment> segments = new HashMap<>();

    try
    {
      String line;
      while ((line = in.readLine()) != null)
      {
        line = line.trim().strip();
        if (line.isEmpty())
          continue;

        String[] fields = line.split("\t", -1);

        int tailNode = parseIntSafe(getField(fields, 0));
        int headNode = parseIntSafe(getField(fields, 1));
        double length = parseDoubleSafe(getField(fields, 2));
        String tempTigerCode = getField(fields, 3);
        char first = tempTigerCode.charAt(0);
        char last = tempTigerCode.charAt(1);
        String tigerCode = "" + first + last;
        String arcID = getField(fields, 4);
        String pre = getField(fields, 5);
        String name = getField(fields, 6);
        String category = getField(fields, 7);
        String suf = getField(fields, 8);
        int tailAddr = parseIntSafe(getField(fields, 9));
        int headAddr = parseIntSafe(getField(fields, 10));

        GeographicShape shape = geographicShapes.getElement(arcID);

        if (shape != null && shape.getShape() != null)
        {
          Rectangle2D sb = shape.getShape().getBounds2D();

          minX = Math.min(minX, sb.getMinX());
          minY = Math.min(minY, sb.getMinY());
          maxX = Math.max(maxX, sb.getMaxX());
          maxY = Math.max(maxY, sb.getMaxY());
        }

        String fullName = Street.createCanonicalName(pre, name, category, suf);

        Street street = streets.get(fullName);

        if (street == null)
        {
          street = new Street(pre, name, category, suf, tigerCode);
          streets.put(fullName, street);
        }

        int low = Math.min(tailAddr, headAddr);
        int high = Math.max(tailAddr, headAddr);

        StreetSegment segment = new StreetSegment(arcID, tigerCode, shape, low, high, tailNode,
            headNode, length);

        street.addSegment(segment);
        segments.put(arcID, segment);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    if (segments.isEmpty())
      return new CartographyDocument<StreetSegment>(segments, new Rectangle2D.Double());

    Rectangle2D.Double bounds = new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
    return new CartographyDocument<StreetSegment>(segments, bounds);
  }

  /**
   * Parses a string as an integer, returning 0 if the string is null, empty, or not a number.
   *
   * @param s
   *          the string to parse
   * @return the parsed integer, or 0 if parsing fails
   */
  private static int parseIntSafe(final String s)
  {
    try
    {
      return (s == null || s.isEmpty()) ? 0 : Integer.parseInt(s);
    }
    catch (NumberFormatException e)
    {
      return 0;
    }
  }

  /**
   * Parses a string as a double, returning 0.0 if the string is null, empty, or not a number.
   *
   * @param s
   *          the string to parse
   * @return the parsed double, or 0.0 if parsing fails
   */
  private static double parseDoubleSafe(final String s)
  {
    try
    {
      return (s == null || s.isEmpty()) ? 0.0 : Double.parseDouble(s);
    }
    catch (NumberFormatException e)
    {
      return 0.0;
    }
  }

  /**
   * Safely retrieves a field from a split line by index, returning an empty string if the index is
   * out of bounds.
   *
   * @param fields
   *          the array of tab-split fields from a line
   * @param index
   *          the index of the field to retrieve
   * @return the trimmed field value, or an empty string if out of bounds
   */
  private static String getField(final String[] fields, final int index)
  {
    if (index < fields.length && fields[index] != null)
    {
      return fields[index].trim();
    }
    return "";
  }
}
