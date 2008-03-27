/*
 * Copyright 2008 Google Inc.
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * <li> .gwt-ScrollTable .headerTable { applied to the header table }
 * <li> .gwt-ScrollTable .dataTable { applied to the data table }
 * <li> .gwt-ScrollTable .footerTable { applied to the footer table }
 * <li> .gwt-ScrollTable .headerWrapper { wrapper around the header table }</li>
 * <li> .gwt-ScrollTable .dataWrapper { wrapper around the data table }</li>
 * <li> .gwt-ScrollTable .footerWrapper { wrapper around the footer table }</li>
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
     * The current columns under the colSpan of the current cell, ordered from
     * narrowest to widest.
     */
    private List<ColumnNode> curCellColumns = new ArrayList<ColumnNode>();

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
      @Override
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
     * The column that will be sacrificed.
     */
    private int sacrificeColumn = -1;
    
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
      FixedWidthFlexTable headerTable = table.getHeaderTable();
      FixedWidthGrid dataTable = table.getDataTable();
      FixedWidthFlexTable footerTable = table.getFooterTable();
      int colspan = DOM.getElementPropertyInt(curCell, "colSpan");
      sacrificeColumn = curCellIndex + colspan;
      ResizePolicy resizePolicy = table.getResizePolicy();
      if (resizePolicy == ResizePolicy.FIXED_WIDTH
          || resizePolicy == ResizePolicy.FILL_WIDTH) {
        if (sacrificeColumn >= dataTable.getColumnCount()) {
          return;
        }
      }

      // Calculate new sizes and total difference
      int[] newWidths = new int[colspan];
      int diff = 0;
      for (int i = 0; i < colspan; i++) {
        int actualColumn = curCellIndex + i;
        if (table.isColumnWidthGuaranteed(actualColumn)) {
          newWidths[i] = dataTable.getColumnWidth(actualColumn);
        } else {
          newWidths[i] = dataTable.getAutoFitColumnWidth(actualColumn);
        }
        diff += (newWidths[i] - table.getColumnWidth(actualColumn));
      }

      // Redistribute the width
      if (resizePolicy == ResizePolicy.FLOW) {
        table.redistributeWidth(-diff, sacrificeColumn);
        diff = 0;
      } else if (resizePolicy != ResizePolicy.UNCONSTRAINED) {
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
          if (table.isColumnWidthGuaranteed(curCellIndex)) {
            curCell = null;
            return false;
          }
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
        int colSpan = DOM.getElementPropertyInt(curCell, "colSpan");
        sacrificeColumn = curCellIndex + colSpan;
        for (int i = 0; i < colSpan; i++) {
          int newCellIndex = curCellIndex + i;
          if (!table.isColumnWidthGuaranteed(newCellIndex)) {
            int originalWidth = table.dataTable.getColumnWidth(newCellIndex);

            // Insert the node into the ordered list by width
            int insertIndex = 0;
            for (ColumnNode curNode : curCellColumns) {
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
      int row = OverrideDOM.getRowIndex(DOM.getParent(cell)) - 1;
      int column = OverrideDOM.getCellIndex(cell);
      return table.headerTable.getColumnIndex(row, column);
    }

    /**
     * Helper method that actually sets the column size. This method is called
     * periodically by a timer.
     */
    private void resizeColumn() {
      if (mouseXLast != mouseXCurrent) {
        mouseXLast = mouseXCurrent;

        int columnsRemaining = curCellColumns.size();
        int totalDelta = mouseXCurrent - mouseXStart;
        for (ColumnNode curNode : curCellColumns) {
          int originalWidth = curNode.getOriginalWidth();
          int column = curNode.getCellIndex();
          int delta = totalDelta / columnsRemaining;
          int colWidth = table.setColumnWidth(column, originalWidth + delta,
              sacrificeColumn);

          totalDelta -= (colWidth - originalWidth);
          columnsRemaining--;
        }
      }
    }
  }

  /**
   * The Opera version of the mouse worker fixes an Opera bug where the cursor
   * isn't updated if the mouse is hovering over an element DOM object when its
   * cursor style is changed.
   */
  @SuppressWarnings("unused")
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
    @Override
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
    @Override
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
    AbstractImagePrototype scrollTableFillWidth();

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
   * The resize policies of table cells.
   * 
   * <ul>
   * <li>DISABLED - Columns cannot be resized by the user</li>
   * <li>UNCONSTRAINED - Columns shrink and expand independently of each other</li>
   * <li>FLOW - As one column expands or shrinks, the columns to the right will
   * do the opposite, trying to maintain the same size. The user can still
   * expand the grid if there is no more space to take from the columns on the
   * right.</li>
   * <li>FIXED_WIDTH - As one column expands or shrinks, the columns to the
   * right will do the opposite, trying to maintain the same size. The width of
   * the grid will remain constant, ignoring column resizing that would result
   * in the grid growing in size.</li>
   * <li>FILL_WIDTH - Same as FIXED_WIDTH, but the grid will always fill the
   * available width, even if the widget is resized.</li>
   * <li>FILL_WIDTH_DISABLED - The grid will always fill the available width,
   * but the user cannot manually change column widths.</li>
   * </ul>
   */
  public enum ResizePolicy {
    DISABLED, UNCONSTRAINED, FLOW, FIXED_WIDTH, FILL_WIDTH, FILL_WIDTH_DISABLED
  }

  /**
   * The scroll policy of the table.
   * 
   * <ul>
   * <li>HORIZONTAL - Only a horizontal scrollbar will be present.</li>
   * <li>BOTH - Both a vertical and horizontal scrollbar will be present.</li>
   * </ul>
   */
  public enum ScrollPolicy {
    HORIZONTAL, BOTH
  }

  /**
   * A boolean indicating whether or not column auto resizing is enabled.
   */
  private boolean autoFitEnabled = true;

  /**
   * Columns which have guaranteed sizes.
   */
  private Set<Integer> guaranteedColumns = new HashSet<Integer>();

  /**
   * The data table.
   */
  private FixedWidthGrid dataTable;

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
  private FixedWidthFlexTable footerTable = null;

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
  private FixedWidthFlexTable headerTable = null;

  /**
   * The non-scrollable wrapper div around the header table.
   */
  private Element headerWrapper;

  /**
   * The last known height of this widget that the user set.
   */
  private String lastHeight = null;

  /**
   * The minimum allowed width of the data table.
   */
  private int minWidth = -1;

  /**
   * A boolean indicating whether or not the grid should try to maintain its
   * width as much as possible.
   */
  private ResizePolicy resizePolicy = ResizePolicy.FLOW;

  /**
   * The worker that helps with mouse resize events.
   */
  private MouseResizeWorker resizeWorker = GWT.create(MouseResizeWorker.class);

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
   * The scrolling policy.
   */
  private ScrollPolicy scrollPolicy = ScrollPolicy.BOTH;

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
   * A map of columns that cannot be sorted.
   * 
   * key = the column index
   * 
   * value = true if the column is sortable, false of not
   */
  private Map<Integer, Boolean> unsortableColumns = new HashMap<Integer, Boolean>();

  /**
   * Constructor.
   * 
   * @param dataTable the data table
   * @param headerTable the header table
   */
  public ScrollTable(FixedWidthGrid dataTable, FixedWidthFlexTable headerTable) {
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
  public ScrollTable(FixedWidthGrid dataTable, FixedWidthFlexTable headerTable,
      final ScrollTableImages images) {
    super();
    this.dataTable = dataTable;
    this.headerTable = headerTable;
    resizeWorker.setScrollTable(this);

    // Prepare the header and data tables
    prepareTable(dataTable, "dataTable");
    prepareTable(headerTable, "headerTable");

    // Create the main div container
    Element mainElem = DOM.createDiv();
    setElement(mainElem);
    setStylePrimaryName(DEFAULT_STYLE_NAME);
    DOM.setStyleAttribute(mainElem, "padding", "0px");
    DOM.setStyleAttribute(mainElem, "overflow", "hidden");
    DOM.setStyleAttribute(mainElem, "position", "relative");

    // Create the table wrapper and spacer
    headerWrapper = createWrapper("headerWrapper");
    headerSpacer = createSpacer(headerWrapper);
    dataWrapper = createWrapper("dataWrapper");

    // Create image to fill width
    fillWidthImage = new Image() {
      @Override
      public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);
        if (DOM.eventGetType(event) == Event.ONCLICK) {
          fillWidth();
        }
      }
    };
    fillWidthImage.setTitle("Shrink/Expand to fill visible area");
    images.scrollTableFillWidth().applyTo(fillWidthImage);
    Element fillWidthImageElem = fillWidthImage.getElement();
    DOM.setStyleAttribute(fillWidthImageElem, "cursor", "pointer");
    DOM.setStyleAttribute(fillWidthImageElem, "position", "absolute");
    DOM.setStyleAttribute(fillWidthImageElem, "top", "0px");
    DOM.setStyleAttribute(fillWidthImageElem, "right", "0px");
    DOM.setStyleAttribute(fillWidthImageElem, "zIndex", "1");
    add(fillWidthImage, getElement());

    // Adopt the header and data tables into the panel
    adoptTable(headerTable, headerWrapper, 1);
    adoptTable(dataTable, dataWrapper, 2);

    // Create the sort indicator Image
    sortedColumnWrapper = DOM.createSpan();
    DOM.setInnerHTML(sortedColumnWrapper, "&nbsp;");
    DOM.appendChild(sortedColumnWrapper, sortedColumnIndicator.getElement());

    // Add some event handling
    sinkEvents(Event.ONMOUSEOUT);
    DOM.setEventListener(dataWrapper, this);
    DOM.sinkEvents(dataWrapper, Event.ONSCROLL);
    DOM.setEventListener(headerWrapper, this);
    DOM.sinkEvents(headerWrapper, Event.ONMOUSEMOVE | Event.ONMOUSEDOWN
        | Event.ONMOUSEUP | Event.ONCLICK | Event.ONDBLCLICK);

    // Listen for sorting events in the data table
    dataTable.addSortableColumnsListener(new SortableColumnsListener() {
      public void onColumnSorted(ColumnSortList sortList) {
        // Get the primary column and sort order
        int column = -1;
        boolean ascending = true;
        if (sortList != null) {
          column = sortList.getPrimaryColumn();
          ascending = sortList.isPrimaryAscending();
        }

        // Remove the sorted column indicator
        if (isColumnSortable(column)) {
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
    setColumnWidth(column, dataTable.getAutoFitColumnWidth(column));
  }

  /**
   * Adjust all column widths so they take up the maximum amount of space
   * without needing a horizontal scroll bar. The distribution will be
   * proportional to the current width of each column.
   * 
   * The {@link ScrollTable} must be visible on the page for this method to
   * work.
   */
  public void fillWidth() {
    // Calculate how much room we have to work with
    int clientWidth = -1;
    if (scrollPolicy != ScrollPolicy.HORIZONTAL) {
      DOM.setStyleAttribute(dataWrapper, "overflow", "scroll");
      clientWidth = DOM.getElementPropertyInt(dataWrapper, "clientWidth") - 1;
      DOM.setStyleAttribute(dataWrapper, "overflow", "auto");
    } else {
      clientWidth = DOM.getElementPropertyInt(dataWrapper, "clientWidth");
    }
    if (clientWidth <= 0) {
      return;
    }
    clientWidth = Math.max(clientWidth, minWidth);
    int diff = clientWidth - ((Widget) dataTable).getOffsetWidth();

    // Temporarily set resize policy to unconstrained
    ResizePolicy tempResizePolicy = getResizePolicy();
    resizePolicy = ResizePolicy.UNCONSTRAINED;

    // Calculate the total width of the columns that aren't guaranteed
    int totalWidth = 0;
    int numColumns = dataTable.getColumnCount();
    int[] colWidths = new int[numColumns];
    for (int i = 0; i < numColumns; i++) {
      if (isColumnWidthGuaranteed(i)) {
        colWidths[i] = 0;
      } else {
        colWidths[i] = dataTable.getColumnWidth(i);
      }
      totalWidth += colWidths[i];
    }

    // Distribute the difference across all columns, weighted by current size
    int remainingDiff = diff;
    for (int i = 0; i < numColumns; i++) {
      if (colWidths[i] > 0) {
        int colDiff = (int) (diff * (colWidths[i] / (float) totalWidth));
        colDiff = setColumnWidth(i, colWidths[i] + colDiff) - colWidths[i];
        remainingDiff -= colDiff;
        colWidths[i] += colDiff;
      }
    }

    // Spread out remaining diff however possible
    if (remainingDiff != 0) {
      for (int i = 0; i < numColumns && remainingDiff != 0; i++) {
        if (!isColumnWidthGuaranteed(i)) {
          int colWidth = setColumnWidth(i, colWidths[i] + remainingDiff);
          remainingDiff -= (colWidth - colWidths[i]);
        }
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
  public FixedWidthGrid getDataTable() {
    return dataTable;
  }

  /**
   * @return the footer table
   */
  public FixedWidthFlexTable getFooterTable() {
    return footerTable;
  }

  /**
   * @return the header table
   */
  public FixedWidthFlexTable getHeaderTable() {
    return headerTable;
  }

  /**
   * @return the minimum width of the data table
   */
  public int getMinWidth() {
    return minWidth;
  }

  /**
   * @return the resize policy
   */
  public ResizePolicy getResizePolicy() {
    return resizePolicy;
  }

  /**
   * @return the current scroll policy
   */
  public ScrollPolicy getScrollPolicy() {
    return scrollPolicy;
  }

  /**
   * @return true if auto fitting is enabled, false if disabled
   */
  public boolean isAutoFitEnabled() {
    return autoFitEnabled;
  }

  /**
   * @param column the column index
   * @return true if the column is sortable, false if it is not sortable
   */
  public boolean isColumnSortable(int column) {
    Boolean sortable = unsortableColumns.get(new Integer(column));
    if (sortable == null) {
      return sortingEnabled;
    } else {
      return sortable.booleanValue();
    }
  }

  /**
   * @param column the column index
   * @return true if the column width is guaranteed
   */
  public boolean isColumnWidthGuaranteed(int column) {
    return guaranteedColumns.contains(new Integer(column));
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
  @Override
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

          // Get the actual column index
          Element cellElem = headerTable.getEventTargetCell(event);
          if (cellElem != null) {
            int row = OverrideDOM.getRowIndex(DOM.getParent(cellElem)) - 1;
            int cell = OverrideDOM.getCellIndex(cellElem);
            int column = headerTable.getColumnIndex(row, cell);
            if (isColumnSortable(column)) {
              if (dataTable.getColumnCount() > column) {
                sortedColumnTrigger = cellElem;
                dataTable.sortColumn(column);
              }
            }
          }
        }
        break;
      case Event.ONMOUSEMOVE:
        // Resize the header column
        if (resizeWorker.isResizing()) {
          resizeWorker.resizeColumn(event);
        } else if (resizePolicy != ResizePolicy.DISABLED
            && resizePolicy != ResizePolicy.FILL_WIDTH_DISABLED) {
          resizeWorker.setCurrentCell(event);
        }
        break;
      case Event.ONMOUSEOUT:
        // Unhover if the mouse leaves the table
        Element toElem = DOM.eventGetToElement(event);
        if (toElem == null || !DOM.isOrHasChild(dataWrapper, toElem)) {
          // Check that the coordinates are not directly over the table
          int clientX = DOM.eventGetClientX(event);
          int clientY = DOM.eventGetClientY(event);
          int tableLeft = DOM.getAbsoluteLeft(dataWrapper);
          int tableTop = DOM.getAbsoluteTop(dataWrapper);
          int tableWidth = DOM.getElementPropertyInt(dataWrapper, "offsetWidth");
          int tableHeight = DOM.getElementPropertyInt(dataWrapper,
              "offsetHeight");
          int tableBottom = tableTop + tableHeight;
          int tableRight = tableLeft + tableWidth;
          if (clientX > tableLeft && clientX < tableRight && clientY > tableTop
              && clientY < tableBottom) {
            return;
          }

          dataTable.hoverCell(null);
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

    if (resizePolicy == ResizePolicy.FILL_WIDTH
        || resizePolicy == ResizePolicy.FILL_WIDTH_DISABLED) {
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
  @Override
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
    this.autoFitEnabled = autoFitEnabled;
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
   * Enable or disable sorting on a specific column. All columns are sortable by
   * default. Use {@link #setSortingEnabled(boolean)} to disable sorting on all
   * columns.
   * 
   * @param column the index of the column
   * @param sortable true to enable sorting for this column, false to disable
   */
  public void setColumnSortable(int column, boolean sortable) {
    unsortableColumns.put(new Integer(column), Boolean.valueOf(sortable));
  }

  /**
   * Set the width of a column. If the column has already been set using the
   * {@link #setGuaranteedColumnWidth(int, int)} method, the column will no
   * longer have a guarenteed column width.
   * 
   * @param column the index of the column
   * @param width the width in pixels
   * @return the new column width
   */
  public int setColumnWidth(int column, int width) {
    guaranteedColumns.remove(new Integer(column));
    return setColumnWidth(column, width, column + 1);
  }

  /**
   * Set the footer table that appears under the data table. If set to null, the
   * footer table will not be shown.
   * 
   * @param footerTable the table to use in the footer
   */
  public void setFooterTable(FixedWidthFlexTable footerTable) {
    // Disown the old footer table
    if (this.footerTable != null) {
      orphan(this.footerTable);
      DOM.removeChild(footerWrapper, this.footerTable.getElement());
      DOM.removeChild(getElement(), footerWrapper);
      getChildren().remove(this.footerTable);
    }

    // Set the new footer table
    this.footerTable = footerTable;
    if (footerTable != null) {
      footerTable.setCellSpacing(getCellSpacing());
      footerTable.setCellPadding(getCellPadding());
      prepareTable(footerTable, "footerTable");

      // Create the footer wrapper and spacer
      if (footerWrapper == null) {
        footerWrapper = createWrapper("footerWrapper");
        footerSpacer = createSpacer(footerWrapper);
      }

      // Adopt the header table into the panel
      adoptTable(footerTable, footerWrapper, 3);
    }

    // Resize the tables
    resizeTablesVertically();
  }

  /**
   * Set the width of a column and guarantees that the width will not change,
   * regardless of the resize policy.
   * 
   * @param column the index of the column
   * @param width the width in pixels
   * @return the new column width
   */
  public int setGuaranteedColumnWidth(int column, int width) {
    guaranteedColumns.add(new Integer(column));
    return setColumnWidth(column, width, column + 1);
  }

  /**
   * @see com.google.gwt.user.client.ui.UIObject
   */
  @Override
  public void setHeight(String height) {
    this.lastHeight = height;
    super.setHeight(height);
  }

  /**
   * Set the minimum allowable width of the data table. If the width of this
   * Widget is smaller than the width of the data table, a horizontal scroll bar
   * will appear.
   * 
   * The minWidth property only applies to the policies
   * {@link ResizePolicy#FILL_WIDTH} and
   * {@link ResizePolicy#FILL_WIDTH_DISABLED}.
   * 
   * @param minWidth the minimum width, or -1 to disable
   */
  public void setMinWidth(int minWidth) {
    this.minWidth = minWidth;
    if (resizePolicy == ResizePolicy.FILL_WIDTH
        || resizePolicy == ResizePolicy.FILL_WIDTH_DISABLED) {
      fillWidth();
    }
  }

  /**
   * Set the resize policy of the table.
   * 
   * @param resizePolicy the resize policy
   */
  public void setResizePolicy(ResizePolicy resizePolicy) {
    this.resizePolicy = resizePolicy;
    if (resizePolicy == ResizePolicy.FIXED_WIDTH
        || resizePolicy == ResizePolicy.DISABLED) {
      fillWidthImage.setVisible(false);
    } else if (resizePolicy == ResizePolicy.FILL_WIDTH
        || resizePolicy == ResizePolicy.FILL_WIDTH_DISABLED) {
      fillWidthImage.setVisible(false);
      fillWidth();
    } else {
      fillWidthImage.setVisible(true);
    }
  }

  /**
   * Set the scroll policy of the table.
   * 
   * @param scrollPolicy the new scroll policy
   */
  public void setScrollPolicy(ScrollPolicy scrollPolicy) {
    this.scrollPolicy = scrollPolicy;

    if (scrollPolicy == ScrollPolicy.HORIZONTAL) {
      super.setHeight("auto");
      DOM.setStyleAttribute(dataWrapper, "height", "auto");
    } else {
      // Reset the height
      if (lastHeight != null) {
        super.setHeight(lastHeight);
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
    this.sortingEnabled = sortingEnabled;

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
  @Override
  protected void onAttach() {
    super.onAttach();
    resizeTablesVertically();
    repositionHeaderSpacer();
    if (resizePolicy == ResizePolicy.FILL_WIDTH
        || resizePolicy == ResizePolicy.FILL_WIDTH_DISABLED) {
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
    // Force browser to redraw
    if (scrollPolicy == ScrollPolicy.HORIZONTAL) {
      DOM.setStyleAttribute(dataWrapper, "overflow", "hidden");
      DOM.setStyleAttribute(dataWrapper, "overflow", "auto");
      scrollTables(true);
      return;
    }

    // Give the data wrapper all remaining height
    int totalHeight = DOM.getElementPropertyInt(getElement(), "clientHeight");
    int headerHeight = headerTable.getOffsetHeight();
    int footerHeight = 0;
    if (footerTable != null) {
      footerHeight = footerTable.getOffsetHeight();
    }
    DOM.setStyleAttribute(headerWrapper, "height", headerHeight + "px");
    if (footerWrapper != null) {
      DOM.setStyleAttribute(footerWrapper, "height", footerHeight + "px");
    }
    DOM.setStyleAttribute(dataWrapper, "height",
        (totalHeight - headerHeight - footerHeight) + "px");
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
    // A zero width will render improperly, so the width must be at least 1
    width = Math.max(width, 1);

    // Try to constrain the size of the grid
    if (resizePolicy != ResizePolicy.UNCONSTRAINED) {
      int diff = width - getColumnWidth(column);
      diff += redistributeWidth(-diff, sacrificeColumn);

      // Prevent over resizing
      if (resizePolicy == ResizePolicy.FIXED_WIDTH
          || resizePolicy == ResizePolicy.FILL_WIDTH
          || resizePolicy == ResizePolicy.FILL_WIDTH_DISABLED) {
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
    setStyleName(wrapper, cssName);
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
    table.addStyleName(cssName);
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
    // Make sure we have a column to distribute to
    int numColumns = Math.max(headerTable.getColumnCount(),
        dataTable.getColumnCount());
    if (startColumn >= numColumns) {
      return 0;
    }

    // Find the first column with a non-guaranteed width
    if (isColumnWidthGuaranteed(startColumn)) {
      boolean columnFound = false;
      for (int i = startColumn + 1; i < numColumns; i++) {
        if (!isColumnWidthGuaranteed(i)) {
          startColumn = i;
          columnFound = true;
          break;
        }
      }
      if (!columnFound) {
        return 0;
      }
    }

    // Redistribute the width across the columns
    int actualWidth = 0;
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
        if (!isColumnWidthGuaranteed(i)) {
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
}
