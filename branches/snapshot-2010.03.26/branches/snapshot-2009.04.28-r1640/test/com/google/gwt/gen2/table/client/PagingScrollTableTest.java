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

import com.google.gwt.gen2.table.client.AbstractColumnDefinitionTest.CustomColumnDefinition;
import com.google.gwt.gen2.table.client.SortableGrid.ColumnSorter;
import com.google.gwt.gen2.table.client.SortableGrid.ColumnSorterCallback;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortList;
import com.google.gwt.gen2.table.client.TableModelHelper.Request;
import com.google.gwt.gen2.table.event.client.PageChangeEvent;
import com.google.gwt.gen2.table.event.client.PageChangeHandler;
import com.google.gwt.gen2.table.event.client.PageCountChangeEvent;
import com.google.gwt.gen2.table.event.client.PageCountChangeHandler;
import com.google.gwt.gen2.table.event.client.PageLoadEvent;
import com.google.gwt.gen2.table.event.client.PageLoadHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests methods in the {@link PagingScrollTable} class.
 */
public class PagingScrollTableTest extends AbstractScrollTableTest {
  /**
   * A custom handler used for testing.
   */
  protected static class TestPageChangeHandler implements PageChangeHandler {
    private PageChangeEvent lastEvent = null;

    public void assertNewPage(int expected) {
      assertNotNull(lastEvent);
      assertEquals(expected, lastEvent.getNewPage());
    }

    public void assertOldPage(int expected) {
      assertNotNull(lastEvent);
      assertEquals(expected, lastEvent.getOldPage());
    }

    public void onPageChange(PageChangeEvent event) {
      lastEvent = event;
    }
  }

  /**
   * A custom handler used for testing.
   */
  protected static class TestPageCountChangeHandler implements
      PageCountChangeHandler {
    private PageCountChangeEvent lastEvent = null;

    public void assertNewPageCount(int expected) {
      assertNotNull(lastEvent);
      assertEquals(expected, lastEvent.getNewPageCount());
    }

    public void assertNotFired() {
      assertNull(lastEvent);
    }

    public void assertOldPageCount(int expected) {
      assertNotNull(lastEvent);
      assertEquals(expected, lastEvent.getOldPageCount());
    }

    public void onPageCountChange(PageCountChangeEvent event) {
      lastEvent = event;
    }

    public void reset() {
      lastEvent = null;
    }
  }

  /**
   * A custom handler used for testing.
   */
  protected static class TestPageLoadHandler implements PageLoadHandler {
    private PageLoadEvent lastEvent = null;

    public void assertNotFired() {
      assertNull(lastEvent);
    }

    public void assertPage(int expected) {
      assertNotNull(lastEvent);
      assertEquals(expected, lastEvent.getPage());
    }

    public void onPageLoad(PageLoadEvent event) {
      lastEvent = event;
    }
  }

  /**
   * A custom {@link ListTableModel} used for testing.
   */
  private static class TestListTableModel extends ListTableModel {
    /**
     * The last {@link Request} that was received.
     */
    private Request lastRequest = null;

    public TestListTableModel(List<List<Object>> rows) {
      super(rows);
    }

    public Request getLastRequest() {
      return lastRequest;
    }

    @Override
    public void requestRows(Request request, Callback<List<Object>> callback) {
      lastRequest = request;
      super.requestRows(request, callback);
    }
  }

  /**
   * Test absolute row index operations.
   */
  @SuppressWarnings("deprecation")
  public void testAbsoluteRowIndex() {
    // Initialize the grid
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    table.setPageSize(5);

    // First page with plenty of rows
    {
      table.setPageSize(5);
      table.gotoPage(0, true);
      assertEquals(0, table.getAbsoluteFirstRowIndex());
      assertEquals(4, table.getAbsoluteLastRowIndex());
      assertEquals(0, table.getFirstRow());
      assertEquals(4, table.getLastRow());
    }

    // Second page with plenty of rows
    {
      table.gotoPage(1, true);
      assertEquals(5, table.getAbsoluteFirstRowIndex());
      assertEquals(9, table.getAbsoluteLastRowIndex());
      assertEquals(5, table.getFirstRow());
      assertEquals(9, table.getLastRow());
    }

    // Second page with limited rows
    {
      table.setPageSize(15);
      assertEquals(15, table.getAbsoluteFirstRowIndex());
      assertEquals(24, table.getAbsoluteLastRowIndex());
      assertEquals(15, table.getFirstRow());
      assertEquals(24, table.getLastRow());
    }

    // First page with no page size
    {
      table.setPageSize(0);
      assertEquals(0, table.getAbsoluteFirstRowIndex());
      assertEquals(24, table.getAbsoluteLastRowIndex());
      assertEquals(0, table.getFirstRow());
      assertEquals(24, table.getLastRow());
    }
  }

