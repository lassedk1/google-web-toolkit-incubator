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
public interface ControllableTable extends SourceDataRequestEvents {
  /**
   * Get the sort index.
   * 
   * @return the sort index, -1 if unsorted
   */
  public int getSortIndex();
  
  /**
   * Insert a row into the table.
   * 
   * @param beforeRow the row index
   * @return the index of the newly created row
   */
  public int insertRow(int beforeRow);

  /**
   * Return true if the sort order is ascending, false if descending.
   * 
   * @return the sort order
   */
  public boolean isSortAscending();
  
  /**
   * Remove a row from the table.
   * 
   * @param row the row index
   */
  public void removeRow(int row);

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
   */
  public void setData(int firstRow, Iterator/* Iterator<Object> */rows);

  /**
   * Set the total number of rows.
   * 
   * @param numRows the new number of rows
   */
  public void setNumRows(int numRows);
}
