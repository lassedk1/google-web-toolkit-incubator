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

import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortInfo;
import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortList;
import com.google.gwt.widgetideas.table.client.TableModel.Request;
import com.google.gwt.widgetideas.table.client.TableModel.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A controller that interfaces between a {@link TableModel} and mutliple views
 * of the data. This controller has its own cache so subsequent requests for the
 * same data will not require another {@link TableModel} request.
 * 
 * <h1>Cache</h1>
 * <p>
 * The CachedTableController supports caching rows of data before they are
 * needed, allowing listeners to request data much more quickly. When another
 * class requests data, the CachedTableController will feed data from its cache
 * if available, and then it will request unavailable data from your specific
 * implementation.
 * </p>
 * <p>
 * The pre cache and post cache refers to the number of rows to request in
 * addition to the actual request. Pre cache refers to rows before the request,
 * and post cache refers to rows after the request. For example, in applications
 * where you expect the user to move forward quickly, you would want a large
 * post cache.
 * </p>
 * <p>
 * The size of your cache depends on the implementation and usage of your view
 * component. If you are using a view that supports paging, such as the
 * {@link PagingGrid}, you should set your cache to a multiple of the page size
 * so the user can go to the next and previous pages quickly.
 * </p>
 * <h1>Limitations</h1>
 * <p>
 * This cache controller has some significant limitations. Row values are not
 * cached for performance reasons and are not passed to the Controlled Tables,
 * even on new requests to the model. You need to set them manually or keep
 * track of them separately if they are important to you.
 * </p>
 * <p>
 * Additionally, the cache is cleared every time the sort order changes.
 * However, if you disallow column sorting or expect that the user will not sort
 * the columns repeatedly, the cache will still improve paging performance.
 * </p>
 */
public class CachedTableController extends TableController {
  /**
   * An empty iterator used for rows that have no data.
   */
  private static Iterator EMPTY_ITERATOR = new Iterator() {
    public boolean hasNext() {
      return false;
    }

    public Object next() {
      throw new NoSuchElementException();
    }

    public void remove() {
      throw new UnsupportedOperationException("Remove not supported.");
    }
  };

  /**
   * A callback that automatically updates a {@link ControllableTable}.
   */
  public class CacheCallback extends ControllableTableCallback {
    /**
     * The first requested row.
     */
    private int firstRow;

    /**
     * The last requested row.
     */
    private int lastRow;

    /**
     * Constructor.
     * 
     * @param controllableTable the {@link ControllableTable} instance
     * @param firstRow the first row that was requested by the table
     * @param lastRow the last row that was requested by the table
     */
    public CacheCallback(ControllableTable controllableTable, int firstRow,
        int lastRow) {
      super(controllableTable);
      this.firstRow = firstRow;
      this.lastRow = lastRow;
    }

    /**
     * @see com.google.gwt.widgetideas.table.client.TableModel.Callback
     */
    public void onRowsReady(Request request, Response response) {
      // Save the response data into the cache
      int curRow = request.getStartRow();
      Iterator rows = response.iterator();
      if (rows != null) {
        // Save the data to the cache
        while (rows.hasNext()) {
          Iterator columnIt = (Iterator) rows.next();
          List/* Object */rowList = ensureRowList(curRow);
          if (columnIt != null) {
            rowList.clear();
            while (columnIt.hasNext()) {
              rowList.add(columnIt.next());
            }
          }

          // Go to next row
          curRow++;
        }
      }

      // Update the table
      getControllableTable().setData(firstRow,
          new CacheIterator(firstRow, lastRow), null);
    }
  }

  /**
   * An iterator that iterates over the cached rows of data.
   */
  private class CacheIterator implements Iterator {
    /**
     * The current row that will be returns on the next call to next.
     */
    int curRow;

    /**
     * The last row to iterate.
     */
    int lastRow;

    /**
     * Constructor.
     * 
     * @param firstRow the first row of data
     * @param lastRow the last row of data
     */
    public CacheIterator(int firstRow, int lastRow) {
      this.curRow = firstRow - 1;
      this.lastRow = lastRow;
    }

    public boolean hasNext() {
      return curRow < lastRow;
    }

    public Object next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      curRow++;
      List rowList = getRowList(curRow);
      if (rowList == null) {
        return EMPTY_ITERATOR;
      } else {
        return rowList.iterator();
      }
    }