  /**
   * Test absolute row operations.
   */
  public void testAbsoluteRows() {
    // Insert absolute row
    {
      PagingScrollTable<List<Object>> table = getPagingScrollTable();
      FixedWidthGrid grid = table.getDataTable();
      table.setPageSize(5);

      table.gotoPage(1, true);
      assertEquals("cell 2:1", grid.getText(2, 1));
      table.insertAbsoluteRow(10);
      assertEquals("cell 2:1", grid.getText(2, 1));
      table.insertAbsoluteRow(1);
      assertEquals("cell 1:1", grid.getText(2, 1));
    }

    // Remove absolute row
    {
      PagingScrollTable<List<Object>> table = getPagingScrollTable();
      FixedWidthGrid grid = table.getDataTable();
      table.setPageSize(5);

      table.gotoPage(1, true);
      assertEquals("cell 2:1", grid.getText(2, 1));
      table.removeAbsoluteRow(10);
      assertEquals("cell 2:1", grid.getText(2, 1));
      table.removeAbsoluteRow(1);
      assertEquals("cell 2:1", grid.getText(2, 1));
      table.removeAbsoluteRow(6);
      assertEquals("cell 3:1", grid.getText(2, 1));
    }

    // Remove very last absolute row
    {
      PagingScrollTable<List<Object>> table = getPagingScrollTable();
      FixedWidthGrid grid = table.getDataTable();
      table.setPageSize(7);

      table.gotoPage(3, true);
      assertEquals(4, grid.getRowCount());
      table.removeAbsoluteRow(24);
      assertEquals(3, grid.getRowCount());
      table.removeAbsoluteRow(23);
      assertEquals(2, grid.getRowCount());
      table.removeAbsoluteRow(22);
      assertEquals(1, grid.getRowCount());
      table.removeAbsoluteRow(21);
      assertEquals(0, grid.getRowCount());
    }
  }

  /**
   * Test column sorting.
   */
  public void testColumnSorting() {
    // Initialize the grid
    PagingScrollTable<List<Object>> scrollTable = getPagingScrollTable();
    FixedWidthGrid dataTable = scrollTable.getDataTable();
    scrollTable.setPageSize(5);
    scrollTable.gotoPage(2, true);

    // Sort the column without specifying a ColumnSorter
    {
      TestPageLoadHandler plh = new TestPageLoadHandler();
      scrollTable.addPageLoadHandler(plh);
      plh.assertNotFired();
      dataTable.sortColumn(3, true);
      plh.assertPage(2);
    }

    // Sort the column with a column sorter
    {
      TestPageLoadHandler plh = new TestPageLoadHandler();
      scrollTable.addPageLoadHandler(plh);
      dataTable.setColumnSorter(new ColumnSorter() {
        @Override
        public void onSortColumn(SortableGrid grid, ColumnSortList sortList,
            ColumnSorterCallback callback) {
        }
      });
      plh.assertNotFired();
      dataTable.sortColumn(3, true);
      plh.assertNotFired();
    }
  }

