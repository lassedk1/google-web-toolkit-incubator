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

import com.google.gwt.gen2.table.client.AbstractScrollTable.SortPolicy;

/**
 * Tests methods in the {@link ScrollTable} class.
 */
public class ScrollTableTest extends AbstractScrollTableTest {

  /**
   * Test accessors and modifiers.
   */
  public void testColumnWidth() {
    // Initialize the table
    FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
    FixedWidthGrid dataTable = new FixedWidthGrid();
    FixedWidthFlexTable footerTable = new FixedWidthFlexTable();
    ScrollTable table = getScrollTable(headerTable, dataTable, footerTable);

    // maximum width
    {
      table.setMaximumColumnWidth(0, 100);
      assertEquals(100, table.getMaximumColumnWidth(0));
    }

    // minimum width
    {
      table.setMinimumColumnWidth(0, 50);
      assertEquals(50, table.getMinimumColumnWidth(0));
    }

    // preferred width
    {
      table.setPreferredColumnWidth(0, 75);
      assertEquals(75, table.getPreferredColumnWidth(0));
    }

    // truncation
    {
      table.setColumnTruncatable(0, false);
      assertFalse(table.isColumnTruncatable(0));
      table.setColumnTruncatable(0, true);
      assertTrue(table.isColumnTruncatable(0));

      table.setFooterColumnTruncatable(0, false);
      assertFalse(table.isFooterColumnTruncatable(0));
      table.setFooterColumnTruncatable(0, true);
      assertTrue(table.isFooterColumnTruncatable(0));

      table.setHeaderColumnTruncatable(0, false);
      assertFalse(table.isHeaderColumnTruncatable(0));
      table.setHeaderColumnTruncatable(0, true);
      assertTrue(table.isHeaderColumnTruncatable(0));
    }
  }

  public void testSorting() {
    // Initialize the table
    FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
    FixedWidthGrid dataTable = new FixedWidthGrid();
    FixedWidthFlexTable footerTable = new FixedWidthFlexTable();
    ScrollTable table = getScrollTable(headerTable, dataTable, footerTable);

    // Column Sorting
    {
      // Default enabled
      table.setSortPolicy(SortPolicy.SINGLE_CELL);
      assertTrue(table.isColumnSortable(0));

      // Default disabled
      table.setSortPolicy(SortPolicy.DISABLED);
      assertFalse(table.isColumnSortable(0));

      // Set to unsortable
      table.setSortPolicy(SortPolicy.SINGLE_CELL);
      table.setColumnSortable(0, false);
      assertFalse(table.isColumnSortable(0));

      // Set to sortable
      table.setSortPolicy(SortPolicy.DISABLED);
      table.setColumnSortable(0, true);
      assertFalse(table.isColumnSortable(0));
    }
  }

  @Override
  protected ScrollTable getScrollTable(FixedWidthFlexTable headerTable,
      FixedWidthGrid dataTable, FixedWidthFlexTable footerTable) {
    ScrollTable scrollTable = new ScrollTable(dataTable, headerTable);
    scrollTable.setFooterTable(footerTable);
    return scrollTable;
  }
}
