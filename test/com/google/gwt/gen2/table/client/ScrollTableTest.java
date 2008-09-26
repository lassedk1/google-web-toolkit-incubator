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
import com.google.gwt.gen2.table.client.ScrollTable.ScrollPolicy;

/**
 * Tests methods in the {@link ScrollTable} class.
 */
public class ScrollTableTest extends Gen2TestBase {

  /**
   * Test accessors and modifiers.
   */
  public void testAccessors() {
    // Initialize the table
    FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
    FixedWidthGrid dataTable = new FixedWidthGrid();
    FixedWidthFlexTable footerTable = new FixedWidthFlexTable();
    ScrollTable table = getScrollTable(headerTable, dataTable, footerTable);

    // Accessible tables
    assertEquals(headerTable, table.getHeaderTable());
    assertEquals(dataTable, table.getDataTable());
    assertEquals(footerTable, table.getFooterTable());
    table.setFooterTable(null);
    assertEquals(null, table.getFooterTable());
    table.setFooterTable(footerTable);
    assertEquals(footerTable, table.getFooterTable());

    // Cell spacing
    table.setCellSpacing(2);
    assertEquals(table.getCellSpacing(), 2);
    assertEquals(dataTable.getCellSpacing(), 2);
    assertEquals(headerTable.getCellSpacing(), 2);

    // Cell padding
    table.setCellPadding(3);
    assertEquals(table.getCellPadding(), 3);
    assertEquals(dataTable.getCellPadding(), 3);
    assertEquals(headerTable.getCellPadding(), 3);

    // Resize policy
    table.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);
    assertEquals(ScrollTable.ResizePolicy.FILL_WIDTH, table.getResizePolicy());
    table.setResizePolicy(ScrollTable.ResizePolicy.FIXED_WIDTH);
    assertEquals(ScrollTable.ResizePolicy.FIXED_WIDTH, table.getResizePolicy());

    // Column resize policy
    table.setColumnResizePolicy(ScrollTable.ColumnResizePolicy.SINGLE_CELL);
    assertEquals(ScrollTable.ColumnResizePolicy.SINGLE_CELL, table.getColumnResizePolicy());
    table.setColumnResizePolicy(ScrollTable.ColumnResizePolicy.MULTI_CELL);
    assertEquals(ScrollTable.ColumnResizePolicy.MULTI_CELL, table.getColumnResizePolicy());

    // Min width
    assertEquals(-1, table.getMinWidth());
    table.setMinWidth(100);
    assertEquals(100, table.getMinWidth());

    // Scrolling enabled
    table.setScrollPolicy(ScrollPolicy.HORIZONTAL);
    assertEquals(ScrollPolicy.HORIZONTAL, table.getScrollPolicy());
    table.setScrollPolicy(ScrollPolicy.BOTH);
    assertEquals(ScrollPolicy.BOTH, table.getScrollPolicy());

    // Sorting enabled
    table.setSortingEnabled(true);
    assertTrue(table.isSortingEnabled());
    table.setSortingEnabled(false);
    assertFalse(table.isSortingEnabled());

    // Default Column width
    int defaultWidth = FixedWidthGrid.DEFAULT_COLUMN_WIDTH;
    assertEquals(defaultWidth, table.getColumnWidth(2));
    assertEquals(defaultWidth, headerTable.getColumnWidth(2));
    assertEquals(defaultWidth, dataTable.getColumnWidth(2));
    assertEquals(defaultWidth, footerTable.getColumnWidth(2));

    // Set column width
    table.setResizePolicy(ScrollTable.ResizePolicy.UNCONSTRAINED);
    table.setColumnWidth(2, 100);
    table.setColumnWidth(3, 20);
    assertEquals(100, table.getColumnWidth(2));
    assertEquals(100, headerTable.getColumnWidth(2));
    assertEquals(100, dataTable.getColumnWidth(2));
    assertEquals(100, footerTable.getColumnWidth(2));
    assertEquals(20, table.getColumnWidth(3));
    assertEquals(20, headerTable.getColumnWidth(3));
    assertEquals(20, dataTable.getColumnWidth(3));
    assertEquals(20, footerTable.getColumnWidth(3));

    // Set guaranteed column width
    assertFalse(table.isColumnWidthGuaranteed(2));
    table.setGuaranteedColumnWidth(2, 100);
    assertTrue(table.isColumnWidthGuaranteed(2));
    table.setColumnWidth(2, 100);
    assertFalse(table.isColumnWidthGuaranteed(2));

    // Remove widget not supported
    try {
      table.remove(dataTable);
      fail("Remove is not a supported operation");
    } catch (UnsupportedOperationException e) {
      assertTrue(true);
    }
  }

  /**
   * Get the scroll table.
   * 
   * @return the scroll table
   */
  protected ScrollTable getScrollTable(FixedWidthFlexTable headerTable, FixedWidthGrid dataTable,
      FixedWidthFlexTable footerTable) {
    ScrollTable scrollTable = new ScrollTable(dataTable, headerTable);
    scrollTable.setFooterTable(footerTable);
    return scrollTable;
  }
}
