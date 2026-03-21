package feature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import geography.GeographicShape;
import gui.CartographyDocument;

public class StreetsReader
{
  private BufferedReader in;
  private CartographyDocument<GeographicShape> geographicShapes;

  public StreetsReader(InputStream is, CartographyDocument<GeographicShape> shapes)
  {

  }

  public CartographyDocument<StreetSegment> read(Map<String, Street> streets) throws IOException
  {
    return null;
  }
}
