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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Tests methods used for all {@link TableController} class.
 */
public class TableControllerTest extends GWTTestCase {
  /**
   * A {@link ControllableTable} used for testing.
   */
  private class TestControllableTable implements ControllableTable {
    /**
     * Has insertRow been called.
     */
    public boolean insertRowCalled = false;

    /**
     * Has removeRow been called.
     */
    public boolean removeRowCalled = false;

    /**
     * The row values.
     */
    public List rowValues = null;

    /**
     * Has setData been called.
     */
    public boolean setDataCalled = false;

    /**
     * Has setData (Iterator version) been called.
     */
    public boolean setDataIteratorCalled = false;

    /**
     * The number of rows.
     */
    private int numRows = -1;

    public void addTableDataRequestListener(TableDataRequestListener listener) {
    }

    /**
     * Get the number of rows.
     * 
     * @return the number of rows
     */
    public int getNumRows() {
      return numRows;
    }

    public int getSortIndex() {
      return 0;
    }

    public void insertAbsoluteRow(int beforeRow, int numRows) {
      insertRowCalled = true;
    }

    public boolean isSortAscending() {
      return false;
    }

    public void removeAbsoluteRow(int row, int numRows) {
      removeRowCalled = true;
    }

    public void removeTableDataRequestListener(TableDataRequestListener listener) {
    }

    public void setData(int row, int column, Object data) {
      setDataCalled = true;
    }

    public void setData(int firstRow, Iterator rows, List rowValues) {
      setDataIteratorCalled = true;
      this.rowValues = rowValues;
    }

    public void setNumRows(int numRows) {
      this.numRows = numRows;
    }
  }

  /**
   * A read only table model.
   */
  private TableModel readOnlyTableModel = new ClientTableModel() {
    public Object getCell(int rowNum, int colNum) {
      if (rowNum < 5 && colNum < 5) {
        return rowNum + ":" + colNum;
      }
      return null;
    }
  };

  /**
   * A writable table model.
   */
  private TableModel writableTableModel = new ListTableModel(new ArrayList());

  public String getModuleName() {
    return "com.google.gwt.widgetideas.WidgetIdeas";
  }

  /**
   * Get the table controller to test.
   */
  public TableController getTableController(TableModel tableModel) {
    return new TableController(tableModel);
  }

  /**
   * Test accessors.
   */
  public void testAccessors() {
    // Create a table controller
    TableModel tableModel = readOnlyTableModel;
    TableController controller = getTableController(tableModel);
    assertEquals(tableModel, controller.getTableModel());

    // setNumRows
    controller.setNumRows(100);
    assertEquals(100, controller.getNumRows());
    controller.setNumRows(200);
    assertEquals(200, controller.getNumRows());
    controller.setNumRows(-1);
    assertEquals(-1, controller.getNumRows());
  }

  /**
   * Test usage of controllable tables.
   */
  public void testControllableTables() {
    // Create a table controller
    TableController controller = getTableController(writableTableModel);

    // Create some controllable tables
    List tables = new ArrayList();
    for (int i = 0; i < 5; i++) {
      tables.add(new TestControllableTable());
    }

    // Try to remove when no tables are present
    controller.removeControllableTable((TestControllableTable) tables.get(0));

    // Add controllable tables
    Iterator tableIt = tables.iterator();
    while (tableIt.hasNext()) {
      controller.addControllableTable((TestControllableTable) tableIt.next());
    }

    // Test row insertion
    controller.insertRow(3);
    tableIt = tables.iterator();
    while (tableIt.hasNext()) {
      TestControllableTable table = (TestControllableTable) tableIt.next();
      assertTrue(table.insertRowCalled);
      assertFalse(table.removeRowCalled);
      assertFalse(table.setDataCalled);
      assertFalse(table.setDataIteratorCalled);
    }

    // Test row removal
    controller.removeRow(3);
    tableIt = tables.iterator();
    while (tableIt.hasNext()) {
      TestControllableTable table = (TestControllableTable) tableIt.next();
      assertTrue(table.insertRowCalled);
      assertTrue(table.removeRowCalled);
      assertFalse(table.setDataCalled);
      assertFalse(table.setDataIteratorCalled);
    }

    // Test set data
    controller.setData(3, 3, null);
    tableIt = tables.iterator();
    while (tableIt.hasNext()) {
      TestControllableTable table = (TestControllableTable) tableIt.next();
      assertTrue(table.insertRowCalled);
      assertTrue(table.removeRowCalled);
      assertTrue(table.setDataCalled);
      assertFalse(table.setDataIteratorCalled);
    }

    // Test set num rows
    controller.setNumRows(2000);
    tableIt = tables.iterator();
    while (tableIt.hasNext()) {
      TestControllableTable table = (TestControllableTable) tableIt.next();
      assertTrue(table.insertRowCalled);
      assertTrue(table.removeRowCalled);
      assertTrue(table.setDataCalled);
      assertEquals(2000, table.getNumRows());
    }
  }

  /**
   * Test Unsupported Operations.
   */
  public void testUnsupportedOperations() {
    // Create a table controller
    TableController controller = getTableController(readOnlyTableModel);

    try {
      controller.insertRow(0);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
    }
    try {
      controller.removeRow(0);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
    }
    try {
      controller.setData(0, 0, null);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
    }
  }
}
