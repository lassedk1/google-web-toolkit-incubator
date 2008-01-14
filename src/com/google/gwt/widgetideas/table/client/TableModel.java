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

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A class to retrieve row data to be used in a table.
 */
public abstract class TableModel {
  /**
   * Callback for {@link TableModel}. Every Request should be associated with a
   * callback that should be called after a Response is generated.
   */
  public static interface Callback {
    /**
     * Called when an error occurs and the rows cannot be loaded.
     * 
     * @param caught the exception that was thrown
     */
    public void onFailure(Throwable caught);

    /**
     * Consume the data created by {@link TableModel} in response to a Request.
     * 
     * @param request the request
     * @param response the response
     */
    public void onRowsReady(Request request, Response response);
  }

  /**
   * Information about the sort order of a specific column in a table.
   */
  public static class ColumnSortInfo implements IsSerializable {
    /**
     * Is the sort ascending.
     */
    private boolean ascending;

    /**
     * The column index.
     */
    private int column;

    /**
     * Default Constructor.
     */
    public ColumnSortInfo() {
      this(0, true);
    }

    /**
     * Constructor.
     * 
     * @param column the column index
     * @param ascending true if sorted ascending
     */
    public ColumnSortInfo(int column, boolean ascending) {
      this.column = column;
      this.ascending = ascending;
    }

    /**
     * Check if this object is equal to another. The objects are equal if the
     * column and ascending values are equal.
     * 
     * @param obj the other object
     * @return true if objects are the same
     */
    public boolean equals(Object obj) {
      // Object is null
      if (obj == null) {
        return false;
      }

      // Compare values and next
      if (obj instanceof ColumnSortInfo) {
        ColumnSortInfo csi = (ColumnSortInfo) obj;
        return getColumn() == csi.getColumn()
            && isAscending() == csi.isAscending();
      }

      // Object is not same type
      return false;
    }

    /**
     * @return the column index
     */
    public int getColumn() {
      return column;
    }

    /**
     * @return true if ascending, false if descending
     */
    public boolean isAscending() {
      return ascending;
    }

    /**
     * Set whether or not the sorting is ascending or descending.
     * 
     * @param ascending true if ascending, false if descending
     */
    public void setAscending(boolean ascending) {
      this.ascending = ascending;
    }

    /**
     * Set the column index.
     * 
     * @param column the column index
     */
    public void setColumn(int column) {
      this.column = column;
    }
  }

