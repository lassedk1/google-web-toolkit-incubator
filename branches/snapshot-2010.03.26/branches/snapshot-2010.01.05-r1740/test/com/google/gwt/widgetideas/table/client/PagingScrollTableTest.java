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

import com.google.gwt.widgetideas.table.client.PagingScrollTable.CellRenderer;
import com.google.gwt.widgetideas.table.client.SortableGrid.ColumnSorter;
import com.google.gwt.widgetideas.table.client.SortableGrid.ColumnSorterCallback;
import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortList;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests methods in the {@link PagingScrollTable} class.
 * 
 * @deprecated Replaced by
 *             {@link com.google.gwt.gen2.table.client.PagingScrollTableTest}
 */
@Deprecated
public class PagingScrollTableTest extends ScrollTableTest {
  /**
   * A {@link RowPagingListener} used for testing.
   */
  private class TestRowPaginingListener implements RowPagingListener {
    /**
     * The current page.
     */
    private int curPage = -1;

    /**
     * Indicates whether or not page loaded has been called.
     */
    private boolean isPageLoaded = false;

    /**
     * The number of pages.
     */
    private int numPages = -1;

    /**
     * @return the curPage
     */
    public int getCurPage() {
      return curPage;
    }

    /**
     * @return the numPages
     */
    public int getNumPages() {
      return numPages;
    }

    /**
     * @return the isPageLoaded
     */
    public boolean isPageLoaded() {
      return isPageLoaded;
    }

    public void onNumPagesChanges(int numPages) {
      this.numPages = numPages;
    }

    public void onPageChanged(int page) {
      this.curPage = page;
    }

    public void onPageLoaded(int page) {
      isPageLoaded = true;
    }

    public void onPagingFailure(Throwable caught) {
    }
  }

  /**
   * Test absolute row operations.
   */
  public void disabledTestAbsoluteRows() {
    // Initialize the grid
    PagingScrollTable<Object> table = getPagingScrollTable();
    FixedWidthGrid grid = table.getDataTable();
    table.setPageSize(5);

    // Set absolute data
    table.gotoPage(1, true);
    assertEquals("6:1", grid.getText(1, 1));
    table.setAbsoluteData(1, 1, "test");
    assertEquals("6:1", grid.getText(1, 1));
    table.setAbsoluteData(6, 1, "test");
    assertEquals("test", grid.getText(1, 1));

    // Insert absolute row
    table.gotoPage(1, true);
    assertEquals("7:1", grid.getText(2, 1));
    table.insertAbsoluteRow(10);
    assertEquals("7:1", grid.getText(2, 1));
    table.insertAbsoluteRow(1);
    assertEquals("6:1", grid.getText(2, 1));
    table.insertAbsoluteRow(7);
    grid.clearCell(4, 2);
    String clearText = grid.getText(4, 2);
    assertEquals(clearText, grid.getText(2, 1).trim());

    // Insert absolute row
    table.gotoPage(1, true);
    assertEquals("7:1", grid.getText(2, 1));
    table.removeAbsoluteRow(10);
    assertEquals("7:1", grid.getText(2, 1));
    table.removeAbsoluteRow(1);
    assertEquals("8:1", grid.getText(2, 1));
    table.removeAbsoluteRow(7);
    assertEquals("9:1", grid.getText(2, 1));
  }

  /**
   * Test accessors.
   */
  public void testAccessor() {
    // Initialize the grid
    PagingScrollTable<Object> table = getPagingScrollTable();
    TableModel<Object> tableModel = table.getTableModel();
    FixedWidthGrid grid = table.getDataTable();

    // pageSize
    table.setPageSize(5);
    assertEquals(5, table.getPageSize());
    table.setPageSize(0);
    assertEquals(0, table.getPageSize());
    table.setPageSize(-1);
    assertEquals(0, table.getPageSize());

    // numPages
    table.setPageSize(5);
    assertEquals(5, table.getNumPages());
    table.setPageSize(10);
    assertEquals(3, table.getNumPages());
    table.setPageSize(25);
    assertEquals(1, table.getNumPages());

    // Unknown number of pages
    tableModel.setRowCount(TableModel.UNKNOWN_ROW_COUNT);
    assertEquals(-1, table.getNumPages());
    table.setPageSize(0);
    assertEquals(1, table.getNumPages());

    // bulk renderer
    FixedWidthGridBulkRenderer bulkRenderer = new FixedWidthGridBulkRenderer(
        grid, 5);
    table.setBulkRenderer(bulkRenderer);
  }

  /**
   * Test inline editing accessors.
   */
  public void testCellEditors() {
    // Initialize the grid
    PagingScrollTable<Object> table = getPagingScrollTable();

    // Create a cell editor
    TextCellEditor<Object> textCellEditor = new TextCellEditor<Object>();

    // cellEditor
    assertFalse(table.hasCellEditor(2));
    assertNull(table.getCellEditor(2));
    table.setCellEditor(2, textCellEditor);
    assertTrue(table.hasCellEditor(2));
    assertFalse(table.hasCellEditor(3));
    assertEquals(textCellEditor, table.getCellEditor(2));
    table.setCellEditor(2, null);
    assertFalse(table.hasCellEditor(2));
    assertNull(table.getCellEditor(2));
  }

