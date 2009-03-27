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

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.table.override.client.FlexTable;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A variation of the {@link FlexTable} that supports smarter column resizing
 * options. Unlike the {@link FlexTable}, columns resized in the
 * {@link FixedWidthFlexTable} class are guaranteed to be resized correctly.
 */
public class FixedWidthFlexTable extends FlexTable {
  /**
   * An implementation used accommodate differences in column width
   * implementations between browsers.
   */
  private static class FixedWidthFlexTableImpl {
    /**
     * Create the ghost row.
     * 
     * @return the ghost row element
     */
    public Element createGhostRow() {
      Element ghostRow = DOM.createTR();
      ghostRow.getStyle().setPropertyPx("margin", 0);
      ghostRow.getStyle().setPropertyPx("padding", 0);
      ghostRow.getStyle().setPropertyPx("height", 0);
      ghostRow.getStyle().setProperty("overflow", "hidden");
      return ghostRow;
    }

    /**
     * Recalculate the ideal column widths of each column in the data table.
     * This method assumes that the tableLayout has already been changed.
     * 
     * @param table the table
     */
    public void recalculateIdealColumnWidthsImpl(FixedWidthFlexTable table) {
      // We need at least one cell to do any calculations
      int columnCount = table.getColumnCount();
      if (!table.isAttached() || table.getRowCount() == 0 || columnCount < 0) {
        table.idealWidths = new int[0];
        return;
      }

      // Determine the width of each column
      FixedWidthFlexCellFormatter formatter = (FixedWidthFlexCellFormatter) table.getCellFormatter();
      table.idealWidths = new int[columnCount];
      for (int i = 0; i < columnCount; i++) {
        Element td = table.getGhostCellElement(i);
        table.idealWidths[i] = td.getPropertyInt("clientWidth");
      }
    }

    /**
     * Setup to recalculate column widths.
     * 
     * @param table the table
     */
    public void recalculateIdealColumnWidthsSetup(FixedWidthFlexTable table) {
      // Let the table layout naturally
      table.getElement().getStyle().setProperty("tableLayout", "");
    }

    /**
     * Tear down after recalculating column widths.
     * 
     * @param table the table
     */
    public void recalculateIdealColumnWidthsTeardown(FixedWidthFlexTable table) {
      // Reset the table layout to fixed
      table.getElement().getStyle().setProperty("tableLayout", "fixed");
    }

    /**
     * Set the width of a column.
     * 
     * @param column the index of the column
     * @param width the width in pixels
     * @throws IndexOutOfBoundsException
     */
    public void setColumnWidth(FixedWidthFlexTable grid, int column, int width) {
      grid.getGhostCellElement(column).getStyle().setPropertyPx("width", width);
    }
  }

  /**
   * Firefox version of the implementation refreshes the table display so the
   * new column size takes effect. Without the display refresh, the column width
   * doesn't update in the browser.
   */
  @SuppressWarnings("unused")
  private static class FixedWidthFlexTableImplFirefox extends
      FixedWidthFlexTableImpl {
    private boolean pendingUpdate = false;

    @Override
    public void setColumnWidth(FixedWidthFlexTable grid, int column, int width) {
      super.setColumnWidth(grid, column, width);
      if (!pendingUpdate) {
        pendingUpdate = true;
        final Element tableElem = grid.getElement();
        tableElem.getStyle().setProperty("width", "1px");
        DeferredCommand.addCommand(new Command() {
          public void execute() {
            tableElem.getStyle().setProperty("width", "0px");
            pendingUpdate = false;
          }
        });
      }
    }
  }

  /**
   * IE6 version of the implementation. IE6 sets the overall column width
   * instead of the innerWidth, so we need to add the padding and spacing.
   */
  @SuppressWarnings("unused")
  private static class FixedWidthFlexTableImplIE6 extends
      FixedWidthFlexTableImpl {
    @Override
    public Element createGhostRow() {
      Element ghostRow = super.createGhostRow();
      ghostRow.getStyle().setProperty("display", "none");
      return ghostRow;
    }

    @Override
    public void recalculateIdealColumnWidthsSetup(FixedWidthFlexTable table) {
      super.recalculateIdealColumnWidthsSetup(table);
      table.ghostRow.getStyle().setProperty("display", "");
    }

    @Override
    public void recalculateIdealColumnWidthsTeardown(FixedWidthFlexTable table) {
      table.ghostRow.getStyle().setProperty("display", "none");
      super.recalculateIdealColumnWidthsTeardown(table);
    }

    @Override
    public void setColumnWidth(FixedWidthFlexTable grid, int column, int width) {
      width += 2 * grid.getCellPadding() + grid.getCellSpacing();
      super.setColumnWidth(grid, column, width);
    }
  }

