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

import com.google.gwt.user.client.Element;
import com.google.gwt.widgetideas.table.client.overrides.FlexTable;

import java.util.List;

/**
 * Table data, provided by a specific{@link TableModel}, is bulk loaded into
 * the current table.
 */
public class BulkLoadedTable extends FlexTable implements
    BulkLoader.BulkLoadable {

  private BulkLoader loader;

  private StringCellRenderer renderer = new StringCellRenderer();

  /**
   * Constructor.
   */
  public BulkLoadedTable() {
    loader = new BulkLoader(this);
  }

  public StringCellRenderer getRenderer() {
    return renderer;
  }

  public int getRowsPerTimeCheck() {
    return loader.getRowsPerTimeCheck();
  }

  /**
   * Sets the table's rows to the list of rows provided. For example,
   * 
   * <pre>
   * ArrayList rows = new ArrayList();
   * ArrayList row1 = new ArrayList();
   * row1.add(&quot;cell 0,1&quot;);
   * row1.add(&quot;cell 0,2&quot;);
   * rows.add(row1);
   * renderRows(rows);
   * </pre>
   * 
   * @param rows list of rows. Each row itself must be another list
   */
  public void renderRows(List rows) {
    loader.renderRows(rows);
  }

  /**
   * Sets the table's rows to all the data contained in the given tableModel.
   * 
   * @param tableModel the data tableModel
   */
  public void renderRows(TableModel tableModel) {
    renderRows(tableModel, 0, TableModel.ALL_ROWS);
  }

  /**
   * Init rows removes all rows in the current table and adds the rows supplied
   * by the tableModel.
   * 
   * @param tableModel the table data
   * @param startRow the tableModel's start row index
   * @param numRows the number of rows to request from the tableModel -1
   *        indicates all of them
   */
  public void renderRows(TableModel tableModel, int startRow, int numRows) {
    loader.renderRows(tableModel, startRow, numRows);
  }

  /**
   * Sets the body element of the table.
   */
  public void setBodyElement(Element e) {
    super.setBodyElement(e);
  }

  /**
   * Sets a callback to be called after the table is finished being loaded.
   * 
   * @param callback the callback
   */
  public void setOnLoadCompleteCallBack(
      BulkLoader.OnLoadCompleteCallBack callback) {
    loader.onLoadComplete = callback;
  }

  /**
   * Adds a renderer to the data table to change how the tableModel's cells are
   * rendered.
   * 
   * @param renderer the renderer
   */
  public void setRenderer(StringCellRenderer renderer) {
    this.renderer = renderer;
  }

  /**
   * Sets how many rows should be processed before time is checked for an event
   * flush. Note: If rowsPerTimeCheck is set to anything other than
   * Integer.MAX_VALUE, then the table is constructed asynchronously.
   * 
   * @param rowsPerTimeCheck
   */
  public void setRowsPerTimeCheck(int rowsPerTimeCheck) {
    loader.setRowsPerTimeCheck(rowsPerTimeCheck);
  }
}
