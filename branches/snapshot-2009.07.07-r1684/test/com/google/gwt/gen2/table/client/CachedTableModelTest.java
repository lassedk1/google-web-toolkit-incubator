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

import com.google.gwt.gen2.table.client.TableModelHelper.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Test methods for {@link CachedTableModel} class.
 */
public class CachedTableModelTest extends ClientTableModelTest {
  /**
   * A table model that records its previous requests.
   */
  private static class TestTableModel extends ListTableModel {
    /**
     * The last request received.
     */
    private Request lastRequest = null;

    /**
     * Constructor.
     * 
     * @param rows the data that this model feeds from
     */
    public TestTableModel(List<List<Object>> rows) {
      super(rows);
    }

    /**
     * @return the last request
     */
    public Request getLastRequest() {
      return lastRequest;
    }

    @Override
    public void requestRows(Request request, final Callback<List<Object>> callback) {
      lastRequest = request;
      super.requestRows(request, callback);
    }

    @Override
    protected boolean onRowInserted(int beforeRow) {
      return true;
    }

    @Override
    protected boolean onRowRemoved(int row) {
      return true;
    }

    @Override
    protected boolean onSetRowValue(int row, List<Object> rowValue) {
      return true;
    }
  }

  @Override
  public CachedTableModel<List<Object>> getTableModel(boolean failureMode) {
    MutableTableModel<List<Object>> tableModel = super.getTableModel(false);

    // Failure mode version
    if (failureMode) {
      return new CachedTableModel<List<Object>>(tableModel) {
        @Override
        public void requestRows(Request request, Callback<List<Object>> callback) {
          callback.onFailure(new Exception());
        }
      };
    }

    // Normal version
    return new CachedTableModel<List<Object>>(tableModel);
  }

  /**
   * Test the accessors.
   */
  public void testAccessors() {
    // Create a table model
    CachedTableModel<List<Object>> tableModel = getTableModel(false);

    // Test cache rows
    assertEquals(0, tableModel.getPreCachedRowCount());
    assertEquals(0, tableModel.getPostCachedRowCount());
    tableModel.setPreCachedRowCount(10);
    tableModel.setPostCachedRowCount(20);
    assertEquals(10, tableModel.getPreCachedRowCount());
    assertEquals(20, tableModel.getPostCachedRowCount());
  }

  /**
   * Test the cache.
   */
  public void testCache() {
    // Create the row data
    List<List<Object>> rowList = new ArrayList<List<Object>>();
    for (int row = 0; row < 40; row++) {
      ArrayList<Object> columnList = new ArrayList<Object>();
      rowList.add(columnList);
      for (int column = 0; column < 3; column++) {
        columnList.add(new CellPair(row, column));
      }
    }

    // Create a table model
    TestTableModel innerModel = new TestTableModel(rowList);
    CachedTableModel<List<Object>> tableModel = new CachedTableModel<List<Object>>(innerModel);
    assertEquals(rowList.size(), tableModel.getRowCount());
    Request lastRequest = null;

    // Send a request
    Request request1 = new Request(10, 5);
    TestCallback<List<Object>> callback1 = new TestCallback<List<Object>>(10, 5, null);
    tableModel.requestRows(request1, callback1);
    assertTrue(callback1.isExecuted());
    lastRequest = innerModel.getLastRequest();
    assertEquals(10, lastRequest.getStartRow());
    assertEquals(5, lastRequest.getNumRows());

    // Send a request for the same data
    Request request2 = new Request(10, 5);
    TestCallback<List<Object>> callback2 = new TestCallback<List<Object>>(10, 5, null);
    tableModel.requestRows(request2, callback2);
    assertTrue(callback2.isExecuted());
    assertEquals(lastRequest, innerModel.getLastRequest());

    // Send a request with pre and post caching, but we already have the data
    tableModel.setPreCachedRowCount(10);
    tableModel.setPostCachedRowCount(10);
    Request request3 = new Request(10, 5);
    TestCallback<List<Object>> callback3 = new TestCallback<List<Object>>(10, 5, null);
    tableModel.requestRows(request3, callback3);
    assertTrue(callback3.isExecuted());
    assertEquals(lastRequest, innerModel.getLastRequest());

    // Send a request with pre and post caching, but we do not have the data
    Request request4 = new Request(25, 5);
    TestCallback<List<Object>> callback4 = new TestCallback<List<Object>>(25, 5, null);
    tableModel.requestRows(request4, callback4);
    assertTrue(callback4.isExecuted());
    assertNotSame(lastRequest, innerModel.getLastRequest());
    lastRequest = innerModel.getLastRequest();
    assertEquals(15, lastRequest.getStartRow());
    assertEquals(25, lastRequest.getNumRows());

    // Verify that rows were pre cached
    Request request5 = new Request(15, 5);
    TestCallback<List<Object>> callback5 = new TestCallback<List<Object>>(15, 5, null);
    tableModel.requestRows(request5, callback5);
    assertTrue(callback5.isExecuted());
    assertEquals(lastRequest, innerModel.getLastRequest());

    // Verify that rows were post cached
    Request request6 = new Request(35, 5);
    TestCallback<List<Object>> callback6 = new TestCallback<List<Object>>(35, 5, null);
    tableModel.requestRows(request6, callback6);
    assertTrue(callback6.isExecuted());
    assertEquals(lastRequest, innerModel.getLastRequest());
  }
}