  /**
   * Safari version of the implementation. Safari sets the overall column width
   * instead of the innerWidth, so we need to add the padding and spacing.
   */
  @SuppressWarnings("unused")
  private static class FixedWidthFlexTableImplSafari extends
      FixedWidthFlexTableImpl {
    @Override
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
  @SuppressWarnings("unused")
  private static class FixedWidthFlexTableImplOpera extends
      FixedWidthFlexTableImpl {
    @Override
    public void setColumnWidth(FixedWidthFlexTable grid, int column, int width) {
      super.setColumnWidth(grid, column, width);
      Element tableElem = grid.getElement();
      Element parentElem = DOM.getParent(tableElem);
      int scrollLeft = 0;
      if (parentElem != null) {
        scrollLeft = parentElem.getScrollLeft();
      }
      tableElem.getStyle().setProperty("display", "none");
      tableElem.getStyle().setProperty("display", "");
      if (parentElem != null) {
        parentElem.setScrollLeft(scrollLeft);
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
    @Override
    public void setColSpan(int row, int column, int colSpan) {
      colSpan = Math.max(1, colSpan);
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
    @Override
    public void setRowSpan(int row, int column, int rowSpan) {
      rowSpan = Math.max(1, rowSpan);
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
  private static FixedWidthFlexTableImpl impl = GWT.create(FixedWidthFlexTableImpl.class);

  /**
   * A mapping of column indexes to their widths in pixels.
   * 
   * key = column index
   * 
   * value = column width in pixels
   */
  private Map<Integer, Integer> colWidths = new HashMap<Integer, Integer>();

  /**
   * A mapping of rows to the number of raw columns (not cells) that they
   * contain.
   * 
   * key = the row index
   * 
   * value = the number of raw columns in the row
   */
  private List<Integer> columnsPerRow = new ArrayList<Integer>();

  /**
   * A mapping of raw columns counts to the number of rows with that column
   * count. For example, if three rows contain a raw column count of five, one
   * of the entries will be (5, 3);
   * 
   * key = number of raw columns
   * 
   * value = number of rows with that many raw columns
   */
  private Map<Integer, Integer> columnCountMap = new HashMap<Integer, Integer>();

  /**
   * The maximum number of raw columns in any single row.
   */
  private int maxRawColumnCount = 0;

  /**
   * The hidden, zero row used for sizing the columns.
   */
  private Element ghostRow;

  /**
   * The ideal widths of all columns (that are available).
   */
  private int[] idealWidths;

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
    ghostRow = impl.createGhostRow();
    DOM.insertChild(getBodyElement(), ghostRow, 0);
  }

  @Override
  public void clear() {
    super.clear();
    clearIdealWidths();
  }

  /**
   * @return the raw number of columns in this table.
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
      return DEFAULT_COLUMN_WIDTH;
    } else {
      return ((Integer) colWidth).intValue();
    }
  }

  /**
   * <p>
   * Calculate the ideal width required to tightly wrap the specified column. If
   * the ideal column width cannot be calculated (eg. if the table is not
   * attached), -1 is returned.
   * </p>
   * <p>
   * Note that this method requires an expensive operation whenever the content
   * of the table is changed, so you should only call it after you've completely
   * modified the contents of your table.
   * </p>
   * 
   * @return the ideal column width, or -1 if it is not applicable
   */
  public int getIdealColumnWidth(int column) {
    maybeRecalculateIdealColumnWidths();
    if (idealWidths.length > column) {
      return idealWidths[column];
    }
    return -1;
  }

  /**
   * @see FlexTable
   */
  @Override
  public Element insertCell(int beforeRow, int beforeColumn) {
    clearIdealWidths();
    Element td = super.insertCell(beforeRow, beforeColumn);
    DOM.setStyleAttribute(td, "overflow", "hidden");
    setNumColumnsPerRow(beforeRow, getNumColumnsPerRow(beforeRow) + 1);
    return td;
  }

  /**
   * @see FlexTable
   */
  @Override
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

  @Override
  public boolean remove(Widget widget) {
    if (super.remove(widget)) {
      clearIdealWidths();
      return true;
    }
    return false;
  }

  /**
   * @see FlexTable
   */
  @Override
  public void removeCell(int row, int column) {
    clearIdealWidths();
    int colSpan = getFlexCellFormatter().getColSpan(row, column);
    int rowSpan = getFlexCellFormatter().getRowSpan(row, column);
    super.removeCell(row, column);

    // Update the column counts
    for (int i = row; i < row + rowSpan; i++) {
      setNumColumnsPerRow(i, getNumColumnsPerRow(i) - colSpan);
    }
  }

  /**
   * @see FlexTable
   */
  @Override
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
    clearIdealWidths();
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

  @Override
  public void setCellPadding(int padding) {
    super.setCellPadding(padding);

    // Reset the width of all columns
    for (Map.Entry<Integer, Integer> entry : colWidths.entrySet()) {
      setColumnWidth(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void setCellSpacing(int spacing) {
    super.setCellSpacing(spacing);

    // Reset the width of all columns
    for (Map.Entry<Integer, Integer> entry : colWidths.entrySet()) {
      setColumnWidth(entry.getKey(), entry.getValue());
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

    // Add the width to the map
    width = Math.max(1, width);
    colWidths.put(new Integer(column), new Integer(width));

    // Update the cell width if possible
    int numGhosts = getGhostColumnCount();
    if (column >= numGhosts) {
      return;
    }

    // Set the actual column width
    impl.setColumnWidth(this, column, width);
  }

  @Override
  public void setHTML(int row, int column, String html) {
    super.setHTML(row, column, html);
    clearIdealWidths();
  }

  @Override
  public void setText(int row, int column, String text) {
    super.setText(row, column, text);
    clearIdealWidths();
  }

  @Override
  public void setWidget(int row, int column, Widget widget) {
    super.setWidget(row, column, widget);
    clearIdealWidths();
  }

  /**
   * @see FlexTable
   */
  @Override
  protected void addCells(int row, int num) {
    // Account for ghost row
    super.addCells(row + 1, num);
    clearIdealWidths();
  }

  @Override
  protected int getDOMCellCount(int row) {
    // Account for ghost row
    return super.getDOMCellCount(row + 1);
  }

  @Override
  protected int getDOMRowCount() {
    // Account for ghost row
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

  @Override
  protected int getRowIndex(Element rowElem) {
    int rowIndex = super.getRowIndex(rowElem);
    if (rowIndex < 0) {
      return rowIndex;
    }
    return rowIndex - 1;
  }

  @Override
  protected boolean internalClearCell(Element td, boolean clearInnerHTML) {
    clearIdealWidths();
    return super.internalClearCell(td, clearInnerHTML);
  }

  @Override
  protected void onAttach() {
    super.onAttach();
    clearIdealWidths();
  }

  @Override
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
   * Recalculate the ideal column widths of each column in the data table.
   */
  protected void recalculateIdealColumnWidths() {
    // We need at least one cell to do any calculations
    int columnCount = getColumnCount();
    if (!isAttached() || getRowCount() == 0 || columnCount < 0) {
      idealWidths = new int[0];
      return;
    }

    recalculateIdealColumnWidthsSetup();
    recalculateIdealColumnWidthsImpl();
    recalculateIdealColumnWidthsTeardown();
  }

  /**
   * Clear the idealWidths field when the ideal widths change.
   */
  void clearIdealWidths() {
    idealWidths = null;
  }

  /**
   * @return true if the ideal column widths have already been calculated
   */
  boolean isIdealColumnWidthsCalculated() {
    return idealWidths != null;
  }

  /**
   * Recalculate the ideal column widths of each column in the data table. This
   * method assumes that the tableLayout has already been changed.
   */
  void recalculateIdealColumnWidthsImpl() {
    impl.recalculateIdealColumnWidthsImpl(this);
  }

  /**
   * Setup to recalculate column widths.
   */
  void recalculateIdealColumnWidthsSetup() {
    impl.recalculateIdealColumnWidthsSetup(this);
  }

  /**
   * Tear down after recalculating column widths.
   */
  void recalculateIdealColumnWidthsTeardown() {
    impl.recalculateIdealColumnWidthsTeardown(this);
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
   * Get the number of columns in a row.
   * 
   * @return the number of columns
   */
  private int getNumColumnsPerRow(int row) {
    if (columnsPerRow.size() <= row) {
      return 0;
    } else {
      return columnsPerRow.get(row).intValue();
    }
  }

  /**
   * Recalculate the ideal column widths of each column in the data table if
   * they have changed since the last calculation.
   */
  private void maybeRecalculateIdealColumnWidths() {
    if (idealWidths == null) {
      recalculateIdealColumnWidths();
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
      int numRows = columnCountMap.get(oldNumColumnsI).intValue();
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
        int numRows = columnCountMap.get(numColumnsI).intValue();
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
      for (Integer curNumColumns : columnCountMap.keySet()) {
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
      for (int i = curNumGhosts; i < maxRawColumnCount; i++) {
        Element td = getGhostCellElement(i);
        td.getStyle().setProperty("overflow", "hidden");
        td.getStyle().setPropertyPx("height", 0);
        td.getStyle().setPropertyPx("paddingTop", 0);
        td.getStyle().setPropertyPx("paddingBottom", 0);
        td.getStyle().setPropertyPx("borderTop", 0);
        td.getStyle().setPropertyPx("borderBottom", 0);
        setColumnWidth(i, getColumnWidth(i));
      }
    } else if (maxRawColumnCount < curNumGhosts) {
      // Remove ghost rows as needed
      int cellsToRemove = curNumGhosts - maxRawColumnCount;
      for (int i = 0; i < cellsToRemove; i++) {
        DOM.removeChild(ghostRow, getGhostCellElement(maxRawColumnCount));
      }
    }
  }
}