  /**
   * An ordered list of sorting info where each entry tells us how to sort a
   * single column. The first entry is the primary sort order, the second entry
   * is the first tie-breaker, and so on.
   */
  public static class ColumnSortList extends ArrayList implements
      IsSerializable {
    /**
     * Add a {@link ColumnSortInfo} to this list. If the column already exists,
     * it will be removed from its current position and placed at the start of
     * the list, becoming the primary sort info.
     * 
     * This add method inserts an entry at the beginning of the list. It does
     * not append the entry to the end of the list.
     * 
     * @param sortInfo the column sort info
     */
    public void addColumnSortInfo(ColumnSortInfo sortInfo) {
      // Remove existing column sort info
      int column = sortInfo.getColumn();
      for (int i = 0; i < size(); i++) {
        ColumnSortInfo curInfo = (ColumnSortInfo) get(i);
        if (curInfo.getColumn() == column) {
          remove(i);
          i--;
        }
      }

      // Insert the new sort info
      add(0, sortInfo);
    }

    /**
     * Add a {@link ColumnSortInfo} to this list. If the column already exists,
     * it will be removed from its current position and placed at the start of
     * the list, becoming the primary sort info.
     * 
     * This add method inserts an entry at the beginning of the list. It does
     * not append the entry to the end of the list.
     * 
     * @param column the column index
     * @param ascending is the sort ascending
     */
    public void addColumnSortInfo(int column, boolean ascending) {
      addColumnSortInfo(new ColumnSortInfo(column, ascending));
    }

    /**
     * Check if this object is equal to another.
     * 
     * @param obj the other object
     * @return true if objects are equal
     */
    public boolean equals(Object obj) {
      // Object is null
      if (obj == null) {
        return false;
      }

      // Compare values and next
      if (obj instanceof ColumnSortList) {
        // Check the size of the lists
        ColumnSortList csl = (ColumnSortList) obj;
        int size = size();
        if (size != csl.size()) {
          return false;
        }

        // Compare the entries
        for (int i = 0; i < size; i++) {
          ColumnSortInfo mySortInfo = (ColumnSortInfo) get(i);
          ColumnSortInfo cslSortInfo = (ColumnSortInfo) csl.get(i);
          if (!mySortInfo.equals(cslSortInfo)) {
            return false;
          }
        }

        // Everything is equal
        return true;
      }

      // Object is not same type
      return false;
    }

    /**
     * Get the primary (first) {@link ColumnSortInfo}'s column index.
     * 
     * @return the primary column or -1 if not sorted
     */
    public int getPrimaryColumn() {
      ColumnSortInfo primaryInfo = getPrimaryColumnSortInfo();
      if (primaryInfo == null) {
        return -1;
      }
      return primaryInfo.getColumn();
    }

    /**
     * Get the primary (first) {@link ColumnSortInfo}.
     * 
     * @return the primary column sort info
     */
    public ColumnSortInfo getPrimaryColumnSortInfo() {
      if (size() > 0) {
        return (ColumnSortInfo) get(0);
      }
      return null;
    }

    /**
     * Get the primary (first) {@link ColumnSortInfo}'s sort order.
     * 
     * @return true if ascending, false if descending
     */
    public boolean isPrimaryAscending() {
      ColumnSortInfo primaryInfo = getPrimaryColumnSortInfo();
      if (primaryInfo == null) {
        return true;
      }
      return primaryInfo.isAscending();
    }
  }

  /**
   * A {@link TableModel} request.
   */
  public static class Request implements IsSerializable {
    /**
     * The number of rows to request.
     */
    private int numRows;

    /**
     * An ordered list of {@link ColumnSortInfo}.
     * 
     * @gwt.typeArgs <com.google.gwt.widgetideas.table.client.TableModel.ColumnSortInfo>
     */
    private ColumnSortList columnSortList;

    /**
     * The first row of table data to request.
     */
    private int startRow;

    /**
     * Default constructor.
     */
    public Request() {
      this(0, 0, null);
    }

    /**
     * Constructor.
     * 
     * @param startRow the first row to request
     * @param numRows the number of rows to request
     */
    public Request(int startRow, int numRows) {
      this(startRow, numRows, null);
    }

    /**
     * Constructor.
     * 
     * @param startRow the first row to request
     * @param numRows the number of rows to request
     * @param columnSortList column sort orders
     */
    public Request(int startRow, int numRows, ColumnSortList columnSortList) {
      this.startRow = startRow;
      this.numRows = numRows;
      this.columnSortList = columnSortList;
    }

    /**
     * Get the {@link ColumnSortInfo}, which includes the sort indexes and
     * ascending or descending order info.
     * 
     * @return the sort info
     */
    public ColumnSortList getColumnSortList() {
      return columnSortList;
    }

    /**
     * Get the number of rows to request.
     * 
     * @return the number of requested rows
     */
    public int getNumRows() {
      return numRows;
    }

    /**
     * Get the first row to request.
     * 
     * @return the first requested row
     */
    public int getStartRow() {
      return startRow;
    }
  }

  /**
   * A response from the {@link TableModel}.
   * 
   */
  public abstract static class Response {
    /**
     * The values associated with each row.
     */
    private List rowValues;

    /**
     * Constructor.
     */
    public Response() {
      this(null);
    }

    /**
     * Constructor.
     * 
     * @param rowValues the values to associate with each row
     */
    public Response(List rowValues) {
      this.rowValues = rowValues;
    }

    /**
     * Gets the iterator associated with the row data generated by the
     * {@link Response}.
     * 
     * @return the rows data
     */
    public abstract Iterator/* <Iterator<Iterator<Object>>> */getIterator();

