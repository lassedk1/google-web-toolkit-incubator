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

import com.google.gwt.widgetideas.table.client.TableModel.Callback;
import com.google.gwt.widgetideas.table.client.TableModel.Request;
import com.google.gwt.widgetideas.table.client.TableModel.Response;

import java.util.Iterator;

/**
 * Tests methods in the {@link ClientTableModel} class.
 */
public class ClientTableModelTest extends TableModelTestBase {
  /**
   * The number of columns in the underlying data.
   */
  public static final int NUM_COLUMNS = 7;

  /**
   * The number of rows in the underlying data.
   */
  public static final int NUM_ROWS = 10;

  /**
   * @see TableModelTestBase#getTableModel(boolean)
   */
  @Override
  public <R> TableModel<R> getTableModel(boolean failureMode) {
    // Failure mode version
    if (failureMode) {
      return new ClientTableModel<R>() {
        @Override
        public Object getCell(int rowNum, int colNum) {
          return null;
        }

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
    return new ClientTableModel<R>() {
      @Override
      public Object getCell(int rowNum, int colNum) {
        if (colNum < NUM_COLUMNS && rowNum < NUM_ROWS) {
          return new CellPair(rowNum, colNum);
        }
        return null;
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
   * Test requestRows method return values.
   */
  public void testRequestRowsValues() {
    // Create the table model
    TableModel<String> tableModel = getTableModel(false);

    // Create a callback for testing
    Callback<String> callback = new Callback<String>() {
      public void onFailure(Throwable caught) {
      }

      public void onRowsReady(Request request, Response<String> response) {
        // Iterate the rows
        int row = 0;
        Iterator<Iterator<Object>> rowIt = response.getIterator();
        while (rowIt.hasNext()) {
          // Iterate the columns
          int column = 0;
          Iterator<Object> colIt = rowIt.next();
          while (colIt.hasNext()) {
            // Check the cell values
            CellPair cellPair = (CellPair) colIt.next();
            cellPair.assertPair(row, column);
            column++;
          }

          // Check the number of columns
          assertEquals(NUM_COLUMNS, column);
          row++;
        }

        // Check the number of rows
        assertEquals(NUM_ROWS, row);
      }
    };

    // Send a request
    Request request = new Request(0, NUM_ROWS);
    tableModel.requestRows(request, callback);
  }
}
