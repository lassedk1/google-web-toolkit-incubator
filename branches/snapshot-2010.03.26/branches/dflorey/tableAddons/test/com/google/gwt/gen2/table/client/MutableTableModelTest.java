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

import com.google.gwt.gen2.table.event.client.RowInsertionEvent;
import com.google.gwt.gen2.table.event.client.RowInsertionHandler;
import com.google.gwt.gen2.table.event.client.RowRemovalEvent;
import com.google.gwt.gen2.table.event.client.RowRemovalHandler;
import com.google.gwt.gen2.table.event.client.RowValueChangeEvent;
import com.google.gwt.gen2.table.event.client.RowValueChangeHandler;
import com.google.gwt.gen2.table.shared.Request;

/**
 * Tests methods in the {@link MutableTableModel} class.
 */
public class MutableTableModelTest extends TableModelTestBase {
  /**
   * A custom handler used for testing.
   */
  protected static class TestRowInsertionHandler implements RowInsertionHandler {
    private RowInsertionEvent lastEvent = null;

    public void assertRowIndex(int rowIndex) {
      assertNotNull(lastEvent);
      assertEquals(rowIndex, lastEvent.getRowIndex());
    }

    public void onRowInsertion(RowInsertionEvent event) {
      lastEvent = event;
    }
  }

  /**
   * A custom handler used for testing.
   */
  protected static class TestRowRemovalHandler implements RowRemovalHandler {
    private RowRemovalEvent lastEvent = null;

    public void assertRowIndex(int rowIndex) {
      assertNotNull(lastEvent);
      assertEquals(rowIndex, lastEvent.getRowIndex());
    }

    public void onRowRemoval(RowRemovalEvent event) {
      lastEvent = event;
    }
  }

  /**
   * A custom handler used for testing.
   */
  protected static class TestRowValueChangeHandler<RowType> implements
      RowValueChangeHandler<RowType> {
    private RowValueChangeEvent<RowType> lastEvent = null;

    public void assertRowIndex(int rowIndex) {
      assertNotNull(lastEvent);
      assertEquals(rowIndex, lastEvent.getRowIndex());
    }

    public void assertRowValue(RowType rowValue) {
      assertNotNull(lastEvent);
      assertEquals(rowValue, lastEvent.getRowValue());
    }

    public void onRowValueChange(RowValueChangeEvent<RowType> event) {
      lastEvent = event;
    }
  }

  @Override
  public <R> MutableTableModel<R> getTableModel(boolean failureMode) {
    // Failure mode version
    if (failureMode) {
      return new MutableTableModel<R>() {
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
        protected boolean onSetRowValue(int row, R rowValue) {
          return false;
        }
      };
    }

    // Normal version
    return new MutableTableModel<R>() {
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
      protected boolean onSetRowValue(int row, R rowValue) {
        return true;
      }
    };
  }

  public void testRowInsertionHandlerWithoutRowCount() {
    // Create a table model
    MutableTableModel<Object> tableModel = getTableModel(false);

    // Add some listeners
    TestRowInsertionHandler handler = new TestRowInsertionHandler();
    tableModel.addRowInsertionHandler(handler);

    // Insert a row
    {
      tableModel.insertRow(5);
      handler.assertRowIndex(5);
      assertEquals(TableModel.UNKNOWN_ROW_COUNT, tableModel.getRowCount());
    }
  }

  public void testRowInsertionHandlerWithRowCount() {
    // Create a table model
    MutableTableModel<Object> tableModel = getTableModel(false);
    tableModel.setRowCount(50);

    // Add some listeners
    TestRowInsertionHandler handler = new TestRowInsertionHandler();
    tableModel.addRowInsertionHandler(handler);

    // Insert a row
    {
      tableModel.insertRow(5);
      handler.assertRowIndex(5);
      assertEquals(51, tableModel.getRowCount());
    }
  }

  public void testRowRemovalHandlerWithoutRowCount() {
    // Create a table model
    MutableTableModel<Object> tableModel = getTableModel(false);

    // Add some listeners
    TestRowRemovalHandler handler = new TestRowRemovalHandler();
    tableModel.addRowRemovalHandler(handler);

    // Insert a row
    {
      tableModel.removeRow(5);
      handler.assertRowIndex(5);
      assertEquals(TableModel.UNKNOWN_ROW_COUNT, tableModel.getRowCount());
    }
  }

  public void testRowRemovalHandlerWithRowCount() {
    // Create a table model
    MutableTableModel<Object> tableModel = getTableModel(false);
    tableModel.setRowCount(50);

    // Add some listeners
    TestRowRemovalHandler handler = new TestRowRemovalHandler();
    tableModel.addRowRemovalHandler(handler);

    // Insert a row
    {
      tableModel.removeRow(5);
      handler.assertRowIndex(5);
      assertEquals(49, tableModel.getRowCount());
    }
  }

  public void testRowValueChangeHandlerWithoutRowCount() {
    // Create a table model
    MutableTableModel<Object> tableModel = getTableModel(false);

    // Add some listeners
    TestRowValueChangeHandler<Object> handler = new TestRowValueChangeHandler<Object>();
    tableModel.addRowValueChangeHandler(handler);

    // Set the row value
    {
      Object newValue = "newValue";
      tableModel.setRowValue(0, newValue);
      handler.assertRowIndex(0);
      handler.assertRowValue(newValue);
      assertEquals(TableModel.UNKNOWN_ROW_COUNT, tableModel.getRowCount());
    }

    // Set the row value passed the last index
    {
      Object newValue = "newValue2";
      tableModel.setRowValue(100, newValue);
      handler.assertRowIndex(100);
      handler.assertRowValue(newValue);
      assertEquals(TableModel.UNKNOWN_ROW_COUNT, tableModel.getRowCount());
    }
  }

  public void testRowValueChangeHandlerWithRowCount() {
    // Create a table model
    MutableTableModel<Object> tableModel = getTableModel(false);
    tableModel.setRowCount(50);

    // Add some listeners
    TestRowValueChangeHandler<Object> handler = new TestRowValueChangeHandler<Object>();
    tableModel.addRowValueChangeHandler(handler);

    // Set the row value
    {
      Object newValue = "newValue";
      tableModel.setRowValue(0, newValue);
      handler.assertRowIndex(0);
      handler.assertRowValue(newValue);
      assertEquals(50, tableModel.getRowCount());
    }

    // Set the row value passed the last index
    {
      Object newValue = "newValue2";
      tableModel.setRowValue(100, newValue);
      handler.assertRowIndex(100);
      handler.assertRowValue(newValue);
      assertEquals(101, tableModel.getRowCount());
    }
  }
}
