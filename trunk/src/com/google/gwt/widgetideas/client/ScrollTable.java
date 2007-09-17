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
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.ExtendedGrid.ExtendedGridListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * ScrollTable consists of a fixed header that remains visible and a scrollable
 * body that contains the data. The data portion is an {@link ExtendedGrid} and
 * the header is a {@link SizableFlexTable}, which allows for column and row
 * spanning.
 * </p>
 * 
 * <p>
 * In order for the columns in the header table and data table to line up, the
 * two table must have the same margin, padding, and border widths. You can use
 * CSS style sheets to manipulate the colors and styles of the cell's, but you
 * must keep the actual sizes consistent (especially with respect to the left
 * and right side of the cells).
 * </p>
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class="css">
 * <li> .gwt-ScrollTable { applied to the entire widget } </li>
 * <li> .gwt-ScrollTable .gwt-ScrollTable-header { applied to the header table }
 * </li>
 * <li> .gwt-ScrollTable .gwt-ScrollTable-header-wrapper { applied to the
 * wrapper around the header table } </li>
 * <li> .gwt-ScrollTable .gwt-ScrollTable-data { applied to the data table }
 * </li>
 * <li> .gwt-ScrollTable .gwt-ScrollTable-data-wrapper { applied to the wrapper
 * around the data table } </li>
 * </ul>
 */
public class ScrollTable extends ComplexPanel implements ResizableWidget {
  /**
   * A helper class that handles some of the mouse events associated with
   * resizing columns.
   */
  private static class MouseResizeWorker {
    /**
     * A Linked List Node that contains information about a column within the
     * colspan of the current cell including its offset from the current cell's
     * index and its original width.
     */
    private class ColumnNode {
      /**
       * The cell index.
       */
      private int cellIndex;

      /**
       * The original width of this cell.
       */
      private int originalWidth;

      public ColumnNode(int cellIndex, int originalWidth) {
        this.cellIndex = cellIndex;
        this.originalWidth = originalWidth;
      }

      public int getCellIndex() {
        return cellIndex;
      }

      public int getOriginalWidth() {
        return originalWidth;
      }
    }

    /**
     * The current header cell that the mouse is affecting.
     */
    private Element curCell = null;

    /**
     * The current cell's colSpan.
     */
    private int curCellColSpan = 0;

    /**
     * The current columns under the colSpan of the current cell, ordered from
     * narrowest to widest.
     */
    private List/* ColumnNode */curCellColumns = new ArrayList();

    /**
     * The index of the current header cell.
     */
    private int curCellIndex = 0;

    /**
     * The current x position of the mouse.
     */
    private int mouseXCurrent = 0;

    /**
     * The last x position of the mouse when we resized.
     */
    private int mouseXLast = 0;

    /**
     * The starting x position of the mouse when resizing a column.
     */
    private int mouseXStart = 0;

    /**
     * A timer used to resize the columns. As long as the timer is active, it
     * will poll for the new row size and resize the columns.
     */
    private Timer resizeTimer = new Timer() {
      public void run() {
        resizeColumn();
        schedule(100);
      }
    };

    /**
     * A boolean indicating that we are resizing the current header cell.
     */
    private boolean resizing = false;

    /**
     * The table that this worker affects.
     */
    private ScrollTable table = null;

    /**
     * Autofit the current cell's column plus any columns it spans.
     */
    public void autoFitCell() {
      if (curCell != null) {
        int colspan = DOM.getElementPropertyInt(curCell, "colSpan");
        for (int i = 0; i < colspan; i++) {
          final int actualColumn = curCellIndex + i;
          DeferredCommand.addCommand(new Command() {
            public void execute() {
              table.autoFitColumnWidth(actualColumn);
            }
          });
        }
      }
    }

    /**
     * Returns the current cell.
     * 
     * @return the current cell
     */
    public Element getCurrentCell() {
      return curCell;
    }

    /**
     * Returns true if a header is currently being resized.
     * 
     * @return true if resizing
     */
    public boolean isResizing() {
      return resizing;
    }

    /**
     * Resize the column on a mouse event. This method also marks the client as
     * busy so we do not try to change the size repeatedly.
     * 
     * @param event the mouse event
     */
    public void resizeColumn(Event event) {
      mouseXCurrent = DOM.eventGetClientX(event);
    }

