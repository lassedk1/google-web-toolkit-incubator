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
import com.google.gwt.widgetideas.table.client.overrides.FlexTable.FlexCellFormatter;

/**
 * Tests methods used for all {@link FixedWidthFlexTable} class.
 */
public class FixedWidthFlexTableTest extends GWTTestCase {
  /**
   * Get the table.
   * 
   * @return the table
   */
  public FixedWidthFlexTable getFixedWidthFlexTable() {
    return new FixedWidthFlexTable();
  }

  public String getModuleName() {
    return "com.google.gwt.widgetideas.WidgetIdeas";
  }

  /**
   * Test accessors.
   */
  public void testAccessors() {
    // Initialize the grid
    FixedWidthFlexTable testTable = getFixedWidthFlexTable();

    // Default column width
    int defaultWidth = testTable.getDefaultColumnWidth();
    assertEquals(FixedWidthFlexTable.DEFAULT_COLUMN_WIDTH, defaultWidth);

    // Column width
    assertEquals(defaultWidth, testTable.getColumnWidth(4));
    testTable.setColumnWidth(4, defaultWidth + 40);
    assertEquals(defaultWidth + 40, testTable.getColumnWidth(4));
    assertEquals(defaultWidth, testTable.getColumnWidth(3));
  }

  /**
   * Test ghost row count.
   */
  public void testGhostRowBasic() {
    // Initialize the table
    FixedWidthFlexTable testTable = getFixedWidthFlexTable();

    // Cell creation
    assertGhostCount(0, testTable);
    testTable.setHTML(0, 0, "");
    assertGhostCount(1, testTable);
    testTable.setHTML(0, 3, "");
    assertGhostCount(4, testTable);
    testTable.setHTML(1, 2, "");
    assertGhostCount(4, testTable);
    testTable.setHTML(2, 4, "");
    assertGhostCount(5, testTable);

    // Row insertion
    testTable.insertRow(0);
    assertGhostCount(5, testTable);
    testTable.insertRow(testTable.getRowCount());
    assertGhostCount(5, testTable);

    // Row removal
    testTable.removeRow(0);
    assertGhostCount(5, testTable);
    testTable.removeRow(2);
    assertGhostCount(4, testTable);

    // Cell removal
    testTable.setHTML(0, 8, "");
    assertGhostCount(9, testTable);
    testTable.removeCell(0, 1);
    assertGhostCount(8, testTable);
    testTable.removeCell(0, 7);
    assertGhostCount(7, testTable);
  }

  /**
   * Test ghost row count with spans.
   */
  public void testGhostRowSpans() {
    // Initialize the table
    FixedWidthFlexTable testTable = getFixedWidthFlexTable();
    FlexCellFormatter cellFormatter = testTable.getFlexCellFormatter();
    for (int row = 0; row < 3; row++) {
      for (int cell = 0; cell < 3; cell++) {
        testTable.setHTML(row, cell, "");
      }
    }

    // Set colspan
    assertGhostCount(3, testTable);
    cellFormatter.setColSpan(0, 1, 3);
    assertGhostCount(5, testTable);
    cellFormatter.setColSpan(0, 1, 2);
    assertGhostCount(4, testTable);
    cellFormatter.setColSpan(0, 1, 1);
    assertGhostCount(3, testTable);
    cellFormatter.setColSpan(0, 1, 0);
    assertGhostCount(3, testTable);

    // Set rowspan
    assertGhostCount(3, testTable);
    cellFormatter.setRowSpan(0, 1, 3);
    assertGhostCount(4, testTable);
    cellFormatter.setRowSpan(0, 1, 2);
    assertGhostCount(4, testTable);
    cellFormatter.setRowSpan(0, 1, 1);
    assertGhostCount(3, testTable);
  }
  
  /**
   * Assert the expected number of columns in the ghost row of the table.
   * 
   * @param expected the expected number of ghost columns
   * @param table the table
   */
  private void assertGhostCount(int expected, FixedWidthFlexTable table) {
    assertEquals(expected, table.getGhostColumnCount());
  }
}
