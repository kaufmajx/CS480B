package graph;

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
  private List<Integer> candidates;

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
      return getLabel(candidates.get(0));
    }
    else
    {
      return getLabel(candidates.get(candidates.size() - 1));
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
    Label headLabel = getLabel(candidates.get(0));
    // TODO make sure the value passed in is right
    headLabel.adjustValue(headLabel.getValue(), segment);
  }

}
