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

import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.gen2.table.client.TableDefinition.HTMLCellView;

import java.util.List;

/**
 * Allows bulk rendering of {@link SelectionGrid}s.
 * 
 * @param <RowType> the data type of the row values
 */
public class SelectionGridBulkRenderer<RowType> extends
    GridBulkRenderer<RowType> {
  /**
   * A customized {@link SelectionGridBulkRenderer.BulkCellView} used by the
   * {@link SelectionGridBulkRenderer}.
   */
  protected class SelectionBulkCellView extends BulkCellView {
    /**
     * Construct a new {@link SelectionBulkCellView}.
     * 
     * @param options the {@link TableBulkRenderer.RenderingOptions} to apply to
     *          the table
     */
    public SelectionBulkCellView(RenderingOptions options) {
      super(options);
    }

    @Override
    protected void renderRowImpl(RowType rowValue,
        List<ColumnDefinition<RowType, ?>> visibleColumns) {
      StringBuffer buffer = getStringBuffer();
      buffer.append("<tr>");

      // Add the input column
      SelectionPolicy selectionPolicy = ((SelectionGrid) getTable()).getSelectionPolicy();
      if (selectionPolicy.hasInputColumn()) {
        buffer.append("<td align='CENTER'>");
        buffer.append(((SelectionGrid) getTable()).getInputHtml(selectionPolicy));
        buffer.append("</td>");
      }

      // Add the columns
      int numColumns = visibleColumns.size();
      for (int i = 0; i < numColumns; i++) {
        setCellIndex(i);
        renderCellImpl(rowValue, visibleColumns.get(i));
      }
      buffer.append("</tr>");
    }
  }

  /**
   * Construct a new {@link SelectionGridBulkRenderer}.
   * 
   * @param grid {@link SelectionGrid} to be be bulk rendered
   * @param tableDef the table definition that should be used during rendering
   */
  public SelectionGridBulkRenderer(SelectionGrid grid,
      TableDefinition<RowType> tableDef) {
    super(grid, tableDef);
  }

  /**
   * Construct a new {@link SelectionGridBulkRenderer}.
   * 
   * @param grid {@link SelectionGrid} to be be bulk rendered
   * @param sourceTableDef the external source of the table definition
   */
  public SelectionGridBulkRenderer(SelectionGrid grid,
      HasTableDefinition<RowType> sourceTableDef) {
    super(grid, sourceTableDef);
  }

  @Override
  protected HTMLCellView<RowType> createCellView(final RenderingOptions options) {
    return new SelectionBulkCellView(options);
  }

}
