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

/**
 * Tests methods in the {@link ReadOnlyTableModel} class.
 */
public class ReadOnlyTableModelTest extends AbstractTableModelTest {
  /**
   * The table model to test.
   */
  private ReadOnlyTableModel tableModel = null;

  /**
   * @see AbstractTableModelTest
   */
  public TableModel getTableModel() {
    if (tableModel == null) {
      tableModel = new ReadOnlyTableModel() {
        public void requestRows(Request request, Callback callback) {
          callback.onRowsReady(request, null);
        }
      };
    }
    return tableModel;
  }

  /**
   * Test Unsupported Opertions that are not read only.
   */
  public void testUnsupportedOperations() {
    getTableModel();
    
    // Try calling unsupported operations
    try {
      tableModel.onRowInserted(0);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
    }
    try {
      tableModel.onRowRemoved(0);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
    }
    try {
      tableModel.onSetData(0, 0, null);
      fail("UnsupportedOperationException expected");
    } catch (UnsupportedOperationException e) {
      assertEquals(e.getMessage(), ReadOnlyTableModel.READ_ONLY_ERROR);
    }
  }
}
