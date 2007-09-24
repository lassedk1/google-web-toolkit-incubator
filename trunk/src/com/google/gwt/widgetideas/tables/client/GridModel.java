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
package com.google.gwt.widgetideas.tables.client;

import com.google.gwt.widgetideas.tables.client.TableOracle.Request;
import com.google.gwt.widgetideas.tables.client.TableOracle.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A model defining a 2-dimensional grid of data.
 * 
 * <h1>Cache</h1>
 * <p>
 * The GridModel supports caching rows of data before they are
 * needed, allowing listeners to request data much more quickly.  When
 * another class requests data, the GridModel will feed data from
 * its cache if available, and then it will request unavailable data
 * from your specific implementation.
 * </p>
 * <p>
 * The pre cache and post cache refers to the number of rows to request in
 * addition to the actual request.  Pre cache refers to rows before the
 * request, and post cache refers to rows after the request.  For example, in
 * applications where you expect the user to move forward quickly, you
 * would want a large post cache.
 * </p>
 * <p>
 * The size of your cache depends on the implementation and usage of your
 * view component.  If you are using the ModeledGrid to do paging, you should
 * set your cache to a multiple of the page size so the user can go to
 * the next and previous pages very quickly.
 * </p>
 */
public class GridModel {
  /**
   * The data contained within this GridModel.  The HashMap
   * contains a collection of "rows", each of which is another
   * HashMap of objects.
   */
  private HashMap/*<Integer, HashMap<Integer, Object>>*/ dataMap =
      new HashMap/*<Integer, HashMap<Integer, Object>>*/();
  
  /**
   * The {@link GridModelListener}s attached to this model.
   */
  private ArrayList/*<GridModelListener>*/ gridModelListeners;
  
  /**
   * The total number of rows in this model.
   */
  private int numRows = 0;
  
  /**
   * The table oracle.
   */
  private TableOracle oracle;
  
  /**
   * The callback used to receive the data.
   */
  private TableOracle.Callback callback = new TableOracle.Callback() {
    public void onRowsReady(Request request, Response response) {
      // Get the rows data
      Collection rows = response.getRows();
      if ( rows == null ) {
        return;
      }
      
      // Iterate throught the data
      int row = request.getStartRow();
      Iterator itRows = rows.iterator();
      while (itRows.hasNext()) {
        int cell = 0;
        Collection cells = (Collection) itRows.next();
        Iterator itCells = cells.iterator();
        while (itCells.hasNext()) {
          setData(row, cell, itCells.next());
          cell++;
        }
        row++;
      }
    }
  };
  
  /**
   * The number of rows to request that come after the actual
   * requested rows.
   */
  private int postCacheRows = 0;

  /**
   * The number of rows to request that come before the actual
   * requested rows.
   */
  private int preCacheRows = 0;

  /**
   * Constructor.
   */
  public GridModel(TableOracle oracle) {
    this.oracle = oracle;
  }
  
  /**
   * Add a new grid model listener.
   *
   * @param listener the listener
   */
  public void addGridModelListener(GridModelListener listener) {
    if (gridModelListeners == null) {
      gridModelListeners = new ArrayList/*<GridModelListener>*/();
    }
    gridModelListeners.add(listener);
  }
  
  /**
   * Get data from the grid.
   * 
   * @param row the row index
   * @param column the column index
   * @return the data at the index
   */
  public Object getData(int row, int column) {
    HashMap/*<Integer, Object>*/ rowMap = getRowMap(row);
    if (rowMap == null) {
      return null;
    } else {
      return rowMap.get(new Integer(column));
    }
  }

  /**
   * Get the number of rows.
   * 
   * @return the number of rows
   */
  public int getNumRows() {
    return numRows;
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
    // Allow a new row to be appended
    if (beforeRow != numRows) {
      checkRowBounds(beforeRow);
    }
    
    // Decrement all row indexes
    for (int i = numRows - 1; i >= beforeRow ; i--) {
      HashMap/*<Integer, Object>*/ rowMap = (HashMap) dataMap.remove(new Integer(i));
      if (rowMap != null) {
        dataMap.put(new Integer(i + 1), rowMap);
      }
    }
    numRows++;

    // Fire listeners
    if (gridModelListeners != null) {
//    for (GridModelListener listener : gridModelListeners) {
      Iterator it = gridModelListeners.iterator();
      while (it.hasNext()) {
        GridModelListener listener = (GridModelListener) it.next();
        listener.onRowInserted(beforeRow);
      }
    }
  }
 
  /**
   * Remove a grid model listener.
   * 
   * @param listener the listener to remove
   */
  public void removeGridModelListener(GridModelListener listener) {
    if (gridModelListeners != null) {
      gridModelListeners.remove(listener);
    }
  }
  
