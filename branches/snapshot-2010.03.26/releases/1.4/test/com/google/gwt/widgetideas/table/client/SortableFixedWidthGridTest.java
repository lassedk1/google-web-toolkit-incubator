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
import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortList;

/**
 * Tests methods used for all {@link SortableFixedWidthGrid} class.
 */
public class SortableFixedWidthGridTest extends GWTTestCase {
  /**
   * A version of {@link SortableColumnsListener} used for testing.
   */
  private class TestSortableColumnsListener implements SortableColumnsListener {
    /**
     * The sorted column info.
     */
    private ColumnSortList sortList = null;

    /**
     * @return the sorted column information
     */
    public ColumnSortList getSortList() {
      return sortList;
    }

    public void onColumnSorted(ColumnSortList sortList) {
      this.sortList = sortList.copy();
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
      public void onSortColumn(SortableFixedWidthGrid grid,
          ColumnSortList sortList, ColumnSorterCallback callback) {
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
    ColumnSortList sortList = testGrid.getColumnSortList();
    testGrid.sortColumn(3, true);
    assertEquals(3, sortList.getPrimaryColumn());
    assertTrue(sortList.isPrimaryAscending());
    testGrid.sortColumn(3);
    assertEquals(3, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());

    // Set HTML
    testGrid.sortColumn(3, true);
    assertEquals(3, sortList.getPrimaryColumn());
    testGrid.setHTML(1, 3, "html");
    assertEquals(3, sortList.getPrimaryColumn());

    // Set text
    testGrid.sortColumn(3, true);
    assertEquals(3, sortList.getPrimaryColumn());
    testGrid.setText(1, 3, "text");
    assertEquals(3, sortList.getPrimaryColumn());

    // Set Widget
    testGrid.sortColumn(3, true);
    assertEquals(3, sortList.getPrimaryColumn());
    testGrid.setWidget(1, 3, new HTML("widget"));
    assertEquals(3, sortList.getPrimaryColumn());
  }

  /**
   * Test the {@link TestSortableColumnsListener}s.
   * 
   * Uses columns: 4, 5
   */
  public void testListener() {
    // Initialize the grid
    SortableFixedWidthGrid testGrid = getSortableGrid();
    ColumnSortList sortList = testGrid.getColumnSortList();

    // Create some listeners
    TestSortableColumnsListener listener1 = new TestSortableColumnsListener();
    testGrid.addSortableColumnsListener(listener1);
    TestSortableColumnsListener listener2 = new TestSortableColumnsListener();
    testGrid.addSortableColumnsListener(listener2);
    TestSortableColumnsListener listener3 = new TestSortableColumnsListener();
    testGrid.addSortableColumnsListener(listener3);

    // Sort columns
    testGrid.sortColumn(5, false);
    assertEquals(5, listener1.getSortList().getPrimaryColumn());
    assertFalse(listener1.getSortList().isPrimaryAscending());
    assertTrue(sortList.equals(listener1.getSortList()));
    assertEquals(5, listener2.getSortList().getPrimaryColumn());
    assertFalse(listener2.getSortList().isPrimaryAscending());
    assertTrue(sortList.equals(listener2.getSortList()));
    assertEquals(5, listener3.getSortList().getPrimaryColumn());
    assertFalse(listener3.getSortList().isPrimaryAscending());
    assertTrue(sortList.equals(listener3.getSortList()));

    // Remove listener
    testGrid.removeSortableColumnsListener(listener2);

    // Sort columns
    testGrid.sortColumn(4, true);
    assertEquals(4, listener1.getSortList().getPrimaryColumn());
    assertTrue(listener1.getSortList().isPrimaryAscending());
    assertTrue(sortList.equals(listener1.getSortList()));
    assertEquals(5, listener2.getSortList().getPrimaryColumn());
    assertFalse(listener2.getSortList().isPrimaryAscending());
    assertFalse(sortList.equals(listener2.getSortList()));
    assertEquals(4, listener3.getSortList().getPrimaryColumn());
    assertTrue(listener3.getSortList().isPrimaryAscending());
    assertTrue(sortList.equals(listener3.getSortList()));
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
    ColumnSortList sortList = testGrid.getColumnSortList();

    // Sort ascending using default sort
    testGrid.setColumnSorter(null);
    testGrid.sortColumn(2);
    assertEquals(2, sortList.getPrimaryColumn());
    assertTrue(sortList.isPrimaryAscending());
    for (int row = 0; row < 10; row++) {
      assertEquals(row + "", testGrid.getHTML(row, 2));
    }

    // Sort descending in same row
    testGrid.sortColumn(2);
    assertEquals(2, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());
    for (int row = 0; row < 10; row++) {
      assertEquals((9 - row) + "", testGrid.getHTML(row, 2));
    }

    // Sort descending in different row
    testGrid.sortColumn(1, false);
    assertEquals(1, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());
    for (int row = 0; row < 10; row++) {
      assertEquals((9 - row) + "", testGrid.getHTML(row, 1));
    }

    // Reverse rows
    testGrid.reverseRows();
    assertEquals(1, sortList.getPrimaryColumn());
    assertTrue(sortList.isPrimaryAscending());
    for (int row = 0; row < 10; row++) {
      assertEquals(row + "", testGrid.getHTML(row, 1));
    }
  }
}
