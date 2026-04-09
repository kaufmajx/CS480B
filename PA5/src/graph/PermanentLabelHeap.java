package graph;

import java.util.Comparator;
import java.util.PriorityQueue;

import feature.StreetSegment;

/**
 * A heap-based implementation of a permanent label manager. Uses a d-ary heap structure to manage
 * labels efficiently.
 * 
 * @author Jelal Kaufman & Tenley Kennett
 * @version 1.0
 */


public class PermanentLabelHeap extends AbstractLabelManager implements PermanentLabelManager
{
  private int d;
  private PriorityQueue<Label> heap;

  /**
   * Creates a permanent label heap.
   *
   * @param d
   *          the number of children per node in the heap
   * @param networkSize
   *          the number of nodes in the network
   */
  public PermanentLabelHeap(final int d, final int networkSize)
  {
    super(networkSize);
    this.d = d;
    this.heap = new PriorityQueue<>(Comparator.comparingDouble(Label::getValue));

    // make a new label that is set to infinity
    for (int i = 0; i < networkSize; i++)
    {
      heap.add(labels[i]);
    }
  }

  /**
   * Returns the smallest label from the heap.
   *
   * @return the smallest label
   */
  @Override
  public Label getSmallestLabel()
  {
    while (!heap.isEmpty())
    {
      Label l = heap.peek();
      if (!l.isPermanent())
        return l;
      heap.poll(); // discard permanent labels lazily
    }
    return null;
  }

  /**
   * Marks the label at the given intersection as permanent.
   *
   * @param intersectionID
   *          the intersection ID
   */
  @Override
  public void makePermanent(final int intersectionID)
  {
    labels[intersectionID].makePermanent();
  }

  /**
   * Updates labels using the given street segment.
   *
   * @param segment
   *          the street segment to process
   */
  @Override
  public void adjustHeadValue(final StreetSegment segment)
  {
    int tailID = segment.getTail();
    int headID = segment.getHead();
    Label tailLabel = labels[tailID];
    Label headLabel = labels[headID];

    double possibleValue = tailLabel.getValue() + segment.getLength();

    // Label.adjustValue() only updates if possibleValue is an improvement
    if (!headLabel.isPermanent())
    {
      heap.remove(headLabel); // remove before updating (heap must re-sort)
      headLabel.adjustValue(possibleValue, segment);
      heap.add(headLabel); // re-add with updated value
    }
  }

}
