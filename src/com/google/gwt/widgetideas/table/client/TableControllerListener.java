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

import java.util.Iterator;

/**
 * Event listener interface for {@link TableController} events.
 */
public interface TableControllerListener {
  /**
   * Fired when the number of rows changes.
   * 
   * @param numRows the new number of rows
   */
  public void onNumRowsChanged(int numRows);

  /**
   * Fired when a row is inserted.
   * 
   * @param beforeRow the row index
   */
  public void onRowInserted(int beforeRow);

  /**
   * Fired when a row is removed.
   * 
   * @param row the row index
   */
  public void onRowRemoved(int row);

  /**
   * Fired when the data in a given cell changes.
   * 
   * @param row the row index
   * @param column the column index
   * @param data the data to set
   */
  public void onSetData(int row, int column, Object data);

  /**
   * Fired when a block of data changes. This method takes an iterator of
   * Collections, where each collection represents one row of data starting with
   * the first row.
   * 
   * @param firstRow the row index
   * @param rows the 2D Iterator of data
   */
  public void onSetData(int firstRow, Iterator/* Iterator<Object> */rows);
}
