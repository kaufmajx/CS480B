package graph;

import feature.StreetSegment;

/**
 * Label objects are used in label setting and label correcting algorithms.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1
 */
public class Label
{
  private boolean permanent;
  private double value;
  private int id;
  private StreetSegment predecessor;

  /**
   * Default Label constructor.
   */
  public Label()
  {

  }

  /**
   * Label constructor with id as a parameter.
   * 
   * @param id
   *          the id of the label
   */
  public Label(final int id)
  {
    this.id = id;
  }

  /**
   * Method that adjusts the value and predecessor attributes of the label when the new value is
   * less than the current one.
   * 
   * @param possibleValue
   * @param possibleStreetSegment
   */
  public void adjustValue(final double possibleValue, final StreetSegment possibleStreetSegment)
  {
    if (this.value > possibleValue)
    {
      this.value = possibleValue;
      this.predecessor = possibleStreetSegment;
    }
  }

  /**
   * Getter for ID.
   * 
   * @return the id
   */
  public int getID()
  {
    return this.id;
  }

  /**
   * Getter for predecessor.
   * 
   * @return predecessor
   */
  public StreetSegment getPredecessor()
  {
    return this.predecessor;
  }

  /**
   * Getter for value.
   * 
   * @return value
   */
  public double getValue()
  {
    return this.value;
  }

  /**
   * Getter for permanent.
   * 
   * @return permanent
   */
  public boolean isPermenant()
  {
    return this.permanent;
  }

  /**
   * Setter for permanent.
   */
  public void makePermenant()
  {
    this.permanent = true;
  }

  /**
   * Setter for value.
   */
  public void setValue(final double value)
  {
    this.value = value;
  }
}
