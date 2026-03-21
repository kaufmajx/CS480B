package feature;

import java.util.ArrayList;
import java.util.List;

public class Intersection
{
  public List<StreetSegment> inbound;
  public List<StreetSegment> outbound;

  public Intersection()
  {
    inbound = new ArrayList<StreetSegment>();
    outbound = new ArrayList<StreetSegment>();
  }

  public void addInbound(StreetSegment segment)
  {
    inbound.add(segment);
  }

  public void addOutbound(StreetSegment segment)
  {
    outbound.add(segment);
  }

  public List<StreetSegment> getInbound()
  {
    return inbound;
  }

  public List<StreetSegment> getOutbound()
  {
    return outbound;
  }
}
