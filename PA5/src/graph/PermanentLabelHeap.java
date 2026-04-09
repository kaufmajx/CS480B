package graph;

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
  private Label[] heap;
  private int[] indexInHeap; // maps intersectionID -> position in heap array
  private int size;

  public PermanentLabelHeap(final int d, final int networkSize)
  {
    super(networkSize);
    this.d = d;
    this.heap = new Label[networkSize];
    this.indexInHeap = new int[networkSize];
    this.size = 0;

    for (int i = 0; i < networkSize; i++)
    {
      heap[i] = labels[i];
      indexInHeap[labels[i].getID()] = i;
      size++;
    }

    // Build heap bottom-up (heapify)
    for (int i = parentOf(size - 1); i >= 0; i--)
    {
      siftDown(i);
    }
  }

  // --- Index math ---

  private int parentOf(int i)
  {
    return (i - 1) / d;
  }

  private int firstChildOf(int i)
  {
    return d * i + 1;
  }

  // --- Core heap operations ---

  private void swap(int i, int j)
  {
    indexInHeap[heap[i].getID()] = j;
    indexInHeap[heap[j].getID()] = i;
    Label temp = heap[i];
    heap[i] = heap[j];
    heap[j] = temp;
  }

  private void siftUp(int i)
  {
    while (i > 0)
    {
      int parent = parentOf(i);
      if (heap[parent].getValue() > heap[i].getValue())
      {
        swap(i, parent);
        i = parent;
      }
      else break;
    }
  }

  void decreaseKey(final int intersectionID)
  {
    siftUp(indexInHeap[intersectionID]);
  }

  
  private void siftDown(int i)
  {
    while (true)
    {
      int smallest = i;
      int firstChild = firstChildOf(i);

      for (int c = firstChild; c < Math.min(firstChild + d, size); c++)
      {
        if (heap[c].getValue() < heap[smallest].getValue())
          smallest = c;
      }

      if (smallest != i)
      {
        swap(i, smallest);
        i = smallest;
      }
      else break;
    }
  }
  
  public void setOrigin(final int intersectionID)
  {
    Label originLabel = labels[intersectionID];
    originLabel.setValue(0.0);
    siftUp(indexInHeap[intersectionID]); // fixes heap ordering
  }

  // --- Interface methods ---

  @Override
  public Label getSmallestLabel()
  {
    // Lazy removal: skip permanent labels at the top
    while (size > 0)
    {
      if (!heap[0].isPermanent())
        return heap[0];
      // Pop the permanent label off the heap
      swap(0, size - 1);
      size--;
      if (size > 0) siftDown(0);
    }
    return null;
  }

  @Override
  public void makePermanent(final int intersectionID)
  {
    labels[intersectionID].makePermanent();
  }

  @Override
  public void adjustHeadValue(final StreetSegment segment)
  {
    int tailID = segment.getTail();
    int headID = segment.getHead();
    Label tailLabel = labels[tailID];
    Label headLabel = labels[headID];

    double possibleValue = tailLabel.getValue() + segment.getLength();

    if (!headLabel.isPermanent() && possibleValue < headLabel.getValue())
    {
      headLabel.adjustValue(possibleValue, segment);
      // Sift up from this label's current position (value only decreased)
      siftUp(indexInHeap[headID]);
    }
  }
}