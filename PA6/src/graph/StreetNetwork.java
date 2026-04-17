package graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import feature.Intersection;
import feature.Street;
import feature.StreetSegment;

/**
 * Represents a network of street intersections. Stores and manages a list of intersections.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1
 */
public class StreetNetwork
{
  private Map<Integer, Intersection> intersections = new HashMap<>();

  /**
   * Constructs an empty StreetNetwork.
   */
  public StreetNetwork()
  {
    this.intersections = new HashMap<>();
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
    intersections.put(index, intersection);

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
  public static StreetNetwork createStreetNetwork(final Map<String, Street> streets)
  {
    StreetNetwork streetNetwork = new StreetNetwork();
    Map<Integer, Intersection> nodeMap = new HashMap<>();

    // loop thru streets to build out intersections
    for (Street street : streets.values())
    {
      Iterator<StreetSegment> it = street.getSegments();

      while (it.hasNext())
      {
        StreetSegment ss = it.next();
        if (!nodeMap.containsKey(ss.getTail()))
          nodeMap.put(ss.getTail(), new Intersection());

        if (!nodeMap.containsKey(ss.getHead()))
          nodeMap.put(ss.getHead(), new Intersection());
      }

    }
    // add each intersection to the streetnetwork
    for (Map.Entry<Integer, Intersection> entry : nodeMap.entrySet())
    {
      streetNetwork.addIntersection(entry.getKey(), entry.getValue());
    }
    // link up incoming and outgoing streetsegments
    for (Street street : streets.values())
    {
      Iterator<StreetSegment> it = street.getSegments();

      while (it.hasNext())
      {
        StreetSegment ss = it.next();
        Intersection tail = nodeMap.get(ss.getTail());
        Intersection head = nodeMap.get(ss.getHead());
        tail.addOutbound(ss);
        head.addInbound(ss);
      }

    }
    return streetNetwork;
  }
}
