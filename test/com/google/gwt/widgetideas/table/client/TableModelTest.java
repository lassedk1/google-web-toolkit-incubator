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

import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortInfo;
import com.google.gwt.widgetideas.table.client.TableModel.ColumnSortList;
import com.google.gwt.widgetideas.table.client.TableModel.Request;
import com.google.gwt.widgetideas.table.client.TableModel.Response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
   * Test {@link Request} object.
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
   * Test {@link Response} object.
   */
  public void testResponse() {
    // Craete row values
    List rowValues = new ArrayList();
    for (int i = 0; i < 10; i++) {
      rowValues.add("value" + i);
    }

    // Create a Response
    Response response = new Response(rowValues) {
      public Iterator iterator() {
        return null;
      }
    };

    // Get the rowValues
    assertEquals(rowValues, response.getRowValues());
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
    sortList.addColumnSortInfo(4, false);
    assertEquals(1, sortList.size());
    assertEquals(4, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());
    assertTrue(sortList.getPrimaryColumnSortInfo().equals(
        new ColumnSortInfo(4, false)));

    // Add more items
    sortList.addColumnSortInfo(6, false);
    sortList.addColumnSortInfo(8, true);
    sortList.addColumnSortInfo(10, true);
    assertEquals(4, sortList.size());
    assertEquals(10, sortList.getPrimaryColumn());
    assertTrue(sortList.isPrimaryAscending());
    assertTrue(sortList.get(0).equals(new ColumnSortInfo(10, true)));
    assertTrue(sortList.get(1).equals(new ColumnSortInfo(8, true)));
    assertTrue(sortList.get(2).equals(new ColumnSortInfo(6, false)));
    assertTrue(sortList.get(3).equals(new ColumnSortInfo(4, false)));
    
    // Add an existing item
    sortList.addColumnSortInfo(6, false);
    assertEquals(4, sortList.size());
    assertEquals(6, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());

    // Add an existing column, different order
    sortList.addColumnSortInfo(8, false);
    assertEquals(4, sortList.size());
    assertEquals(8, sortList.getPrimaryColumn());
    assertFalse(sortList.isPrimaryAscending());
  }
}
