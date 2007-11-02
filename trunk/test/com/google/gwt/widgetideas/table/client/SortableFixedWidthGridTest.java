/*
 * Copyright 2007 Google Inc.
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
package com.google.gwt.widgetideas.table.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.widgetideas.table.client.SortableFixedWidthGrid.ColumnSorter;
import com.google.gwt.widgetideas.table.client.SortableFixedWidthGrid.ColumnSorterCallback;

/**
 * Tests methods used for all {@link SortableFixedWidthGrid} class.
 */
public class SortableFixedWidthGridTest extends GWTTestCase {
  /**
   * A version of {@link SortableColumnsListener} used for testing.
   */
  private class TestSortableColumnsListener implements SortableColumnsListener {
    /**
     * The sorted column index.
     */
    private int sortedColumn = -1;

    /**
     * Is sorting reversed.
     */
    private boolean sortedAscending = true;

    /**
     * @return the sortedColumn
     */
    public int getSortedColumn() {
      return sortedColumn;
    }

    /**
     * @return the sortedAscending
     */
    public boolean isSortedAscending() {
      return sortedAscending;
    }

    public void onSetSortedColumn(int column, boolean ascending) {
      sortedColumn = column;
      sortedAscending = ascending;
    }
  }

  /**
   * The grid to test.
   */
  private SortableFixedWidthGrid grid = null;

  public String getModuleName() {
    return "com.google.gwt.widgetideas.WidgetIdeas";
  }

  /**
   * Get the grid.
   * 
   * @return the grid
   */
  public SortableFixedWidthGrid getSortableGrid() {
    if (grid == null) {
      grid = new SortableFixedWidthGrid();
      grid.resize(10, 10);

      // Set some data (0-9) in each column
      for (int row = 0; row < 10; row++) {
        for (int col = 0; col < 10; col++) {
          grid.setHTML(row, col, row + "");
        }
      }
    }
    return grid;
  }

  /**
   * Test accessors.
   * 
   * Uses columns: 3
   */
  public void testAccessor() {
    // Initialize the grid
    SortableFixedWidthGrid testGrid = getSortableGrid();
    ColumnSorter mySorter = new ColumnSorter() {
      public void onSortColumn(SortableFixedWidthGrid grid, int column,
          boolean ascending, ColumnSorterCallback callback) {
      }
    };

    // Column sorter
    testGrid.setColumnSorter(null);
    assertNull(testGrid.getColumnSorter());
    testGrid.setColumnSorter(mySorter);
    assertEquals(mySorter, testGrid.getColumnSorter());
    testGrid.setColumnSorter(null);
    assertNull(testGrid.getColumnSorter());

    // Sorted index
    testGrid.sortColumn(3, true);
    assertEquals(3, testGrid.getSortIndex());
    assertTrue(testGrid.isSortAscending());
    testGrid.sortColumn(3);
    assertEquals(3, testGrid.getSortIndex());
    assertFalse(testGrid.isSortAscending());

    // Set HTML
    testGrid.sortColumn(3, true);
    assertEquals(3, testGrid.getSortIndex());
    testGrid.setHTML(1, 3, "html");
    assertEquals(-1, testGrid.getSortIndex());

    // Set text
    testGrid.sortColumn(3, true);
    assertEquals(3, testGrid.getSortIndex());
    testGrid.setText(1, 3, "text");
    assertEquals(-1, testGrid.getSortIndex());

    // Set Widget
    testGrid.sortColumn(3, true);
    assertEquals(3, testGrid.getSortIndex());
    testGrid.setWidget(1, 3, new HTML("widget"));
    assertEquals(-1, testGrid.getSortIndex());
  }