  /**
   * Test accessors.
   */
  public void testCellRender() {
    // Initialize the grid
    PagingScrollTable<Object> table = getPagingScrollTable();
    FixedWidthGrid grid = table.getDataTable();

    // Get data without renderer
    table.setPageSize(5);
    table.gotoFirstPage();
    table.renderCell(0, 0, "0:0");
    assertNull(table.getCellRenderer());
    assertEquals("0:0", grid.getHTML(0, 0));

    // Create a cell renderer
    CellRenderer cellRenderer = new CellRenderer() {
      public void renderCell(FixedWidthGrid grid, int row, int column,
          Object data) {
        grid.setHTML(row, column, "ROW: " + row);
      }
    };

    // Check text with new renderer
    table.setCellRenderer(cellRenderer);
    assertEquals(cellRenderer, table.getCellRenderer());
    assertEquals("0:0", grid.getHTML(0, 0));
    table.renderCell(0, 0, "0:0");
    assertEquals("ROW: 0", grid.getHTML(0, 0));

    // Remove the new renderer
    table.setCellRenderer(null);
    assertEquals(null, table.getCellRenderer());
    assertEquals("ROW: 0", grid.getHTML(0, 0));
    table.renderCell(0, 0, "0:0");
    assertEquals("0:0", grid.getHTML(0, 0));
  }

  /**
   * Test column sorting.
   */
  public void testColumnSorting() {
    // Initialize the grid
    PagingScrollTable<Object> scrollTable = getPagingScrollTable();
    FixedWidthGrid dataTable = scrollTable.getDataTable();
    scrollTable.setPageSize(5);
    scrollTable.gotoPage(2, true);

    // Sort the column without specifying a ColumnSorter
    TestRowPaginingListener listener1 = new TestRowPaginingListener();
    scrollTable.addRowPagingListener(listener1);
    assertEquals(-1, listener1.getCurPage());
    dataTable.sortColumn(3, true);
    assertEquals(2, listener1.getCurPage());

    // Sort the column with a column sorter
    TestRowPaginingListener listener2 = new TestRowPaginingListener();
    scrollTable.addRowPagingListener(listener2);
    dataTable.setColumnSorter(new ColumnSorter() {
      @Override
      public void onSortColumn(SortableGrid grid, ColumnSortList sortList,
          ColumnSorterCallback callback) {
      }
    });
    assertEquals(-1, listener2.getCurPage());
    dataTable.sortColumn(3, true);
    assertEquals(-1, listener2.getCurPage());
  }

  /**
   * Test page navigation.
   */
  public void testPaging() {
    // Initialize the grid
    PagingScrollTable<Object> table = getPagingScrollTable();
    TableModel<Object> tableModel = table.getTableModel();

    // Goto first or last page
    table.setPageSize(5);
    table.gotoPage(2, false);
    assertEquals(2, table.getCurrentPage());
    table.gotoFirstPage();
    assertEquals(0, table.getCurrentPage());
    table.gotoFirstPage();
    assertEquals(0, table.getCurrentPage());
    table.gotoLastPage();
    assertEquals(4, table.getCurrentPage());
    table.gotoLastPage();
    assertEquals(4, table.getCurrentPage());

    // Goto next or previous page
    table.gotoPage(2, false);
    assertEquals(2, table.getCurrentPage());
    table.gotoNextPage();
    assertEquals(3, table.getCurrentPage());
    table.gotoNextPage();
    assertEquals(4, table.getCurrentPage());
    table.gotoPreviousPage();
    assertEquals(3, table.getCurrentPage());
    table.gotoPreviousPage();
    assertEquals(2, table.getCurrentPage());

    // Goto specific page
    table.gotoPage(3, false);
    assertEquals(3, table.getCurrentPage());
    table.gotoPage(0, false);
    assertEquals(0, table.getCurrentPage());
    table.gotoPage(0, true);
    assertEquals(0, table.getCurrentPage());
    table.gotoPage(4, false);
    assertEquals(4, table.getCurrentPage());
    table.gotoPage(4, true);
    assertEquals(4, table.getCurrentPage());

    // Check page bounds
    table.gotoPage(-1, false);
    assertEquals(0, table.getCurrentPage());
    table.gotoPreviousPage();
    assertEquals(0, table.getCurrentPage());
    table.gotoPage(5, false);
    assertEquals(4, table.getCurrentPage());
    table.gotoNextPage();
    assertEquals(4, table.getCurrentPage());

    // Unknown number of pages
    tableModel.setRowCount(TableModel.UNKNOWN_ROW_COUNT);
    table.gotoPage(20, false);
    assertEquals(20, table.getCurrentPage());
    table.gotoPage(300, false);
    assertEquals(300, table.getCurrentPage());
    table.gotoLastPage();
    assertEquals(300, table.getCurrentPage());
    table.gotoFirstPage();
    assertEquals(0, table.getCurrentPage());
  }

