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

import com.google.gwt.gen2.base.client.Gen2TestBase;
import com.google.gwt.gen2.table.client.TableModel.Callback;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortInfo;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortList;
import com.google.gwt.gen2.table.client.TableModelHelper.Request;
import com.google.gwt.gen2.table.client.TableModelHelper.Response;
import com.google.gwt.gen2.table.event.client.RowCountChangeEvent;
import com.google.gwt.gen2.table.event.client.RowCountChangeHandler;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

/**
 * Tests methods in the {@link TableModel} class.
 */
public abstract class TableModelTestBase extends Gen2TestBase {
  /**
   * A row value used for testing that can be serialized.
   */
  protected static class CustomRowValue implements IsSerializable {
    private List<String> cellValues;

    /**
     * Create a new {@link CustomRowValue}.
     * 
     * @param cellValues the list of cell values
     */
    public CustomRowValue(List<String> cellValues) {
      this.cellValues = cellValues;
    }

    public String getCellValue(int index) {
      return cellValues.get(index);
    }
  }

  /**
   * A {@link Callback} used for testing.
   * 
   * @param <R> the data type of the row values
   */
  protected static class TestCallback<R> implements Callback<R> {
    /**
     * A boolean indicating that this callback has been executed.
     */
    private boolean executed = false;

    /**
     * Expected num rows in Request.
     */
    private int numRows;

    /**
     * A boolean indicating that a failure occurred.
     */
    private boolean failed = false;

    /**
     * Column sort info.
     */
    private ColumnSortList sortList;

    /**
     * Expected start row in Request.
     */
    private int startRow;

    /**
     * Constructor.
     * 
     * @param startRow expected start row in request
     * @param numRows expected num rows in request
     * @param sortList the column sort information
     */
    public TestCallback(int startRow, int numRows, ColumnSortList sortList) {
      this.startRow = startRow;
      this.numRows = numRows;
      this.sortList = sortList;
    }

    /**
     * @return the number of rows.
     */
    public int getNumRows() {
      return numRows;
    }

    /**
     * @return the start row
     */
    public int getStartRow() {
      return startRow;
    }

    /**
     * Check if this callback has been executed.
     * 
     * @return true if executed
     */
    public boolean isExecuted() {
      return executed;
    }

    /**
     * Check if this callback failed.
     * 
     * @return true if failed
     */
    public boolean isFailed() {
      return failed;
    }

    public void onFailure(Throwable caught) {
      failed = true;
    }

    public void onRowsReady(Request request, Response<R> response) {
      executed = true;
      assertEquals(request.getStartRow(), startRow);
      assertEquals(request.getNumRows(), numRows);
      if (sortList == null) {
        if (request.getColumnSortList() != null) {
          fail("Request has a sortList, but none expected");
        }
      } else {
        assertTrue(sortList.equals(request.getColumnSortList()));
      }
    }
  }

  /**
   * A {@link RowCountChangeEvent} used for testing.
   */
  protected static class TestRowCountChangeHandler implements RowCountChangeHandler {
    private RowCountChangeEvent lastEvent = null;

    public void assertNewRowCount(int rowCount) {
      assertNotNull(lastEvent);
      assertEquals(rowCount, lastEvent.getNewRowCount());
    }

    public void assertOldRowCount(int rowCount) {
      assertNotNull(lastEvent);
      assertEquals(rowCount, lastEvent.getOldRowCount());
    }

    public void onRowCountChange(RowCountChangeEvent event) {
      lastEvent = event;
    }
  }

  @Override
  public String getModuleName() {
    return "com.google.gwt.gen2.table.TableSuite";
  }

  /**
   * Get an instance of the table model.
   * 
   * @param failureMode if true, all requests should return an error
   * @return the table model
   */
  public abstract <R> TableModel<R> getTableModel(boolean failureMode);

  /**
   * Test requestRows method.
   */
  public void testRequestRows() {
    // Get the table model
    TableModel<CustomRowValue> tableModel = getTableModel(false);

    // Request some rows without sorting
    TestCallback<CustomRowValue> callback1 = new TestCallback<CustomRowValue>(10, 20, null);
    Request request1 = new Request(10, 20);
    tableModel.requestRows(request1, callback1);
    assertTrue(callback1.isExecuted());
    assertFalse(callback1.isFailed());

    // Request some rows with sorting
    ColumnSortList sortList = new ColumnSortList();
    sortList.add(new ColumnSortInfo(5, true));
    TestCallback<CustomRowValue> callback2 = new TestCallback<CustomRowValue>(5, 6, sortList);
    Request request2 = new Request(5, 6, sortList);
    tableModel.requestRows(request2, callback2);
    assertTrue(callback2.isExecuted());
    assertFalse(callback2.isFailed());

    // Request some rows with sorting descending
    sortList.add(new ColumnSortInfo(5, false));
    TestCallback<CustomRowValue> callback3 = new TestCallback<CustomRowValue>(5, 6, sortList);
    Request request3 = new Request(5, 6, sortList);
    tableModel.requestRows(request3, callback3);
    assertTrue(callback3.isExecuted());
    assertFalse(callback3.isFailed());

    // Request some rows and fail to return them
    TableModel<CustomRowValue> failureModel = getTableModel(true);
    TestCallback<CustomRowValue> callback4 = new TestCallback<CustomRowValue>(-1, -1, null);
    Request request4 = new Request(5, 6, sortList);
    failureModel.requestRows(request4, callback4);
    assertFalse(callback4.isExecuted());
    assertTrue(callback4.isFailed());
  }

  public void testRowCountChangeHandler() {
    // Create a table model
    TableModel<Object> tableModel = getTableModel(false);

    // Add some listeners
    TestRowCountChangeHandler handler = new TestRowCountChangeHandler();
    tableModel.addRowCountChangeHandler(handler);

    // Set the row count
    int rowCount = tableModel.getRowCount() + 100;
    tableModel.setRowCount(rowCount);
    handler.assertNewRowCount(rowCount);

    // Change the row count
    int newRowCount = rowCount + 50;
    tableModel.setRowCount(newRowCount);
    handler.assertOldRowCount(rowCount);
    handler.assertNewRowCount(newRowCount);
  }
}
