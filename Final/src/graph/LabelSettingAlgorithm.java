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
  private StreetSegment firstSegment;

  /**
   * Creates a label-setting algorithm.
   *
   * @param labels
   *          the permanent label manager
   */
  public LabelSettingAlgorithm(final PermanentLabelManager labels)
  {
    this.labels = labels;
    this.firstSegment = null;
  }

  /**
   * Creates a label-setting algorithm that avoids an immediate U-turn.
   *
   * @param labels
   *          the permanent label manager
   * @param firstSegment
   *          the segment the vehicle is already traveling on
   */
  public LabelSettingAlgorithm(final PermanentLabelManager labels, final StreetSegment firstSegment)
  {
    this.labels = labels;
    this.firstSegment = firstSegment;
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
    boolean firstStep = true;
    while (i != destination)
    {
      Intersection is = net.getIntersection(i);
      for (StreetSegment ss : is.getOutbound())
      {
        if (shouldSkipOnFirstStep(ss, is, firstStep))
          continue;

        labels.adjustHeadValue(ss);
      }
      firstStep = false;
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

  /**
   * Determines if this segment would immediately reverse the segment already being traveled.
   *
   * @param segment
   *          the candidate outbound segment
   * @param intersection
   *          the current intersection
   * @param firstStep
   *          true when processing the first route node
   * @return true if the segment should be skipped
   */
  private boolean shouldSkipOnFirstStep(final StreetSegment segment,
      final Intersection intersection, final boolean firstStep)
  {
    return firstStep && firstSegment != null && isImmediateReverse(segment)
        && hasNonReverseOutbound(intersection);
  }

  /**
   * Determines if the segment goes directly back to the previous node.
   *
   * @param segment
   *          the candidate segment
   * @return true if the candidate is an immediate reverse
   */
  private boolean isImmediateReverse(final StreetSegment segment)
  {
    return segment.getTail() == firstSegment.getHead()
        && segment.getHead() == firstSegment.getTail();
  }

  /**
   * Determines if the current intersection has at least one non-reverse exit.
   *
   * @param intersection
   *          the current intersection
   * @return true if there is another way to leave the intersection
   */
  private boolean hasNonReverseOutbound(final Intersection intersection)
  {
    for (StreetSegment segment : intersection.getOutbound())
    {
      if (!isImmediateReverse(segment))
        return true;
    }

    return false;
  }
}
