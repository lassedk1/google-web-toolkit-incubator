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
import com.google.gwt.user.client.Event;
import com.google.gwt.widgetideas.table.client.overrides.Grid;
import com.google.gwt.widgetideas.table.client.overrides.OverrideDOM;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A variation of the {@link Grid} that supports row or cell hovering and row
 * selection.
 */
public class SelectionGrid extends Grid implements SourceTableSelectionEvents {
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
   * This class contains methods used to format a table's rows.
   */
  public class SelectedGridRowFormatter extends RowFormatter {
    protected Element getRawElement(int row) {
      return super.getRawElement(row);
    }
  }

  /**
   * The {@link TableSelectionListener}s attached to this grid.
   */
  private TableSelectionListenerCollection tableSelectionListeners = null;

  /**
   * The current hovering policy.
   */
  private int hoveringPolicy = HOVERING_POLICY_CELL;

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
   * Constructor.
   */
  public SelectionGrid() {
    super();
    setRowFormatter(new SelectedGridRowFormatter());

    // Sink hover and selection events
    sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEDOWN | Event.ONCLICK);
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

  /**
   * Add a {@link TableSelectionListener}.
   * 
   * @param listener the listener
   */
  public void addTableSelectionListener(TableSelectionListener listener) {
    if (tableSelectionListeners == null) {
      tableSelectionListeners = new TableSelectionListenerCollection();
    }
    tableSelectionListeners.add(listener);
  }

