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
    BulkLoadableImpl.BulkLoadable {

  private BulkLoadableImpl impl;

  /**
   * Constructor.
   */
  public BulkLoadedTable() {
    impl = new BulkLoadableImpl(this);
  }

  private StringCellRenderer renderer = new StringCellRenderer();

  /**
   * Sets the table's rows to the list of rows provided.
   * 
   * @param rows list of rows. Each row itself must be another list
   */
  public void renderRows(List rows) {
    impl.renderRows(rows);
  }

  /**
   * Sets the table's rows to all the data contained in the given tableModel.
   * 
   * @param tableModel the data tableModel
   */
  public void renderRows(TableModel tableModel) {
    renderRows(tableModel, 0, TableModel.ALL_ROWS);
  }

  /** Sets the body element of the table. */
  public void setBodyElement(Element e) {
    super.setBodyElement(e);
  }

  /**
   * Init rows removes all rows in the current table and adds the rows supplied
   * by the tableModel.
   * 
   * @param tableModel the table data
   * @param startRow the tableModel's start row index
   * @param numRows the number of rows to request from the tableModel -1
   *          indicates all of them
   */
  public void renderRows(TableModel tableModel, int startRow, int numRows) {
    impl.renderRows(tableModel, startRow, numRows);
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

  public StringCellRenderer getRenderer() {
    return renderer;
  }
}
