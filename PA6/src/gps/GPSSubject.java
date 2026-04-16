package gps;

//TBI
public interface GPSSubject
{
  /**
   * Add a GPSObserver to be notified of incoming NMEA sentences.
   *
   * @param observer
   *          the observer to add
   */
  public void addGPSObserver(GPSObserver observer);

  /**
   * Notify all registered GPSObserver objects of an incoming NMEA sentence.
   *
   * @param sentence
   *          the raw NMEA sentence string
   */
  public void notifyGPSObservers(String sentence);

  /**
   * Remove a GPSObserver so it no longer receives NMEA sentence notifications.
   *
   * @param observer
   *          the observer to remove
   */
  public void removeGPSObserver(GPSObserver observer);

}
