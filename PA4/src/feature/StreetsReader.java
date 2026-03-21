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
    streets = new HashMap<>();

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
        System.out.println(line);

        String[] shapeInfo = line.split("\t");
//        System.out.println("Shape Info:");
        
        Street newSt = new StreetSegment(shapeInfo[4], shapeInfo[3], shape, low, high, shapeInfo[0], shapeInfo[1], shapeInfo[2]);
        
        
        
        if (shapeInfo.length >= 4 && shapeInfo[0].equals("Type:"))
//        {
//          String id = shapeInfo[3];
//          PieceWiseLinearCurve newShape = null;
//
//          if (shapeInfo[1].equals("Polygon"))
//          {
//            newShape = new Polygon(id);
//          }
//          else if (shapeInfo[1].equals("PiecewiseLinearCurve"))
//          {
//            newShape = new PieceWiseLinearCurve(id);
//          }
//
//          while ((line = in.readLine()) != null && !line.trim().equals("END"))
//          {
//            line = line.trim();
//            if (line.isEmpty())
//              continue;
//
//            String[] points = line.split("\t");
//
////            double[] projected = proj.forward(
////                new double[] {Double.parseDouble(points[0]), Double.parseDouble(points[1])});
////
////            newShape.add(projected);
////
////            minX = Math.min(minX, projected[0]);
////            minY = Math.min(minY, projected[1]);
////            maxX = Math.max(maxX, projected[0]);
////            maxY = Math.max(maxY, projected[1]);
//          }
//
////          elements.put(id, newShape);
//        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    Rectangle2D.Double bounds = new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);

    return null;
  }
}
