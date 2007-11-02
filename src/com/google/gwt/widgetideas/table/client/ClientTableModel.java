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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A {@link ClientTableModel} used when the data source can be accessed
 * synchronously.
 */
public abstract class ClientTableModel extends TableModel {
  /**
   * Error message for non-read only operations.
   */
  public static final String READ_ONLY_ERROR = "TableModel is read only.";

  /**
   * A local {@link ListTableModel} response.
   */
  public static class ClientResponse extends Response {

    /**
     * An iterator over the rows of data.
     */
    private Iterator/* <Iterator<Object>> */rows = null;

    /**
     * Constructor.
     */
    public ClientResponse() {
    }

    /**
     * Constructor. Create a response with the data to enter into each cell of
     * each row.
     * 
     * @param rows the data for the table
     */
    public ClientResponse(Iterator/* <Iterator<Object>> */rows) {
      this.rows = rows;
    }

    /**
     * Get the rows data.
     * 
     * @return the rows data
     */
    public Iterator/* <Iterator<Object>> */iterator() {
      return rows;
    }
  }

  /**
   * An iterator over a single row of data.
   */
  private class ColumnIterator implements Iterator {
    /**
     * The row index that we are iterating.
     */
    private int row;

    /**
     * The current column index.
     */
    private int curCell = 0;

    /**
     * The next value to return.
     */
    private Object next = null;

    /**
     * Constructor.
     * 
     * @param row the row index
     */
    public ColumnIterator(int row) {
      this.row = row;
      next = getCell(row, curCell);
    }

    public boolean hasNext() {
      return (next != null);
    }

    public Object next() {
      // Next not available
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      // Save the next data
      Object current = next;
      curCell++;
      next = getCell(row, curCell);
      
      // Return the current data
      return current;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * An iterator over rows of data.
   */
  private class RowIterator implements Iterator {
    /**
     * The current row that is being iterated.
     */
    private int curRow;

    /**
     * The maximum row that can be iterated.
     */
    private int maxRow;

    /**
     * Constructor.
     * 
     * @param request the request for rows
     */
    public RowIterator(Request request) {
      curRow = request.getStartRow();
      if (request.getNumRows() == -1) {
        maxRow = Integer.MAX_VALUE;
      } else {
        maxRow = curRow + request.getNumRows();
      }
    }

    public boolean hasNext() {
      return (curRow < maxRow);
    }

    public Object next() {
      // Next not available
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      // Get next column iterator
      ColumnIterator colIt = new ColumnIterator(curRow);
      curRow++;
      return colIt;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Get the value for a given cell. Return null if no more values are
   * available.
   * 
   * @param rowNum the row index
   * @param colNum the column index
   * @return the value at the given row and column
   */
  public abstract Object getCell(int rowNum, int colNum);

  /**
   * Event fired when a row is inserted.
   * 
   * @param beforeRow the row index of the new row
   */
  public void onRowInserted(int beforeRow) {
    throw new UnsupportedOperationException(READ_ONLY_ERROR);
  }

  /**
   * Event fired when a row is removed.
   * 
   * @param row the row index of the removed row
   */
  public void onRowRemoved(int row) {
    throw new UnsupportedOperationException(READ_ONLY_ERROR);
  }

  /**
   * Event fired when the local data changes.
   * 
   * @param row the row index
   * @param cell the cell index
   * @param data the new contents of the cell
   */
  public void onSetData(int row, int cell, Object data) {
    throw new UnsupportedOperationException(READ_ONLY_ERROR);
  }

  /**
   * See {@link TableModel}.
   */
  public void requestRows(Request request, Callback callback) {
    RowIterator rowIter = new RowIterator(request);
    callback.onRowsReady(request, new ClientResponse(rowIter));
  }
}