  /**
   * Create a {@link PagingScrollTable} with an empty table definition.
   */
  public void testEmptyTableDefinition() {
    // Without bulk renderer
    {
      TableDefinition<List<Object>> tableDef = new DefaultTableDefinition<List<Object>>();
      TableModel<List<Object>> tableModel = createTableModel();
      PagingScrollTable<List<Object>> table = new PagingScrollTable<List<Object>>(
          tableModel, tableDef);
      table.gotoFirstPage();
    }

    // With bulk renderer
    {
      TableDefinition<List<Object>> tableDef = new DefaultTableDefinition<List<Object>>();
      TableModel<List<Object>> tableModel = createTableModel();
      PagingScrollTable<List<Object>> table = new PagingScrollTable<List<Object>>(
          tableModel, tableDef);
      FixedWidthGridBulkRenderer<List<Object>> renderer = new FixedWidthGridBulkRenderer<List<Object>>(
          table.getDataTable(), table);
      table.setBulkRenderer(renderer);
      table.gotoFirstPage();
    }
  }

  /**
   * Generate a two row, column spanning footer.
   */
  public void testFooterGenerationColSpan() {
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    FixedWidthFlexTable footerTable = table.getFooterTable();
    TableDefinition<List<Object>> tableDef = table.getTableDefinition();

    // Setup the footers
    List<ColumnDefinition<List<Object>, ?>> colDefs = tableDef.getVisibleColumnDefinitions();
    int numColumns = colDefs.size();
    for (int i = 0; i < numColumns; i++) {
      AbstractColumnDefinition<List<Object>, ?> colDef = (AbstractColumnDefinition<List<Object>, ?>) colDefs.get(i);
      colDef.setFooter(0, "Group");
      colDef.setFooter(1, "Column");
    }

    // Test the footer table
    table.setFooterGenerated(true);
    assertEquals(numColumns, footerTable.getColumnCount());
    assertEquals(1, footerTable.getCellCount(0));
    assertEquals(1, footerTable.getCellCount(1));
    assertEquals(2, footerTable.getRowCount());
    assertEquals("Group", footerTable.getHTML(0, 0));
    assertEquals("Column", footerTable.getHTML(1, 0));
  }

  /**
   * Generate a two row, row spanning footer.
   */
  public void testFooterGenerationRowSpan() {
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    FixedWidthFlexTable footerTable = table.getFooterTable();
    TableDefinition<List<Object>> tableDef = table.getTableDefinition();

    // Setup the footers
    List<ColumnDefinition<List<Object>, ?>> colDefs = tableDef.getVisibleColumnDefinitions();
    int numColumns = colDefs.size();
    for (int i = 0; i < numColumns; i++) {
      AbstractColumnDefinition<List<Object>, ?> colDef = (AbstractColumnDefinition<List<Object>, ?>) colDefs.get(i);
      colDef.setFooter(0, "Column " + i);
      colDef.setFooter(1, "Column " + i);
    }

    // Test the footer table
    table.setFooterGenerated(true);
    assertEquals(numColumns, footerTable.getColumnCount());
    assertEquals(numColumns, footerTable.getCellCount(0));
    assertEquals(1, footerTable.getRowCount());
    for (int i = 0; i < numColumns; i++) {
      assertEquals("Column " + i, footerTable.getHTML(0, i));
    }
  }

  /**
   * Generate a two row, non spanning footer.
   */
  public void testFooterGenerationSimple() {
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    FixedWidthFlexTable footerTable = table.getFooterTable();
    TableDefinition<List<Object>> tableDef = table.getTableDefinition();

    // Setup the footers
    List<ColumnDefinition<List<Object>, ?>> colDefs = tableDef.getVisibleColumnDefinitions();
    int numColumns = colDefs.size();
    for (int i = 0; i < numColumns; i++) {
      AbstractColumnDefinition<List<Object>, ?> colDef = (AbstractColumnDefinition<List<Object>, ?>) colDefs.get(i);
      colDef.setFooter(0, "Group " + i);
      colDef.setFooter(1, "Column " + i);
    }

    // Test the footer table
    table.setFooterGenerated(true);
    assertEquals(numColumns, footerTable.getColumnCount());
    assertEquals(numColumns, footerTable.getCellCount(0));
    assertEquals(numColumns, footerTable.getCellCount(1));
    assertEquals(2, footerTable.getRowCount());
    for (int i = 0; i < numColumns; i++) {
      assertEquals("Group " + i, footerTable.getHTML(0, i));
      assertEquals("Column " + i, footerTable.getHTML(1, i));
    }
  }

