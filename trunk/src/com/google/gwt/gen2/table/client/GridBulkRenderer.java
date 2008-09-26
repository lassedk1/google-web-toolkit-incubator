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

import com.google.gwt.gen2.table.client.overrides.Grid;

/**
 * Allows bulk rendering of {@link Grid}s.
 * <p>
 * Must use the {@link Grid} in the overrides package.
 * </p>
 * 
 * @param <RowType> the data type of the row values
 */
public class GridBulkRenderer<RowType> extends TableBulkRenderer<RowType> {
  /**
   * Construct a new {@link GridBulkRenderer}.
   * 
   * @param grid {@link Grid} to be be bulk rendered
   * @param renderer the renderer that should be used during bulk rendering
   */
  public GridBulkRenderer(Grid grid, TableDefinition<RowType> renderer) {
    super(grid, renderer);
    if (grid instanceof FixedWidthGrid
        && (!(this instanceof FixedWidthGridBulkRenderer))) {
      throw new UnsupportedOperationException(
          "Must use a FixedWidthGridBulkLoader to bulk load a fixed grid");
    }
  }

  @Override
  protected void renderRows(String rawHTMLTable) {
    super.renderRows(rawHTMLTable);
    setGridDimensions((Grid) getTable());
  }

  /**
   * Short term hack to set protected row and columns.
   */
  native void setGridDimensions(Grid table) /*-{
    var numRows =  table.@com.google.gwt.gen2.table.client.overrides.HTMLTable::getDOMRowCount()();
    table.@com.google.gwt.gen2.table.client.overrides.Grid::numRows = numRows;
    table.@com.google.gwt.gen2.table.client.overrides.Grid::numColumns =
      table.@com.google.gwt.gen2.table.client.overrides.HTMLTable::getDOMCellCount(I)(numRows - 1);
  }-*/;

}