    /**
     * Set the current cell that will be resized based on the mouse event.
     * 
     * @param event the event that triggered the new cell
     * @return true if the cell was actually changed
     */
    public boolean setCurrentCell(Event event) {
      // See if we are near the edge of the cell
      Element cell = table.headerTable.getEventTargetCell(event);
      int clientX = DOM.eventGetClientX(event);
      if (cell != null) {
        int absRight = DOM.getAbsoluteLeft(cell)
            + DOM.getElementPropertyInt(cell, "offsetWidth");
        if (clientX < absRight - 15 || clientX > absRight) {
          cell = null;
        }
      }

      // Change out the current cell
      if (!DOM.compare(cell, curCell)) {
        // Clear the old cell
        if (curCell != null) {
          DOM.setStyleAttribute(curCell, "cursor", "default");
        }

        // Set the new cell
        curCell = cell;
        if (curCell != null) {
          curCellIndex = getCellIndex(curCell);
          DOM.setStyleAttribute(curCell, "cursor", "e-resize");
        }
        return true;
      }

      // The cell did not change
      return false;
    }

    /**
     * Set the ScrollTable table that this worker affects.
     * 
     * @param table the scroll table
     */
    public void setScrollTable(ScrollTable table) {
      this.table = table;
    }

    /**
     * Start resizing the current cell when the user clicks on the right edge of
     * the cell.
     * 
     * @param event the mouse event
     */
    public void startResizing(Event event) {
      if (curCell != null) {
        resizing = true;
        mouseXStart = DOM.eventGetClientX(event);
        mouseXLast = mouseXStart;
        mouseXCurrent = mouseXStart;

        // Get the columns under this cell's colspan
        curCellColSpan = DOM.getElementPropertyInt(curCell, "colSpan");
        for (int i = 0; i < curCellColSpan; i++) {
          int newCellIndex = curCellIndex + i;
          int originalWidth = table.dataTable.getColumnWidth(newCellIndex);

          // Insert the node into the ordered list by width
          int insertIndex = 0;
          Iterator it = curCellColumns.iterator();
          while (it.hasNext()) {
            ColumnNode curNode = (ColumnNode) it.next();
            if (originalWidth > curNode.getOriginalWidth()) {
              insertIndex++;
            } else {
              break;
            }
          }

          // Add the node at the correct index
          curCellColumns.add(insertIndex, new ColumnNode(newCellIndex,
              originalWidth));
        }

        // Start the timer and listen for changes
        DOM.setCapture(table.getElement());
        resizeTimer.schedule(20);
      }
    }

    /**
     * Stop resizing the current cell.
     * 
     * @param event the mouse event
     */
    public void stopResizing(Event event) {
      if (curCell != null && resizing) {
        curCellColumns.clear();
        resizing = false;
        DOM.releaseCapture(table.getElement());
        resizeTimer.cancel();
        resizeColumn();
      }
    }

    /**
     * Get the scroll table.
     * 
     * @return the scroll table
     */
    protected ScrollTable getScrollTable() {
      return table;
    }

    /**
     * Get the actual cell index of a cell in the header table.
     * 
     * @param cell the cell element
     * @return the cell index
     */
    private int getCellIndex(Element cell) {
      int row = DOM.getRowIndex(DOM.getParent(cell)) - 1;
      int column = DOM.getCellIndex(cell);
      return table.headerTable.getFlexCellFormatter().getColumnIndex(row,
          column);
    }

    /**
     * Helper method that actually sets the column size. This method is called
     * periodically by a timer.
     */
    private void resizeColumn() {
      if (mouseXLast != mouseXCurrent) {
        mouseXLast = mouseXCurrent;

        int totalDelta = mouseXCurrent - mouseXStart;
        int colSpanRemaining = curCellColSpan;
        Iterator it = curCellColumns.iterator();
        while (it.hasNext()) {
          ColumnNode curNode = (ColumnNode) it.next();
          int originalWidth = curNode.getOriginalWidth();
          int column = curNode.getCellIndex();
          int delta = totalDelta / colSpanRemaining;
          int colWidth = table.setColumnWidth(column, originalWidth + delta);

          totalDelta -= (colWidth - originalWidth);
          colSpanRemaining--;
        }
      }
    }
  }

