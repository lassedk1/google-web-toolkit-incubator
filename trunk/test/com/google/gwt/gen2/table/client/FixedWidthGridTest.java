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

import com.google.gwt.user.client.ui.RootPanel;

/**
 * Tests methods used for all {@link FixedWidthGrid} class.
 */
public class FixedWidthGridTest extends SortableGridTest {
  /**
   * Get the grid.
   * 
   * @return the grid
   */
  public FixedWidthGrid getFixedWidthGrid() {
    FixedWidthGrid grid = new FixedWidthGrid(10, 10);

    // Set some data (0-9) in each column
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        grid.setHTML(row, col, row + "");
      }
    }

    return grid;
  }

  /**
   * Get the grid.
   * 
   * @return the grid
   */
  @Override
  public SortableGrid getSortableGrid() {
    return getFixedWidthGrid();
  }

  /**
   * Test accessors.
   */
  @Override
  public void testAccessors() {
    // Initialize the grid
    FixedWidthGrid testGrid = getFixedWidthGrid();
    int defaultWidth = FixedWidthGrid.DEFAULT_COLUMN_WIDTH;

    // Column width
    assertEquals(defaultWidth, testGrid.getColumnWidth(4));
    testGrid.setColumnWidth(4, defaultWidth + 40);
    assertEquals(defaultWidth + 40, testGrid.getColumnWidth(4));
    assertEquals(defaultWidth, testGrid.getColumnWidth(3));
  }

  /**
   * Test accessors.
   */
  public void testGhostRow() {
    // Initialize the grid
    FixedWidthGrid testGrid = getFixedWidthGrid();

    // Resize columns
    testGrid.resizeColumns(10);
    assertEquals(10, testGrid.getColumnCount());
    assertEquals(10, testGrid.getGhostColumnCount());
    testGrid.resizeColumns(4);
    assertEquals(4, testGrid.getColumnCount());
    assertEquals(4, testGrid.getGhostColumnCount());
    testGrid.resize(10, 10);

    assertEquals(10, testGrid.getColumnCount());
    assertEquals(10, testGrid.getGhostColumnCount());
  }

  public void testIdealColumnWidth() {
    // Initialize the grid
    FixedWidthGrid testGrid = getFixedWidthGrid();

    // When not attached, the ideal width should be -1
    assertEquals(-1, testGrid.getIdealColumnWidth(0));

    // Out of bounds indexes should
    RootPanel.get().add(testGrid);
    assertEquals(-1, testGrid.getIdealColumnWidth(11));

    // Ideal width should be greater than 0
    assertTrue(testGrid.getIdealColumnWidth(0) > 0);

    RootPanel.get().remove(testGrid);
  }

  /**
   * Test selection with row insertion and removal.
   */
  public void testSelectionInsertion() {
    // Initialize the grid
    FixedWidthGrid testGrid = getFixedWidthGrid();

    // Row removal with selection
    testGrid.deselectAllRows();
    testGrid.selectRow(2, false);
    testGrid.selectRow(4, false);
    testGrid.selectRow(6, false);
    assertEquals(3, testGrid.getSelectedRows().size());
    testGrid.removeRow(5);
    assertEquals(0, testGrid.getSelectedRows().size());

    // Row insertion with selection
    testGrid.deselectAllRows();
    testGrid.selectRow(2, false);
    testGrid.selectRow(4, false);
    testGrid.selectRow(6, false);
    assertEquals(3, testGrid.getSelectedRows().size());
    testGrid.insertRow(5);
    assertEquals(0, testGrid.getSelectedRows().size());
  }
}
