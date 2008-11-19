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

import com.google.gwt.gen2.table.shared.ColumnFilterList;
import com.google.gwt.gen2.table.shared.ColumnSortList;
import com.google.gwt.gen2.table.shared.Request;
import com.google.gwt.gen2.table.shared.SerializableResponse;
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
  public static class TreeRequest extends Request implements IsSerializable {
    private Set<String> invertedNodes;
    private boolean open;

    public TreeRequest() {
    }

    public TreeRequest(int startRow, int numRows, ColumnSortList columnSortList,
        ColumnFilterList columnFilterList, boolean open, Set<String> invertedNodes) {
      super(startRow, numRows, columnSortList, columnFilterList);
      this.open = open;
      this.invertedNodes = invertedNodes;
    }

    public Set<String> getInvertedNodes() {
      return invertedNodes;
    }

    public boolean isOpen() {
      return open;
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

  public void requestRows(Request request, Callback<R> callback) {
    requestTreeItems((TreeRequest)request, callback);
  }

  public abstract void requestTreeItems(TreeRequest request,
      Callback<R> callback);

  public void setRowMetadata(int rowCount) {
    setRowCount(rowCount);
  }
}