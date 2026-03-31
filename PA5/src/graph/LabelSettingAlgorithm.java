package graph;

import java.util.Map;

import feature.StreetSegment;

/**
 * A shortest path algorithm using the label-setting approach.
 * 
 * @author Jelal Kaufman
 * @version 1.0
 */
public class LabelSettingAlgorithm extends AbstractShortestPathAlgorithm
{

  /** Manages permanent labels. */
  private PermanentLabelManager labels;

  /**
   * Creates a label-setting algorithm.
   *
   * @param labels
   *          the permanent label manager
   */
  public LabelSettingAlgorithm(final PermanentLabelManager labels)
  {
    this.labels = labels;
  }

  /**
   * Finds the shortest path from an origin to a destination.
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
  public Map<String, StreetSegment> findPath(final int origin, final int destination, final StreetNetwork net)
  {
    // TODO Auto-generated method stub
    return null;
  }
}
