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

import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A {@link PagingGrid} responds to events from an underlying
 * {@link TableController}.
 */
public class PagingGrid extends SortableFixedWidthGrid implements HasRowPaging,
    ControllableTable {
  /**
   * The renderer used to set cell contents.
   */
  public interface CellRenderer {
    /**
     * Render the contents of a cell.
     * 
     * @param grid the grid to render the contents in
     * @param row the row index
     * @param column the column index
     * @param data the data to render
     */
    void renderCell(PagingGrid grid, int row, int column, Object data);
  }

  /**
   * The renderer used to render an entire table when a data request is
   * received.
   */
  protected interface TableRenderer {
    /**
     * Render the table from the data. Do not forget to include a ghost row.
     * 
     * @param grid the grid to render the contents in
     * @param rows an iterator of iterators of objects
     * @param callback the callback to use when rendering completes
     */
    void renderTable(PagingGrid grid, Iterator rows, RendererCallback callback);
  }

  /**
   * An iterator over the visible rows in an iterator over many rows.
   */
  private class VisibleRowsIterator implements Iterator {
    /**
     * The iterator of row data.
     */
    private Iterator rows;

    /**
     * The current row of the rows iterator.
     */
    private int curRow;

    /**
     * The last visible row in the grid.
     */
    private int lastVisibleRow;

    /**
     * Constructor.
     * 
     * @param rows the iterator over row data
     * @param firstRow the first absolute row of the rows iterator
     * @param firstVisibleRow the first visible row in this grid
     * @param lastVisibleRow the last visible row in this grid
     */
    public VisibleRowsIterator(Iterator rows, int firstRow,
        int firstVisibleRow, int lastVisibleRow) {
      this.curRow = firstRow;
      this.lastVisibleRow = lastVisibleRow;

      // Iterate up to the first row
      while (curRow < firstVisibleRow && rows.hasNext()) {
        rows.next();
        curRow++;
      }
      this.rows = rows;
    }

    public boolean hasNext() {
      return (curRow <= lastVisibleRow && rows.hasNext());
    }

    public Object next() {
      // Check that the next row exists
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return rows.next();
    }

    public void remove() {
      throw new UnsupportedOperationException("Remove not supported");
    }
  }

  /**
   * The cell rendering object.
   */
  private CellRenderer cellRenderer = null;

  /**
   * The current visible page.
   */
  private int currentPage = -1;

  /**
   * The listeners that respond to data requests.
   */
  private TableDataRequestListenerCollection dataRequestListeners = null;

  /**
   * The number of rows of data that we expect to exist.
   */
  private int totalNumRows = -1;

  /**
   * The listeners attached to this {@link PagingGrid}.
   */
  private RowPagingListenerCollection rowPagingListeners = null;

  /**
   * The values associated with each row. This is an optional list of data that
   * ties the visible content in each row to an underlying object.
   */
  private List rowValues = null;

  /**
   * The number of rows per page. If the number of rows per page is equal to the
   * number of rows, paging is disabled because only one page exists.
   */
  private int pageSize = 0;

  /**
   * The {@link TableRenderer} used to render the contents of this table.
   */
  private TableRenderer tableRenderer = null;

  /**
   * The {@link RendererCallback} used when table rendering completes.
   */
  private RendererCallback tableRendererCallback = null;

  /**
   * Constructor.
   * 
   * @param tableController the underlying {@link TableController}
   */
  public PagingGrid(TableController tableController) {
    tableController.addControllableTable(this);
  }

  /**
   * Constructor.
   * 
   * @param tableModel the {@link TableModel} used to retrieve data
   */
  public PagingGrid(TableModel tableModel) {
    this(new TableController(tableModel));
  }

  /**
   * Add a new {@link RowPagingListener}.
   * 
   * @param listener the listener
   */
  public void addRowPagingListener(RowPagingListener listener) {
    if (rowPagingListeners == null) {
      rowPagingListeners = new RowPagingListenerCollection();
    }
    rowPagingListeners.add(listener);
  }

  /**
   * Add a new {@link TableDataRequestListener}.
   * 
   * @param listener the listener
   */
  public void addTableDataRequestListener(TableDataRequestListener listener) {
    if (dataRequestListeners == null) {
      dataRequestListeners = new TableDataRequestListenerCollection();
    }
    dataRequestListeners.add(listener);
  }

  /**
   * @return the {@link CellRenderer} used to render cells.
   */
  public CellRenderer getCellRenderer() {
    return cellRenderer;
  }

  /**
   * @return the current page
   */
  public int getCurrentPage() {
    return currentPage;
  }

  /**
   * @return the number of pages, or -1 if not known
   */
  public int getNumPages() {
    if (pageSize < 1) {
      return 1;
    } else {
      int numDataRows = totalNumRows;
      if (numDataRows < 0) {
        return -1;
      }
      return (int) Math.ceil(numDataRows / (pageSize + 0.0));
    }
  }

  /**
   * @return the total number of rows, or -1 if not known
   */
  public int getNumRows() {
    return totalNumRows;
  }

  /**
   * @return the number of rows per page
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * Get the value associated with a row.
   * 
   * @param row the row index
   * @return the value associated with the row
   */
  public Object getRowValue(int row) {
    if (rowValues == null || rowValues.size() <= row) {
      return null;
    }
    return rowValues.get(row);
  }

  /**
   * Go to the first page.
   */
  public void gotoFirstPage() {
    gotoPage(0, false);
  }

  /**
   * Go to the last page. If the number of pages is not known, this method is
   * ignored.
   */
  public void gotoLastPage() {
    if (getNumPages() >= 0) {
      gotoPage(getNumPages(), false);
    }
  }

  /**
   * Go to the next page.
   */
  public void gotoNextPage() {
    gotoPage(currentPage + 1, false);
  }

  /**
   * Set the current page. If the page is out of bounds, it will be
   * automatically set to zero or the last page without throwing any errors.
   * 
   * @param page the page
   * @param forced reload the page even if it is already loaded
   */
  public void gotoPage(int page, boolean forced) {
    int oldPage = currentPage;
    int numPages = getNumPages();
    if (numPages >= 0) {
      currentPage = Math.max(0, Math.min(page, numPages - 1));
    } else {
      currentPage = page;
    }

    if (currentPage != oldPage || forced) {
      // Deselect rows when switching pages
      deselectRows();

      // Fire listeners
      if (rowPagingListeners != null) {
        rowPagingListeners.firePageChanged(currentPage);
      }

      // Clear out existing data if we aren't bulk rendering
      if (tableRenderer == null) {
        int rowCount = getLastRow() - getFirstRow() + 1;
        if (rowCount != getRowCount()) {
          resizeRows(rowCount);
        }
        clearAll();
      }

      // Request the new data from the controller
      if (dataRequestListeners != null) {
        dataRequestListeners.fireRequestData(currentPage * pageSize, pageSize,
            getColumnSortList());
      }
    }
  }

  /**
   * Go to the previous page.
   */
  public void gotoPreviousPage() {
    gotoPage(currentPage - 1, false);
  }

  /**
   * Insert a row into the table relative to the total number of rows.
   * 
   * @param beforeRow the row index
   * @param numRows the new number of rows
   */
  public void insertAbsoluteRow(int beforeRow, int numRows) {
    // Update the total number of rows
    setNumRows(numRows);

    int lastRow = getLastRow() + 1;
    if (beforeRow <= lastRow) {
      int firstRow = getFirstRow();
      if (beforeRow >= firstRow) {
        // Insert row in the middle of the page
        super.insertRow(beforeRow - firstRow);
      } else {
        // Insert zero row because row is before this page
        super.insertRow(0);
      }
      if (this.numRows > pageSize) {
        super.removeRow(pageSize);
      }
    }
  }

  /**
   * Reload the current page.
   */
  public void reloadPage() {
    resetPage(true);
  }

  /**
   * Remove a row from the table relative to the total number of rows.
   * 
   * @param row the row index
   * @param numRows the new number of rows
   */
  public void removeAbsoluteRow(int row, int numRows) {
    // Update the total number of rows
    setNumRows(numRows);

    int lastRow = getLastRow();
    if (row <= lastRow) {
      int firstRow = getFirstRow();
      if (row >= firstRow) {
        // Remove a row in the middle of the page
        super.removeRow(row - firstRow);
      } else {
        // Remove first row because row is before this page
        super.removeRow(0);
      }
      super.insertRow(pageSize - 1);
    }
  }

  /**
   * Remove a {@link RowPagingListener}.
   * 
   * @param listener the listener to remove
   */
  public void removeRowPagingListener(RowPagingListener listener) {
    if (rowPagingListeners != null) {
      rowPagingListeners.remove(listener);
    }
  }

  /**
   * Remove a {@link TableDataRequestListener}.
   * 
   * @param listener the listener to remove
   */
  public void removeTableDataRequestListener(TableDataRequestListener listener) {
    if (dataRequestListeners != null) {
      dataRequestListeners.remove(listener);
    }
  }

  /**
   * Set the {@link CellRenderer} used to render cell contents.
   * 
   * @param cellRenderer the new renderer
   */
  public void setCellRenderer(CellRenderer cellRenderer) {
    this.cellRenderer = cellRenderer;
  }

  /**
   * Set the data in a cell. The data object will be rendered using the
   * {@link CellRenderer}, if one is specified.
   * 
   * The row index in this method is relative to the total number of rows across
   * all pages. It is not the same as the row index passed into setHTML, which
   * is relative to the current page.
   * 
   * @param row the row index
   * @param column the column index
   * @param data the data to set
   */
  public void setData(int row, int column, Object data) {
    int firstRow = getFirstRow();
    if ((row >= firstRow) && (row <= getLastRow())) {
      renderCell(row - firstRow, column, data);
    }
  }

  /**
   * Set a block of data. This method is used when responding to data requests.
   * 
   * This method takes an iterator of Collections, where each collection
   * represents one row of data starting with the first row.
   * 
   * @param firstRow the row index
   * @param rows the 2D Iterator of data
   * @param rowValues the values associated with each row
   */
  public void setData(int firstRow, Iterator/* Iterator<Object> */rows,
      List rowValues) {
    this.rowValues = rowValues;
    if (rows != null) {
      int firstVisibleRow = getFirstRow();
      int lastVisibleRow = getLastRow();

      // Use the table renderer
      if (tableRenderer != null) {
        tableRenderer.renderTable(this, new VisibleRowsIterator(rows, firstRow,
            firstVisibleRow, lastVisibleRow), tableRendererCallback);
        return;
      }

      // Render the cells the default way
      while (rows.hasNext()) {
        Iterator columnIt = (Iterator) rows.next();
        if ((firstRow >= firstVisibleRow) && (firstRow <= lastVisibleRow)) {
          int curColumn = 0;
          while (columnIt.hasNext()) {
            renderCell(firstRow - firstVisibleRow, curColumn, columnIt.next());
            curColumn++;
          }
        }
        firstRow++;
      }
    }

    // Fire an event to inform the listeners
    firePageLoadedEvent();
  }

  /**
   * Set the number of rows in the underlying data.
   * 
   * @param numRows the new number of rows
   */
  public void setNumRows(int numRows) {
    numRows = Math.max(-1, numRows);
    int numPages = getNumPages();
    totalNumRows = numRows;

    // Update our listeners if page count changed
    int newNumPages = getNumPages();
    if (newNumPages != numPages) {
      if (rowPagingListeners != null) {
        rowPagingListeners.fireNumPagesChanged(newNumPages);
      }
    }

    // Reset the page
    resetPage(false);
  }

  /**
   * Set the number of rows per page.
   * 
   * By default, the page size is zero, which indicates that all rows should be
   * shown on the page.
   * 
   * @param pageSize the number of rows per page
   */
  public void setPageSize(int pageSize) {
    pageSize = Math.max(0, pageSize);
    this.pageSize = pageSize;
    resetPage(true);

    // Fire listeners
    int numPages = getNumPages();
    if (rowPagingListeners != null) {
      rowPagingListeners.fireNumPagesChanged(numPages);
    }
  }

  /**
   * @see ControllableTable#setPagingFailure(java.lang.Throwable)
   */
  public void setPagingFailure(Throwable caught) {
    if (rowPagingListeners != null) {
      rowPagingListeners.firePagingFailuire(caught);
    }
  }

  /**
   * Associate a row in the table with a value.
   * 
   * @param row the row index
   * @param value the value to associate
   */
  public void setRowValue(int row, Object value) {
    // Make sure the list can fit the row
    if (rowValues == null) {
      rowValues = new ArrayList(row + 1);
    }
    for (int i = rowValues.size(); i <= row; i++) {
      rowValues.add(null);
    }

    // Set the row value
    rowValues.set(row, value);
  }

  /**
   * Sort the grid according to the specified column.
   * 
   * Unlike a normal {@link SortableFixedWidthGrid}, the {@link PagingGrid}
   * does not automatically sort columns using a client side quick sort.
   * Instead, {@link PagingGrid} sets the sorted column and reloads the page,
   * allowing the user to move sorting to the server side.
   * 
   * Setting a column sorter manually will override this behavior.
   * 
   * @param column the column to sort
   * @param ascending reverse sort the column
   * @throws IndexOutOfBoundsException
   */
  @Override
  public void sortColumn(int column, boolean ascending) {
    // Verify the column bounds
    if (column < 0) {
      throw new IndexOutOfBoundsException(
          "Cannot access a column with a negative index: " + column);
    } else if (column >= numColumns) {
      throw new IndexOutOfBoundsException("Column index: " + column
          + ", Column size: " + numColumns);
    }

    // Sort the column
    if (getColumnSorter() == null) {
      getColumnSortList().add(column, ascending);
      gotoPage(currentPage, true);
      fireColumnSorted();
    } else {
      super.sortColumn(column, ascending);
    }
  }

  /**
   * Fire an event when a page has loaded.
   */
  protected void firePageLoadedEvent() {
    if (rowPagingListeners != null) {
      rowPagingListeners.firePageLoaded(currentPage);
    }
  }

  /**
   * Get the first visible row index.
   * 
   * @return the first row index
   */
  protected int getFirstRow() {
    return currentPage * pageSize;
  }

  /**
   * Get the last visible row index.
   * 
   * @return the last row index
   */
  protected int getLastRow() {
    if (totalNumRows < 0) {
      return (currentPage + 1) * pageSize - 1;
    }
    return Math.min(totalNumRows, (currentPage + 1) * pageSize) - 1;
  }

  /**
   * This method is called immediately after a widget becomes attached to the
   * browser's document.
   */
  @Override
  protected void onLoad() {
    super.onLoad();

    // If we have not loaded any pages, load one now
    if (currentPage < 0) {
      gotoPage(0, true);
    }
  }

  /**
   * Render the contents of the cell.
   * 
   * @param row the row index
   * @param column the column index
   * @param data the data to render
   */
  protected void renderCell(int row, int column, Object data) {
    if (cellRenderer == null) {
      if (data instanceof Widget) {
        setWidget(row, column, (Widget) data);
      } else {
        setHTML(row, column, data + "");
      }
    } else {
      cellRenderer.renderCell(this, row, column, data);
    }
  }

  /**
   * Set the {@link TableRenderer}.
   * 
   * @param tableRenderer the table renderer
   */
  protected void setTableRenderer(TableRenderer tableRenderer) {
    this.tableRenderer = tableRenderer;
    if (tableRenderer != null && tableRendererCallback == null) {
      tableRendererCallback = new RendererCallback() {
        public void onRendered() {
          firePageLoadedEvent();
        }
      };
    }
  }

  /**
   * Reload the current page.
   * 
   * @param force true to force a refresh
   */
  private void resetPage(boolean force) {
    if (currentPage >= 0) {
      gotoPage(currentPage, force);
    }
  }
}
