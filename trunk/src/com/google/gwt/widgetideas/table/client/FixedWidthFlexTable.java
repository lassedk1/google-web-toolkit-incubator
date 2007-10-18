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
import com.google.gwt.widgetideas.table.client.overrides.FlexTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A variation of the {@link FlexTable} that supports smarter column resizing
 * options. Unlike the {@link FlexTable}, columns resized in the
 * {@link FixedWidthFlexTable} class are guaranteed to be resized correctly.
 */
public class FixedWidthFlexTable extends FlexTable implements
    HasFixedColumnWidth {
  /**
   * An implementation used accommodate differences in column width
   * implementations between browsers.
   */
  private static class FixedWidthFlexTableImpl {
    /**
     * Set the width of a column.
     * 
     * @param column the index of the column
     * @param width the width in pixels
     * @throws IndexOutOfBoundsException
     */
    public void setColumnWidth(FixedWidthFlexTable grid, int column, int width) {
      Element tableElem = grid.getElement();
      DOM.setStyleAttribute(grid.getGhostCellElement(column), "width", width
          + "px");
    }
  }

  /**
   * IE6 version of the implementation. IE6 sets the overall column width
   * instead of the innerWidth, so we need to add the padding and spacing.
   */
  private static class FixedWidthFlexTableImplIE6 extends
      FixedWidthFlexTableImpl {
    public void setColumnWidth(FixedWidthFlexTable grid, int column, int width) {
      width += 2 * grid.getCellPadding() + grid.getCellSpacing();
      super.setColumnWidth(grid, column, width);
    }
  }

  /**
   * Safari version of the implementation. Safari sets the overall column width
   * instead of the innerWidth, so we need to add the padding and spacing.
   */
  private static class FixedWidthFlexTableImplSafari extends
      FixedWidthFlexTableImpl {
    public void setColumnWidth(FixedWidthFlexTable grid, int column, int width) {
      width += 2 * grid.getCellPadding() + grid.getCellSpacing();
      super.setColumnWidth(grid, column, width);
    }
  }

  /**
   * Opera version of the implementation refreshes the table display so the new
   * column size takes effect. Without the display refresh, the column width
   * doesn't update in the browser.
   */
  private static class FixedWidthFlexTableImplOpera extends
      FixedWidthFlexTableImpl {
    public void setColumnWidth(FixedWidthFlexTable grid, int column, int width) {
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
   * FlexTable-specific implementation of
   * {@link com.google.gwt.user.client.ui.HTMLTable.CellFormatter}.
   */
  public class FixedWidthFlexCellFormatter extends FlexCellFormatter {
    /**
     * Sets the column span for the given cell. This is the number of logical
     * columns covered by the cell.
     * 
     * @param row the cell's row
     * @param column the cell's column
     * @param colSpan the cell's column span
     * @throws IndexOutOfBoundsException
     */
    public void setColSpan(int row, int column, int colSpan) {
      int colSpanDelta = colSpan - getColSpan(row, column);
      super.setColSpan(row, column, colSpan);

      // Update the column counts
      int rowSpan = getRowSpan(row, column);
      for (int i = row; i < row + rowSpan; i++) {
        setNumColumnsPerRow(i, getNumColumnsPerRow(i) + colSpanDelta);
      }
    }

    /**
     * Sets the row span for the given cell. This is the number of logical rows
     * covered by the cell.
     * 
     * @param row the cell's row
     * @param column the cell's column
     * @param rowSpan the cell's row span
     * @throws IndexOutOfBoundsException
     */
    public void setRowSpan(int row, int column, int rowSpan) {
      int curRowSpan = getRowSpan(row, column);
      super.setRowSpan(row, column, rowSpan);

      // Update the column counts
      int colSpan = getColSpan(row, column);
      if (rowSpan > curRowSpan) {
        for (int i = row + curRowSpan; i < row + rowSpan; i++) {
          setNumColumnsPerRow(i, getNumColumnsPerRow(i) + colSpan);
        }
      } else if (rowSpan < curRowSpan) {
        for (int i = row + rowSpan; i < row + curRowSpan; i++) {
          setNumColumnsPerRow(i, getNumColumnsPerRow(i) - colSpan);
        }
      }
    }

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

    /**
     * Gets the TD element representing the specified cell unsafely (meaning
     * that it doesn't ensure that the row and column are valid).
     * 
     * @param row the row of the cell to be retrieved
     * @param column the column of the cell to be retrieved
     * @return the column's TD element
     */
    protected Element getRawElement(int row, int column) {
      return super.getRawElement(row + 1, column);
    }
  }

  /**
   * This class contains methods used to format a table's columns. It is limited
   * by the support cross-browser HTML support for column formatting.
   */
  public class FixedWidthFlexColumnFormatter extends ColumnFormatter {
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
  public class FixedWidthFlexRowFormatter extends RowFormatter {
    /**
     * Unsafe method to get a row element.
     * 
     * @param row the row to get
     * @return the row element
     */
    protected Element getRawElement(int row) {
      return super.getRawElement(row + 1);
    }
  }

  /**
   * The default width of a column in pixels.
   */
  public static final int DEFAULT_COLUMN_WIDTH = 80;

  /**
   * The implementation class.
   */
  private static FixedWidthFlexTableImpl impl = (FixedWidthFlexTableImpl) GWT.create(FixedWidthFlexTableImpl.class);

  /**
   * A mapping of column indexes to their widths in pixels.
   * 
   * (Integer) key = column index
   * 
   * (Integer) value = column width in pixels
   */
  private Map colWidths = new HashMap();

  /**
   * A mapping of rows to the number of raw columns (not cells) that they
   * contain.
   * 
   * (Integer) key = the row index
   * 
   * (Integer) value = the number of raw columns in the row
   */
  private List columnsPerRow = new ArrayList();

  /**
   * A mapping of raw columns counts to the number of rows with that column
   * count. For example, if three rows contain a raw column count of five, one
   * of the entries will be (5, 3);
   * 
   * (Integer) key = number of raw columns
   * 
   * (Integer) value = number of rows with that many raw columns
   */
  private Map columnCountMap = new HashMap();

  /**
   * The maximum number of raw columns in any single row.
   */
  private int maxRawColumnCount = 0;

  /**
   * The hidden, zero row used for sizing the columns.
   */
  private Element zeroRow;

  /**
   * Constructor.
   */
  public FixedWidthFlexTable() {
    super();
    Element tableElem = getElement();
    DOM.setStyleAttribute(tableElem, "tableLayout", "fixed");
    DOM.setStyleAttribute(tableElem, "width", "0px");

    setCellFormatter(new FixedWidthFlexCellFormatter());
    setColumnFormatter(new FixedWidthFlexColumnFormatter());
    setRowFormatter(new FixedWidthFlexRowFormatter());

    // Create the zero row for sizing
    zeroRow = DOM.createTR();
    DOM.setStyleAttribute(zeroRow, "height", "0px");
    DOM.setStyleAttribute(zeroRow, "overflow", "hidden");
    DOM.insertChild(getBodyElement(), zeroRow, 0);
  }
  
  /**
   * Gets the number of columns in this table.
   * 
   * @return the number of columns
   */
  public int getColumnCount() {
    return maxRawColumnCount;
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
      return getDefaultColumnWidth();
    } else {
      return ((Integer) colWidth).intValue();
    }
  }

  /**
   * Get the default width of a column in pixels.
   * 
   * @return the default column width
   */
  public int getDefaultColumnWidth() {
    return DEFAULT_COLUMN_WIDTH;
  }
  
  /**
   * Inserts a cell into the FlexTable.
   * 
   * @param beforeRow the cell's row
   * @param beforeColumn the cell's column
   * @return the element
   */
  public Element insertCell(int beforeRow, int beforeColumn) {
    Element td = super.insertCell(beforeRow, beforeColumn);
    DOM.setStyleAttribute(td, "overflow", "hidden");
    setNumColumnsPerRow(beforeRow, getNumColumnsPerRow(beforeRow) + 1);
    return td;
  }

  /**
   * Inserts a new row into the table.
   * 
   * @param beforeRow the index before which the new row will be inserted
   * @return the index of the newly-created row
   * @throws IndexOutOfBoundsException
   */
  public int insertRow(int beforeRow) {
    // Get the affected colSpan, which is the number of raw cells created by
    // row spanning cells in rows above the new row.
    FlexCellFormatter formatter = getFlexCellFormatter();
    int affectedColSpan = getNumColumnsPerRow(beforeRow);
    if (beforeRow != getRowCount()) {
      int numCellsInRow = getCellCount(beforeRow);
      for (int cell = 0; cell < numCellsInRow; cell++) {
        affectedColSpan -= formatter.getColSpan(beforeRow, cell);
      }
    }

    // Specifically allow the row count as an insert position.
    if (beforeRow != getRowCount()) {
      checkRowBounds(beforeRow);
    }
    Element tr = DOM.createTR();
    DOM.insertChild(getBodyElement(), tr, beforeRow + 1);
    columnsPerRow.add(beforeRow, new Integer(0));

    // Make sure the column counts are still correct by iterating backward
    // through the rows looking for cells that span down into the newly
    // inserted row. Stop iterating when every raw column is accounted for.
    for (int curRow = beforeRow - 1; curRow >= 0; curRow--) {
      // No more affect of rowspan, so we are done
      if (affectedColSpan <= 0) {
        break;
      }

      // Look for cells that span into the new row
      int numCells = getCellCount(curRow);
      for (int curCell = 0; curCell < numCells; curCell++) {
        int affectedRow = curRow + formatter.getRowSpan(curRow, curCell);
        if (affectedRow > beforeRow) {
          int colSpan = formatter.getColSpan(curRow, curCell);
          affectedColSpan -= colSpan;
          setNumColumnsPerRow(beforeRow, getNumColumnsPerRow(beforeRow)
              + colSpan);
          setNumColumnsPerRow(affectedRow, getNumColumnsPerRow(affectedRow)
              - colSpan);
        }
      }
    }

    // Return the new row index
    return beforeRow;
  }

  /**
   * Removes the specified cell from the table.
   * 
   * @param row the row of the cell to remove
   * @param column the column of cell to remove
   * @throws IndexOutOfBoundsException
   */
  public void removeCell(int row, int column) {
    int colSpan = getFlexCellFormatter().getColSpan(row, column);
    int rowSpan = getFlexCellFormatter().getRowSpan(row, column);
    super.removeCell(row, column);

    // Update the column counts
    for (int i = row; i < row + rowSpan; i++) {
      setNumColumnsPerRow(i, getNumColumnsPerRow(i) - colSpan);
    }
  }

  /**
   * Removes the specified row from the table.
   * 
   * @param row the index of the row to be removed
   * @throws IndexOutOfBoundsException
   */
  public void removeRow(int row) {
    // Set the rowspan of everything in this row to 1
    FlexCellFormatter formatter = getFlexCellFormatter();
    int affectedColSpan = getNumColumnsPerRow(row);
    int numCellsInRow = getCellCount(row);
    for (int cell = 0; cell < numCellsInRow; cell++) {
      formatter.setRowSpan(row, cell, 1);
      affectedColSpan -= formatter.getColSpan(row, cell);
    }

    // Actually remove the row
    super.removeRow(row);
    setNumColumnsPerRow(row, -1);
    columnsPerRow.remove(row);

    // Make sure the column counts are still correct by iterating backward
    // through the rows looking for cells that span down into the newly
    // inserted row. Stop iterating when every raw column is accounted for.
    for (int curRow = row - 1; curRow >= 0; curRow--) {
      // No more affects from rowspan, so we are done
      if (affectedColSpan <= 0) {
        break;
      }

      // Look for cells that span into the removed row
      int numCells = getCellCount(curRow);
      for (int curCell = 0; curCell < numCells; curCell++) {
        int affectedRow = curRow + formatter.getRowSpan(curRow, curCell) - 1;
        if (affectedRow >= row) {
          int colSpan = formatter.getColSpan(curRow, curCell);
          affectedColSpan -= colSpan;
          setNumColumnsPerRow(affectedRow, getNumColumnsPerRow(affectedRow)
              + colSpan);
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
    int numGhosts = getGhostColumnCount();
    if (column >= numGhosts) {
      return;
    }

    // Update the total width
    impl.setColumnWidth(this, column, width);
  }

  /**
   * Add cells to the specified row.
   * 
   * @param row the row to affect
   * @param num the number of cells to add
   */
  protected void addCells(int row, int num) {
    super.addCells(row + 1, num);
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
   * Returns the current number of ghost columns in existence.
   * 
   * @return the number of ghost columns
   */
  protected int getGhostColumnCount() {
    return super.getDOMCellCount(0);
  }

  /**
   * Ensure that the cell exists.
   * 
   * @param row the row to prepare.
   * @param column the column to prepare.
   * @throws IndexOutOfBoundsException if the row is negative
   */
  protected void prepareCell(int row, int column) {
    int curNumCells = 0;
    if (getRowCount() > row) {
      curNumCells = getCellCount(row);
    }
    super.prepareCell(row, column);

    // Add ghost columns as needed
    if (column >= curNumCells) {
      // Add ghost cells
      int cellsAdded = column - curNumCells + 1;
      setNumColumnsPerRow(row, getNumColumnsPerRow(row) + cellsAdded);

      // Set the new cells to hide overflow
      for (int cell = curNumCells; cell < column; cell++) {
        Element td = getCellFormatter().getElement(row, cell);
        DOM.setStyleAttribute(td, "overflow", "hidden");
      }
    }
  }

  /**
   * Returns a cell in the ghost row.
   * 
   * @param column the cell's column
   * @return the ghost cell
   */
  private Element getGhostCellElement(int column) {
    return DOM.getChild(zeroRow, column);
  }

  /**
   * Get the number of columns in a row.
   * 
   * @return the number of columns
   */
  private int getNumColumnsPerRow(int row) {
    if (columnsPerRow.size() <= row) {
      return 0;
    } else {
      return ((Integer) columnsPerRow.get(row)).intValue();
    }
  }

  /**
   * Set the number of columns in a row. A negative value in numColumns means
   * the row should be removed.
   * 
   * @param row the row index
   * @param numColumns the number of columns in the row
   */
  private void setNumColumnsPerRow(int row, int numColumns) {
    // Get the old number of raw columns in the row
    int oldNumColumns = getNumColumnsPerRow(row);
    if (oldNumColumns == numColumns) {
      return;
    }

    // Update the list of columns per row
    Integer numColumnsI = new Integer(numColumns);
    Integer oldNumColumnsI = new Integer(oldNumColumns);
    if (row < columnsPerRow.size()) {
      columnsPerRow.set(row, numColumnsI);
    } else {
      columnsPerRow.add(numColumnsI);
    }

    // Decrement the old number of columns
    boolean oldNumColumnsRemoved = false;
    if (columnCountMap.containsKey(oldNumColumnsI)) {
      int numRows = ((Integer) columnCountMap.get(oldNumColumnsI)).intValue();
      if (numRows == 1) {
        columnCountMap.remove(oldNumColumnsI);
        oldNumColumnsRemoved = true;
      } else {
        columnCountMap.put(oldNumColumnsI, new Integer(numRows - 1));
      }
    }

    // Increment the new number of columns
    if (numColumns > 0) {
      if (columnCountMap.containsKey(numColumnsI)) {
        int numRows = ((Integer) columnCountMap.get(numColumnsI)).intValue();
        columnCountMap.put(numColumnsI, new Integer(numRows + 1));
      } else {
        columnCountMap.put(numColumnsI, new Integer(1));
      }
    }

    // Update the maximum number of rows
    if (numColumns > maxRawColumnCount) {
      maxRawColumnCount = numColumns;
    } else if ((numColumns < oldNumColumns)
        && (oldNumColumns == maxRawColumnCount) && oldNumColumnsRemoved) {
      // Column count decreased from max count
      maxRawColumnCount = 0;
      Iterator it = columnCountMap.keySet().iterator();
      while (it.hasNext()) {
        Integer curNumColumns = (Integer) it.next();
        maxRawColumnCount = Math.max(maxRawColumnCount,
            curNumColumns.intValue());
      }
    }

    // Update the ghost row
    updateGhostRow();
  }

  /**
   * Add or remove ghost cells when the table size changes.
   */
  private void updateGhostRow() {
    int curNumGhosts = getGhostColumnCount();
    if (maxRawColumnCount > curNumGhosts) {
      // Add ghosts as needed
      super.addCells(0, maxRawColumnCount - curNumGhosts);
      CellFormatter formatter = getCellFormatter();
      for (int i = curNumGhosts; i < maxRawColumnCount; i++) {
        Element td = getGhostCellElement(i);
        DOM.setStyleAttribute(td, "height", "0px");
        DOM.setStyleAttribute(td, "overflow", "hidden");
        DOM.setStyleAttribute(td, "paddingTop", "0px");
        DOM.setStyleAttribute(td, "paddingBottom", "0px");
        DOM.setStyleAttribute(td, "borderTop", "0px");
        DOM.setStyleAttribute(td, "borderBottom", "0px");
        setColumnWidth(i, getColumnWidth(i));
      }
    } else if (maxRawColumnCount < curNumGhosts) {
      // Remove ghost rows as needed
      int cellsToRemove = curNumGhosts - maxRawColumnCount;
      for (int i = 0; i < cellsToRemove; i++) {
        DOM.removeChild(zeroRow, getGhostCellElement(maxRawColumnCount));
      }
    }
  }
}
