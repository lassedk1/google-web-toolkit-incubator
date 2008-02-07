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

/**
 * Event listener interface for {@link FixedWidthGrid} events.
 */
public interface TableSelectionListener {
  /**
   * Fired when all rows are deselected.
   */
  public void onAllRowsDeselected();

  /**
   * Fired when a cell is clicked.
   * 
   * @param row the row index
   * @param cell the cell index
   */
  public void onCellClicked(int row, int cell);

  /**
   * Fired when a cell is hovered.
   * 
   * @param row the row index
   * @param cell the cell index
   */
  public void onCellHover(int row, int cell);
  
  /**
   * Fired when a cell is unhovered.
   * 
   * @param row the row index
   * @param cell the cell index
   */
  public void onCellUnhover(int row, int cell);

  /**
   * Fired when a single row is deselected. This method will not fire when all
   * rows are deselected. In that case, use the onAllRowsDeselected events.
   * 
   * @param row the row index
   */
  public void onRowDeselected(int row);

  /**
   * Fired when a row is hovered.
   * 
   * @param row the row index
   */
  public void onRowHover(int row);

  /**
   * Fired when one or more rows are selected.
   * 
   * @param firstRow the row index of the first row
   * @param numRows the number of selected rows
   */
  public void onRowsSelected(int firstRow, int numRows);

  /**
   * Fired when a row is unhovered.
   * 
   * @param row the row index
   */
  public void onRowUnhover(int row);
}
