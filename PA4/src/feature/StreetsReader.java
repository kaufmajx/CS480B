package feature;

import java.awt.geom.Rectangle2D;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import geography.GeographicShape;
import geography.PieceWiseLinearCurve;
import geography.Polygon;
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
        line = line.trim();
        if (line.isEmpty())
          continue;

        String[] fields = line.split("\t");
        for (int i = 0; i < fields.length; i++)
          fields[i] = fields[i].trim();
        
        if (fields.length < 11)
          continue;
        int tailNode = Integer.parseInt(fields[0]);
        int headNode = Integer.parseInt(fields[1]);
        double length = Double.parseDouble(fields[2]);
        String tigerCode = fields[3];
        String arcID = fields[4];
        String pre = fields[5];
        String name = fields[6];
        String category = fields[7];
        String suf = fields[8];
        int tailAddr = Integer.parseInt(fields[9]);
        int headAddr = Integer.parseInt(fields[10]);

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
      return new CartographyDocument<>(segments, new Rectangle2D.Double());
    
    Rectangle2D.Double bounds = new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
    return new CartographyDocument<>(segments, bounds);
  }
}
