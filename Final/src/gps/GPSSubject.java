package gps;

/**
 * GPSSubject interface.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public interface GPSSubject
{
  /**
   * Add a GPSObserver to be notified of incoming NMEA sentences.
   *
   * @param observer
   *          the observer to add
   */
  public void addGPSObserver(final GPSObserver observer);

  /**
   * Notify all registered GPSObserver objects of an incoming NMEA sentence.
   *
   * @param sentence
   *          the raw NMEA sentence string
   */
  public void notifyGPSObservers(final String sentence);

  /**
   * Remove a GPSObserver so it no longer receives NMEA sentence notifications.
   *
   * @param observer
   *          the observer to remove
   */
  public void removeGPSObserver(final GPSObserver observer);

}