  /**
   * Test the {@link RowPagingListener} class.
   */
  public void testRowPagingListener() {
    // Initialize the grid
    PagingScrollTable<Object> table = getPagingScrollTable();
    TableModel<Object> tableModel = table.getTableModel();

    // Create some listeners
    TestRowPaginingListener listener1 = new TestRowPaginingListener();
    TestRowPaginingListener listener2 = new TestRowPaginingListener();
    TestRowPaginingListener listener3 = new TestRowPaginingListener();

    // Add and remove listeners
    table.addRowPagingListener(listener1);
    table.addRowPagingListener(listener2);
    table.addRowPagingListener(listener3);
    table.removeRowPagingListener(listener2);

    // Change num pages
    tableModel.setRowCount(1000);
    table.setPageSize(10);
    assertEquals(100, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(100, listener3.getNumPages());
    tableModel.setRowCount(500);
    assertEquals(50, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(50, listener3.getNumPages());
    table.setPageSize(5);
    assertEquals(100, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(100, listener3.getNumPages());
    tableModel.setRowCount(-1);
    assertEquals(-1, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(-1, listener3.getNumPages());
    table.setPageSize(10);
    assertEquals(-1, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(-1, listener3.getNumPages());
    tableModel.setRowCount(1);
    assertEquals(1, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(1, listener3.getNumPages());
    table.setPageSize(0);
    assertEquals(1, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(1, listener3.getNumPages());

    // Goto page
    tableModel.setRowCount(100);
    table.setPageSize(10);
    table.gotoPage(0, true);
    assertEquals(0, listener1.getCurPage());
    assertEquals(-1, listener2.getCurPage());
    assertEquals(0, listener3.getCurPage());
    assertTrue(listener1.isPageLoaded());
    assertFalse(listener2.isPageLoaded());
    assertTrue(listener3.isPageLoaded());
    table.gotoPage(4, true);
    assertEquals(4, listener1.getCurPage());
    assertEquals(-1, listener2.getCurPage());
    assertEquals(4, listener3.getCurPage());
  }

  /**
   * Test values associated with rows.
   */
  public void testRowValues() {
    // Initialize the grid
    PagingScrollTable<Object> table = getPagingScrollTable();
    table.getDataTable().resize(10, 10);

    // Test manually setting values
    String value1 = "value1";
    String value2 = "value2";
    String value3 = "value3";
    assertNull(table.getRowValue(0));
    assertNull(table.getRowValue(10));
    table.setRowValue(4, value1);
    assertNull(table.getRowValue(3));
    assertEquals(value1, table.getRowValue(4));
    assertNull(table.getRowValue(5));
    table.setRowValue(4, value2);
    assertNull(table.getRowValue(3));
    assertEquals(value2, table.getRowValue(4));
    assertNull(table.getRowValue(5));
    table.setRowValue(5, value3);
    assertNull(table.getRowValue(3));
    assertEquals(value2, table.getRowValue(4));
    assertEquals(value3, table.getRowValue(5));
    assertNull(table.getRowValue(6));
  }

  /**
   * Get the scroll table.
   * 
   * @return the scroll table
   */
  protected PagingScrollTable<Object> getPagingScrollTable() {
    FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
    FixedWidthGrid dataTable = new FixedWidthGrid();
    FixedWidthFlexTable footerTable = new FixedWidthFlexTable();
    return getPagingScrollTable(headerTable, dataTable, footerTable);
  }

  /**
   * Get the scroll table.
   * 
   * @return the scroll table
   */
  protected PagingScrollTable<Object> getPagingScrollTable(
      FixedWidthFlexTable headerTable, FixedWidthGrid dataTable,
      FixedWidthFlexTable footerTable) {
    TableModel<Object> tableModel = createTableModel();
    PagingScrollTable<Object> scrollTable = new PagingScrollTable<Object>(
        tableModel, dataTable, headerTable);
    scrollTable.setFooterTable(footerTable);
    return scrollTable;
  }

  /**
   * @see ScrollTableTest#getScrollTable(FixedWidthFlexTable, FixedWidthGrid,
   *      FixedWidthFlexTable)
   */
  @Override
  protected ScrollTable getScrollTable(FixedWidthFlexTable headerTable,
      FixedWidthGrid dataTable, FixedWidthFlexTable footerTable) {
    return getPagingScrollTable(headerTable, dataTable, footerTable);
  }

  /**
   * Create a table model with 25 rows.
   */
  private <R> TableModel<R> createTableModel() {
    // Create the row data
    List<List<Object>> rowList = new ArrayList<List<Object>>();
    for (int row = 0; row < 25; row++) {
      ArrayList<Object> columnList = new ArrayList<Object>();
      rowList.add(columnList);
      for (int column = 0; column < 4; column++) {
        columnList.add(row + ":" + column);
      }
    }
    return new ListTableModel<R>(rowList);
  }
}
