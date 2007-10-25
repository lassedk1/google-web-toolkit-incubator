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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A controller that interfaces between a {@link TableModel} and one or more
 * views of the 2-dimensional data. This model does not include any internal
 * caching, and as such it will send a new request to the {@link TableModel}
 * with every request for rows.
 */
public class TableController {

  /**
   * The {@link TableControllerListener}s attached to this controller.
   */
  private ArrayList/* <TableControllerListener> */tableControllerListener;

  /**
   * The total number of rows that we predict the model can return.
   */
  private int numRows = -1;

  /**
   * The underlying table model.
   */
  private MutableTableModel tableModel;

  /**
   * The callback used to receive the data.
   */
  private Callback callback = new Callback() {
    public void onRowsReady(Request request, Response response) {
      setData(request.getStartRow(), response.iterator());
    }
  };

  /**
   * Constructor.
   * 
   * @param tableModel the underlying {@link MutableTableModel}
   */
  public TableController(MutableTableModel tableModel) {
    this.tableModel = tableModel;
  }

  /**
   * Add a new {@link TableControllerListener}.
   * 
   * @param listener the listener
   */
  public void addTableControllerListener(TableControllerListener listener) {
    if (tableControllerListener == null) {
      tableControllerListener = new ArrayList/* <TableControllerListener> */();
    }
    tableControllerListener.add(listener);
  }

  /**
   * Get the number of rows. If the number of rows is not known, return -1.
   * 
   * @return the number of rows
   */
  public int getNumRows() {
    return numRows;
  }

  /**
   * Get the underlying {@link TableModel}.
   * 
   * @return the table model
   */
  public TableModel getTableModel() {
    return tableModel;
  }
  
  /**
   * Insert a row of data.
   * 
   * @param beforeRow the row to insert
   */
  public void insertRow(int beforeRow) {
    // Allow a new row to be appended
    if (beforeRow != numRows) {
      checkRowBounds(beforeRow);
    }

    // Increment number of rows
    if (numRows >= 0) {
      numRows++;
    }

    // Fire listeners
    tableModel.onRowInserted(beforeRow);
    if (tableControllerListener != null) {
      Iterator it = tableControllerListener.iterator();
      while (it.hasNext()) {
        TableControllerListener listener = (TableControllerListener) it.next();
        listener.onRowInserted(beforeRow);
      }
    }
  }

  /**
   * Remove a row of data and decrement all rows after the removed row.
   * 
   * @param row the row to remove
   * @throws IndexOutOfBoundsException
   */
  public void removeRow(int row) {
    // Check row bounds
    checkRowBounds(row);
  
    // Decrement the number of rows
    if (numRows >= 0) {
      numRows--;
    }
  
    // Fire listeners
    tableModel.onRowRemoved(row);
    if (tableControllerListener != null) {
      Iterator it = tableControllerListener.iterator();
      while (it.hasNext()) {
        TableControllerListener listener = (TableControllerListener) it.next();
        listener.onRowRemoved(row);
      }
    }
  }

  /**
   * Remove a {@link TableControllerListener}.
   * 
   * @param listener the listener to remove
   */
  public void removeTableControllerListener(TableControllerListener listener) {
    if (tableControllerListener != null) {
      tableControllerListener.remove(listener);
    }
  }

  /**
   * Request new data to load.
   * 
   * @param firstRow the start row of the request
   * @param rowCount the number of rows of data to request
   */
  public void requestData(int firstRow, int rowCount) {
    tableModel.requestRows(firstRow, rowCount, callback);
  }

  /**
   * Set data in the controller.
   * 
   * @param row the row index
   * @param column the column index
   * @param data the data to set
   */
  public void setData(int row, int column, Object data) {
    tableModel.onSetData(row, column, data);
    if (tableControllerListener != null) {
      Iterator it = tableControllerListener.iterator();
      while (it.hasNext()) {
        TableControllerListener listener = (TableControllerListener) it.next();
        listener.onSetData(row, column, data);
      }
    }
  }

  /**
   * Set blocks of data in the controller.
   * 
   * This method takes an iterator of Collections, where each collection
   * represents one row of data starting with the first row.
   * 
   * @param firstRow the first row index
   * @param rows the 2D iterator of row data
   */
  public void setData(int firstRow, Iterator/* Iterator<Object> */rows) {
    // Fire listeners
    if (tableControllerListener != null) {
      Iterator it = tableControllerListener.iterator();
      while (it.hasNext()) {
        TableControllerListener listener = (TableControllerListener) it.next();
        listener.onSetData(firstRow, rows);
      }
    }
  }

  /**
   * Set the total number of rows. Pass in -1 if the number of rows is not
   * known.
   * 
   * @param numRows the total number of rows
   */
  public void setNumRows(int numRows) {
    this.numRows = Math.max(-1, numRows);

    // Fire listeners
    if (tableControllerListener != null) {
      Iterator it = tableControllerListener.iterator();
      while (it.hasNext()) {
        TableControllerListener listener = (TableControllerListener) it.next();
        listener.onNumRowsChanged(this.numRows);
      }
    }
  }

  /**
   * Verify that a row is within the row bounds.
   * 
   * @param row the row index to check
   * @throws IndexOutOfBoundsException
   */
  protected void checkRowBounds(int row) {
    if ((numRows >= 0) && ((row >= numRows) || (row < 0))) {
      throw new IndexOutOfBoundsException("Row index: " + row + ", Row size: "
          + numRows);
    }
  }
}