  /**
   * Test the {@link TestSortableColumnsListener}s.
   * 
   * Uses columns: 4, 5
   */
  public void testListener() {
    // Initialize the grid
    SortableFixedWidthGrid testGrid = getSortableGrid();

    // Create some listeners
    TestSortableColumnsListener listener1 = new TestSortableColumnsListener();
    testGrid.addSortableColumnsListener(listener1);
    TestSortableColumnsListener listener2 = new TestSortableColumnsListener();
    testGrid.addSortableColumnsListener(listener2);
    TestSortableColumnsListener listener3 = new TestSortableColumnsListener();
    testGrid.addSortableColumnsListener(listener3);

    // Sort columns
    testGrid.sortColumn(5, false);
    assertEquals(5, listener1.getSortedColumn());
    assertFalse(listener1.isSortedAscending());
    assertEquals(5, listener2.getSortedColumn());
    assertFalse(listener2.isSortedAscending());
    assertEquals(5, listener3.getSortedColumn());
    assertFalse(listener3.isSortedAscending());

    // Remove listener
    testGrid.removeSortableColumnsListener(listener2);

    // Sort columns
    testGrid.sortColumn(4, true);
    assertEquals(4, listener1.getSortedColumn());
    assertTrue(listener1.isSortedAscending());
    assertEquals(5, listener2.getSortedColumn());
    assertFalse(listener2.isSortedAscending());
    assertEquals(4, listener3.getSortedColumn());
    assertTrue(listener3.isSortedAscending());
  }

  /**
   * Test moving rows.
   * 
   * Uses columns: 6
   */
  public void testMoveRows() {
    // Initialize the grid
    SortableFixedWidthGrid testGrid = getSortableGrid();
    testGrid.sortColumn(6);

    // Move a row up
    testGrid.moveRowUp(5);
    assertEquals("4", testGrid.getHTML(5, 6));
    assertEquals("5", testGrid.getHTML(4, 6));
    
    // Move a row down
    testGrid.moveRowDown(7);
    assertEquals("7", testGrid.getHTML(8, 6));
    assertEquals("8", testGrid.getHTML(7, 6));

    // Swap rows
    testGrid.swapRows(1, 9);
    assertEquals("1", testGrid.getHTML(9, 6));
    assertEquals("9", testGrid.getHTML(1, 6));
    
    // Test invalid indexes
    try {
      testGrid.moveRowUp(0);
      fail("Expected IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      testGrid.moveRowUp(10);
      fail("Expected IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      testGrid.moveRowDown(9);
      fail("Expected IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      testGrid.moveRowDown(-1);
      fail("Expected IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      testGrid.swapRows(-1, 5);
      fail("Expected IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      testGrid.swapRows(10, 5);
      fail("Expected IndexOutOfBoundsException");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
  }

  /**
   * Test built in client side quick sorting.
   * 
   * Uses columns: 1, 2
   */
  public void testSorting() {
    // Initialize the grid
    SortableFixedWidthGrid testGrid = getSortableGrid();

    // Sort ascending using default sort
    testGrid.setColumnSorter(null);
    testGrid.sortColumn(2);
    assertEquals(2, testGrid.getSortIndex());
    assertTrue(testGrid.isSortAscending());
    for (int row = 0; row < 10; row++) {
      assertEquals(row + "", testGrid.getHTML(row, 2));
    }

    // Sort descending in same row
    testGrid.sortColumn(2);
    assertEquals(2, testGrid.getSortIndex());
    assertFalse(testGrid.isSortAscending());
    for (int row = 0; row < 10; row++) {
      assertEquals((9 - row) + "", testGrid.getHTML(row, 2));
    }

    // Sort descending in different row
    testGrid.sortColumn(1, false);
    assertEquals(1, testGrid.getSortIndex());
    assertFalse(testGrid.isSortAscending());
    for (int row = 0; row < 10; row++) {
      assertEquals((9 - row) + "", testGrid.getHTML(row, 1));
    }

    // Reverse rows
    testGrid.reverseRows();
    assertEquals(1, testGrid.getSortIndex());
    assertTrue(testGrid.isSortAscending());
    for (int row = 0; row < 10; row++) {
      assertEquals(row + "", testGrid.getHTML(row, 1));
    }
  }
}
