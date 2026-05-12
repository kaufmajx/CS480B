package graph;

import java.util.HashMap;
import java.util.Map;

import feature.Intersection;
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
  public Map<String, StreetSegment> findPath(final int origin, final int destination,
      final StreetNetwork net)
  {
    // Set origin value and fix heap ordering
    labels.getLabel(origin).setValue(0.0);
    if (labels instanceof PermanentLabelHeap)
    {
      ((PermanentLabelHeap) labels).decreaseKey(origin); // sifts up after setValue
    }

    labels.makePermanent(origin);
    
    Map<String, StreetSegment> result = new HashMap<>();
    int i = origin;
    while (i != destination)
    {
      Intersection is = net.getIntersection(i);
      for (StreetSegment ss : is.getOutbound())
      {
        labels.adjustHeadValue(ss);
      }
      Label tinyLabel = labels.getSmallestLabel();
      if (tinyLabel == null)
        break;

      labels.makePermanent(tinyLabel.getID());

      i = tinyLabel.getID();

    }

    // Reconstruct path by walking predecessors back from destination
    int current = destination;
    while (current != origin)
    {
      Label currLabel = labels.getLabel(current);
      StreetSegment pred = currLabel.getPredecessor();
      if (pred == null)
        return null;
      result.put(pred.getID(), pred);
      current = pred.getTail();
    }
    return result;
  }
}