  /**
   * The Opera version of the mouse worker fixes an Opera bug where the cursor
   * isn't updated if the mouse is hovering over an element DOM object when its
   * cursor style is changed.
   */
  private static class MouseResizeWorkerOpera extends MouseResizeWorker {
    /**
     * A div used to force the cursor to update.
     */
    private Element cursorUpdateDiv;

    /**
     * Constructor.
     */
    public MouseResizeWorkerOpera() {
      cursorUpdateDiv = DOM.createDiv();
      DOM.setStyleAttribute(cursorUpdateDiv, "position", "absolute");
    }

    /**
     * Set the current cell that will be resized based on the mouse event.
     * 
     * @param event the event that triggered the new cell
     * @return true if the cell was actually changed
     */
    public boolean setCurrentCell(Event event) {
      // Check if cursor update div is active
      if (DOM.compare(DOM.eventGetTarget(event), cursorUpdateDiv)) {
        removeCursorUpdateDiv();
        return false;
      }

      // Use the parent method
      boolean cellChanged = super.setCurrentCell(event);

      // Position a div that forces a cursor redraw in Opera
      if (cellChanged) {
        DOM.setCapture(getScrollTable().getElement());
        DOM.setStyleAttribute(cursorUpdateDiv, "height",
            (Window.getClientHeight() - 1) + "px");
        DOM.setStyleAttribute(cursorUpdateDiv, "width",
            (Window.getClientWidth() - 1) + "px");
        DOM.setStyleAttribute(cursorUpdateDiv, "left", "0px");
        DOM.setStyleAttribute(cursorUpdateDiv, "top", "0px");
        DOM.appendChild(RootPanel.getBodyElement(), cursorUpdateDiv);
      }
      return cellChanged;
    }

    /**
     * Start resizing the current cell.
     * 
     * @param event the mouse event
     */
    public void startResizing(Event event) {
      removeCursorUpdateDiv();
      super.startResizing(event);
    }

    /**
     * Remove the cursor update div from the page.
     */
    private void removeCursorUpdateDiv() {
      if (DOM.getCaptureElement() != null) {
        DOM.removeChild(RootPanel.getBodyElement(), cursorUpdateDiv);
        DOM.releaseCapture(getScrollTable().getElement());
      }
    }
  }

  /**
   * A modified version of the {@link SizableFlexTable} used to add some
   * required features.
   */
  private class ScrollSizableFlexTable extends SizableFlexTable {
    /**
     * Determines the TD associated with the specified event.
     * 
     * @param event the event to be queried
     * @return the TD associated with the event, or <code>null</code> if none
     *         is found.
     */
    public Element getEventTargetCell(Event event) {
      return super.getEventTargetCell(event);
    }

    /**
     * Inserts a cell into the FlexTable.
     * 
     * @param beforeRow the cell's row
     * @param beforeColumn the cell's column
     * @return the element
     */
    public Element insertCell(int beforeRow, int beforeColumn) {
      Element cell = super.insertCell(beforeRow, beforeColumn);
      resizeTablesVertically();
      return cell;
    }

    /**
     * Inserts a new row into the table.
     * 
     * @param beforeRow the index before which the new row will be inserted
     * @return the index of the newly-created row
     * @throws IndexOutOfBoundsException
     */
    public int insertRow(int beforeRow) {
      int row = super.insertRow(beforeRow);
      resizeTablesVertically();
      return row;
    }

    /**
     * @see com.google.gwt.user.client.ui.HTMLTable#removeCell(int, int)
     */
    public void removeCell(int row, int col) {
      super.removeCell(row, col);
      resizeTablesVertically();
    }

    /**
     * Removes the specified row from the table.
     * 
     * @param row the index of the row to be removed
     * @throws IndexOutOfBoundsException
     */
    public void removeRow(int row) {
      super.removeRow(row);
      resizeTablesVertically();
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
      resizeTablesVertically();
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
      resizeTablesVertically();
    }

    /**
     * Sets the widget within the specified cell.
     * <p>
     * Inherited implementations may either throw IndexOutOfBounds exception if
     * the cell does not exist, or allocate a new cell to store the content.
     * </p>
     * <p>
     * FlexTable will automatically allocate the cell at the correct location
     * and then set the widget. Grid will set the widget if and only if the cell
     * is within the Grid's bounding box.
     * </p>
     * 
     * @param widget The widget to be added
     * @param row the cell's row
     * @param column the cell's column
     * @throws IndexOutOfBoundsException
     */
    public void setWidget(int row, int column, Widget widget) {
      super.setWidget(row, column, widget);
      resizeTablesVertically();
    }

