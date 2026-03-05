package geography;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import gui.CartographyDocument;

public class GeographicShapesReader
{
  private BufferedReader in;
  private MapProjection proj;

  public GeographicShapesReader(InputStream is, MapProjection proj)
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

        String[] shapeInfo = line.split("\t");

        if (shapeInfo.length >= 4 && shapeInfo[0].equals("Type:") && shapeInfo[1].equals("Polygon"))
        {

          String id = shapeInfo[3];
          PieceWiseLinearCurve newPoly = new Polygon(id);

          while ((line = in.readLine()) != null && !line.equals("END"))
          {
            String[] points = line.split("\t");

            double[] projected = proj.forward(
                new double[] {Double.parseDouble(points[0]), Double.parseDouble(points[1])});

            newPoly.add(projected);

            minX = Math.min(minX, projected[0]);
            minY = Math.min(minY, projected[1]);
            maxX = Math.max(maxX, projected[0]);
            maxY = Math.max(maxY, projected[1]);
          }

          elements.put(id, newPoly);
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
