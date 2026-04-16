package gps;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.List;

import javax.swing.SwingWorker;

/**
 * Background task that continuously reads NMEA sentences from a GPS device and notifies registered
 * GPSObserver objects of relevant sentences.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */
public class GPSReaderTask extends SwingWorker<Void, String> implements GPSSubject
{
  private BufferedReader in;
  private String[] sentences;

  /**
   * Construct a GPSReaderTask.
   *
   * @param is
   *          the InputStream to read from, typically obtained from a GPS device
   * @param sentences
   *          the NMEA sentence types to process (e.g. "$GPGGA", "$GPGSV")
   */
  public GPSReaderTask(InputStream is, String[] sentences)
  {

  }

  /**
   * Continuously reads lines from the BufferedReader that decorates the InputStream and publishes
   * each line for processing. Runs until the task is cancelled.
   * 
   * @return
   */
  public Void doInBackground()
  {
    return null;
  }

  /**
   * Notifies all registered GPSObserver objects of each line that matches one of the sentence types
   * specified at construction. Multiple lines may be coalesced into a single chunk for performance
   * reasons, which is why a List is accepted rather than an individual String.
   *
   * @param lines
   *          the list of NMEA sentence strings to process
   */
  @Override
  public void process(List<String> lines)
  {

  }

  @Override
  public void addGPSObserver(GPSObserver observer)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void notifyGPSObservers(String sentence)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeGPSObserver(GPSObserver observer)
  {
    // TODO Auto-generated method stub

  }

}