    public void remove() {
      throw new UnsupportedOperationException("Remove not supported.");
    }
  }

  /**
   * The cached data contained within this CachedTableController. The HashMap
   * contains a collection of "rows", each of which is another List of objects.
   */
  private HashMap/* Integer, ArrayList<Object> */dataMap = new HashMap();

  /**
   * The last sort info.
   */
  private ColumnSortList lastSortList = null;

  /**
   * The number of rows to request that come after the actual requested rows.
   */
  private int postCacheRows = 0;

  /**
   * The number of rows to request that come before the actual requested rows.
   */
  private int preCacheRows = 0;

  /**
   * Constructor.
   * 
   * @param tableModel the underlying {@link TableModel}
   */
  public CachedTableController(TableModel tableModel) {
    super(tableModel);
  }

  /**
   * Get data from the cache. If the data is not available, this method will not
   * request the data from the table model.
   * 
   * @param row the row index
   * @param column the column index
   * @return the data at the index
   */
  public Object getData(int row, int column) {
    List/* Object */rowList = getRowList(row);
    if ((rowList == null) || (rowList.size() < column + 1)) {
      return null;
    } else {
      return rowList.get(column);
    }
  }

  /**
   * @return the number of rows to cache after the requested rows
   */
  public int getNumPostCachedRows() {
    return postCacheRows;
  }

  /**
   * @return the number of rows to cache before the requested rows
   */
  public int getNumPreCachedRows() {
    return preCacheRows;
  }

  /**
   * @see TableController
   */
  public void insertRow(int beforeRow) {
    dataMap.clear();
    super.insertRow(beforeRow);
  }

  /**
   * @see TableController
   */
  public void removeRow(int row) {
    dataMap.clear();
    super.removeRow(row);
  }

  /**
   * @see TableController
   */
  public void setData(int row, int column, Object data) {
    // Save the data to the cache
    List/* <Object> */rowList = ensureRowList(row);
    for (int i = rowList.size(); i < column + 1; i++) {
      rowList.add(null);
    }
    rowList.set(column, data);

    // Update the number of rows
    int numRows = getNumRows();
    if (numRows >= 0) {
      setNumRows(Math.max(numRows, row + 1));
    }

    // Call super method
    super.setData(row, column, data);
  }

  /**
   * Set the number of rows to cache after the visible data area.
   * 
   * @param postCacheRows the number of rows to post cache
   */
  public void setNumPostCachedRows(int postCacheRows) {
    this.postCacheRows = postCacheRows;
  }

  /**
   * Set the number of rows to cache before the visible data area.
   * 
   * @param preCacheRows the number of rows to pre cache
   */
  public void setNumPreCachedRows(int preCacheRows) {
    this.preCacheRows = preCacheRows;
  }

  /**
   * @see TableController
   */
  protected void requestRows(int startRow, int numRows,
      ColumnSortList sortList, ControllableTable table) {
    // Clear the cache if the sort order has changed
    if (sortList == null) {
      if (lastSortList != null) {
        dataMap.clear();
        lastSortList = null;
      }
    } else if (!sortList.equals(lastSortList)) {
      dataMap.clear();
      // Copy the sort list and save it
      lastSortList = new ColumnSortList();
      Iterator it = sortList.iterator();
      while (it.hasNext()) {
        ColumnSortInfo sortInfo = (ColumnSortInfo) it.next();
        lastSortList.add(new ColumnSortInfo(sortInfo.getColumn(), sortInfo
            .isAscending()));
      }
    }

    // Check if all requested rows are in the cache
    int lastRow = startRow + numRows - 1;
    boolean fullyCached = true;
    for (int row = startRow; row <= lastRow; row++) {
      if (!dataMap.containsKey(new Integer(row))) {
        fullyCached = false;
        break;
      }
    }
    if (fullyCached) {
      table.setData(startRow, new CacheIterator(startRow, lastRow), null);
      return;
    }

    // Calculate bounds including the cache
    int uncachedFirstRow = Math.max(0, startRow - preCacheRows);
    int uncachedLastRow = lastRow + postCacheRows;

    // Check the upper bounds against the total number of rows
    int totalNumRows = getNumRows();
    if (totalNumRows >= 0) {
      lastRow = Math.min(totalNumRows - 1, lastRow);
      uncachedLastRow = Math.min(totalNumRows - 1, uncachedLastRow);
    }

    // Remove any data already retrieved starting at the first rows
    for (int row = uncachedFirstRow; row <= lastRow; row++) {
      if (dataMap.containsKey(new Integer(row))) {
        uncachedFirstRow++;
      } else {
        // Need to request the remaining rows
        break;
      }
    }

    // Remove any data already retrieved from the last Row
    for (int row = uncachedLastRow; row >= startRow; row--) {
      if (dataMap.containsKey(new Integer(row))) {
        uncachedLastRow--;
      } else {
        // Need to request the remaining rows
        break;
      }
    }

    // Request the remaining rows that aren't in the cache
    requestRows(uncachedFirstRow, uncachedLastRow - uncachedFirstRow + 1,
        sortList, table, new CacheCallback(table, startRow, lastRow));
  }

  /**
   * Get a row from the dataMap, creating it if it doesn't exist yet.
   * 
   * @param row the row index
   * @return the List of row data
   */
  private List/* <Object> */ensureRowList(int row) {
    Integer rowI = new Integer(row);
    List rowList = (List) dataMap.get(rowI);
    if (rowList == null) {
      rowList = new ArrayList();
      dataMap.put(rowI, rowList);
    }
    return rowList;
  }

  /**
   * Get a row from the dataMap.
   * 
   * @param row the row index
   * @return the List of row data
   */
  private List/* <Object> */getRowList(int row) {
    return (List) dataMap.get(new Integer(row));
  }
}
