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

public class StreetsReader
{
  private BufferedReader in;
  private CartographyDocument<GeographicShape> geographicShapes;

  public StreetsReader(InputStream is, CartographyDocument<GeographicShape> shapes)
  {
    InputStreamReader isr = new InputStreamReader(is);
    in = new BufferedReader(isr);

    this.geographicShapes = shapes;
  }

  public CartographyDocument<StreetSegment> read(Map<String, Street> streets) throws IOException
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
        String tigerCode = getField(fields, 3);
        String arcID = getField(fields, 4);
        String pre = getField(fields, 5);
        String name = getField(fields, 6);
        String category = getField(fields, 7);
        String suf = getField(fields, 8);
        int tailAddr = parseIntSafe(getField(fields, 9));
        int headAddr = parseIntSafe(getField(fields, 10));

        
        GeographicShape shape = geographicShapes.getElement(arcID);

        String fullName = Street.createCanonicalName(pre, name, category, suf);

        Street street = streets.get(fullName);

        if (street == null)
        {
          street = new Street(pre, name, category, suf, tigerCode);
          streets.put(fullName, street);
        }

        StreetSegment segment = new StreetSegment(arcID, tigerCode, shape, tailAddr, headAddr,
            tailNode, headNode, length);

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

  private static int parseIntSafe(String s)
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

  private static double parseDoubleSafe(String s)
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
  
  private static String getField(String[] fields, int index) {
    if (index < fields.length && fields[index] != null) {
        return fields[index].trim();
    }
    return "";
}
}
