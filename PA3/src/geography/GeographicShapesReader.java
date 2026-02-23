package geography;

import java.io.BufferedReader;
import java.io.InputStream;

import gui.CartographyDocument;

public class GeographicShapesReader
{
  private BufferedReader in;
  private MapProjection proj;

  public GeographicShapesReader(InputStream is, MapProjection proj)
  {

    this.proj = proj;
  }

  public CartographyDocument<GeographicShape> read()
  {
    return null;

  }
}
