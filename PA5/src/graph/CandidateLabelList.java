package graph;

import java.util.ArrayList;
import java.util.List;

import feature.StreetSegment;

/**
 * A list-based implementation of a candidate label manager. It keeps track of candidate labels and
 * selects them based on a policy.
 */
public class CandidateLabelList extends AbstractLabelManager implements CandidateLabelManager
{

  /** Policy that selects the newest candidate. */
  public static final String NEWEST = "N";

  /** Policy that selects the oldest candidate. */
  public static final String OLDEST = "O";

  /** List of candidate intersection IDs. */
  List<Integer> candidates;

  /** Selection policy (e.g., newest or oldest). */
  private String policy;

  /**
   * Creates a candidate label list.
   *
   * @param policy
   *          the selection policy
   * @param networkSize
   *          the number of nodes in the network
   */
  public CandidateLabelList(final String policy, final int networkSize)
  {
    super(networkSize);
    this.policy = policy;
    this.candidates = new ArrayList<Integer>();
  }

  /**
   * Returns the next candidate label based on the policy.
   *
   * @return the selected candidate label
   */
  @Override
  public Label getCandidateLabel()
  {
    if (candidates.isEmpty())
    {
      return null;
    }
    if (policy.equals(OLDEST))
    {
      return getLabel(candidates.remove(0));
    }
    else
    {
      return getLabel(candidates.remove(candidates.size() - 1));
    }
  }

  /**
   * Updates labels using the given street segment.
   *
   * @param segment
   *          the street segment to process
   */
  @Override
  public void adjustHeadValue(final StreetSegment segment)
  {
    //
    double newValue = getLabel(segment.getTail()).getValue() + segment.getLength();
    Label headLabel = getLabel(segment.getHead()); // get the segment's head
    double oldValue = headLabel.getValue();
    headLabel.adjustValue(newValue, segment);
    if (headLabel.getValue() < oldValue && !candidates.contains(segment.getHead()))
    {
      candidates.add(segment.getHead());
    }
  }

}
