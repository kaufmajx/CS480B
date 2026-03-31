package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import feature.Intersection;
import feature.Street;

/**
 * Represents a network of street intersections. Stores and manages a list of intersections.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1
 */
public class StreetNetwork
{
  private List<Intersection> intersections;

  /**
   * Constructs an empty StreetNetwork.
   */
  public StreetNetwork()
  {
    this.intersections = new ArrayList<>();
  }

  /**
   * Adds an intersection at the specified index.
   *
   * @param index
   *          the position where the intersection will be added
   * @param intersection
   *          the intersection to add
   */
  public void addIntersection(final int index, final Intersection intersection)
  {
    this.intersections.add(index, intersection);
  }

  /**
   * Retrieves the intersection at the given index.
   *
   * @param index
   *          the position of the intersection
   * @return the intersection at the specified index
   */
  public Intersection getIntersection(final int index)
  {
    return this.intersections.get(index);
  }

  /**
   * Returns the number of intersections in the network.
   *
   * @return the size of the intersection list
   */
  public int size()
  {
    return intersections.size();
  }

  /**
   * Creates a StreetNetwork based on a map of streets.
   *
   * @param streets
   *          a map of street names to Street objects
   * @return a new StreetNetwork instance
   */
  public StreetNetwork createStreetNetwork(final Map<String, Street> streets)
  {
    return null;
  }
}
