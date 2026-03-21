package feature;

public abstract class AbstractFeature implements Feature
{
  private String id;

  public AbstractFeature(String id)
  {
  }
  
  public String getID()
  {
    return id;
  }
}
