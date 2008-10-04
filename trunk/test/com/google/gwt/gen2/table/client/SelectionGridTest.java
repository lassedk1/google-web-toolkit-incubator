/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.gen2.table.event.client.CellHighlightEvent;
import com.google.gwt.gen2.table.event.client.CellHighlightHandler;
import com.google.gwt.gen2.table.event.client.CellUnhighlightEvent;
import com.google.gwt.gen2.table.event.client.CellUnhighlightHandler;
import com.google.gwt.gen2.table.event.client.RowHighlightEvent;
import com.google.gwt.gen2.table.event.client.RowHighlightHandler;
import com.google.gwt.gen2.table.event.client.RowSelectionEvent;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.gen2.table.event.client.RowUnhighlightEvent;
import com.google.gwt.gen2.table.event.client.RowUnhighlightHandler;
import com.google.gwt.gen2.table.event.client.TableEvent.Row;
import com.google.gwt.gen2.table.override.client.HTMLTable.CellFormatter;
import com.google.gwt.gen2.table.override.client.HTMLTable.RowFormatter;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

import java.util.Set;

/**
 * Tests methods used for all {@link SelectionGrid} class.
 */
public class SelectionGridTest extends Gen2TestBase {
  /**
   * A custom handler used for testing.
   */
  protected static class TestCellHighlightHandler implements
      CellHighlightHandler {
    private CellHighlightEvent lastEvent = null;

    public void assertCellIndex(int cellIndex) {
      assertNotNull(lastEvent);
      assertEquals(cellIndex, lastEvent.getValue().getCellIndex());
    }

    public void assertNotFired() {
      assertNull(lastEvent);
    }

    public void assertRowIndex(int rowIndex) {
      assertNotNull(lastEvent);
      assertEquals(rowIndex, lastEvent.getValue().getRowIndex());
    }

    public void onCellHighlight(CellHighlightEvent event) {
      lastEvent = event;
    }

    public void reset() {
      lastEvent = null;
    }
  }

  /**
   * A custom handler used for testing.
   */
  protected static class TestCellUnhighlightHandler implements
      CellUnhighlightHandler {
    private CellUnhighlightEvent lastEvent = null;

    public void assertCellIndex(int cellIndex) {
      assertNotNull(lastEvent);
      assertEquals(cellIndex, lastEvent.getValue().getCellIndex());
    }

    public void assertNotFired() {
      assertNull(lastEvent);
    }

    public void assertRowIndex(int rowIndex) {
      assertNotNull(lastEvent);
      assertEquals(rowIndex, lastEvent.getValue().getRowIndex());
    }

    public void onCellUnhighlight(CellUnhighlightEvent event) {
      lastEvent = event;
    }

    public void reset() {
      lastEvent = null;
    }
  }

  /**
   * A custom handler used for testing.
   */
  protected static class TestRowHighlightHandler implements RowHighlightHandler {
    private RowHighlightEvent lastEvent = null;

    public void assertNotFired() {
      assertNull(lastEvent);
    }

    public void assertRowIndex(int rowIndex) {
      assertNotNull(lastEvent);
      assertEquals(rowIndex, lastEvent.getValue().getRowIndex());
    }

    public void onRowHighlight(RowHighlightEvent event) {
      lastEvent = event;
    }

    public void reset() {
      lastEvent = null;
    }
  }

  /**
   * A custom handler used for testing.
   */
  protected static class TestRowUnhighlightHandler implements
      RowUnhighlightHandler {
    private RowUnhighlightEvent lastEvent = null;

    public void assertNotFired() {
      assertNull(lastEvent);
    }

    public void assertRowIndex(int rowIndex) {
      assertNotNull(lastEvent);
      assertEquals(rowIndex, lastEvent.getValue().getRowIndex());
    }

    public void onRowUnhighlight(RowUnhighlightEvent event) {
      lastEvent = event;
    }

    public void reset() {
      lastEvent = null;
    }
  }

