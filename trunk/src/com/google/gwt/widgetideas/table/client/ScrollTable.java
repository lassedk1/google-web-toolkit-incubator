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
import com.google.gwt.widgetideas.client.ResizableWidget;
import com.google.gwt.widgetideas.client.ResizableWidgetCollection;
import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortList;
import com.google.gwt.widgetideas.table.client.overrides.OverrideDOM;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * ScrollTable consists of a fixed header and footer (optional) that remain
 * visible and a scrollable body that contains the data.
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
 * <li> .gwt-ScrollTable-header { applied to the header table }
 * <li> .gwt-ScrollTable-data { applied to the data table }
 * <li> .gwt-ScrollTable-footer { applied to the footer table }
 * <li> .gwt-ScrollTable-header-wrapper { wrapper around the header table }</li>
 * <li> .gwt-ScrollTable-data-wrapper { wrapper around the data table }</li>
 * <li> .gwt-ScrollTable-footer-wrapper { wrapper around the footer table }</li>
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
      if (curCell == null || !table.isAutoFitEnabled()) {
        return;
      }

      // Make sure we have a sacrifice column if needed
      HasFixedColumnWidth headerTable = table.getHeaderTable();
      HasAutoFitColumn dataTable = (HasAutoFitColumn) table.getDataTable();
      HasFixedColumnWidth footerTable = table.getFooterTable();
      int colspan = DOM.getElementPropertyInt(curCell, "colSpan");
      int sacrificeColumn = curCellIndex + colspan;
      int resizePolicy = table.getResizePolicy();
      if (resizePolicy == RESIZE_POLICY_FIXED_WIDTH
          || resizePolicy == RESIZE_POLICY_FILL_WIDTH) {
        if (sacrificeColumn >= dataTable.getColumnCount()) {
          return;
        }
      }

      // Calculate new sizes and total difference
      int[] newWidths = new int[colspan];
      int diff = 0;
      for (int i = 0; i < colspan; i++) {
        int actualColumn = curCellIndex + i;
        newWidths[i] = dataTable.getAutoFitColumnWidth(actualColumn);
        diff += (newWidths[i] - table.getColumnWidth(actualColumn));
      }

      // Redistribute the width
      if (resizePolicy == RESIZE_POLICY_FLOW) {
        table.redistributeWidth(-diff, sacrificeColumn);
        diff = 0;
      } else if (resizePolicy != RESIZE_POLICY_UNCONSTRAINED) {
        diff += table.redistributeWidth(-diff, sacrificeColumn);
      }

      // Resize the columns
      for (int i = 0; i < colspan; i++) {
        int actualColumn = curCellIndex + i;
        int unclaimedDiff = diff / (colspan - i);
        newWidths[i] -= unclaimedDiff;
        dataTable.setColumnWidth(actualColumn, newWidths[i]);
        headerTable.setColumnWidth(actualColumn, newWidths[i]);
        if (footerTable != null) {
          footerTable.setColumnWidth(actualColumn, newWidths[i]);
        }
        diff -= unclaimedDiff;
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
     * Get the width of the scrollbars.
     * 
     * @return the scroll bar width
     */
    protected int getScrollBarWidth() {
      return 15;
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
      int row = OverrideDOM.getRowIndex(DOM.getParent(cell)) - 1;
      int column = OverrideDOM.getCellIndex(cell);
      if (table.headerTable instanceof HasCellSpans) {
        return ((HasCellSpans) table.headerTable).getColumnIndex(row, column);
      } else {
        return column;
      }
    }

    /**
     * Helper method that actually sets the column size. This method is called
     * periodically by a timer.
     */
    private void resizeColumn() {
      if (mouseXLast != mouseXCurrent) {
        mouseXLast = mouseXCurrent;

        int sacrificeColumn = curCellIndex + curCellColSpan;
        int totalDelta = mouseXCurrent - mouseXStart;
        int colSpanRemaining = curCellColSpan;
        Iterator it = curCellColumns.iterator();
        while (it.hasNext()) {
          ColumnNode curNode = (ColumnNode) it.next();
          int originalWidth = curNode.getOriginalWidth();
          int column = curNode.getCellIndex();
          int delta = totalDelta / colSpanRemaining;
          int colWidth = table.setColumnWidth(column, originalWidth + delta,
              sacrificeColumn);

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
   * An {@link ImageBundle} that provides images for {@link ScrollTable}.
   */
  public static interface ScrollTableImages extends ImageBundle {
    /**
     * An image used to fill the available width.
     * 
     * @return a prototype of this image
     */
    AbstractImagePrototype fillWidth();

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
   * The default style name.
   */
  public static final String DEFAULT_STYLE_NAME = "gwt-ScrollTable";

  /**
   * Columns will expand and shrink independently of each other.
   */
  public static final int RESIZE_POLICY_UNCONSTRAINED = 0;

  /**
   * As one column expands or shrinks, the columns to the right will do the
   * opposite, trying to maintain the same size. The user can still expand the
   * grid if there is no more space to take from the columns on the right.
   */
  public static final int RESIZE_POLICY_FLOW = 1;

  /**
   * Same as FIXED_WIDTH, but the grid will always fill the available width,
   * even if the widget is resized.
   */
  public static final int RESIZE_POLICY_FILL_WIDTH = 2;

  /**
   * As one column expands or shrinks, the columns to the right will do the
   * opposite, trying to maintain the same size. The width of the grid will
   * remain constant, ignoring column resizing that would result in the grid
   * growing in size.
   */
  public static final int RESIZE_POLICY_FIXED_WIDTH = 3;

  /**
   * A boolean indicating whether or not column auto resizing is enabled.
   */
  private boolean autoFitEnabled = true;

  /**
   * The data table.
   */
  private HasFixedColumnWidth dataTable;

  /**
   * The scrollable wrapper div around the data table.
   */
  private Element dataWrapper;

  /**
   * An image used to show a fill width button.
   */
  private Image fillWidthImage;

  /**
   * A spacer used to stretch the footerTable area so we can scroll past the
   * edge of the footer table.
   */
  private Element footerSpacer = null;

  /**
   * The footer table.
   */
  private HasFixedColumnWidth footerTable = null;

  /**
   * The non-scrollable wrapper div around the footer table.
   */
  private Element footerWrapper = null;

  /**
   * A spacer used to stretch the headerTable area so we can scroll past the
   * edge of the header table.
   */
  private Element headerSpacer;

  /**
   * The header table.
   */
  private HasFixedColumnWidth headerTable = null;

  /**
   * The non-scrollable wrapper div around the header table.
   */
  private Element headerWrapper;

  /**
   * The last known height of this widget that the user set.
   */
  private String lastHeight = null;

  /**
   * A boolean indicating whether or not the grid should try to maintain its
   * width as much as possible.
   */
  private int resizePolicy = RESIZE_POLICY_FLOW;

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
   * A boolean indicating whether or not scrolling is enabled.
   */
  private boolean scrollingEnabled = true;

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
   * 
   * @param dataTable the data table
   * @param headerTable the header table
   */
  public ScrollTable(HasFixedColumnWidth dataTable,
      HasFixedColumnWidth headerTable) {
    this(dataTable, headerTable,
        (ScrollTableImages) GWT.create(ScrollTableImages.class));
  }

  /**
   * Constructor.
   * 
   * @param dataTable the data table
   * @param headerTable the header table
   * @param images the images to use in the table
   */
  public ScrollTable(HasFixedColumnWidth dataTable,
      HasFixedColumnWidth headerTable, final ScrollTableImages images) {
    super();
    this.dataTable = dataTable;
    this.headerTable = headerTable;
    resizeWorker.setScrollTable(this);

    // Prepare the header and data tables
    prepareTable((Widget) dataTable, "data");
    prepareTable((Widget) headerTable, "header");

    // Create the main div container
    Element mainElem = DOM.createDiv();
    setElement(mainElem);
    setStylePrimaryName(DEFAULT_STYLE_NAME);
    DOM.setStyleAttribute(mainElem, "padding", "0px");
    DOM.setStyleAttribute(mainElem, "overflow", "hidden");
    DOM.setStyleAttribute(mainElem, "position", "relative");

    // Create the table wrapper and spacer
    headerWrapper = createWrapper("header-wrapper");
    headerSpacer = createSpacer(headerWrapper);
    dataWrapper = createWrapper("data-wrapper");

    // Create image to fill width
    fillWidthImage = new Image() {
      public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);
        if (DOM.eventGetType(event) == Event.ONCLICK) {
          fillWidth();
        }
      }
    };
    fillWidthImage.setTitle("Shrink/Expand to fill visible area");
    images.fillWidth().applyTo(fillWidthImage);
    Element fillWidthImageElem = fillWidthImage.getElement();
    DOM.setStyleAttribute(fillWidthImageElem, "cursor", "pointer");
    DOM.setStyleAttribute(fillWidthImageElem, "position", "absolute");
    DOM.setStyleAttribute(fillWidthImageElem, "top", "0px");
    DOM.setStyleAttribute(fillWidthImageElem, "right", "0px");
    DOM.setStyleAttribute(fillWidthImageElem, "zIndex", "1");
    add(fillWidthImage, getElement());

    // Adopt the header and data tables into the panel
    adoptTable((Widget) headerTable, headerWrapper, 1);
    adoptTable((Widget) dataTable, dataWrapper, 2);

    // Create the sort indicator Image
    sortedColumnWrapper = DOM.createSpan();
    DOM.setInnerHTML(sortedColumnWrapper, "&nbsp;");
    DOM.appendChild(sortedColumnWrapper, sortedColumnIndicator.getElement());

    // Add some event handling
    DOM.setEventListener(dataWrapper, this);
    DOM.sinkEvents(dataWrapper, Event.ONSCROLL);
    sinkEvents(Event.MOUSEEVENTS | Event.ONDBLCLICK | Event.ONCLICK);

    // Listen for sorting events in the data table
    if (dataTable instanceof HasSortableColumns) {
      ((HasSortableColumns) dataTable).addSortableColumnsListener(new SortableColumnsListener() {
        public void onColumnSorted(ColumnSortList sortList) {
          // Get the primary column and sort order
          int column = -1;
          boolean ascending = true;
          if (sortList != null) {
            column = sortList.getPrimaryColumn();
            ascending = sortList.isPrimaryAscending();
          }

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
              if (ascending) {
                images.scrollTableAscending().applyTo(sortedColumnIndicator);
              } else {
                images.scrollTableDescending().applyTo(sortedColumnIndicator);
              }
              sortedColumnTrigger = null;
            }
          }
        }
      });
    }

    // Add to Resizable Collection
    ResizableWidgetCollection.get().add(this);

    // Set the default supported operations
    try {
      setSortingEnabled(sortingEnabled);
    } catch (UnsupportedOperationException e) {
      // Ignore, this may not be implemented
    }
    try {
      setAutoFitEnabled(autoFitEnabled);
    } catch (UnsupportedOperationException e) {
      // Ignore, this may not be implemented
    }
  }

  /**
   * Stretches or shrinks the column to automatically fit its data content.
   * 
   * @param column the column to fit
   * @throws IndexOutOfBoundsException
   */
  public void autoFitColumnWidth(int column) {
    if (dataTable instanceof HasAutoFitColumn) {
      int width = ((HasAutoFitColumn) dataTable).getAutoFitColumnWidth(column);
      setColumnWidth(column, width);
    } else {
      throwUnimplementedInterfaceException("HasAutoFitColumn");
    }
  }

  /**
   * Adjust all column widths so they take up the maximum amount of space
   * without needing a horizontal scroll bar. The distribution will be
   * proportional to the current width of each column.
   */
  public void fillWidth() {
    // Calculate how much room we have to work with
    int clientWidth = -1;
    if (scrollingEnabled) {
      DOM.setStyleAttribute(dataWrapper, "overflow", "scroll");
      clientWidth = DOM.getElementPropertyInt(dataWrapper, "clientWidth") - 1;
      DOM.setStyleAttribute(dataWrapper, "overflow", "auto");
    } else {
      clientWidth = DOM.getElementPropertyInt(dataWrapper, "clientWidth");
    }
    if (clientWidth < 0) {
      return;
    }
    int diff = clientWidth - ((Widget) dataTable).getOffsetWidth();

    // Temporarily set resize policy to unconstrained
    int tempResizePolicy = getResizePolicy();
    resizePolicy = RESIZE_POLICY_UNCONSTRAINED;

    // Calculate the total width of the cells
    int totalWidth = 0;
    int numColumns = dataTable.getColumnCount();
    int[] colWidths = new int[numColumns];
    for (int i = 0; i < numColumns; i++) {
      colWidths[i] = dataTable.getColumnWidth(i);
      totalWidth += colWidths[i];
    }

    // Distribute the difference across all columns, weighted by current size
    int remainingDiff = diff;
    for (int i = 0; i < numColumns; i++) {
      int colDiff = (int) (diff * (colWidths[i] / (float) totalWidth));
      colDiff = setColumnWidth(i, colWidths[i] + colDiff) - colWidths[i];
      remainingDiff -= colDiff;
      colWidths[i] += colDiff;
    }

    // Spread out remaining diff however possible
    if (remainingDiff != 0) {
      for (int i = 0; i < numColumns && remainingDiff != 0; i++) {
        int colWidth = setColumnWidth(i, colWidths[i] + remainingDiff);
        remainingDiff -= (colWidth - colWidths[i]);
      }
    }

    // Reset the resize policy
    resizePolicy = tempResizePolicy;
  }

  /**
   * @return the cell padding of the tables, in pixels
   */
  public int getCellPadding() {
    return dataTable.getCellPadding();
  }

  /**
   * @return the cell spacing of the tables, in pixels
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
   * @return the data table
   */
  public HasFixedColumnWidth getDataTable() {
    return dataTable;
  }

  /**
   * @return the footer table
   */
  public HasFixedColumnWidth getFooterTable() {
    return footerTable;
  }

  /**
   * @return the header table
   */
  public HasFixedColumnWidth getHeaderTable() {
    return headerTable;
  }

  /**
   * @return the resize policy
   */
  public int getResizePolicy() {
    return resizePolicy;
  }

  /**
   * @return true if auto fitting is enabled, false if disabled
   */
  public boolean isAutoFitEnabled() {
    return autoFitEnabled;
  }

  /**
   * @return true if scrolling is enabled, false if disabled
   */
  public boolean isScrollingEnabled() {
    return scrollingEnabled;
  }

  /**
   * @return true if sorting is enabled, false if disabled
   */
  public boolean isSortingEnabled() {
    return sortingEnabled;
  }

  /**
   * @see Widget
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
        if (DOM.eventGetButton(event) != Event.BUTTON_LEFT) {
          return;
        }
        if (resizeWorker.getCurrentCell() != null) {
          DOM.eventPreventDefault(event);
          DOM.eventCancelBubble(event, true);
          resizeWorker.startResizing(event);
        }
        break;
      case Event.ONMOUSEUP:
        // Stop resizing the header column
        if (DOM.eventGetButton(event) != Event.BUTTON_LEFT) {
          return;
        }
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
              int row = OverrideDOM.getRowIndex(DOM.getParent(cellElem)) - 1;
              int cell = OverrideDOM.getCellIndex(cellElem);
              int column = cell;
              if (headerTable instanceof HasCellSpans) {
                column = ((HasCellSpans) headerTable).getColumnIndex(row, cell);
              }
              if (dataTable.getColumnCount() > column) {
                sortedColumnTrigger = cellElem;
                ((HasSortableColumns) dataTable).sortColumn(column);
              }
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
    redraw();
  }

  /**
   * Redraw the table. This is a relatively cheap operation and should be called
   * after modifying the header or footer sections.
   */
  public void redraw() {
    resizeTablesVertically();

    if (resizePolicy == RESIZE_POLICY_FILL_WIDTH) {
      DeferredCommand.addCommand(new Command() {
        public void execute() {
          fillWidth();
        }
      });
    }
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
   * Enable or disable automatic column fitting via mouse clicks to the header
   * cells.
   * 
   * @param autoFitEnabled true to enable column sorting via mouse events
   */
  public void setAutoFitEnabled(boolean autoFitEnabled) {
    if (!autoFitEnabled) {
      // Disable auto fit
      this.autoFitEnabled = false;
    } else if (dataTable instanceof HasAutoFitColumn) {
      // Enable auto fit
      this.autoFitEnabled = true;
    } else {
      // Auto fit not supported
      this.autoFitEnabled = false;
      throwUnimplementedInterfaceException("HasAutoFitColumn");
    }
  }

  /**
   * Sets the amount of padding to be added around all cells.
   * 
   * @param padding the cell padding, in pixels
   */
  public void setCellPadding(int padding) {
    headerTable.setCellPadding(padding);
    dataTable.setCellPadding(padding);
    if (footerTable != null) {
      footerTable.setCellPadding(padding);
    }
  }

  /**
   * Sets the amount of spacing to be added around all cells.
   * 
   * @param spacing the cell spacing, in pixels
   */
  public void setCellSpacing(int spacing) {
    headerTable.setCellSpacing(spacing);
    dataTable.setCellSpacing(spacing);
    if (footerTable != null) {
      footerTable.setCellSpacing(spacing);
    }
  }

  /**
   * Set the width of a column.
   * 
   * @param column the index of the column
   * @param width the width in pixels
   * @return the new column width
   */
  public int setColumnWidth(int column, int width) {
    return setColumnWidth(column, width, column + 1);
  }

  /**
   * Set the footer table that appears under the data table. If set to null, the
   * footer table will not be shown.
   * 
   * @param footerTable the table to use in the footer
   */
  public void setFooterTable(HasFixedColumnWidth footerTable) {
    // Disown the old footer table
    if (this.footerTable != null) {
      orphan((Widget) this.footerTable);
      DOM.removeChild(footerWrapper, ((Widget) this.footerTable).getElement());
      DOM.removeChild(getElement(), footerWrapper);
      getChildren().remove((Widget) this.footerTable);
    }

    // Set the new footer table
    this.footerTable = footerTable;
    if (footerTable != null) {
      footerTable.setCellSpacing(getCellSpacing());
      footerTable.setCellPadding(getCellPadding());
      prepareTable((Widget) footerTable, "footer");

      // Create the footer wrapper and spacer
      if (footerWrapper == null) {
        footerWrapper = createWrapper("footer-wrapper");
        footerSpacer = createSpacer(footerWrapper);
      }

      // Adopt the header table into the panel
      adoptTable((Widget) footerTable, footerWrapper, 3);
    }

    // Resize the tables
    resizeTablesVertically();
  }

  /**
   * @see com.google.gwt.user.client.ui.UIObject
   */
  public void setHeight(String height) {
    this.lastHeight = height;
    super.setHeight(height);
  }

  /**
   * Set the resize policy of the table.
   * 
   * @param resizePolicy the resize policy
   */
  public void setResizePolicy(int resizePolicy) {
    // Check the resize policy
    if (resizePolicy != RESIZE_POLICY_FILL_WIDTH
        && resizePolicy != RESIZE_POLICY_FIXED_WIDTH
        && resizePolicy != RESIZE_POLICY_FLOW
        && resizePolicy != RESIZE_POLICY_UNCONSTRAINED) {
      throw new IllegalArgumentException("Invalid resizePolicy");
    }

    // Set the resize policy
    this.resizePolicy = resizePolicy;
    if (resizePolicy == RESIZE_POLICY_FIXED_WIDTH) {
      fillWidthImage.setVisible(false);
    } else if (resizePolicy == RESIZE_POLICY_FILL_WIDTH) {
      fillWidthImage.setVisible(false);
      fillWidth();
    } else {
      fillWidthImage.setVisible(true);
    }
  }

  /**
   * Enable or disable scrolling of the data area. If enabled, the data area
   * will be confined to a scrollable area. If disabled, the entire
   * {@link ScrollTable} will grow vertically so all of the data area is
   * visible.
   * 
   * @param scrollingEnabled true to enable data scrolling
   */
  public void setScrollingEnabled(boolean scrollingEnabled) {
    this.scrollingEnabled = scrollingEnabled;

    if (scrollingEnabled) {
      DOM.setStyleAttribute(headerWrapper, "overflow", "hidden");
      DOM.setStyleAttribute(dataWrapper, "overflow", "hidden");
      if (footerWrapper != null) {
        DOM.setStyleAttribute(footerWrapper, "overflow", "hidden");
      }

      // Reset the height
      if (lastHeight != null) {
        super.setHeight(lastHeight);
      }
    } else {
      super.setHeight("auto");
      DOM.setStyleAttribute(headerWrapper, "overflow", "");
      DOM.setStyleAttribute(headerWrapper, "height", "auto");
      DOM.setStyleAttribute(dataWrapper, "overflow", "");
      DOM.setStyleAttribute(dataWrapper, "height", "auto");
      if (footerWrapper != null) {
        DOM.setStyleAttribute(footerWrapper, "overflow", "");
        DOM.setStyleAttribute(footerWrapper, "height", "auto");
      }
    }

    // Resize the tables
    redraw();
  }

  /**
   * Enable or disable column sorting via mouse clicks to the header cells.
   * 
   * @param sortingEnabled true to enable column sorting via mouse events
   */
  public void setSortingEnabled(boolean sortingEnabled) {
    if (!sortingEnabled) {
      // Disable sorting
      this.sortingEnabled = false;
    } else if (dataTable instanceof HasSortableColumns) {
      // Enable sorting
      this.sortingEnabled = true;
    } else {
      // Sorting not supported
      this.sortingEnabled = false;
      throwUnimplementedInterfaceException("HasSortableColumns");
    }

    // Remove the sorted indicator image
    Element parent = DOM.getParent(sortedColumnWrapper);
    if (parent != null) {
      DOM.removeChild(parent, sortedColumnWrapper);
    }
  }

  /**
   * @return the wrapper element around the data table
   */
  protected Element getDataWrapper() {
    return dataWrapper;
  }

  /**
   * Resize the widget and redistribute space as needed.
   */
  protected void onAttach() {
    super.onAttach();
    resizeTablesVertically();
    repositionHeaderSpacer();
    if (resizePolicy == RESIZE_POLICY_FILL_WIDTH) {
      fillWidth();
    }
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
    if (!scrollingEnabled) {
      DOM.setStyleAttribute(dataWrapper, "overflow", "hidden");
      DOM.setStyleAttribute(dataWrapper, "overflow", "");
      scrollTables(true);
      return;
    }

    int totalHeight = DOM.getElementPropertyInt(getElement(), "clientHeight");
    int headerHeight = ((Widget) headerTable).getOffsetHeight();
    int footerHeight = 0;
    if (footerTable != null) {
      footerHeight = ((Widget) footerTable).getOffsetHeight();
    }
    DOM.setStyleAttribute(headerWrapper, "height", headerHeight + "px");
    if (footerWrapper != null) {
      DOM.setStyleAttribute(footerWrapper, "height", footerHeight + "px");
    }
    DOM.setStyleAttribute(dataWrapper, "height",
        (totalHeight - headerHeight - footerHeight) + "px");
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
      int scrollLeft = DOM.getElementPropertyInt(dataWrapper, "scrollLeft");
      DOM.setElementPropertyInt(headerWrapper, "scrollLeft", scrollLeft);
      if (footerWrapper != null) {
        DOM.setElementPropertyInt(footerWrapper, "scrollLeft", scrollLeft);
      }
    }
  }

  /**
   * Set the width of a column.
   * 
   * @param column the index of the column
   * @param width the width in pixels
   * @param sacrificeColumn the column that will be shrunk to maintain the width
   * @return the new column width
   */
  protected int setColumnWidth(int column, int width, int sacrificeColumn) {
    // Calculate the difference in size
    width = Math.max(width, 1);

    // Try to constrain the size of the grid
    if (resizePolicy != RESIZE_POLICY_UNCONSTRAINED) {
      int diff = width - getColumnWidth(column);
      diff += redistributeWidth(-diff, sacrificeColumn);

      // Prevent over resizing
      if (resizePolicy == RESIZE_POLICY_FIXED_WIDTH
          || resizePolicy == RESIZE_POLICY_FILL_WIDTH) {
        width -= diff;
      }
    }

    // Resize the column
    dataTable.setColumnWidth(column, width);
    headerTable.setColumnWidth(column, width);
    if (footerTable != null) {
      footerTable.setColumnWidth(column, width);
    }

    // Reposition things as needed
    repositionHeaderSpacer();
    scrollTables(false);
    return width;
  }

  /**
   * Adopt a table into this {@link ScrollTable} within its wrapper.
   * 
   * @param table the table to adopt
   * @param wrapper the wrapper element
   * @param index the index to insert the wrapper in the main element
   */
  private void adoptTable(Widget table, Element wrapper, int index) {
    getChildren().add(table);
    DOM.insertChild(getElement(), wrapper, index);
    DOM.appendChild(wrapper, table.getElement());
    adopt(table);
  }

  /**
   * Create a spacer element that allows scrolling past the edge of a table.
   * 
   * @param wrapper the wrapper element
   * @return a new spacer element
   */
  private Element createSpacer(Element wrapper) {
    Element spacer = DOM.createDiv();
    DOM.setStyleAttribute(spacer, "height", "1px");
    DOM.setStyleAttribute(spacer, "width", "10000px");
    DOM.setStyleAttribute(spacer, "position", "absolute");
    DOM.setStyleAttribute(spacer, "top", "1px");
    DOM.setStyleAttribute(spacer, "left", "1px");
    DOM.appendChild(wrapper, spacer);
    return spacer;
  }

  /**
   * Create a wrapper element that will hold a table.
   * 
   * @param cssName the style name added to the base name
   * @return a new wrapper element
   */
  private Element createWrapper(String cssName) {
    Element wrapper = DOM.createDiv();
    DOM.setStyleAttribute(wrapper, "width", "100%");
    DOM.setStyleAttribute(wrapper, "padding", "0px");
    DOM.setStyleAttribute(wrapper, "overflow", "hidden");
    DOM.setStyleAttribute(wrapper, "position", "relative");
    setStylePrimaryName(wrapper, DEFAULT_STYLE_NAME + "-" + cssName);
    return wrapper;
  }

  /**
   * Prepare a table to be added to the {@link ScrollTable}.
   * 
   * @param table the table to prepare
   * @param cssName the style name added to the base name
   */
  private void prepareTable(Widget table, String cssName) {
    Element tableElem = table.getElement();
    DOM.setStyleAttribute(tableElem, "margin", "0px");
    DOM.setStyleAttribute(tableElem, "border", "0px");
    table.setStyleName(DEFAULT_STYLE_NAME + "-" + cssName);
  }

  /**
   * Distribute a given amount of width over all columns to the right of the
   * specified column.
   * 
   * @param width the width to distribute
   * @param startColumn the index of the first column to receive the width
   * @return the actual amount of distributed width
   */
  private int redistributeWidth(int width, int startColumn) {
    int numColumns = Math.max(headerTable.getColumnCount(),
        dataTable.getColumnCount());
    int actualWidth = 0;
    if (startColumn < numColumns) {
      if (width > 0) {
        int startWidth = getColumnWidth(startColumn);
        int newWidth = startWidth + width;
        dataTable.setColumnWidth(startColumn, newWidth);
        headerTable.setColumnWidth(startColumn, newWidth);
        if (footerTable != null) {
          footerTable.setColumnWidth(startColumn, newWidth);
        }
        actualWidth = width;
      } else if (width < 0) {
        for (int i = startColumn; i < numColumns && width < 0; i++) {
          int startWidth = getColumnWidth(i);
          int newWidth = startWidth + width;
          dataTable.setColumnWidth(i, newWidth);
          headerTable.setColumnWidth(i, newWidth);
          if (footerTable != null) {
            footerTable.setColumnWidth(i, newWidth);
          }
          int colDiff = startWidth - getColumnWidth(i);
          width += colDiff;
          actualWidth -= colDiff;
        }
      }
    }

    // Return the actual width
    return actualWidth;
  }

  /**
   * Reposition the header spacer next to the header table.
   */
  private void repositionHeaderSpacer() {
    DOM.setStyleAttribute(headerSpacer, "left",
        ((Widget) headerTable).getOffsetWidth() + "px");
    if (footerTable != null) {
      DOM.setStyleAttribute(footerSpacer, "left",
          ((Widget) footerTable).getOffsetWidth() + "px");
    }
  }

  /**
   * Throw an unsupported operation exception when an interface is not
   * implemented.
   * 
   * @param interfaceClass the interface class
   */
  private void throwUnimplementedInterfaceException(String interfaceClass) {
    throw new UnsupportedOperationException("Data table does not implement "
        + interfaceClass);
  }
}
