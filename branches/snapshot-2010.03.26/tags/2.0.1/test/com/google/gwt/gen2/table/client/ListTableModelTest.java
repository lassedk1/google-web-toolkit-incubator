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

import com.google.gwt.gen2.table.client.TableModelHelper.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests methods in the {@link ListTableModel} class.
 */
public class ListTableModelTest extends ClientTableModelTest {
  @Override
  public ListTableModel getTableModel(boolean failureMode) {
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
      return new ListTableModel(rowList) {
        @Override
        public void requestRows(Request request, Callback<List<Object>> callback) {
          callback.onFailure(new Exception());
        }
      };
    }

    // Create normal version
    return new ListTableModel(rowList);
  }

  /**
   * Test insertion and deletion of rows.
   */
  public void testRowInsertion() {
    // Test row insertion at beginning
    {
      // Set the row value of the table model
      ListTableModel tableModel = getTableModel(false);
      List<Object> rowValue0 = new ArrayList<Object>();
      rowValue0.add("a");
      rowValue0.add("b");
      rowValue0.add("c");
      rowValue0.add("d");
      tableModel.setRowValue(0, rowValue0);

      // Insert a row before the new row value
      tableModel.insertRow(0);
      assertEquals(NUM_ROWS + 1, tableModel.getRowCount());
      assertEquals("c", tableModel.getCellValue(1, 2));
      assertNull(tableModel.getCellValue(0, 0));
    }

    // Test row removal at beginning
    {
      // Remove the first row
      ListTableModel tableModel = getTableModel(false);
      assertEquals(NUM_ROWS, tableModel.getRowCount());
      tableModel.removeRow(0);
      assertEquals(NUM_ROWS - 1, tableModel.getRowCount());

      // Set the row value at row 1
      List<Object> rowValue = new ArrayList<Object>();
      rowValue.add("a");
      rowValue.add("b");
      rowValue.add("c");
      rowValue.add("d");
      tableModel.setRowValue(1, rowValue);
      assertEquals("b", tableModel.getCellValue(1, 1));

      // Remove the first row
      tableModel.removeRow(0);
      assertEquals(NUM_ROWS - 2, tableModel.getRowCount());
      assertEquals("b", tableModel.getCellValue(0, 1));
    }

    // Test row insertion in middle
    {
      // Set the row value at row 4
      ListTableModel tableModel = getTableModel(false);
      List<Object> rowValue = new ArrayList<Object>();
      rowValue.add("a");
      rowValue.add("b");
      rowValue.add("c");
      rowValue.add("d");
      tableModel.setRowValue(4, rowValue);
      assertEquals("b", tableModel.getCellValue(4, 1));

      // Insert a row in the middle
      tableModel.insertRow(3);
      assertEquals(NUM_ROWS + 1, tableModel.getRowCount());
      assertEquals("b", tableModel.getCellValue(5, 1));
    }

    // Test row removal in middle
    {
      // Set the row value at row 4
      ListTableModel tableModel = getTableModel(false);
      List<Object> rowValue = new ArrayList<Object>();
      rowValue.add("a");
      rowValue.add("b");
      rowValue.add("c");
      rowValue.add("d");
      tableModel.setRowValue(4, rowValue);
      assertEquals("b", tableModel.getCellValue(4, 1));

      // Remove a row in the middle
      tableModel.removeRow(3);
      assertEquals(NUM_ROWS - 1, tableModel.getRowCount());
      assertEquals("b", tableModel.getCellValue(3, 1));
    }
  }

  /**
   * Test setting data.
   */
  public void testSetData() {
    // Set data over existing data
    {
      ListTableModel tableModel = getTableModel(false);
      List<Object> rowValue = new ArrayList<Object>();
      rowValue.add("a");
      rowValue.add("b");
      rowValue.add("c");
      rowValue.add("d");
      rowValue.add("e");
      rowValue.add("f");
      tableModel.setRowValue(NUM_ROWS - 1, rowValue);
      assertEquals("b", tableModel.getCellValue(NUM_ROWS - 1, 1));
      assertEquals(NUM_ROWS, tableModel.getRowCount());
    }

    // Set data over a row and column that do not exist
    {
      ListTableModel tableModel = getTableModel(false);
      List<Object> rowValue = new ArrayList<Object>();
      rowValue.add("a");
      rowValue.add("b");
      rowValue.add("c");
      rowValue.add("d");
      rowValue.add("e");
      rowValue.add("f");
      tableModel.setRowValue(NUM_ROWS + 2, rowValue);
      assertEquals("b", tableModel.getCellValue(NUM_ROWS + 2, 1));
      assertEquals(NUM_ROWS + 3, tableModel.getRowCount());
    }
  }
}
