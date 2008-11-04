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
   * Test absolute row operations.
   */
  public void testAbsoluteRows() {
    // Initialize the grid
    PagingScrollTable<List<Object>> table = getPagingScrollTable();
    FixedWidthGrid grid = table.getDataTable();
    table.setPageSize(5);

    // Insert absolute row
    table.gotoPage(1, true);
    assertEquals("cell 2:1", grid.getText(2, 1));
    table.insertAbsoluteRow(10);
    assertEquals("cell 2:1", grid.getText(2, 1));
    table.insertAbsoluteRow(1);
    assertEquals("cell 1:1", grid.getText(2, 1));

    // Insert absolute row
    table.gotoPage(1, true);
    assertEquals("cell 2:1", grid.getText(2, 1));
    table.removeAbsoluteRow(10);
    assertEquals("cell 2:1", grid.getText(2, 1));
    table.removeAbsoluteRow(1);
    assertEquals("cell 3:1", grid.getText(2, 1));
    table.removeAbsoluteRow(7);
    assertEquals("cell 4:1", grid.getText(2, 1));
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
    FixedWidthFlexTable headerTable = new FixedWidthFlexTable();
    FixedWidthGrid dataTable = new FixedWidthGrid();
    FixedWidthFlexTable footerTable = new FixedWidthFlexTable();
    return getScrollTable(headerTable, dataTable, footerTable);
  }

  @Override
  protected PagingScrollTable<List<Object>> getScrollTable(
      FixedWidthFlexTable headerTable, FixedWidthGrid dataTable,
      FixedWidthFlexTable footerTable) {
    // Create a table cell renderer
    List<ColumnDefinition<List<Object>, ?>> colDefs = new ArrayList<ColumnDefinition<List<Object>, ?>>();
    for (int i = 0; i < 10; i++) {
      colDefs.add(new CustomColumnDefinition<List<Object>, Object>());
    }
    DefaultTableDefinition<List<Object>> tcr = new DefaultTableDefinition<List<Object>>(
        colDefs);

    // Create the table model
    TableModel<List<Object>> tableModel = createTableModel();

    // Create the table
    PagingScrollTable<List<Object>> scrollTable = new PagingScrollTable<List<Object>>(
        tableModel, dataTable, headerTable, tcr);
    scrollTable.setFooterTable(footerTable);
    return scrollTable;
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
    return new ListTableModel(rowList);
  }
}
