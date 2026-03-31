package graph;

/**
 * A manager for Candidate Label objects.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1
 */
public interface CandidateLabelManager extends LabelManager
{

  /**
   * Getter for Label.
   * 
   * @return the current label
   */
  public Label getCandidateLabel();
}
