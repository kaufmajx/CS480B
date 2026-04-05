package graph;

import java.util.Map;

import feature.StreetSegment;

public class LabelCorrectingAlgorithm extends AbstractShortestPathAlgorithm
{
  private CandidateLabelManager labels;

  public LabelCorrectingAlgorithm(final CandidateLabelManager labels)
  {
    this.labels = labels;
  }

  @Override
  public Map<String, StreetSegment> findPath(final int origin, final int destination,
      final StreetNetwork net)
  {
//    Initialize all labels to infinity;
//
//    Initialize the label of the origin to 0;
//
//    Make the origin the working node;
//
//    while (There are candidates nodes)
//    {
//        // Update the temporary labels
//        for (All nodes that are reachable from the working node)
//        {
//
//          Calculate the distance to this node through the working node;
//
//          if (This distance is less than the node's current label)
//          {
//
//            Set the node's label equal to this distance;
//
//            Make this node a candidate;
//
//            Set the node's predecessor equal to the working node;
//          }
//        }
//
//       // Make any candidate node the new working node
//       Choose a candidate node;
//
//       Set the working node equal to this node;
//    }
    // TODO Auto-generated method stub
    return null;
  }
}
