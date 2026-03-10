package geography;

import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import gui.CartographyDocument;

/**
 * Reads geographic shapes from an input stream and applies a map projection.
 */
public class GeographicShapesReader
{
  private BufferedReader in;
  private MapProjection proj;
  private String tabChar = "\t";

  /**
   * Constructs a GeographicShapesReader from the given input stream and projection.
   * 
   * @param is
   *          the input stream to read from
   * @param proj
   *          the map projection to apply
   */
  public GeographicShapesReader(final InputStream is, final MapProjection proj)
  {
    this.proj = proj;
    try
    {
      in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }

  }

  /**
   * Reads and returns a CartographyDocument containing all parsed geographic shapes.
   * 
   * @return a CartographyDocument with the parsed shapes and bounds
   */
  public CartographyDocument<GeographicShape> read()
  {
    Map<String, GeographicShape> elements = new HashMap<>();

    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxX = -Double.MAX_VALUE;
    double maxY = -Double.MAX_VALUE;

    try
    {
      String line;

      while ((line = in.readLine()) != null)
      {
        line = line.trim();

        String[] shapeInfo = line.split(tabChar);

        if (shapeInfo.length >= 4 && shapeInfo[0].equals("Type:"))
        {
          String id = shapeInfo[3];
          PieceWiseLinearCurve newShape = null;

          if (shapeInfo[1].equals("Polygon"))
          {
            newShape = new Polygon(id);
          }
          else if (shapeInfo[1].equals("PiecewiseLinearCurve"))
          {
            newShape = new PieceWiseLinearCurve(id);
          }

          while ((line = in.readLine()) != null && !line.trim().equals("END"))
          {
            line = line.trim();
            if (line.isEmpty())
              continue;

            String[] points = line.split(tabChar);

            double[] projected = proj.forward(
                new double[] {Double.parseDouble(points[0]), Double.parseDouble(points[1])});

            newShape.add(projected);

            minX = Math.min(minX, projected[0]);
            minY = Math.min(minY, projected[1]);
            maxX = Math.max(maxX, projected[0]);
            maxY = Math.max(maxY, projected[1]);
          }

          elements.put(id, newShape);
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    Rectangle2D.Double bounds = new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);

    return new CartographyDocument<>(elements, bounds);
  }
}