  /**
   * Generate a two row, column spanning footer.
   */
  public void testFooterGenerationUneven() {
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    FixedWidthFlexTable footerTable = table.getFooterTable();
    TableDefinition<List<Object>> tableDef = table.getTableDefinition();

    // Setup the footers
    List<ColumnDefinition<List<Object>, ?>> colDefs = tableDef.getVisibleColumnDefinitions();
    int numColumns = colDefs.size();
    for (int i = 0; i < numColumns; i++) {
      AbstractColumnDefinition<List<Object>, ?> colDef = (AbstractColumnDefinition<List<Object>, ?>) colDefs.get(i);
      colDef.setFooter(0, "Topic " + i);
      if (i % 2 == 0) {
        colDef.setFooter(1, "Group " + i);
        colDef.setFooter(2, "Column " + i);
      }
    }

    // Test the footer table
    table.setFooterGenerated(true);
    assertEquals(numColumns, footerTable.getColumnCount());
    assertEquals(numColumns, footerTable.getCellCount(0));
    assertEquals(numColumns, footerTable.getCellCount(1));
    assertEquals(numColumns / 2, footerTable.getCellCount(2));
    assertEquals(3, footerTable.getRowCount());
    for (int i = 0; i < numColumns; i++) {
      if (i % 2 == 0) {
        assertEquals("Group " + i, footerTable.getHTML(1, i));
      } else {
        assertEquals("&nbsp;", footerTable.getHTML(1, i));
      }
      assertEquals("Topic " + i, footerTable.getHTML(0, i));
    }
  }

  /**
   * Generate a two row, column spanning header.
   */
  public void testHeaderGenerationColSpan() {
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    FixedWidthFlexTable headerTable = table.getHeaderTable();
    TableDefinition<List<Object>> tableDef = table.getTableDefinition();

    // Setup the headers
    List<ColumnDefinition<List<Object>, ?>> colDefs = tableDef.getVisibleColumnDefinitions();
    int numColumns = colDefs.size();
    for (int i = 0; i < numColumns; i++) {
      AbstractColumnDefinition<List<Object>, ?> colDef = (AbstractColumnDefinition<List<Object>, ?>) colDefs.get(i);
      colDef.setHeader(0, "Column");
      colDef.setHeader(1, "Group");
    }

    // Test the header table
    table.setHeaderGenerated(true);
    assertEquals(numColumns, headerTable.getColumnCount());
    assertEquals(1, headerTable.getCellCount(0));
    assertEquals(1, headerTable.getCellCount(1));
    assertEquals(2, headerTable.getRowCount());
    assertEquals("Group", headerTable.getHTML(0, 0));
    assertEquals("Column", headerTable.getHTML(1, 0));
  }

  /**
   * Generate a two row, row spanning header.
   */
  public void testHeaderGenerationRowSpan() {
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    FixedWidthFlexTable headerTable = table.getHeaderTable();
    TableDefinition<List<Object>> tableDef = table.getTableDefinition();

    // Setup the headers
    List<ColumnDefinition<List<Object>, ?>> colDefs = tableDef.getVisibleColumnDefinitions();
    int numColumns = colDefs.size();
    for (int i = 0; i < numColumns; i++) {
      AbstractColumnDefinition<List<Object>, ?> colDef = (AbstractColumnDefinition<List<Object>, ?>) colDefs.get(i);
      colDef.setHeader(0, "Column " + i);
      colDef.setHeader(1, "Column " + i);
    }

    // Test the header table
    table.setHeaderGenerated(true);
    assertEquals(numColumns, headerTable.getColumnCount());
    assertEquals(numColumns, headerTable.getCellCount(0));
    assertEquals(1, headerTable.getRowCount());
    for (int i = 0; i < numColumns; i++) {
      assertEquals("Column " + i, headerTable.getHTML(0, i));
    }
  }

