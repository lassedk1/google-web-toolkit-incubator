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

import com.google.gwt.gen2.table.client.TableModelHelper.ColumnFilterList;
import com.google.gwt.gen2.table.client.TableModelHelper.ColumnSortList;
import com.google.gwt.gen2.table.client.TableModelHelper.SerializableResponse;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Collection;
import java.util.Set;

/**
 * A tree table version of the {@link TableModel}.
 * 
 * @param <R> the data type of the row values
 */
public abstract class TreeTableModel<R extends TreeTableItem> extends TableModel<R> {
  /**
   * Request object taking care of the tree nodes state
   */
  public static class Request extends TableModelHelper.Request implements IsSerializable {
    private Set<String> openNodes;

    public Request() {
    }

    public Request(int startRow, int numRows, ColumnSortList columnSortList,
        ColumnFilterList columnFilterList, Set<String> openNodes) {
      super(startRow, numRows, columnSortList, columnFilterList);
      this.openNodes = openNodes;
    }

    public Set<String> getOpenNodes() {
      return openNodes;
    }
  }

  /**
   * Request object
   */
  public static class Response<RowType extends TreeTableItem> extends
      SerializableResponse<RowType> {
    private int rowCount;

    public Response() {
      super(null);
    }

    public Response(Collection<RowType> rows, int rowCount) {
      super(rows);
      this.rowCount = rowCount;
    }

    public int getRowCount() {
      return rowCount;
    }
  }

  public void requestRows(TableModelHelper.Request request, Callback<R> callback) {
    requestTreeItems((TreeTableModel.Request) request, callback);
  }

  public abstract void requestTreeItems(TreeTableModel.Request request,
      Callback<R> callback);

  public void setRowMetadata(int rowCount) {
    setRowCount(rowCount);
  }
}