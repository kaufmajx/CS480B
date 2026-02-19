package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Line2D;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.DigitizerPanel;
import gui.DisplayDigitizerDocument;

class DisplayDigitizerDocumentTest
{

  private DisplayDigitizerDocument doc;

  @BeforeEach
  void setUp()
  {
    // DigitizerPanel requires a BufferedImage; use a minimal 1x1 image
    java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(1, 1,
        java.awt.image.BufferedImage.TYPE_INT_RGB);
    DigitizerPanel panel = new DigitizerPanel(img);
    doc = new DisplayDigitizerDocument(panel);
  }

  // --- addLine / getLines ---

  @Test
  void testGetLines_initiallyEmpty()
  {
    assertTrue(doc.getLines().isEmpty());
  }

  @Test
  void testAddLine_singleLine()
  {
    doc.addLine(new double[] {0, 0}, new double[] {1, 1});
    List<Line2D.Double> lines = doc.getLines();
    assertEquals(1, lines.size());
    Line2D.Double l = lines.get(0);
    assertEquals(0.0, l.getX1());
    assertEquals(0.0, l.getY1());
    assertEquals(1.0, l.getX2());
    assertEquals(1.0, l.getY2());
  }

  @Test
  void testAddLine_multipleLines()
  {
    doc.addLine(new double[] {0, 0}, new double[] {1, 1});
    doc.addLine(new double[] {2, 2}, new double[] {3, 3});
    assertEquals(2, doc.getLines().size());
  }

  // --- removeLine ---

  @Test
  void testRemoveLine_removesCorrectLine()
  {
    doc.addLine(new double[] {0, 0}, new double[] {1, 1});
    Line2D.Double line = doc.getLines().get(0);
    doc.removeLine(line);
    assertTrue(doc.getLines().isEmpty());
  }

  @Test
  void testRemoveLine_lineNotPresent_doesNothing()
  {
    doc.addLine(new double[] {0, 0}, new double[] {1, 1});
    Line2D.Double phantom = new Line2D.Double(9, 9, 10, 10);
    doc.removeLine(phantom); // should not throw
    assertEquals(1, doc.getLines().size());
  }

  @Test
  void testRemoveLine_null_doesNothing()
  {
    doc.addLine(new double[] {0, 0}, new double[] {1, 1});
    doc.removeLine(null); // should not throw
    assertEquals(1, doc.getLines().size());
  }

  @Test
  void testRemoveLine_onlyRemovesOneWhenDuplicates()
  {
    doc.addLine(new double[] {0, 0}, new double[] {1, 1});
    doc.addLine(new double[] {0, 0}, new double[] {1, 1});
    Line2D.Double line = doc.getLines().get(0);
    doc.removeLine(line);
    assertEquals(1, doc.getLines().size());
  }

  // --- getClosest ---

  @Test
  void testGetClosest_noLines_returnsNull()
  {
    assertNull(doc.getClosest(new double[] {0, 0}));
  }

  @Test
  void testGetClosest_singleLine_returnsThatLine()
  {
    doc.addLine(new double[] {0, 0}, new double[] {1, 1});
    Line2D.Double result = doc.getClosest(new double[] {0, 0});
    assertNotNull(result);
    assertEquals(0.0, result.getX1());
  }

  @Test
  void testGetClosest_returnsNearestLine()
  {
    // Line A is far away
    doc.addLine(new double[] {100, 100}, new double[] {200, 200});
    // Line B is close to origin
    doc.addLine(new double[] {1, 1}, new double[] {2, 2});

    Line2D.Double closest = doc.getClosest(new double[] {0, 0});
    assertEquals(1.0, closest.getX1(), "Should return Line B (the closer one)");
  }

  @Test
  void testGetClosest_pointEquidistant_returnsFirst()
  {
    // Both lines equidistant from point {5, 0}
    doc.addLine(new double[] {0, 0}, new double[] {10, 0});
    doc.addLine(new double[] {0, 0}, new double[] {10, 0});
    Line2D.Double result = doc.getClosest(new double[] {5, 0});
    assertNotNull(result);
  }
}
