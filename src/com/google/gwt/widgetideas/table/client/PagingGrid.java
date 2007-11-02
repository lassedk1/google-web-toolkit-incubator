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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Widget;

import java.util.Iterator;
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
    public void renderCell(PagingGrid grid, int row, int column, Object data);
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
    public void renderTable(PagingGrid grid, Iterator rows,
        TableRendererCallback callback);
  }

  /**
   * Called when table rendering completes.
   */
  protected interface TableRendererCallback {
    /**
     * Call this when rendering is complete.
     */
    public void onTableRendered();
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
  private DataRequestListenerCollection dataRequestListeners = null;

  /**
   * The number of rows of data that we expect to exist.
   */
  private int totalNumRows = -1;

  /**
   * The listeners attached to this {@link PagingGrid}.
   */
  private RowPagingListenerCollection rowPagingListeners = null;

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
   * The {@link TableRendererCallback} used when table rendering completes.
   */
  private TableRendererCallback tableRendererCallback = null;

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
   * Add a new {@link DataRequestListener}.
   * 
   * @param listener the listener
   */
  public void addDataRequestListener(DataRequestListener listener) {
    if (dataRequestListeners == null) {
      dataRequestListeners = new DataRequestListenerCollection();
    }
    dataRequestListeners.add(listener);
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
   * Get the cell renderer.
   * 
   * @return the cell rendered
   */
  public CellRenderer getCellRenderer() {
    return cellRenderer;
  }

  /**
   * Returns the currently visible page. If no page has been loaded, -1 is
   * returned.
   * 
   * @return the current page
   */
  public int getCurrentPage() {
    return currentPage;
  }

  /**
   * Get the number of pages. If the number of pages is unknown, -1 is returned.
   * 
   * @return the page count
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
   * Get the total number of rows. If -1, the totol number of rows is not known.
   * 
   * @return the total number of rows
   */
  public int getNumRows() {
    return totalNumRows;
  }

  /**
   * Get the number of rows per page.
   * 
   * @return the number of rows per page
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * Go to the first page.
   */
  public void gotoFirstPage() {
    gotoPage(0, false);
  }

  /**
   * Go to the last page.
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

      // Clear the current data in the grid
      DeferredCommand.addCommand(new Command() {
        public void execute() {
          // Resize the grid if needed
          renderContents();
        }
      });
    }
  }

  /**
   * Go to the previous page.
   */
  public void gotoPreviousPage() {
    gotoPage(currentPage - 1, false);
  }

  /**
   * Insert a row into the table.
   * 
   * @param beforeRow the row index
   * @return the index of the newly created row
   */
  public int insertRow(int beforeRow) {
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
    return beforeRow;
  }

  /**
   * Reload the current page.
   */
  public void reloadPage() {
    resetPage(true);
  }

  /**
   * Remove a {@link DataRequestListener}.
   * 
   * @param listener the listener to remove
   */
  public void removeDataRequestListener(DataRequestListener listener) {
    if (dataRequestListeners != null) {
      dataRequestListeners.remove(listener);
    }
  }

  /**
   * Remove a row from the table.
   * 
   * @param row the row index
   */
  public void removeRow(int row) {
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
   * Set the cell renderer.
   * 
   * @param cellRenderer the new renderer
   */
  public void setCellRenderer(CellRenderer cellRenderer) {
    this.cellRenderer = cellRenderer;
  }

  /**
   * Set data in the controller.
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
   */
  public void setData(int firstRow, Iterator/* Iterator<Object> */rows) {
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
   * Fired when the number of rows changes.
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
      setSortedColumn(column, ascending);
      gotoPage(currentPage, true);
    } else {
      super.sortColumn(column, ascending);
    }
  }

  /**
   * Fire an event when a page has loaded.
   */
  protected void firePageLoadedEvent() {
    if (rowPagingListeners != null) {
      rowPagingListeners.firePageLoaded();
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
  protected void onLoad() {
    super.onLoad();
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
   * Render the contents of the {@link PagingGrid}.
   */
  protected void renderContents() {
    int rowCount = getLastRow() - getFirstRow() + 1;
    if (rowCount != getRowCount()) {
      resizeRows(rowCount);
    }

    // Clear out existing data
    clearAll();

    // Request the new data from the controller
    if (dataRequestListeners != null) {
      dataRequestListeners.fireRequestData(currentPage * pageSize, pageSize,
          getSortIndex(), isSortAscending());
    }
  }

  /**
   * Set the {@link TableRenderer}.
   * 
   * @param tableRenderer the table renderer
   */
  protected void setTableRendered(TableRenderer tableRenderer) {
    this.tableRenderer = tableRenderer;
    if (tableRenderer != null) {
      tableRendererCallback = new TableRendererCallback() {
        public void onTableRendered() {
          firePageLoadedEvent();
        }
      };
    }
  }

  /**
   * Reset the current page.
   * 
   * @param force true to force a refresh
   */
  private void resetPage(boolean force) {
    if (currentPage >= 0) {
      gotoPage(currentPage, force);
    }
  }
}
