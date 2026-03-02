package geography;

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
    String line;
    CartographyDocument cd;
    Map<String, GeographicShape> elements = new HashMap<>();

    try
    {
      while ((line = in.readLine()) != null)
      {
        // System.out.println("Outer while loop");

        line = line.trim().replace("\n", "");
        String[] shapeInfo = line.split("\t");

        // System.out.println("shapeInfo: " + shapeInfo[0]);

        if (shapeInfo[0].equals("Type:"))
        {
          // System.out.println("First if: " + shapeInfo[0]);
          if (shapeInfo[1].equals("Polygon"))
          {
            // System.out.println(2"Second ifif: " + shapeInfo[1]);
            PieceWiseLinearCurve newPoly = new Polygon(shapeInfo[2]);
            while (!(line = in.readLine()).equals("END"))
            {
              String[] points = line.split("\t");
              double[] linePoint = new double[] {Double.parseDouble(points[0]),
                  Double.parseDouble(points[1])};
              newPoly.add(linePoint);
              System.out.println(points[0]);
            }
            elements.put(shapeInfo[1], newPoly);
          }
        }
      }
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    cd = new CartographyDocument<>(elements, null);
    return cd;

  }
}