    /**
     * Ensure that the cell exists.
     * 
     * @param row the row to prepare.
     * @param column the column to prepare.
     * @throws IndexOutOfBoundsException if the row is negative
     */
    protected void prepareCell(int row, int column) {
      int curNumGhosts = getGhostColumnCount();
      super.prepareCell(row, column);

      // Set the correct column width as ghosts are added
      int numGhosts = getGhostColumnCount();
      if (numGhosts > curNumGhosts) {
        for (int i = curNumGhosts; i < numGhosts; i++) {
          headerTable.setColumnWidth(i, dataTable.getColumnWidth(i));
        }
      }
    }

    /**
     * Ensure that the row exists.
     * 
     * @param row The row to prepare.
     * @throws IndexOutOfBoundsException if the row is negative
     */
    protected void prepareRow(int row) {
      super.prepareRow(row);
      resizeTablesVertically();
    }
  }

  /**
   * An {@link ImageBundle} that provides images for {@link ScrollTable}.
   */
  public static interface ScrollTableImages extends ImageBundle {

    /**
     * An image indicating that a column is sorted in ascending order.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype scrollTableAscending();

    /**
     * An image indicating a column is sorted in descending order.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype scrollTableDescending();
  }

  /**
   * The default width of a column in pixels.
   */
  public static final int DEFAULT_COLUMN_WIDTH = 80;

  /**
   * The data table.
   */
  private ExtendedGrid dataTable;

  /**
   * The scrollable wrapper div around the data table.
   */
  private Element dataWrapper;

  /**
   * The extended grid listener added to the dataTable.
   */
  private ExtendedGridListener dataTableListener = new ExtendedGridListener() {
    public void onRowDeselected(int row) {
    }

    public void onRowHover(int row) {
    }

    public void onRowSelected(int row) {
    }

    public void onRowUnhover(int row) {
    }

    /**
     * Fired when the currently sorted column changes.
     * 
     * @param column the currently sorted column, -1 for unsorted
     * @param reversed specifies that this is a reverse sorting
     */
    public void onSetSortedColumn(int column, boolean reversed) {
      // Remove the sorted column indicator
      if (sortingEnabled) {
        Element parent = DOM.getParent(sortedColumnWrapper);
        if (parent != null) {
          DOM.removeChild(parent, sortedColumnWrapper);
        }

        // Re-add the sorted column indicator
        if (column < 0) {
          sortedColumnTrigger = null;
        } else if (sortedColumnTrigger != null) {
          DOM.appendChild(sortedColumnTrigger, sortedColumnWrapper);
          if (reversed) {
            images.scrollTableDescending().applyTo(sortedColumnIndicator);
          } else {
            images.scrollTableAscending().applyTo(sortedColumnIndicator);
          }
        }
      }
    }
  };

  /**
   * A spacer used to stretch the headerTable area so we can scroll past the
   * edge of the header table.
   */
  private Element headerSpacer;

  /**
   * The header table.
   */
  private ScrollSizableFlexTable headerTable = new ScrollSizableFlexTable();

  /**
   * The non-scrollable wrapper div around the header table.
   */
  private Element headerWrapper;

  /**
   * The images used in the scroll table.
   */
  private ScrollTableImages images;

  /**
   * The worker that helps with mouse resize events.
   */
  private MouseResizeWorker resizeWorker = (MouseResizeWorker) GWT.create(MouseResizeWorker.class);

  /**
   * A Deferred command used to resize tables vertically. Using this command
   * ensures that the tables don't resize until the client has time to modify
   * rendering.
   */
  private Command resizeTablesVerticallyCommand = new Command() {
    public void execute() {
      resizeTablesVerticallyNow();
    }
  };

  /**
   * The Image use to indicate the currently sorted column.
   */
  private Image sortedColumnIndicator = new Image();

  /**
   * The TD cell that initiated a column sort operation.
   */
  private Element sortedColumnTrigger = null;

