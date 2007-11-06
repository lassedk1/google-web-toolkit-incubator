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

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.widgetideas.table.client.PagingGrid.CellRenderer;
import com.google.gwt.widgetideas.table.client.PagingGrid.TableRenderer;
import com.google.gwt.widgetideas.table.client.SortableFixedWidthGrid.ColumnSorter;
import com.google.gwt.widgetideas.table.client.SortableFixedWidthGrid.ColumnSorterCallback;
import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortList;

import java.util.Iterator;

/**
 * Tests methods used for all {@link PagingGrid} class.
 */
public class PagingGridTest extends GWTTestCase {
  /**
   * A {@link TableDataRequestListener} used for testing.
   */
  private class TestDataRequestListener implements TableDataRequestListener {
    /**
     * The request row.
     */
    private int firstRow = -1;

    /**
     * The requested row count.
     */
    private int rowCount = -1;

    /**
     * @return the firstRow
     */
    public int getFirstRow() {
      return firstRow;
    }

    /**
     * @return the rowCount
     */
    public int getRowCount() {
      return rowCount;
    }

    public void onRequestData(int firstRow, int rowCount,
        ColumnSortList sortList) {
      this.firstRow = firstRow;
      this.rowCount = rowCount;
    }
  }

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

    public void onPageLoaded() {
      isPageLoaded = true;
    }
  }

  /**
   * The grid to test.
   */
  private PagingGrid grid = null;

  public String getModuleName() {
    return "com.google.gwt.widgetideas.WidgetIdeas";
  }

  /**
   * Get the grid.
   * 
   * @return the grid
   */
  public PagingGrid getPagingGrid() {
    if (grid == null) {
      // Create a simple table model
      TableModel tableModel = new ClientTableModel() {
        public Object getCell(int rowNum, int colNum) {
          if (colNum < 10) {
            return rowNum + ":" + colNum;
          }
          return null;
        }
      };

      // Create a grid
      grid = new PagingGrid(tableModel);
    }
    return grid;
  }

  /**
   * Test accessors.
   */
  public void testAccessor() {
    // Initialize the grid
    PagingGrid testGrid = getPagingGrid();

    // numRows
    testGrid.setNumRows(1000);
    assertEquals(1000, testGrid.getNumRows());
    testGrid.setNumRows(-1);
    assertEquals(-1, testGrid.getNumRows());
    testGrid.setNumRows(-2);
    assertEquals(-1, testGrid.getNumRows());

    // pageSize
    testGrid.setPageSize(5);
    assertEquals(5, testGrid.getPageSize());
    testGrid.setPageSize(0);
    assertEquals(0, testGrid.getPageSize());
    testGrid.setPageSize(-1);
    assertEquals(0, testGrid.getPageSize());

    // numPages
    testGrid.setNumRows(1000);
    testGrid.setPageSize(5);
    assertEquals(200, testGrid.getNumPages());
    testGrid.setNumRows(500);
    assertEquals(100, testGrid.getNumPages());
    testGrid.setPageSize(10);
    assertEquals(50, testGrid.getNumPages());

    // Unknown number of pages
    testGrid.setPageSize(5);
    testGrid.setNumRows(-1);
    assertEquals(-1, testGrid.getNumPages());
    testGrid.setPageSize(0);
    testGrid.setNumRows(1000);
    assertEquals(1, testGrid.getNumPages());
  }

  /**
   * Test the {@link TableDataRequestListener} class.
   */
  public void testDataRequestListener() {
    // Initialize the grid
    PagingGrid testGrid = getPagingGrid();

    // Create some listeners
    TestDataRequestListener listener1 = new TestDataRequestListener();
    TestDataRequestListener listener2 = new TestDataRequestListener();
    TestDataRequestListener listener3 = new TestDataRequestListener();

    // Add and remove listeners
    testGrid.addTableDataRequestListener(listener1);
    testGrid.addTableDataRequestListener(listener2);
    testGrid.addTableDataRequestListener(listener3);
    testGrid.removeTableDataRequestListener(listener2);

    // Goto a page
    testGrid.setPageSize(10);
    testGrid.setNumRows(100);
    testGrid.gotoPage(3, true);
    testGrid.renderContents();

    // Test listeners
    assertEquals(30, listener1.getFirstRow());
    assertEquals(-1, listener2.getFirstRow());
    assertEquals(30, listener3.getFirstRow());

    assertEquals(10, listener1.getRowCount());
    assertEquals(-1, listener2.getFirstRow());
    assertEquals(10, listener3.getRowCount());
  }

  /**
   * Test accessors.
   */
  public void testCellRender() {
    // Initialize the grid
    PagingGrid testGrid = getPagingGrid();

    // Get data without renderer
    testGrid.setNumRows(1000);
    testGrid.setPageSize(5);
    testGrid.renderCell(0, 0, "0:0");
    assertNull(testGrid.getCellRenderer());
    assertEquals("0:0", testGrid.getHTML(0, 0));

    // Create a cell renderer
    CellRenderer cellRenderer = new CellRenderer() {
      public void renderCell(PagingGrid grid, int row, int column, Object data) {
        grid.setHTML(row, column, "ROW: " + row);
      }
    };

    // Check text with new renderer
    testGrid.setCellRenderer(cellRenderer);
    assertEquals(cellRenderer, testGrid.getCellRenderer());
    assertEquals("0:0", testGrid.getHTML(0, 0));
    testGrid.renderCell(0, 0, "0:0");
    assertEquals("ROW: 0", testGrid.getHTML(0, 0));

    // Remove the new renderer
    testGrid.setCellRenderer(null);
    assertEquals(null, testGrid.getCellRenderer());
    assertEquals("ROW: 0", testGrid.getHTML(0, 0));
    testGrid.renderCell(0, 0, "0:0");
    assertEquals("0:0", testGrid.getHTML(0, 0));
  }

  /**
   * Test column sorting.
   */
  public void testColumnSorting() {
    // Initialize the grid
    PagingGrid testGrid = getPagingGrid();
    testGrid.setNumRows(100);
    testGrid.setPageSize(10);
    testGrid.gotoPage(5, true);
    testGrid.renderContents();

    // Sort the column without specifying a ColumnSorter
    TestRowPaginingListener listener1 = new TestRowPaginingListener();
    testGrid.addRowPagingListener(listener1);
    assertEquals(-1, listener1.getCurPage());
    testGrid.sortColumn(3, true);
    assertEquals(5, listener1.getCurPage());

    // Sort the column with a column sorter
    TestRowPaginingListener listener2 = new TestRowPaginingListener();
    testGrid.addRowPagingListener(listener2);
    testGrid.setColumnSorter(new ColumnSorter() {
      public void onSortColumn(SortableFixedWidthGrid grid,
          ColumnSortList sortList, ColumnSorterCallback callback) {
      }
    });
    assertEquals(-1, listener2.getCurPage());
    testGrid.sortColumn(3, true);
    assertEquals(-1, listener2.getCurPage());

    // Remove the sorter and sort again
    testGrid.setColumnSorter(null);
    assertEquals(-1, listener2.getCurPage());
    testGrid.sortColumn(3, true);
    assertEquals(5, listener2.getCurPage());
  }

  /**
   * Test page navigation.
   */
  public void testPaging() {
    // Initialize the grid
    PagingGrid testGrid = getPagingGrid();

    // Goto first or last page
    testGrid.setNumRows(1000);
    testGrid.setPageSize(5);
    testGrid.gotoPage(10, false);
    assertEquals(10, testGrid.getCurrentPage());
    testGrid.gotoFirstPage();
    assertEquals(0, testGrid.getCurrentPage());
    testGrid.gotoFirstPage();
    assertEquals(0, testGrid.getCurrentPage());
    testGrid.gotoLastPage();
    assertEquals(199, testGrid.getCurrentPage());
    testGrid.gotoLastPage();
    assertEquals(199, testGrid.getCurrentPage());

    // Goto next or previous page
    testGrid.gotoPage(50, false);
    assertEquals(50, testGrid.getCurrentPage());
    testGrid.gotoNextPage();
    assertEquals(51, testGrid.getCurrentPage());
    testGrid.gotoNextPage();
    assertEquals(52, testGrid.getCurrentPage());
    testGrid.gotoPreviousPage();
    assertEquals(51, testGrid.getCurrentPage());
    testGrid.gotoPreviousPage();
    assertEquals(50, testGrid.getCurrentPage());

    // Goto specific page
    testGrid.gotoPage(25, false);
    assertEquals(25, testGrid.getCurrentPage());
    testGrid.gotoPage(0, false);
    assertEquals(0, testGrid.getCurrentPage());
    testGrid.gotoPage(0, true);
    assertEquals(0, testGrid.getCurrentPage());
    testGrid.gotoPage(199, false);
    assertEquals(199, testGrid.getCurrentPage());
    testGrid.gotoPage(199, true);
    assertEquals(199, testGrid.getCurrentPage());

    // Check page bounds
    testGrid.gotoPage(-1, false);
    assertEquals(0, testGrid.getCurrentPage());
    testGrid.gotoPreviousPage();
    assertEquals(0, testGrid.getCurrentPage());
    testGrid.gotoPage(200, false);
    assertEquals(199, testGrid.getCurrentPage());
    testGrid.gotoNextPage();
    assertEquals(199, testGrid.getCurrentPage());

    // Unknown number of pages
    testGrid.setNumRows(-1);
    testGrid.gotoPage(20, false);
    assertEquals(20, testGrid.getCurrentPage());
    testGrid.gotoPage(300, false);
    assertEquals(300, testGrid.getCurrentPage());
    testGrid.gotoLastPage();
    assertEquals(300, testGrid.getCurrentPage());
    testGrid.gotoFirstPage();
    assertEquals(0, testGrid.getCurrentPage());
  }

  /**
   * Test the {@link RowPagingListener} class.
   */
  public void testRowPagingListener() {
    // Initialize the grid
    PagingGrid testGrid = getPagingGrid();

    // Create some listeners
    TestRowPaginingListener listener1 = new TestRowPaginingListener();
    TestRowPaginingListener listener2 = new TestRowPaginingListener();
    TestRowPaginingListener listener3 = new TestRowPaginingListener();

    // Add and remove listeners
    testGrid.addRowPagingListener(listener1);
    testGrid.addRowPagingListener(listener2);
    testGrid.addRowPagingListener(listener3);
    testGrid.removeRowPagingListener(listener2);

    // Change num pages
    testGrid.setNumRows(1000);
    testGrid.setPageSize(10);
    assertEquals(100, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(100, listener3.getNumPages());
    testGrid.setNumRows(500);
    assertEquals(50, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(50, listener3.getNumPages());
    testGrid.setPageSize(5);
    assertEquals(100, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(100, listener3.getNumPages());
    testGrid.setNumRows(-1);
    assertEquals(-1, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(-1, listener3.getNumPages());
    testGrid.setPageSize(10);
    assertEquals(-1, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(-1, listener3.getNumPages());
    testGrid.setNumRows(1);
    assertEquals(1, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(1, listener3.getNumPages());
    testGrid.setPageSize(0);
    assertEquals(1, listener1.getNumPages());
    assertEquals(-1, listener2.getNumPages());
    assertEquals(1, listener3.getNumPages());

    // Goto page
    testGrid.setNumRows(100);
    testGrid.setPageSize(10);
    testGrid.gotoPage(0, true);
    testGrid.renderContents();
    assertEquals(0, listener1.getCurPage());
    assertEquals(-1, listener2.getCurPage());
    assertEquals(0, listener3.getCurPage());
    assertTrue(listener1.isPageLoaded());
    assertFalse(listener2.isPageLoaded());
    assertTrue(listener3.isPageLoaded());
    testGrid.gotoPage(4, true);
    assertEquals(4, listener1.getCurPage());
    assertEquals(-1, listener2.getCurPage());
    assertEquals(4, listener3.getCurPage());
  }

  /**
   * Test values associated with rows.
   */
  public void testRowValues() {
    // Initialize the grid
    PagingGrid testGrid = getPagingGrid();

    // Test manually setting values
    String value1 = "value1";
    String value2 = "value2";
    String value3 = "value3";
    assertNull(testGrid.getRowValue(0));
    assertNull(testGrid.getRowValue(10));
    testGrid.setRowValue(4, value1);
    assertNull(testGrid.getRowValue(3));
    assertEquals(value1, testGrid.getRowValue(4));
    assertNull(testGrid.getRowValue(5));
    testGrid.setRowValue(4, value2);
    assertNull(testGrid.getRowValue(3));
    assertEquals(value2, testGrid.getRowValue(4));
    assertNull(testGrid.getRowValue(5));
    testGrid.setRowValue(5, value3);
    assertNull(testGrid.getRowValue(3));
    assertEquals(value2, testGrid.getRowValue(4));
    assertEquals(value3, testGrid.getRowValue(5));
    assertNull(testGrid.getRowValue(6));
  }

  /**
   * Test using a table renderer.
   */
  public void testTableRenderer() {
    // Initialize the grid
    PagingGrid testGrid = getPagingGrid();

    // Create a table renderer
    TableRenderer tableRenderer = new TableRenderer() {
      public void renderTable(PagingGrid grid, Iterator rows,
          RendererCallback callback) {
        int firstRow = grid.getPageSize() * grid.getCurrentPage();
        int row = 0;
        while (rows.hasNext()) {
          int col = 0;
          Iterator cells = (Iterator) rows.next();
          while (cells.hasNext()) {
            cells.next();
            grid.setHTML(row, col, (firstRow + row) + "-" + col);
            col++;
          }
          row++;
        }
        callback.onRendered();
      }
    };

    // Setup the grid page
    testGrid.setNumRows(100);
    testGrid.setPageSize(10);
    testGrid.gotoPage(3, true);
    testGrid.renderContents();

    // Check that the content is based on cell renderer
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        assertEquals((row + 30) + ":" + col, testGrid.getHTML(row, col));
      }
    }

    // Check that content is based on table renderer
    TestRowPaginingListener listener = new TestRowPaginingListener();
    testGrid.setTableRenderer(tableRenderer);
    testGrid.addRowPagingListener(listener);
    assertFalse(listener.isPageLoaded());
    testGrid.renderContents();
    for (int row = 0; row < 10; row++) {
      for (int col = 0; col < 10; col++) {
        assertEquals((row + 30) + "-" + col, testGrid.getHTML(row, col));
      }
    }
    assertTrue(listener.isPageLoaded());
  }
}