  /**
   * A custom handler used for testing.
   */
  protected static class TestRowSelectionHandler implements RowSelectionHandler {
    private RowSelectionEvent lastEvent = null;

    public void assertDeselectedRows(int[] expected) {
      assertSets(expected, lastEvent.getDeselectedRows());
    }

    public void assertNewRows(int[] expected) {
      assertSets(expected, lastEvent.getNewValue());
    }

    public void assertOldRows(int[] expected) {
      assertSets(expected, lastEvent.getOldValue());
    }

    public void assertSelectedRows(int[] expected) {
      assertSets(expected, lastEvent.getSelectedRows());
    }

    public RowSelectionEvent getEvent() {
      return lastEvent;
    }

    public void onRowSelection(RowSelectionEvent event) {
      lastEvent = event;
    }

    private void assertSets(int[] expected, Set<Row> actual) {
      assertEquals(expected.length, actual.size());
      int i = 0;
      for (Row row : actual) {
        assertEquals(expected[i], row.getRowIndex());
        i++;
      }
    }
  }

  /**
   * TODO (jlabanca): Check for bug in TreeSet, order is not correct.
   */
  public void disabledTestSelectionHandlers() {
    // Initialize the grid
    SelectionGrid testGrid = getSelectionGrid();

    // Add some handlers
    TestRowSelectionHandler rsh = new TestRowSelectionHandler();
    testGrid.addRowSelectionHandler(rsh);

    // Select a row
    testGrid.selectRow(4, true);
    rsh.assertDeselectedRows(new int[] {});
    rsh.assertSelectedRows(new int[] {4});
    rsh.assertOldRows(new int[] {});
    rsh.assertNewRows(new int[] {4});

    // Select another row
    testGrid.selectRow(2, false);
    rsh.assertDeselectedRows(new int[] {});
    rsh.assertSelectedRows(new int[] {2});
    rsh.assertOldRows(new int[] {4});
    rsh.assertNewRows(new int[] {2, 4});

    // Deselect all rows
    testGrid.deselectAllRows();
    rsh.assertDeselectedRows(new int[] {2, 4});
    rsh.assertSelectedRows(new int[] {});
    rsh.assertOldRows(new int[] {2, 4});
    rsh.assertNewRows(new int[] {});
  }

  /**
   * Get the selection grid.
   * 
   * @return the grid
   */
  public SelectionGrid getSelectionGrid() {
    return new SelectionGrid(10, 10);
  }

  /**
   * Test accessors.
   */
  public void testAccessors() {
    // Initialize the grid
    SelectionGrid testGrid = getSelectionGrid();

    // Selection policy
    testGrid.setSelectionPolicy(SelectionGrid.SelectionPolicy.CHECKBOX);
    assertEquals(SelectionGrid.SelectionPolicy.CHECKBOX,
        testGrid.getSelectionPolicy());
    testGrid.setSelectionPolicy(SelectionGrid.SelectionPolicy.ONE_ROW);
    assertEquals(SelectionGrid.SelectionPolicy.ONE_ROW,
        testGrid.getSelectionPolicy());

    // Selection enabled
    testGrid.setSelectionEnabled(false);
    assertFalse(testGrid.isSelectionEnabled());
    testGrid.setSelectionEnabled(true);
    assertTrue(testGrid.isSelectionEnabled());
  }