  /**
   * The wrapper around the image indicator.
   */
  private Element sortedColumnWrapper = null;

  /**
   * A boolean indicating whether or not sorting is enabled.
   */
  private boolean sortingEnabled = true;

  /**
   * Constructor.
   */
  public ScrollTable() {
    this(new ExtendedGrid());
  }

  /**
   * Constructor.
   * 
   * @param dataTable the data table
   */
  public ScrollTable(ExtendedGrid dataTable) {
    this(dataTable, (ScrollTableImages) GWT.create(ScrollTableImages.class));
  }

  /**
   * Constructor.
   * 
   * @param images the images to use in the table
   */
  public ScrollTable(ScrollTableImages images) {
    this(new ExtendedGrid(), images);
  }

  /**
   * Constructor.
   * 
   * @param dataTable the data table
   * @param images the images to use in the table
   */
  public ScrollTable(ExtendedGrid dataTable, ScrollTableImages images) {
    super();
    this.dataTable = dataTable;
    this.images = images;
    resizeWorker.setScrollTable(this);

    // Setup the dataTable
    Element dataTableElem = dataTable.getElement();
    DOM.setStyleAttribute(dataTableElem, "margin", "0px");
    DOM.setStyleAttribute(dataTableElem, "border", "0px");
    dataTable.setStyleName("gwt-ScrollTable-data");
    dataTable.addExtendedGridListener(dataTableListener);

    // Setup the header table
    Element headerTableElem = headerTable.getElement();
    DOM.setStyleAttribute(headerTableElem, "margin", "0px");
    DOM.setStyleAttribute(headerTableElem, "border", "0px");
    headerTable.setStyleName("gwt-ScrollTable-header");

    // Create a div container
    Element mainElem = DOM.createDiv();
    setElement(mainElem);
    setStyleName("gwt-ScrollTable");
    DOM.setStyleAttribute(mainElem, "padding", "0px");
    DOM.setStyleAttribute(mainElem, "overflow", "hidden");
    DOM.setStyleAttribute(mainElem, "position", "relative");

    // Setup the header wrapper
    headerWrapper = DOM.createDiv();
    DOM.setStyleAttribute(headerWrapper, "width", "100%");
    DOM.setStyleAttribute(headerWrapper, "overflow", "hidden");
    DOM.setStyleAttribute(headerWrapper, "position", "relative");
    DOM.setElementProperty(headerWrapper, "className",
        "gwt-ScrollTable-header-wrapper");

    // Setup the data table Wrapper
    dataWrapper = DOM.createDiv();
    DOM.setStyleAttribute(dataWrapper, "width", "100%");
    DOM.setStyleAttribute(dataWrapper, "overflow", "auto");
    DOM.setStyleAttribute(dataWrapper, "position", "relative");
    DOM.setElementProperty(dataWrapper, "className",
        "gwt-ScrollTable-data-wrapper");
    DOM.appendChild(mainElem, dataWrapper);
    
    // Create the header extra spacer
    headerSpacer = DOM.createDiv();
    DOM.setStyleAttribute(headerSpacer, "height", "1px");
    DOM.setStyleAttribute(headerSpacer, "width", "10000px");
    DOM.setStyleAttribute(headerSpacer, "position", "absolute");
    DOM.setStyleAttribute(headerSpacer, "top", "1px");
    DOM.setStyleAttribute(headerSpacer, "left", "1px");
    DOM.appendChild(headerWrapper, headerSpacer);
    
    // Adopt the header table into the panel
    getChildren().add(headerTable);
    DOM.appendChild(mainElem, headerWrapper);
    DOM.appendChild(headerWrapper, headerTableElem);
    adopt(headerTable);

    // Adopt the data table into the panel
    getChildren().add(dataTable);
    DOM.appendChild(mainElem, dataWrapper);
    DOM.appendChild(dataWrapper, dataTableElem);
    adopt(dataTable);

    // Create the sort indicator Image
    sortedColumnWrapper = DOM.createSpan();
    DOM.setInnerHTML(sortedColumnWrapper, "&nbsp;");
    DOM.appendChild(sortedColumnWrapper, sortedColumnIndicator.getElement());

    // Add some event handling
    DOM.setEventListener(dataWrapper, this);
    DOM.sinkEvents(dataWrapper, Event.ONSCROLL);
    sinkEvents(Event.MOUSEEVENTS | Event.ONDBLCLICK | Event.ONCLICK);

    // Add to Resizable Collection
    ResizableWidgetCollection.get().add(this);
  }

