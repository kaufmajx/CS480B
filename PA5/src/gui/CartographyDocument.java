package gui;

import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a cartographic document containing mappable elements and their bounds.
 * 
 * @param <T>
 *          the type of geographic elements stored in this document
 */
public class CartographyDocument<T> implements Iterable<T>
{

  private Map<String, T> highlight;
  private Map<String, T> elements;
  private Rectangle2D.Double bounds;

  /**
   * Constructs a CartographyDocument with the given elements and bounds.
   * 
   * @param elements
   *          a map of element IDs to their corresponding objects
   * @param bounds
   *          the bounding rectangle covering all elements
   */
  public CartographyDocument(final Map<String, T> elements, final Rectangle2D.Double bounds)
  {
    this.elements = elements;
    this.bounds = bounds;
  }

  /**
   * Returns the bounding rectangle of all elements in this document.
   * 
   * @return the bounds as a Rectangle2D.Double
   */
  public Rectangle2D.Double getBounds()
  {
    return this.bounds;
  }

  /**
   * Returns the element associated with the given ID.
   * 
   * @param id
   *          the unique identifier of the element
   * @return the element, or null if not found
   */
  public T getElement(final String id)
  {
    return elements.get(id);
  }

  /**
   * Returns an iterator over the currently highlighted elements.
   * 
   * @return an iterator over highlighted elements, or an empty iterator if none are highlighted
   */
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

  /**
   * Sets the highlighted elements for this document.
   * 
   * @param highlighted
   *          a map of element IDs to their corresponding highlighted objects
   */
  public void setHighlighted(final Map<String, T> highlighted)
  {
    this.highlight = highlighted;
  }
}
