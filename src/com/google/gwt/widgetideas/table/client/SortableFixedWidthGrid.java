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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * A variation of the
 * {@link com.google.gwt.widgetideas.table.client.overrides.Grid} that supports
 * many more options such as lazy row and column creation, row movement,
 * hovering, row selection, and better column resizing.
 */
public class SortableFixedWidthGrid extends FixedWidthGrid implements
    HasSortableColumns {
  /**
   * The column sorter defines an algorithm to sort columns.
   */
  public abstract static class ColumnSorter {
    /**
     * Override this method to implement a custom column sorting algorithm.
     * 
     * @param grid the grid that the sorting will be applied to
     * @param column the column to sort
     * @param reversed true if the column should be reverse sorted
     * @param callback the callback object when sorting is complete
     */
    public abstract void onSortColumn(SortableFixedWidthGrid grid, int column,
        boolean reversed, ColumnSorterCallback callback);
  }

  /**
   * Callback that is called when the row sorting is complete.
   */
  public class ColumnSorterCallback {
    /**
     * Set the order of all rows after a column sort request.
     * 
     * This method takes an array of the row indexes in this Grid, ordered
     * according to their new positions in the Grid.
     * 
     * @param column the column that was sorted
     * @param reversed a boolean indicating that the sorting is reversed
     * @param trIndexes the row index in their new order
     */
    protected void onSortingComplete(int column, boolean reversed,
        int[] trIndexes) {
      // Convert indexes to row elements
      FixedWidthGridRowFormatter formatter = getFixedWidthGridRowFormatter();
      Element[] trElems = new Element[trIndexes.length];
      for (int i = 0; i < trElems.length; i++) {
        trElems[i] = formatter.getRawElement(trIndexes[i]);
      }

      // Call main callback method
      onSortingComplete(column, reversed, trElems);
    }

    /**
     * Set the order of all rows after a column sort request.
     * 
     * This method takes an array of the row elements in this Grid, ordered
     * according to their new positions in the Grid.
     * 
     * @param column the column that was sorted
     * @param reversed a boolean indicating that the sorting is reversed
     * @param trElems the row elements in their new order
     */
    protected void onSortingComplete(int column, boolean reversed,
        Element[] trElems) {
      // Move the rows to their new positions
      Element bodyElem = getBodyElement();
      for (int i = trElems.length - 1; i >= 0; i--) {
        if (trElems[i] != null) {
          DOM.removeChild(bodyElem, trElems[i]);
          DOM.insertChild(bodyElem, trElems[i], 1);
        }
      }

      // Mark the column as sorted
      setSortedColumn(column, reversed);
    }
  }

  /**
   * The class used to do column sorting.
   */
  private ColumnSorter columnSorter = null;

  /**
   * The sortable columns listeners attached to this grid.
   */
  private ArrayList/* <HasSortableColumnsListener> */sortableColumnsListeners = null;

  /**
   * The currently sorted column, if one exists.
   */
  private int sortedColumnIndex = -1;

  /**
   * If true, the currently sorted column is reversed.
   */
  private boolean sortedColumnReversed = false;

  /**
   * Add a {@link SortableColumnsListener} listener.
   * 
   * @param listener the listener
   */
  public void addSortableColumnsListener(SortableColumnsListener listener) {
    if (sortableColumnsListeners == null) {
      sortableColumnsListeners = new ArrayList();
    }
    sortableColumnsListeners.add(listener);
  }

  /**
   * Get the column sorter. Optionally create one if it doesn't exist.
   * 
   * @return the column sorter
   */
  public ColumnSorter getColumnSorter() {
    return getColumnSorter(false);
  }

  /**
   * Move a row up one index to a smaller index.
   * 
   * @param row the row index to move
   * @throws IndexOutOfBoundsException
   */
  public void moveRowDown(int row) {
    swapRows(row, row + 1);
  }

  /**
   * Move a row down one index to a higher index.
   * 
   * @param row the row index to move
   * @throws IndexOutOfBoundsException
   */
  public void moveRowUp(int row) {
    swapRows(row, row - 1);
  }

  /**
   * Remove a {@link SortableColumnsListener}bleColumnsListener listener.
   * 
   * @param listener the listener to remove
   */
  public void removeSortableColumnsListener(SortableColumnsListener listener) {
    if (sortableColumnsListeners != null) {
      sortableColumnsListeners.remove(listener);
    }
  }

  /**
   * Completely reverse the order of all rows in the table.
   */
  public void reverseRows() {
    int lastRow = numRows - 1;
    for (int i = 0; i < numRows / 2; i++) {
      swapRowsRaw(i, lastRow);
      lastRow--;
    }

    // Set the column sorting as reversed
    setSortedColumn(sortedColumnIndex, !sortedColumnReversed);
  }

  /**
   * Set the column sorter.
   * 
   * @param sorter the new column sorter
   */
  public void setColumnSorter(ColumnSorter sorter) {
    this.columnSorter = sorter;
  }

  /**
   * Sets the HTML contents of the specified cell.
   * 
   * @param row the cell's row
   * @param column the cell's column
   * @param html the cell's HTML contents
   * @throws IndexOutOfBoundsException
   */
  public void setHTML(int row, int column, String html) {
    super.setHTML(row, column, html);

    // Mark as unsorted
    setSortedColumn(-1, false);
  }

  /**
   * Sets the text within the specified cell.
   * 
   * @param row the cell's row
   * @param column cell's column
   * @param text the cell's text contents
   * @throws IndexOutOfBoundsException
   */
  public void setText(int row, int column, String text) {
    super.setText(row, column, text);

    // Mark as unsorted
    setSortedColumn(-1, false);
  }

  /**
   * Sets the widget within the specified cell.
   * <p>
   * Inherited implementations may either throw IndexOutOfBounds exception if
   * the cell does not exist, or allocate a new cell to store the content.
   * </p>
   * <p>
   * FlexTable will automatically allocate the cell at the correct location and
   * then set the widget. Grid will set the widget if and only if the cell is
   * within the Grid's bounding box.
   * </p>
   * 
   * @param widget The widget to be added
   * @param row the cell's row
   * @param column the cell's column
   * @throws IndexOutOfBoundsException
   */
  public void setWidget(int row, int column, Widget widget) {
    super.setWidget(row, column, widget);

    // Mark as unsorted
    setSortedColumn(-1, false);
  }

  /**
   * Sort the grid according to the specified column. If the column is already
   * sorted, reverse sort it.
   * 
   * @param column the column to sort
   * @throws IndexOutOfBoundsException
   */
  public void sortColumn(int column) {
    if (column == sortedColumnIndex) {
      sortColumn(column, !sortedColumnReversed);
    } else {
      sortColumn(column, false);
    }
  }

  /**
   * Sort the grid according to the specified column.
   * 
   * @param column the column to sort
   * @param reversed reverse sort the column
   * @throws IndexOutOfBoundsException
   */
  public void sortColumn(int column, boolean reversed) {
    // Verify the column bounds
    if (column < 0) {
      throw new IndexOutOfBoundsException(
          "Cannot access a column with a negative index: " + column);
    } else if (column >= numColumns) {
      throw new IndexOutOfBoundsException("Column index: " + column
          + ", Column size: " + numColumns);
    }

    // See if column is already sorted
    if (column == sortedColumnIndex) {
      if (reversed != sortedColumnReversed) {
        reverseRows();
      }
      return;
    }

    // Use the onSort method to actually sort the column
    getColumnSorter(true).onSortColumn(this, column, reversed,
        new ColumnSorterCallback());
  }

  /**
   * Swap the positions of two rows.
   * 
   * @param row1 the first row to swap
   * @param row2 the second row to swap
   * @throws IndexOutOfBoundsException
   */
  public void swapRows(int row1, int row2) {
    checkRowBounds(row1);
    checkRowBounds(row2);
    swapRowsRaw(row1, row2);

    // Mark as unsorted
    setSortedColumn(-1, false);
  }

  /**
   * Get the column sorter. Optionally create one if it doesn't exist. The
   * default column sorter uses a quicksort algorithm.
   * 
   * @param createAsNeeded create a default sorter if needed
   * @return the column sorter
   */
  protected ColumnSorter getColumnSorter(boolean createAsNeeded) {
    if ((columnSorter == null) && createAsNeeded) {
      columnSorter = new ColumnSorter() {
        public void onSortColumn(SortableFixedWidthGrid grid, int column,
            boolean reversed, ColumnSorterCallback callback) {
          // Apply the default quicksort algorithm
          FixedWidthGridCellFormatter formatter = grid.getFixedWidthGridCellFormatter();
          Element[] tdElems = new Element[grid.getRowCount()];
          for (int i = 0; i < tdElems.length; i++) {
            tdElems[i] = formatter.getRawElement(i, column);
          }
          this.onSortColumnQuickSort(tdElems, 0, tdElems.length - 1);

          // Convert tdElems to trElems
          for (int i = 0; i < tdElems.length; i++) {
            tdElems[i] = DOM.getParent(tdElems[i]);
          }

          // Use the callback to complete the sorting
          callback.onSortingComplete(column, reversed, tdElems);
        }

        /**
         * Recursive quicksort algorithm.
         * 
         * @param tdElems an array of row elements
         * @param start the start index to sort
         * @param end the last index to sort
         */
        private void onSortColumnQuickSort(Element[] tdElems, int start, int end) {
          // No need to sort
          if (start >= end) {
            return;
          }

          // Sort this set
          int i = start + 1;
          int k = end;
          String pivot = DOM.getInnerText(tdElems[start]);
          while (k >= i) {
            if (DOM.getInnerText(tdElems[i]).compareTo(pivot) < 0) {
              // Move i until the value is great than the pivot
              i++;
            } else if (k == i) {
              // Don't swap if equal
              k--;
            } else if (DOM.getInnerText(tdElems[k]).compareTo(pivot) < 0) {
              // Swap the elements at k and i
              Element tr = tdElems[i];
              tdElems[i] = tdElems[k];
              tdElems[k] = tr;
              i++;
              k--;
            } else {
              // Decrement k
              k--;
            }
          }

          // Swap k and pivot
          if (k != start) {
            Element tr = tdElems[k];
            tdElems[k] = tdElems[start];
            tdElems[start] = tr;
          }

          // Sort the subsets
          onSortColumnQuickSort(tdElems, start, k - 1);
          onSortColumnQuickSort(tdElems, k + 1, end);
        }
      };
    }

    return columnSorter;
  }

  /**
   * Set the currently sorted column.
   * 
   * @param column the column index
   * @param reversed whether the column is reverse sorted
   */
  protected void setSortedColumn(int column, boolean reversed) {
    if ((column == sortedColumnIndex) && (reversed == sortedColumnReversed)) {
      // Nothing changed
      return;
    } else if (column < 0) {
      sortedColumnIndex = -1;
      sortedColumnReversed = false;
    } else {
      sortedColumnIndex = column;
      sortedColumnReversed = reversed;
    }

    // Fire grid listeners
    if (sortableColumnsListeners != null) {
      // for (ExtendedGridListener listener : extendedGridListeners) {
      Iterator it = sortableColumnsListeners.iterator();
      while (it.hasNext()) {
        SortableColumnsListener listener = (SortableColumnsListener) it.next();
        listener.onSetSortedColumn(sortedColumnIndex, sortedColumnReversed);
      }
    }
  }

  /**
   * Swap two rows without checking the cell bounds.
   * 
   * @param row1 the first row to swap
   * @param row2 the second row to swap
   */
  protected void swapRowsRaw(int row1, int row2) {
    if (row1 == row2 + 1) {
      // Just move row1 up one
      Element tr = getFixedWidthGridRowFormatter().getRawElement(row1);
      DOM.removeChild(getBodyElement(), tr);
      DOM.insertChild(getBodyElement(), tr, row2 + 1);
    } else if (row2 == row1 + 1) {
      // Just move row2 up one
      Element tr = getFixedWidthGridRowFormatter().getRawElement(row2);
      DOM.removeChild(getBodyElement(), tr);
      DOM.insertChild(getBodyElement(), tr, row1 + 1);
    } else if (row1 == row2) {
      // Do nothing if rows are the same
      return;
    } else {
      // Remove both rows
      Element tr1 = getFixedWidthGridRowFormatter().getRawElement(row1);
      Element tr2 = getFixedWidthGridRowFormatter().getRawElement(row2);
      DOM.removeChild(getBodyElement(), tr1);
      DOM.removeChild(getBodyElement(), tr2);

      // Reinsert them into the table
      if (row1 > row2) {
        DOM.insertChild(getBodyElement(), tr1, row2 + 1);
        DOM.insertChild(getBodyElement(), tr2, row1 + 1);
      } else if (row1 < row2) {
        DOM.insertChild(getBodyElement(), tr2, row1 + 1);
        DOM.insertChild(getBodyElement(), tr1, row2 + 1);
      }
    }

    // Update the selected rows table
    Map selectedRows = getSelectedRowsMap();
    Element tr1 = (Element) selectedRows.remove(new Integer(row1));
    Element tr2 = (Element) selectedRows.remove(new Integer(row2));
    if (tr1 != null) {
      selectedRows.put(new Integer(row2), tr1);
    }
    if (tr2 != null) {
      selectedRows.put(new Integer(row1), tr2);
    }
  }
}
