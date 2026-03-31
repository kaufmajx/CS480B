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
    // TODO Auto-generated method stub
    return null;
  }
}