  /**
   * Generate a two row, non spanning header.
   */
  public void testHeaderGenerationSimple() {
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    FixedWidthFlexTable headerTable = table.getHeaderTable();
    TableDefinition<List<Object>> tableDef = table.getTableDefinition();

    // Setup the headers
    List<ColumnDefinition<List<Object>, ?>> colDefs = tableDef.getVisibleColumnDefinitions();
    int numColumns = colDefs.size();
    for (int i = 0; i < numColumns; i++) {
      AbstractColumnDefinition<List<Object>, ?> colDef = (AbstractColumnDefinition<List<Object>, ?>) colDefs.get(i);
      colDef.setHeader(0, "Column " + i);
      colDef.setHeader(1, "Group " + i);
    }

    // Test the header table
    table.setHeaderGenerated(true);
    assertEquals(numColumns, headerTable.getColumnCount());
    assertEquals(numColumns, headerTable.getCellCount(0));
    assertEquals(numColumns, headerTable.getCellCount(1));
    assertEquals(2, headerTable.getRowCount());
    for (int i = 0; i < numColumns; i++) {
      assertEquals("Group " + i, headerTable.getHTML(0, i));
      assertEquals("Column " + i, headerTable.getHTML(1, i));
    }
  }

  /**
   * Generate a two row, column spanning header.
   */
  public void testHeaderGenerationUneven() {
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    FixedWidthFlexTable headerTable = table.getHeaderTable();
    TableDefinition<List<Object>> tableDef = table.getTableDefinition();

    // Setup the headers
    List<ColumnDefinition<List<Object>, ?>> colDefs = tableDef.getVisibleColumnDefinitions();
    int numColumns = colDefs.size();
    for (int i = 0; i < numColumns; i++) {
      AbstractColumnDefinition<List<Object>, ?> colDef = (AbstractColumnDefinition<List<Object>, ?>) colDefs.get(i);
      colDef.setHeader(0, "Column " + i);
      if (i % 2 == 0) {
        colDef.setHeader(1, "Group " + i);
        colDef.setHeader(2, "Topic " + i);
      }
    }

    // Test the header table
    table.setHeaderGenerated(true);
    assertEquals(numColumns, headerTable.getColumnCount());
    assertEquals(numColumns, headerTable.getCellCount(0));
    assertEquals(numColumns / 2, headerTable.getCellCount(1));
    assertEquals(numColumns, headerTable.getCellCount(2));
    assertEquals(3, headerTable.getRowCount());
    for (int i = 0; i < numColumns; i++) {
      if (i % 2 == 0) {
        assertEquals("Topic " + i, headerTable.getHTML(0, i));
      } else {
        assertEquals("&nbsp;", headerTable.getHTML(0, i));
      }
      assertEquals("Column " + i, headerTable.getHTML(2, i));
    }
  }

  public void testOnDataTableRendered() {
    final List<Object> tester = new ArrayList<Object>();
    TableModel<List<Object>> tableModel = createTableModel();
    TableDefinition<List<Object>> tableDef = createTableDefinition();
    PagingScrollTable<List<Object>> table = new PagingScrollTable<List<Object>>(
        tableModel, tableDef) {
      @Override
      protected void onDataTableRendered() {
        super.onDataTableRendered();
        tester.add("onDataTableRendered");
      }
    };

    // Goto to a page
    assertEquals(0, tester.size());
    table.gotoFirstPage();
    assertEquals(1, tester.size());
  }

  public void testPageChangeHandler() {
    // Initialize the grid
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    TableModel<List<Object>> tableModel = table.getTableModel();

    // Create some handlers
    TestPageChangeHandler pch = new TestPageChangeHandler();
    table.addPageChangeHandler(pch);
    tableModel.setRowCount(100);
    table.setPageSize(10);

    // Goto page
    table.gotoPage(0, true);
    pch.assertOldPage(-1);
    pch.assertNewPage(0);

    // Goto another page
    table.gotoPage(4, true);
    pch.assertOldPage(0);
    pch.assertNewPage(4);
  }

