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

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.table.client.overrides.Grid;
import com.google.gwt.widgetideas.table.client.overrides.OverrideDOM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A variation of the {@link Grid} that supports many more options such as lazy
 * row and column creation, row movement, hovering, row selection, and better
 * column resizing.
 */
public class ExtendedGrid extends Grid {
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
    public abstract void onSortColumn(ExtendedGrid grid, int column,
        boolean reversed, ColumnSorterCallback callback);
  }

  /**
   * An implementation used to accommodate differences in column width
   * implementations between browsers.
   */
  private static class ExtendedGridImpl {
    /**
     * Native method to add rows into a table with a given number of columns.
     * 
     * @param table the table element
     * @param rows number of rows to add
     * @param columns the number of columns per row
     */
    public native void addRows(Element table, int rows, int columns) /*-{
      var span = $doc.createElement("span");
      span.style["padding"] = "0px";
      span.innerHTML = "&nbsp;";
      var td = $doc.createElement("td");
      td.style["overflow"] = "hidden";
      td.appendChild(span);

      var row = $doc.createElement("tr");
      for(var cellNum = 0; cellNum < columns; cellNum++) {
        var cell = td.cloneNode(true);
        row.appendChild(cell);
      }
      table.appendChild(row);
      for(var rowNum = 1; rowNum < rows; rowNum++) {  
        table.appendChild(row.cloneNode(true));
      }
    }-*/;

    /**
     * Set the width of a column.
     * 
     * @param grid the grid to resize
     * @param column the index of the column
     * @param width the width in pixels
     * @throws IndexOutOfBoundsException
     */
    public void setColumnWidth(ExtendedGrid grid, int column, int width) {
      Element tableElem = grid.getElement();
      DOM.setStyleAttribute(grid.getGhostCellElement(column), "width", width
          + "px");
    }
  }

  /**
   * IE6 version of the implementation. IE6 sets the overall column width
   * instead of the innerWidth, so we need to add the padding and spacing.
   */
  private static class ExtendedGridImplIE6 extends ExtendedGridImpl {
    public void setColumnWidth(ExtendedGrid grid, int column, int width) {
      width += 2 * grid.getCellPadding() + grid.getCellSpacing();
      super.setColumnWidth(grid, column, width);
    }
  }

  /**
   * Safari version of the implementation. Safari sets the overall column width
   * instead of the innerWidth, so we need to add the padding and spacing.
   */
  private static class ExtendedGridImplSafari extends ExtendedGridImpl {
    public void setColumnWidth(ExtendedGrid grid, int column, int width) {
      width += 2 * grid.getCellPadding() + grid.getCellSpacing();
      super.setColumnWidth(grid, column, width);
    }
  }

  /**
   * Opera version of the implementation refreshes the table display so the new
   * column size takes effect. Without the display refresh, the column width
   * doesn't update in the browser.
   */
  private static class ExtendedGridImplOpera extends ExtendedGridImpl {
    public void setColumnWidth(ExtendedGrid grid, int column, int width) {
      super.setColumnWidth(grid, column, width);
      Element tableElem = grid.getElement();
      Element parentElem = DOM.getParent(tableElem);
      int scrollLeft = 0;
      if (parentElem != null) {
        scrollLeft = DOM.getElementPropertyInt(parentElem, "scrollLeft");
      }
      DOM.setStyleAttribute(tableElem, "display", "none");
      DOM.setStyleAttribute(tableElem, "display", "");
      if (parentElem != null) {
        DOM.setElementPropertyInt(parentElem, "scrollLeft", scrollLeft);
      }
    }
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
      ExtendedGridRowFormatter formatter = getExtendedGridRowFormatter();
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
   * This class contains methods used to format a table's cells.
   */
  public class ExtendedGridCellFormatter extends CellFormatter {
    /**
     * UnsupportedOperation. Use ExtendedGrid.setColumnWidth(column, width)
     * instead.
     * 
     * @param row the row of the cell whose width is to be set
     * @param column the cell whose width is to be set
     * @param width the cell's new width, in CSS units
     * @throws UnsupportedOperationException
     */
    public void setWidth(int row, int column, String width) {
      throw new UnsupportedOperationException("setWidth is not supported.  "
          + "Use ExtendedGrid.setColumnWidth(int, int) instead.");
    }

    protected Element getRawElement(int row, int column) {
      return super.getRawElement(row + 1, column);
    }
  }

  /**
   * This class contains methods used to format a table's columns. It is limited
   * by the support cross-browser HTML support for column formatting.
   */
  public class ExtendedGridColumnFormatter extends ColumnFormatter {
    /**
     * UnsupportedOperation. Use ExtendedGrid.setColumnWidth(column, width)
     * instead.
     * 
     * @param column the column of the cell whose width is to be set
     * @param width the cell's new width, in percentage or pixel units
     * @throws UnsupportedOperationException
     */
    public void setWidth(int column, String width) {
      throw new UnsupportedOperationException("setWidth is not supported.  "
          + "Use ExtendedGrid.setColumnWidth(int, int) instead.");
    }
  }

  /**
   * This class contains methods used to format a table's rows.
   */
  public class ExtendedGridRowFormatter extends RowFormatter {
    protected Element getRawElement(int row) {
      return super.getRawElement(row + 1);
    }
  }

  /**
   * The default width of a column in pixels.
   */
  public static final int DEFAULT_COLUMN_WIDTH = 80;

  /**
   * Enable hovering of a cell.
   */
  public static final int HOVERING_POLICY_CELL = 0;

  /**
   * Disable hovering.
   */
  public static final int HOVERING_POLICY_DISABLED = 1;

  /**
   * Enable hovering of a row.
   */
  public static final int HOVERING_POLICY_ROW = 2;

  /**
   * The minimum width of any column.
   */
  public static final int MIN_COLUMN_WIDTH = 1;

  /**
   * Disable row selection.
   */
  public static final int SELECTION_POLICY_DISABLED = 3;

  /**
   * Allow multiple rows to be selected.
   */
  public static final int SELECTION_POLICY_MULTI_ROW = 4;

  /**
   * Allow only one row to be selected at a time.
   */
  public static final int SELECTION_POLICY_ONE_ROW = 5;

  /**
   * The implementation class.
   */
  private static ExtendedGridImpl impl =
      (ExtendedGridImpl) GWT.create(ExtendedGridImpl.class);

  /**
   * A mapping of column indexes to their widths in pixels.
   */
  private HashMap/* <Integer, Integer> */colWidths = new HashMap/*
                                                                 * <Integer,
                                                                 * Integer>
                                                                 */();

  /**
   * The class used to do column sorting.
   */
  private ColumnSorter columnSorter = null;

  /**
   * The extended grid listeners attached to this grid.
   */
  private ArrayList/* <ExtendedGridListener> */extendedGridListeners = null;

  /**
   * The hidden, ghost row used for sizing the columns.
   */
  private Element ghostRow = DOM.createTR();

  /**
   * The current hovering policy.
   */
  private int hoveringPolicy = HOVERING_POLICY_ROW;

  /**
   * The index of the cell currently being hovered.
   */
  private int hoveringCellIndex = -1;

  /**
   * The current element being hovered.
   */
  private Element hoveringElement = null;

  /**
   * The index of the row currently being hovered.
   */
  private int hoveringRowIndex = -1;

  /**
   * The index of the row that the user selected last.
   */
  private int lastSelectedRowIndex = -1;

  /**
   * The minimum row index that can be hovered or selected. Used to add header
   * rows to the grid.
   */
  private int minHoverRowIndex = 0;

  /**
   * The rows that are currently selected.
   */
  private Map/* <Integer, Element> */selectedRows = new HashMap();

  /**
   * The selection policy determines if the user can select zero, one, or many
   * rows.
   */
  private int selectionPolicy = SELECTION_POLICY_MULTI_ROW;

  /**
   * The currently sorted column, if one exists.
   */
  private int sortedColumnIndex = -1;

  /**
   * If true, the currently sorted column is reversed.
   */
  private boolean sortedColumnReversed = false;

  /**
   * Constructor.
   */
  public ExtendedGrid() {
    super();
    setClearText("&nbsp;");

    // Setup the table element
    Element tableElem = getElement();
    DOM.setStyleAttribute(tableElem, "tableLayout", "fixed");
    DOM.setStyleAttribute(tableElem, "width", "0px");

    // Replace the formatters
    setRowFormatter(new ExtendedGridRowFormatter());
    setCellFormatter(new ExtendedGridCellFormatter());
    setColumnFormatter(new ExtendedGridColumnFormatter());

    // Create the ghost row for sizing
    DOM.setStyleAttribute(ghostRow, "height", "0px");
    DOM.setStyleAttribute(ghostRow, "overflow", "hidden");
    DOM.insertChild(getBodyElement(), ghostRow, 0);

    // Sink hover and selection events
    sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEDOWN | Event.ONCLICK);
  }

  /**
   * Add a listener to this grid.
   * 
   * @param listener the listener
   */
  public void addExtendedGridListener(ExtendedGridListener listener) {
    if (extendedGridListeners == null) {
      extendedGridListeners = new ArrayList/* <ExtendedGridListener> */();
    }
    extendedGridListeners.add(listener);
  }

  /**
   * Stretches or shrinks the column to automatically fit its data content.
   * 
   * @param column the column to fit
   * @return the new column width
   * @throws IndexOutOfBoundsException
   */
  public int autoFitColumnWidth(int column) {
    int newWidth = getAutoFitColumnWidth(column);
    setColumnWidth(column, newWidth);
    return newWidth;
  }

  /**
   * Deselect a row in the data table.
   * 
   * @param row the row index
   */
  public void deselectRow(int row) {
    Element rowElem = (Element) selectedRows.remove(new Integer(row));
    if (rowElem != null) {
      deselectRow(row, rowElem);

      // Fire grid listeners
      if (extendedGridListeners != null) {
        Iterator it = extendedGridListeners.iterator();
        while (it.hasNext()) {
          ExtendedGridListener listener = (ExtendedGridListener) it.next();
          listener.onRowDeselected(row);
        }
      }
    }
  }

  /**
   * Deselect all selected rows in the data table.
   */
  public void deselectRows() {
    // Deselect all rows
    Iterator it = selectedRows.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      int row = ((Integer) entry.getKey()).intValue();
      deselectRow(row, (Element) entry.getValue());
    }

    // Fire grid listeners
    if (extendedGridListeners != null) {
      Iterator listenerIt = extendedGridListeners.iterator();
      while (listenerIt.hasNext()) {
        ExtendedGridListener listener =
            (ExtendedGridListener) listenerIt.next();
        listener.onAllRowsDeselected();
      }
    }

    // Clear out the rows
    selectedRows.clear();
  }

  /**
   * Calculate the minimum required width to fit the contents of the column, but
   * do not actually change the column size.
   * 
   * @param column the column to fit
   * @return the minimum column width
   * @throws IndexOutOfBoundsException
   */
  public int getAutoFitColumnWidth(int column) {
    // Ensure that the indices are not negative.
    super.prepareColumn(column);

    // Calculate difference between current width and desired width
    int columnWidth = getColumnWidth(column);
    int widthOffset = -1 * columnWidth;
    int contentVisibleWidth = 0;
    ExtendedGridCellFormatter formatter = getExtendedGridCellFormatter();
    for (int row = 0; row < numRows; row++) {
      Element td = formatter.getRawElement(row, column);

      // Get the visible width for all cells in the column
      if (row == 0) {
        int clientWidth = DOM.getElementPropertyInt(td, "clientWidth");
        int offsetWidth = DOM.getElementPropertyInt(td, "offsetWidth");
        contentVisibleWidth = clientWidth - 2 * getCellPadding();
      }

      // Calculate hidden area for this cell
      widthOffset =
          Math.max(widthOffset, DOM.getElementPropertyInt(getCellContainer(td),
              "offsetWidth")
              - contentVisibleWidth);

      // Scroll back if needed
      DOM.setElementPropertyInt(DOM.getFirstChild(td), "scrollLeft", 0);
    }

    // Set the new width
    int newWidth = columnWidth + widthOffset;
    newWidth = Math.max(newWidth, MIN_COLUMN_WIDTH);
    return newWidth;
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
   * Return the column width for a given column index. If a width has not been
   * assigned, the default width is returned.
   * 
   * @param column the column index
   * @return the column width in pixels
   */
  public int getColumnWidth(int column) {
    Object colWidth = colWidths.get(new Integer(column));
    if (colWidth == null) {
      return DEFAULT_COLUMN_WIDTH;
    } else {
      return ((Integer) colWidth).intValue();
    }
  }

  /**
   * Returns the current hovering policy.
   * 
   * @return the hovering policy.
   */
  public int getHoveringPolicy() {
    return hoveringPolicy;
  }

  /**
   * Returns a Set of selected row indexes.
   * 
   * @return the selected rows
   */
  public Set/* <Integer> */getSelectedRows() {
    return selectedRows.keySet();
  }

  /**
   * Returns the current selection policy.
   * 
   * @return the selection policy
   */
  public int getSelectionPolicy() {
    return selectionPolicy;
  }

  /**
   * Inserts a new row into the table.
   * 
   * @param beforeRow the index before which the new row will be inserted
   * @return the index of the newly-created row
   * @throws IndexOutOfBoundsException
   */
  public int insertRow(int beforeRow) {
    // Specifically allow the row count as an insert position.
    if (beforeRow != getRowCount()) {
      checkRowBounds(beforeRow);
    }
    Element tr = DOM.createTR();
    DOM.insertChild(getBodyElement(), tr, beforeRow + 1);
    numRows++;

    // Add cells to the row
    for (int column = 0; column < numColumns; column++) {
      insertCell(beforeRow, column);
    }
    return beforeRow;
  }

  /**
   * Returns true if the row is currently selected.
   * 
   * @param row the row index
   * @return true if the row is selected
   */
  public boolean isRowSelected(int row) {
    return selectedRows.containsKey(new Integer(row));
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
   * Method to process events generated from the browser.
   * 
   * @param event the generated event
   */
  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);

    // Get the target cell and row
    Element targetRow = null;
    Element targetCell = getEventTargetCell(event);
    if (targetCell != null) {
      targetRow = DOM.getParent(targetCell);
    }

    switch (DOM.eventGetType(event)) {
      // Fire event on cell click
      case Event.ONCLICK:
        if (targetRow == null) {
          return;
        }
        int rowIndex = OverrideDOM.getRowIndex(targetRow) - 1;
        int cellIndex = OverrideDOM.getCellIndex(targetCell);
        onCellClicked(rowIndex, cellIndex);
        if (extendedGridListeners != null) {
          Iterator it = extendedGridListeners.iterator();
          while (it.hasNext()) {
            ExtendedGridListener listener = (ExtendedGridListener) it.next();
            listener.onCellClicked(rowIndex, cellIndex);
          }
        }
        break;

      // Highlight a row on mouse over
      case Event.ONMOUSEOVER:
        switch (hoveringPolicy) {
          case HOVERING_POLICY_ROW:
            if (!DOM.compare(targetRow, hoveringElement)) {
              unhover();
              hoverRow(targetRow);
            }
            break;
          case HOVERING_POLICY_CELL:
            if (!DOM.compare(targetCell, hoveringElement)) {
              unhover();
              hoverCell(targetCell, targetRow);
            }
            break;
        }
        break;

      // Select a row on click
      case Event.ONMOUSEDOWN:
        // Check the target row
        if (targetRow == null) {
          return;
        }
        int targetRowIndex = OverrideDOM.getRowIndex(targetRow) - 1;

        switch (selectionPolicy) {
          case SELECTION_POLICY_MULTI_ROW:
            boolean shiftKey = DOM.eventGetShiftKey(event);
            boolean ctrlKey =
                DOM.eventGetCtrlKey(event) || DOM.eventGetMetaKey(event);

            // Prevent default text selection
            if (ctrlKey || shiftKey) {
              DOM.eventPreventDefault(event);
            }

            // Select the rows
            selectRow(targetRowIndex, ctrlKey, shiftKey);
            break;
          case SELECTION_POLICY_ONE_ROW:
            selectRow(-1, targetRow, true, true);
            lastSelectedRowIndex = targetRowIndex;
            break;
        }
        break;
    }
  }

  /**
   * Handle cell click events.
   * 
   * @param row the row index
   * @param cell the cell index
   */
  public void onCellClicked(int row, int cell) {
  }

  /**
   * Remove one of the grid listeners.
   * 
   * @param listener the listener to remove
   */
  public void removeExtendedGridListener(ExtendedGridListener listener) {
    if (extendedGridListeners != null) {
      extendedGridListeners.remove(listener);
    }
  }

  /**
   * Removes the specified row from the table.
   * 
   * @param row the index of the row to be removed
   * @throws IndexOutOfBoundsException
   */
  public void removeRow(int row) {
    checkRowBounds(row);
    deselectRow(row);
    super.removeRow(row);
    numRows--;
  }

  /**
   * Resizes the grid to the specified number of columns.
   * 
   * @param columns the number of columns
   * @throws IndexOutOfBoundsException
   */
  public void resizeColumns(int columns) {
    super.resizeColumns(columns);
    updateGhostRow();
  }

  /**
   * Resizes the grid to the specified number of rows.
   * 
   * @param rows the number of rows
   * @throws IndexOutOfBoundsException
   */
  public void resizeRows(int rows) {
    int curNumRows = numRows;
    if (numRows == rows) {
      return;
    }
    if (rows < 0) {
      throw new IndexOutOfBoundsException("Cannot set number of rows to "
          + rows);
    }
    if (numRows < rows) {
      impl.addRows(getBodyElement(), rows - numRows, numColumns);
      numRows = rows;
    } else {
      while (numRows > rows) {
        // Fewer rows. Remove extraneous ones.
        removeRow(numRows - 1);
      }
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
   * Select a row in the data table.
   * 
   * @param row the row index
   * @param unselectAll unselect all other rows first
   * @throws IndexOutOfBoundsException
   */
  public void selectRow(int row, boolean unselectAll) {
    selectRow(row, getExtendedGridRowFormatter().getElement(row), unselectAll,
        true);
  }

  /**
   * Select a row in the data table. Simulate the effect of a shift click and/or
   * control click. This method ignores the selection policy, which only applies
   * to user selection via mouse events.
   * 
   * @param row the row index
   * @param unselectAll unselect all rows even if ctrlKey is true
   * @param ctrlKey true to simulate a control click
   * @param shiftKey true to simulate a shift selection
   * @throws IndexOutOfBoundsException
   */
  public void selectRow(int row, boolean ctrlKey, boolean shiftKey) {
    // Check the row bounds
    checkRowBounds(row);

    // Deselect all rows
    if (!ctrlKey) {
      deselectRows();
    }

    if (shiftKey && (lastSelectedRowIndex > -1)) {
      // Shift+select rows
      ExtendedGridRowFormatter formatter = getExtendedGridRowFormatter();
      int firstRow = Math.min(row, lastSelectedRowIndex);
      int lastRow = Math.max(row, lastSelectedRowIndex);
      lastRow = Math.min(lastRow, getRowCount() - 1);
      for (int curRow = firstRow; curRow <= lastRow; curRow++) {
        selectRow(curRow, formatter.getRawElement(curRow), false, false);
      }

      // Fire grid listeners
      if (extendedGridListeners != null) {
        Iterator it = extendedGridListeners.iterator();
        while (it.hasNext()) {
          ExtendedGridListener listener = (ExtendedGridListener) it.next();
          listener.onRowsSelected(firstRow, lastRow - firstRow + 1);
        }
      }
    } else if (selectedRows.containsKey(new Integer(row))) {
      // Ctrl+unselect a selected row
      deselectRow(row);
      lastSelectedRowIndex = row;
    } else {
      // Select the row
      ExtendedGridRowFormatter formatter = getExtendedGridRowFormatter();
      selectRow(row, formatter.getRawElement(row), false, true);
      lastSelectedRowIndex = row;
    }
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
   * Set the width of a column.
   * 
   * @param column the index of the column
   * @param width the width in pixels
   * @throws IndexOutOfBoundsException
   */
  public void setColumnWidth(int column, int width) {
    // Ensure that the indices are not negative.
    if (column < 0) {
      throw new IndexOutOfBoundsException(
          "Cannot access a column with a negative index: " + column);
    }

    // Set the width
    width = Math.max(MIN_COLUMN_WIDTH, width);
    colWidths.put(new Integer(column), new Integer(width));

    // Update the cell width if possible
    if (column >= numColumns) {
      return;
    }

    // Set the actual column width
    impl.setColumnWidth(this, column, width);
  }

  /**
   * Set the hovering policy.
   * 
   * @param hoveringPolicy the hovering policy to use
   */
  public void setHoveringPolicy(int hoveringPolicy) {
    if (this.hoveringPolicy != hoveringPolicy) {
      unhover();
      this.hoveringPolicy = hoveringPolicy;
    }
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
   * Set the index of the first row that can be hovered or selected. Use this
   * option if the first one or more rows of your Grid are used as headers.
   * 
   * @param minRow the min row that can be hovered
   */
  public void setMinHoverRow(int minRow) {
    minHoverRowIndex = minRow;
  }

  /**
   * Set the selection policy, which determines if the user can select zero,
   * one, or multiple rows.
   * 
   * @param selectionPolicy the selection policy
   */
  public void setSelectionPolicy(int selectionPolicy) {
    deselectRows();
    this.selectionPolicy = selectionPolicy;
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
   * Get the container element from a cell. The container element is a span
   * inside of the cell that actually holds the content.
   * 
   * @param td the cell element
   * @return the container element
   */
  protected Element getCellContainer(Element td) {
    return DOM.getFirstChild(td);
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
        public void onSortColumn(ExtendedGrid grid, int column,
            boolean reversed, ColumnSorterCallback callback) {
          // Apply the default quicksort algorithm
          ExtendedGridCellFormatter formatter =
              grid.getExtendedGridCellFormatter();
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
   * Directly ask the underlying DOM what the cell count on the given row is.
   * 
   * @param row the row
   * @return number of columns in the row
   */
  protected int getDOMCellCount(int row) {
    return super.getDOMCellCount(row + 1);
  }

  /**
   * Directly ask the underlying DOM what the row count is.
   * 
   * @return Returns the number of rows in the table
   */
  protected int getDOMRowCount() {
    return super.getDOMRowCount() - 1;
  }

  /**
   * Explicitly gets the {@link ExtendedGridCellFormatter}. The results of
   * {@link com.google.gwt.user.client.ui.HTMLTable#getCellFormatter()} may also
   * be downcast to a {@link ExtendedGridCellFormatter}.
   * 
   * @return the ExtendedGrid's cell formatter
   */
  protected ExtendedGridCellFormatter getExtendedGridCellFormatter() {
    return (ExtendedGridCellFormatter) getCellFormatter();
  }

  /**
   * Explicitly gets the {@link ExtendedGridRowFormatter}. The results of
   * {@link com.google.gwt.user.client.ui.HTMLTable#getCellFormatter()} may also
   * be downcast to a {@link ExtendedGridRowFormatter}.
   * 
   * @return the ExtendedGrid's cell formatter
   */
  protected ExtendedGridRowFormatter getExtendedGridRowFormatter() {
    return (ExtendedGridRowFormatter) getRowFormatter();
  }

  /**
   * Returns the current number of ghost columns in existence.
   * 
   * @return the number of ghost columns
   */
  protected int getGhostColumnCount() {
    return super.getDOMCellCount(0);
  }

  /**
   * Set the current hovering cell.
   * 
   * @param cellElem the cell element
   */
  protected void hoverCell(Element cellElem, Element rowElem) {
    // Check for null cell element
    if ((cellElem == null) || (rowElem == null)) {
      return;
    }

    int row = OverrideDOM.getRowIndex(rowElem) - 1;
    if (row >= minHoverRowIndex) {
      // Highlight the row if not selected
      setStyleName(cellElem, "hovering", true);
      hoveringElement = cellElem;
      hoveringRowIndex = row;
      hoveringCellIndex = OverrideDOM.getCellIndex(cellElem);

      // Fire grid listeners
      if (extendedGridListeners != null) {
        // for (ExtendedGridListener listener : extendedGridListeners) {
        Iterator it = extendedGridListeners.iterator();
        while (it.hasNext()) {
          ExtendedGridListener listener = (ExtendedGridListener) it.next();
          listener.onCellHover(row, hoveringCellIndex);
        }
      }
    }
  }

  /**
   * Set the current hovering row.
   * 
   * @param rowElem the row element
   */
  protected void hoverRow(Element rowElem) {
    // Check for null row element
    if (rowElem == null) {
      return;
    }

    int row = OverrideDOM.getRowIndex(rowElem) - 1;
    if (row >= minHoverRowIndex) {
      // Highlight the row if not selected
      setStyleName(rowElem, "hovering", true);
      hoveringElement = rowElem;
      hoveringRowIndex = row;

      // Fire grid listeners
      if (extendedGridListeners != null) {
        // for (ExtendedGridListener listener : extendedGridListeners) {
        Iterator it = extendedGridListeners.iterator();
        while (it.hasNext()) {
          ExtendedGridListener listener = (ExtendedGridListener) it.next();
          listener.onRowHover(row);
        }
      }
    }
  }

  /**
   * Inserts a new cell into the specified row.
   * 
   * @param row the row into which the new cell will be inserted
   * @param column the column before which the cell will be inserted
   * @return the new element
   * @throws IndexOutOfBoundsException
   */
  protected Element insertCell(int row, int column) {
    Element tr = getRowFormatter().getElement(row);
    Element td = createCell();
    appendCellContainer(td);
    DOM.insertChild(tr, td, column);
    return td;
  }

  /**
   * Checks that a cell is a valid cell in the table.
   * 
   * @param row the cell's row
   * @param column the cell's column
   * @throws IndexOutOfBoundsException
   */
  protected void prepareCell(int row, int column) {
    prepareColumn(column);
    super.prepareCell(row, column);
  }

  /**
   * Checks that the column index is valid. ExtendedGrid lazily creates columns
   * as needed.
   * 
   * @param column The column index to be checked
   * @throws IndexOutOfBoundsException if the column is negative
   */
  protected void prepareColumn(int column) {
    // Ensure that the indices are not negative.
    if (column < 0) {
      throw new IndexOutOfBoundsException(
          "Cannot access a column with a negative index: " + column);
    }

    // ExtendedGrid lazily creates cells
    if (column >= numColumns) {
      resizeColumns(column + 1);
    }
  }

  /**
   * Checks that the row index is valid. ExtendedGrid lazily creates rows as
   * needed.
   * 
   * @param row The row index to be checked
   * @throws IndexOutOfBoundsException if the row is negative
   */
  protected void prepareRow(int row) {
    // Ensure that the indices are not negative.
    if (row < 0) {
      throw new IndexOutOfBoundsException(
          "Cannot access a row with a negative index: " + row);
    }

    // ExtendedGrid lazily creates cells
    if (row >= numRows) {
      resizeRows(row + 1);
    }
  }

  /**
   * Select a row in the data table.
   * 
   * @param row the row index, or -1 if unknown
   * @param rowElem the row element
   * @param unselectAll true to unselect all currently selected rows
   * @param fireEvent true to fire the select event to listeners
   */
  protected void selectRow(int row, Element rowElem, boolean unselectAll,
      boolean fireEvent) {
    // Deselect current rows
    if (unselectAll) {
      deselectRows();
    }

    // Get the row index if needed
    if (row < 0) {
      row = OverrideDOM.getRowIndex(rowElem) - 1;
    }

    // Select the new row
    Integer rowI = new Integer(row);
    if (selectedRows.containsKey(rowI)) {
      // Ignore request if row already selected
      return;
    } else if (row >= minHoverRowIndex) {
      selectedRows.put(rowI, rowElem);
      setStyleName(rowElem, "selected", true);

      // Fire grid listeners
      if (fireEvent && extendedGridListeners != null) {
        Iterator it = extendedGridListeners.iterator();
        while (it.hasNext()) {
          ExtendedGridListener listener = (ExtendedGridListener) it.next();
          listener.onRowsSelected(row, 1);
        }
      }
    }
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
    if (extendedGridListeners != null) {
      // for (ExtendedGridListener listener : extendedGridListeners) {
      Iterator it = extendedGridListeners.iterator();
      while (it.hasNext()) {
        ExtendedGridListener listener = (ExtendedGridListener) it.next();
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
      Element tr = getExtendedGridRowFormatter().getRawElement(row1);
      DOM.removeChild(getBodyElement(), tr);
      DOM.insertChild(getBodyElement(), tr, row2 + 1);
    } else if (row2 == row1 + 1) {
      // Just move row2 up one
      Element tr = getExtendedGridRowFormatter().getRawElement(row2);
      DOM.removeChild(getBodyElement(), tr);
      DOM.insertChild(getBodyElement(), tr, row1 + 1);
    } else if (row1 == row2) {
      // Do nothing if rows are the same
      return;
    } else {
      // Remove both rows
      Element tr1 = getExtendedGridRowFormatter().getRawElement(row1);
      Element tr2 = getExtendedGridRowFormatter().getRawElement(row2);
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
    Element tr1 = (Element) selectedRows.remove(new Integer(row1));
    Element tr2 = (Element) selectedRows.remove(new Integer(row2));
    if (tr1 != null) {
      selectedRows.put(new Integer(row2), tr1);
    }
    if (tr2 != null) {
      selectedRows.put(new Integer(row1), tr2);
    }
  }

  /**
   * Unhighlight the currently hovering row or cell.
   */
  protected void unhover() {
    // Nothing to unhover
    if (hoveringElement == null) {
      return;
    }

    // Unhighlight the element
    setStyleName(hoveringElement, "hovering", false);
    hoveringElement = null;

    // Fire grid listeners
    if (extendedGridListeners != null) {
      if (hoveringCellIndex >= 0) {
        // Unhover the cell
        Iterator it = extendedGridListeners.iterator();
        while (it.hasNext()) {
          ExtendedGridListener listener = (ExtendedGridListener) it.next();
          listener.onCellUnhover(hoveringRowIndex, hoveringCellIndex);
        }
      } else {
        // Unhover the row
        Iterator it = extendedGridListeners.iterator();
        while (it.hasNext()) {
          ExtendedGridListener listener = (ExtendedGridListener) it.next();
          listener.onRowUnhover(hoveringCellIndex);
        }
      }
    }

    // Reset the hovering integers
    hoveringRowIndex = -1;
    hoveringCellIndex = -1;
  }

  /**
   * Appends a div and span container to a cell. All cell content is actually
   * added to the container, which allows for forceful control of the width of a
   * column.
   * 
   * @param td the td element
   */
  private void appendCellContainer(Element td) {
    // Set the enforced cell style
    DOM.setInnerHTML(td, "");
    DOM.setStyleAttribute(td, "overflow", "hidden");

    // Add a tight span to hold the contents
    Element span = DOM.createSpan();
    DOM.setStyleAttribute(span, "padding", "0px");
    DOM.setInnerHTML(span, "&nbsp;");
    DOM.appendChild(td, span);
  }

  /**
   * Deselect a row in the data table. This method does not remove the element
   * from the selected rows index.
   * 
   * @param row the row index
   * @param rowElem the row element
   */
  private void deselectRow(int row, Element rowElem) {
    DOM.setElementProperty(rowElem, "className", "");
  }

  /**
   * Returns a cell in the ghost row.
   * 
   * @param column the cell's column
   * @return the ghost cell
   */
  private Element getGhostCellElement(int column) {
    return DOM.getChild(ghostRow, column);
  }

  /**
   * Add or remove ghost cells when the table size changes.
   */
  private void updateGhostRow() {
    int numGhosts = getGhostColumnCount();
    if (numColumns > numGhosts) {
      // Add ghosts as needed
      for (int i = numGhosts; i < numColumns; i++) {
        Element td = OverrideDOM.createTD();
        DOM.setStyleAttribute(td, "height", "0px");
        DOM.setStyleAttribute(td, "overflow", "hidden");
        DOM.setStyleAttribute(td, "paddingTop", "0px");
        DOM.setStyleAttribute(td, "paddingBottom", "0px");
        DOM.setStyleAttribute(td, "borderTop", "0px");
        DOM.setStyleAttribute(td, "borderBottom", "0px");
        DOM.appendChild(ghostRow, td);
        setColumnWidth(i, getColumnWidth(i));
      }
    } else if (numColumns < numGhosts) {
      int cellsToRemove = numGhosts - numColumns;
      for (int i = 0; i < cellsToRemove; i++) {
        Element td = getGhostCellElement(numColumns);
        DOM.removeChild(ghostRow, td);
      }
    }
  }
}