  /**
   * Stretches or shrinks the column to automatically fit its data content.
   * 
   * @param column the column to fit
   * @throws IndexOutOfBoundsException
   */
  public void autoFitColumnWidth(int column) {
    int width = dataTable.autoFitColumnWidth(column);
    headerTable.setColumnWidth(column, width);
    repositionHeaderSpacer();
    scrollTables(false);
  }

  /**
   * Gets the amount of padding that is added around all cells.
   * 
   * @return the cell padding, in pixels
   */
  public int getCellPadding() {
    return dataTable.getCellPadding();
  }

  /**
   * Gets the amount of spacing that is added around all cells.
   * 
   * @return the cell spacing, in pixels
   */
  public int getCellSpacing() {
    return dataTable.getCellSpacing();
  }

  /**
   * Return the column width for a given column index.
   * 
   * @param column the column index
   * @return the column width in pixels
   */
  public int getColumnWidth(int column) {
    return dataTable.getColumnWidth(column);
  }

  /**
   * Get the data table.
   * 
   * @return the data table
   */
  public ExtendedGrid getDataTable() {
    return dataTable;
  }

  /**
   * Get the header table.
   * 
   * @return the header table
   */
  public SizableFlexTable getHeaderTable() {
    return headerTable;
  }

  /**
   * Returns true if column sorting is currently enabled.
   * 
   * @return true if sorting is enabled
   */
  public boolean isSortingEnabled() {
    return sortingEnabled;
  }

