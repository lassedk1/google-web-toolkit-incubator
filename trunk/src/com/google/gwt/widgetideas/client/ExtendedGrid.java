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
package com.google.gwt.widgetideas.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A variation of the {@link Grid} that supports many more options such
 * as lazy row and column creation, row movement, hovering, row selection,
 * and better column resizing.
 */
public class ExtendedGrid extends Grid {
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
      DOM.setStyleAttribute(grid.getGhostCellElement(column),
          "width", width + "px");
    }
  }
  
  /**
   * IE6 version of the implementation.  IE6 sets the overall column width
   * instead of the innerWidth, so we need to add the padding and spacing.
   */
  private static class ExtendedGridImplIE6 extends ExtendedGridImpl  {
    public void setColumnWidth(ExtendedGrid grid, int column, int width) {
      width += 2 * grid.getCellPadding() + grid.getCellSpacing();
      super.setColumnWidth(grid, column, width);
    }
  }
  
  /**
   * Safari version of the implementation.  Safari sets the overall column
   * width instead of the innerWidth, so we need to add the padding and
   * spacing.
   */
  private static class ExtendedGridImplSafari extends ExtendedGridImpl {
    public void setColumnWidth(ExtendedGrid grid, int column, int width) {
      width += 2 * grid.getCellPadding() + grid.getCellSpacing();
      super.setColumnWidth(grid, column, width);
    }
  }
  
  /**
   * Opera version of the implementation refreshes the table display so the
   * new column size takes effect.  Without the display refresh, the column
   * width doesn't update in the browser.
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
   * Event listener interface for {@link ExtendedGrid} events.
   */
  public abstract static interface ExtendedGridListener extends EventListener {
    /**
     * Fired when a row is deselected.
     * 
     * @param row the row index
     */
    public void onRowDeselected(int row);

    /**
     * Fired when a row is hovered.
     * 
     * @param row the row index
     */
    public void onRowHover(int row);
    
    /**
     * Fired when a row is selected.
     * 
     * @param row the row index
     */
    public void onRowSelected(int row);

    /**
     * Fired when a row is unhovered.
     * 
     * @param row the row index
     */
    public void onRowUnhover(int row);

    /**
     * Fired when the currently sorted column changes.
     * 
     * @param column the currently sorted column, -1 for unsorted
     * @param reversed specifies that this is a reverse sorting
     */
    public void onSetSortedColumn(int column, boolean reversed);
  }
  
  /**
   * This class contains methods used to format a table's cells.
   */
  public class ExtendedGridCellFormatter extends CellFormatter {
    /**
     * UnsupportedOperation.
     * Use ExtendedGrid.setColumnWidth(column, width) instead.
     * 
     * @param row the row of the cell whose width is to be set
     * @param column the cell whose width is to be set
     * @param width the cell's new width, in CSS units
     * @throws UnsupportedOperationException
     */
    public void setWidth(int row, int column, String width) {
      throw new UnsupportedOperationException(
          "setWidth is not supported.  " +
          "Use ExtendedGrid.setColumnWidth(int, int) instead.");
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
     * UnsupportedOperation.
     * Use ExtendedGrid.setColumnWidth(column, width) instead.
     * 
     * @param column the column of the cell whose width is to be set
     * @param width the cell's new width, in percentage or pixel units
     * @throws UnsupportedOperationException
     */
    public void setWidth(int column, String width) {
      throw new UnsupportedOperationException(
          "setWidth is not supported.  " +
          "Use ExtendedGrid.setColumnWidth(int, int) instead.");
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
   * Disable row selection.
   */
  public static final int SELECTION_POLICY_DISABLED = 0;

  /**
   * Allow multiple rows to be selected.
   */
  public static final int SELECTION_POLICY_MULTI_ROW = 1;

  /**
   * Allow only one row to be selected at a time.
   */
  public static final int SELECTION_POLICY_ONE_ROW = 2;

  /**
   * The implementation class.
   */
  private static ExtendedGridImpl impl = (ExtendedGridImpl) GWT.create(ExtendedGridImpl.class);
  
  /**
   * A mapping of column indexes to their widths in pixels.
   */
  private HashMap/*<Integer, Integer>*/ colWidths = new HashMap/*<Integer, Integer>*/();
  
  /**
   * The extended grid listeners attached to this grid.
   */
  private ArrayList/*<ExtendedGridListener>*/ extendedGridListeners = null;
  
  /**
   * The hidden, ghost row used for sizing the columns.
   */
  private Element ghostRow = DOM.createTR(); 
  
  /**
   * A boolean indicating whether or not hovering is enabled.
   */
  private boolean hoveringEnabled = true;
  
  /**
   * The current row being hovered.
   */
  private Element hoveringRow = null;
  
  /**
   * The index of the row currently being hovered.
   */
  private int hoveringRowIndex = -1;
  
  /**
   * The index of the row that the user selected last.
   */
  private int lastSelectedRowIndex = -1;
  
  /**
   * The minimum row index that can be hovered or selected.  Used to add
   * header rows to the grid.
   */
  private int minHoverRowIndex = 0;
  
  /**
   * The rows that are currently selected.
   */
  private Map/*<Integer, Element>*/ selectedRows =
    new HashMap/*<Integer, Element>*/();
  
  /**
   * The selection policy determines if the user can
   * select zero, one, or many rows.
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
    sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEDOWN);
  }

  /**
   * Add a listener to this grid.
   * 
   * @param listener the listener
   */
  public void addExtendedGridListener(ExtendedGridListener listener) {
    if (extendedGridListeners == null) {
      extendedGridListeners = new ArrayList/*<ExtendedGridListener>*/();
    }
    extendedGridListeners.add(listener);
  }
  
  /**
   * Stretches or shrinks the column to automatically fit its
   * data content.
   * 
   * @param column the column to fit
   * @return the new column width
   * @throws IndexOutOfBoundsException
   */
  public int autoFitColumnWidth(int column) {
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
      widthOffset = Math.max(widthOffset,
          DOM.getElementPropertyInt(getCellContainer(td), "offsetWidth") -
          contentVisibleWidth);
     
      // Scroll back if needed
      DOM.setElementPropertyInt(DOM.getFirstChild(td), "scrollLeft", 0);
    }

    // Set the new width
    int newWidth = columnWidth + widthOffset;
    setColumnWidth(column, newWidth);
    return newWidth;
  }
  
  /**
   * Replaces the contents of the specified cell with a single space.
   * 
   * @param row the cell's row
   * @param column the cell's column
   * @throws IndexOutOfBoundsException
   */
  public boolean clearCell(int row, int column) {
    Element span = getCellContainer(row, column, false);
    boolean b = super.internalClearCell(span, false);
    DOM.setInnerHTML(span, "&nbsp;");
    return b;
  }
  
  /**
   * Deselect a row in the data table.
   * 
   * @param row the row index
   */
  public void deselectRow(int row) {
    Element rowElem = (Element) selectedRows.remove(new Integer(row));
    if (rowElem != null) {
      // Update property
      DOM.setElementProperty(rowElem, "className", "");
      
      // Fire grid listeners
      if (extendedGridListeners != null) {
//        for (ExtendedGridListener listener : extendedGridListeners) {
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
    Iterator map_it = selectedRows.entrySet().iterator();
    while (map_it.hasNext()) {
      Map.Entry entry = (Map.Entry) map_it.next();
      int row = ((Integer) entry.getKey()).intValue();
      Element curRow = (Element) entry.getValue();
      DOM.setElementProperty(curRow, "className", "");
      
      // Fire grid listeners
      if (extendedGridListeners != null) {
        Iterator l_it = extendedGridListeners.iterator();
        while (l_it.hasNext()) {
          ExtendedGridListener listener = (ExtendedGridListener) l_it.next();
          listener.onRowDeselected(row);
        }
      }
    }
    selectedRows.clear();
  }
  
  /**
   * Return the column width for a given column index.  If a width
   * has not been assigned, the default width is returned.
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
   * Gets the HTML contents of the specified cell.
   * 
   * @param row the cell's row
   * @param column the cell's column
   * @return the cell's HTML contents
   * @throws IndexOutOfBoundsException
   */
  public String getHTML(int row, int column) {
    return DOM.getInnerHTML(getCellContainer(row, column, false));
  }
  
  /**
   * Returns a HashMap of the selected rows where the key is there
   * index as an Integer, and the value is the row Element.
   * 
   * @return the selected rows
   */
  public Map/*<Integer, Element>*/ getSelectedRows() {
    return selectedRows;
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
   * Gets the text within the specified cell.
   * 
   * @param row the cell's row
   * @param column the cell's column
   * @return the cell's text contents
   * @throws IndexOutOfBoundsException
   */
  public String getText(int row, int column) {
    return DOM.getInnerText(getCellContainer(row, column, false));
 }

  /**
   * Gets the widget in the specified cell.
   * 
   * @param row the cell's row
   * @param column the cell's column
   * @return the widget in the specified cell, or <code>null</code> if none is
   *         present
   * @throws IndexOutOfBoundsException
   */
  public Widget getWidget(int row, int column) {
    checkCellBounds(row, column);
    return getWidgetImpl(row, column);
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
   * Returns true if row hovering is enabled.
   * 
   * @return true if hovering enabled
   */
  public boolean isHoveringEnabled() {
    return hoveringEnabled;
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
    Element targetRow = getEventTargetRow(event);
    switch (DOM.eventGetType(event)) {
      // Highlight a row on mouse over
      case Event.ONMOUSEOVER:
        if (hoveringEnabled && (targetRow != hoveringRow)) {
          // Unhighlight the old row
          unhoverRow();
          
          // Highlight the new row
          if (targetRow != null) {
            hoverRow(targetRow);
          }
        }
        break;
      
      // Select a row on click
      case Event.ONMOUSEDOWN:
        if ((selectionPolicy != SELECTION_POLICY_DISABLED) &&
            (targetRow != null)) {
          // Clear out currently selected rows
          boolean ctrlKey = DOM.eventGetCtrlKey(event); 
          int targetRowIndex = DOM.getRowIndex(targetRow) - 1;
          if (!ctrlKey || !(selectionPolicy == SELECTION_POLICY_MULTI_ROW)) {
            deselectRows();
          }
          
          // Select rows
          if ((selectionPolicy == SELECTION_POLICY_MULTI_ROW) &&
              DOM.eventGetShiftKey(event) &&
              (lastSelectedRowIndex > -1)) {
            RowFormatter formatter = getRowFormatter();
            if (lastSelectedRowIndex > targetRowIndex) {
              for (int row = targetRowIndex; row <= lastSelectedRowIndex; row++) {
                selectRow(row, false);
              }
            } else {
              for (int row = lastSelectedRowIndex; row <= targetRowIndex; row++) {
                selectRow(row, false);
              }
            }
          } else if (selectedRows.containsKey(new Integer(targetRowIndex))) {
            deselectRow(targetRowIndex);
          } else {
            selectRow(targetRowIndex, false);
            lastSelectedRowIndex = targetRowIndex;
          }
        }
        break;
    }
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
    // Check the row bounds
    checkRowBounds(row);

    // Deselect current rows
    if (unselectAll) {
      deselectRows();
    }
    
    // Select the new row
    if (!unselectAll && selectedRows.containsKey(new Integer(row))) {
      // Ignore request if row already selected
      return;
    } else if (row >= minHoverRowIndex) {
      Element rowElem = getExtendedGridRowFormatter().getRawElement(row);
      selectedRows.put(new Integer(row), rowElem);
      DOM.setElementProperty(rowElem, "className", "gwt-ScrollTable-selected");
      
      // Fire grid listeners
      if (extendedGridListeners != null) {
//        for (ExtendedGridListener listener : extendedGridListeners) {
        Iterator it = extendedGridListeners.iterator();
        while (it.hasNext()) {
          ExtendedGridListener listener = (ExtendedGridListener) it.next();
          listener.onRowSelected(row);
        }
      }
    }
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
    width = Math.max(1, width);
    colWidths.put(new Integer(column), new Integer(width));
    
    // Update the cell width if possible
    if (column >= numColumns) {
      return;
    }
    
    // Set the actual column width
    impl.setColumnWidth(this, column, width);
  }
  
  /**
   * Set whether or not row hovering is enabled.  If enabled, a special
   * CSS style name will be applied to rows when the user hovers the
   * mouse over the row.
   * 
   * @param enableHovering true to enable hover, false to disable
   */
  public void setHoveringEnabled(boolean enableHovering) {
    hoveringEnabled = enableHovering;
    if (!hoveringEnabled) {
      unhoverRow();
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
    prepareCell(row, column);
    Element td = cleanCell(row, column, html == null);
    if (html != null) {
      DOM.setInnerHTML(getCellContainer(td), html);
    }

    // Mark as unsorted
    setSortedColumn(-1, false);
  }
    
  /**
   * Set the index of the first row that can be hovered or selected.
   * Use this option if the first one or more rows of your Grid are
   * used as headers.
   * 
   * @param minRow the min row that can be hovered
   */
  public void setMinHoverRow(int minRow) {
    minHoverRowIndex = minRow;
  }
 
  /**
   * Set the selection policy, which determines if the user can select
   * zero, one, or multiple rows.
   * 
   * @param selectionPolicy the selection policy
   */
  public void setSelectionPolicy(int selectionPolicy) {
    this.selectionPolicy = selectionPolicy;
    
    // Deselect rows as needed
    if ((selectionPolicy == SELECTION_POLICY_DISABLED) || 
        ((selectionPolicy == SELECTION_POLICY_ONE_ROW) &&
        (selectedRows.size() > 1))) {
      deselectRows();
    }
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
    prepareCell(row, column);
    Element td = cleanCell(row, column, text == null);
    if (text != null) {
      DOM.setInnerText(getCellContainer(td), text);
    }

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
    prepareCell(row, column);
    if (widget != null) {
      // Call this early to ensure that the table doesn't end up partially
      // constructed when an exception is thrown from adopt().
      widget.removeFromParent();

      // Attach it to the cell's TD.
      Element td = cleanCell(row, column, true);

      // Add the widget to the map.
      getWidgetMap().putWidget(widget);

      // Set the widget's parent.
      adopt(widget, getCellContainer(td));
    }

    // Mark as unsorted
    setSortedColumn(-1, false);
  }
  
  /**
   * Sort the grid according to the specified column.  If the
   * column is already sorted, reverse sort it.
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
    onSortColumn(column, reversed);
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
   * Get the container element from a cell.  The container
   * element is a span inside of the cell that actually holds
   * the content.
   * 
   * @param td the cell element
   * @return the container element
   */
  protected Element getCellContainer(Element td) {
    return DOM.getFirstChild(td);
  }
  
  /**
   * Get the container element from a cell.
   * 
   * @param row the cell row
   * @param column the cell column
   * @param raw do not check cell bounds
   * @return the container element
   */
  protected Element getCellContainer(int row, int column, boolean raw) {
    if (raw) {
      return getCellContainer(getExtendedGridCellFormatter().getRawElement(row, column));
    } else {
      return getCellContainer(getCellFormatter().getElement(row, column));
    }
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
   * {@link HTMLTable#getCellFormatter()} may also be downcast to a
   * {@link ExtendedGridCellFormatter}.
   * 
   * @return the ExtendedGrid's cell formatter
   */
  protected ExtendedGridCellFormatter getExtendedGridCellFormatter() {
    return (ExtendedGridCellFormatter) getCellFormatter();
  }
  
  /**
   * Explicitly gets the {@link ExtendedGridRowFormatter}. The results of
   * {@link HTMLTable#getCellFormatter()} may also be downcast to a
   * {@link ExtendedGridRowFormatter}.
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
   * Set the current hoverring row.
   * 
   * @param rowElem the row element
   */
  protected void hoverRow(Element rowElem) {
    int row = DOM.getRowIndex(rowElem) - 1;
    if (row >= minHoverRowIndex)  {
      // Highlight the row if not selected
      if (!selectedRows.containsKey(new Integer(row))) {
        DOM.setElementProperty(rowElem, "className",
            "gwt-ScrollTable-hover");
      }
      hoveringRow = rowElem;
      hoveringRowIndex = row;
      
      // Fire grid listeners
      if (extendedGridListeners != null) {
//        for (ExtendedGridListener listener : extendedGridListeners) {
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
   * Does actual clearing, used by clearCell and cleanCell. All HTMLTable
   * methods should use internalClearCell rather than clearCell, as clearCell
   * may be overridden in subclasses to format an empty cell.
   * 
   * Instead of clearing the cell, we actually clear the container
   * within it.
   * 
   * @param td element to clear
   * @param clearInnerHTML should the cell's inner html be cleared?
   * @return returns whether a widget was cleared
   */
  protected boolean internalClearCell(Element td, boolean clearInnerHTML) {
    return super.internalClearCell(getCellContainer(td), clearInnerHTML);
  }

  /**
   * Take an action when the user sorts a column.  Override this method
   * to implement a custom or server-side sorting algorithm.
   * 
   * By default, this method applies a quicksort algorithm to the
   * innerText component of every cell using a native string comparison.
   * 
   * @param column the column to sort
   * @param reversed reverse sort the column
   */
  protected void onSortColumn(int column, boolean reversed) {
    // Apply the default quicksort algorithm
    ExtendedGridCellFormatter formatter = getExtendedGridCellFormatter();
    Element[] tdElems = new Element[getRowCount()];
    for (int i = 0; i < tdElems.length; i++) {
      tdElems[i] = formatter.getRawElement(i, column);
    }
    onSortColumnQuickSort(tdElems, 0, tdElems.length - 1);
    
    // Convert tdElems to trElems
    for (int i = 0; i < tdElems.length; i++) {
      tdElems[i] = DOM.getParent(tdElems[i]);
    }

    // Use the callback to complete the sorting
    onSortColumnCallback(column, reversed, tdElems);
  }
 
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
  protected void onSortColumnCallback(int column, boolean reversed,
      int[] trIndexes) {
    // Convert indexes to row elements
    ExtendedGridRowFormatter formatter = getExtendedGridRowFormatter();
    Element[] trElems = new Element[trIndexes.length];
    for (int i = 0; i < trElems.length; i++) {
      trElems[i] = formatter.getRawElement(trIndexes[i]);
    }
    
    // Call main callback method
    onSortColumnCallback(column, reversed, trElems);
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
  protected void onSortColumnCallback(int column, boolean reversed,
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
   * Checks that the column index is valid.  ExtendedGrid lazily
   * creates columns as needed.
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
   * Checks that the row index is valid.  ExtendedGrid lazily
   * creates rows as needed.
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
//      for (ExtendedGridListener listener : extendedGridListeners) {
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
   * Unhighlight the currently hovering row.
   */
  protected void unhoverRow() {
    if (hoveringRow != null) {
      // Unhighlight the row
      int row = hoveringRowIndex;
      if (!selectedRows.containsKey(new Integer(hoveringRowIndex))) {
        DOM.setElementProperty(hoveringRow, "className", "");
      }
      hoveringRow = null;
      hoveringRowIndex = -1;
      
      // Fire grid listeners
      if (extendedGridListeners != null) {
//        for (ExtendedGridListener listener : extendedGridListeners) {
        Iterator it = extendedGridListeners.iterator();
        while (it.hasNext()) {
          ExtendedGridListener listener = (ExtendedGridListener) it.next();
          listener.onRowUnhover(row);
        }
      }
    }
  }
  
  /**
   * Appends a div and span container to a cell.  All cell
   * content is actually added to the container, which allows
   * for forceful control of the width of a column.
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
   * Get the row that is the target of this event.
   * 
   * @param event the event
   * @return the row Element
   */
  private Element getEventTargetRow(Event event) {
    Element target = DOM.eventGetTarget(event);
    while (target != null) {
      if (DOM.getElementProperty(target, "tagName").equalsIgnoreCase("tr")) {
        return target;
      }
      target = DOM.getParent(target);
    }
    
    // No row found
    return null;
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
   * Gets the Widget associated with the given cell.
   * 
   * @param row the cell's row
   * @param column the cell's column
   * @return the widget
   */
  private Widget getWidgetImpl(int row, int column) {
    Element e = getCellContainer(row, column, true);
    Element child = DOM.getFirstChild(e);
    if (child == null) {
      return null;
    } else {
      return getWidgetMap().getWidget(child);
    }
  }

  /**
   * Recursive quicksort algorithm used as the default column sorting
   * method.
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
  
  /**
   * Add or remove ghost cells when the table size changes.
   */
  private void updateGhostRow() {
    int numGhosts = getGhostColumnCount();
    if (numColumns > numGhosts) {
      // Add ghosts as needed
      for (int i = numGhosts; i < numColumns; i++) {
        Element td = DOM.createTD();
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