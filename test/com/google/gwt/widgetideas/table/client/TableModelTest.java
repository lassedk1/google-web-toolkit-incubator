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

import com.google.gwt.widgetideas.table.client.TableModel.Request;

/**
 * Tests methods in the {@link TableModel} class.
 */
public class TableModelTest extends AbstractTableModelTest {
  /**
   * @see AbstractTableModelTest
   */
  public TableModel getTableModel() {
    return new TableModel() {
      public void onRowInserted(int beforeRow) {
      }

      public void onRowRemoved(int row) {
      }

      public void onSetData(int row, int cell, Object data) {
      }

      public void requestRows(Request request, Callback callback) {
        callback.onRowsReady(request, null);
      }
    };
  }

  /**
   * Test Request object.
   */
  public void testRequest() {
    // Without sort index
    Request request1 = new Request(10, 20);
    assertEquals(request1.getStartRow(), 10);
    assertEquals(request1.getNumRows(), 20);
    assertEquals(request1.getSortIndex(), -1);
    assertEquals(request1.isSortAscending(), true);

    // With sort index
    Request request2 = new Request(5, 10, 4, false);
    assertEquals(request2.getStartRow(), 5);
    assertEquals(request2.getNumRows(), 10);
    assertEquals(request2.getSortIndex(), 4);
    assertEquals(request2.isSortAscending(), false);
  }
}