    /**
     * Get a list of objects associated with the retrieved rows.
     * 
     * @return the list of objects associated with the retrieved row
     */
    public List getRowValues() {
      return rowValues;
    }
  }

  /**
   * An {@link Iterator} over a 2D {@link Collection} of column data.
   */
  public static class SerializableIterator implements IsSerializable, Iterator {
    /**
     * The {@link Collection} of row data. Each row is a {@link Collection} of
     * column data.
     * 
     * @gwt.typeArgs <java.util.Collection>
     */
    private Iterator rows;

    /**
     * Default constructor.
     */
    public SerializableIterator() {
      this(null);
    }

    /**
     * Constructor.
     * 
     * @param rows the row data
     */
    public SerializableIterator(Iterator rows) {
      this.rows = rows;
    }

    public boolean hasNext() {
      if (rows == null) {
        return false;
      }
      return rows.hasNext();
    }

    public Object next() {
      if (rows == null) {
        return null;
      }
      Collection next = (Collection) rows.next();
      if (next == null) {
        return null;
      } else {
        return next.iterator();
      }
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  /**
   * A response from the {@link TableModel} that is serializable, and can by
   * used over RPC.
   */
  public static class SerializableResponse extends Response implements
      IsSerializable {
    /**
     * The {@link Collection} of row data. Each row is a {@link Collection} of
     * column data.
     * 
     * @gwt.typeArgs <java.util.Collection>
     */
    private Collection rows;

    /**
     * Default constructor.
     */
    public SerializableResponse() {
      this(null);
    }

    /**
     * Constructor.
     * 
     * @param rows the row data
     */
    public SerializableResponse(Collection rows) {
      this(rows, null);
    }

    /**
     * Constructor.
     * 
     * @param rows the row data
     * @param rowValues the values to associate with each row
     */
    public SerializableResponse(Collection rows, List rowValues) {
      super(rowValues);
      this.rows = rows;
    }

    public Iterator getIterator() {
      if (rows == null) {
        return null;
      }
      return new SerializableIterator(rows.iterator());
    }
  }

  /**
   * Use the ALL_ROWS value in place of the numRows variable when requesting all
   * rows.
   */
  public static final int ALL_ROWS = -1;

  /**
   * Event fired when a row is inserted.
   * 
   * @param beforeRow the row index of the new row
   */
  public abstract void onRowInserted(int beforeRow);

  /**
   * Event fired when a row is removed.
   * 
   * @param row the row index of the removed row
   */
  public abstract void onRowRemoved(int row);

  /**
   * Event fired when the local data changes.
   * 
   * @param row the row index
   * @param cell the cell index
   * @param data the new contents of the cell
   */
  public abstract void onSetData(int row, int cell, Object data);

  /**
   * Generate a {@link Response} based on a specific {@link Request}. The
   * response is passed into the {@link Callback}.
   * 
   * @param startRow the first row to request
   * @param numRows the number of rows to request
   * @param callback the {@link Callback} to use for the {@link Response}
   */
  public void requestRows(int startRow, int numRows, Callback callback) {
    requestRows(new Request(startRow, numRows), callback);
  }

  /**
   * Generate a {@link Response} based on a specific {@link Request}. The
   * response is passed into the {@link Callback}.
   * 
   * @param startRow the first row to request
   * @param numRows the number of rows to request
   * @param sortList detailed information of column sorting
   * @param callback the {@link Callback} to use for the {@link Response}
   */
  public void requestRows(int startRow, int numRows, ColumnSortList sortList,
      Callback callback) {
    requestRows(new Request(startRow, numRows, sortList), callback);
  }

  /**
   * Generate a {@link Response} based on a specific {@link Request}. The
   * response is passed into the {@link Callback}.
   * 
   * @param request the {@link Request} for row data
   * @param callback the {@link Callback} to use for the {@link Response}
   */
  public abstract void requestRows(Request request, Callback callback);
}
