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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A model defining a 2-dimensional grid of data. This grid has its own cache so
 * subsequent requests for the same data will not require another
 * {@link TableModel} request.
 * 
 * <h1>Cache</h1>
 * <p>
 * The GridModel supports caching rows of data before they are needed, allowing
 * listeners to request data much more quickly. When another class requests
 * data, the GridModel will feed data from its cache if available, and then it
 * will request unavailable data from your specific implementation.
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
 * component. If you are using the ModeledGrid to do paging, you should set your
 * cache to a multiple of the page size so the user can go to the next and
 * previous pages very quickly.
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
      return null;
    }
    public void remove() {
      throw new UnsupportedOperationException("Remove not supported.");
    }
  };
  
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
      if (hasNext()) {
        curRow++;
        List rowList = getRowList(curRow);
        if (rowList == null) {
          return EMPTY_ITERATOR;
        } else {
          return rowList.iterator();
        }
      }
      return null;
    }

    public void remove() {
      throw new UnsupportedOperationException("Remove not supported.");
    }
  }
  
  /**
   * The data contained within this GridModel. The HashMap contains a collection
   * of "rows", each of which is another List of objects.
   */
  private HashMap/* Integer, ArrayList<Object> */dataMap = new HashMap();

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
   */
  public CachedTableController(MutableTableModel oracle) {
    super(oracle);
  }

  /**
   * Get data from the grid.
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
   * Get the number of rows to cache after the visible area.
   * 
   * @return the number of rows to post cache
   */
  public int getPostCacheRows() {
    return postCacheRows;
  }

  /**
   * Get the number of rows to cache before the visible area.
   * 
   * @return the number of rows to pre cache
   */
  public int getPreCacheRows() {
    return preCacheRows;
  }

  /**
   * Insert a row of data.
   * 
   * @param beforeRow the row to insert
   */
  public void insertRow(int beforeRow) {
    dataMap.clear();
    super.insertRow(beforeRow);
  }

  /**
   * Remove a row of data and decrement all rows after the removed row.
   * 
   * @param row the row to remove
   * @throws IndexOutOfBoundsException
   */
  public void removeRow(int row) {
    dataMap.clear();
    super.removeRow(row);
  }

  /**
   * Request new data to load.
   * 
   * @param firstRow the start row of the request
   * @param rowCount the number of rows of data to request
   */
  public void requestData(int firstRow, int rowCount) {
    // Calculate bounds including the cache
    int numRows = getNumRows();
    int lastRow = Math.min(numRows - 1, firstRow + rowCount - 1);
    int uncachedFirstRow = Math.max(0, firstRow - preCacheRows);
    int uncachedLastRow = Math.min(numRows - 1, lastRow + postCacheRows);

    // Remove any data already retrieved from the first Row
    for (int row = uncachedFirstRow; row <= lastRow; row++) {
      if (dataMap.containsKey(new Integer(row))) {
        uncachedFirstRow++;
      } else {
        // Need to request the remaining rows
        break;
      }
    }

    // Send back the info we already have from the front
    if (uncachedFirstRow > firstRow) {
      super.setData(firstRow, new CacheIterator(firstRow, uncachedFirstRow - 1));
      firstRow = uncachedFirstRow;
    }

    // Remove any data already retrieved from the last Row
    for (int row = uncachedLastRow; row >= firstRow; row--) {
      if (dataMap.containsKey(new Integer(row))) {
        uncachedLastRow--;
      } else {
        // Need to request the remaining rows
        break;
      }
    }

    // Send back the info we already have from the back
    if (uncachedLastRow < lastRow) {
      super.setData(uncachedLastRow + 1, new CacheIterator(uncachedLastRow + 1,
          lastRow));
    }

    // Request the remaining rows
    if (uncachedFirstRow <= uncachedLastRow) {
      for (int i = uncachedFirstRow; i <= uncachedLastRow; i++) {
        ensureRowList(i, false);
      }
      super.requestData(uncachedFirstRow, uncachedLastRow - uncachedFirstRow
          + 1);
    }
  }

  /**
   * Set data in the grid.
   * 
   * @param row the row index
   * @param column the column index
   * @param data the data to set
   */
  public void setData(int row, int column, Object data) {
    // Save the data to the cache
    List/* <Object> */rowList = ensureRowList(row, true);
    ensureListCapacity(rowList, column + 1);
    rowList.set(column, data);
    setNumRows(Math.max(getNumRows(), row + 1));

    // Call super method
    super.setData(row, column, data);
  }

  /**
   * Set blocks of data in the grid.
   * 
   * This method takes an iterator of Collections, where each collection
   * represents one row of data starting with the first row.
   * 
   * @param firstRow the first row index
   * @param rows the 2D collection of row data
   */
  public void setData(int firstRow, Iterator rows) {
    int curRow = firstRow;
    if (rows != null) {
      // Save the data to the cache
      while (rows.hasNext()) {
        Iterator columnIt = (Iterator) rows.next();
        if (columnIt == null) {
          ensureRowList(curRow, false);
        } else {
          List/* Object */rowList = ensureRowList(curRow, true);
          rowList.clear();
          while (columnIt.hasNext()) {
            rowList.add(columnIt.next());
          }
        }

        // Go to next row
        curRow++;
      }
    }

    // Call super method
    super.setData(firstRow, new CacheIterator(firstRow, curRow - 1));
  }

  /**
   * Set the number of rows to cache before and after the visible data area.
   * 
   * @param preCacheRows the number of rows to pre cache
   * @param postCacheRows the number of rows to post cache
   */
  public void setNumCachedRows(int preCacheRows, int postCacheRows) {
    setNumPreCachedRows(preCacheRows);
    setNumPostCachedRows(postCacheRows);
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
   * Ensure that the specified list has the minimum size.
   * 
   * Note that this is the actual number of elements, not just the capacity.
   * 
   * @param list the list to enlarge
   * @param minSize the minimum number of elements in the list
   */
  private void ensureListCapacity(List list, int minSize) {
    for (int i = list.size(); i < minSize; i++) {
      list.add(null);
    }
  }

  /**
   * Get a row from the dataGrid, creating it if it doesn't exist yet.
   * 
   * If createList is true, this method will ensure that the row has a key in
   * the cache AND the key points to a valid List object. If createList is
   * false, this method will just ensure the key exists but point it to null.
   * 
   * @param row the row index
   * @param createList actually add the list, not just the key
   * @return the List of row data
   */
  private List/* <Object> */ensureRowList(int row, boolean createList) {
    Integer rowI = new Integer(row);
    if (createList) {
      // Make sure the List is created
      List rowList = (List) dataMap.get(rowI);
      if (rowList == null) {
        rowList = new ArrayList();
        dataMap.put(rowI, rowList);
      }
      return rowList;
    } else {
      // Just make sure a key is set for the row
      if (dataMap.containsKey(rowI)) {
        return (List) dataMap.get(rowI);
      } else {
        dataMap.put(rowI, null);
        return null;
      }
    }
  }

  /**
   * Get a row from the dataGrid.
   * 
   * @param row the row index
   * @return the List of row data
   */
  private List/* <Object> */getRowList(int row) {
    return (List) dataMap.get(new Integer(row));
  }
}
