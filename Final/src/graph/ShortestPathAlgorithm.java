package graph;

import java.util.List;
import java.util.Map;

import feature.StreetSegment;
import feature.StreetSegmentObserver;
import feature.StreetSegmentSubject;

/**
 * Defines a shortest path algorithm on a street network.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1
 */
public interface ShortestPathAlgorithm extends StreetSegmentSubject
{
  /**
   * Finds a path from an origin to a destination.
   *
   * @param origin
   *          the starting intersection
   * @param destination
   *          the ending intersection
   * @param net
   *          the street network
   * @return a map of segment IDs to street segments representing the path
   */
  public Map<String, StreetSegment> findPath(final int origin, final int destination, final StreetNetwork net);

  /**
   * Adds an observer to be notified of street segment updates.
   *
   * @param observer
   *          the observer to add
   */
  public void addStreetSegmentObserver(final StreetSegmentObserver observer);

  /**
   * Removes an observer.
   *
   * @param observer
   *          the observer to remove
   */
  public void removeStreetSegmentObserver(final StreetSegmentObserver observer);

  /**
   * Notifies observers about updated street segments.
   *
   * @param segmentIDs
   *          the IDs of updated street segments
   */
  public void notifyStreetSegmentObservers(final List<String> segmentIDs);
}
