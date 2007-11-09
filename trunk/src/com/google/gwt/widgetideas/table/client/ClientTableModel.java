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
 * A {@link TableModel} used when the data source can be accessed synchronously.
 */
public abstract class ClientTableModel extends TableModel {

  /**
   * A {@link Response} that can be used when the response is not going over a
   * RPC request.
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
    public Iterator/* <Iterator<Object>> */getIterator() {
      return rows;
    }
  }

  private class ColumnIterator extends StubIterator {
    private int row = 0;

    public Object computeNext() {
      return getCell(row, index++);
    }
  }

  private class RowIterator extends StubIterator {
    int max;

    public RowIterator(Request request) {
      index = request.getStartRow();
      if (request.getNumRows() == -1) {
        max = Integer.MAX_VALUE;
      } else {
        max = request.getNumRows() + index;
      }
    }

    protected Object computeNext() {
      // Reset column iterator rather than creating new one.
      columnIter.index = 0;
      columnIter.row = index++;
      columnIter.done = false;
      columnIter.next = null;
      // Now check for next.
      if (columnIter.hasNext() && index < max) {
        return columnIter;
      } else {
        return null;
      }
    }
  }

  /**
   * Convenience class to support custom iterators.
   */
  private abstract class StubIterator implements Iterator {
    int index;
    Object next;
    boolean done = false;

    protected abstract Object computeNext();

    public boolean hasNext() {
      if (done) {
        return false;
      }
      if (next == null) {
        next = computeNext();
        if (next == null) {
          done = true;
          return false;
        }
      }
      return true;
    }

    public Object next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      } else {
        Object accum = next;
        next = null;
        return accum;
      }
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private ColumnIterator columnIter = new ColumnIterator();

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
    ReadOnlyTableModel.throwReadOnlyException();
  }

  /**
   * Event fired when a row is removed.
   * 
   * @param row the row index of the removed row
   */
  public void onRowRemoved(int row) {
    ReadOnlyTableModel.throwReadOnlyException();
  }

  /**
   * Event fired when the local data changes.
   * 
   * @param row the row index
   * @param cell the cell index
   * @param data the new contents of the cell
   */
  public void onSetData(int row, int cell, Object data) {
    ReadOnlyTableModel.throwReadOnlyException();
  }

  /**
   * See {@link TableModel}.
   */
  public void requestRows(Request request, Callback callback) {
    RowIterator rowIter = new RowIterator(request);
    callback.onRowsReady(request, new ClientResponse(rowIter));
  }

}
