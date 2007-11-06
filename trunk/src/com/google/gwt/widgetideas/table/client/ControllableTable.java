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
import java.util.List;

/**
 * Event listener interface for {@link TableController} events.
 */
public interface ControllableTable extends SourceTableDataRequestEvents {
  /**
   * Insert a row into the table relative to the total number of rows.
   * 
   * @param beforeRow the row index
   * @param numRows the new number of rows
   */
  public void insertAbsoluteRow(int beforeRow, int numRows);

  /**
   * Remove a row from the table relative to the total number of rows.
   * 
   * @param row the row index
   * @param numRows the new number of rows
   */
  public void removeAbsoluteRow(int row, int numRows);

  /**
   * Set some arbitrary data in a specific cell.
   * 
   * @param row the row index
   * @param column the column index
   * @param data the data to set
   */
  public void setData(int row, int column, Object data);

  /**
   * Set a block of data. This method is used when responding to data requests.
   * 
   * This method takes an iterator of Collections, where each collection
   * represents one row of data starting with the first row.
   * 
   * @param firstRow the row index
   * @param rows the 2D Iterator of data
   * @param rowValues the values associated with the rows
   */
  public void setData(int firstRow, Iterator/* Iterator<Object> */rows,
      List rowValues);

  /**
   * Set the total number of rows.
   * 
   * @param numRows the new number of rows
   */
  public void setNumRows(int numRows);
}
