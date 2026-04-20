package gps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
  private ArrayList<GPSObserver> observers;

  /**
   * Construct a GPSReaderTask.
   *
   * @param is
   *          the InputStream to read from, typically obtained from a GPS device
   * @param sentences
   *          the NMEA sentence types to process (e.g. "$GPGGA", "$GPGSV")
   */
  public GPSReaderTask(InputStream is, String... sentences)
  {
    try
    {
      this.in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    }
    catch (UnsupportedEncodingException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    this.sentences = sentences;
    this.observers = new ArrayList<>();
  }

  /**
   * Continuously reads lines from the BufferedReader that decorates the InputStream and publishes
   * each line for processing. Runs until the task is cancelled.
   * 
   * @return
   */
  public Void doInBackground()
  {

    while (!isCancelled())
    {
      try
      {
        String line = in.readLine();
        if (line != null)
        {
          publish(line);
        }
        else
        {
          break;
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
        break;
      }
    }
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
    for (String line : lines)
    {
      for (String sentenceType : sentences)
      {
        if (line.startsWith(sentenceType))
        {
          notifyGPSObservers(line);
          break;
        }
      }
    }
  }

  @Override
  public void addGPSObserver(GPSObserver observer)
  {
    observers.add(observer);
  }

  @Override
  public void notifyGPSObservers(String sentence)
  {
    for (GPSObserver obs : observers)
    {
      obs.handleGPSData(sentence);
    }
  }

  @Override
  public void removeGPSObserver(GPSObserver observer)
  {
    observers.remove(observer);
  }

}
