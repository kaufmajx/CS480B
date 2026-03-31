package graph;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import feature.StreetSegment;
import feature.StreetSegmentObserver;

/**
 * Base class for shortest path algorithms. Handles common observer functionality.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public abstract class AbstractShortestPathAlgorithm implements ShortestPathAlgorithm
{

  /** List of observers watching street segment updates. */
  private Collection<StreetSegmentObserver> observers;

  /**
   * Creates a new shortest path algorithm.
   */
  public AbstractShortestPathAlgorithm()
  {

  }

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
  @Override
  public abstract Map<String, StreetSegment> findPath(final int origin, final int destination,
      StreetNetwork net);

  /**
   * Adds an observer.
   *
   * @param observer
   *          the observer to add
   */
  @Override
  public void addStreetSegmentObserver(final StreetSegmentObserver observer)
  {

  }

  /**
   * Removes an observer.
   *
   * @param observer
   *          the observer to remove
   */
  @Override
  public void removeStreetSegmentObserver(final StreetSegmentObserver observer)
  {

  }

  /**
   * Notifies all observers of updated street segments.
   *
   * @param segmentIDs
   *          the IDs of updated street segments
   */
  @Override
  public void notifyStreetSegmentObservers(final List<String> segmentIDs)
  {

  }
}