  /**
   * Remove a row of data and decrement all rows after the
   * removed row.
   * 
   * @param row the row to remove
   * @throws IndexOutOfBoundsException
   */
  public void removeRow(int row) {
    // Check row bounds
    checkRowBounds(row);
    
    // Remove the actual row
    if (dataMap.remove(new Integer(row)) != null) {
      // Decrement all row indexes
      for (int i = row + 1; i < numRows; i++) {
        HashMap/*<Integer, Object>*/ rowMap = (HashMap) dataMap.remove(new Integer(i));
        if (rowMap != null) {
          dataMap.put(new Integer(i - 1), rowMap);
        }
      }
      numRows--;
    }

    // Fire listeners
    if (gridModelListeners != null) {
//      for (GridModelListener listener : gridModelListeners) {
      Iterator it = gridModelListeners.iterator();
      while (it.hasNext()) {
        GridModelListener listener = (GridModelListener) it.next();
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
    // Calculate bounds including the cache
    firstRow = Math.max(0, firstRow);
    int lastRow = Math.min(numRows, firstRow + rowCount + postCacheRows) - 1;
    firstRow = Math.max(0, firstRow - preCacheRows);
    
    // Remove any data already retrieved
    for (int row = firstRow; row <= lastRow; row++) {
      // Maybe we already retrieved this data
      HashMap/*<Integer, Object>*/ rowMap = getRowMap(row);
      if (rowMap != null) {
        firstRow++;
//        for (Map.Entry/*<Integer, Object>*/ entry : rowMap.entrySet()) {
        Iterator it0 = rowMap.entrySet().iterator();
        while (it0.hasNext()) {
          Map.Entry entry = (Map.Entry) it0.next();
          Integer columnInteger = (Integer) entry.getKey();
          Object data = entry.getValue();
          if (gridModelListeners != null) {
//            for (GridModelListener listener : gridModelListeners) {
            Iterator it1 = gridModelListeners.iterator();
            while (it1.hasNext()) {
              GridModelListener listener = (GridModelListener) it1.next();
              listener.onSetData(row, columnInteger.intValue(), data);
            }
          }
        }
      } else {
        // Need to request the remaining rows
        break;
      }
    }

    // Remove any data already retrieved
    for (int row = lastRow; row >= firstRow; row--) {
      // Maybe we already retrieved this data
      HashMap/*<Integer, Object>*/ rowMap = getRowMap(row);
      if (rowMap != null) {
        lastRow--;
//        for (Map.Entry<Integer, Object> entry : rowMap.entrySet()) {
        Iterator it1 = rowMap.entrySet().iterator();
        while (it1.hasNext()) {
          Map.Entry entry = (Map.Entry) it1.next();
          Integer columnInteger = (Integer) entry.getKey();
          Object data = entry.getValue();
          if (gridModelListeners != null) {
//            for (GridModelListener listener : gridModelListeners) {
            Iterator it2 = gridModelListeners.iterator();
            while (it2.hasNext()) {
              GridModelListener listener = (GridModelListener) it2.next();
              listener.onSetData(row, columnInteger.intValue(), data);
            }
          }
        }
      } else {
        // Need to request the remaining rows
        break;
      }
    }

    // Request the remaining rows
    if (firstRow <= lastRow) {
      // File in any blank rows with HashMaps so we don't request again
      for (int i = firstRow; i <= lastRow; i++) {
        ensureRowMap(i);
      }
      oracle.requestRows(firstRow, lastRow - firstRow + 1, callback);
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
    // Save the data
    HashMap/*<Integer, Object>*/ rowMap = ensureRowMap(row);
    rowMap.put(new Integer(column), data);
    numRows = Math.max(numRows, row + 1);
    
    // Fire listeners
    if (gridModelListeners != null) {
//      for (GridModelListener listener : gridModelListeners) {
      Iterator it = gridModelListeners.iterator();
      while (it.hasNext()) {
        GridModelListener listener = (GridModelListener) it.next();
        listener.onSetData(row, column, data);
      }
    }
  }
  
  /**
   * Set the number of rows to cache before and after the visible
   * data area.
   * 
   * @param preCacheRows the number of rows to pre cache
   * @param postCacheRows the number of rows to post cache
   */
  public void setNumCachedRows(int preCacheRows, int postCacheRows) {
    this.preCacheRows = preCacheRows;
    this.postCacheRows = postCacheRows;
  }
  
  /**
   * Set the number of rows to cache after the visible
   * data area.
   * 
   * @param postCacheRows the number of rows to post cache
   */
  public void setNumPostCachedRows(int postCacheRows) {
    this.postCacheRows = postCacheRows;
  }
  
  /**
   * Set the number of rows to cache before the visible
   * data area.
   * 
   * @param preCacheRows the number of rows to pre cache
   */
  public void setNumPreCachedRows(int preCacheRows) {
    this.preCacheRows = preCacheRows;
  }
  
  /**
   * Set the total number of rows.
   * 
   * @param numRows the total number of rows
   */
  public void setNumRows(int numRows) {
    this.numRows = Math.max(0, numRows);
    
    // Fire listeners
    if (gridModelListeners != null) {
//      for (GridModelListener listener : gridModelListeners) {
      Iterator it = gridModelListeners.iterator();
      while (it.hasNext()) {
        GridModelListener listener = (GridModelListener) it.next();
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
    if ((row >= numRows) || (row < 0)) {
      throw new IndexOutOfBoundsException("Row index: " + row + ", Row size: "
          + numRows);
    }
  }
  
  /**
   * Get a row from the dataGrid, creating it if it doesn't exist
   * yet.
   * 
   * @param row the row index
   * @return the HashMap of row data
   */
  private HashMap/*<Integer, Object>*/ ensureRowMap(int row) {
    HashMap/*<Integer, Object>*/ rowMap = getRowMap(row);
    
    // Create a new row map if needed
    if (rowMap == null)  {
      rowMap = new HashMap/*<Integer, Object>*/();
      dataMap.put(new Integer(row), rowMap);
    }
  
    // Return the row map
    return rowMap;
  }
  
  /**
   * Get a row from the dataGrid.
   * 
   * @param row the row index
   * @return the HashMap of row data
   */
  private HashMap/*<Integer, Object>*/ getRowMap(int row) {
    return (HashMap) dataMap.get(new Integer(row));
  }
}
