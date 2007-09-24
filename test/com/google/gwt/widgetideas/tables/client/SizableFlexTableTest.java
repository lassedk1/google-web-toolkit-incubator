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
package com.google.gwt.widgetideas.tables.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.widgetideas.tables.client.SizableFlexTable;

/**
 * Tests methods in the {@link SizableFlexTable} class.
 */
public class SizableFlexTableTest extends GWTTestCase {
  
  public String getModuleName() {
    return "com.google.gwt.widgetideas.tables.SizableFlexTable";
  }
  
  /**
   * Set and get cell contents.
   */
  public void testCellContents() {
    // Create a small grid
    SizableFlexTable grid = new SizableFlexTable();
    
    // set/get Widget
    HTML widget1 = new HTML("Widget 1");
    HTML widget2 = new HTML("Widget 2");
    grid.setWidget(0, 0, widget1);
    grid.setWidget(0, 1, widget2);
    assertEquals(grid.getWidget(0, 0), widget1);
    assertEquals(grid.getWidget(0, 1), widget2);
    assertEquals(grid.getCellCount(0), 2);
    assertEquals(grid.getRowCount(), 1);

    // set/get Text
    grid.setText(1, 0, "Hello Text");
    assertEquals(grid.getHTML(1, 0), "Hello Text");
    assertEquals(grid.getCellCount(1), 1);
    assertEquals(grid.getRowCount(), 2);

    // set/get HTML
    grid.setHTML(2, 0, "Hello HTML");
    assertEquals(grid.getHTML(2, 0), "Hello HTML");
    assertEquals(grid.getCellCount(2), 1);
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
   
    // Clear a cell and verify the values
    grid.setHTML(3, 0, "Hello HTML");
    grid.setText(3, 1, "Hello Text");
    grid.setWidget(3, 2, new HTML("Hello Widget"));
    grid.clearCell(3, 0);
    assertEquals(grid.getHTML(3, 0), "");
    grid.clearCell(3, 1);
    assertEquals(grid.getText(3, 0), "");
    grid.clearCell(3, 2);
    assertEquals(grid.getWidget(3, 0), null);
  }
  
  /**
   * Test cell manipulation using insertion, deletion, colspan, and rowspan.
   */
  public void testCellManipulation() {
    // Create a 3x3 grid
    SizableFlexTable grid = new SizableFlexTable() {
      public int getGhostColumnCount() {
        return super.getGhostColumnCount();
      }
    };
    for (int row = 0; row < 3; row++) {
      for (int cell = 0; cell < 3; cell++) {
        grid.setHTML(row, cell, row + ":" + cell);        
      }
    }
    assertEquals(grid.getGhostColumnCount(), 3);
    
    // Basic setting of row and column spans
    grid.getFlexCellFormatter().setColSpan(1, 1, 4);
    assertEquals(grid.getGhostColumnCount(), 6);
    grid.getFlexCellFormatter().setColSpan(1, 1, 2);
    assertEquals(grid.getGhostColumnCount(), 4);
    grid.getFlexCellFormatter().setColSpan(1, 1, 1);
    assertEquals(grid.getGhostColumnCount(), 3);
    grid.getFlexCellFormatter().setRowSpan(1, 1, 3);
    assertEquals(grid.getGhostColumnCount(), 4);
    grid.getFlexCellFormatter().setRowSpan(1, 1, 2);
    assertEquals(grid.getGhostColumnCount(), 4);
    grid.getFlexCellFormatter().setRowSpan(1, 1, 1);
    assertEquals(grid.getGhostColumnCount(), 3);
    
    // Insert cells
    grid.insertCell(1, 1);
    assertEquals(grid.getGhostColumnCount(), 4);
    grid.insertCell(1, 1);
    assertEquals(grid.getGhostColumnCount(), 5);
    grid.insertCell(2, 1);
    assertEquals(grid.getGhostColumnCount(), 5);

    // Remove cells
    grid.getFlexCellFormatter().setColSpan(2, 1, 3);
    assertEquals(grid.getGhostColumnCount(), 6);
    grid.removeCell(2, 1);
    assertEquals(grid.getGhostColumnCount(), 5);
    grid.removeCell(1, 1);
    assertEquals(grid.getGhostColumnCount(), 4);
    grid.removeCell(1, 1);
    assertEquals(grid.getGhostColumnCount(), 3);

    // Insert/Remove rows
    grid.getFlexCellFormatter().setRowSpan(1, 1, 2);
    assertEquals(grid.getGhostColumnCount(), 4);
    grid.insertRow(2);
    assertEquals(grid.getGhostColumnCount(), 3);
    grid.removeRow(2);
    assertEquals(grid.getGhostColumnCount(), 4);
  }
  
  /**
   * Test column width methods.
   */
  public void testColumnWidth() {
    // Create a small grid
    SizableFlexTable grid = new SizableFlexTable();
    RootPanel.get().add(grid);
    
    // Set and get the column width
    grid.setHTML(0, 0, "");
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
    
    // Test invalid columns
    try {
      grid.setColumnWidth(-1, 100);
      fail("Expected IndexOutOfBoundsException exception");
    } catch (IndexOutOfBoundsException e) {
      assertTrue(true);
    }
  }
}
