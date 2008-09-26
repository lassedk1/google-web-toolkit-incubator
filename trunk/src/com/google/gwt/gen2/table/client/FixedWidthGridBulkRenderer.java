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

import com.google.gwt.gen2.table.client.overrides.HTMLTable;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

import java.util.Iterator;

/**
 * Helper class to bulk load {@link FixedWidthGrid} tables.
 * 
 * @param <RowType> the data type of the row values
 */
public class FixedWidthGridBulkRenderer<RowType> extends
    GridBulkRenderer<RowType> {
  private int numColumns;

  /**
   * Constructor. Takes in the number of columns in the table to allow efficient
   * creation of the header row.
   * 
   * @param table table to be bulk rendered
   * @param renderer the renderer that should be used during bulk rendering
   */
  public FixedWidthGridBulkRenderer(FixedWidthGrid table,
      TableDefinition<RowType> renderer) {
    super(table, renderer);
    this.numColumns = renderer.getColumnDefinitionCount();
    table.resizeColumns(numColumns);
  }

  /**
   * Gets the new ghost element from the table.
   * 
   * @param table the table
   * @return the new ghost row
   */
  protected native Element getBulkLoadedGhostRow(HTMLTable table)
  /*-{
    return table.@com.google.gwt.gen2.table.client.overrides.HTMLTable::getBodyElement()(table).rows[0];
  }-*/;

  @Override
  protected void renderRows(Iterator<RowType> iterator,
      final RenderingOptions options) {

    FixedWidthGrid table = (FixedWidthGrid) super.getTable();
    if (table.getColumnCount() == 0) {
      throw new IllegalStateException(
          "Bulk Rendered FixedWidthGrids must call resizeColumns() before being loaded");
    }
    table.updateGhostRow();
    options.headerRow = DOM.toString(table.getGhostRow());
    super.renderRows(iterator, options);
  }

  @Override
  protected void renderRows(String rawHTMLTable) {
    super.renderRows(rawHTMLTable);

    // Update the ghost row variable
    Element newGhostRow = getBulkLoadedGhostRow(getTable());
    ((FixedWidthGrid) getTable()).setGhostRow(newGhostRow);
  }
}
