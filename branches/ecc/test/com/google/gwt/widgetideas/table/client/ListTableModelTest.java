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

import java.util.ArrayList;
import java.util.List;

/**
 * Tests methods in the {@link ListTableModel} class.
 */
public class ListTableModelTest extends ClientTableModelTest {
  /**
   * The table model to test.
   */
  private ListTableModel tableModel = null;

  /**
   * @see TableModelTestBase
   */
  public TableModel getTableModel() {
    if (tableModel == null) {
      // Create a list of data
      List rowList = new ArrayList();
      for (int row = 0; row < NUM_ROWS; row++) {
        List columnList = new ArrayList();
        rowList.add(columnList);
        for (int column = 0; column < NUM_COLUMNS; column++) {
          columnList.add(new CellPair(row, column));
        }
      }

      // Create the table model
      tableModel = new ListTableModel(rowList);
    }

    // Return the table model
    return tableModel;
  }

  /**
   * Test insertion and deletion of rows.
   */
  public void testRowInsertion() {
    // Prepare the table model
    String testObject = "TEST";
    ListTableModel listTableModel = (ListTableModel) getTableModel();
  
    // Clear the existing table model
    tableModel = null;
    
    // Test row insertion at beginning
    listTableModel.onSetData(0, 2, testObject);
    listTableModel.onRowInserted(0);
    assertNull(listTableModel.getCell(0, 0));
    assertEquals(testObject, listTableModel.getCell(1, 2));
    listTableModel.onSetData(0, 4, testObject);
    assertEquals(testObject, listTableModel.getCell(0, 4));
    assertEquals(testObject, listTableModel.getCell(1, 2));
  
    // Test row removal at beginning
    listTableModel.onRowRemoved(0);
    assertEquals(testObject, listTableModel.getCell(0, 2));
    
    // Test row insertion in middle
    listTableModel.onSetData(4, 2, testObject);
    listTableModel.onRowInserted(3);
    assertNull(listTableModel.getCell(3, 0));
    assertEquals(testObject, listTableModel.getCell(5, 2));
    listTableModel.onSetData(3, 4, testObject);
    assertEquals(testObject, listTableModel.getCell(3, 4));
    assertEquals(testObject, listTableModel.getCell(5, 2));
    
    // Test row removal in middle
    listTableModel.onRowRemoved(3);
    assertEquals(testObject, listTableModel.getCell(4, 2));
  }

  /**
   * Test setting data.
   */
  public void testSetData() {
    // Prepare the table model
    String testObject = "TEST";
    ListTableModel listTableModel = (ListTableModel) getTableModel();
    
    // Clear the existing table model
    tableModel = null;

    // Set data over existing data
    listTableModel.onSetData(NUM_ROWS - 1, NUM_COLUMNS - 2, testObject);
    assertEquals(testObject, listTableModel.getCell(NUM_ROWS - 1,
        NUM_COLUMNS - 2));

    // Set data over a row and column that do not exist
    listTableModel.onSetData(NUM_ROWS + 2, NUM_COLUMNS + 2, testObject);
    assertEquals(testObject, listTableModel.getCell(NUM_ROWS + 2,
        NUM_COLUMNS + 2));
  }
  
  /**
   * All operations supported.
   */
  public void testUnsupportedOperations() {
  }
}
