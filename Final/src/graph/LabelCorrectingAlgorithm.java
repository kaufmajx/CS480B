package graph;

import java.util.HashMap;
import java.util.Map;

import feature.StreetSegment;

/**
 * Label correcting algorithm.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1
 */
public class LabelCorrectingAlgorithm extends AbstractShortestPathAlgorithm
{
  private CandidateLabelManager labels;

  /**
   * Default correcting algorithm.
   * 
   * @param labels
   *          all the current candidate labels
   */
  public LabelCorrectingAlgorithm(final CandidateLabelManager labels)
  {
    this.labels = labels;
  }

  @Override
  public Map<String, StreetSegment> findPath(final int origin, final int destination,
      final StreetNetwork net)
  {
    // Initialize all labels to infinity;
    // This is done in the AbstractLabelManager constructor.

    // Initialize the label of the origin to 0;
    labels.getLabel(origin).setValue(0.0);

    // Make the origin the working node;
    int workingNode = origin;

    // Start the candidate list with the origin
    ((CandidateLabelList) labels).candidates.add(origin);

    // while (There are candidates nodes)
    while (true)
    {
      // Choose a candidate node and make it the new working node
      // getCandidateLabel() removes it from candidates and returns it
      Label next = labels.getCandidateLabel();
      if (next == null)
      {
        break;
      }
      workingNode = next.getID();
      //
      // if (workingNode == destination)
      // {
      // break;
      // }
      // For all segments reachable from the working node
      for (StreetSegment segment : net.getIntersection(workingNode).getOutbound())
      {
        // Calculate the distance to this node and update if better
        // Also adds head node to candidates if its value improved
        labels.adjustHeadValue(segment);
      }
      // Choose a candidate node and make it the new working node
      // getCandidateLabel() removes it from candidates and returns it
      // Label next = labels.getCandidateLabel();
      // if (next == null)
      // {
      // break;
      // }
      //
      // workingNode = next.getID();
    }

    // Reconstruct the path by following predecessors back from destination to origin
    Map<String, StreetSegment> path = new HashMap<String, StreetSegment>();
    Label current = labels.getLabel(destination);

    while (current.getPredecessor() != null)
    {
      StreetSegment seg = current.getPredecessor();

      path.put(seg.getID(), seg);
      // move to the label at the START of the segment (the tail, unfortunately)
      current = labels.getLabel(seg.getTail());
    }

    return path;
  }
}
