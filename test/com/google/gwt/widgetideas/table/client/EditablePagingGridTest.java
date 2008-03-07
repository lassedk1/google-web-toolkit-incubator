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

/**
 * Tests methods used for all {@link EditablePagingGrid} class.
 */
public class EditablePagingGridTest extends GWTTestCase {
  /**
   * The grid to test.
   */
  private EditablePagingGrid grid = null;

  public String getModuleName() {
    return "com.google.gwt.widgetideas.WidgetIdeas";
  }

  /**
   * Get the grid.
   * 
   * @return the grid
   */
  public EditablePagingGrid getEditablePagingGrid() {
    if (grid == null) {
      // Create a simple table model
      TableModel tableModel = new ClientTableModel() {
        public Object getCell(int rowNum, int colNum) {
          if (colNum < 10) {
            return rowNum + ":" + colNum;
          }
          return null;
        }
      };

      // Create a grid
      grid = new EditablePagingGrid(tableModel);
    }
    return grid;
  }

  /**
   * Test accessors.
   */
  public void testAccessor() {
    // Initialize the grid
    EditablePagingGrid testGrid = getEditablePagingGrid();
    testGrid.setNumRows(100);
    testGrid.setPageSize(10);

    // Create a cell editor
    TextCellEditor textCellEditor = new TextCellEditor();

    // cellEditor
    assertFalse(testGrid.hasCellEditor(2));
    assertNull(testGrid.getCellEditor(2));
    testGrid.setCellEditor(2, textCellEditor);
    assertTrue(testGrid.hasCellEditor(2));
    assertFalse(testGrid.hasCellEditor(3));
    assertEquals(textCellEditor, testGrid.getCellEditor(2));
    testGrid.setCellEditor(2, null);
    assertFalse(testGrid.hasCellEditor(2));
    assertNull(testGrid.getCellEditor(2));

    // Hovering policy
    testGrid.setHoveringPolicy(EditablePagingGrid.HOVERING_POLICY_EDITABLE_CELL);
    assertEquals(EditablePagingGrid.HOVERING_POLICY_EDITABLE_CELL,
        testGrid.getHoveringPolicy());
    testGrid.setHoveringPolicy(PagingGrid.HOVERING_POLICY_DISABLED);
    assertEquals(PagingGrid.HOVERING_POLICY_DISABLED,
        testGrid.getHoveringPolicy());
  }
}
