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

import com.google.gwt.gen2.table.shared.ColumnSortInfo;
import com.google.gwt.gen2.table.shared.ColumnSortList;
import com.google.gwt.gen2.table.shared.Request;
import com.google.gwt.gen2.table.shared.Response;
import com.google.gwt.gen2.table.shared.SerializableResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Tests methods in the {@link MutableTableModel} class.
 */
public class TableModelTest extends TableModelTestBase {

  @Override
  public <R> TableModel<R> getTableModel(boolean failureMode) {
    // Failure mode version
    if (failureMode) {
      return new TableModel<R>() {
        @Override
        public void requestRows(Request request, Callback<R> callback) {
          callback.onFailure(new Exception());
        }
      };
    }

    // Normal version
    return new TableModel<R>() {
      @Override
      public void requestRows(Request request, Callback<R> callback) {
        callback.onRowsReady(request, null);
      }
    };
  }

  /**
   * Test the accessors in {@link Request}.
   */
  public void testRequest() {
    // Without sort index
    Request request1 = new Request(10, 20);
    assertEquals(request1.getStartRow(), 10);
    assertEquals(request1.getNumRows(), 20);
    assertNull(request1.getColumnSortList());

    // With sort index
    ColumnSortList sortList = new ColumnSortList();
    Request request2 = new Request(5, 10, sortList);
    assertEquals(request2.getStartRow(), 5);
    assertEquals(request2.getNumRows(), 10);
    assertEquals(request2.getColumnSortList(), sortList);
  }

  /**
   * Test the row values in a {@link Response}.
   */
  public void testSerializableResponse() {
    // Create row values and cell data
    List<CustomRowValue> rowValues = new ArrayList<CustomRowValue>();
    for (int row = 0; row < 10; row++) {
      List<String> cellValues = new ArrayList<String>();
      for (int cell = 0; cell < 10; cell++) {
        cellValues.add(row + ":" + cell);
      }
      rowValues.add(new CustomRowValue(cellValues));
    }

    // Create a Response
    Response<CustomRowValue> response = new SerializableResponse<CustomRowValue>(rowValues);

    // Compare the rowValues to the expected rowValues
    Iterator<CustomRowValue> rowIt = response.getRowValues();
    int row = 0;
    while (rowIt.hasNext()) {
      assertEquals(rowValues.get(row), rowIt.next());
      row++;
    }
    assertEquals(10, row);
  }

  /**
   * Test the {@link ColumnSortInfo} class.
   */
  public void testSortInfo() {
    // Test constructor
    ColumnSortInfo sortInfo = new ColumnSortInfo(3, true);
    assertEquals(3, sortInfo.getColumn());
    assertTrue(sortInfo.isAscending());

    // Test modifiers
    sortInfo.setColumn(5);
    sortInfo.setAscending(false);
    assertEquals(5, sortInfo.getColumn());
    assertFalse(sortInfo.isAscending());

    // Test equals
    assertTrue(sortInfo.equals(new ColumnSortInfo(5, false)));
    assertFalse(sortInfo.equals(new ColumnSortInfo(4, false)));
    assertFalse(sortInfo.equals(new ColumnSortInfo(5, true)));
  }

  /**
   * Test the {@link ColumnSortList} class.
   */
  public void testSortList() {
    // Create a new list
    ColumnSortList sortList = new ColumnSortList();
    assertEquals(0, sortList.size());

    // Add one item
    sortList.add(new ColumnSortInfo(4, false));
    assertEquals(1, sortList.size());
    assertEquals(4, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());
    assertTrue(sortList.getPrimaryColumnSortInfo().equals(new ColumnSortInfo(4, false)));

    // Add more items
    sortList.add(new ColumnSortInfo(6, false));
    sortList.add(new ColumnSortInfo(8, true));
    sortList.add(new ColumnSortInfo(10, true));
    assertEquals(4, sortList.size());
    assertEquals(10, sortList.getPrimaryColumn());
    assertTrue(sortList.isPrimaryAscending());

    // Check all of the entries in the sort lise
    int count = 0;
    for (ColumnSortInfo info : sortList) {
      switch (count) {
        case 0:
          assertTrue(info.equals(new ColumnSortInfo(10, true)));
          break;
        case 1:
          assertTrue(info.equals(new ColumnSortInfo(8, true)));
          break;
        case 2:
          assertTrue(info.equals(new ColumnSortInfo(6, false)));
          break;
        case 3:
          assertTrue(info.equals(new ColumnSortInfo(4, false)));
          break;
      }
      count++;
    }

    // Add an existing item
    sortList.add(new ColumnSortInfo(6, false));
    assertEquals(4, sortList.size());
    assertEquals(6, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());

    // Add an existing column, different order
    sortList.add(new ColumnSortInfo(8, false));
    assertEquals(4, sortList.size());
    assertEquals(8, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());

    // Compare two lists
    ColumnSortList sortList1 = new ColumnSortList();
    sortList1.add(new ColumnSortInfo(1, true));
    sortList1.add(new ColumnSortInfo(2, false));
    sortList1.add(new ColumnSortInfo(3, true));
    ColumnSortList sortList2 = new ColumnSortList();
    sortList2.add(new ColumnSortInfo(1, true));
    sortList2.add(new ColumnSortInfo(2, false));
    sortList2.add(new ColumnSortInfo(3, true));
    ColumnSortList sortList3 = new ColumnSortList();
    sortList3.add(new ColumnSortInfo(1, true));
    sortList3.add(new ColumnSortInfo(4, false));
    sortList3.add(new ColumnSortInfo(3, true));
    ColumnSortList sortList4 = new ColumnSortList();
    sortList4.add(new ColumnSortInfo(1, true));
    sortList4.add(new ColumnSortInfo(2, true));
    sortList4.add(new ColumnSortInfo(3, true));
    assertTrue(sortList1.equals(sortList2));
    assertFalse(sortList1.equals(sortList3));
    assertFalse(sortList1.equals(sortList4));
    assertFalse(sortList3.equals(sortList4));
  }
}
