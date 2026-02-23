package geography;

import java.awt.Shape;

public abstract class AbstractGeographicShape implements GeographicShape
{

  private String id;

  public AbstractGeographicShape(String id)
  {
    this.id = id;
  }

  @Override
  public String getID()
  {
    return id;
  }

  @Override
  public abstract Shape getShape();

}