  public void testPageCountChangeHandler() {
    // Initialize the grid
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    TableModel<List<Object>> tableModel = table.getTableModel();

    // Create some handlers
    TestPageCountChangeHandler pch = new TestPageCountChangeHandler();
    table.addPageCountChangeHandler(pch);
    tableModel.setRowCount(1000);
    table.setPageSize(10);

    // Set row count
    tableModel.setRowCount(500);
    pch.assertOldPageCount(100);
    pch.assertNewPageCount(50);

    // Set page size
    table.setPageSize(5);
    pch.assertOldPageCount(50);
    pch.assertNewPageCount(100);

    // Unknown row count
    tableModel.setRowCount(-1);
    pch.assertOldPageCount(100);
    pch.assertNewPageCount(-1);

    // Set page size with unknown row count
    pch.reset();
    table.setPageSize(10);
    pch.assertNotFired();

    // Reestablish row count
    tableModel.setRowCount(20);
    pch.assertOldPageCount(-1);
    pch.assertNewPageCount(2);

    // No page size
    table.setPageSize(0);
    pch.assertOldPageCount(2);
    pch.assertNewPageCount(1);
  }

  /**
   * Test page navigation.
   */
  public void testPaging() {
    // Initialize the grid
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    TableModel<List<Object>> tableModel = table.getTableModel();

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
    assertFalse(table.isPageLoading());

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
    tableModel.setRowCount(MutableTableModel.UNKNOWN_ROW_COUNT);
    table.gotoPage(20, false);
    assertEquals(20, table.getCurrentPage());
    table.gotoPage(300, false);
    assertEquals(300, table.getCurrentPage());
    table.gotoLastPage();
    assertEquals(300, table.getCurrentPage());
    table.gotoFirstPage();
    assertEquals(0, table.getCurrentPage());
  }

  public void testPagingAccessors() {
    // Initialize the grid
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    TableModel<List<Object>> tableModel = table.getTableModel();
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
    assertEquals(5, table.getPageCount());
    table.setPageSize(10);
    assertEquals(3, table.getPageCount());
    table.setPageSize(25);
    assertEquals(1, table.getPageCount());

    // Unknown number of pages
    tableModel.setRowCount(MutableTableModel.UNKNOWN_ROW_COUNT);
    assertEquals(-1, table.getPageCount());
    table.setPageSize(0);
    assertEquals(1, table.getPageCount());

    // bulk renderer
    FixedWidthGridBulkRenderer<List<Object>> bulkRenderer = new FixedWidthGridBulkRenderer<List<Object>>(
        grid, table.getTableDefinition());
    table.setBulkRenderer(bulkRenderer);

    // Empty table widget
    {
      Widget emptyTableWidget = new HTML("Empty");
      table.setEmptyTableWidget(emptyTableWidget);
      assertEquals(emptyTableWidget, table.getEmptyTableWidget());
    }

    // header generation
    {
      table.setHeaderGenerated(true);
      assertTrue(table.isHeaderGenerated());
      table.setHeaderGenerated(false);
      assertFalse(table.isHeaderGenerated());
    }

    // footer generation
    {
      table.setFooterGenerated(true);
      assertTrue(table.isFooterGenerated());
      table.setFooterGenerated(false);
      assertFalse(table.isFooterGenerated());
    }
  }

  /**
   * Test page navigation when there is only one page.
   */
  public void testPagingSinglePage() {
    // Initialize the grid
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    TestListTableModel tableModel = (TestListTableModel) table.getTableModel();
    table.setPageSize(0);

    table.gotoPage(0, false);
    assertEquals(0, table.getCurrentPage());
    assertEquals(25, table.getDataTable().getRowCount());
    assertEquals(0, tableModel.getLastRequest().getStartRow());
    assertEquals(25, tableModel.getLastRequest().getNumRows());
  }

  /**
   * Test paging when the TableModel responds with a failure.
   */
  public void testPagingFailure() {
    TableModel<List<Object>> tableModel = new TableModel<List<Object>>() {
      @Override
      public void requestRows(Request request,
          TableModel.Callback<List<Object>> callback) {
        callback.onFailure(new Exception());
      }
    };
    PagingScrollTable<List<Object>> table = getPagingScrollTable(tableModel);

    assertFalse(table.isPageLoading());
    table.gotoPage(0, true);
    assertFalse(table.isPageLoading());
  }

