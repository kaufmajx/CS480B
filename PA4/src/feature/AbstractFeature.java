package feature;

public abstract class AbstractFeature implements Feature
{
  private String id;

  public AbstractFeature(String id)
  {
    this.id = id;
  }
  
  public String getID()
  {
    return id;
  }
}
