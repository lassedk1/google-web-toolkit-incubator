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

import com.google.gwt.widgetideas.table.client.TableModel.Request;
import com.google.gwt.widgetideas.table.client.TableModel.Response;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A model defining a 2-dimensional grid of data. This model does not include
 * any internal caching, and as such it will send a new request to the
 * {@link TableModel} with every request for rows.
 */
public class TableController {

  /**
   * The {@link TableControllerListener}s attached to this model.
   */
  private ArrayList/* <GridModelListener> */gridModelListeners;

  /**
   * The total number of rows in this model.
   */
  private int numRows = -1;

  /**
   * The table oracle.
   */
  private MutableTableModel oracle;

  /**
   * The callback used to receive the data.
   */
  private TableModel.Callback callback = new TableModel.Callback() {
    public void onRowsReady(Request request, Response response) {
      setData(request.getStartRow(), response.getRows());
    }
  };

  /**
   * Constructor.
   */
  public TableController(MutableTableModel oracle) {
    this.oracle = oracle;
  }

  /**
   * Add a new grid model listener.
   * 
   * @param listener the listener
   */
  public void addGridModelListener(TableControllerListener listener) {
    if (gridModelListeners == null) {
      gridModelListeners = new ArrayList/* <GridModelListener> */();
    }
    gridModelListeners.add(listener);
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
    oracle.onRowInserted(beforeRow);
    if (gridModelListeners != null) {
      Iterator it = gridModelListeners.iterator();
      while (it.hasNext()) {
        TableControllerListener listener = (TableControllerListener) it.next();
        listener.onRowInserted(beforeRow);
      }
    }
  }

  /**
   * Remove a grid model listener.
   * 
   * @param listener the listener to remove
   */
  public void removeGridModelListener(TableControllerListener listener) {
    if (gridModelListeners != null) {
      gridModelListeners.remove(listener);
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
    oracle.onRowRemoved(row);
    if (gridModelListeners != null) {
      Iterator it = gridModelListeners.iterator();
      while (it.hasNext()) {
        TableControllerListener listener = (TableControllerListener) it.next();
        listener.onRowRemoved(row);
      }
    }
  }

  /**
   * Request new data to load.
   * 
   * @param firstRow the start row of the request
   * @param rowCount the number of rows of data to request
   */
  public void requestData(int firstRow, int rowCount) {
    oracle.requestRows(firstRow, rowCount, callback);
  }

  /**
   * Set data in the grid.
   * 
   * @param row the row index
   * @param column the column index
   * @param data the data to set
   */
  public void setData(int row, int column, Object data) {
    oracle.onSetData(row, column, data);
    if (gridModelListeners != null) {
      Iterator it = gridModelListeners.iterator();
      while (it.hasNext()) {
        TableControllerListener listener = (TableControllerListener) it.next();
        listener.onSetData(row, column, data);
      }
    }
  }

  /**
   * Set blocks of data in the grid.
   * 
   * This method takes an iterator of Collections, where each collection
   * represents one row of data starting with the first row.
   * 
   * @param firstRow the first row index
   * @param rows the 2D iterator of row data
   */
  public void setData(int firstRow, Iterator/* Iterator<Object> */rows) {
    // Fire listeners
    if (gridModelListeners != null) {
      Iterator it = gridModelListeners.iterator();
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
    if (gridModelListeners != null) {
      Iterator it = gridModelListeners.iterator();
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
