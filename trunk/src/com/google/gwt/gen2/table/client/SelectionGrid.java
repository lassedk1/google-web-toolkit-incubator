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
package com.google.gwt.gen2.table.client;

import com.google.gwt.gen2.event.shared.HandlerRegistration;
import com.google.gwt.gen2.table.client.overrides.Grid;
import com.google.gwt.gen2.table.client.overrides.OverrideDOM;
import com.google.gwt.gen2.table.event.client.CellHighlightEvent;
import com.google.gwt.gen2.table.event.client.CellHighlightHandler;
import com.google.gwt.gen2.table.event.client.CellUnhighlightEvent;
import com.google.gwt.gen2.table.event.client.CellUnhighlightHandler;
import com.google.gwt.gen2.table.event.client.HasCellHighlightHandlers;
import com.google.gwt.gen2.table.event.client.HasCellUnhighlightHandlers;
import com.google.gwt.gen2.table.event.client.HasRowHighlightHandlers;
import com.google.gwt.gen2.table.event.client.HasRowSelectionHandlers;
import com.google.gwt.gen2.table.event.client.HasRowUnhighlightHandlers;
import com.google.gwt.gen2.table.event.client.RowHighlightEvent;
import com.google.gwt.gen2.table.event.client.RowHighlightHandler;
import com.google.gwt.gen2.table.event.client.RowSelectionEvent;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.gen2.table.event.client.RowUnhighlightEvent;
import com.google.gwt.gen2.table.event.client.RowUnhighlightHandler;
import com.google.gwt.gen2.table.event.client.RowHighlightEvent.Row;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * A variation of the {@link Grid} that supports row or cell hovering and row
 * selection.
 * 
 * <h3>CSS Style Rules</h3>
 * 
 * <ul class="css"> <li>tr.selected { applied to selected rows }</li> <li>
 * tr.hovering { applied to row currently being hovered }</li> <li>td.hovering {
 * applied to cell currently being hovered }</li> </ul>
 */
