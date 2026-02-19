package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.DigitizerDocument;
import gui.DigitizerPanel;

class DigitizerPanelTest
{

  private DigitizerPanel panel;

  @BeforeAll
  static void setHeadless()
  {
    // Must be set before any AWT/Swing class is loaded
    System.setProperty("java.awt.headless", "true");
  }

  @BeforeEach
  void setUp()
  {
    BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    panel = new DigitizerPanel(img);
  }

  // --- Constants ---

  @Test
  void testConstants()
  {
    assertEquals(0, DigitizerPanel.ADD);
    assertEquals(1, DigitizerPanel.DELETE);
  }

  // --- createDefaultMode ---

  @Test
  void testCreateDefaultMode_returnsNonNull()
  {
    assertNotNull(panel.createDefaultModel());
  }

  @Test
  void testCreateDefaultMode_returnsDigitizerDocument()
  {
    assertTrue(panel.createDefaultModel() instanceof DigitizerDocument);
  }

  // --- setMode / getLines ---

  @Test
  void testGetLines_initiallyEmpty()
  {
    assertTrue(panel.getLines().isEmpty());
  }

  @Test
  void testGetLines_reflectsModelState()
  {
    panel.setMode(DigitizerPanel.DELETE);
    panel.mouseReleased(makeMouseEvent(panel, 10, 10));
    panel.setMode(DigitizerPanel.ADD);
    panel.mousePressed(makeMouseEvent(panel, 0, 0));
    panel.mouseReleased(makeMouseEvent(panel, 10, 10));
    assertEquals(1, panel.getLines().size());
  }

  @Test
  void testSetMode_addMode()
  {
    panel.setMode(DigitizerPanel.ADD);
    // No exception; mode is internal but we verify indirectly via mouse events below
  }

  @Test
  void testSetMode_deleteMode()
  {
    panel.setMode(DigitizerPanel.DELETE);
    // No exception thrown
  }

  // --- Mouse events in ADD mode ---

  @Test
  void testMousePressed_addMode_doesNotThrow()
  {
    panel.setMode(DigitizerPanel.ADD);
    java.awt.event.MouseEvent press = makeMouseEvent(panel, 10, 20);
    panel.mousePressed(press);
  }

  @Test
  void testMouseDragged_addMode_doesNotThrow()
  {
    panel.setMode(DigitizerPanel.ADD);
    java.awt.event.MouseEvent press = makeMouseEvent(panel, 10, 20);
    panel.mousePressed(press);
    java.awt.event.MouseEvent drag = makeMouseEvent(panel, 30, 40);
    panel.mouseDragged(drag);
  }

  @Test
  void testMouseReleased_addMode_addsLine()
  {
    panel.setMode(DigitizerPanel.ADD);
    panel.mousePressed(makeMouseEvent(panel, 0, 0));
    panel.mouseReleased(makeMouseEvent(panel, 10, 10));
    // mouseReleased adds the drawn line AND mouseClicked adds the hardcoded (3,3)->(8,5) line
    assertFalse(panel.getLines().isEmpty());
  }

  // --- Mouse events in DELETE mode ---

  @Test
  void testMouseClicked_deleteMode_noLines_doesNotThrow()
  {
    panel.setMode(DigitizerPanel.ADD);
    panel.mouseClicked(makeMouseEvent(panel, 5, 5));
    panel.setMode(DigitizerPanel.DELETE);
    // getClosest returns null; removeLine(null) should not throw
    panel.mouseClicked(makeMouseEvent(panel, 5, 5));
  }

  @Test
  void testMouseClicked_deleteMode_removesClosestLine()
  {
    panel.setMode(DigitizerPanel.ADD);
    panel.mousePressed(makeMouseEvent(panel, 0, 0));
    panel.mouseReleased(makeMouseEvent(panel, 1, 1));
    assertEquals(1, panel.getLines().size());

    panel.setMode(DigitizerPanel.DELETE);
    panel.mouseClicked(makeMouseEvent(panel, 0, 0));
    assertTrue(panel.getLines().isEmpty());
  }

  @Test
  void testMouseDragged_deleteMode_doesNotThrow()
  {
    panel.setMode(DigitizerPanel.DELETE);
    panel.mouseDragged(makeMouseEvent(panel, 5, 5));
  }

  // --- Unused listener stubs ---

  @Test
  void testMouseEntered_doesNotThrow()
  {
    panel.mouseEntered(makeMouseEvent(panel, 0, 0));
  }

  @Test
  void testMouseExited_doesNotThrow()
  {
    panel.mouseExited(makeMouseEvent(panel, 0, 0));
  }

  @Test
  void testMouseMoved_doesNotThrow()
  {
    panel.mouseMoved(makeMouseEvent(panel, 0, 0));
  }

  @Test
  void testPaint_doesNotThrow()
  {
    panel.setMode(DigitizerPanel.DELETE);
    panel.mousePressed(makeMouseEvent(panel, 0, 0));

    panel.setMode(DigitizerPanel.ADD);
    panel.mousePressed(makeMouseEvent(panel, 0, 0));
    panel.mouseReleased(makeMouseEvent(panel, 10, 10));

    BufferedImage canvas = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = canvas.createGraphics();
    panel.setSize(200, 200); // panel must have nonzero size for super.paint()
    panel.paint(g2); // exercises green-line branch
    g2.dispose();
  }

  @Test
  void testPaint_whileDrawing_doesNotThrow()
  {
    panel.setMode(DigitizerPanel.ADD);
    panel.mousePressed(makeMouseEvent(panel, 0, 0)); // sets drawing = true
    panel.mouseDragged(makeMouseEvent(panel, 10, 10)); // updates currLine

    BufferedImage canvas = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = canvas.createGraphics();
    panel.setSize(200, 200);
    panel.paint(g2); // exercises yellow-line (drawing) branch
    g2.dispose();
  }

  // --- Helper ---

  private java.awt.event.MouseEvent makeMouseEvent(DigitizerPanel source, int x, int y)
  {
    return new java.awt.event.MouseEvent(source, java.awt.event.MouseEvent.MOUSE_CLICKED,
        System.currentTimeMillis(), 0, x, y, 1, false);
  }
}