  /**
   * Test paging when the TableModel does not respond.
   */
  public void testPagingNoResponse() {
    TableModel<List<Object>> tableModel = new TableModel<List<Object>>() {
      @Override
      public void requestRows(Request request,
          TableModel.Callback<List<Object>> callback) {
      }
    };
    PagingScrollTable<List<Object>> table = getPagingScrollTable(tableModel);

    assertFalse(table.isPageLoading());
    table.gotoPage(0, true);
    assertTrue(table.isPageLoading());
  }

  /**
   * Test changing a row value.
   */
  public void testRowValueChanged() {
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    TestListTableModel tableModel = (TestListTableModel) table.getTableModel();
    table.setPageSize(5);
    table.gotoPage(1, true);

    // Set a row value on the page
    {
      List<Object> rowValue = new ArrayList<Object>();
      tableModel.setRowValue(6, rowValue);
      assertEquals(rowValue, table.getRowValue(1));
    }

    // Set a row value before the page
    {
      List<Object> rowValue = new ArrayList<Object>();
      tableModel.setRowValue(4, rowValue);
    }

    // Set a row value after the page
    {
      List<Object> rowValue = new ArrayList<Object>();
      tableModel.setRowValue(10, rowValue);
    }
  }

  /**
   * Test values associated with rows.
   */
  public void testRowValues() {
    // Initialize the grid
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    table.getDataTable().resize(10, 10);

    // Test manually setting values
    List<Object> value1 = new ArrayList<Object>();
    List<Object> value2 = new ArrayList<Object>();
    List<Object> value3 = new ArrayList<Object>();
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
   * @return a {@link PagingScrollTable}
   */
  protected PagingScrollTable<List<Object>> getPagingScrollTable() {
    return getPagingScrollTable(createTableModel());
  }

  @Override
  protected PagingScrollTable<List<Object>> getScrollTable(
      FixedWidthFlexTable headerTable, FixedWidthGrid dataTable,
      FixedWidthFlexTable footerTable) {
    return getPagingScrollTable(headerTable, dataTable, footerTable,
        createTableModel());
  }

  /**
   * @return a new table definition with 10 columns
   */
  private DefaultTableDefinition<List<Object>> createTableDefinition() {
    List<ColumnDefinition<List<Object>, ?>> colDefs = new ArrayList<ColumnDefinition<List<Object>, ?>>();
    for (int i = 0; i < 10; i++) {
      colDefs.add(new CustomColumnDefinition<List<Object>, Object>());
    }
    return new DefaultTableDefinition<List<Object>>(colDefs);
  }

  /**
   * @return a new table model with 25 rows
   */
  private TableModel<List<Object>> createTableModel() {
    // Create the row data
    List<List<Object>> rowList = new ArrayList<List<Object>>();
    for (int row = 0; row < 25; row++) {
      ArrayList<Object> columnList = new ArrayList<Object>();
      rowList.add(columnList);
      for (int column = 0; column < 4; column++) {
        columnList.add(row + ":" + column);
      }
    }
    return new TestListTableModel(rowList);
  }

  /**
   * @return a {@link PagingScrollTable}
   */
  private PagingScrollTable<List<Object>> getPagingScrollTable(
      TableModel<List<Object>> tableModel) {
    FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
    FixedWidthGrid dataTable = new FixedWidthGrid();
    FixedWidthFlexTable footerTable = new FixedWidthFlexTable();
    return getPagingScrollTable(headerTable, dataTable, footerTable, tableModel);
  }

  private PagingScrollTable<List<Object>> getPagingScrollTable(
      FixedWidthFlexTable headerTable, FixedWidthGrid dataTable,
      FixedWidthFlexTable footerTable, TableModel<List<Object>> tableModel) {
    DefaultTableDefinition<List<Object>> tableDef = createTableDefinition();
    PagingScrollTable<List<Object>> scrollTable = new PagingScrollTable<List<Object>>(
        tableModel, dataTable, headerTable, tableDef);
    scrollTable.setFooterTable(footerTable);
    return scrollTable;
  }
}
