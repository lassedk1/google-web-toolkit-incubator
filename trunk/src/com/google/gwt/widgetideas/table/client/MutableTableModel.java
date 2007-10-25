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

import java.util.Collection;

/**
 * A class to manage the connection between the local table and an external
 * source of data that supports manipulation.
 */
public abstract class MutableTableModel extends TableModel {
  /**
   * Event fired when a row is inserted.
   * 
   * @param beforeRow the row index of the new row
   */
  public abstract void onRowInserted(int beforeRow);

  /**
   * Event fired when a row is removed.
   * 
   * @param row the row index of the removed row
   */
  public abstract void onRowRemoved(int row);

  /**
   * Event fired when the local data changes.
   * 
   * @param row the row index
   * @param cell the cell index
   * @param data the new contents of the cell
   */
  public abstract void onSetData(int row, int cell, Object data);

  /**
   * Event fired when the local data changes.
   * 
   * @param firstRow the first row index
   * @param rows the 2D collection of row data
   */
  public abstract void onSetData(int firstRow, Collection rows);

  /**
   * 
   */

  /**
   * {@link TableModelAdapter} wraps a read-only {@link TableModel} in a
   * {@link MutableTableModel} wrapper, throwing
   * {@link UnsupportedOperationException} when appropriate. This was done to
   * simplify the design of tables, such as {@link ScrollTable} which may or may
   * not have mutable data.
   * 
   */
  public static class TableModelAdaptor extends MutableTableModel {
    private TableModel baseModel;

    /**
     * Constructor for {@link TableModelAdaptor}.
     * 
     * @param readOnlyTableModel the underlying read only table model
     */
    public TableModelAdaptor(TableModel readOnlyTableModel) {
      baseModel = readOnlyTableModel;
    }

    public void onRowInserted(int beforeRow) {
      throw new UnsupportedOperationException(
          "This Table Model supports read only operations");
    }

    public void onRowRemoved(int row) {
      throw new UnsupportedOperationException(
          "This Table Model supports read only operations");
    }

    public void onSetData(int row, int cell, Object data) {
      throw new UnsupportedOperationException(
          "This Table Model supports read only operations");
    }

    public void onSetData(int firstRow, Collection rows) {
      throw new UnsupportedOperationException(
          "This Table Model supports read only operations");
    }

    public void requestRows(Request request, Callback callback) {
      baseModel.requestRows(request, callback);
    }

  }

}
