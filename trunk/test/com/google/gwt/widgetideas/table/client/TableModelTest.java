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

import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortInfo;
import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortList;
import com.google.gwt.widgetideas.table.client.TableModel.Request;
import com.google.gwt.widgetideas.table.client.TableModel.Response;
import com.google.gwt.widgetideas.table.client.TableModel.SerializableResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Tests methods in the {@link TableModel} class.
 */
public class TableModelTest extends TableModelTestBase {
  /**
   * A {@link TableModelListener} used for testing.
   */
  private static class TestTableModelListener implements TableModelListener {
    /**
     * The cell that data was set into.
     */
    private int dataCell = -1;

    /**
     * The row that data was set into.
     */
    private int dataRow = -1;

    /**
     * The value that was set into the cell.
     */
    private Object dataValue = null;

    /**
     * The row that was last inserted.
     */
    private int insertedRow = -1;

    /**
     * The row that was last removed.
     */
    private int removedRow = -1;

    /**
     * The row count.
     */
    private int rowCount = TableModel.UNKNOWN_ROW_COUNT;

    public void assertDataValue(int row, int cell, Object value) {
      assertEquals(dataRow, row);
      assertEquals(dataCell, cell);
      assertEquals(dataValue, value);
    }

    public void assertRowCount(int rowCount) {
      assertEquals(rowCount, this.rowCount);
    }

    public void assertRowInserted(int row) {
      assertEquals(insertedRow, row);
    }

    public void assertRowRemoved(int row) {
      assertEquals(removedRow, row);
    }

    public void onRowCountChanged(int rowCount) {
      this.rowCount = rowCount;
    }

    public void onRowInserted(int beforeRow) {
      insertedRow = beforeRow;
    }

    public void onRowRemoved(int row) {
      removedRow = row;
    }

    public void onSetData(int row, int cell, Object data) {
      dataRow = row;
      dataCell = cell;
      dataValue = data;
    }
  }

  /**
   * @see TableModelTestBase#getTableModel(boolean)
   */
  @Override
  public <R> TableModel<R> getTableModel(boolean failureMode) {
    // Failure mode version
    if (failureMode) {
      return new TableModel<R>() {
        @Override
        public void requestRows(Request request, Callback<R> callback) {
          callback.onFailure(new Exception());
        }

        @Override
        protected boolean onRowInserted(int beforeRow) {
          return false;
        }

        @Override
        protected boolean onRowRemoved(int row) {
          return false;
        }

        @Override
        protected boolean onSetData(int row, int cell, Object data) {
          return false;
        }
      };
    }

    // Normal version
    return new TableModel<R>() {
      @Override
      public void requestRows(Request request, Callback<R> callback) {
        callback.onRowsReady(request, null);
      }

      @Override
      protected boolean onRowInserted(int beforeRow) {
        return true;
      }

      @Override
      protected boolean onRowRemoved(int row) {
        return true;
      }

      @Override
      protected boolean onSetData(int row, int cell, Object data) {
        return true;
      }
    };
  }

