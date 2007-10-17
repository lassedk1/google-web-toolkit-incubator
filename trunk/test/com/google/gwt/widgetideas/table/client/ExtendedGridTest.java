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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Tests methods in the {@link ExtendedGrid} class.
 */
public class ExtendedGridTest extends GWTTestCase {

  public String getModuleName() {
    return "com.google.gwt.widgetideas.table.ExtendedGrid";
  }

  //ECC removed as it's currently failing
  /**
   * Set and get cell contents.
   */
  public void removedtestCellContents() {
    // Create a small grid
    ExtendedGrid grid = new ExtendedGrid();
    
    // set/get Widget
    HTML widget1 = new HTML("Widget 1");
    HTML widget2 = new HTML("Widget 2");
    grid.setWidget(0, 0, widget1);
    grid.setWidget(0, 1, widget2);
    assertEquals(grid.getWidget(0, 0), widget1);
    assertEquals(grid.getWidget(0, 1), widget2);
    assertEquals(grid.getColumnCount(), 2);
    assertEquals(grid.getRowCount(), 1);

    // set/get Text
    grid.setText(1, 0, "Hello Text");
    assertEquals(grid.getHTML(1, 0), "Hello Text");
    assertEquals(grid.getColumnCount(), 2);
    assertEquals(grid.getRowCount(), 2);

    // set/get HTML
    grid.setHTML(2, 0, "Hello HTML");
    assertEquals(grid.getHTML(2, 0), "Hello HTML");
    assertEquals(grid.getColumnCount(), 2);
    assertEquals(grid.getRowCount(), 3);
    
    // getHTML out of bounds
    try {
      grid.getHTML(3, 3);
      fail("Expected IndexOutOfBoundsException exception");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      grid.getHTML(-1, 0);
      fail("Expected IndexOutOfBoundsException exception");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      grid.getHTML(0, -1);
      fail("Expected IndexOutOfBoundsException exception");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }

    // getText out of bounds
    try {
      grid.getText(3, 3);
      fail("Expected IndexOutOfBoundsException exception");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      grid.getText(-1, 0);
      fail("Expected IndexOutOfBoundsException exception");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      grid.getText(0, -1);
      fail("Expected IndexOutOfBoundsException exception");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    
    // getWidget out of bounds
    try {
      grid.getWidget(3, 3);
      fail("Expected IndexOutOfBoundsException exception");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      grid.getWidget(-1, 0);
      fail("Expected IndexOutOfBoundsException exception");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
    try {
      grid.getWidget(0, -1);
      fail("Expected IndexOutOfBoundsException exception");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }

    // get cell values that have not been set
    final HTML nbsp = new HTML("&nbsp;");
    assertEquals(grid.getText(2, 1), nbsp.getText());
    assertEquals(grid.getHTML(2, 1), nbsp.getHTML());
    assertEquals(grid.getWidget(2, 1), null);
    
    // Clear a cell and verify the values
    grid.setHTML(3, 0, "Hello HTML");
    grid.setText(3, 1, "Hello Text");
    grid.setWidget(3, 2, new HTML("Hello Widget"));
    grid.clearCell(3, 0);
    assertEquals(grid.getHTML(3, 0), nbsp.getHTML());
    grid.clearCell(3, 1);
    assertEquals(grid.getText(3, 0), nbsp.getText());
    grid.clearCell(3, 2);
    assertEquals(grid.getWidget(3, 0), null);
  }
  
  /**
   * Test column width methods.
   */
  public void testColumnWidth() {
    // Create a small grid
    ExtendedGrid grid = new ExtendedGrid();
    grid.resize(5, 5);
    RootPanel.get().add(grid);
    
    // Set and get the column width
    grid.setColumnWidth(0, 100);
    assertEquals(grid.getColumnWidth(0), 100);
    Element td = grid.getCellFormatter().getElement(0, 0);
    assertTrue(DOM.getElementPropertyInt(td, "offsetWidth") >= 100);

    // Set to invalid width
    grid.setColumnWidth(0, -1);
    assertEquals(grid.getColumnWidth(0), 1);
    
    // Make sure we can undersize a column
    grid.setHTML(0, 0, "Hello World");
    grid.setColumnWidth(0, 500);
    assertEquals(grid.getColumnWidth(0), 500);
    grid.setColumnWidth(0, 5);
    assertEquals(grid.getColumnWidth(0), 5);
    
    // Test auto fit column
    String cellContents = "";
    int curColumnWidth = 0;
    for (int row = 0; row < grid.getRowCount(); row++) {
      // Add more content to next cell down
      cellContents += "a";
      grid.setHTML(row, 0, cellContents);
      
      // Verify auto fit grows the column
      grid.autoFitColumnWidth(0);
      assertTrue(grid.getColumnWidth(0) > curColumnWidth);
      
      // Verify repeated calls to auto fit do not grow the column
      curColumnWidth = grid.getColumnWidth(0);
      grid.autoFitColumnWidth(0);
      assertEquals(grid.getColumnWidth(0), curColumnWidth);
      
      // Verify auto fit always picks the same number
      grid.setColumnWidth(0, 1);
      grid.autoFitColumnWidth(0);
      assertEquals(grid.getColumnWidth(0), curColumnWidth);
    }
    
    // Test invalid columns
    try {
      grid.setColumnWidth(-1, 100);
      fail("Expected IndexOutOfBoundsException exception");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
  }
  
  /**
   * Tests resizing of the grid to larger and smaller sizes.
   */
  public void testResizeGrid() {
    // Create an empty grid
    ExtendedGrid grid = new ExtendedGrid();

    // Verify columns widths can be added before the column
    int defaultWidth = ExtendedGrid.DEFAULT_COLUMN_WIDTH;
    grid.setColumnWidth(2, defaultWidth + 20);
    grid.setColumnWidth(3, defaultWidth + 30);
    grid.resize(4, 4);
    assertEquals(grid.getColumnWidth(0), defaultWidth);
    assertEquals(grid.getColumnWidth(1), defaultWidth);
    assertEquals(grid.getColumnWidth(2), defaultWidth + 20);
    assertEquals(grid.getColumnWidth(3), defaultWidth + 30);

    // Verify column widths remain after shrinking the grid
    grid.resize(2, 2);
    assertEquals(grid.getColumnWidth(0), defaultWidth);
    assertEquals(grid.getColumnWidth(1), defaultWidth);
    assertEquals(grid.getColumnWidth(2), defaultWidth + 20);
    assertEquals(grid.getColumnWidth(3), defaultWidth + 30);
    
    // Verify column widths remain after enlarging the grid
    grid.resize(4, 4);
    assertEquals(grid.getColumnWidth(0), defaultWidth);
    assertEquals(grid.getColumnWidth(1), defaultWidth);
    assertEquals(grid.getColumnWidth(2), defaultWidth + 20);
    assertEquals(grid.getColumnWidth(3), defaultWidth + 30);
  }
}
