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

import com.google.gwt.widgetideas.table.client.TableModelHelper.Request;

/**
 * Tests methods in the {@link ReadOnlyTableModel} class.
 * 
 * @deprecated Replaced by
 *             {@link com.google.gwt.gen2.table.client.TableModelTest}
 */
@Deprecated
public class ReadOnlyTableModelTest extends TableModelTestBase {
  /**
   * @see TableModelTestBase#getTableModel(boolean)
   */
  @Override
  public <R> TableModel<R> getTableModel(boolean failureMode) {
    // Failure mode version
    if (failureMode) {
      return new ReadOnlyTableModel<R>() {
        @Override
        public void requestRows(Request request, Callback<R> callback) {
          callback.onFailure(new Exception());
        }
      };
    }

    // Normal version
    return new ReadOnlyTableModel<R>() {
      @Override
      public void requestRows(Request request, Callback<R> callback) {
        callback.onRowsReady(request, null);
      }
    };
  }

  /**
   * Test Unsupported Operations that are not read only.
   */
  public void testUnsupportedOperations() {
    // Create the table model
    TableModel<String> tableModel = getTableModel(false);
    tableModel.setRowCount(10);

    // Try calling unsupported operations
    try {
      tableModel.onRowInserted(0);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
      assertEquals(10, tableModel.getRowCount());
    }
    try {
      tableModel.onRowRemoved(0);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
      assertEquals(10, tableModel.getRowCount());
    }
    try {
      tableModel.onSetData(20, 0, null);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
      assertEquals(10, tableModel.getRowCount());
    }
  }
}