  /**
   * Test the listeners when the row count is unknown.
   */
  public void testListenersWithoutRowCount() {
    // Create a table model
    TableModel<String> tableModel = getTableModel(false);

    // Add some listeners
    TestTableModelListener l1 = new TestTableModelListener();
    TestTableModelListener l2 = new TestTableModelListener();
    TestTableModelListener l3 = new TestTableModelListener();
    tableModel.addTableModelListener(l1);
    tableModel.addTableModelListener(l2);
    tableModel.addTableModelListener(l3);
    tableModel.removeTableModelListener(l2);

    // Insert a row
    tableModel.insertRow(5);
    l1.assertRowInserted(5);
    l2.assertRowInserted(-1);
    l3.assertRowInserted(5);
    l1.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);
    l2.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);
    l3.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);

    // Remove a row
    tableModel.removeRow(8);
    l1.assertRowRemoved(8);
    l2.assertRowRemoved(-1);
    l3.assertRowRemoved(8);
    l1.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);
    l2.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);
    l3.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);

    // Set some data past the last row
    tableModel.setData(30, 2, "test");
    l1.assertDataValue(30, 2, "test");
    l2.assertDataValue(-1, -1, null);
    l3.assertDataValue(30, 2, "test");
    l1.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);
    l2.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);
    l3.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);
  }

  /**
   * Test the listeners when the row count is known.
   */
  public void testListenersWithRowCount() {
    // Create a table model
    TableModel<String> tableModel = getTableModel(false);

    // Add some listeners
    TestTableModelListener l1 = new TestTableModelListener();
    TestTableModelListener l2 = new TestTableModelListener();
    TestTableModelListener l3 = new TestTableModelListener();
    tableModel.addTableModelListener(l1);
    tableModel.addTableModelListener(l2);
    tableModel.addTableModelListener(l3);
    tableModel.removeTableModelListener(l2);

    // Set the row count
    tableModel.setRowCount(22);
    l1.assertRowCount(22);
    l2.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);
    l3.assertRowCount(22);

    // Insert a row
    tableModel.insertRow(5);
    l1.assertRowInserted(5);
    l2.assertRowInserted(-1);
    l3.assertRowInserted(5);
    l1.assertRowCount(23);
    l2.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);
    l3.assertRowCount(23);

    // Remove a row
    tableModel.removeRow(8);
    l1.assertRowRemoved(8);
    l2.assertRowRemoved(-1);
    l3.assertRowRemoved(8);
    l1.assertRowCount(22);
    l2.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);
    l3.assertRowCount(22);

    // Set some data past the last row
    tableModel.setData(30, 2, "test");
    l1.assertDataValue(30, 2, "test");
    l2.assertDataValue(-1, -1, null);
    l3.assertDataValue(30, 2, "test");
    l1.assertRowCount(31);
    l2.assertRowCount(TableModel.UNKNOWN_ROW_COUNT);
    l3.assertRowCount(31);
  }

  /**
   * Test the accessors in {@link Request}.
   */
  public void testRequest() {
    // Without sort index
    Request request1 = new Request(10, 20);
    assertEquals(request1.getStartRow(), 10);
    assertEquals(request1.getNumRows(), 20);
    assertNull(request1.getColumnSortList());

    // With sort index
    ColumnSortList sortList = new ColumnSortList();
    Request request2 = new Request(5, 10, sortList);
    assertEquals(request2.getStartRow(), 5);
    assertEquals(request2.getNumRows(), 10);
    assertEquals(request2.getColumnSortList(), sortList);
  }

  /**
   * Test the row values in a {@link Response}.
   */
  public void testResponse() {
    // Create row values
    List<String> rowValues = new ArrayList<String>();
    for (int i = 0; i < 10; i++) {
      rowValues.add("value" + i);
    }

    // Create a Response
    Response<String> response = new Response<String>(rowValues) {
      @Override
      public Iterator<Iterator<Object>> getIterator() {
        return null;
      }
    };

    // Get the rowValues
    assertEquals(rowValues, response.getRowValues());
  }

  /**
   * Test the row values in a {@link Response}.
   */
  public void testSerializableResponse() {
    // Create row values and cell data
    List<String> rowValues = new ArrayList<String>();
    Collection<Collection<Serializable>> cellData = new ArrayList<Collection<Serializable>>();
    for (int row = 0; row < 10; row++) {
      rowValues.add("value" + row);
      List<Serializable> rowData = new ArrayList<Serializable>();
      for (int cell = 0; cell < 4; cell++) {
        rowData.add(row + ":" + cell);
      }
      cellData.add(rowData);
    }

    // Create a Response
    Response<String> response = new SerializableResponse<String>(cellData,
        rowValues);

    // Get the rowValues
    assertEquals(rowValues, response.getRowValues());

    // Get the data
    Iterator<Iterator<Object>> rowIt = response.getIterator();
    int row = 0;
    while (rowIt.hasNext()) {
      Iterator<Object> cellIt = rowIt.next();
      int cell = 0;
      while (cellIt.hasNext()) {
        assertEquals(cellIt.next(), row + ":" + cell);
        cell++;
      }
      assertEquals(4, cell);
      row++;
    }
    assertEquals(10, row);
  }

  /**
   * Test the {@link ColumnSortInfo} class.
   */
  public void testSortInfo() {
    // Test constructor
    ColumnSortInfo sortInfo = new ColumnSortInfo(3, true);
    assertEquals(3, sortInfo.getColumn());
    assertTrue(sortInfo.isAscending());

    // Test modifiers
    sortInfo.setColumn(5);
    sortInfo.setAscending(false);
    assertEquals(5, sortInfo.getColumn());
    assertFalse(sortInfo.isAscending());

    // Test equals
    assertTrue(sortInfo.equals(new ColumnSortInfo(5, false)));
    assertFalse(sortInfo.equals(new ColumnSortInfo(4, false)));
    assertFalse(sortInfo.equals(new ColumnSortInfo(5, true)));
  }

  /**
   * Test the {@link ColumnSortList} class.
   */
  public void testSortList() {
    // Create a new list
    ColumnSortList sortList = new ColumnSortList();
    assertEquals(0, sortList.size());

    // Add one item
    sortList.add(new ColumnSortInfo(4, false));
    assertEquals(1, sortList.size());
    assertEquals(4, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());
    assertTrue(sortList.getPrimaryColumnSortInfo().equals(
        new ColumnSortInfo(4, false)));

    // Add more items
    sortList.add(new ColumnSortInfo(6, false));
    sortList.add(new ColumnSortInfo(8, true));
    sortList.add(new ColumnSortInfo(10, true));
    assertEquals(4, sortList.size());
    assertEquals(10, sortList.getPrimaryColumn());
    assertTrue(sortList.isPrimaryAscending());

    // Check all of the entries in the sort lise
    int count = 0;
    for (ColumnSortInfo info : sortList) {
      switch (count) {
        case 0:
          assertTrue(info.equals(new ColumnSortInfo(10, true)));
          break;
        case 1:
          assertTrue(info.equals(new ColumnSortInfo(8, true)));
          break;
        case 2:
          assertTrue(info.equals(new ColumnSortInfo(6, false)));
          break;
        case 3:
          assertTrue(info.equals(new ColumnSortInfo(4, false)));
          break;
      }
      count++;
    }

    // Add an existing item
    sortList.add(new ColumnSortInfo(6, false));
    assertEquals(4, sortList.size());
    assertEquals(6, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());

    // Add an existing column, different order
    sortList.add(new ColumnSortInfo(8, false));
    assertEquals(4, sortList.size());
    assertEquals(8, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());

    // Compare two lists
    ColumnSortList sortList1 = new ColumnSortList();
    sortList1.add(new ColumnSortInfo(1, true));
    sortList1.add(new ColumnSortInfo(2, false));
    sortList1.add(new ColumnSortInfo(3, true));
    ColumnSortList sortList2 = new ColumnSortList();
    sortList2.add(new ColumnSortInfo(1, true));
    sortList2.add(new ColumnSortInfo(2, false));
    sortList2.add(new ColumnSortInfo(3, true));
    ColumnSortList sortList3 = new ColumnSortList();
    sortList3.add(new ColumnSortInfo(1, true));
    sortList3.add(new ColumnSortInfo(4, false));
    sortList3.add(new ColumnSortInfo(3, true));
    ColumnSortList sortList4 = new ColumnSortList();
    sortList4.add(new ColumnSortInfo(1, true));
    sortList4.add(new ColumnSortInfo(2, true));
    sortList4.add(new ColumnSortInfo(3, true));
    assertTrue(sortList1.equals(sortList2));
    assertFalse(sortList1.equals(sortList3));
    assertFalse(sortList1.equals(sortList4));
    assertFalse(sortList3.equals(sortList4));
  }
}
