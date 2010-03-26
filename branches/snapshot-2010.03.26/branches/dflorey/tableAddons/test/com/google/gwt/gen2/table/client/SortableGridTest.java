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

import com.google.gwt.gen2.table.client.SortableGrid.ColumnSorter;
import com.google.gwt.gen2.table.client.SortableGrid.ColumnSorterCallback;
import com.google.gwt.gen2.table.event.client.ColumnSortEvent;
import com.google.gwt.gen2.table.event.client.ColumnSortHandler;
import com.google.gwt.gen2.table.shared.ColumnSortList;
import com.google.gwt.user.client.ui.HTML;

/**
 * Tests methods used for all {@link SortableGrid} class.
 */
public class SortableGridTest extends SelectionGridTest {
  /**
   * A custom handler used for testing.
   */
  protected static class TestColumnSortHandler implements ColumnSortHandler {
    private ColumnSortEvent lastEvent = null;

    public void assertAscending(boolean ascending) {
      assertNotNull(lastEvent);
      assertEquals(ascending, lastEvent.getColumnSortList().isPrimaryAscending());
    }

    public void assertColumnIndex(int colIndex) {
      assertNotNull(lastEvent);
      assertEquals(colIndex, lastEvent.getColumnSortList().getPrimaryColumn());
    }

    public void onColumnSorted(ColumnSortEvent event) {
      lastEvent = event;
    }
  }

  /**
   * Get the selection grid.
   * 
   * @return the grid
   */
  @Override
  public SelectionGrid getSelectionGrid() {
    return getSortableGrid();
  }

  /**
   * Get the grid.
   * 
   * @return the grid
   */
  public SortableGrid getSortableGrid() {
    SortableGrid grid = new SortableGrid(10, 10);

    // Set some data (0-9) in each column
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        grid.setHTML(row, col, row + "");
      }
    }

    return grid;
  }

  /**
   * Test accessors.
   */
  public void testAccessor() {
    // Initialize the grid
    SortableGrid testGrid = getSortableGrid();
    ColumnSorter mySorter = new ColumnSorter() {
      @Override
      public void onSortColumn(SortableGrid grid, ColumnSortList sortList,
          ColumnSorterCallback callback) {
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

  public void testColumnSortHandler() {
    // Initialize the grid
    SortableGrid testGrid = getSortableGrid();
    ColumnSortList sortList = testGrid.getColumnSortList();

    // Create some listeners
    TestColumnSortHandler csh = new TestColumnSortHandler();
    testGrid.addColumnSortHandler(csh);

    // Sort columns
    testGrid.sortColumn(5, false);
    csh.assertColumnIndex(5);
    csh.assertAscending(false);

    // Sort columns
    testGrid.sortColumn(4, true);
    csh.assertColumnIndex(4);
    csh.assertAscending(true);
  }

  /**
   * Test moving rows.
   */
  public void testMoveRows() {
    // Initialize the grid
    SortableGrid testGrid = getSortableGrid();

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
   */
  public void testSorting() {
    // Initialize the grid
    SortableGrid testGrid = getSortableGrid();
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

    // Set the column sort list
    assertEquals(sortList, testGrid.getColumnSortList());
    ColumnSortList newList = new ColumnSortList();
    testGrid.setColumnSortList(newList);
    assertEquals(newList, testGrid.getColumnSortList());
  }
}