  /**
   * Deselect a row in the data table. This method is safe to call even if the
   * row is not selected, or doesn't exist (out of bounds).
   * 
   * @param row the row index
   */
  public void deselectRow(int row) {
    Element rowElem = (Element) selectedRows.remove(new Integer(row));
    if (rowElem != null) {
      deselectRow(row, rowElem);

      // Fire grid listeners
      if (tableSelectionListeners != null) {
        tableSelectionListeners.fireRowDeselected(row);
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
    if (tableSelectionListeners != null) {
      tableSelectionListeners.fireAllRowsDeselected();
    }

    // Clear out the rows
    selectedRows.clear();
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
   * Get the minimum row that can be hovered or selected.
   * 
   * @return the minimum hover row
   */
  public int getMinHoverRow() {
    return minHoverRowIndex;
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
   * Returns true if the row is currently selected.
   * 
   * @param row the row index
   * @return true if the row is selected
   */
  public boolean isRowSelected(int row) {
    return selectedRows.containsKey(new Integer(row));
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
        int rowIndex = getRowIndex(targetRow);
        int cellIndex = OverrideDOM.getCellIndex(targetCell);
        onCellClicked(rowIndex, cellIndex);
        if (tableSelectionListeners != null) {
          tableSelectionListeners.fireCellClicked(rowIndex, cellIndex);
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
        int targetRowIndex = getRowIndex(targetRow);

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
   * Remove a {@link TableSelectionListener}.
   * 
   * @param listener the listener to remove
   */
  public void removeTableSelectionListener(TableSelectionListener listener) {
    if (tableSelectionListeners != null) {
      tableSelectionListeners.remove(listener);
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

    // Deselect all rows
    if (!ctrlKey) {
      deselectRows();
    }

    if (shiftKey && (lastSelectedRowIndex > -1)) {
      // Shift+select rows
      SelectedGridRowFormatter formatter =
          (SelectedGridRowFormatter) getRowFormatter();
      int firstRow = Math.min(row, lastSelectedRowIndex);
      int lastRow = Math.max(row, lastSelectedRowIndex);
      lastRow = Math.min(lastRow, getRowCount() - 1);
      for (int curRow = firstRow; curRow <= lastRow; curRow++) {
        selectRow(curRow, formatter.getRawElement(curRow), false, false);
      }

      // Fire grid listeners
      if (tableSelectionListeners != null) {
        tableSelectionListeners.fireRowsSelected(firstRow, lastRow - firstRow
            + 1);
      }
    } else if (selectedRows.containsKey(new Integer(row))) {
      // Ctrl+unselect a selected row
      deselectRow(row);
      lastSelectedRowIndex = row;
    } else {
      // Select the row
      SelectedGridRowFormatter formatter =
          (SelectedGridRowFormatter) getRowFormatter();
      selectRow(row, formatter.getRawElement(row), false, true);
      lastSelectedRowIndex = row;
    }
  }

  /**
   * Set the hovering policy.
   * 
   * @param hoveringPolicy the hovering policy to use
   */
  public void setHoveringPolicy(int hoveringPolicy) {
    // Check the hovering policy
    if (hoveringPolicy != HOVERING_POLICY_DISABLED
        && hoveringPolicy != HOVERING_POLICY_CELL
        && hoveringPolicy != HOVERING_POLICY_ROW) {
      throw new IllegalArgumentException("Invalid hoveringPolicy");
    }

    setHoveringPolicyRaw(hoveringPolicy);
  }

  /**
   * Set the index of the first row that can be hovered or selected. Use this
   * option if the first one or more rows of your Grid are used as headers.
   * 
   * @param minRow the min row that can be hovered
   */
  public void setMinHoverRow(int minRow) {
    minHoverRowIndex = Math.max(0, minRow);
  }

  /**
   * Set the selection policy, which determines if the user can select zero,
   * one, or multiple rows.
   * 
   * @param selectionPolicy the selection policy
   */
  public void setSelectionPolicy(int selectionPolicy) {
    // Check the selection policy
    if (selectionPolicy != SELECTION_POLICY_DISABLED
        && selectionPolicy != SELECTION_POLICY_ONE_ROW
        && selectionPolicy != SELECTION_POLICY_MULTI_ROW) {
      throw new IllegalArgumentException("Invalid selectionPolicy");
    }

    // Set selection policy
    deselectRows();
    this.selectionPolicy = selectionPolicy;
  }

  /**
   * Get the element that is currently being hovered.
   * 
   * @return the hovering element
   */
  protected Element getHoveringElement() {
    return hoveringElement;
  }

  /**
   * Get the index of a row given the row element.
   */
  protected int getRowIndex(Element rowElem) {
    return OverrideDOM.getRowIndex(rowElem);
  }

  /**
   * Returns a HashMap of selected rows.
   * 
   * @return the selected rows
   */
  protected Map getSelectedRowsMap() {
    return selectedRows;
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

    int row = getRowIndex(rowElem);
    if (row >= minHoverRowIndex) {
      // Highlight the row if not selected
      setStyleName(cellElem, "hovering", true);
      hoveringElement = cellElem;
      hoveringRowIndex = row;
      hoveringCellIndex = OverrideDOM.getCellIndex(cellElem);

      // Fire grid listeners
      if (tableSelectionListeners != null) {
        tableSelectionListeners.fireCellHover(row, hoveringCellIndex);
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

    int row = getRowIndex(rowElem);
    if (row >= minHoverRowIndex) {
      // Highlight the row if not selected
      setStyleName(rowElem, "hovering", true);
      hoveringElement = rowElem;
      hoveringRowIndex = row;

      // Fire grid listeners
      if (tableSelectionListeners != null) {
        tableSelectionListeners.fireRowHover(row);
      }
    }
  }

  /**
   * Inserts a new row into the table, deselecting all selected rows.
   * 
   * @param beforeRow the index before which the new row will be inserted
   * @return the index of the newly-created row
   * @throws IndexOutOfBoundsException
   */
  protected int insertRow(int beforeRow) {
    deselectRows();
    return super.insertRow(beforeRow);
  }

  /**
   * Removes the specified row from the table.
   * 
   * @param row the index of the row to be removed
   * @throws IndexOutOfBoundsException
   */
  protected void removeRow(int row) {
    checkRowBounds(row);
    deselectRows();
    super.removeRow(row);
    numRows--;
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
      row = getRowIndex(rowElem);
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
      if (fireEvent && tableSelectionListeners != null) {
        tableSelectionListeners.fireRowsSelected(row, 1);
      }
    }
  }

  /**
   * Set the hovering policy without checking that it is valid.
   * 
   * @param hoveringPolicy the new hovering policy
   */
  protected void setHoveringPolicyRaw(int hoveringPolicy) {
    if (this.hoveringPolicy != hoveringPolicy) {
      unhover();
      this.hoveringPolicy = hoveringPolicy;
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
    if (tableSelectionListeners != null) {
      if (hoveringCellIndex >= 0) {
        // Unhover the cell
        tableSelectionListeners.fireCellUnhover(hoveringRowIndex,
            hoveringCellIndex);
      } else {
        // Unhover the row
        tableSelectionListeners.fireRowUnhover(hoveringCellIndex);
      }
    }

    // Reset the hovering integers
    hoveringRowIndex = -1;
    hoveringCellIndex = -1;
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
}
