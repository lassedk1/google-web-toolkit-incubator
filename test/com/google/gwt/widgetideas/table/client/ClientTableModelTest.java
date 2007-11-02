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

import com.google.gwt.widgetideas.table.client.TableModel.Callback;
import com.google.gwt.widgetideas.table.client.TableModel.Request;
import com.google.gwt.widgetideas.table.client.TableModel.Response;

import java.util.Iterator;

/**
 * Tests methods in the {@link ClientTableModel} class.
 */
public class ClientTableModelTest extends AbstractTableModelTest {
  /**
   * The number of columns in the underlying data.
   */
  public static final int NUM_COLUMNS = 7; 

  /**
   * The number of rows in the underlying data.
   */
  public static final int NUM_ROWS = 10; 
  
  /**
   * A row/column pairing used for confirming values.
   */
  protected class CellPair {
    /**
     * The row index.
     */
    private int row;

    /**
     * The column index.
     */
    private int column;

    /**
     * Constructor.
     * 
     * @param row the row index
     * @param column the column index
     */
    public CellPair(int row, int column) {
      this.row = row;
      this.column = column;
    }

    /**
     * Confirm that this pair equals the specified pair.
     * 
     * @param row the row index
     * @param column the column index
     */
    public void confirmPair(int row, int column) {
      assertEquals(this.row, row);
      assertEquals(this.column, column);
    }
  }

  /**
   * The table model to test.
   */
  private ClientTableModel tableModel = new ClientTableModel() {
    public Object getCell(int rowNum, int colNum) {
      if (colNum < NUM_COLUMNS && rowNum < NUM_ROWS) {
        return new CellPair(rowNum, colNum);
      }
      return null;
    }
  };

  /**
   * @see AbstractTableModelTest
   */
  public TableModel getTableModel() {
    return tableModel;
  }

  /**
   * Test requestRows method return values.
   */
  public void testRequestRows() {
    getTableModel().requestRows(0, NUM_ROWS, new Callback() {
      public void onRowsReady(Request request, Response response) {
        // Iterate the rows
        int row = 0;
        Iterator rowIt = response.iterator();
        while (rowIt.hasNext()) {
          // Iterate the columns
          int column = 0;
          Iterator colIt = (Iterator) rowIt.next();
          while (colIt.hasNext()) {
            // Check the cell values
            CellPair cellPair = (CellPair) colIt.next();
            cellPair.confirmPair(row, column);
            column++;
          }

          // Check the number of columns
          assertEquals(column, NUM_COLUMNS);
          row++;
        }

        // Check the number of rows
        assertEquals(row, NUM_ROWS);
      }
    });
  }

  /**
   * Test Unsupported Opertions that are not read only.
   */
  public void testUnsupportedOperations() {
    // Try calling unsupported operations
    try {
      getTableModel().onRowInserted(0);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
    }
    try {
      getTableModel().onRowRemoved(0);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
    }
    try {
      getTableModel().onSetData(0, 0, null);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
    }
  }
}