public class SelectionGrid extends Grid implements HasRowHighlightHandlers,
    HasRowUnhighlightHandlers, HasCellHighlightHandlers, HasCellUnhighlightHandlers,
    HasRowSelectionHandlers {
  /**
   * This class contains methods used to format a table's cells.
   */
  public class SelectionGridCellFormatter extends CellFormatter {
    @Override
    protected Element getRawElement(int row, int column) {
      return super.getRawElement(row, column);
    }
  }

  /**
   * This class contains methods used to format a table's rows.
   */
  public class SelectionGridRowFormatter extends RowFormatter {
    @Override
    protected Element getRawElement(int row) {
      return super.getRawElement(row);
    }
  }

  /**
   * Selection policies.
   * 
   * <ul>
   * <li>DISABLED - selection is disabled</li>
   * <li>ONE_ROW - one row can be selected at a time</li>
   * <li>MULTI_ROW - multiple rows can be selected at a time</li>
   * </ul>
   */
  public static enum SelectionPolicy {
    DISABLED, ONE_ROW, MULTI_ROW
  }

  /**
   * The cell element currently being hovered.
   */
  private Element hoveringCellElem = null;

  /**
   * The index of the cell currently being hovered.
   */
  private int hoveringCellIndex = -1;

  /**
   * The row element currently being hovered.
   */
  private Element hoveringRowElem = null;

  /**
   * The index of the row currently being hovered.
   */
  private int hoveringRowIndex = -1;

  /**
   * The index of the row that the user selected last.
   */
  private int lastSelectedRowIndex = -1;

  /**
   * The rows that are currently selected.
   */
  private Map<Integer, Element> selectedRows = new HashMap<Integer, Element>();

  /**
   * The selection policy determines if the user can select zero, one, or many
   * rows.
   */
  private SelectionPolicy selectionPolicy = SelectionPolicy.MULTI_ROW;

  /**
   * Constructor.
   */
  public SelectionGrid() {
    super();
    setCellFormatter(new SelectionGridCellFormatter());
    setRowFormatter(new SelectionGridRowFormatter());

    // Sink hover and selection events
    sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.ONMOUSEDOWN);
  }

  /**
   * Constructs a {@link SelectionGrid} with the requested size.
   * 
   * @param rows the number of rows
   * @param columns the number of columns
   * @throws IndexOutOfBoundsException
   */
  public SelectionGrid(int rows, int columns) {
    this();
    resize(rows, columns);
  }

  public HandlerRegistration addCellHighlightHandler(CellHighlightHandler handler) {
    return addHandler(CellHighlightEvent.KEY, handler);
  }

  public HandlerRegistration addCellUnhighlightHandler(CellUnhighlightHandler handler) {
    return addHandler(CellUnhighlightEvent.KEY, handler);
  }

  public HandlerRegistration addRowHighlightHandler(RowHighlightHandler handler) {
    return addHandler(RowHighlightEvent.KEY, handler);
  }

  public HandlerRegistration addRowSelectionHandler(RowSelectionHandler handler) {
    return addHandler(RowSelectionEvent.KEY, handler);
  }

  public HandlerRegistration addRowUnhighlightHandler(RowUnhighlightHandler handler) {
    return addHandler(RowUnhighlightEvent.KEY, handler);
  }

  /**
   * Deselect a row in the grid. This method is safe to call even if the row is
   * not selected, or doesn't exist (out of bounds).
   * 
   * @param row the row index
   */
  public void deselectRow(int row) {
    deselectRow(row, true);
  }

  /**
   * Deselect all selected rows in the data table.
   */
  public void deselectRows() {
    deselectRows(true);
  }

  /**
   * @return the set of selected row indexes
   */
  public Set<Integer> getSelectedRows() {
    return selectedRows.keySet();
  }

  /**
   * Explicitly gets the {@link SelectionGridCellFormatter}. The results of
   * {@link com.google.gwt.gen2.table.client.overrides.HTMLTable#getCellFormatter()}
   * may also be downcast to a {@link SelectionGridCellFormatter}.
   * 
   * @return the FlexTable's cell formatter
   */
  public SelectionGridCellFormatter getSelectionGridCellFormatter() {
    return (SelectionGridCellFormatter) getCellFormatter();
  }

  /**
   * Explicitly gets the {@link SelectionGridRowFormatter}. The results of
   * {@link com.google.gwt.gen2.table.client.overrides.HTMLTable#getRowFormatter()}
   * may also be downcast to a {@link SelectionGridRowFormatter}.
   * 
   * @return the FlexTable's cell formatter
   */
  public SelectionGridRowFormatter getSelectionGridRowFormatter() {
    return (SelectionGridRowFormatter) getRowFormatter();
  }

  /**
   * @return the selection policy
   */
  public SelectionPolicy getSelectionPolicy() {
    return selectionPolicy;
  }

  /**
   * @param row the row index
   * @return true if the row is selected, false if not
   */
  public boolean isRowSelected(int row) {
    return selectedRows.containsKey(new Integer(row));
  }

  @Override
  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);
    Element targetRow = null;
    Element targetCell = null;

    switch (DOM.eventGetType(event)) {
      // Hover the cell on mouse over
      case Event.ONMOUSEOVER:
        Element cellElem = getEventTargetCell(event);
        if (cellElem != null) {
          hoverCell(cellElem);
        }
        break;

      // Unhover on mouse out
      case Event.ONMOUSEOUT:
        Element toElem = DOM.eventGetToElement(event);
        if (hoveringRowElem != null
            && (toElem == null || !DOM.isOrHasChild(hoveringRowElem, toElem))) {
          // Check that the coordinates are not directly over the cell
          int clientX = DOM.eventGetClientX(event);
          int clientY = DOM.eventGetClientY(event);
          int rowLeft = DOM.getAbsoluteLeft(hoveringRowElem);
          int rowTop = DOM.getAbsoluteTop(hoveringRowElem);
          int rowWidth = DOM.getElementPropertyInt(hoveringRowElem, "offsetWidth");
          int rowHeight = DOM.getElementPropertyInt(hoveringRowElem, "offsetHeight");
          int rowBottom = rowTop + rowHeight;
          int rowRight = rowLeft + rowWidth;
          if (clientX > rowLeft && clientX < rowRight && clientY > rowTop && clientY < rowBottom) {
            return;
          }

          // Unhover the current cell
          hoverCell(null);
        }
        break;

      // Select a row on click
      case Event.ONMOUSEDOWN:
        // Get the target row
        targetCell = getEventTargetCell(event);
        if (targetCell == null) {
          return;
        }
        targetRow = DOM.getParent(targetCell);
        int targetRowIndex = getRowIndex(targetRow);

        // Select the row
        if (selectionPolicy == SelectionPolicy.MULTI_ROW) {
          boolean shiftKey = DOM.eventGetShiftKey(event);
          boolean ctrlKey = DOM.eventGetCtrlKey(event) || DOM.eventGetMetaKey(event);

          // Prevent default text selection
          if (ctrlKey || shiftKey) {
            DOM.eventPreventDefault(event);
          }

          // Select the rows
          selectRow(targetRowIndex, ctrlKey, shiftKey);
        } else if (selectionPolicy == SelectionPolicy.ONE_ROW) {
          selectRow(-1, targetRow, true, true);
          lastSelectedRowIndex = targetRowIndex;
          break;
        }
        break;
    }
  }

  /**
   * Select a row in the data table.
   * 
   * @param row the row index
   * @param unselectAll unselect all other rows first
   * @throws IndexOutOfBoundsException
   */
  public void selectRow(int row, boolean unselectAll) {
    selectRow(row, getRowFormatter().getElement(row), unselectAll, true);
  }

  /**
   * Select a row in the data table. Simulate the effect of a shift click and/or
   * control click. This method ignores the selection policy, which only applies
   * to user selection via mouse events.
   * 
   * @param row the row index
   * @param ctrlKey true to simulate a control click
   * @param shiftKey true to simulate a shift selection
   * @throws IndexOutOfBoundsException
   */
  public void selectRow(int row, boolean ctrlKey, boolean shiftKey) {
    // Check the row bounds
    checkRowBounds(row);

    // Get the old list of selected rows
    Set<Row> oldRowList = getSelectedRowsSet();

    // Deselect all rows
    if (!ctrlKey) {
      deselectRows(false);
    }

    if (shiftKey && (lastSelectedRowIndex > -1)) {
      // Shift+select rows
      SelectionGridRowFormatter formatter = getSelectionGridRowFormatter();
      int firstRow = Math.min(row, lastSelectedRowIndex);
      int lastRow = Math.max(row, lastSelectedRowIndex);
      lastRow = Math.min(lastRow, getRowCount() - 1);
      for (int curRow = firstRow; curRow <= lastRow; curRow++) {
        selectRow(curRow, formatter.getRawElement(curRow), false, false);
      }

      // Fire Event
      fireRowSelectionEvent(oldRowList);
    } else if (selectedRows.containsKey(new Integer(row))) {
      // Ctrl+unselect a selected row
      deselectRow(row, false);
      lastSelectedRowIndex = row;
      fireRowSelectionEvent(oldRowList);
    } else {
      // Select the row
      SelectionGridRowFormatter formatter = getSelectionGridRowFormatter();
      selectRow(row, formatter.getRawElement(row), false, false);
      lastSelectedRowIndex = row;
      fireRowSelectionEvent(oldRowList);
    }
  }

  /**
   * Set the selection policy, which determines if the user can select zero,
   * one, or multiple rows.
   * 
   * @param selectionPolicy the selection policy
   */
  public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
    if (this.selectionPolicy != selectionPolicy) {
      deselectRows();
      this.selectionPolicy = selectionPolicy;
    }
  }

  /**
   * Deselect a row in the grid. This method is safe to call even if the row is
   * not selected, or doesn't exist (out of bounds).
   * 
   * @param row the row index
   * @param fireEvent true to fire events
   */
  protected void deselectRow(int row, boolean fireEvent) {
    Element rowElem = selectedRows.remove(new Integer(row));
    if (rowElem != null) {
      // Get the old list of selected rows
      Set<Row> oldRows = null;
      if (fireEvent) {
        oldRows = getSelectedRowsSet();
      }

      // Deselect the row
      setStyleName(rowElem, "selected", false);

      // Fire Event
      if (fireEvent) {
        fireRowSelectionEvent(oldRows);
      }
    }
  }

  /**
   * Deselect all selected rows in the data table.
   * 
   * @param fireEvent true to fire events
   */
  protected void deselectRows(boolean fireEvent) {
    // Get the old list of selected rows
    Set<Row> oldRows = null;
    if (fireEvent) {
      oldRows = getSelectedRowsSet();
    }

    // Deselect all rows
    for (Element rowElem : selectedRows.values()) {
      setStyleName(rowElem, "selected", false);
    }

    // Clear out the rows
    selectedRows.clear();

    // Fire event
    if (fireEvent) {
      fireRowSelectionEvent(oldRows);
    }
  }

  /**
   * Fire a {@link RowSelectionEvent}. This method will automatically add the
   * currently selected rows.
   * 
   * @param oldRowList the set of previously selected rows
   */
  protected void fireRowSelectionEvent(Set<Row> oldRowList) {
    Set<Row> newRowList = getSelectedRowsSet();
    if (newRowList.equals(oldRowList)) {
      return;
    }
    fireEvent(new RowSelectionEvent(oldRowList, newRowList));
  }

  /**
   * @return a map or selected row indexes to their elements
   */
  protected Map<Integer, Element> getSelectedRowsMap() {
    return selectedRows;
  }

  /**
   * @return a list of selected rows to pass into a {@link RowSelectionEvent}
   */
  protected Set<Row> getSelectedRowsSet() {
    Set<Row> rowSet = new TreeSet<Row>();
    for (Integer rowIndex : selectedRows.keySet()) {
      rowSet.add(new Row(rowIndex.intValue()));
    }
    return rowSet;
  }

  /**
   * Set the current hovering cell.
   * 
   * @param cellElem the cell element
   * TODO(jlabanca): change all references of hover to highlight
   */
  protected void hoverCell(Element cellElem) {
    // Ignore if the cell is already being hovered
    if (DOM.compare(cellElem, hoveringCellElem)) {
      return;
    }

    // Get the row element
    Element rowElem = null;
    if (cellElem != null) {
      rowElem = DOM.getParent(cellElem);
    }

    // Unhover the current cell
    if (hoveringCellElem != null) {
      setStyleName(hoveringCellElem, "hovering", false);
      fireEvent(new CellUnhighlightEvent(hoveringRowIndex, hoveringCellIndex));
      hoveringCellElem = null;
      hoveringCellIndex = -1;

      // Unhover the current row if it changed
      if (!DOM.compare(rowElem, hoveringRowElem)) {
        setStyleName(hoveringRowElem, "hovering", false);
        fireEvent(new RowUnhighlightEvent(hoveringRowIndex));
        hoveringRowElem = null;
        hoveringRowIndex = -1;
      }
    }

    // Hover the cell
    if (cellElem != null) {
      setStyleName(cellElem, "hovering", true);
      hoveringCellElem = cellElem;
      hoveringCellIndex = OverrideDOM.getCellIndex(cellElem);

      // Hover the row if it changed
      if (hoveringRowElem == null) {
        setStyleName(rowElem, "hovering", true);
        hoveringRowElem = rowElem;
        hoveringRowIndex = getRowIndex(hoveringRowElem);
        fireEvent(new RowHighlightEvent(hoveringRowIndex));
      }

      // Fire listeners
      fireEvent(new CellHighlightEvent(hoveringRowIndex, hoveringCellIndex));
    }
  }

  @Override
  protected int insertRow(int beforeRow) {
    deselectRows();
    return super.insertRow(beforeRow);
  }

  @Override
  protected void removeRow(int row) {
    deselectRows();
    super.removeRow(row);
  }

  /**
   * Select a row in the data table.
   * 
   * @param row the row index, or -1 if unknown
   * @param rowElem the row element
   * @param unselectAll true to unselect all currently selected rows
   * @param fireEvent true to fire the select event to listeners
   */
  protected void selectRow(int row, Element rowElem, boolean unselectAll, boolean fireEvent) {
    // Get the row index if needed
    if (row < 0) {
      row = getRowIndex(rowElem);
    }

    // Ignore request if row already selected
    Integer rowI = new Integer(row);
    if (selectedRows.containsKey(rowI)) {
      return;
    }

    // Get the old list of selected rows
    Set<Row> oldRowSet = null;
    if (fireEvent) {
      oldRowSet = getSelectedRowsSet();
    }

    // Deselect current rows
    if (unselectAll) {
      deselectRows(false);
    }

    // Select the new row
    selectedRows.put(rowI, rowElem);
    setStyleName(rowElem, "selected", true);

    // Fire grid listeners
    if (fireEvent) {
      fireRowSelectionEvent(oldRowSet);
    }
  }
}
