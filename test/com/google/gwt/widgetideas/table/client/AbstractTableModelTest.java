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
import com.google.gwt.widgetideas.table.client.TableModel.Callback;
import com.google.gwt.widgetideas.table.client.TableModel.Request;
import com.google.gwt.widgetideas.table.client.TableModel.Response;

/**
 * Tests methods used for all {@link TableModel} class.
 */
public abstract class AbstractTableModelTest extends GWTTestCase {
  /**
   * A {@link Callback} used for testing.
   */
  private class TestCallback implements Callback {
    /**
     * A boolean indicating that this callback has been executed.
     */
    private boolean executed = false;

    /**
     * Expected num rows in Request.
     */
    private int numRows;

    /**
     * Expected sort order in Request.
     */
    private boolean sortAscending;

    /**
     * Expected sort index in Request.
     */
    private int sortIndex;

    /**
     * Expected start row in Request.
     */
    private int startRow;

    /**
     * Constructor.
     * 
     * @param startRow expected start row in request
     * @param numRows expected num rows in request
     * @param sortIndex expected sort index in request
     * @param sortAscending expected sort order in request
     */
    public TestCallback(int startRow, int numRows, int sortIndex,
        boolean sortAscending) {
      this.startRow = startRow;
      this.numRows = numRows;
      this.sortIndex = sortIndex;
      this.sortAscending = sortAscending;
    }

    /**
     * Check if this callback has been executed.
     * 
     * @return true if executed
     */
    public boolean isExecuted() {
      return executed;
    }

    public void onRowsReady(Request request, Response response) {
      executed = true;
      assertEquals(request.getStartRow(), startRow);
      assertEquals(request.getNumRows(), numRows);
      assertEquals(request.getSortIndex(), sortIndex);
      assertEquals(request.isSortAscending(), sortAscending);
    }
  }

  public String getModuleName() {
    return "com.google.gwt.widgetideas.WidgetIdeas";
  }

  /**
   * Get an instance of the table model.
   * 
   * @return the table model
   */
  public abstract TableModel getTableModel();

  /**
   * Test requestRows method.
   */
  public void testRequestRows() {
    // Get the table model
    TableModel tableModel = getTableModel();

    // Request some rows without sorting
    TestCallback callback1 = new TestCallback(10, 20, -1, true);
    tableModel.requestRows(10, 20, callback1);
    assertTrue(callback1.isExecuted());

    // Request some rows with sorting
    TestCallback callback2 = new TestCallback(5, 6, 5, true);
    tableModel.requestRows(5, 6, 5, true, callback2);
    assertTrue(callback2.isExecuted());

    // Request some rows with sorting descending
    TestCallback callback3 = new TestCallback(5, 6, 5, false);
    tableModel.requestRows(5, 6, 5, false, callback3);
    assertTrue(callback2.isExecuted());
  }
}