  /**
   * Method to process events generated from the browser.
   * 
   * @param event the generated event
   */
  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);
    Element target = DOM.eventGetTarget(event);
    switch (DOM.eventGetType(event)) {
      case Event.ONSCROLL:
        // Reposition the tables on scroll
        scrollTables(false);
        break;

      case Event.ONMOUSEDOWN:
        // Start resizing a header column
        if (resizeWorker.getCurrentCell() != null) {
          DOM.eventPreventDefault(event);
          DOM.eventCancelBubble(event, true);
          resizeWorker.startResizing(event);
        }
        break;
      case Event.ONMOUSEUP:
        // Stop resizing the header column
        if (resizeWorker.isResizing()) {
          resizeWorker.stopResizing(event);
        } else {
          // Scroll tables if needed
          if (DOM.isOrHasChild(headerWrapper, target)) {
            scrollTables(true);
          } else {
            scrollTables(false);
          }

          // Sort column on click
          if (sortingEnabled) {
            Element cellElem = headerTable.getEventTargetCell(event);
            if (cellElem != null) {
              int row = DOM.getRowIndex(DOM.getParent(cellElem)) - 1;
              int cell = DOM.getCellIndex(cellElem);
              int column = headerTable.getFlexCellFormatter().getColumnIndex(
                  row, cell);
              sortedColumnTrigger = cellElem;
              dataTable.sortColumn(column);
            }
          }
        }
        break;
      case Event.ONMOUSEMOVE:
        // Resize the header column
        if (resizeWorker.isResizing()) {
          resizeWorker.resizeColumn(event);
        } else {
          resizeWorker.setCurrentCell(event);
        }
        break;

      // Disable data row highlighting
      case Event.ONMOUSEOVER:
        if (!DOM.isOrHasChild(dataTable.getElement(), target)) {
          dataTable.unhoverRow();
        }
        break;
      case Event.ONMOUSEOUT:
        if (!DOM.isOrHasChild(dataTable.getElement(), target)) {
          dataTable.unhoverRow();
        }
        break;

      // Auto resize the column on double click
      case Event.ONDBLCLICK:
        if (resizeWorker.getCurrentCell() != null) {
          DOM.eventPreventDefault(event);
          DOM.eventCancelBubble(event, true);
          resizeWorker.autoFitCell();
        }
        break;
    }
  }

  /**
   * This method is called when the dimensions of the parent element change.
   * Subclasses should override this method as needed.
   * 
   * @param width the new client width of the element
   * @param height the new client height of the element
   */
  public void onResize(int width, int height) {
    resizeTablesVertically();
  }

  /**
   * Unsupported.
   * 
   * @param child the widget to be removed
   * @return false
   * @throws UnsupportedOperationException
   */
  public boolean remove(Widget child) {
    throw new UnsupportedOperationException(
        "This panel does not support remove()");
  }

  /**
   * Sets the amount of padding to be added around all cells.
   * 
   * @param padding the cell padding, in pixels
   */
  public void setCellPadding(int padding) {
    headerTable.setCellPadding(padding);
    dataTable.setCellPadding(padding);
  }

  /**
   * Sets the amount of spacing to be added around all cells.
   * 
   * @param spacing the cell spacing, in pixels
   */
  public void setCellSpacing(int spacing) {
    headerTable.setCellSpacing(spacing);
    dataTable.setCellSpacing(spacing);
  }

  /**
   * Set the width of a column.
   * 
   * @param column the index of the column
   * @param width the width in pixels
   * @return the new column width
   */
  public int setColumnWidth(int column, int width) {
    width = Math.max(width, 1);
    dataTable.setColumnWidth(column, width);
    headerTable.setColumnWidth(column, width);
    repositionHeaderSpacer();
    scrollTables(false);
    return width;
  }

  /**
   * Enable or disable column sorting via mouse clicks to the header cells.
   * 
   * @param sortingEnabled true to enable column sorting via mouse events
   */
  public void setSortingEnabled(boolean sortingEnabled) {
    this.sortingEnabled = sortingEnabled;

    // Remove the sorted indicator image
    Element parent = DOM.getParent(sortedColumnWrapper);
    if (parent != null) {
      DOM.removeChild(parent, sortedColumnWrapper);
    }
  }

  /**
   * Get the data wrapper Element.
   * 
   * @return the dataWrapper
   */
  protected Element getDataWrapper() {
    return dataWrapper;
  }

  /**
   * Get the header wrapper Element.
   * 
   * @return the headerWrapper
   */
  protected Element getHeaderWrapper() {
    return headerWrapper;
  }

  /**
   * This method is called when a widget is attached to the browser's document.
   * To receive notification after a Widget has been added from the document,
   * override the onLoad method.
   * 
   * <p>
   * Subclasses that override this method must call
   * <code>super.onAttach()</code> to ensure that the Widget has been attached
   * to the underlying Element.
   * </p>
   * 
   * @throws IllegalStateException if this widget is already attached
   */
  protected void onAttach() {
    super.onAttach();
    resizeTablesVertically();
    repositionHeaderSpacer();
  }

  /**
   * Fixes the table heights so the header is visible and the data takes up the
   * remaining vertical space.
   */
  protected void resizeTablesVertically() {
    DeferredCommand.addCommand(resizeTablesVerticallyCommand);
  }

  /**
   * Helper method that actually performs the vertical resizing.
   */
  protected void resizeTablesVerticallyNow() {
    int totalHeight = DOM.getElementPropertyInt(getElement(), "clientHeight");
    int headerHeight = headerTable.getOffsetHeight();
    DOM.setStyleAttribute(headerWrapper, "height", headerHeight + "px");
    DOM.setStyleAttribute(dataWrapper, "height", (totalHeight - headerHeight)
        + "px");
    DOM.setStyleAttribute(dataWrapper, "width", "100%");
    DOM.setStyleAttribute(dataWrapper, "overflow", "hidden");
    DOM.setStyleAttribute(dataWrapper, "overflow", "auto");
    scrollTables(true);
  }

  /**
   * Sets the scroll property of the header and data wrappers when scrolling.
   * 
   * @param baseHeader If true, use the header as the alignment source
   */
  protected void scrollTables(boolean baseHeader) {
    if (isAttached()) {
      if (baseHeader) {
        DOM.setElementPropertyInt(dataWrapper, "scrollLeft",
            DOM.getElementPropertyInt(headerWrapper, "scrollLeft"));
      }
      DOM.setElementPropertyInt(headerWrapper, "scrollLeft",
          DOM.getElementPropertyInt(dataWrapper, "scrollLeft"));
    }
  }

  /**
   * Reposition the header spacer next to the header table.
   */
  private void repositionHeaderSpacer() {
    DOM.setStyleAttribute(headerSpacer, "left", headerTable.getOffsetWidth()
        + "px");
  }
}