  /**
   * Test selection options.
   */
  public void testBasicSelection() {
    // Initialize the grid
    SelectionGrid testGrid = getSelectionGrid();

    // Selection with deselect
    testGrid.deselectAllRows();
    testGrid.selectRow(3, true);
    assertTrue(testGrid.isRowSelected(3));
    assertSelected(testGrid, new int[] {3});
    testGrid.selectRow(5, true);
    assertFalse(testGrid.isRowSelected(3));
    assertTrue(testGrid.isRowSelected(5));
    assertSelected(testGrid, new int[] {5});
    testGrid.selectRow(7, true);
    assertSelected(testGrid, new int[] {7});

    // Selection without deselect
    testGrid.deselectAllRows();
    testGrid.selectRow(3, false);
    assertSelected(testGrid, new int[] {3});
    testGrid.selectRow(5, false);
    assertSelected(testGrid, new int[] {3, 5});
    testGrid.selectRow(7, false);
    assertSelected(testGrid, new int[] {3, 5, 7});

    // Deselection
    testGrid.deselectAllRows();
    testGrid.selectRow(3, false);
    assertTrue(testGrid.isRowSelected(3));
    testGrid.deselectRow(3);
    assertFalse(testGrid.isRowSelected(3));

    // Select all rows
    testGrid.selectAllRows();
    assertEquals(testGrid.getRowCount(), testGrid.getSelectedRows().size());

    // Deselection of all rows
    testGrid.deselectAllRows();
    assertEquals(0, testGrid.getSelectedRows().size());
    testGrid.selectRow(3, false);
    testGrid.selectRow(4, false);
    testGrid.selectRow(5, false);
    assertEquals(3, testGrid.getSelectedRows().size());
    testGrid.deselectAllRows();
    assertEquals(0, testGrid.getSelectedRows().size());

    // Selection Out of bounds
    try {
      testGrid.selectRow(-1, true);
      fail("Expected IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      testGrid.selectRow(10, true);
      fail("Expected IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
  }

  /**
   * Test that selection with a checkbox column works correctly.
   */
  public void testCheckboxSelection() {
    // Initialize the grid
    SelectionGrid testGrid = getSelectionGrid();
    testGrid.resize(3, 4);
    RowFormatter rowFormatter = testGrid.getRowFormatter();
    CellFormatter cellFormatter = testGrid.getCellFormatter();

    // Verify the column count before setting the checkbox policy
    assertEquals(4, testGrid.getColumnCount());
    for (int i = 0; i < 3; i++) {
      Element tr = rowFormatter.getElement(i);
      assertEquals(4, tr.getChildNodes().getLength());
    }

    // Verify the column count after setting the checkbox policy
    testGrid.setSelectionPolicy(SelectionPolicy.CHECKBOX);
    assertEquals(4, testGrid.getColumnCount());
    for (int i = 0; i < 3; i++) {
      Element tr = rowFormatter.getElement(i);
      assertEquals(5, tr.getChildNodes().getLength());
    }

    // Set html and verify
    testGrid.setText(0, 0, "test");
    assertEquals("test", testGrid.getText(0, 0));
    assertEquals("test", cellFormatter.getElement(0, 0).getInnerText());
    assertEquals("test",
        DOM.getChild(rowFormatter.getElement(0), 1).getInnerText());
  }

  /**
   * Test that highlighting a cell correctly sets the style.
   */
  public void testHighlight() {
    // Initialize the grid
    SelectionGrid testGrid = getSelectionGrid();
    RowFormatter rowFormatter = testGrid.getRowFormatter();
    CellFormatter cellFormatter = testGrid.getCellFormatter();

    // Highlight a cell
    assertEquals(rowFormatter.getStyleName(1), "");
    assertEquals(cellFormatter.getStyleName(1, 1), "");
    testGrid.highlightCell(cellFormatter.getElement(1, 1));
    assertEquals(rowFormatter.getStyleName(1), "highlighted");
    assertEquals(cellFormatter.getStyleName(1, 1), "highlighted");

    // Highlight a cell in the same row
    testGrid.highlightCell(cellFormatter.getElement(1, 3));
    assertEquals(rowFormatter.getStyleName(1), "highlighted");
    assertEquals(cellFormatter.getStyleName(1, 1), "");
    assertEquals(cellFormatter.getStyleName(1, 3), "highlighted");

    // Highlight a cell in a different row
    testGrid.highlightCell(cellFormatter.getElement(2, 4));
    assertEquals(rowFormatter.getStyleName(1), "");
    assertEquals(cellFormatter.getStyleName(1, 3), "");
    assertEquals(rowFormatter.getStyleName(2), "highlighted");
    assertEquals(cellFormatter.getStyleName(2, 4), "highlighted");

    // Unhighlight the cell
    testGrid.highlightCell(null);
    assertEquals(rowFormatter.getStyleName(2), "");
    assertEquals(cellFormatter.getStyleName(2, 4), "");
  }

  public void testHighlightHandlers() {
    // Initialize the grid
    SelectionGrid testGrid = getSelectionGrid();
    CellFormatter cellFormatter = testGrid.getCellFormatter();

    // Add some handlers
    TestRowHighlightHandler rhh = new TestRowHighlightHandler();
    testGrid.addRowHighlightHandler(rhh);
    TestRowUnhighlightHandler ruh = new TestRowUnhighlightHandler();
    testGrid.addRowUnhighlightHandler(ruh);
    TestCellHighlightHandler chh = new TestCellHighlightHandler();
    testGrid.addCellHighlightHandler(chh);
    TestCellUnhighlightHandler cuh = new TestCellUnhighlightHandler();
    testGrid.addCellUnhighlightHandler(cuh);

    // Highlight a cell
    testGrid.highlightCell(cellFormatter.getElement(4, 2));
    cuh.assertNotFired();
    ruh.assertNotFired();
    rhh.assertRowIndex(4);
    chh.assertRowIndex(4);
    chh.assertCellIndex(2);

    // Highlight a cell in the same row
    rhh.reset();
    chh.reset();
    testGrid.highlightCell(cellFormatter.getElement(4, 3));
    cuh.assertRowIndex(4);
    cuh.assertCellIndex(2);
    ruh.assertNotFired();
    rhh.assertNotFired();
    chh.assertRowIndex(4);
    chh.assertCellIndex(3);

    // Highlight a cell in the different row row
    rhh.reset();
    chh.reset();
    testGrid.highlightCell(cellFormatter.getElement(2, 3));
    cuh.assertRowIndex(4);
    cuh.assertCellIndex(3);
    ruh.assertRowIndex(4);
    rhh.assertRowIndex(2);
    chh.assertRowIndex(2);
    chh.assertCellIndex(3);
  }

  /**
   * Test selection options using the ctrl/shift method.
   */
  public void testShiftSelection() {
    // Initialize the grid
    SelectionGrid testGrid = getSelectionGrid();

    // Select rows using ctrl
    testGrid.deselectAllRows();
    testGrid.selectRow(4, false, false);
    testGrid.selectRow(8, true, false);
    assertSelected(testGrid, new int[] {4, 8});
    testGrid.selectRow(6, true, false);
    assertSelected(testGrid, new int[] {4, 6, 8});
    testGrid.selectRow(6, true, false);
    assertSelected(testGrid, new int[] {4, 8});

    // Select rows using shift
    testGrid.deselectAllRows();
    testGrid.selectRow(2, false, false);
    testGrid.selectRow(4, false, true);
    assertSelected(testGrid, new int[] {2, 3, 4});
    testGrid.selectRow(6, false, true);
    assertSelected(testGrid, new int[] {4, 5, 6});
    testGrid.selectRow(0, false, true);
    assertSelected(testGrid, new int[] {0, 1, 2, 3, 4, 5, 6});

    // Select rows using ctrl + shift
    testGrid.deselectAllRows();
    testGrid.selectRow(2, false, false);
    testGrid.selectRow(4, false, true);
    assertSelected(testGrid, new int[] {2, 3, 4});
    testGrid.selectRow(6, true, false);
    assertSelected(testGrid, new int[] {2, 3, 4, 6});
    testGrid.selectRow(8, true, true);
    assertSelected(testGrid, new int[] {2, 3, 4, 6, 7, 8});
  }

  /**
   * Verify that a specific set of rows are selected.
   * 
   * @param expected the rows that are expected to be selected
   */
  private void assertSelected(SelectionGrid grid, int[] expected) {
    Set<Integer> selected = grid.getSelectedRows();
    assertEquals(expected.length, selected.size());
    for (int i = 0; i < expected.length; i++) {
      assertTrue(selected.contains(new Integer(expected[i])));
    }
  }
}
