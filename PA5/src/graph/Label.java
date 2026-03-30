package graph;

import feature.StreetSegment;

public class Label
{
  private boolean permanent;
  private double value;
  private int id;
  private StreetSegment predecessor;

  public Label()
  {

  }

  public Label(int id)
  {
    this.id = id;
  }

  public void adjustValue(double possibleValue, StreetSegment possibleStreetSegment)
  {
  }

  public int getID()
  {
    return this.id;
  }

  public StreetSegment getPredecessor()
  {
    return this.predecessor;
  }

  public double getValue()
  {
    return this.value;
  }

  public boolean isPermenant()
  {
    return this.permanent;
  }

  public void makePermenant()
  {
    this.permanent = true;
  }

  public void setValue(double value)
  {
    this.value = value;
  }
}
