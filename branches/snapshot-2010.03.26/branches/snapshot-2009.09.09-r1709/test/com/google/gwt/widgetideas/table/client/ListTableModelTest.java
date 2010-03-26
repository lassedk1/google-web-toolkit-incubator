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
package com.google.gwt.widgetideas.table.client;

import com.google.gwt.widgetideas.table.client.TableModelHelper.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests methods in the {@link ListTableModel} class.
 * 
 * @deprecated Replaced by
 *             {@link com.google.gwt.gen2.table.client.ListTableModelTest}
 */
@Deprecated
public class ListTableModelTest extends ClientTableModelTest {
  /**
   * @see TableModelTestBase#getTableModel(boolean)
   */
  @Override
  public <R> TableModel<R> getTableModel(boolean failureMode) {
    // Create the row data
    List<List<Object>> rowList = new ArrayList<List<Object>>();
    for (int row = 0; row < NUM_ROWS; row++) {
      ArrayList<Object> columnList = new ArrayList<Object>();
      rowList.add(columnList);
      for (int column = 0; column < NUM_COLUMNS; column++) {
        columnList.add(new CellPair(row, column));
      }
    }

    // Failure mode version
    if (failureMode) {
      return new ListTableModel<R>(rowList) {
        @Override
        public void requestRows(Request request, Callback<R> callback) {
          callback.onFailure(new Exception());
        }
      };
    }

    // Create normal version
    return new ListTableModel<R>(rowList);
  }

  /**
   * Test insertion and deletion of rows.
   */
  public void testRowInsertion() {
    // Prepare the table model
    String testObject = "TEST";
    ListTableModel<Object> tableModel = (ListTableModel<Object>) getTableModel(false);

    // Test row insertion at beginning
    tableModel.setData(0, 2, testObject);
    tableModel.insertRow(0);
    assertEquals(NUM_ROWS + 1, tableModel.getRowCount());
    assertNull(tableModel.getCell(0, 0));
    assertEquals(testObject, tableModel.getCell(1, 2));
    tableModel.setData(0, 4, testObject);
    assertEquals(testObject, tableModel.getCell(0, 4));
    assertEquals(testObject, tableModel.getCell(1, 2));

    // Test row removal at beginning
    tableModel.removeRow(0);
    assertEquals(NUM_ROWS, tableModel.getRowCount());
    assertEquals(testObject, tableModel.getCell(0, 2));

    // Test row insertion in middle
    tableModel.setData(4, 2, testObject);
    tableModel.insertRow(3);
    assertEquals(NUM_ROWS + 1, tableModel.getRowCount());
    assertNull(tableModel.getCell(3, 0));
    assertEquals(testObject, tableModel.getCell(5, 2));
    tableModel.setData(3, 4, testObject);
    assertEquals(testObject, tableModel.getCell(3, 4));
    assertEquals(testObject, tableModel.getCell(5, 2));

    // Test row removal in middle
    tableModel.removeRow(3);
    assertEquals(NUM_ROWS, tableModel.getRowCount());
    assertEquals(testObject, tableModel.getCell(4, 2));
  }

  /**
   * Test setting data.
   */
  public void testSetData() {
    // Prepare the table model
    String testObject = "TEST";
    ListTableModel<Object> tableModel = (ListTableModel<Object>) getTableModel(false);

    // Set data over existing data
    tableModel.setData(NUM_ROWS - 1, NUM_COLUMNS - 2, testObject);
    assertEquals(testObject, tableModel.getCell(NUM_ROWS - 1, NUM_COLUMNS - 2));
    assertEquals(NUM_ROWS, tableModel.getRowCount());

    // Set data over a row and column that do not exist
    tableModel.setData(NUM_ROWS + 2, NUM_COLUMNS + 2, testObject);
    assertEquals(testObject, tableModel.getCell(NUM_ROWS + 2, NUM_COLUMNS + 2));
    assertEquals(NUM_ROWS + 3, tableModel.getRowCount());
  }
}
