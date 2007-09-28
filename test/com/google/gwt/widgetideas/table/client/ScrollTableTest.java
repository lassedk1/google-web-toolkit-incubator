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
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Tests methods in the {@link ScrollTableTest} class.
 */
public class ScrollTableTest extends GWTTestCase {

  public String getModuleName() {
    return "com.google.gwt.widgetideas.tables.ScrollTable";
  }
  
  /**
   * Test accessors and modifiers.
   */
  public void testAccessors() {
    // Create a ScrollTable
    ScrollTable table = new ScrollTable();
    ExtendedGrid dataTable = table.getDataTable();
    SizableFlexTable headerTable = table.getHeaderTable();
    RootPanel.get().add(table);

    // Add some default data
    for (int row = 0; row < 5; row++) {
      for (int cell = 0; cell < 5; cell++) {
        dataTable.setHTML(row, cell, row + " - " + cell);
      }
    }
    
    // Setting widths
    table.setColumnWidth(2, 100);
    assertEquals(table.getColumnWidth(2), 100);
    assertEquals(dataTable.getColumnWidth(2), 100);
    assertEquals(headerTable.getColumnWidth(2), 100);

    // Auto-fit width
    table.autoFitColumnWidth(1);
    assertEquals(dataTable.getColumnWidth(1), headerTable.getColumnWidth(1));
    assertTrue(dataTable.getColumnWidth(1) > 0);
    
    // Spacing and padding
    table.setCellPadding(3);
    table.setCellSpacing(2);
    assertEquals(table.getCellPadding(), 3);
    assertEquals(dataTable.getCellPadding(), 3);
    assertEquals(headerTable.getCellPadding(), 3);
    assertEquals(table.getCellSpacing(), 2);
    assertEquals(dataTable.getCellSpacing(), 2);
    assertEquals(headerTable.getCellSpacing(), 2);
    
    // Remove not supported
    try {
      table.remove(dataTable);
      fail("Remove is not a supported operation");
    } catch (UnsupportedOperationException e) {
      assertTrue(true);
    }
  }
}
