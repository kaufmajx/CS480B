package gui;

import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class CartographyDocument<T> implements Iterable<T>
{

  private Map<String, T> highlight;
  private Map<String, T> elements;
  private Rectangle2D.Double bounds;

  public CartographyDocument(Map<String, T> elements, Rectangle2D.Double bounds)
  {
    this.elements = elements;
    this.bounds = bounds;
  }

  public Rectangle2D.Double getBounds()
  {
    return this.bounds;
  }

  public T getElement(String id)
  {
    return elements.get(id);
  }

  public Iterator<T> highlighted()
  {
    if (highlight == null)
    {
      return Collections.emptyIterator();
    }
    return highlight.values().iterator();

  }

  @Override
  public Iterator<T> iterator()
  {
    return elements.values().iterator();
  }

  public void setHighlighted(Map<String, T> highlighted)
  {
    this.highlight = highlighted;
  }
}
